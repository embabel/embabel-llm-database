import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/models',
    method: 'GET',
    body: () => [
    {
        "modelId":"some-model-id-001",
        "name": "command-r-plus-04-2024",
        "provider": "Command R+",
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
        "modelId":"some-model-id-002",
        "name": "llama-3.1-70b-instruct",
        "provider": "Llama 3.1 70B Instruct",
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
        "modelId":"some-model-id-003",
        "name": "llama-3.1-8b-instruct",
        "provider": "Llama 3.1 8B Instruct",
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
        "modelId":"some-model-id-004",
        "name": "llama-3.2-11b-instruct",
        "provider": "Llama 3.2 11B Instruct",
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
        "modelId":"some-model-id-005",
        "name": "llama-3.3-70b-instruct",
        "provider": "Llama 3.3 70B Instruct",
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
        "modelId":"some-model-id-006",
        "name": "llama-4-maverick",
        "provider": "Llama 4 Maverick",
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
        "modelId":"some-model-id-007",
        "name": "qwen3-32b",
        "provider": "Qwen3 32B",
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
        "modelId":"some-model-id-008",
        "name": "deepseek-r1",
        "provider": "DeepSeek-R1",
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
        "modelId":"some-model-id-009",
        "name": "gemma-3n-e4b-it",
        "provider": "Gemma 3n E4B Instructed",
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
        "modelId":"some-model-id-010",
        "name": "llama-3.1-405b-instruct",
        "provider": "Llama 3.1 405B Instruct",
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
        "modelId":"some-model-id-011",
        "name": "llama-3.1-70b-instruct",
        "provider": "Llama 3.1 70B Instruct",
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
        "modelId":"some-model-id-012",
        "name": "llama-3.1-8b-instruct",
        "provider": "Llama 3.1 8B Instruct",
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
        "modelId":"some-model-id-013",
        "name": "llama-3.2-11b-instruct",
        "provider": "Llama 3.2 11B Instruct",
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
        "modelId":"some-model-id-014",
        "name": "llama-3.2-90b-instruct",
        "provider": "Llama 3.2 90B Instruct",
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
        "modelId":"some-model-id-015",
        "name": "llama-3.3-70b-instruct",
        "provider": "Llama 3.3 70B Instruct",
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
        "modelId":"some-model-id-016",
        "name": "llama-4-maverick",
        "provider": "Llama 4 Maverick",
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
        "modelId":"some-model-id-017",
        "name": "llama-4-scout",
        "provider": "Llama 4 Scout",
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
        "modelId":"some-model-id-018",
        "name": "qwen-2.5-72b-instruct",
        "provider": "Qwen2.5 72B Instruct",
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
        "modelId":"some-model-id-019",
        "name": "qwen-2.5-7b-instruct",
        "provider": "Qwen2.5 7B Instruct",
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
        "modelId":"some-model-id-020",
        "name": "qwen3-235b-a22b",
        "provider": "Qwen3 235B A22B",
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
    }    
],
});