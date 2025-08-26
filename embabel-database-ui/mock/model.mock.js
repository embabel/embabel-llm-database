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
});