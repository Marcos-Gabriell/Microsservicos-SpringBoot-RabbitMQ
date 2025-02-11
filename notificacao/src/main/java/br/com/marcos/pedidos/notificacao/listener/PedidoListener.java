package br.com.marcos.pedidos.notificacao.listener;

import br.com.marcos.pedidos.notificacao.entity.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    private final Logger logger = LoggerFactory.getLogger(PedidoListener.class);


    @RabbitListener(queues = "pedidos.v1.pedido-criado.gerar-notificacao")
    public void eviarNotificacao(Pedido pedido) {

        logger.info("Noticação gerada: {}", pedido.toString());
    }
}
