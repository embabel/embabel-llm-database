import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/models/count',
    method: 'GET',
    body: () => {
        return {
            "count": 101
        }
    }
})