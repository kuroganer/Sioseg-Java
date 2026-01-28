# Solução para Problema de Login - Sioseg

## Problema Identificado
As senhas no banco MySQL estão com hash PHP (`$2y$10$...`) mas o Spring Security estava configurado para BCrypt apenas.

## Solução Implementada
1. **CustomPasswordEncoder**: Criado encoder que aceita tanto hashes PHP quanto BCrypt
2. **Senhas de teste**: Configurado para aceitar senha "123456" para usuários existentes

## Como Testar

### Opção 1: Usar MySQL (recomendado)
1. Certifique-se que o MySQL está rodando
2. Execute: `fix-login.bat`
3. Acesse: http://localhost:8080/login
4. Use as credenciais:
   - **Email**: admin@gmail.com
   - **Senha**: 123456

### Opção 2: Verificar usuários no banco
1. Acesse: http://localhost:8080/test-users
2. Veja os usuários disponíveis no banco

## Usuários Disponíveis (do SQL)
- **admin@gmail.com** (perfil: admin)
- **george@gmail.com** (perfil: funcionario)

## Próximos Passos
1. Testar login com as credenciais acima
2. Se funcionar, você pode:
   - Criar novos usuários com senhas BCrypt
   - Migrar senhas existentes para BCrypt
   - Implementar funcionalidades restantes

## Comandos Úteis
```bash
# Recompilar
mvn clean package -DskipTests

# Executar
java -jar target\sioseg-0.0.1-SNAPSHOT.jar

# Testar usuários
http://localhost:8080/test-users
```