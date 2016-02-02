# jSync
Sync large amount of files privately

## Build
gradle fatServerJar

gradle fatClientJar

## Usage
### Start the server on a public available machine

'java -jar jSync-Server PORT'

### Start the reciever client

'java -jar jSync-Client r PATH-TO-SYNC IP/HOST PORT'

### Start the sender client

'java -jar jSync-Client s PATH-TO-SYNC IP/HOST PORT'
