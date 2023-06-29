package model;

import java.math.BigDecimal;

public class AssinaturaAnual implements ITipoAssinatura {
    private Assinatura assinatura;

    public Assinatura getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(Assinatura assinatura) {
        this.assinatura = assinatura;
    }

    @Override
    public BigDecimal calcularTaxa() {
        return BigDecimal.ZERO;
    }
}
