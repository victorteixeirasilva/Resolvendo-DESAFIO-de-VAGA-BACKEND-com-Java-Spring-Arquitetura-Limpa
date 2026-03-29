# Email Service API — Backend em Java com Arquitetura Limpa e AWS SES

> **E-mails transacionais confiáveis, API REST clara e código preparado para escalar — sem acoplamento ao provedor na camada de negócio.**

---

## Slogan / Chamada curta

**Do desafio técnico à entrega profissional: um microsserviço de envio de e-mail que une Spring Boot, boas práticas de arquitetura e integração real com a nuvem.**

---

## Introdução e visão geral

### O problema

Empresas e produtos digitais precisam enviar e-mails de forma **segura, rastreável e integrada** (confirmações, notificações, recuperação de senha, etc.). Muitas soluções misturam regras de negócio com detalhes de infraestrutura, dificultando testes, manutenção e troca de provedor. Um desafio de backend exige ainda **clareza de design**, **documentação de API** e **código que demonstre maturidade técnica** — exatamente o que recrutadores e clientes avaliam em um portfólio.

### A solução

Este repositório apresenta um **serviço REST em Java** que expõe um endpoint para envio de e-mails, com a lógica organizada em **camadas inspiradas na Arquitetura Limpa (Clean Architecture)**: núcleo de domínio e casos de uso desacoplados da implementação concreta de envio. A entrega real é feita via **Amazon Simple Email Service (SES)**, configurável por variáveis de ambiente e propriedades Spring.

### Benefícios para o negócio e para quem avalia o projeto

| Benefício | Descrição |
|-----------|-----------|
| **Manutenibilidade** | Separação entre regras (core), aplicação, adaptadores e infraestrutura facilita evolução e onboarding. |
| **Testabilidade** | Interfaces (`UseCase`, `Gateway`) permitem mocks e testes focados sem depender da AWS em todo cenário. |
| **Profissionalismo** | Documentação OpenAPI (Swagger UI) e stack moderna (Spring Boot 3, Java 23) comunicam atualização técnica. |
| **Nuvem real** | Integração com SES reflete cenários de produção em ambientes AWS. |
| **Portfólio forte** | O projeto mostra domínio de API REST, injeção de dependências, tratamento de erros e boas práticas de pacotes. |

---

## Funcionalidades principais

1. **API REST para envio de e-mail (`POST /api/email`)**  
   - **Propósito:** receber destinatário, assunto e corpo em JSON e disparar o envio.  
   - **Valor:** contrato simples para integração com front-ends, outros microsserviços ou filas no futuro.

2. **Modelo de requisição com `record` (`EmailRequest`)**  
   - **Propósito:** representar `to`, `subject` e `body` de forma imutável e expressiva (Java moderno).  
   - **Valor:** menos boilerplate, código legível e alinhado às práticas atuais da linguagem.

3. **Caso de uso explícito (`EmailSanderUseCase` + `EmailSanderService`)**  
   - **Propósito:** centralizar a orquestração do envio na camada de aplicação, delegando o “como” ao gateway.  
   - **Valor:** o domínio não conhece SES nem HTTP — facilita trocar provedor ou adicionar políticas (rate limit, auditoria).

4. **Porta de saída / Gateway (`EmailSanderGateway` + `SesEmailService`)**  
   - **Propósito:** isolar a integração com **AWS SES** (construção de `SendEmailRequest`, corpo texto).  
   - **Valor:** infraestrutura plugável; o restante da aplicação permanece estável.

5. **Configuração AWS (`AwsSesConfig` + `application.properties`)**  
   - **Propósito:** instanciar o cliente SES e externalizar região e credenciais (`AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`).  
   - **Valor:** alinhamento com **12-factor** e boas práticas de segurança (sem chaves hardcoded no código).

6. **Tratamento de falhas (`EmailServiceException` + respostas HTTP)**  
   - **Propósito:** capturar erros do provedor e responder com status adequado ao cliente HTTP.  
   - **Valor:** comportamento previsível da API em cenários de indisponibilidade ou rejeição do SES.

7. **Documentação interativa (SpringDoc / OpenAPI)**  
   - **Propósito:** expor descrição da API, tags e operações para testes via Swagger UI.  
   - **Valor:** acelera validação do desafio, demonstrações para empregadores e integração com ferramentas de API.

8. **Testes de contexto Spring (`@SpringBootTest`)**  
   - **Propósito:** garantir que o contexto da aplicação sobe corretamente.  
   - **Valor:** base para expansão com testes de controller e de integração mockando o gateway.

---

## Tecnologias utilizadas

| Categoria | Tecnologia |
|-----------|------------|
| **Linguagem** | Java **23** |
| **Framework** | Spring Boot **3.3.10** (Spring Web) |
| **Build** | Apache Maven + **Maven Wrapper** (`mvnw` / `mvnw.cmd`) |
| **Nuvem / E-mail** | **AWS SES** (`aws-java-sdk-ses` 1.12.472) |
| **Documentação API** | **springdoc-openapi** (OpenAPI 3 + Swagger UI) |
| **Produtividade** | Lombok, Spring Boot DevTools |
| **Testes** | JUnit 5, Spring Boot Test |

### Por que algumas dessas escolhas importam

