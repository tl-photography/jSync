# jSync
Sync large amount of files privately

## build
gradle fatServerJar

gradle fatClientJar

## usage
start the server on a public available machine

java -jar jSync-Server <PORT>

start the reciever client

java -jar jSync-Client r <PATH-TO-SYNC> <IP/HOST> <PORT>

start the sender client

java -jar jSync-Client s <PATH-TO-SYNC> <IP/HOST> <PORT>
