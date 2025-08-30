# Building

## Server

To build the entire server:
`mvn clean install`

To run the server locally
`java -jar embabel-database-server/target/embabel-database-server-{version}.jar`

## UI

To build the ui;
1. from the root directory, run `npm i` to install all the packages
2. to run as a dev server `npm run dev`
3. to build and include in the `embabel-database-server` run `npm run build`

The output of the build files will be automatically placed in `embabel-database-server/src/main/resources/static`