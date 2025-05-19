# 📚 Desafio Livros API + Frontend Vue

Projeto fullstack com **Spring Boot + Vue 3** integrados via **Axios**, totalmente dockerizado.  
Permite gerenciar um catálogo de livros com autores e gêneros.

---

## 🚀 Tecnologias utilizadas

- ✅ Backend: Java 17 + Spring Boot 3.4.5
- ✅ Frontend: Vue 3 + Vite
- ✅ Banco de dados: PostgreSQL 15
- ✅ Containerização: Docker + Docker Compose
- ✅ Gerenciador de requisições: Axios
- ✅ Estilo: HTML básico (pronto para melhorar com Tailwind ou Bootstrap)

---

## 🧱 Funcionalidades

- ✅ Listagem de livros com autor e gênero
- ✅ Cadastro de livros (via formulário no Vue)
- ✅ Cadastro de autor e gênero (via Postman)
- ✅ Relacionamentos: N livros ↔ 1 autor e 1 gênero
- ✅ Conexão full frontend ↔ backend
- ✅ Segurança desativada e CORS habilitado para desenvolvimento

---

## 🐳 Como rodar com Docker

### 1. Clone o repositório

```bash
git clone https://github.com/wmorato/desafio-livros-api.git
cd desafio-livros-api

2. Rode tudo com Docker Compose

docker-compose up --build

🌐 Endpoints
Backend API (porta 8080)
GET /livros

POST /livros

GET /autores

POST /autores

GET /generos

POST /generos

Frontend SPA (porta 80)
Acesse: http://localhost

📘 Exemplo de criação de livro

POST /livros

{
  "titulo": "Fundação",
  "autorId": 1,
  "generoId": 1
}


