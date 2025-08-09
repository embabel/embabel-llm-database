# JSON Schemas for LLM Stats Data

This directory contains JSON Schema definitions for all data types used in the LLM Stats project. These schemas define the structure, types, and validation rules for data stored in the hierarchical file system under `data/`.

## Schema Files

### Core Entity Schemas

- **`organization.schema.json`** - Schema for AI/ML organizations (e.g., OpenAI, Anthropic)
- **`model.schema.json`** - Schema for model metadata
- **`license.schema.json`** - Schema for software licenses governing model usage
- **`benchmark.schema.json`** - Schema for evaluation benchmark definitions
- **`provider.schema.json`** - Schema for model inference providers (e.g., AWS Bedrock, Google Vertex)

### Relationship Schemas

- **`benchmark-results.schema.json`** - Schema for model performance scores on benchmarks
- **`provider-models.schema.json`** - Schema for provider-specific model configurations and pricing

## Data Structure

The schemas correspond to data organized hierarchically:

```
data/
├── organizations/
│   └── [org_id]/
│       ├── organization.json    # Validates against organization.schema.json
│       └── models/
│           └── [model_id]/
│               ├── model.json       # Validates against model.schema.json
│               └── benchmarks.json  # Array validating against benchmark-results.schema.json
├── providers/
│   └── [provider_id]/
│       ├── provider.json        # Validates against provider.schema.json
│       └── models.json          # Array validating against provider-models.schema.json
├── licenses/
│   └── [license_id].json        # Validates against license.schema.json
└── benchmarks/
    └── [benchmark_id].json      # Validates against benchmark.schema.json
```

## Usage

These schemas can be used for:

1. **Data Validation** - Ensure all data files conform to expected structure
2. **Documentation** - Understand what fields are available and their meanings
3. **Code Generation** - Generate TypeScript interfaces or other language types
4. **API Contracts** - Define expected request/response formats

## Validation Example

To validate a data file against its schema using Python:

```python
import json
import jsonschema

# Load schema
with open('schemas/model.schema.json') as f:
    schema = json.load(f)

# Load data
with open('data/organizations/openai/models/gpt-4/model.json') as f:
    data = json.load(f)

# Validate
jsonschema.validate(instance=data, schema=schema)
```

## Schema Features

All schemas use JSON Schema Draft 7 and include:

- **Descriptions** - Every field has a human-readable description
- **Types** - Strict type definitions with null handling
- **Patterns** - Regular expressions for ID formats
- **Examples** - Real-world examples for clarity
- **Enums** - Restricted value sets where applicable
- **Format Validators** - For dates, URIs, etc.
- **Required Fields** - Clearly defined required vs optional

## Contributing

When adding new fields or modifying schemas:

1. Update the relevant schema file
2. Add clear descriptions and examples
3. Consider backward compatibility
4. Update this README if adding new schemas
5. Validate existing data against updated schemas

## Schema Versioning

Currently, all schemas target JSON Schema Draft 7. Future versions may adopt newer drafts as tooling support improves.
