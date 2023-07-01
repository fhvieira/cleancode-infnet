import jdk.jshell.spi.ExecutionControlProvider;
import model.*;
import service.AssinaturaService;
import service.PagamentoService;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        final PagamentoService pagamentoService = new PagamentoService();
        final AssinaturaService assinaturaService = new AssinaturaService();

        Cliente johnDoe = new Cliente("John Doe");
        Cliente janeSmith = new Cliente("Jane Smith");
        Cliente emilyBrown = new Cliente("Emily Brown");
        Cliente sarahWilson = new Cliente("Sarah Wilson");

        Assinatura assinaturaJohn = new Assinatura.Builder()
                .mensalidade(BigDecimal.valueOf(99.98))
                .inicio(LocalDateTime.now().minusMonths(6))
                .cliente(johnDoe)
                .build();

        Assinatura assinaturaJane = new Assinatura.Builder()
                .mensalidade(BigDecimal.valueOf(99.98))
                .inicio(LocalDateTime.now().minusMonths(4))
                .fim(LocalDateTime.now())
                .cliente(janeSmith)
                .build();

        Assinatura assinaturaEmily = new Assinatura.Builder()
                .mensalidade(BigDecimal.valueOf(99.98))
                .inicio(LocalDateTime.now().minusMonths(3))
                .fim(LocalDateTime.now())
                .cliente(emilyBrown)
                .build();

        Assinatura assinaturaSaraWilson = new Assinatura.Builder()
                .mensalidade(BigDecimal.valueOf(99.98))
                .inicio(LocalDateTime.now().minusMonths(3))
                .cliente(sarahWilson)
                .pagamentoAtrasado(true)
                .build();

        List<Assinatura> assinaturas = List.of(assinaturaJohn, assinaturaJane, assinaturaEmily);


        Produto musica = new Produto("Musica", Path.of("musica.mp3"), BigDecimal.valueOf(100));
        Produto video = new Produto("Video", Path.of("video.mp4"), BigDecimal.valueOf(150));
        Produto imagem = new Produto("Imagem", Path.of("imagem.jpg"), BigDecimal.valueOf(120));

        Pagamento pagamentoHoje = new Pagamento(List.of(musica, video), LocalDateTime.now(), assinaturaJohn);
        Pagamento pagamentoOntem = new Pagamento(List.of(imagem), LocalDateTime.now().minusDays(1), assinaturaJane);
        Pagamento pagamentoMesPassado = new Pagamento(List.of(video, imagem),
                LocalDateTime.now().minusMonths(1), assinaturaEmily);

        List<Pagamento> pagamentos = List.of(pagamentoOntem, pagamentoHoje, pagamentoMesPassado);

        new Pagamento(List.of(musica, video), LocalDateTime.now(), assinaturaSaraWilson);

        pagamentoService.ordernarEImprimirPagamentoPorDataDeCompra(pagamentos);

        System.out.println("3 - Soma dos valores dos produtos no primeiro pagamento (double): " +
                pagamentos.get(0).calcularSomaDoValorDosProdutos());

        pagamentoService.CalcularValorDeTodosPagamentos(pagamentos);
        pagamentoService.imprimirQuantidadeCadaProdutoVendido(pagamentos);
        pagamentoService.criarMapa(pagamentos);
        pagamentoService.identificarClienteGastouMais(pagamentos);
        pagamentoService.cacularValorFaturadoEmUmMes(pagamentos);
        assinaturaService.imprimirTempoEmMesesDeAssinatuvaAtiva(assinaturaJohn);
        assinaturaService.imprimirTempoDeAssinaturas(assinaturas);
        assinaturaService.cacularValorPagoEmCadaAssinatura(assinaturas);
    }

}
