# 🧪 Portfólio de Automação de Testes de API - Rest Assured & Java 21

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-3.8+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Rest Assured](https://img.shields.io/badge/Rest_Assured-5.5.0-green?style=for-the-badge)
![JUnit 5](https://img.shields.io/badge/JUnit-5.10.2-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-CI/CD-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)

Este repositório é um projeto de **Automação de Testes de API REST** desenvolvido com **Java 21**, **Rest Assured 5.5.0**, **JUnit 5 (Jupiter)** e **Allure Report**. O objetivo é demonstrar uma arquitetura de testes robusta, legível, sustentável e padronizada.

---

## 🎯 Objetivo do Projeto

Demonstrar boas práticas de testes automatizados de API RESTful cobrindo:
- Padronização de arquitetura segundo o padrão Maven (`src/test/java`).
- Centralização de configurações globais do Rest Assured.
- Validação especializada de respostas complexas.
- Testes cobrindo requisições positivas (Happy Path), negativas (Edge Cases), autenticação (Basic, JWT, Cookie/Session), schemas (JSON Schema e XML XSD) e manipulação de arquivos (Upload/Download).
- **Relatório Visual Interativo & Evidências**: Geração automática de dashboards gráficos com **Allure Report** e publicação contínua via **GitHub Pages**.

---

## 📊 Relatório de Testes (Allure Report)

O projeto gera e publica automaticamente o relatório gráfico e interativo do Allure no GitHub Pages a cada execução da esteira de CI:

🌐 **[Visualizar Allure Report Online (GitHub Pages)](https://luciano-mend.github.io/CRestAssured/)**

---

## 🧪 Estratégia de Testes

A suíte de testes foi estruturada para garantir cobertura ampla e cenários diversificados:

| Categoria | Descrição | Status Code Validados |
|---|---|---|
| **Testes Positivos** | Cadastro, consulta, atualização e exclusão de recursos via JSON e XML | `200 OK`, `201 Created`, `204 No Content` |
| **Testes Negativos** | Validação de campos obrigatórios ausentes, recursos inexistentes e limite de upload | `400 Bad Request`, `404 Not Found`, `413 Payload Too Large` |
| **Autenticação & Segurança** | Testes de acesso protegido com Basic Auth, Preemptive Basic Auth, Token JWT e Cookies de Sessão Web | `200 OK`, `401 Unauthorized` |
| **Validação de Schemas (Contrato)** | Validação de contrato das respostas contra arquivos JSON Schema (`.json`) e XML Schema (`.xsd`) | `200 OK` |
| **Validadores Customizados** | Classe isolada `ValidadorRespostaUsuario` para asserção em lote de 5 ou mais atributos em um único objeto de resposta | `200 OK` |
| **Upload e Download** | Envio multipart de arquivos (`.pdf`, `.zip`) e validação de download de arquivos de imagem (`.jpg`) | `200 OK`, `413 Payload Too Large` |

---

## 📂 Estrutura do Projeto

```
CRestAssured
├── .github
│   └── workflows
│       └── maven-ci.yml                    # Pipeline CI/CD no GitHub Actions (Testes + Allure + GitHub Pages)
├── src
│   └── test
│       ├── java
│       │   └── br/luciano/rest
│       │       ├── core
│       │       │   └── ConfiguracaoBaseTest.java # Classe base para configurações do Rest Assured e filtro do Allure
│       │       ├── model
│       │       │   └── Usuario.java              # POJO / DTO de modelo serializável para JSON/XML
│       │       ├── validations
│       │       │   └── ValidadorRespostaUsuario.java # Validador isolado para respostas com > 5 atributos
│       │       ├── tests
│       │       │   ├── TestesAutenticacao.java       # Suíte de autenticação (Basic Auth, JWT, Cookie)
│       │       │   ├── TestesVerbosHttp.java         # Suíte de métodos HTTP (GET, POST, PUT, DELETE)
│       │       │   ├── TestesUsuarioJson.java        # Suíte de buscas e extração JSONPath
│       │       │   ├── TestesUsuarioXml.java         # Suíte de buscas e extração XMLPath e XPath
│       │       │   ├── TestesUploadDownloadArquivo.java # Suíte de Multipart Upload e Download
│       │       │   ├── TestesSchemaValidation.java   # Suíte de validação de contratos JSON/XML
│       │       │   ├── TestesEnvioParametros.java    # Suíte de envio de Query String, QueryParam e Headers
│       │       │   └── TestesHtml.java               # Suíte de validações de respostas HTML
│       │       └── suite
│       │           └── SuiteDeTestes.java            # Suíte principal de execução unificada (JUnit 5 Suite)
│       └── resources
│           ├── arquivo.pdf                       # Arquivo de teste para Upload
│           ├── arquivoGrande.zip                 # Arquivo grande para teste de limite de upload
│           ├── file.jpg                          # Imagem baixada no teste de Download
│           ├── users.json                        # Contrato JSON Schema
│           └── users.xsd                         # Contrato XML Schema
├── pom.xml
└── README.md
```

---

## 🛠️ Tecnologias e Papéis na Arquitetura

- **JUnit 5 (Jupiter)**: Framework moderno para estruturação, ciclo de vida e asserção das suítes de teste.
- **Rest Assured 5.5.0**: Automação de chamadas HTTP/RESTful.
- **Maven Surefire 3.5.2**: Execução Maven, geração de relatórios XML técnicos e definição de aprovação/reprovação do build.
- **Allure Report 2.29.0 (JUnit 5)**: Relatório gráfico interativo, histórico de tendências e anexos de evidências HTTP.
- **GitHub Actions & GitHub Pages**: CI/CD automatizado e hospedagem pública do Allure Report.
- **Java 21 LTS**: Linguagem base do projeto.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java 21** instalado e configurado nas variáveis de ambiente (`JAVA_HOME`).
- **Apache Maven 3.8+** instalado (`mvn -version`).

### Executando a Suíte Completa de Testes
No terminal, execute:
```bash
mvn clean test
```

### Executando Apenas a Suíte Unificada
```bash
mvn test -Dtest=SuiteDeTestes
```

### Executando uma Classe de Teste Específica
```bash
mvn test -Dtest=TestesAutenticacao
```
```bash
mvn test -Dtest=TestesUsuarioJson
```

---

## ✍️ Autor
Desenvolvido por **Luciano Mend** como projeto demonstrativo de automação de testes de API.
