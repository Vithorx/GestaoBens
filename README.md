# Sistema de Gestão de Bens

Este é um projeto de um Sistema de Gestão de Controle de Bens, desenvolvido como parte de um processo de avaliação técnica. O sistema permite o cadastro, pesquisa, edição e exclusão de bens e seus respectivos tipos, além de calcular a depreciação anual dos bens.

## Estrutura do Projeto

O projeto segue uma arquitetura baseada em camadas, comum em aplicações Jakarta EE, para separar as responsabilidades:

-   `src/main/java/br/com/gestaobens/model`: Contém as classes de entidade JPA (`Bem`, `TipoProduto`) que representam as tabelas do banco de dados.
-   `src/main/java/br/com/gestaobens/dao`: Contém as classes DAO (Data Access Object) responsáveis por toda a comunicação com o banco de dados (salvar, buscar, etc.), utilizando o `EntityManager` do JPA.
-   `src/main/java/br/com/gestaobens/controller`: Contém os Managed Beans do JSF (`BemBean`, `TipoProdutoBean`), que servem como controladores, gerenciando o estado da tela e orquestrando as ações do usuário.
-   `src/main/webapp`: Contém as telas da aplicação, desenvolvidas com JSF e PrimeFaces (`.xhtml`), além dos arquivos de configuração web como `web.xml` e `beans.xml`.
-   `src/main/resources`: Contém o arquivo de configuração de persistência do JPA (`persistence.xml`).

## Tecnologias Utilizadas

-   **Linguagem:** Java 17
-   **Servidor de Aplicação:** GlassFish 7
-   **Banco de Dados:** PostgreSQL
-   **Frameworks e Bibliotecas:**
    -   Jakarta EE 10
    -   JSF 4.0 (Jakarta Faces)
    -   PrimeFaces 13 (Componentes de UI)
    -   JPA 3.1 (Jakarta Persistence)
    -   CDI 4.0 (Jakarta Contexts and Dependency Injection)
    -   EJB 4.0 (Jakarta Enterprise Beans)
    -   OmniFaces 4.3
-   **Build:** Apache Maven

## Passos para Configuração e Execução

#### Pré-requisitos
-   JDK 17 ou superior
-   Apache Maven 3.8+
-   Servidor GlassFish 7
-   Banco de Dados PostgreSQL

#### 1. Configuração do Banco de Dados
É necessário ter uma instância do PostgreSQL rodando. As tabelas `tipos_produto` e `bens` serão criadas e gerenciadas pela aplicação ou podem ser criadas manualmente.

#### 2. Configuração do DataSource no GlassFish
No painel de administração do GlassFish (`http://localhost:4848`):
1.  Crie um **JDBC Connection Pool** apontando para o seu banco de dados PostgreSQL.
2.  Crie um **JDBC Resource** com o nome JNDI **`jdbc/gestao_bens_ds`**. Este é o nome que a aplicação utiliza para encontrar o banco de dados, conforme definido no arquivo `persistence.xml`.

#### 3. Compilando o Projeto
Navegue até a pasta raiz do projeto pelo terminal e execute o seguinte comando Maven:
```bash
mvn clean package
```
Isso irá gerar o arquivo `gestaobens.war` (ou similar) dentro da pasta `target/`.

#### 4. Executando a Aplicação
1.  Faça o deploy do arquivo `.war` gerado no seu servidor GlassFish.
2.  Acesse a aplicação no seu navegador, geralmente no endereço `http://localhost:8080/gestaobens/`.

## Explicação do Cálculo de Depreciação

O cálculo da depreciação anual foi implementado utilizando o **método linear**, conforme solicitado.

A fórmula utilizada é:
> Depreciação Anual = (Preço de Compra - Valor Residual) / Vida Útil (em anos)

Esta lógica está implementada no método `getDepreciacaoAnual()` dentro da classe de modelo `src/main/java/br/com/gestaobens/model/Bem.java`.

O método utiliza a classe `java.math.BigDecimal` para todos os cálculos monetários, garantindo a precisão e evitando problemas de arredondamento comuns com tipos primitivos como `double`. O valor da depreciação é então exibido na tabela de bens, formatado como moeda.
