const fs = require("fs");
const path = require("path");
const tv4 = require("tv4");
const glob = require("glob");

function validateSchema(schemaName, filePattern, isArray = false) {
  console.log(`\nValidating ${schemaName}...`);
  const schemaPath = path.join(__dirname, `${schemaName}.schema.json`);

  let schema;
  try {
    schema = JSON.parse(fs.readFileSync(schemaPath, "utf8"));
  } catch (error) {
    console.error(`Error reading schema file: ${schemaPath}`);
    console.error(error);
    return false;
  }

  const files = glob.sync(path.join(__dirname, "..", filePattern));

  if (files.length === 0) {
    console.warn(`⚠️ No files found matching pattern: ${filePattern}`);
    return true;
  }

  let isValid = true;

  for (const file of files) {
    try {
      const data = JSON.parse(fs.readFileSync(file, "utf8"));

      // If expecting an array, validate each item
      if (isArray) {
        if (!Array.isArray(data)) {
          console.error(
            `❌ Invalid: ${file} - Expected array but got ${typeof data}`
          );
          isValid = false;
          continue;
        }

        let allItemsValid = true;
        data.forEach((item, index) => {
          const result = tv4.validateMultiple(item, schema);
          if (!result.valid) {
            console.error(`❌ Invalid item [${index}] in: ${file}`);
            result.errors.forEach((error) =>
              console.error(`  - ${error.message} at ${error.dataPath}`)
            );
            allItemsValid = false;
          }
        });

        if (allItemsValid) {
          console.log(`✅ Valid: ${file} (${data.length} items)`);
        } else {
          isValid = false;
        }
      } else {
        // Single object validation
        const result = tv4.validateMultiple(data, schema);

        if (result.valid) {
          console.log(`✅ Valid: ${file}`);
        } else {
          console.error(`❌ Invalid: ${file}`);
          result.errors.forEach((error) =>
            console.error(`  - ${error.message} at ${error.dataPath}`)
          );
          isValid = false;
        }
      }
    } catch (error) {
      console.error(`Error processing file: ${file}`);
      console.error(error);
      isValid = false;
    }
  }

  return isValid;
}

console.log("🔍 Validating LLM Stats Data Structure...\n");
console.log("=".repeat(60));
console.log("Phase 1: Schema Validation");
console.log("=".repeat(60));

// Validate all data types
const validations = [
  // Core entities
  {
    schema: "organization",
    pattern: "data/organizations/*/organization.json",
  },
  {
    schema: "model",
    pattern: "data/organizations/*/models/*/model.json",
  },
  { schema: "license", pattern: "data/licenses/*.json" },
  { schema: "benchmark", pattern: "data/benchmarks/*.json" },
  { schema: "provider", pattern: "data/providers/*/provider.json" },

  // Arrays
  {
    schema: "benchmark-results",
    pattern: "data/organizations/*/models/*/benchmarks.json",
    isArray: true,
  },
  {
    schema: "provider-models",
    pattern: "data/providers/*/models.json",
    isArray: true,
  },
];

let allValid = true;

for (const { schema, pattern, isArray } of validations) {
  const isValid = validateSchema(schema, pattern, isArray);
  allValid = allValid && isValid;
}

if (allValid) {
  console.log("\n✅ All schemas are valid! 🎉");

  // Run integrity validation
  console.log("\n" + "=".repeat(60));
  console.log("Phase 2: Data Integrity Validation");
  console.log("=".repeat(60));

  const IntegrityValidator = require("./integrity-validator.js");
  const integrityValidator = new IntegrityValidator();

  integrityValidator.validate().then((integrityValid) => {
    if (integrityValid) {
      console.log("\n🎉 All validations passed successfully!");
      process.exit(0);
    } else {
      console.error("\n❌ Data integrity validation failed.");
      process.exit(1);
    }
  });
} else {
  console.error("\n❌ Schema validation failed.");
  process.exit(1);
}
