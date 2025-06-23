# 🌍 Sistema de Agência de Viagens 

Este projeto implementa um sistema completo para gerenciamento de uma agência de viagens, com interface gráfica desenvolvida em **Java Swing** e integração com banco de dados **MySQL**

---

## 🎯 Funcionalidades

✔️ Cadastro, listagem, busca e exclusão de:
- Clientes (nacionais e estrangeiros)
- Pacotes de viagem
- Serviços adicionais

✔️ Relacionamentos:
- Cliente → múltiplos pacotes
- Pacote → múltiplos serviços adicionais

✔️ Consultas:
- Pacotes contratados por cliente
- Clientes que contrataram determinado pacote

✔️ Validações:
- CPF e passaporte válidos
- E-mail com formato correto
- Campos obrigatórios
- Regra de negócio: não excluir pacote com vínculo

✔️ Interface:
- Menu principal com acesso a todas as telas
- Layout limpo, intuitivo e funcional

---

## 🛠 Tecnologias Utilizadas

- Java 11+
- Java Swing (interface gráfica)
- MySQL (banco de dados)
- JDBC (conexão com o banco)
- IDE utilizada: IntelliJ

---

## 💡 Como Executar

### 1. Configurar o Banco

- Crie o banco `agencia_viagens`
- Execute o script `banco.sql` na pasta `/sql/`

### 2. Ajustar credenciais

Edite a classe `DB.java` com suas informações:

```java
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
