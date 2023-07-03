package test;

import model.Assinatura;
import model.Cliente;
import model.Pagamento;
import model.Produto;
import service.AssinaturaService;
import service.PagamentoService;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final PagamentoService pagamentoService = new PagamentoService();
        final AssinaturaService assinaturaService = new AssinaturaService();

        Cliente johnDoe = new Cliente("John Doe");
        Cliente janeSmith = new Cliente("Jane Smith");
        Cliente emilyBrown = new Cliente("Emily Brown");
        Cliente sarahWilson = new Cliente("Sarah Wilson");

        Assinatura assinaturaJohn = new Assinatura(BigDecimal.valueOf(99.98),
                LocalDateTime.now().minusMonths(6), johnDoe);

        Assinatura assinaturaJane = new Assinatura(BigDecimal.valueOf(99.98),
                LocalDateTime.now().minusMonths(4), LocalDateTime.now(), janeSmith);

        Assinatura assinaturaEmily = new Assinatura(BigDecimal.valueOf(99.98),
                LocalDateTime.now().minusMonths(3), LocalDateTime.now(), emilyBrown);

        Assinatura assinaturaSaraWilson = new Assinatura(BigDecimal.valueOf(99.98),
                LocalDateTime.now().minusMonths(3), sarahWilson, true);

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

        pagamentoService.calcularValorDeTodosPagamentos(pagamentos);
        pagamentoService.imprimirQuantidadeCadaProdutoVendido(pagamentos);
        pagamentoService.criarMapa(pagamentos);
        pagamentoService.identificarClienteGastouMais(pagamentos);
        pagamentoService.cacularValorFaturadoEmUmMes(pagamentos);
        assinaturaService.imprimirTempoEmMesesDeAssinatuvaAtiva(assinaturaJohn);
        assinaturaService.imprimirTempoDeAssinaturas(assinaturas);
        assinaturaService.calcularValorPagoEmCadaAssinatura(assinaturas);
    }

}
