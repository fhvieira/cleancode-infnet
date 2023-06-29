package model;

import java.time.LocalDateTime;
import java.util.List;

public class Pagamento {

    List<Produto> produtos;
    private LocalDateTime dataCompra;
    private Cliente cliente;

    public Pagamento(List<Produto> produtos, LocalDateTime dataCompra, Cliente cliente) {
        this.produtos = produtos;
        this.dataCompra = dataCompra;
        this.cliente = cliente;
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
}
