# Back-End Criar Evento

Sistema back-end para gerenciamento de eventos desenvolvido com Spring Boot, oferecendo APIs REST para criação de eventos, autenticação de usuários e gerenciamento de inscrições.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Security** (autenticação JWT)
- **Spring Data JPA** (persistência de dados)
- **MySQL** (banco de dados)
- **JWT (JSON Web Token)** - autenticação stateless
- **Maven** (gerenciamento de dependências)

## 📋 Funcionalidades

### Autenticação
- Login com JWT (usuário/email + senha)
- Tokens com expiração configurável (8 horas padrão)
- Senhas criptografadas com BCrypt

### Eventos
- Criação de eventos
- Listagem de todos os eventos
- Busca de evento por ID
- Campos: nome, descrição, data, hora, local, categoria e vagas

### Inscrições
- Inscrição de usuários em eventos
- Validação de vagas disponíveis
- Verificação de inscrições duplicadas
- Consulta de status de inscrição

## 🔧 Configuração

### Pré-requisitos
- JDK 17 ou superior
- MySQL 8.0+
- Maven 3.6+

### Banco de Dados

1. Crie o banco de dados MySQL:
```sql
CREATE DATABASE Liferay;
```

2. Configure as credenciais em `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Liferay
spring.datasource.username=root
spring.datasource.password=SUA_SENHA
```

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/Back-End-Criar-Evento.git
cd Back-End-Criar-Evento/eventos
```

2. Compile o projeto:
```bash
./mvnw clean install
```

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## 📡 Endpoints da API

### Autenticação

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "login": "testuser",
  "senha": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tipo": "Bearer",
  "id": 1,
  "nomeUsuario": "testuser",
  "email": "teste@email.com"
}
```

### Eventos

#### Criar Evento
```http
POST /api/eventos/criar
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Workshop de Spring Boot",
  "descricao": "Aprenda Spring Boot na prática",
  "data": "2025-12-15",
  "hora": "14:00:00",
  "local": "Auditório Principal",
  "categoria": "Tecnologia",
  "vagas": 50
}
```

#### Listar Eventos
```http
GET /api/eventos
Authorization: Bearer {token}
```

#### Buscar Evento por ID
```http
GET /api/eventos/{id}
Authorization: Bearer {token}
```

### Inscrições

#### Inscrever-se em Evento
```http
POST /api/eventos/{id}/inscricoes
Authorization: Bearer {token}
Content-Type: application/json

{
  "usuarioId": 1
}
```

**Respostas possíveis:**
- Sucesso: `{"mensagem": "Inscrição realizada com sucesso!", "eventoId": 1}`
- Erro: `{"erro": "JA_INSCRITO"}` ou `{"erro": "VAGAS_ESGOTADAS"}`

#### Verificar Status de Inscrição
```http
GET /api/eventos/{id}/inscricoes/{usuarioId}/status
Authorization: Bearer {token}
```

**Resposta:**
```json
{
  "jaInscrito": false,
  "esgotado": false,
  "prazoExpirado": false
}
```

## 🔐 Segurança

- **CORS**: Configurado para aceitar requisições de:
  - `http://localhost:5500`
  - `http://localhost:3000`
  - `http://127.0.0.1:5500`

- **JWT**: Tokens com validade de 8 horas (28800000 ms)
- **Endpoints públicos**: Apenas `/api/auth/**`
- **Endpoints protegidos**: Todos os demais requerem autenticação

## 👥 Usuários de Teste

A aplicação cria automaticamente dois usuários ao iniciar:

| Usuário | Email | Senha |
|---------|-------|-------|
| testuser | teste@email.com | 123456 |
| jorgeuser | jorge@email.com | 12345 |

## 📁 Estrutura do Projeto

```
eventos/
├── src/main/java/com/eventos/eventos/
│   ├── config/          # Configurações (Security, JWT)
│   ├── controller/      # Controllers REST
│   ├── dto/            # Data Transfer Objects
│   ├── model/          # Entidades JPA
│   ├── repository/     # Repositórios Spring Data
│   └── service/        # Serviços de negócio
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## 🛠️ Desenvolvimento

### Adicionar novos endpoints

1. Crie um novo controller em `controller/`
2. Injete os repositories necessários
3. Configure as permissões em `SecurityConfig.java`

### Modificar configuração JWT

Edite `application.properties`:
```properties
jwt.secret=SUA_CHAVE_SECRETA_AQUI
jwt.expiration=28800000  # 8 horas em milissegundos
```

## 📝 Licença

Este projeto está sob a licença MIT.

## 👨‍💻 Autor

Desenvolvido como projeto de back-end para sistema de gerenciamento de eventos.

---

**Nota**: Lembre-se de alterar as credenciais do banco de dados e a chave secreta JWT antes de fazer deploy em produção!
