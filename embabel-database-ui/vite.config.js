import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import { mockDevServerPlugin } from 'vite-plugin-mock-dev-server'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    mockDevServerPlugin()
  ],
  server: {
    proxy: {
      '^/api': 'http://embabel.com'
    }
  }
})
