import { defineMock } from 'vite-plugin-mock-dev-server'

export default [
  defineMock({
      url: '/api/v1/agents/AiModelRepositoryAgent/processes',
      method: 'GET',
      body: () => [
          "zealous_roentgen"
      ]
  }),
  defineMock({
    url: '/api/v1/agents/AiModelRepositoryAgent',
    method: 'POST',
    body: () => {
      return ['zealous_roentgen'];
    }
  })
];