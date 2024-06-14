package br.com.desafio.tabelafipe.service;

import br.com.desafio.tabelafipe.model.Dados;
import br.com.desafio.tabelafipe.model.Modelos;
import br.com.desafio.tabelafipe.model.Veiculo;
import br.com.desafio.tabelafipe.model.Constantes;

import java.util.List;
import java.util.Scanner;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Menu {
    private final Scanner scanner;
    private final ConsumoApi consumoApi;
    private final ConverteDados conversor;

    public Menu(Scanner scanner, ConsumoApi consumoApi, ConverteDados conversor) {
        this.scanner = scanner;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }

    public void exibeMenu() {
        boolean sair = false;
        while (!sair) {
            exibirOpcoesMenu();
            String opcao = scanner.nextLine().trim().toLowerCase();

            switch (opcao) {
                case "1":
                case "carro":
                case "car":
                    pesquisarVeiculo("carros/marcas");
                    break;
                case "2":
                case "moto":
                case "mot":
                    pesquisarVeiculo("motos/marcas");
                    break;
                case "3":
                case "caminhão":
                case "caminhao":
                    pesquisarVeiculo("caminhoes/marcas");
                    break;
                case "4":
                case "sair":
                    System.out.println("Até a próxima!");
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    private void pesquisarVeiculo(String tipoVeiculo) {
        boolean continuar = true;
        while (continuar) {
            String endpoint = Constantes.URL_BASE + tipoVeiculo;
            String json = consumoApi.chamarApi(endpoint);
            List<Dados> marcas = conversor.obterDadosLista(json, Dados.class);
            exibirMarcas(marcas);

            String codigoMarca = solicitarCodigoMarca();
            String modelosEndpoint = tipoVeiculo + "/" + codigoMarca + "/modelos";
            String modelosJson = consumoApi.chamarApi(Constantes.URL_BASE + modelosEndpoint);
            Modelos modelos = conversor.obterDados(modelosJson, Modelos.class);
            exibirModelos(modelos);

            String nomeVeiculo = solicitarNomeVeiculo();
            List<Dados> modelosFiltrados = filtrarModelos(modelos, nomeVeiculo);
            exibirModelosFiltrados(modelosFiltrados);

            String codigoModelo = solicitarCodigoModelo();
            exibirAvaliacoesPorAno(modelosEndpoint, codigoModelo);

            continuar = continuarPesquisa();
        }
    }

    private void exibirMarcas(List<Dados> marcas) {
        System.out.println("\nMarcas filtradas:");
        marcas.stream()
                .sorted(Comparator.comparing(dados -> Integer.parseInt(dados.codigo())))
                .forEach(dados -> System.out.println("Código: " + dados.codigo() + ", Nome: " + dados.nome()));
    }

    private String solicitarCodigoMarca() {
        System.out.print("\nInforme o código da marca para consulta: ");
        return scanner.nextLine();
    }

    private void exibirModelos(Modelos modelos) {
        System.out.println("\nModelos dessa marca:");
        modelos.modelos().stream()
                .sorted(Comparator.comparing(dados -> Integer.parseInt(dados.codigo())))
                .forEach(dados -> System.out.println("Código: " + dados.codigo() + ", Nome: " + dados.nome()));
    }

    private String solicitarNomeVeiculo() {
        boolean nomeVeiculoValido = false;
        String nomeVeiculo = "";
        while (!nomeVeiculoValido) {
            System.out.print("\nDigite um trecho do nome do veículo a ser buscado: ");
            nomeVeiculo = scanner.nextLine().toLowerCase();
            if (!nomeVeiculo.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("\nEntrada inválida. O nome do veículo deve conter apenas letras e espaços.");
            } else {
                nomeVeiculoValido = true;
            }
        }
        return nomeVeiculo;
    }

    private List<Dados> filtrarModelos(Modelos modelos, String nomeVeiculo) {
        return modelos.modelos().stream()
                .filter(dados -> dados.nome().toLowerCase().contains(nomeVeiculo))
                .collect(Collectors.toList());
    }

    private void exibirModelosFiltrados(List<Dados> modelosFiltrados) {
        System.out.println("\nModelos filtrados:");
        modelosFiltrados.stream()
                .sorted(Comparator.comparing(dados -> Integer.parseInt(dados.codigo())))
                .forEach(dados -> System.out.println("Código: " + dados.codigo() + ", Nome: " + dados.nome()));
    }

    private String solicitarCodigoModelo() {
        System.out.print("\nDigite o código do modelo para buscar os valores de avaliação: ");
        return scanner.nextLine();
    }

    private void exibirAvaliacoesPorAno(String modelosEndpoint, String codigoModelo) {
        String anosEndpoint = modelosEndpoint + "/" + codigoModelo + "/anos";
        String anosJson = consumoApi.chamarApi(Constantes.URL_BASE + anosEndpoint);
        List<Dados> anos = conversor.obterDadosLista(anosJson, Dados.class);
        List<Veiculo> veiculos = anos.stream()
                .map(dados -> {
                    String veiculoJson = consumoApi.chamarApi(Constantes.URL_BASE + anosEndpoint + "/" + dados.codigo());
                    return conversor.obterDados(veiculoJson, Veiculo.class);
                })
                .collect(Collectors.toList());
        System.out.println("\nTodos os veículos filtrados com avaliações por ano:");
        veiculos.forEach(System.out::println);
    }

    private boolean continuarPesquisa() {
        System.out.print("\nDeseja fazer outra busca para esse tipo de veiculo? (sim/não) ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        return resposta.equals("sim") || resposta.equals("s");
    }

    private void exibirOpcoesMenu() {
        System.out.println("\nBem vindo ao menu principal de consulta da tabela fipe, por favor insira a opção desejada:");
        System.out.println("1 - Carro");
        System.out.println("2 - Moto");
        System.out.println("3 - Caminhão");
        System.out.println("4 - Sair");
    }
}