- **Spring Boot 3.x + Java 23:** stack atual, com suporte a records e ecossistema maduro para APIs REST.  
- **Clean Architecture (camadas):** comunica ao mercado que você pensa em **fronteiras** e **dependências apontando para dentro**.  
- **AWS SES:** serviço gerenciado, custo previsível e amplamente usado em produção para e-mail transacional.  
- **SpringDoc:** padrão de mercado para documentar APIs Spring; melhora a “primeira impressão” do repositório.

---

## Como executar / instalar

### Pré-requisitos

- **JDK 23** instalado e disponível no `PATH` (`java -version`).  
- Conta **AWS** com **SES** habilitado e identidade de e-mail/domínio **verificada** (conforme regras da região).  
- **Credenciais IAM** com permissão para `ses:SendEmail` (ou política equivalente).  
- Opcional: **Maven** instalado globalmente; caso contrário, use o **Maven Wrapper** incluído no módulo `email-service`.

### Variáveis de ambiente

Configure antes de subir a aplicação (PowerShell no Windows):

```powershell
$env:AWS_ACCESS_KEY_ID = "sua_access_key"
$env:AWS_SECRET_ACCESS_KEY = "sua_secret_key"
```

A região padrão está em `email-service/src/main/resources/application.properties` (`aws.region=us-east-1`). Ajuste se sua conta SES estiver em outra região.

### Remetente (origem) no SES

No código da infraestrutura, o remetente está definido para integração com SES. **Antes de enviar em produção ou demo real**, configure o endereço autorizado na AWS SES no serviço que monta o `SendEmailRequest` (constante `{SEU_EMAIL}` em `SesEmailService`), substituindo pelo e-mail ou domínio verificado no console SES.

### Passo a passo

1. **Clone o repositório**

   ```bash
   git clone https://github.com/victorteixeirasilva/Resolvendo-DESAFIO-de-VAGA-BACKEND-com-Java-Spring-Arquitetura-Limpa.git
   cd Resolvendo-DESAFIO-de-VAGA-BACKEND-com-Java-Spring-Arquitetura-Limpa
   ```

2. **Entre no módulo da aplicação**

   ```bash
   cd email-service
   ```

3. **Instale dependências e execute** (Windows, com wrapper):

   ```powershell
   .\mvnw.cmd clean spring-boot:run
   ```

   Linux / macOS:

   ```bash
   ./mvnw clean spring-boot:run
   ```

4. **Acesse a API e a documentação**

   - Base típica: `http://localhost:8080`  
   - **Swagger UI (springdoc):** `http://localhost:8080/swagger-ui/index.html`  
   - **Endpoint de envio:** `POST http://localhost:8080/api/email`  

5. **Exemplo de corpo JSON**

   ```json
   {
     "to": "destinatario@exemplo.com",
     "subject": "Teste via API",
     "body": "Corpo da mensagem em texto simples."
   }
   ```

   Resposta de sucesso (exemplo): corpo de texto `email sent sucessfully` com HTTP 200.

---

## Contribuição (opcional)

Contribuições são bem-vindas para evoluir o portfólio coletivamente.

1. Faça um **fork** do repositório.  
2. Crie uma **branch** para sua feature (`git checkout -b feature/nome-da-melhoria`).  
3. Commit com mensagens claras seguindo boas práticas.  
4. Abra um **Pull Request** descrevendo motivação, mudanças e como validar.

Sugestões de melhorias alinhadas ao projeto: testes de integração com LocalStack ou mock do SES, validação de entrada (Bean Validation), internacionalização de mensagens de erro, configuração do remetente via propriedades e pipeline CI (GitHub Actions).

---

## Licença

Este projeto é disponibilizado sob a **Licença MIT** — uso permissivo para estudo, portfólio e reutilização, com poucas restrições. Para formalizar, recomenda-se adicionar um arquivo `LICENSE` na raiz do repositório com o texto padrão da MIT e o ano/nome do autor.

---

## Agradecimentos (opcional)

- Inspiração no **desafio de código** e nas diretrizes de boas práticas para coding challenges ([Uber — coding challenge tools](https://github.com/uber-archive/coding-challenge-tools/blob/master/coding_challenge.md)).  
- Comunidades e documentação **Spring**, **AWS** e **OpenAPI** pelo ecossistema que viabiliza APIs profissionais em pouco tempo.

---

## Contato

**Victor Teixeira Silva**

- **GitHub:** [github.com/victorteixeirasilva](https://github.com/victorteixeirasilva)  
- **Repositório deste projeto:** [Resolvendo-DESAFIO-de-VAGA-BACKEND-com-Java-Spring-Arquitetura-Limpa](https://github.com/victorteixeirasilva/Resolvendo-DESAFIO-de-VAGA-BACKEND-com-Java-Spring-Arquitetura-Limpa)  
- **LinkedIn:** *[https://www.linkedin.com/in/victor-teixeira-354a131a3/](https://www.linkedin.com/in/victor-teixeira-354a131a3/)*  
- **E-mail:** *[victor.teixeira@inovasoft.tech]*  

---

*Desenvolvido com foco em clareza arquitetural, integração cloud e apresentação sólida para o mercado de backend Java.*

## Coding Challenge Guidelines
https://github.com/uber-archive/coding-challenge-tools/blob/master/coding_challenge.md
