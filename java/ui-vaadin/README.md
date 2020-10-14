# NOVA-BORDER.EU statisztika

## Running the Project in Development Mode

IDE: Use appropriate launch file (gradle) 

Command line:
```
./gradlew clean bootRun
```

Wait for the application to start

Open http://localhost:9090/ to view the application.

## Running the Project in Production Mode

Build and run from command line:

```
./gradlew -Pvaadin.productionMode
cd build/libs/
java -jar nova-border-eu-stat*.jar
```
