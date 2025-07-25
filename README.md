![Build](https://github.com/embabel/embabel-llm-database/actions/workflows/maven.yml/badge.svg)

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)
![Jinja](https://img.shields.io/badge/jinja-white.svg?style=for-the-badge&logo=jinja&logoColor=black)
![JSON](https://img.shields.io/badge/JSON-000?logo=json&logoColor=fff)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-black?style=for-the-badge&logo=sonarqube&logoColor=4E9BCD)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

<img align="left" src="https://github.com/embabel/agent-api/blob/main/images/315px-Meister_der_Weltenchronik_001.jpg?raw=true" width="180">

&nbsp;&nbsp;&nbsp;&nbsp;

# Embabel Large Language Model Database



## Overview

Embabel Database is REST-compliant database based on a simple repository structure.  Default implementation includes an in-memory database with a JSON local file persistence.

The Database is loaded from the Project [Llm LeaderBoard](https://github.com/JonathanChavezTamales/llm-leaderboard/) Project with a dedicated [Agent](./embabel-database-agent/) to load and maintain on a periodic basis.


## Modules

### Embabel Database Core

[Embabel Database Core](./embabel-database-core/) comprises of the core repository definition including models and initial In-Memory implementation

### Embabel Database Agent

[Embabel Database Agent](./embabel-database-agent/) comprises of the repository maintenance agent implemented in Java.  This agent is responsible for retrieveing and updating the repositories from the [Llm LeaderBoard](https://github.com/JonathanChavezTamales/llm-leaderboard/) using a Git and parsing process.  

### Embabel Database Server

[Embabel Database Server](./embabel-database-server/) is a Spring Rest standlone server that supports running the repository and exposing it via REST endpoints.

`GET /api/v1/models` returns an array of JSON objects representing a `ModelMetadata` object
`GET /api/v1/models/search/findByName?name={model name}` returns a list of matching `ModelMetadata` including providers and costs for each matching model
`GET /api/v1/models/lastUpdate` returns a timestamp for when the repository was last refreshed

Repository maintenance is via an Agent approach.  The server provides an MCP Server compliant toolset as well as a direct, manual mechanism to trigger the Agent.  The Agent will validate if the repository needs refreshing.
`POST /api/v1/agents/{agentName}` manually triggers the repository maintenance agent  (e.g. `POST /api/v1/agents/A1ModelRepositoryAgent`)

## Testing

[Testing](./TESTING.md)

## Docker

[Docker](./DOCKER.md)

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

---

© 2025 Embabel Software, Inc.

