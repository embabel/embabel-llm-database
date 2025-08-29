import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/models/d2a19cdb-b29f-4752-9000-b4ebd318177f',
    method: 'GET',
    body: () => {
        return {
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
        }}
    },
    {
        url: '/api/v1/models/8a40c696-6458-402d-8c64-cafe7e187e4f',
        method: 'GET',
        body: () => {
            return {
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
            }}
    }
);