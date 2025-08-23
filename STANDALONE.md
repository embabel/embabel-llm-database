# Standalone

To run the server from the commandline, execute the following

`java -Daws.region=us-east-1 -jar embabel-database-server-{version}.jar`

## AWS Bedrock Models

### Prerequisites

Use a AWS account which has a minimum of the `AmazonBedrockReadOnly` set and get the;
- AWS Access Key
- AWS Secret

### Run

`java -Dspring.profiles.active=mcp-server,ollama,aws -Daws.region=us-east-1 -Daws.accessKeyID=AWS_KEY -Daws.secretAccessKey=AWS_SECRET -jar embabel-database-server-{version}.jar`


## Testing

### Testing endpoints with cURL

#### /findByTags

`curl -G localhost:8000/api/v1/models/search/findByTags --data-urlencode "tags=text-to-text"`
