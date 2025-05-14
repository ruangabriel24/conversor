import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConversorMoedas {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD"; // Exemplo de API

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBem-vindo ao Conversor de Moedas!");
            System.out.println("Escolha uma opção de conversão:");
            System.out.println("1. USD para BRL");
            System.out.println("2. BRL para USD");
            System.out.println("3. EUR para BRL");
            System.out.println("4. BRL para EUR");
            System.out.println("5. JPY para BRL");
            System.out.println("6. BRL para JPY");
            System.out.println("0. Sair");
            System.out.print("Digite o número da opção desejada: ");

            String escolha = scanner.nextLine();

            if (escolha.equals("0")) {
                System.out.println("Saindo do conversor. Até a próxima!");
                break;
            }

            switch (escolha) {
                case "1":
                    converterMoeda("USD", "BRL", scanner);
                    break;
                case "2":
                    converterMoeda("BRL", "USD", scanner);
                    break;
                case "3":
                    converterMoeda("EUR", "BRL", scanner);
                    break;
                case "4":
                    converterMoeda("BRL", "EUR", scanner);
                    break;
                case "5":
                    converterMoeda("JPY", "BRL", scanner);
                    break;
                case "6":
                    converterMoeda("BRL", "JPY", scanner);
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }
        scanner.close();
    }

    private static void converterMoeda(String moedaOrigem, String moedaDestino, Scanner scanner) throws IOException, InterruptedException {
        System.out.print("Digite o valor em " + moedaOrigem + ": ");
        String valorStr = scanner.nextLine();
        try {
            double valor = Double.parseDouble(valorStr);
            double taxa = obterTaxaDeCambio(moedaOrigem, moedaDestino);
            if (taxa > 0) {
                double resultado = valor * taxa;
                System.out.println(valor + " " + moedaOrigem + " equivalem a " + String.format("%.2f", resultado) + " " + moedaDestino);
            } else {
                System.out.println("Não foi possível obter a taxa de câmbio para " + moedaOrigem + " e " + moedaDestino + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Por favor, digite um número.");
        }
    }

    private static double obterTaxaDeCambio(String moedaOrigem, String moedaDestino) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL)) // A URL base precisa ser adaptada para obter taxas específicas
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            // Aqui precisaremos analisar o JSON para extrair a taxa de câmbio desejada
            // A estrutura do JSON dependerá da API utilizada.
            // Este é um exemplo simplificado e precisará de uma implementação mais robusta
            if (responseBody.contains(moedaDestino)) {
                // Simulação da extração da taxa (isso precisará de uma análise JSON real)
                double taxaExemplo = Math.random() * 5 + 1; // Taxa aleatória para demonstração
                System.out.println("(Taxa de câmbio simulada para " + moedaOrigem + " para " + moedaDestino + ": " + String.format("%.4f", taxaExemplo) + ")");
                return taxaExemplo;
            }
        } else {
            System.err.println("Erro ao acessar a API: " + response.statusCode());
        }
        return 0.0;
    }
}