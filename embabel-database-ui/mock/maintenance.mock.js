import { defineMock } from 'vite-plugin-mock-dev-server'
import { randomUUID } from 'crypto'

export default defineMock({
  url: '/api/v1/agents/AiModelRepositoryAgent',
  method: 'POST',
  body: ({ params }) => {
    const { agentId } = 'zealous_roentgen';
    return agentId;
  }
})