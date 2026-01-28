@echo off
echo ========================================
echo TESTE RAPIDO - SIOSeG
echo ========================================

echo.
echo Parando Java...
taskkill /f /im java.exe 2>nul

echo.
echo Iniciando aplicacao...
echo.
echo CREDENCIAIS:
echo Email: teste@teste.com
echo Senha: 123
echo.
echo URL: http://localhost:8080
echo.

start /b mvn spring-boot:run

timeout /t 8 /nobreak >nul
start http://localhost:8080

echo Aplicacao rodando... Teste os redirecionamentos!
pause