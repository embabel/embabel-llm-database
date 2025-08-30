import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import { mockDevServerPlugin } from 'vite-plugin-mock-dev-server'
import { resolve } from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    mockDevServerPlugin()
  ],
  build: {
    outDir: resolve(__dirname, '../embabel-database-server/src/main/resources/static'),
    emptyOutDir: true // ensures old builds are cleared
  },
  server: {
    proxy: {
      '^/api': 'http://embabel.com'
    }
  }
})
