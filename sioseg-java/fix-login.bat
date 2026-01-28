@echo off
echo Corrigindo problema de login do Sioseg...
echo.

echo 1. Parando aplicacao se estiver rodando...
taskkill /f /im java.exe 2>nul

echo 2. Verificando se MySQL esta rodando...
net start | find "MySQL" >nul
if errorlevel 1 (
    echo MySQL nao esta rodando. Tentando iniciar...
    net start MySQL80 2>nul
    if errorlevel 1 (
        echo Erro: MySQL nao pode ser iniciado.
        echo Verifique se o MySQL esta instalado e configurado.
        pause
        exit /b 1
    )
)

echo 3. Executando aplicacao...
cd /d "c:\Users\Kyoshiro\Downloads\sioseg-java"
java -jar target\sioseg-0.0.1-SNAPSHOT.jar

pause