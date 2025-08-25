import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/models/some-model-id-001',
    method: 'GET',
    body: () => {
        return {
            "modelId": "some-id",
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
        }}
});