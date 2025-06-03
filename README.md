# 📚 Desafio Livros API

Sistema completo de gerenciamento de livros, autores e gêneros com autenticação JWT, interface web moderna (Vue 3) e backend Spring Boot. Integração via Docker para setup rápido e eficiente.

---

## ✨ Demonstração Visual

![Tela de Login](./screenshots/login.png)
![Gerenciamento de Livros](./screenshots/livros-crud.png)

---

## 📂 Estrutura do Projeto

```
desafio-livros-api/
├── backend/         # API Java Spring Boot + JWT + Swagger + PostgreSQL
├── frontend/        # SPA Vue 3 + Pinia + Tailwind + Jest + Playwright
├── docker/          # Arquivos de inicialização Docker (banco, configs)
├── docker-compose.yaml
```

---

## 🚀 Como rodar o projeto (via Docker)

### Requisitos:

* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)

```sh
git clone https://github.com/wmorato/desafio-livros-api.git
cd desafio-livros-api
docker compose up --build
```

* Frontend via Docker: [http://localhost/](http://localhost/)
* Frontend localmente: [http://localhost:5173](http://localhost:5173)
* API/Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🧑‍💻 Usuários de Teste

| Usuário | Senha  | Perfil      |
| ------- | ------ | ----------- |
| admin   | 123456 | ROLE\_ADMIN |
| user    | 123456 | ROLE\_USER  |

---

## 📖 Funcionalidades Principais

* Autenticação JWT (login, expiração, proteção de rotas)
* CRUD completo: livros, autores e gêneros
* Filtros por autor/gênero + busca dinâmica
* Layout responsivo (dark/light), usabilidade moderna
* Documentação completa com Swagger
* Testes automatizados: backend (JUnit), frontend (Jest e Playwright)
* Geração automática de relatório com prints
* Docker Compose para integração total
* Estrutura pronta para CI/CD

---

## 🛠️ Tecnologias Utilizadas

### Backend

* Java 17, Spring Boot 3.4
* Spring Data JPA, Spring Security (JWT), Validation
* Swagger (springdoc-openapi), Actuator
* PostgreSQL
* JUnit, Mockito

### Frontend

* Vue 3 + Vite
* Pinia (state management)
* Axios (API)
* Tailwind CSS + CSS customizado
* Jest (@vue/test-utils)
* Playwright (teste funcional e visual)

---

## 🏗️ Comandos Úteis

### Backend

```bash
cd backend
./mvnw test                # Executa testes JUnit
./mvnw spring-boot:run     # Sobe API local (porta 8080)
```

### Frontend

```bash
cd frontend
npm install                # Instala dependências
npm run dev                # Inicia frontend local (porta 5173)
npm run build              # Build de produção
npm test                   # Executa testes unitários (Jest)
```

### Docker

```bash
docker compose up --build   # Sobe frontend, backend e banco
docker compose down         # Para os containers
```

### Testes Funcionais (Playwright)

#### Geração de relatório automatizado com screenshots

```bash
node frontend/tests/e2e/generate-report.js
```

#### Executar testes com navegador visível (modo headed)

```bash
npx playwright test frontend/tests/e2e --headed
```

#### Executar teste pontual (exemplo: home.spec.ts)

```bash
npx playwright test frontend/tests/e2e/home.spec.ts --headed
```

#### Outros testes:

```bash
npx playwright test frontend/tests/e2e/livro-list.spec.ts --headed  # Pesquisar livro
npx playwright test frontend/tests/e2e/home.spec.ts --headed        # Navegar no menu
```

#### Executar todos os testes no modo headless (sem navegador)

```bash
npx playwright test frontend/tests/e2e
```

---

## 🌐 Documentação da API

Acesse via Swagger UI:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🚪 Executando os Testes

### Backend (JUnit)

```bash
cd backend
./mvnw test
```

### Frontend (Jest)

```bash
cd frontend
npm test
```

---

## 📁 Estrutura de Código

### Backend

```
src/main/java/com/exemplo/backend/
├── controller/
├── service/
├── entity/
├── dto/
├── repository/
src/test/java/com/exemplo/backend/
pom.xml, Dockerfile
```

### Frontend

```
src/assets/css/      # Estilos customizados + Tailwind
src/views/           # Páginas (CRUD, login)
src/components/      # Componentes reutilizáveis
src/store/           # Pinia store
frontend/tests/e2e/  # Testes Playwright funcionais e visuais
Dockerfile, jest.config.mjs, .babelrc, package.json
```

---

## 🏅 Diferenciais

* JWT corporativo pronto para produção
* Layout responsivo de alto padrão
* Testes automatizados no front e back (inclui testes funcionais visuais)
* Integração simplificada via Docker
* Código limpo, organizado e pronto para escalar

---

## ❓ FAQ & Dicas

<details>
  <summary><b>Clique para expandir</b></summary>

* Verifique as portas se o frontend não conectar ao backend
* O banco é criado automaticamente na primeira execução
* Usuários admin e user já estão cadastrados

</details>

---

## 📆 Populando o Banco Manualmente (opcional)

```sql
INSERT INTO autor (nome) VALUES ('Machado de Assis');
INSERT INTO genero (nome) VALUES ('Romance');
INSERT INTO livro (titulo, autor_id, genero_id) VALUES ('Dom Casmurro', 1, 1);
```

> Certifique-se de que os IDs 1 existem. Rode `SELECT * FROM autor;` e `SELECT * FROM genero;` para validar.

---

---

## 🚦 Integração Contínua (CI) – GitHub Actions

O projeto utiliza **GitHub Actions** para rodar testes automatizados a cada push/pull request na branch `main`, garantindo qualidade contínua em todas as entregas.

### **Workflows Automatizados**

- **Testes Backend (JUnit):** Executa os testes unitários do backend (Java/Spring) automaticamente.
- **Testes Frontend (Jest):** Executa os testes unitários dos componentes Vue.
- **Testes Funcionais/E2E (Playwright):** Roda testes automatizados ponta a ponta no frontend, garantindo a integração entre frontend e backend.

#### **Arquivo do workflow**
`.github/workflows/ci.yml`

#### **Como funciona**

A cada push ou pull request na branch `main`:

1. **Backend**  
   - Instala o JDK 17  
   - Executa `./mvnw test` (JUnit)

2. **Frontend**  
   - Instala o Node.js 20  
   - Executa `npm ci` para instalar dependências  
   - Executa `npm test` (Jest)

3. **Integração/E2E**  
   - Instala dependências do frontend  
   - Verifica se a API backend está disponível (`/actuator/health`)  
   - Executa `npx playwright test` para rodar testes E2E (Playwright)

Os resultados podem ser visualizados em [Actions do GitHub](https://github.com/wmorato/desafio-livros-api/actions).

---

## 📋 Exemplo de saída do workflow

- ✅ Testes Backend passaram
- ✅ Testes Frontend passaram
- ✅ Testes Funcionais passaram (ou skip caso a API não esteja disponível)

---


## 💼 Sobre o Desafio

Projeto desenvolvido para avaliação técnica.

Repositório: [https://github.com/wmorato/desafio-livros-api](https://github.com/wmorato/desafio-livros-api)

Contato: **Wilson Morato**
