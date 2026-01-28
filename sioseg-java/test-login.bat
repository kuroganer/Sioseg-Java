@echo off
echo Testando login no sistema...
echo.
echo Tentando fazer login com:
echo Email: admin@teste.com
echo Senha: 123456
echo.

curl -X POST http://localhost:8080/login ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "email=admin@teste.com&senha=123456" ^
  -v

echo.
echo Teste concluido!