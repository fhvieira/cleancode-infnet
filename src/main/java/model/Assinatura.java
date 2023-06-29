package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Assinatura {
    private BigDecimal mensalidade;
    private LocalDate begin;
    private LocalDate end;
    private Cliente cliente;

    public Assinatura(BigDecimal mensalidade, LocalDate begin, LocalDate end, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.begin = begin;
        this.end = end;
        this.cliente = cliente;
    }

    public Assinatura(BigDecimal mensalidade, LocalDate begin, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.begin = begin;
        this.cliente = cliente;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
