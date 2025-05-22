# 📚 Desafio Livros API

Sistema completo de gerenciamento de livros, autores e gêneros com autenticação JWT, interface web moderna (Vue 3) e backend Spring Boot. Integração Docker para fácil setup. Desenvolvido como desafio técnico full stack.

---

## ✨ Demonstração

![Tela de Login](./screenshots/login.png)
![Gerenciamento de Livros](./screenshots/livros-crud.png)

---

## 📂 Estrutura do Projeto

desafio-livros-api/
├── backend/ # API Java Spring Boot + JWT + Swagger + PostgreSQL

├── frontend/ # SPA Vue 3 + Pinia + Tailwind + Jest

├── docker/ # Arquivos de inicialização Docker (banco, configs)

├── docker-compose.yaml



---

## 🚀 Como rodar o projeto (usando Docker)

**Pré-requisitos:**  
- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) instalados

```sh
git clone https://github.com/wmorato/desafio-livros-api.git
cd desafio-livros-api
docker-compose up --build



---

## 🚀 Como rodar o projeto (usando Docker)

**Pré-requisitos:**  
- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) instalados

```sh
git clone https://github.com/wmorato/desafio-livros-api.git
cd desafio-livros-api
docker-compose up --build```


Acesse o frontend: http://localhost:5173

API/Swagger: http://localhost:8080/swagger-ui/index.html

🧑‍💻 Usuários de Teste
Usuário	Senha	Perfil
admin	123456	ROLE_ADMIN
user	123456	ROLE_USER

📖 Principais Funcionalidades
Autenticação JWT: login, proteção de rotas, expiração.

CRUD Completo: livros, autores e gêneros.

Filtros e Pesquisa: filtro por autor/gênero, busca dinâmica.

Interface Responsiva: layout corporativo, dark/light, UX moderna.

Swagger/OpenAPI: documentação e teste dos endpoints.

Testes automatizados: backend (JUnit), frontend (Jest).

Docker Compose: frontend, backend e banco integrados.

CI-ready: fácil extensão para deploy e pipelines.

🛠️ Tecnologias Utilizadas
Backend
Java 17, Spring Boot 3.4

Spring Data JPA, Spring Security (JWT), Validation, Actuator

Swagger (springdoc-openapi)

PostgreSQL

Testes: JUnit, Mockito

Frontend
Vue 3 + Vite

Pinia (state management)

Axios (API)

Tailwind CSS + custom CSS

Jest (@vue/test-utils)

Docker

🏗️ Comandos Úteis
Backend

cd backend
./mvnw test        # Executa todos os testes JUnit
./mvnw spring-boot:run # Sobe API sem Docker (porta 8080)


Frontend

cd frontend
npm install        # Instala dependências
npm run dev        # Sobe SPA localmente (porta 5173)
npm run build      # Gera build de produção
npm test           # Executa testes unitários (Jest)


Docker

docker-compose up --build   # Sobe tudo (frontend, backend, banco)
docker-compose down         # Para todos os containers


🌐 Documentação da API
Acesse o Swagger:
http://localhost:8080/swagger-ui/index.html


🧪 Executando os Testes

Backend (JUnit):

cd backend
./mvnw test


Frontend (Jest):

cd frontend
npm test

cd frontend
npm test

 Organização dos Arquivos
Backend
src/main/java/com/exemplo/backend/...

controller/, service/, entity/, dto/, repository/

src/test/java/com/exemplo/backend/...

Testes separados por camada (controller, service)

pom.xml, Dockerfile

Frontend
src/assets/css/ - Estilos customizados (incluindo Tailwind)

src/views/ - Páginas principais (CRUD, login)

src/components/ - Componentes reutilizáveis

src/store/ - Pinia stores

Dockerfile, jest.config.mjs, .babelrc, package.json

🏅 Diferenciais
Autenticação JWT corporativa pronta para produção

Layout profissional, usabilidade de alto nível

Cobertura de testes nas duas camadas

Setup rápido com Docker (basta um comando)

Código limpo e organizado para fácil manutenção


 <details> <summary><b>FAQ & Dicas</b></summary>
Se o frontend não conectar ao backend, confira as portas e variáveis de ambiente.

O banco de dados é criado automaticamente no primeiro start.

Usuários admin e user já vêm populados.

</details> 


BANCO DE DADOS

Caso precise popular manualmente as tabelas, rode no seu client SQL (ou no psql/DBeaver/pgAdmin):

INSERT INTO autor (nome) VALUES ('Machado de Assis');
INSERT INTO genero (nome) VALUES ('Romance');
INSERT INTO livro (titulo, autor_id, genero_id) VALUES ('Dom Casmurro', 1, 1);


⚠️ Atenção:

Certifique-se de que o ID do autor e do gênero realmente são 1.

Você pode rodar SELECT * FROM autor; ou SELECT * FROM genero; para conferir.

(Troque postgres-db pelo nome do seu container.)


💼 Sobre o desafio
Este projeto foi desenvolvido para o processo seletivo conforme instruções recebidas.

Repositório:
https://github.com/wmorato/desafio-livros-api

📞 Contato
Wilson Morato
