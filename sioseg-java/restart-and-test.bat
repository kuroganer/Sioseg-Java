@echo off
echo ========================================
echo Reiniciando aplicacao SIOSeG
echo ========================================

echo.
echo 1. Parando processos Java existentes...
taskkill /f /im java.exe 2>nul

echo.
echo 2. Limpando e compilando projeto...
call mvn clean compile

echo.
echo 3. Iniciando aplicacao...
echo.
echo CREDENCIAIS DE TESTE:
echo Email: admin@teste.com
echo Senha: 123456
echo.
echo Acesse: http://localhost:8080
echo.

start /b mvn spring-boot:run

echo.
echo Aplicacao iniciando... Aguarde alguns segundos e acesse http://localhost:8080
echo.
echo Para parar a aplicacao, feche esta janela ou pressione Ctrl+C
echo.

timeout /t 10 /nobreak >nul
start http://localhost:8080

pause