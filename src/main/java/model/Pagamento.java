package model;

import java.time.LocalDateTime;
import java.util.List;

public class Pagamento {

    List<Produto> produtos;
    private LocalDateTime dataCompra;
    private Cliente cliente;

    public Pagamento(List<Produto> produtos, LocalDateTime dataCompra, Assinatura assinatura) {

        if (assinatura.isPagamentoAtrasado()) {
            System.out.println("Compra não autorizada. Assinatura em atraso.");
            return;
        }

        this.produtos = produtos;
        this.dataCompra = dataCompra;
        this.cliente = assinatura.getCliente();
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "produtos=" + produtos +
                ", dataCompra=" + dataCompra +
                ", cliente=" + cliente +
                '}';
    }

    public double calcularSomaDoValorDosProdutos() {
        return getProdutos().stream()
                .mapToDouble(p -> p.getPreco().doubleValue())
                .sum();
    }

}
