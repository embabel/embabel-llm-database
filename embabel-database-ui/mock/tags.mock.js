import { defineMock } from 'vite-plugin-mock-dev-server'

export default [
    defineMock({
        url: '/api/v1/tags',
        method: 'GET',
        body: () => ["text-to-text", "image-text-to-text", "audio-video-image-text-to-text", "video-text-to-text", "text-to-image"],
    })
]

