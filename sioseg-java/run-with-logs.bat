@echo off
REM Script para capturar logs da aplicação

cd /d c:\Users\Kyoshiro\Downloads\sioseg-java

set JAVA_HOME=%USERPROFILE%\jdk
set PATH=%PATH%;%JAVA_HOME%\bin;%USERPROFILE%\tools\maven\bin

echo.
echo ==== Iniciando aplicacao com logs ====
echo.

REM Executar Maven spring-boot:run e redirecionar output para arquivo
call %USERPROFILE%\tools\maven\bin\mvn.cmd spring-boot:run > app.log 2>&1

REM Mostrar o arquivo de log
echo.
echo ==== Conteudo do log ====
echo.
type app.log

pause
