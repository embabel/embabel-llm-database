import { defineMockData } from "vite-plugin-mock-dev-server";

const models = defineMockData('models',[
            {
                "modelId": "d2a19cdb-b29f-4752-9000-b4ebd318177f",
                "name": "Command R+",
                "provider": "cohere",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 25.0,
                    "usdPer1mOutputTokens": 100.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ce655661-21c1-424f-94d2-af485587ca80",
                "name": "Llama 3.1 70B Instruct",
                "provider": "sambanova",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 500.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "6025ff09-c56c-470e-9a37-2be08c044a94",
                "name": "Llama 3.1 8B Instruct",
                "provider": "sambanova",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "8a40c696-6458-402d-8c64-cafe7e187e4f",
                "name": "Llama 3.2 11B Instruct",
                "provider": "sambanova",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "6c95dd06-113c-4bfc-a571-fe6aa5db1953",
                "name": "Llama 3.3 70B Instruct",
                "provider": "sambanova",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 60.0,
                    "usdPer1mOutputTokens": 120.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b24aa20b-a94c-450a-8328-05a7b5cb1b1c",
                "name": "Llama 4 Maverick",
                "provider": "sambanova",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 63.0,
                    "usdPer1mOutputTokens": 179.0
                },
                "size": 1000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "38bbfea8-4c1f-441e-b537-38d3f20ea27e",
                "name": "Qwen3 32B",
                "provider": "sambanova",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 40.0,
                    "usdPer1mOutputTokens": 80.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "465eca30-0eac-4c86-a564-929a4f309988",
                "name": "DeepSeek-R1",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 700.0,
                    "usdPer1mOutputTokens": 700.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ad9a3ba2-0c64-4b8f-a310-dc2caac7d2c1",
                "name": "Gemma 3n E4B Instructed",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 2000.0,
                    "usdPer1mOutputTokens": 4000.0
                },
                "size": 32000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "7ac84c09-8290-4915-b0c0-f551a6bbfce0",
                "name": "Llama 3.1 405B Instruct",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 350.0,
                    "usdPer1mOutputTokens": 350.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "f1fd3379-4836-44b3-8032-ff0a84d30010",
                "name": "Llama 3.1 70B Instruct",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "919b1624-6b95-4095-8e98-30ae61f3837c",
                "name": "Llama 3.1 8B Instruct",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b70d219f-4a37-45eb-9f7e-05af94a8483b",
                "name": "Llama 3.2 11B Instruct",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 18.0,
                    "usdPer1mOutputTokens": 18.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "3b2567a4-e5b9-456a-8a4d-747b0373621a",
                "name": "Llama 3.2 90B Instruct",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 120.0,
                    "usdPer1mOutputTokens": 120.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c1b699c5-3f66-467e-be3a-db9f75c71cb7",
                "name": "Llama 3.3 70B Instruct",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 88.0,
                    "usdPer1mOutputTokens": 88.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "42d26136-8b39-4778-b5c7-7ff4f1d94f76",
                "name": "Llama 4 Maverick",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 27.0,
                    "usdPer1mOutputTokens": 85.0
                },
                "size": 1000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "3162a214-40b4-463f-9dea-ef3db988971a",
                "name": "Llama 4 Scout",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 18.0,
                    "usdPer1mOutputTokens": 59.0
                },
                "size": 10000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "5e530dff-f3f4-4245-aaa1-f9e3554db633",
                "name": "Qwen2.5 72B Instruct",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 120.0,
                    "usdPer1mOutputTokens": 120.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "09112e57-78c2-4452-a0e3-d47119b9169d",
                "name": "Qwen2.5 7B Instruct",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 30.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "55ece706-b377-4dc7-a8d5-1210871ef1c1",
                "name": "Qwen3 235B A22B",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "87affeaf-9b96-48f1-9e7a-e5ff8efc9b67",
                "name": "QwQ-32B-Preview",
                "provider": "together",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 120.0,
                    "usdPer1mOutputTokens": 120.0
                },
                "size": 32768,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d316aedd-c6d4-4367-90cd-0e68570ea61f",
                "name": "GPT-3.5 Turbo",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 50.0,
                    "usdPer1mOutputTokens": 150.0
                },
                "size": 16385,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "19e52a18-d3be-41ca-b984-cf4d76f002e5",
                "name": "GPT-4",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 3000.0,
                    "usdPer1mOutputTokens": 6000.0
                },
                "size": 32768,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "96a3220d-15e6-421c-8258-103df085318c",
                "name": "GPT-4o",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 250.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "402fffc9-dae6-4481-9b51-715f4ec25ca9",
                "name": "GPT-4o",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 250.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "e605de92-6169-4aa7-a97d-50d4d6406e12",
                "name": "GPT-4o mini",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "aff5a00c-c239-4db5-a88d-2605d890fa69",
                "name": "GPT-4 Turbo",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1000.0,
                    "usdPer1mOutputTokens": 3000.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "66747568-4fbd-42b9-8f0a-a7fa7147facc",
                "name": "o1",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 6000.0
                },
                "size": 200000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "97a6cc11-7055-4f00-becb-85844dec58bb",
                "name": "o1-mini",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 330.0,
                    "usdPer1mOutputTokens": 1320.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "e6071fc4-759d-40d7-aa3f-952e193741e4",
                "name": "o1-preview",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1650.0,
                    "usdPer1mOutputTokens": 6600.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "0a1fcb97-d055-412f-86d8-458bec15e46e",
                "name": "o3-mini",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 110.0,
                    "usdPer1mOutputTokens": 440.0
                },
                "size": 200000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "9504f77b-1638-42da-8601-f971e253abb9",
                "name": "Phi-3.5-mini-instruct",
                "provider": "azure",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 10.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "4c633d6f-dbeb-40c8-ba4e-ba733f839369",
                "name": "DeepSeek-V2.5",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 200.0
                },
                "size": 8192,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "382e169a-8500-4f9e-a7e6-03d5ae034120",
                "name": "Llama 3.1 405B Instruct",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 400.0,
                    "usdPer1mOutputTokens": 400.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "32b9608c-1f00-4a11-a38f-50135955807c",
                "name": "Llama 3.1 70B Instruct",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 40.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "71d0ca0b-f35a-4992-b9b0-590ee74b6410",
                "name": "Llama 3.1 8B Instruct",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 10.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "823ba26b-0e94-4663-b5e0-51e9a94914d8",
                "name": "Llama 3.2 90B Instruct",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 200.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "20966bbd-d168-49d3-80fa-8580e94ba353",
                "name": "Llama 3.3 70B Instruct",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 40.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "6e81fa57-68cf-4b71-a499-d89a80f59657",
                "name": "Qwen2.5 72B Instruct",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 40.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b6a3589c-72af-4e2c-a79a-d6ade55acfe4",
                "name": "Qwen2.5-Coder 32B Instruct",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "3b5d44fc-ca9c-4c3f-ae6c-0c10c8fa554c",
                "name": "QwQ-32B-Preview",
                "provider": "hyperbolic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 32768,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "2a44d519-161d-489c-ad47-c0923348b869",
                "name": "DeepSeek-R1",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 85.0,
                    "usdPer1mOutputTokens": 250.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d1d7715c-ad4e-4036-b227-aa7c5f046a95",
                "name": "DeepSeek-R1-0528",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 50.0,
                    "usdPer1mOutputTokens": 215.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "4bfa1bed-10b9-480e-8534-e04c1667959e",
                "name": "DeepSeek R1 Distill Llama 70B",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "6478af60-4609-4690-b9a5-95b04fd88979",
                "name": "DeepSeek R1 Distill Qwen 32B",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 12.0,
                    "usdPer1mOutputTokens": 18.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "93f81b15-15bf-4a42-82de-c0ef907ec20f",
                "name": "DeepSeek-V2.5",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 70.0,
                    "usdPer1mOutputTokens": 140.0
                },
                "size": 8192,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "cf1d41e1-75c5-4596-978e-cc7794c1cb5b",
                "name": "Gemma 3 12B",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 5.0,
                    "usdPer1mOutputTokens": 10.0
                },
                "size": 131072,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b6b79363-d8fe-4d36-951b-43d036f49d5c",
                "name": "Gemma 3 27B",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 131072,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c47373d4-c721-447c-a4c8-7c22d9f4fa8d",
                "name": "Gemma 3 4B",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 2.0,
                    "usdPer1mOutputTokens": 4.0
                },
                "size": 131072,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "03646638-8495-4a56-8612-291ee57a2f7b",
                "name": "Llama 3.1 405B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 179.0,
                    "usdPer1mOutputTokens": 179.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "183f4dd4-0327-4829-8a7d-5fdcfd12c71a",
                "name": "Llama 3.1 70B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 35.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "e35bfb1c-0f05-4d42-9fae-cb5bfa77e376",
                "name": "Llama 3.1 8B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 5.0,
                    "usdPer1mOutputTokens": 5.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c63f0832-99d6-4ba0-a788-c58e1c7613d6",
                "name": "Llama 3.2 11B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 5.0,
                    "usdPer1mOutputTokens": 5.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "66c0f7e9-733a-497b-bfe2-c93251a1e97e",
                "name": "Llama 3.2 3B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1.0,
                    "usdPer1mOutputTokens": 2.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ac01c025-904a-4c39-8c36-5239ab727797",
                "name": "Llama 3.2 90B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 35.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "cc555b52-2c02-40df-88e9-acb71be4c066",
                "name": "Llama 3.3 70B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 23.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "71d5899e-6fd7-45be-9ff9-9dcbc13c4a19",
                "name": "Llama 4 Maverick",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 17.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 1000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c72fbd8f-0fa2-4ef7-8364-d9979e6abdf6",
                "name": "Llama 4 Scout",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 8.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 10000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "576cfddc-e4d3-44b5-bea3-aafd9ad05aa0",
                "name": "Mistral Small 3 24B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 7.0,
                    "usdPer1mOutputTokens": 14.0
                },
                "size": 32000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "4e6f2f08-1656-40e2-85da-259b884d1ce7",
                "name": "Phi 4",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 7.0,
                    "usdPer1mOutputTokens": 14.0
                },
                "size": 16000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "5b6896ef-81f1-41d2-8098-55892736bef4",
                "name": "Phi-4-multimodal-instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 5.0,
                    "usdPer1mOutputTokens": 10.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "21e0e60a-e521-4f79-b68e-53753e84d606",
                "name": "Qwen2.5 72B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 35.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "dd01ada7-3b18-4d58-a911-e7974523f19a",
                "name": "Qwen2.5-Coder 32B Instruct",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 18.0,
                    "usdPer1mOutputTokens": 18.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "319e6f6c-ffbf-4d45-9ee2-2a4f81c5086e",
                "name": "Qwen3 235B A22B",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ff72159b-6296-4d1c-98c7-0269f384a1d5",
                "name": "Qwen3 30B A3B",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "9646616b-a4f5-4c6e-a3cd-1f06deb85cb2",
                "name": "Qwen3 32B",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b798e7e9-9eea-47c4-8033-4428faa14f25",
                "name": "QwQ-32B-Preview",
                "provider": "deepinfra",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 32768,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "efddda7d-8790-4c66-9d31-910b5890994b",
                "name": "Claude 3.5 Haiku",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 80.0,
                    "usdPer1mOutputTokens": 400.0
                },
                "size": 200000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "6a589e4a-3c1b-47f3-b5de-a10b08ad049e",
                "name": "Claude 3.5 Sonnet",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "8e502482-716e-4275-96c6-60b29a3fbacb",
                "name": "Claude 3.5 Sonnet",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "19d49c4b-f860-4478-beef-6fcd47a48ca4",
                "name": "Claude 3.7 Sonnet",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "210d2902-ecd8-406e-b073-18be8b27f806",
                "name": "Claude 3 Haiku",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 25.0,
                    "usdPer1mOutputTokens": 125.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "fc43cda2-a815-4e06-8f8b-f2be20fc1ed6",
                "name": "Claude 3 Opus",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b680948c-2ca6-4abd-a16c-c3320e33bcf3",
                "name": "Claude 3 Sonnet",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "51d53abd-d908-46cf-8f5a-e46952d7af0a",
                "name": "Claude Opus 4",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "137a230e-cae0-41d6-8efd-ee3d8b5ab88b",
                "name": "Claude Opus 4.1",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "a01d4b35-75c5-434f-a4f5-80eea934fb66",
                "name": "Claude Sonnet 4",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "a2392b68-6f94-4350-8f68-b3351c00f299",
                "name": "Codestral-22B",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 32768,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "3b3c3b60-07ca-462a-9f69-64417a346fac",
                "name": "Gemini 1.0 Pro",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 50.0,
                    "usdPer1mOutputTokens": 150.0
                },
                "size": 32760,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "9d36ce56-ac47-4b7d-b06b-a7388654f9d3",
                "name": "Gemini 1.5 Flash",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "1aff6e12-5aff-4d7b-8e12-bce289563923",
                "name": "Gemini 1.5 Flash 8B",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 7.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "dd197143-a2a7-4c66-988a-14f332c93f3a",
                "name": "Gemini 1.5 Pro",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 250.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 2097152,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "e276c153-e54c-4cad-a2bd-1337ae7db5ec",
                "name": "Gemini 2.0 Flash",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "82044ee4-0a19-41be-be4e-1103f8878ad6",
                "name": "Gemini 2.0 Flash-Lite",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 7.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "954086a3-f4f8-4d82-9f98-1ccde094b93c",
                "name": "Gemini 2.5 Flash",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 30.0,
                    "usdPer1mOutputTokens": 250.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "072ba9a7-8004-4b75-a6f6-706e3702a56f",
                "name": "Gemini 2.5 Flash-Lite",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b734406e-ff4c-43a5-83e7-a251caf77d6e",
                "name": "Gemini 2.5 Pro",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 125.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "91adea46-655f-4919-a737-d80ef4ba1998",
                "name": "Gemini 2.5 Pro Preview 06-05",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 125.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "540de47b-7864-42f9-a86f-2c629a9baa68",
                "name": "Jamba 1.5 Large",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 800.0
                },
                "size": 256000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d162a354-1a65-4b7e-881c-15b518351390",
                "name": "Jamba 1.5 Mini",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 256144,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "8031681f-d049-4791-91f6-ba00aaa2dc5f",
                "name": "Llama 3.1 405B Instruct",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 500.0,
                    "usdPer1mOutputTokens": 1600.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "5709e754-1a15-48ce-939c-351ca1a58bc1",
                "name": "Mistral Large 2",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 600.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "added31e-25ba-400e-a7e6-dfed17d8e7b0",
                "name": "Mistral NeMo Instruct",
                "provider": "google",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 15.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "9ae7a8ee-3b80-4973-9583-1c3e09bc63b1",
                "name": "DeepSeek-R1",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 800.0,
                    "usdPer1mOutputTokens": 800.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "61ceb611-3b2d-4054-b966-cc0f072fcefc",
                "name": "Llama 3.1 405B Instruct",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 300.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "91f69916-f66e-446e-8032-425deb4e8556",
                "name": "Llama 3.1 70B Instruct",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "78c5c236-4248-43c4-9959-72bf73799967",
                "name": "Llama 3.1 8B Instruct",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "1e43ee1d-dc16-47da-b10a-8ba289752779",
                "name": "Llama 3.2 11B Instruct",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "a8f64223-6f69-49d6-a22e-c8940f2b6efd",
                "name": "Llama 3.2 90B Instruct",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "5498ef97-0f6d-4081-94de-33e4703dd8e1",
                "name": "Llama 3.3 70B Instruct",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "67270754-c6b2-40e3-8723-8c9e7d0302f3",
                "name": "Llama 4 Maverick",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 22.0,
                    "usdPer1mOutputTokens": 88.0
                },
                "size": 1000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c0dd98f3-f41d-4453-abec-7f1cec591e70",
                "name": "Llama 4 Scout",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 10000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "140e5cef-fce1-4860-9b27-adfc20f98298",
                "name": "Qwen2.5 72B Instruct",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "340650bb-30ef-4e2f-9aec-71273172454a",
                "name": "Qwen2.5-Coder 32B Instruct",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "34e321bc-f4b5-437b-aca5-c6b9851fe259",
                "name": "Qwen3 235B A22B",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 10.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "6486002b-d604-4c56-b003-a64b572c552f",
                "name": "Qwen3 30B A3B",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "8a9a68a4-ea49-46f7-b759-fe8f56d9237a",
                "name": "QwQ-32B-Preview",
                "provider": "fireworks",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 32768,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "33011fc2-6834-4a38-8226-52dc471da627",
                "name": "Llama 3.1 70B Instruct",
                "provider": "groq",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 59.0,
                    "usdPer1mOutputTokens": 78.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "a0dfe592-718c-47a5-990f-7d8c16b4c949",
                "name": "Llama 3.1 8B Instruct",
                "provider": "groq",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 5.0,
                    "usdPer1mOutputTokens": 8.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "febe9955-54c3-4187-980e-bf2b14692a98",
                "name": "Llama 3.2 11B Instruct",
                "provider": "groq",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 18.0,
                    "usdPer1mOutputTokens": 18.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "a1f73297-4690-44d7-b8e2-6ef5202deead",
                "name": "Llama 3.3 70B Instruct",
                "provider": "groq",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 59.0,
                    "usdPer1mOutputTokens": 790.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b30202af-5385-4500-b6f3-20e902309e5e",
                "name": "Llama 4 Maverick",
                "provider": "groq",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 1000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "83b7bc5d-a35f-4549-aa7c-1a137df6e629",
                "name": "Llama 4 Scout",
                "provider": "groq",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 11.0,
                    "usdPer1mOutputTokens": 34.0
                },
                "size": 10000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "3df00888-a56d-44bb-af86-a9bb0b6e9020",
                "name": "OpenAI OSS 120B",
                "provider": "groq",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 131000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "82ffc33c-78a4-482b-9641-6d31ca5c066e",
                "name": "OpenAI OSS 20B",
                "provider": "groq",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 50.0
                },
                "size": 131000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "adb04596-9e96-4ba0-95e8-d00dd1bf4721",
                "name": "DeepSeek-R1-0528",
                "provider": "novita",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 70.0,
                    "usdPer1mOutputTokens": 250.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c06bb1f1-9036-4f8a-aee5-7e321c68df0b",
                "name": "Gemma 3 27B",
                "provider": "novita",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 11.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 131072,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "7e1c41a4-da68-4af5-a15b-dceab4cc9176",
                "name": "Kimi K2 Instruct",
                "provider": "novita",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 57.0,
                    "usdPer1mOutputTokens": 229.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "e865bc0f-7e1e-4ab9-a6cf-750be1cc6ff8",
                "name": "Llama 4 Maverick",
                "provider": "novita",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 17.0,
                    "usdPer1mOutputTokens": 85.0
                },
                "size": 1000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d1e4dc30-9676-42be-9e6d-435fb5f0ebac",
                "name": "Llama 4 Scout",
                "provider": "novita",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 50.0
                },
                "size": 10000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "f551ac2e-28cf-43f2-9edd-8040612c05c6",
                "name": "Qwen3 235B A22B",
                "provider": "novita",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 80.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "2d18633a-a74c-4ec0-82ce-2ccb1879bc32",
                "name": "Qwen3 30B A3B",
                "provider": "novita",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 44.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "47e5be19-598f-4b71-98d3-32a6c87c237c",
                "name": "Qwen3 32B",
                "provider": "novita",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 44.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "78e56ef6-0ed5-48ac-8c8f-7426096dc4bf",
                "name": "DeepSeek-R1",
                "provider": "deepseek",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 55.0,
                    "usdPer1mOutputTokens": 219.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "fe1570d2-d2b7-4776-ba4a-828da34a3f86",
                "name": "DeepSeek-R1-0528",
                "provider": "deepseek",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 55.0,
                    "usdPer1mOutputTokens": 219.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b54778db-d53b-4977-bd56-023ecd20950b",
                "name": "DeepSeek-V2.5",
                "provider": "deepseek",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 14.0,
                    "usdPer1mOutputTokens": 28.0
                },
                "size": 8192,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "4bfa7946-f31a-48dc-9189-fdfa68ca6e44",
                "name": "DeepSeek-V3",
                "provider": "deepseek",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 27.0,
                    "usdPer1mOutputTokens": 110.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "e279e2f7-b5dc-40a9-9591-487bdef9c6a7",
                "name": "Grok-2",
                "provider": "xai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "60d178fa-cc98-44a2-9f2a-9a975e893d24",
                "name": "Grok-3",
                "provider": "xai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c6c1bdd0-2678-42f7-9f9e-4a9bc5000231",
                "name": "Grok-3 Mini",
                "provider": "xai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 30.0,
                    "usdPer1mOutputTokens": 50.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "218ecd77-b6d3-4eb9-bad9-86fe72cf0e81",
                "name": "Grok-4",
                "provider": "xai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 256000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "9b3a2962-0f06-4b5b-8bda-04ee8ff850ed",
                "name": "Claude 3.5 Haiku",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 80.0,
                    "usdPer1mOutputTokens": 400.0
                },
                "size": 200000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "dd01111e-e7f7-4dae-8d37-3172deddf2e3",
                "name": "Claude 3.5 Sonnet",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c5dbed85-ab80-44f7-bdcc-f41a74c2d903",
                "name": "Claude 3.5 Sonnet",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d5bfccc8-4f82-485b-93e0-f42aefab79c5",
                "name": "Claude 3.7 Sonnet",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b943ab2b-5804-462c-824a-5c87f0bc1755",
                "name": "Claude 3 Haiku",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 25.0,
                    "usdPer1mOutputTokens": 125.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "4b6071d2-567f-4509-9fbf-9b5b0aee3089",
                "name": "Claude 3 Opus",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "9e4c0468-e8e7-4ea9-885f-dc8dbd3c0201",
                "name": "Claude 3 Sonnet",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b67ec476-affb-4156-ac80-f4e5804329db",
                "name": "Claude Opus 4",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "2095f148-5dd0-40db-b81f-77babf85bccf",
                "name": "Claude Opus 4.1",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "6c4fe94f-2ad8-45cb-b610-229fcb0bc674",
                "name": "Claude Sonnet 4",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "17ec24b7-aff1-4d7a-821c-8040595f5283",
                "name": "Command R+",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "0eacd1d6-7e16-4013-bd3b-c119dd0d6f4e",
                "name": "Jamba 1.5 Large",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 800.0
                },
                "size": 256000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "1e93731f-11df-4d5a-bccf-f9a3b3567f6b",
                "name": "Jamba 1.5 Mini",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 256144,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "0d823aa7-35d6-4ead-b9ec-33a69887fff5",
                "name": "Llama 3.1 405B Instruct",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 300.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "004f6e62-bc00-4021-992e-6f1ea1c1a972",
                "name": "Llama 3.1 70B Instruct",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "39b0fc7e-e5f4-4885-8b5e-244f70dad3f9",
                "name": "Llama 3.1 8B Instruct",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 22.0,
                    "usdPer1mOutputTokens": 22.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "61fcb8d4-015a-4e8c-a112-467ba03450cf",
                "name": "Llama 3.2 11B Instruct",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 16.0,
                    "usdPer1mOutputTokens": 16.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "f7f442b9-344d-4931-996a-68f48e6156a3",
                "name": "Llama 3.2 90B Instruct",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 72.0,
                    "usdPer1mOutputTokens": 72.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "6760c009-e4a3-4406-a85a-9e8d472cec2a",
                "name": "Llama 3.3 70B Instruct",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 72.0,
                    "usdPer1mOutputTokens": 72.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d651964c-920a-44f1-be0d-4c8a9feeb20c",
                "name": "Nova Lite",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 6.0,
                    "usdPer1mOutputTokens": 24.0
                },
                "size": 300000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d14417f6-3a40-4cb1-9791-018ffa9ad04b",
                "name": "Nova Micro",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 3.0,
                    "usdPer1mOutputTokens": 14.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "99b8845f-6a6e-45e9-8423-7e0fb92da78e",
                "name": "Nova Pro",
                "provider": "bedrock",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 80.0,
                    "usdPer1mOutputTokens": 320.0
                },
                "size": 300000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "dd627e51-1336-4338-85fb-c3609cc68431",
                "name": "Llama 3.1 405B Instruct",
                "provider": "lambda",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 89.0,
                    "usdPer1mOutputTokens": 89.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "1958d4e2-6cfa-4af8-a58d-b8d096952cd8",
                "name": "Llama 3.1 70B Instruct",
                "provider": "lambda",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ffbc1dee-4b68-49b8-ad1f-25004ef95b7f",
                "name": "Llama 3.1 8B Instruct",
                "provider": "lambda",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 3.0,
                    "usdPer1mOutputTokens": 3.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "56299527-5ac5-4ff2-a934-6c397ba8b7c1",
                "name": "Llama 3.3 70B Instruct",
                "provider": "lambda",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 20.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "559ef3dc-5d22-4881-a7d4-73a0ad89e038",
                "name": "Llama 4 Maverick",
                "provider": "lambda",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 18.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 1000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "14a059b6-57ba-47d9-8e78-71ca99089401",
                "name": "Llama 4 Scout",
                "provider": "lambda",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 8.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 10000000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ca72f61a-5df3-4249-b400-f2157774e18c",
                "name": "Qwen2.5-Coder 32B Instruct",
                "provider": "lambda",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 9.0,
                    "usdPer1mOutputTokens": 9.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "0418f3db-0d30-4b70-b4a0-7e6a16705118",
                "name": "DeepSeek VL2",
                "provider": "replicate",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 950.0,
                    "usdPer1mOutputTokens": 480000.0
                },
                "size": 129280,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b939dfff-a4c3-42ab-8aa0-18f3d95c9aa7",
                "name": "Llama 3.1 405B Instruct",
                "provider": "replicate",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 950.0,
                    "usdPer1mOutputTokens": 950.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b1274950-6379-4a9b-8fb4-f171bb9f5978",
                "name": "Claude 3.5 Haiku",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 100.0,
                    "usdPer1mOutputTokens": 500.0
                },
                "size": 200000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b4c5101d-9dc1-4e67-93f3-2fa9d0e5794e",
                "name": "Claude 3.5 Sonnet",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "40424e51-13c1-425b-b23a-b0b7d5611cbe",
                "name": "Claude 3.7 Sonnet",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "24a26ba5-5f16-4bda-a734-d6ee217e3304",
                "name": "Claude 3 Haiku",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 25.0,
                    "usdPer1mOutputTokens": 125.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c8994b45-4732-4e08-8275-49653b7a6aba",
                "name": "Claude 3 Opus",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "2483e0a8-e1e1-4413-a16a-740869280cdd",
                "name": "Claude 3 Sonnet",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "f79df4e3-19d7-44a1-8db4-90844f827bd8",
                "name": "Claude Opus 4",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "7ac40eb6-d0a5-4ca7-adb9-fb72229a76d7",
                "name": "Claude Opus 4.1",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "53678cd7-c4f7-4c0b-b0a1-b6a9927bb4f4",
                "name": "Claude Sonnet 4",
                "provider": "anthropic",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "2a99fff0-a9d9-4a10-aac2-8a400b83367a",
                "name": "Llama 3.1 70B Instruct",
                "provider": "cerebras",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 60.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "337d9565-4746-454d-a23e-d37c021d27b3",
                "name": "Llama 3.1 8B Instruct",
                "provider": "cerebras",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 10.0
                },
                "size": 131072,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "35f4212d-9a67-4635-b8bf-0ae929bef171",
                "name": "Llama 3.3 70B Instruct",
                "provider": "cerebras",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 70.0,
                    "usdPer1mOutputTokens": 80.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "0978969d-9833-4c0a-bfe7-a224bd3d5fb8",
                "name": "Claude 3.7 Sonnet",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "a8898bbf-a377-487c-9e09-da2323da3c3a",
                "name": "Claude Opus 4",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c8957197-c891-46db-aaa1-a5d300eef950",
                "name": "Claude Opus 4.1",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 7500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "52997f98-437d-4e4e-8e7c-086fd7350b94",
                "name": "Claude Sonnet 4",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "2da0e841-82eb-416a-a0e5-d2e1402e6b32",
                "name": "Gemini 2.5 Flash",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 30.0,
                    "usdPer1mOutputTokens": 250.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "8207b844-c0e7-4dc0-88dc-63fd5811ec30",
                "name": "Gemini 2.5 Pro",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 125.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 1048576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "78d51e71-6cbc-498a-94b3-9ff278cd026c",
                "name": "GPT-4.1 mini",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 40.0,
                    "usdPer1mOutputTokens": 160.0
                },
                "size": 1047576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "9453b2db-c308-4d63-9d1b-437c36314a18",
                "name": "GPT-4o",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 250.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "956fc86a-35e1-487d-aae7-ad2a96255158",
                "name": "Grok-4",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1500.0
                },
                "size": 256000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "37f94820-24d7-47bf-bab3-65cf5ae360c3",
                "name": "OpenAI OSS 120B",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 131000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "e45078f4-d584-492e-a3b4-ae590d61c846",
                "name": "OpenAI OSS 20B",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 50.0
                },
                "size": 131000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ff228114-2844-4195-a17d-b0dd7570159a",
                "name": "GPT-5",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 125.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 400000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "05e37792-258c-4234-8792-e729e6d57e9d",
                "name": "GPT-5 mini",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 25.0,
                    "usdPer1mOutputTokens": 200.0
                },
                "size": 400000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "7ad1890c-c2b4-4411-9a64-ada298dd1221",
                "name": "GPT-5 nano",
                "provider": "zeroeval",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 5.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 400000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d0fd8913-a27c-40af-9fbd-e700940513e3",
                "name": "Codestral-22B",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 32768,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "7bfa75a4-d2ba-4931-9e4f-0675c49caf32",
                "name": "Devstral Medium",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 40.0,
                    "usdPer1mOutputTokens": 200.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "e665365b-970d-4215-857f-81fddf18fe21",
                "name": "Devstral Small 1.1",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b576e9a5-738e-476d-8a2c-3e64d7722449",
                "name": "Ministral 8B Instruct",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 10.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "61b2a5d9-f85d-42a5-9fd4-7f58fec6b416",
                "name": "Mistral Large 2",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 600.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "85382c74-74c8-4f3a-bd46-f2600a3e2e96",
                "name": "Mistral NeMo Instruct",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 15.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "08d64ccf-f801-43f0-856b-00f4c4b8faa0",
                "name": "Mistral Small",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 20.0,
                    "usdPer1mOutputTokens": 60.0
                },
                "size": 32768,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "7ae69729-9bf2-4075-a5d1-49e758dbdec1",
                "name": "Mistral Small 3 24B Instruct",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 32000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "d209c943-1d0d-4a85-bde3-e1ddc734b048",
                "name": "Mistral Small 3.1 24B Base",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 30.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "0e4537a0-523f-4b1a-bb8d-cfd2ee076566",
                "name": "Pixtral-12B",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 15.0,
                    "usdPer1mOutputTokens": 15.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "8184d474-1127-4d60-b17c-d64bde04d6fe",
                "name": "Pixtral Large",
                "provider": "mistral",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 600.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "a9925582-c0f6-44a0-a092-eb42103d8387",
                "name": "GPT-3.5 Turbo",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 50.0,
                    "usdPer1mOutputTokens": 150.0
                },
                "size": 16385,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b05f4885-fef2-4cd2-afe9-e27c00d7d4da",
                "name": "GPT-4",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 3000.0,
                    "usdPer1mOutputTokens": 6000.0
                },
                "size": 32768,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "f36fb21f-3a39-4479-a7ac-5b88f59922cf",
                "name": "GPT-4.1",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 800.0
                },
                "size": 1047576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b138c087-9cfe-4b3c-b136-ca55eb8c4daf",
                "name": "GPT-4.1 mini",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 40.0,
                    "usdPer1mOutputTokens": 160.0
                },
                "size": 1047576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "0c98e0b6-ff86-4084-800c-aeda8c361215",
                "name": "GPT-4.1 nano",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 1047576,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "85ffac73-3b85-4d95-ad34-22ffd70d1dbd",
                "name": "GPT-4.5",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 7500.0,
                    "usdPer1mOutputTokens": 15000.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "55b70190-a961-4fd9-8be0-1e4b5ef9fa42",
                "name": "GPT-4o",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 250.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "f3303df8-abd2-4711-a6f9-0623fcf064b9",
                "name": "GPT-4o",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 250.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 128000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "c9264c14-ab16-492f-ad3b-055dea476b9a",
                "name": "GPT-4 Turbo",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1000.0,
                    "usdPer1mOutputTokens": 3000.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "03770070-3836-4297-822e-b3df209c2081",
                "name": "o1",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 6000.0
                },
                "size": 200000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b274d921-b078-4c0b-ac06-6bb8c393c1cc",
                "name": "o1-mini",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 300.0,
                    "usdPer1mOutputTokens": 1200.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b32d392a-bf85-41ce-b2f3-90aadf26f4ef",
                "name": "o1-preview",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 1500.0,
                    "usdPer1mOutputTokens": 6000.0
                },
                "size": 128000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ec701bca-d358-448c-aec8-02e62936478a",
                "name": "o3",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 200.0,
                    "usdPer1mOutputTokens": 800.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "37721c53-9a59-4e96-87c4-e166a671b528",
                "name": "o3-mini",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 110.0,
                    "usdPer1mOutputTokens": 440.0
                },
                "size": 200000,
                "tags": [
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "b5a60dd0-2f8a-4c3c-b2f7-fa01ec84308e",
                "name": "o3-pro",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 2000.0,
                    "usdPer1mOutputTokens": 8000.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "f6cfb8b4-bbe0-43a9-a4ef-d121ec0a7a74",
                "name": "o4-mini",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 110.0,
                    "usdPer1mOutputTokens": 440.0
                },
                "size": 200000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "aa703b97-4af3-48af-97e7-17dfca7dea66",
                "name": "GPT OSS 120B",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 50.0
                },
                "size": 131072,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "fc130a07-f1d3-4a21-9e94-4d3f46a3f884",
                "name": "GPT OSS 20B",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 10.0,
                    "usdPer1mOutputTokens": 50.0
                },
                "size": 131072,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "ccf04ec1-b32d-41ef-a3f4-316375aab722",
                "name": "GPT-5",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 125.0,
                    "usdPer1mOutputTokens": 1000.0
                },
                "size": 400000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "1c6efbcc-33bc-4d11-90cb-92bd403aa665",
                "name": "GPT-5 mini",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 25.0,
                    "usdPer1mOutputTokens": 200.0
                },
                "size": 400000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            },
            {
                "modelId": "322bc282-b5e9-40dc-a7ad-da771c9561dd",
                "name": "GPT-5 nano",
                "provider": "openai",
                "knowledgeCutoffDate": [
                    1970,
                    1,
                    1
                ],
                "pricingModel": {
                    "usdPer1mInputTokens": 5.0,
                    "usdPer1mOutputTokens": 40.0
                },
                "size": 400000,
                "tags": [
                    "image-to-text",
                    "image-text-to-text",
                    "text-to-image-text",
                    "video-text-to-text",
                    "text-to-text",
                    "audio-video-image-text-to-text"
                ],
                "source": "LlmLeaderboardParser",
                "type": "LLM"
            }
    ])

export default models;