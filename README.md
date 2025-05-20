
# 📚 Desafio Livros API

Sistema completo de cadastro e listagem de livros com autenticação JWT, frontend em Vue 3 com TailwindCSS e backend Spring Boot rodando em containers via Docker Compose.

---

## 🧰 Tecnologias utilizadas

- ⚙️ **Spring Boot 3.4.5**
- 🐳 **Docker + Docker Compose**
- 🐘 **PostgreSQL**
- 🔐 **Spring Security + JWT**
- 🧾 **Swagger (OpenAPI 3)**
- 🖼️ **Vue 3 + Vite**
- 🎨 **TailwindCSS**

---

## 🚀 Como subir o projeto do zero

### 📁 Pré-requisitos

- [Git](https://git-scm.com/)
- [Docker e Docker Compose](https://docs.docker.com/compose/)
- [Node.js 18+ e npm](https://nodejs.org/)

---

### ⏬ 1. Clone o projeto

```bash
git clone https://github.com/seu-usuario/desafio-livros-api.git
cd desafio-livros-api
🐳 2. Suba os serviços com Docker
bash
Copiar
Editar
docker-compose up --build
A API estará disponível em http://localhost:8080
O frontend em http://localhost (porta padrão do nginx)

📘 3. Acesse a documentação Swagger
bash
Copiar
Editar
http://localhost:8080/swagger-ui.html
🔐 Credenciais de acesso
Use este usuário para autenticação:

json
Copiar
Editar
{
  "username": "admin",
  "password": "123456"
}
🧪 Como testar a API
Faça login com o POST /auth/login

Copie o token retornado

Clique no botão Authorize no Swagger e cole:
Bearer eyJhbGciOi...

Teste os endpoints protegidos como /livros, /autores, /generos

🖥️ Frontend
Frontend Vue 3 + Vite + Tailwind está dentro da pasta frontend.

Já está containerizado e funcionando ao subir o docker-compose.

💡 Funcionalidades
 Login com autenticação JWT

 Cadastro de livros com associação a autor e gênero

 Listagem e exclusão de livros

 Validação e feedback visual no frontend

 Integração Axios com token automático

 Atualização da lista em tempo real

 Interface documentada via Swagger

 Projeto dockerizado e pronto para produção/local

👨‍💻 Autor
Wilson Morato (Gudi)
Desenvolvedor Full Stack | Open to Work
linkedin.com/in/wilsonmorato

📦 Extras (opcional)
✅ Sistema preparado para uso com banco real

✅ Fácil deploy em servidores cloud ou render.com

✅ Pode ser extendido com usuários reais e permissões por perfil

