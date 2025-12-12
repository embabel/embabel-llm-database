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

<img align="left" src="./embabel-database-core/images/315px-Meister_der_Weltenchronik_001.jpg?raw=true" width="180">

&nbsp;&nbsp;&nbsp;&nbsp;

# Embabel Large Language Model Database



## Overview

Embabel Database is REST-compliant database based on a simple repository structure.  Default implementation includes an in-memory database with a JSON local file persistence.

The Database is loaded from the Project [Llm LeaderBoard](https://github.com/JonathanChavezTamales/llm-leaderboard/) Project with a dedicated [Agent](./embabel-database-agent/) to load and maintain on a periodic basis.


## Modules

### Embabel Database Core

[Embabel Database Core](./embabel-database-core/) comprises of the core repository definition including models and initial In-Memory and JPA implementations  

### Embabel Database Agent

[Embabel Database Agent](./embabel-database-agent/) comprises of the repository maintenance and recommendation agent implemented in Java.  This agent is responsible for retrieving and updating the repositories from the [Llm Stats](https://llm-stats.com/) using an API and parsing process.
In addition, the Recommendation Agent supports a "chat" process to help identify models that support a users use case.  

### Embabel Database Batch

[Embabel Database Batch]('./embabel-database-batch) is a Spring Batch process used to refresh the repository.  The Batch Job can be scheduled or triggered manually using the 'maintenance' endpoints.

### Embabel Database Server

[Embabel Database Server](./embabel-database-server/) is a Spring Rest standalone server that supports running the repository and exposing it via REST endpoints.

### Embabel Database UI

[Embabel Database UI](./embabel-database-ui/) is a React-Vite frontend that leverages the BluePrintJS UX library.

## Testing

[Testing](./TESTING.md)

## Docker

[Docker](./DOCKER.md)

## Standalone

[Standalone](./STANDALONE.md)

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Contributors

[![Embabel contributors](https://contrib.rocks/image?repo=embabel/embabel-llm-database)](https://github.com/embabel/embabel-llm-database/graphs/contributors)

---

© 2025 Embabel Software, Inc.

