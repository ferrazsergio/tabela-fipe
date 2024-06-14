package br.com.desafio.tabelafipe.application;

import br.com.desafio.tabelafipe.service.Menu;
import br.com.desafio.tabelafipe.service.ConsumoApi;
import br.com.desafio.tabelafipe.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class TabelaFipeApplication implements CommandLineRunner {
	private final ConsumoApi consumoApi = new ConsumoApi();
	private final ConverteDados converteDados = new ConverteDados();
	private final Scanner scanner = new Scanner(System.in);
	private final Menu menu = new Menu(scanner, consumoApi, converteDados);
	public static void main(String[] args) {
		SpringApplication.run(TabelaFipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	menu.exibeMenu();
	}
}
