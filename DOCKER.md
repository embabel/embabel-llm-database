# Docker

## Build
`docker build -t embabel-database-server:latest .`

## Run

### Prequisites
- ollama server (`OLLAMA_HOST=0.0.0.0 ollama serve`)
- Llama3.1:8b model (`ollama pull llama3.1:8b)


### Running
`docker run --name embabel-database-server -p 8000:8000 -e SPRING_AI_OLLAMA_BASE-URL=[your ollama server url] -d embabel-database-server:latest`

