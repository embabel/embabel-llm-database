import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/platform-info/agents',
    method: 'GET',
    body: () => [
    {
        "name": "AiModelRepositoryAgent",
        "provider": "com.embabel.database.agent",
        "version": "0.1.0-SNAPSHOT",
        "description": "Discovers and loads AI models from various sources",
        "goals": [
            {
                "name": "com.embabel.database.agent.AiModelRepositoryAgent.maintainCatalog",
                "description": "Retrieves latest models and updates the repository",
                "pre": [
                    "hasRun_com.embabel.database.agent.AiModelRepositoryAgent.maintainCatalog",
                    "it:com.embabel.database.agent.ListModelMetadata",
                    "it:java.time.LocalDateTime"
                ],
                "inputs": [
                    "it:java.time.LocalDateTime"
                ],
                "outputClass": "java.time.LocalDateTime",
                "value": 0.0,
                "tags": [],
                "examples": [],
                "export": {
                    "name": null,
                    "remote": false,
                    "local": true,
                    "startingInputTypes": []
                }
            }
        ],
        "actions": [
            {
                "name": "com.embabel.database.agent.AiModelRepositoryAgent.maintainCatalog",
                "description": "maintainCatalog",
                "inputs": [
                    "it:com.embabel.database.agent.ListModelMetadata"
                ],
                "outputs": [
                    "it:java.time.LocalDateTime"
                ],
                "preconditions": {
                    "it:com.embabel.database.agent.ListModelMetadata": "TRUE",
                    "it:java.time.LocalDateTime": "FALSE",
                    "hasRun_com.embabel.database.agent.AiModelRepositoryAgent.maintainCatalog": "FALSE"
                },
                "effects": {
                    "it:java.time.LocalDateTime": "TRUE",
                    "hasRun_com.embabel.database.agent.AiModelRepositoryAgent.maintainCatalog": "TRUE"
                },
                "cost": 0.0,
                "value": 0.0,
                "canRerun": false,
                "qos": {
                    "maxAttempts": 5,
                    "backoffMillis": 10000,
                    "backoffMultiplier": 5.0,
                    "backoffMaxInterval": 60000,
                    "idempotent": false,
                    "retryPolicy": {
                        "maxAttempts": -1
                    }
                }
            },
            {
                "name": "com.embabel.database.agent.AiModelRepositoryAgent.discoverModels",
                "description": "discoverModels",
                "inputs": [],
                "outputs": [
                    "it:com.embabel.database.agent.ListModelMetadata"
                ],
                "preconditions": {
                    "repository_needs_refresh": "TRUE",
                    "it:com.embabel.database.agent.ListModelMetadata": "FALSE",
                    "hasRun_com.embabel.database.agent.AiModelRepositoryAgent.discoverModels": "FALSE"
                },
                "effects": {
                    "it:com.embabel.database.agent.ListModelMetadata": "TRUE",
                    "hasRun_com.embabel.database.agent.AiModelRepositoryAgent.discoverModels": "TRUE"
                },
                "cost": 0.0,
                "value": 0.0,
                "canRerun": false,
                "qos": {
                    "maxAttempts": 5,
                    "backoffMillis": 10000,
                    "backoffMultiplier": 5.0,
                    "backoffMaxInterval": 60000,
                    "idempotent": false,
                    "retryPolicy": {
                        "maxAttempts": -1
                    }
                }
            },
            {
                "name": "com.embabel.database.agent.AiModelRepositoryAgent.validateModels",
                "description": "validateModels",
                "inputs": [
                    "it:com.embabel.database.agent.ListModelMetadata"
                ],
                "outputs": [
                    "it:com.embabel.database.agent.ListModelMetadata"
                ],
                "preconditions": {
                    "have_models": "TRUE",
                    "it:com.embabel.database.agent.ListModelMetadata": "TRUE",
                    "hasRun_com.embabel.database.agent.AiModelRepositoryAgent.validateModels": "FALSE"
                },
                "effects": {
                    "it:com.embabel.database.agent.ListModelMetadata": "TRUE",
                    "hasRun_com.embabel.database.agent.AiModelRepositoryAgent.validateModels": "TRUE"
                },
                "cost": 0.0,
                "value": 0.0,
                "canRerun": false,
                "qos": {
                    "maxAttempts": 5,
                    "backoffMillis": 10000,
                    "backoffMultiplier": 5.0,
                    "backoffMaxInterval": 60000,
                    "idempotent": false,
                    "retryPolicy": {
                        "maxAttempts": -1
                    }
                }
            }
        ],
        "conditions": [
            "repository_needs_refresh",
            "have_models"
        ]
    }]
});