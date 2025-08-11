# Docker

## Build
`docker build -t embabel-llm-database-server:latest .`

## Run

### Prequisites
- ollama server (`OLLAMA_HOST=0.0.0.0 ollama serve`)
- Llama3.1:8b model (`ollama pull llama3.1:8b`)

### Running
`docker run --name embabel-llm-database-server -p 8000:8000 -e SPRING_AI_OLLAMA_BASE-URL=[your ollama server url] -d embabel-database-server:latest`

### Ollama Stub Docker Container
- Runs a self-contained Ollama Stub that provides `llama3.1:8b` model
- `docker run --name ollama-stub -p 11434:11434 -d embabel-database-server-ollama-stub:latest`

