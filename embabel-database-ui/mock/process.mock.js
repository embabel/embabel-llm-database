import { defineMock } from 'vite-plugin-mock-dev-server'

export default defineMock({
    url: '/api/v1/process/zealous_roentgen',
    method: 'GET',
    body: () => ({
        "id": "zealous_roentgen",
        "status": "COMPLETED",
        "timestamp": "2025-08-30T15:30:28.691605Z",
        "runningTime": "PT15.588515S",
        "result": "2025-08-30T18:30:31.996141",
        "statusUrl": "/api/v1/process/zealous_roentgen",
        "sseUrl": "/events/process/zealous_roentgen/status"
    })
});