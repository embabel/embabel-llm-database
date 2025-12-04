import { defineMock } from 'vite-plugin-mock-dev-server'

// import models from './models';
import models from './models.json';

export default [
    defineMock({
        url: '/api/v1/models/recommend',
        method: 'POST',
        response: (req, res) => {
            const hasHeader = req?.headers?.['x-embabel-request-id']
            let responseData = null;
            if (hasHeader) {
                console.log('header passed');
                //send back the full body
                responseData =  {
                    "models": {
                        "message": "Here are the suggested models",
                        "listModels": {
                            "models": [
                                {
                                    "name": "Gemini 3 Pro",
                                    "id": "gemini-3-pro-preview",
                                    "tags": [
                                        "audio-video-image-text-to-text"
                                    ],
                                    "knowledgeCutoff": "2025-01-31",
                                    "releaseDate": "2025-11-18",
                                    "parameterCount": 0,
                                    "organization": {
                                        "id": "google",
                                        "name": "Google",
                                        "website": "https://google.com"
                                    },
                                    "multiModal": true,
                                    "modelProviders": [
                                        {
                                        "id": "google",
                                        "provider": {
                                            "id": "google",
                                            "name": "Google",
                                            "website": "https://ai.google.dev"
                                        },
                                        "inputPerMillion": 2.0,
                                        "outputPerMillion": 12.0,
                                        "tags": [],
                                        "deprecated": false
                                        }
                                    ],
                                    "description": "Gemini 3 Pro is the first model in the new Gemini 3 series. It is best for complex tasks that require broad world knowledge and advanced reasoning across modalities. Gemini 3 Pro uses dynamic thinking by default to reason through prompts, and features a 1 million-token input context window with 64k output tokens."
                                }
                            ]
                        }
                    }
                }              
            } else {
                console.log('no header');
                responseData =  {
                    "providers": {
                        "message": "Please choose your preferred provider from the following list",
                        "providers": [
                        "Anthropic",
                        "Google"
                        ]
                    }
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
                "count": models.length
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
            const m = models.filter((model) => model.provider.toLowerCase().includes(provider.toLowerCase()));
            return m ? m : { error: 'model not found'};
        }}),
    defineMock({
        url: '/api/v1/models/search/findByNameContains',
        method: 'GET',
        body: (req) => {
            const { name } = req.query;
            const m = models.filter((model) => model.name.toLowerCase().includes(name.toLowerCase()));
            return m ? m : { error: 'model not found'};
        }
    }),    
    defineMock({
        url: '/api/v1/models/:id',
        method: 'GET',
        body: (req) => { 
            const { id } = req.params
            const m = models.find((model) => model.id === id);
            return m ? m : { error: 'model not found (by id)'};
        }
    }),
    defineMock({
        url: '/api/v1/models',
        method: 'GET',
        body: () => {
            return models;
        }
    })
];