# Consulta Tabela FIPE

Este projeto Java oferece uma aplicação de linha de comando para consultar informações da Tabela FIPE (Fundação Instituto de Pesquisas Econômicas).

## Funcionalidades

- Pesquisa de veículos por tipo (carros, motos, caminhões).
- Consulta de marcas, modelos e valores de veículos através da API da Tabela FIPE.
- Filtragem e visualização de dados específicos conforme a entrada do usuário.

## Pré-requisitos

- Java 11 ou superior.
- Maven para gerenciamento de dependências (opcional, dependendo de como você estruturou seu projeto).

## Uso

1. Ao iniciar a aplicação, você verá um menu com opções para carros, motos, caminhões e sair.
2. Escolha o tipo de veículo digitando o número correspondente ou palavras-chave como "carro", "moto", etc.
3. Siga as instruções para selecionar uma marca, filtrar modelos por nome e escolher um modelo específico para ver os valores de avaliação.

### Exemplo de Uso

Aqui está um exemplo básico de como usar a aplicação:

1. Seleção da opção `1` para carros.
2. Escolha da marca através do código listado.
3. Digite um trecho do nome do veículo para filtrar os modelos.
4. Escolha um modelo específico digitando seu código.
5. Visualize os valores de avaliação dos veículos disponíveis.

## Estrutura do Projeto

A estrutura do projeto está organizada da seguinte forma:

- `src/main/java`: Contém o código-fonte da aplicação Java.
- `br.com.desafio.tabelafipe.application`: Pacote contendo a classe `TabelaFipeApplication`, que executa a aplicação.
- `br.com.desafio.tabelafipe.model`: Pacote contendo as classes de modelo como `Dados`, `Modelos`, `Constantes` e `Veiculo`.
- `br.com.desafio.tabelafipe.service`: Pacote contendo serviços como `ConsumoApi`, `ConverteDados`,`Menu`.
- `src/test/java`: Diretório para testes unitários, se existirem.

## Contribuições

Contribuições são bem-vindas! Para mudanças importantes, abra um problema para discutir o que você gostaria de mudar.
