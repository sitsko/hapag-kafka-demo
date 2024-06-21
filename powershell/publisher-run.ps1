$env:JAVA_HOME='c:\Program Files\Java\jdk-21.0.1+12\'
$env:Path = $env:JAVA_HOME+'\bin;'+$env:Path
java -version
cd .\avro-hapag-publisher\
mvn quarkus:dev