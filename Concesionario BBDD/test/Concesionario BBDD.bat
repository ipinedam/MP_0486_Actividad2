@echo off
cls
set _SCRIPT_PATH=%~dp0
chcp 65001 > nul
cd %_SCRIPT_PATH%\..
"%JAVA_HOME%\bin\java.exe" -Dfile.encoding=UTF-8 -classpath ".\bin;.\lib\mysql-connector-java-8.0.22.jar" practica.Principal
cd %_SCRIPT_PATH%
chcp 850 > nul