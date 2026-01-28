@echo off
echo ========================================
echo TESTE FORMULARIOS - SIOSeG
echo ========================================

echo.
echo Parando Java...
taskkill /f /im java.exe 2>nul

echo.
echo Iniciando aplicacao...
start /b mvn spring-boot:run

timeout /t 8 /nobreak >nul

echo.
echo Testando URLs dos formularios:
echo.

echo 1. Clientes: http://localhost:8080/admin/clientes
start http://localhost:8080/admin/clientes

timeout /t 2 /nobreak >nul

echo 2. Produtos: http://localhost:8080/admin/produtos  
start http://localhost:8080/admin/produtos

timeout /t 2 /nobreak >nul

echo 3. Tecnicos: http://localhost:8080/admin/tecnicos
start http://localhost:8080/admin/tecnicos

timeout /t 2 /nobreak >nul

echo 4. Usuarios: http://localhost:8080/admin/usuarios
start http://localhost:8080/admin/usuarios

echo.
echo Clique nos botoes "Novo..." para testar os formularios!
pause