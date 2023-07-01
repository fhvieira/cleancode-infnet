package service;

import model.Cliente;
import model.Pagamento;
import model.Produto;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PagamentoService {
    public void ordernarEImprimirPagamentoPorDataDeCompra(final List<Pagamento> pagamentos) {
        System.out.println("2 - Pagamentos ordenados pela data de compra:");

        pagamentos.parallelStream()
                .sorted(Comparator.comparing(Pagamento::getDataCompra))
                .toList()
                .forEach(System.out::println);
    }

    public void CalcularValorDeTodosPagamentos(List<Pagamento> pagamentos) {
        BigDecimal valorTotalPagamentos = pagamentos.stream()
                .flatMap(p -> p.getProdutos().stream())
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("4 - Valor total dos pagamentos: " + valorTotalPagamentos);
    }

    public void imprimirQuantidadeCadaProdutoVendido(List<Pagamento> pagamentos) {
        Map<String, Long> quantidadeProdutosVendidos = pagamentos.stream()
                .flatMap(p -> p.getProdutos().stream())
                .collect(Collectors.groupingBy(Produto::getNome, Collectors.counting()));

        System.out.println("5 - Quantidade de cada produto vendido:");

        quantidadeProdutosVendidos
                .forEach((produto, quantidade) -> System.out.println(produto + ": " + quantidade));
    }

    public void criarMapa(List<Pagamento> pagamentos) {
        Map<Cliente, List<Produto>> mapaClienteProdutos = pagamentos.stream()
                .collect(Collectors.groupingBy(Pagamento::getCliente,
                        Collectors.flatMapping(p -> p.getProdutos().stream(), Collectors.toList())));

        System.out.println("6 - Mapa de Cliente -> List<Produto>:");

        mapaClienteProdutos.forEach((cliente, produtos) ->
                System.out.println(cliente.getNome() + ": " + produtos));
    }

    public void identificarClienteGastouMais(List<Pagamento> pagamentos) {
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

    public void cacularValorFaturadoEmUmMes(List<Pagamento> pagamentos) {
        BigDecimal faturamentoJunho2023 = pagamentos.stream()
                .filter(p -> p.getDataCompra().getMonth() == Month.JUNE && p.getDataCompra().getYear() == 2023)
                .flatMap(p -> p.getProdutos().stream())
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("8 - Faturamento em junho de 2023: " + faturamentoJunho2023);
    }


}
