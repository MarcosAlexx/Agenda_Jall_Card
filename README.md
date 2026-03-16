# Agenda Telefonica

Sistema de agenda telefonica desenvolvido como teste tecnico para a JallCard.

## Tecnologias

- Java 21
- Spring Boot 3.3.0
- Spring Security
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- H2 Database
- Docker

## Funcionalidades

- Cadastro e login de usuarios
- CRUD de contatos (criar, listar, editar, deletar)
- Multiplos telefones por contato
- Cada usuario ve apenas seus proprios contatos
- Busca de contatos por nome

## Como Executar

### Com Docker
```bash
docker-compose up --build
```

### Com Maven
```bash
mvn spring-boot:run
```

## Acessar

- Aplicacao: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/agendadb`
  - Usuario: `sa`
  - Senha: (vazio)

## Rodar Testes

```bash
mvn test
```

## Estrutura do Projeto

```
src/main/java/br/com/jallcard/agenda/
├── config/        # Configuracoes (Security)
├── controller/    # Controllers (rotas)
├── dto/           # Objetos de transferencia
├── entity/        # Entidades (tabelas)
├── enums/         # Enumeracoes
├── mapper/        # Conversores DTO <-> Entity
├── repository/    # Acesso ao banco
└── service/       # Regras de negocio
```

## Autor

Marcos - Teste Tecnico JallCard
