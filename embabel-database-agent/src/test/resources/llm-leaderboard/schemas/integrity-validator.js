const fs = require("fs");
const path = require("path");
const glob = require("glob");

class IntegrityValidator {
  constructor(dataDir) {
    this.dataDir = dataDir || path.join(__dirname, "..", "data");
    this.errors = [];
    this.warnings = [];

    // Collections to store all entities
    this.models = new Map();
    this.benchmarks = new Map();
    this.organizations = new Map();
    this.licenses = new Map();
    this.providers = new Map();

    // Maps to check for duplicates
    // Note: Model names can be duplicated (e.g., different versions), only IDs must be unique
    this.benchmarkNames = new Map();
  }

  loadJSON(filePath) {
    try {
      const content = fs.readFileSync(filePath, "utf8");
      return JSON.parse(content);
    } catch (error) {
      this.errors.push(`Failed to load ${filePath}: ${error.message}`);
      return null;
    }
  }

  // Load all data into memory
  async loadAllData() {
    console.log("\n📂 Loading all data files...\n");

    // Load organizations
    const orgFiles = glob.sync(
      path.join(this.dataDir, "organizations/*/organization.json")
    );
    for (const file of orgFiles) {
      const data = this.loadJSON(file);
      if (data) {
        this.organizations.set(data.organization_id, data);
      }
    }
    console.log(`✅ Loaded ${this.organizations.size} organizations`);

    // Load models
    const modelFiles = glob.sync(
      path.join(this.dataDir, "organizations/*/models/*/model.json")
    );
    for (const file of modelFiles) {
      const data = this.loadJSON(file);
      if (data) {
        // Check for duplicate model IDs
        if (this.models.has(data.model_id)) {
          const existing = this.models.get(data.model_id);
          this.errors.push(
            `❌ Duplicate model ID "${data.model_id}" found:\n` +
            `   - First occurrence: ${path.relative(this.dataDir, existing.file)}\n` +
            `   - Duplicate found: ${path.relative(this.dataDir, file)}`
          );
        }
        this.models.set(data.model_id, { ...data, file });
      }
    }
    console.log(`✅ Loaded ${this.models.size} models`);

    // Load benchmarks
    const benchmarkFiles = glob.sync(
      path.join(this.dataDir, "benchmarks/*.json")
    );
    for (const file of benchmarkFiles) {
      const data = this.loadJSON(file);
      if (data) {
        // Check for duplicate benchmark IDs
        if (this.benchmarks.has(data.benchmark_id)) {
          const existing = this.benchmarks.get(data.benchmark_id);
          this.errors.push(
            `❌ Duplicate benchmark ID "${data.benchmark_id}" found:\n` +
            `   - First occurrence: ${path.relative(this.dataDir, existing.file)}\n` +
            `   - Duplicate found: ${path.relative(this.dataDir, file)}`
          );
        }
        this.benchmarks.set(data.benchmark_id, { ...data, file });

        // Check for duplicate benchmark names
        if (this.benchmarkNames.has(data.name)) {
          this.benchmarkNames
            .get(data.name)
            .push({ id: data.benchmark_id, file });
        } else {
          this.benchmarkNames.set(data.name, [{ id: data.benchmark_id, file }]);
        }
      }
    }
    console.log(`✅ Loaded ${this.benchmarks.size} benchmarks`);

    // Load licenses
    const licenseFiles = glob.sync(path.join(this.dataDir, "licenses/*.json"));
    for (const file of licenseFiles) {
      const data = this.loadJSON(file);
      if (data) {
        this.licenses.set(data.license_id, data);
      }
    }
    console.log(`✅ Loaded ${this.licenses.size} licenses`);

    // Load providers
    const providerFiles = glob.sync(
      path.join(this.dataDir, "providers/*/provider.json")
    );
    for (const file of providerFiles) {
      const data = this.loadJSON(file);
      if (data) {
        this.providers.set(data.provider_id, data);
      }
    }
    console.log(`✅ Loaded ${this.providers.size} providers`);
  }

