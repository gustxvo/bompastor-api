<div align="center">
  <img src="./assets/operarios.png" alt="Banner Operários"/>
</div>

# Operários API

Repositório do serviço de gerenciamento de escalas do
projeto [Operários](https://github.com/gustavobraga1001/operarios-react).

## Implementações Técnicas

- Autenticação e autorização com JWT + Refresh Tokens
- Arquitetura em Camadas
- Migrations de Banco de Dados
- Docker para conteinerização da aplicação
- Configuração de perfis para execução local e em ambiente de produção

## Tecnologias e Ferramentas

A API foi desenvolvida utilizando as seguintes tecnologias:

- Linguagem de Programação: Java
- Framework: Spring Boot
- Banco de Dados: PostgreSQL
- Ferramentas: Git, Docker, Flyway

## Instalação

Antes de começar, certifique-se de ter o java 21 (ou mais recente) instalado em sua máquina.

```shell
./gradlew bootRun
```

## Configuração do Banco de Dados

O projeto utiliza por padrão um banco de dados em memória (H2) para execução local, pré-populado com uma massa de dados
inicial. O console h2
está disponível em http://localhost:8080/h2-console.

### PostgresSQL

Há suporte para PostgreSQL, o qual pode ser iniciado localmente via docker. Toda a configuração de gerenciamento do
Docker é feita pelo Spring Boot.

Para iniciar a aplicação com o banco de dados PostgreSQL, é necessário alterar o profile do Spring Boot para "dev".

```shell
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## 🌐Operários App

O app Operários pode ser acessado através deste [link](https://operarios-react.vercel.app/).

![Screenshots](./assets/prototypes.png)

## 🫴Autores

- [Gustavo Braga](https://www.github.com/gustavobraga1001/)
- [Matheus Torres](https://www.instagram.com/slk.torress/)
- [Gustavo Almeida Carvalho](https://www.github.com/gustxvo/)
