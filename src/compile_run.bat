SET JAVA_HOME="C:\Program Files\Java\jdk1.6.0_10"
SET PATH=%PATH%;%JAVA_HOME%\bin
SET CLASSPATH=%JAVA_HOME%\lib\tools.jar
SET INPUT_FILE_PATH=C:\\Users\\shemnani\\Desktop\\BaggageRouter\\Input


SET CLASSPATH=%CLASSPATH%;.;

# Compile all classes
%JAVA_HOME%\bin\java *.java

# Run the baggage routing
%JAVA_HOME%\bin\java BaggageRouting INPUT_FILE_PATH