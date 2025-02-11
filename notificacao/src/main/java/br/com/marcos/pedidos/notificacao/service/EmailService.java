package br.com.marcos.pedidos.notificacao.service;


import br.com.marcos.pedidos.notificacao.entity.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(Pedido pedido) {
        if (pedido.getEmailNotificacao() == null || pedido.getEmailNotificacao().isEmpty()) {
            throw new IllegalArgumentException("Endereço de e-mail do destinatário não pode ser nulo ou vazio");
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("pedidos-api@companny.com");
        simpleMailMessage.setTo(pedido.getEmailNotificacao());
        simpleMailMessage.setSubject("Pedido de Compra");
        simpleMailMessage.setText(this.gerarMensagem(pedido));

        mailSender.send(simpleMailMessage);
    }


    private String gerarMensagem(Pedido pedido) {
        String pedidoId = pedido.getId().toString();
        String cliente = pedido.getCliente();
        String valor = String.valueOf(pedido.getValorTotal());
        String status = pedido.getStatus().name();
        return String.format("Olá %s seu pedido de nº %s no valor de %s, foi realizado com sucesso.\nStatus: %s.", cliente, pedidoId, valor, status);
    }

}
