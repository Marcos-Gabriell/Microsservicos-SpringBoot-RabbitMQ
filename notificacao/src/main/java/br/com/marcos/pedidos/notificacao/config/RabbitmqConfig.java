package br.com.marcos.pedidos.notificacao.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exachangName;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Bean
    public FanoutExchange pedidosExchange() {
        return new FanoutExchange(exachangName);
    }

    @Bean
    public Queue notificacoesQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(notificacoesQueue()).to(pedidosExchange());
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public ApplicationListener<ApplicationEvent> applicationEventApplicationListener(RabbitAdmin rabbitAdmin) {
        return event -> {
            rabbitAdmin.declareQueue(notificacoesQueue());
        };
    }
}
