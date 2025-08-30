import { defineMock } from 'vite-plugin-mock-dev-server'

import models from './models';

export default [
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