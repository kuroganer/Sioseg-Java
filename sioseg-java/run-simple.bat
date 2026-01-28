@echo off
REM Rebuild script simples - apenas compila e roda

cd /d c:\Users\Kyoshiro\Downloads\sioseg-java

set JAVA_HOME=%USERPROFILE%\jdk
set PATH=%PATH%;%JAVA_HOME%\bin;%USERPROFILE%\tools\maven\bin

echo.
echo ==== Compilando ====
echo.

call %USERPROFILE%\tools\maven\bin\mvn.cmd clean package -DskipTests 2>&1 | tee build.log

echo.
echo ==== Iniciando aplicacao ====
echo.

call %USERPROFILE%\tools\maven\bin\mvn.cmd spring-boot:run

pause
