@echo off
rem
rem
rem PLease remember to install jdk 1.6 or higher to run the mendelson as2 server
rem
rem

set CLASSPATH="as2.jar";"jetty\start.jar"
call :appendJarsAndZips lib
call :appendJarsAndZips lib\mina
call :appendJarsAndZips lib\vaadin
call :appendJarsAndZips lib\httpclient
call :appendJarsAndZips lib\help
call :appendJarsAndZips jetty\lib

:RunIt
java -Xmx1024M -Xms92M -classpath %CLASSPATH% de.mendelson.comm.as2.AS2

:appendToCP
set CLASSPATH=%CLASSPATH%;%1
goto :EOF

:appendJarsAndZips
for %%D in (%1\*.jar,%1\*.zip) do call :appendToCP "%%D"
goto :EOF

