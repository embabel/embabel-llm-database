import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/models/search/findByTags?tags=image-to-text',
    method: 'GET',
    body: () => [                
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