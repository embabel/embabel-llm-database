import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/models/search/findByName?name=llama',
    method: 'GET',
    body: () => [
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
    ]
})