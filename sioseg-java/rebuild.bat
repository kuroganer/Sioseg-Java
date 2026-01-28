@echo off
REM Rebuild script para sioseg-java
REM Este script recompila e executa a aplicação

cd /d c:\Users\Kyoshiro\Downloads\sioseg-java

REM Configurar variáveis de ambiente
set JAVA_HOME=%USERPROFILE%\jdk
set PATH=%PATH%;%JAVA_HOME%\bin;%USERPROFILE%\tools\maven\bin

echo.
echo ==== Compilando Aplicacao ====
echo.

REM Limpar e compilar
call %USERPROFILE%\tools\maven\bin\mvn.cmd clean package -DskipTests

if %ERRORLEVEL% equ 0 (
    echo.
    echo Build bem-sucedido!
    echo.
    echo Iniciando aplicacao com Maven...
    echo.
    call %USERPROFILE%\tools\maven\bin\mvn.cmd spring-boot:run
) else (
    echo.
    echo Build falhou!
    pause
)
