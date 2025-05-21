
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


📚 Projeto Livraria - Execução de Testes
Este projeto segue as melhores práticas de separação de ambientes e testes com Spring Boot, permitindo fácil execução e manutenção em desenvolvimento, produção e testes automatizados.

🚀 Como Executar os Testes Automatizados
Pré-requisitos
Java 17+ instalado

Maven 3.8+ instalado

(Opcional) Docker, se usar banco via container

1. Estrutura de Arquivos de Ambiente
O projeto possui arquivos separados para cada ambiente:

application.yml (configuração base)

application-dev.yml (desenvolvimento)

application-prod.yml (produção)

application-test.yml (testes automatizados)

2. Rodando os Testes de Unidade
Os testes usam por padrão o profile test, que configura o banco H2 em memória para execuções isoladas e rápidas.

Com Maven Wrapper (recomendado)
bash
Copiar
Editar
./mvnw test -Dspring.profiles.active=test
Ou com Maven global
bash
Copiar
Editar
mvn test -Dspring.profiles.active=test
3. (Opcional) Rodando um teste específico
bash
Copiar
Editar
./mvnw -Dtest=LivroServiceTest test -Dspring.profiles.active=test
4. Configuração do Banco de Dados para Testes
O profile test usa banco H2 em memória, configurado em application-test.yml:

yaml
Copiar
Editar
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect
5. Resultados dos Testes
Após rodar os testes, os resultados podem ser visualizados em:

bash
Copiar
Editar
target/surefire-reports/
Ou diretamente no terminal, onde serão exibidos os testes executados, aprovados e falhas (se houver).

6. Dicas rápidas
Para rodar o backend em um ambiente específico, use:

bash
Copiar
Editar
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
Para produção, use o profile prod (definido via variável de ambiente no deploy).

7. Dúvidas ou problemas?
Consulte os arquivos de configuração em src/main/resources/

Verifique os logs para mensagens de erro detalhadas

Se precisar de ajuda, abra uma issue ou entre em contato com o mantenedor do projeto

🏆 Boas práticas
Mantenha sempre os testes atualizados

Execute os testes localmente antes de subir código para o repositório principal

Use o profile de teste para garantir que os dados não afetam ambientes de desenvolvimento ou produção



