# Testing

Projects comprise of Unit Tests `*Test.java` and Integation Tests `*ITest.java`.  Integration tests are excluded by default.

To run integration tests `mvn clean test -Dtest=*ITest`


## Local Running

1. install ollama `curl -fsSL https://ollama.com/install.sh | sh`
2. start ollama `ollama serve > /dev/null 2>&1 &`
3. pull model `ollama pull llama3.1:8b`
4. build `mvn clean install`
5. run `java -jar embabel-database-server/target/embabel-database-server-${VERISON}.jar`