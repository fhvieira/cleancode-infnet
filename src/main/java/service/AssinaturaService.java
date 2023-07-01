package service;

import model.Assinatura;

import java.math.BigDecimal;
import java.util.List;

public class AssinaturaService {

    public void imprimirTempoEmMesesDeAssinatuvaAtiva(Assinatura assinaturaAtiva) {
        assinaturaAtiva.getFim().ifPresent(d -> System.out.println("10 - A assinatura não está mais ativa."));

        System.out.println("10 - Tempo em meses da assinatura ainda ativa: " +
                assinaturaAtiva.getQtdeMesesAssinatura());
    }

    public void imprimirTempoDeAssinaturas(List<Assinatura> assinaturas) {
        System.out.println("11 - Tempo de meses entre o início e o fim das assinaturas:");

        assinaturas.forEach(assinatura -> {System.out.println("Assinatura do cliente " +
                assinatura.getCliente().getNome() + ": " +
                assinatura.getQtdeMesesAssinatura() + " meses");
        });
    }

    public void cacularValorPagoEmCadaAssinatura(List<Assinatura> assinaturas) {
        System.out.println("12 - Valor pago em cada assinatura até o momento:");

        assinaturas.forEach(assinatura -> {
            BigDecimal valorPago = assinatura.getMensalidade().multiply(BigDecimal.valueOf(
                    assinatura.getQtdeMesesAssinatura() + 1));

            System.out.println("Assinatura do cliente " + assinatura.getCliente().getNome() + ": " + valorPago);
        });
    }

}
