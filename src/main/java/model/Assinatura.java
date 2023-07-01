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
    private LocalDateTime fim;
    private Cliente cliente;
    private TipoAssinatura tipoAssinatura;
    private boolean pagamentoAtrasado;

    private Assinatura(Builder builder) {
        this.mensalidade = builder.mensalidade;
        this.inicio = builder.inicio;
        this.fim = builder.fim;
        this.cliente = builder.cliente;
        this.tipoAssinatura = builder.tipoAssinatura;
        this.pagamentoAtrasado = builder.pagamentoAtrasado;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public Optional<LocalDateTime> getFim() {
        return Optional.ofNullable(fim);
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

    public static class Builder {
        private BigDecimal mensalidade;
        private LocalDateTime inicio;
        private LocalDateTime fim;
        private Cliente cliente;
        private TipoAssinatura tipoAssinatura;
        private boolean pagamentoAtrasado;

        public Builder mensalidade(BigDecimal mensalidade) {
            this.mensalidade = mensalidade;
            return this;
        }

        public Builder inicio(LocalDateTime inicio) {
            this.inicio = inicio;
            return this;
        }

        public Builder fim(LocalDateTime fim) {
            this.fim = fim;
            return this;
        }

        public Builder cliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder tipoAssinatura(TipoAssinatura tipoAssinatura) {
            this.tipoAssinatura = tipoAssinatura;
            return this;
        }

        public Builder pagamentoAtrasado(boolean pagamentoAtrasado) {
            this.pagamentoAtrasado = pagamentoAtrasado;
            return this;
        }

        public Assinatura build() {
            return new Assinatura(this);
        }
    }

}