  // Check for duplicate names
  checkDuplicates() {
    console.log("\n🔍 Checking for duplicate names...\n");

    let duplicatesFound = false;

    // Check duplicate benchmark names (benchmark names should be unique)
    for (const [name, instances] of this.benchmarkNames.entries()) {
      if (instances.length > 1) {
        duplicatesFound = true;
        this.errors.push(
          `❌ Duplicate benchmark name "${name}" found in ${instances.length} benchmarks:\n` +
            instances
              .map(
                (i) => `   - ${i.id} in ${path.relative(this.dataDir, i.file)}`
              )
              .join("\n")
        );
      }
    }

    if (!duplicatesFound) {
      console.log("✅ No duplicate benchmark names found");
    }

    // Note: Model names can be duplicated (e.g., different versions of the same model)
    // IDs are checked during loading and must be unique
  }

  // Check all references
  checkReferences() {
    console.log("\n🔗 Checking references...\n");

    // Check model references
    for (const [modelId, model] of this.models.entries()) {
      const relPath = path.relative(this.dataDir, model.file);

      // Check organization reference
      if (
        model.organization_id &&
        !this.organizations.has(model.organization_id)
      ) {
        this.errors.push(
          `❌ Model "${modelId}" references non-existent organization "${model.organization_id}"\n` +
            `   in ${relPath}`
        );
      }

      // Check license reference
      if (model.license_id && !this.licenses.has(model.license_id)) {
        this.errors.push(
          `❌ Model "${modelId}" references non-existent license "${model.license_id}"\n` +
            `   in ${relPath}`
        );
      }

      // Check fine-tuned from reference
      if (
        model.fine_tuned_from_model_id &&
        !this.models.has(model.fine_tuned_from_model_id)
      ) {
        this.errors.push(
          `❌ Model "${modelId}" references non-existent base model "${model.fine_tuned_from_model_id}"\n` +
            `   in ${relPath}`
        );
      }

      // Check model family reference
      if (model.model_family_id && !this.models.has(model.model_family_id)) {
        this.warnings.push(
          `⚠️  Model "${modelId}" references model family "${model.model_family_id}" which doesn't exist as a model\n` +
            `   in ${relPath}`
        );
      }
    }

    // Check benchmark results references
    const benchmarkResultFiles = glob.sync(
      path.join(this.dataDir, "organizations/*/models/*/benchmarks.json")
    );

    for (const file of benchmarkResultFiles) {
      const results = this.loadJSON(file);
      if (results && Array.isArray(results)) {
        const relPath = path.relative(this.dataDir, file);

        for (let i = 0; i < results.length; i++) {
          const result = results[i];

          // Check model_id reference
          if (result.model_id && !this.models.has(result.model_id)) {
            this.errors.push(
              `❌ Benchmark result [${i}] references non-existent model "${result.model_id}"\n` +
                `   in ${relPath}`
            );
          }

          // Check benchmark_id reference
          if (
            result.benchmark_id &&
            !this.benchmarks.has(result.benchmark_id)
          ) {
            this.errors.push(
              `❌ Benchmark result [${i}] references non-existent benchmark "${result.benchmark_id}"\n` +
                `   in ${relPath}`
            );
          }

          // Check verification_provider_id reference
          if (
            result.verification_provider_id &&
            !this.providers.has(result.verification_provider_id)
          ) {
            this.warnings.push(
              `⚠️  Benchmark result [${i}] references non-existent verification provider "${result.verification_provider_id}"\n` +
                `   in ${relPath}`
            );
          }
        }
      }
    }

    // Check provider models references
    const providerModelFiles = glob.sync(
      path.join(this.dataDir, "providers/*/models.json")
    );

    for (const file of providerModelFiles) {
      const models = this.loadJSON(file);
      if (models && Array.isArray(models)) {
        const relPath = path.relative(this.dataDir, file);

        for (let i = 0; i < models.length; i++) {
          const providerModel = models[i];

          // Check model_id reference
          if (
            providerModel.model_id &&
            !this.models.has(providerModel.model_id)
          ) {
            this.errors.push(
              `❌ Provider model [${i}] references non-existent model "${providerModel.model_id}"\n` +
                `   in ${relPath}`
            );
          }

          // Check provider_id reference
          if (
            providerModel.provider_id &&
            !this.providers.has(providerModel.provider_id)
          ) {
            this.errors.push(
              `❌ Provider model [${i}] references non-existent provider "${providerModel.provider_id}"\n` +
                `   in ${relPath}`
            );
          }
        }
      }
    }

    // Check benchmark parent references
    for (const [benchmarkId, benchmark] of this.benchmarks.entries()) {
      if (
        benchmark.parent_benchmark_id &&
        !this.benchmarks.has(benchmark.parent_benchmark_id)
      ) {
        const relPath = path.relative(this.dataDir, benchmark.file);
        this.errors.push(
          `❌ Benchmark "${benchmarkId}" references non-existent parent benchmark "${benchmark.parent_benchmark_id}"\n` +
            `   in ${relPath}`
        );
      }
    }

    if (this.errors.length === 0 && this.warnings.length === 0) {
      console.log("✅ All references are valid");
    }
  }

