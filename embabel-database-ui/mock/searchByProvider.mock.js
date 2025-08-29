import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/models/search/findByProvider?provider=sambanova',
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
    ]
})