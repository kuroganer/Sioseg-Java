# Sioseg Java (Spring Boot)

Scaffold inicial convertido a partir de projeto PHP.

Quick start:

```powershell
cd 'c:\Users\Kyoshiro\Downloads\sioseg-java'
mvn package
java -jar target\sioseg-0.0.1-SNAPSHOT.jar
```

Edit `src/main/resources/application.properties` to set DB credentials.

O que foi convertido nesta etapa:
- Entidades JPA: `Cliente`, `Produto`, `OrdemServico`, `Tecnico`, `Usuario`
- Repositories + Services básicos
- Controllers básicos: `HomeController`, `ClienteController`, `ProdutoController`
- Templates Thymeleaf exemplares: `login`, `dashboard`, `clientes list`, `produtos list`

Próximos passos sugeridos:
- Completar controllers e templates restantes
- Implementar autenticação (Spring Security)
- Mapear relacionamentos JPA (ex.: OS -> Cliente/Tecnico/Usuario)
- Transferir lógica complexa das models PHP para services