  // Check for orphaned data
  checkOrphans() {
    console.log("\n👻 Checking for orphaned data...\n");

    // Check for models without benchmark results
    const modelsWithBenchmarks = new Set();
    const benchmarkResultFiles = glob.sync(
      path.join(this.dataDir, "organizations/*/models/*/benchmarks.json")
    );

    for (const file of benchmarkResultFiles) {
      const results = this.loadJSON(file);
      if (results && Array.isArray(results)) {
        results.forEach((r) => modelsWithBenchmarks.add(r.model_id));
      }
    }

    let modelsWithoutBenchmarks = 0;
    for (const modelId of this.models.keys()) {
      if (!modelsWithBenchmarks.has(modelId)) {
        modelsWithoutBenchmarks++;
      }
    }

    if (modelsWithoutBenchmarks > 0) {
      this.warnings.push(
        `⚠️  ${modelsWithoutBenchmarks} models have no benchmark results`
      );
    }

    // Check for unused benchmarks
    const usedBenchmarks = new Set();
    for (const file of benchmarkResultFiles) {
      const results = this.loadJSON(file);
      if (results && Array.isArray(results)) {
        results.forEach((r) => usedBenchmarks.add(r.benchmark_id));
      }
    }

    let unusedBenchmarks = 0;
    for (const benchmarkId of this.benchmarks.keys()) {
      if (!usedBenchmarks.has(benchmarkId)) {
        unusedBenchmarks++;
      }
    }

    if (unusedBenchmarks > 0) {
      this.warnings.push(
        `⚠️  ${unusedBenchmarks} benchmarks are not used by any model`
      );
    }

    // Check for unused licenses
    const usedLicenses = new Set();
    for (const model of this.models.values()) {
      if (model.license_id) {
        usedLicenses.add(model.license_id);
      }
    }

    let unusedLicenses = 0;
    for (const licenseId of this.licenses.keys()) {
      if (!usedLicenses.has(licenseId)) {
        unusedLicenses++;
      }
    }

    if (unusedLicenses > 0) {
      this.warnings.push(
        `⚠️  ${unusedLicenses} licenses are not used by any model`
      );
    }
  }

  // Main validation function
  async validate() {
    console.log("🔍 Running Data Integrity Validation...\n");
    console.log(`Data directory: ${this.dataDir}\n`);

    await this.loadAllData();
    this.checkDuplicates();
    this.checkReferences();
    this.checkOrphans();

    // Print summary
    console.log("\n" + "=".repeat(60));
    console.log("📊 Validation Summary");
    console.log("=".repeat(60));

    if (this.errors.length > 0) {
      console.log(`\n❌ Found ${this.errors.length} errors:\n`);
      this.errors.forEach((error) => console.log(error));
    }

    if (this.warnings.length > 0) {
      console.log(`\n⚠️  Found ${this.warnings.length} warnings:\n`);
      this.warnings.forEach((warning) => console.log(warning));
    }

    if (this.errors.length === 0 && this.warnings.length === 0) {
      console.log("\n✅ All integrity checks passed! 🎉");
      return true;
    }

    console.log("\n" + "=".repeat(60));

    return this.errors.length === 0;
  }
}

// Run validation if called directly
if (require.main === module) {
  const validator = new IntegrityValidator();
  validator.validate().then((success) => {
    process.exit(success ? 0 : 1);
  });
}

module.exports = IntegrityValidator;
