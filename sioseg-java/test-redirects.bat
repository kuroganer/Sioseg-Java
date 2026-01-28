@echo off
echo ========================================
echo Testando Redirecionamentos SIOSeG
echo ========================================

echo.
echo Testando URLs principais...
echo.

echo 1. Testando Dashboard...
curl -s -o nul -w "Dashboard: %%{http_code}\n" http://localhost:8080/dashboard

echo 2. Testando Lista de OS...
curl -s -o nul -w "Lista OS: %%{http_code}\n" http://localhost:8080/admin/os

echo 3. Testando Nova OS...
curl -s -o nul -w "Nova OS: %%{http_code}\n" http://localhost:8080/admin/os/register

echo 4. Testando Clientes...
curl -s -o nul -w "Clientes: %%{http_code}\n" http://localhost:8080/admin/clientes

echo 5. Testando Produtos...
curl -s -o nul -w "Produtos: %%{http_code}\n" http://localhost:8080/admin/produtos

echo 6. Testando Relatórios...
curl -s -o nul -w "Relatórios: %%{http_code}\n" http://localhost:8080/admin/relatorios

echo.
echo Códigos de resposta:
echo 200 = OK (página carregou)
echo 302 = Redirecionamento
echo 404 = Página não encontrada
echo 500 = Erro interno
echo.

pause