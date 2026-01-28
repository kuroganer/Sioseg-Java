@echo off
echo Criando usuario de teste no banco H2...
echo.

curl -X POST "http://localhost:8080/h2-console" ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "language=pt&setting=Generic+H2+%28Embedded%29&name=Generic+H2+%28Embedded%29&driver=org.h2.Driver&url=jdbc%3Ah2%3Amem%3Asioseg&user=sa&password=" > nul 2>&1

echo Inserindo usuario admin diretamente no banco...
curl -X POST "http://localhost:8080/h2-console/query.do" ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "sql=INSERT INTO usuario (nome_usu, cpf_usu, email_usu, senha_hash_usu, perfil, status, data_cadastro_usu) VALUES ('Admin Teste', '12345678901', 'admin@teste.com', '$2a$10$N.zmdr9k7uH/LVKg2N.Ue.J1Neoql3zrYvdNpzAHBOgCS0VMEhEIe', 'admin', 'ativo', NOW())" > nul 2>&1

echo.
echo Usuario criado com sucesso!
echo Email: admin@teste.com
echo Senha: 123456
echo.
echo Agora voce pode fazer login em: http://localhost:8080/login