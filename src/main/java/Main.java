import model.Assinatura;
import model.Cliente;
import model.Pagamento;
import model.Produto;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Cliente johnDoe = new Cliente("John Doe");
        Cliente janeSmith = new Cliente("Jane Smith");
        Cliente emilyBrown = new Cliente("Emily Brown");

        Produto musica = new Produto("Musica", Path.of("musica.mp3"), BigDecimal.valueOf(100));
        Produto video = new Produto("Video", Path.of("video.mp4"), BigDecimal.valueOf(150));
        Produto imagem = new Produto("Imagem", Path.of("imagem.jpg"), BigDecimal.valueOf(120));

        Pagamento pagamentoHoje = new Pagamento(List.of(musica, video), LocalDateTime.now(), johnDoe);
        Pagamento pagamentoOntem = new Pagamento(List.of(imagem), LocalDateTime.now().minusDays(1), janeSmith);
        Pagamento pagamentoMesPassado = new Pagamento(List.of(video, imagem),
                LocalDateTime.now().minusMonths(1), emilyBrown);

        List<Pagamento> pagamentos = List.of(pagamentoOntem, pagamentoHoje, pagamentoMesPassado);

        Assinatura assinaturaJohn = new Assinatura(new BigDecimal("99.98"),
                LocalDate.now().minusMonths(6), johnDoe);

        Assinatura assinaturaJane = new Assinatura(new BigDecimal("99.98"),
                LocalDate.now().minusMonths(4), LocalDate.now(), janeSmith);

        Assinatura assinaturaEmily = new Assinatura(new BigDecimal("99.98"),
                LocalDate.now().minusMonths(3), LocalDate.now(), emilyBrown);

        List<Assinatura> assinaturas = List.of(assinaturaJohn, assinaturaJane, assinaturaEmily);

        ordernarEImprimirPagamentoPorDataDeCompra(pagamentos);
        calcularEImprimirASomaDosPagamentos(pagamentoHoje);
        CalcularValorDeTodosPagamentos(pagamentos);
        imprimirQuantidadeCadaProdutoVendido(pagamentos);
        criarMapa(pagamentos);
        identificarClienteGastouMais(pagamentos);
        cacularValorFaturadoEmUmMes(pagamentos);
        imprimirTempoEmMesesDeAssinatuvaAtiva(assinaturaJohn);
        imprimirTempoDeAssinaturas(assinaturas);
        cacularValorPagoEmCadaAssinatura(assinaturas);
    }

    private static void ordernarEImprimirPagamentoPorDataDeCompra(final List<Pagamento> pagamentos) {
        System.out.println("2 - Pagamentos ordenados pela data de compra:");

        pagamentos.parallelStream()
                .sorted(Comparator.comparing(Pagamento::getDataCompra))
                .toList()
                .forEach(System.out::println);
    }

    private static void calcularEImprimirASomaDosPagamentos(Pagamento pagamento) {
        double somaValoresProdutos = pagamento.getProdutos().stream()
                .mapToDouble(p -> p.getPreco().doubleValue())
                .sum();

        System.out.println("3 - Soma dos valores dos produtos no primeiro pagamento (double): " + somaValoresProdutos);
    }

    private static void CalcularValorDeTodosPagamentos(List<Pagamento> pagamentos) {
        BigDecimal valorTotalPagamentos = pagamentos.stream()
                .flatMap(p -> p.getProdutos().stream())
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("4 - Valor total dos pagamentos: " + valorTotalPagamentos);
    }

    private static void imprimirQuantidadeCadaProdutoVendido(List<Pagamento> pagamentos) {
        Map<String, Long> quantidadeProdutosVendidos = pagamentos.stream()
                .flatMap(p -> p.getProdutos().stream())
                .collect(Collectors.groupingBy(Produto::getNome, Collectors.counting()));

        System.out.println("5 - Quantidade de cada produto vendido:");

        quantidadeProdutosVendidos
                .forEach((produto, quantidade) -> System.out.println(produto + ": " + quantidade));
    }

    private static void criarMapa(List<Pagamento> pagamentos) {
        Map<Cliente, List<Produto>> mapaClienteProdutos = pagamentos.stream()
                .collect(Collectors.groupingBy(Pagamento::getCliente,
                        Collectors.flatMapping(p -> p.getProdutos().stream(), Collectors.toList())));

        System.out.println("6 - Mapa de Cliente -> List<Produto>:");

        mapaClienteProdutos.forEach((cliente, produtos) ->
                System.out.println(cliente.getNome() + ": " + produtos));
    }

    private static void identificarClienteGastouMais(List<Pagamento> pagamentos) {
        Map<Cliente, BigDecimal> totalGastoPorCliente = pagamentos.stream()
                .collect(Collectors.groupingBy(Pagamento::getCliente,
                        Collectors.flatMapping(p -> p.getProdutos().stream(),
                            Collectors.mapping(Produto::getPreco,
                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)))));

        Optional<Map.Entry<Cliente, BigDecimal>> clienteMaisGastou = totalGastoPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        clienteMaisGastou.ifPresent(cliente -> System.out.println("7 - Cliente que gastou mais: "
                + cliente.getKey().getNome()));
    }

    private static void cacularValorFaturadoEmUmMes(List<Pagamento> pagamentos) {
        BigDecimal faturamentoJunho2023 = pagamentos.stream()
                .filter(p -> p.getDataCompra().getMonth() == Month.JUNE && p.getDataCompra().getYear() == 2023)
                .flatMap(p -> p.getProdutos().stream())
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("8 - Faturamento em junho de 2023: " + faturamentoJunho2023);
    }

    private static void imprimirTempoEmMesesDeAssinatuvaAtiva(Assinatura assinaturaAtiva) {
        if (assinaturaAtiva.getEnd() != null) {
            System.out.println("10 - A assinatura não está mais ativa.");
            return;
        }
        long mesesAtivos = assinaturaAtiva.getBegin().until(LocalDate.now()).toTotalMonths();
        System.out.println("10 - Tempo em meses da assinatura ainda ativa: " + mesesAtivos);
    }

    private static void imprimirTempoDeAssinaturas(List<Assinatura> assinaturas) {
        System.out.println("11 - Tempo de meses entre o início e o fim das assinaturas:");

        assinaturas.forEach(assinatura -> {
            if (assinatura.getEnd() != null) {
                long meses = assinatura.getBegin().until(assinatura.getEnd()).toTotalMonths();
                System.out.println("Assinatura do cliente " + assinatura.getCliente().getNome()
                        + ": " + meses + " meses");
            }
        });
    }

    private static void cacularValorPagoEmCadaAssinatura(List<Assinatura> assinaturas) {
        System.out.println("12 - Valor pago em cada assinatura até o momento:");

        assinaturas.forEach(assinatura -> {
            BigDecimal valorPago = assinatura.getMensalidade()
                    .multiply(BigDecimal.valueOf(assinatura.getBegin().until(LocalDate.now()).toTotalMonths() + 1));

            System.out.println("Assinatura do cliente " + assinatura.getCliente().getNome() + ": " + valorPago);
        });
    }


}
