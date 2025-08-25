import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/tags',
    method: 'GET',
    body: () => [
        {
            "tagId":1,
            "tag": "image-to-text",
            "inputText": false,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": true,
            "outputText": true,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": false
        },
        {
            "tagId":2,
            "tag": "image-text-to-text",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": true,
            "outputText": true,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": false
        },
        {
            "tagId":3,
            "tag": "text-to-image",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": false,
            "outputText": false,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": true
        },
        {
            "tagId":4,
            "tag": "text-to-image-text",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": false,
            "outputText": true,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": true
        },
        {
            "tagId":5,
            "tag": "text-to-video",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": false,
            "outputText": false,
            "outputAudio": false,
            "outputVideo": true,
            "outputImage": false
        },
        {
            "tagId":6,
            "tag": "video-text-to-text",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": true,
            "inputImage": false,
            "outputText": true,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": false
        },
        {
            "tagId":7,
            "tag": "audio-to-text",
            "inputText": false,
            "inputAudio": true,
            "inputVideo": false,
            "inputImage": false,
            "outputText": true,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": false
        },
        {
            "tagId":8,
            "tag": "text-to-audio",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": false,
            "outputText": false,
            "outputAudio": true,
            "outputVideo": false,
            "outputImage": false
        },
        {
            "tagId":9,
            "tag": "audio-to-audio",
            "inputText": true,
            "inputAudio": true,
            "inputVideo": false,
            "inputImage": false,
            "outputText": false,
            "outputAudio": true,
            "outputVideo": false,
            "outputImage": false
        },
        {
            "tagId":10,
            "tag": "image-text-to-video",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": true,
            "outputText": false,
            "outputAudio": false,
            "outputVideo": true,
            "outputImage": false
        },
        {
            "tagId":11,
            "tag": "text-to-text",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": false,
            "outputText": true,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": false
        },
        {
            "tagId":12,
            "tag": "audio-video-image-text-to-text",
            "inputText": true,
            "inputAudio": true,
            "inputVideo": true,
            "inputImage": true,
            "outputText": true,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": false
        },
        {
            "tagId":13,
            "tag": "text-to-embedding",
            "inputText": true,
            "inputAudio": false,
            "inputVideo": false,
            "inputImage": false,
            "outputText": false,
            "outputAudio": false,
            "outputVideo": false,
            "outputImage": false
        }
    ],
})