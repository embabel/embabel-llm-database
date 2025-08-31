import { defineMock } from 'vite-plugin-mock-dev-server'

import models from './models';

export default [
    defineMock({
        url: '/api/v1/models/recommend',
        method: 'POST',
        response: (req, res) => {
            const hasHeader = req?.headers?.['X-embabel-request-id']
            let responseData = null;
            if (hasHeader) {
                console.log('header passed');
                //send back the full body
                responseData =  {
                    "models": [
                        {
                            "modelId": "9f43df68-4beb-4983-ae6c-42e2953f4086",
                            "name": "Llama 3.1 70B Instruct",
                            "provider": "sambanova",
                            "knowledgeCutoffDate": null,
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
                            "parameters": 0,
                            "modelName": "llama-3.1-70b-instruct",
                            "type": "LLM"
                        },
                        {
                            "modelId": "fe292fcc-1418-423e-aa6b-465456da5528",
                            "name": "Llama 3.1 8B Instruct",
                            "provider": "sambanova",
                            "knowledgeCutoffDate": null,
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
                            "parameters": 0,
                            "modelName": "llama-3.1-8b-instruct",
                            "type": "LLM"
                        },
                        {
                            "modelId": "20922f3d-4eae-4b94-8933-c2c04b1292f5",
                            "name": "Llama 3.2 11B Instruct",
                            "provider": "sambanova",
                            "knowledgeCutoffDate": null,
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
                            "parameters": 0,
                            "modelName": "llama-3.2-11b-instruct",
                            "type": "LLM"
                        },
                        {
                            "modelId": "d452e1a0-a9d2-404a-95d1-11d6022206fc",
                            "name": "Llama 3.3 70B Instruct",
                            "provider": "sambanova",
                            "knowledgeCutoffDate": null,
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
                            "parameters": 0,
                            "modelName": "llama-3.3-70b-instruct",
                            "type": "LLM"
                        },
                        {
                            "modelId": "128ab79f-d57c-4aeb-8272-ad5f60e6c63c",
                            "name": "Llama 4 Maverick",
                            "provider": "sambanova",
                            "knowledgeCutoffDate": null,
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
                            "parameters": 0,
                            "modelName": "llama-4-maverick",
                            "type": "LLM"
                        },
                        {
                            "modelId": "e88154ac-cd34-4f99-8027-3e38eda60391",
                            "name": "Qwen3 32B",
                            "provider": "sambanova",
                            "knowledgeCutoffDate": null,
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
                            "parameters": 0,
                            "modelName": "qwen3-32b",
                            "type": "LLM"
                        }
                    ]
                }                
            } else {
                console.log('no header');
                responseData =  {
                    "providers" : "cohere,sambanova,together,azure,hyperbolic,deepinfra,google,fireworks,groq,novita,deepseek,xai,bedrock,lambda,replicate,anthropic,cerebras,zeroeval,mistral,openai"
                }
            }
            res.status = 200
            res.setHeader('X-embabel-request-id','1234')
            res.end(JSON.stringify(responseData));
        }
    }),
    defineMock({
        url: '/api/v1/models/count',
        method: 'GET',
        body: () => {
            return {
                "count": models.value.length
            }
        }
    }),
    defineMock({
        url: '/api/v1/models/lastUpdated',
        method: 'GET',
        body: () => {
            return "2025-08-30T11:16:35.709156";
        }
    }),
    defineMock({
        url: '/api/v1/models/search/findByTags',
        method: 'GET',
        body: (req) => {
            const { tags } = req.query;
            const tagArray = Array.isArray(tags) ? tags : [tags];
            const lowerTags = tagArray.map((t) => t.toLowerCase());
            const m = models.value.filter((model) => {
                if (!Array.isArray(model.tags)) return false;//quick out
                return model.tags.some((tag) => lowerTags.includes(tag.toLowerCase()));
            });
            return m ? m : { error: 'models not found'};
        }        
    }),
    defineMock({
        url: '/api/v1/models/search/findByProvider',
        method: 'GET',
        body: (req) => {
            const { provider } = req.query;
            const m = models.value.filter((model) => model.provider.toLowerCase().includes(provider.toLowerCase()));
            return m ? m : { error: 'model not found'};
        }}),
    defineMock({
        url: '/api/v1/models/search/findByNameContains',
        method: 'GET',
        body: (req) => {
            const { name } = req.query;
            const m = models.value.filter((model) => model.name.toLowerCase().includes(name.toLowerCase()));
            return m ? m : { error: 'model not found'};
        }
    }),    
    defineMock({
        url: '/api/v1/models/:id',
        method: 'GET',
        body: (req) => { 
            const { id } = req.params
            const m = models.value.find((model) => model.modelId === id);
            return m ? m : { error: 'model not found'};
        }
    }),
    defineMock({
        url: '/api/v1/models',
        method: 'GET',
        body: () => {return models.value;} })
];