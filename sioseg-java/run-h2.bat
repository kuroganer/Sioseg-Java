@echo off
REM Rebuild script para sioseg-java com H2 (sem MySQL)
REM Este script é para testes/desenvolvimento sem banco de dados externo

cd /d c:\Users\Kyoshiro\Downloads\sioseg-java

set JAVA_HOME=%USERPROFILE%\jdk
set PATH=%PATH%;%JAVA_HOME%\bin;%USERPROFILE%\tools\maven\bin

echo.
echo ==== Compilando Aplicacao (modo H2 - sem MySQL) ====
echo.

REM Limpar e compilar
call %USERPROFILE%\tools\maven\bin\mvn.cmd clean package -DskipTests

if %ERRORLEVEL% equ 0 (
    echo.
    echo Build bem-sucedido!
    echo.
    echo Iniciando aplicacao com Maven (perfil H2)...
    echo.
    call %USERPROFILE%\tools\maven\bin\mvn.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=h2"
) else (
    echo.
    echo Build falhou!
    pause
)
