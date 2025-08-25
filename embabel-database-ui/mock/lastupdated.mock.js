import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/models/lastUpdated',
    method: 'GET',
    body: () => {
        return new Date().toISOString();
    }
})