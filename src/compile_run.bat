echo off
SET JAVA_HOME="D:\Tools\Java\jdk1.7.0_07"
SET INPUT_FILE_PATH=C:\\Users\\shemnani\\Desktop\\BaggageRouter\\Input


SET PATH=%PATH%;%JAVA_HOME%\bin
SET CLASSPATH=%JAVA_HOME%\lib\tools.jar
SET CLASSPATH=%CLASSPATH%;.;

%JAVA_HOME%\bin\javac com\sanjeev\valueobject\BagVO.java
%JAVA_HOME%\bin\javac com\sanjeev\valueobject\FlightVO.java
%JAVA_HOME%\bin\javac com\sanjeev\valueobject\NodeVO.java
%JAVA_HOME%\bin\javac com\sanjeev\scanner\transferobject\BaggageRouterTransferObject.java
%JAVA_HOME%\bin\javac com\sanjeev\routing\dataobject\RoutingDO.java
%JAVA_HOME%\bin\javac com\sanjeev\routing\BaggageRouterEngine.java
%JAVA_HOME%\bin\javac com\sanjeev\scanner\ScanInput.java
%JAVA_HOME%\bin\javac BaggageRouting.java


%JAVA_HOME%\bin\java BaggageRouting %INPUT_FILE_PATH%