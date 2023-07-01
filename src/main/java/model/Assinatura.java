package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static model.TipoAssinatura.*;

public class Assinatura {
    private BigDecimal mensalidade;
    private LocalDateTime inicio;
    private Optional<LocalDateTime> fim;
    private Cliente cliente;
    private TipoAssinatura tipoAssinatura;
    private boolean pagamentoAtrasado;

    public Assinatura(BigDecimal mensalidade, LocalDateTime inicio, LocalDateTime fim, Cliente cliente) {
        this(mensalidade, inicio, cliente);
        this.fim = Optional.of(fim);
    }

    public Assinatura(BigDecimal mensalidade, LocalDateTime inicio, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.inicio = inicio;
        this.fim = Optional.empty();
        this.cliente = cliente;
    }

    public Assinatura(BigDecimal mensalidade, LocalDateTime inicio, Cliente cliente, boolean pagamentoAtrasado) {
        this(mensalidade, inicio, cliente);
        this.pagamentoAtrasado = pagamentoAtrasado;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public Optional<LocalDateTime> getFim() {
        return fim;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public TipoAssinatura getTipoAssinatura() {
        return tipoAssinatura;
    }

    public boolean isPagamentoAtrasado() {
        return pagamentoAtrasado;
    }

    public LocalDateTime getFimOrElseNow() {
        return getFim().orElse(LocalDateTime.now());
    }

    public int getQtdeMesesAssinatura() {
        return (int)ChronoUnit.MONTHS.between(inicio, getFimOrElseNow());
    }

    public BigDecimal getTaxa() {
        return mensalidade.multiply(BigDecimal.valueOf(this.getQtdeMesesAssinatura()));
    }

    public BigDecimal calcularTaxa() {
        return switch(this.tipoAssinatura) {
            case ANUAL -> BigDecimal.ZERO;
            case SEMESTRAL -> getTaxa().multiply(BigDecimal.valueOf(0.03));
            case TRIMESTRAL -> getTaxa().multiply(BigDecimal.valueOf(0.05));
        };
    }

}
