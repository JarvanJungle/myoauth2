package com.doxa.oauth2.microservices.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.util.ErrorHandler;

/**
 * @author vuducnoi
 * Rabbitmq configuration
 * Queue name pattern: <env>.queue.<unique_identify>.<service_receiver_id>.<resource_name>.<action>
 * ex: dev.queue.123.requisition.approval_matrix.updated
 * Exchange name pattern: <env>.exchange.<unique_identify><resource_name>
 * ex: dev.exchange.713.approval_matrix
 */
@Configuration
@EnableRabbit
public class AMQPConfig implements RabbitListenerConfigurer {

    @Bean
    public Queue connexParkingLot() {
        return new Queue(Queues.CONNEX_PARKING_LOT, true);
    }
    @Bean
    public FanoutExchange parkingLotExchange() {
        return new FanoutExchange(Queues.CONNEX_PARKING_LOT_DLX);
    }

    @Bean
    public Binding bindParkingLotQueue() {
        return BindingBuilder.bind(connexParkingLot()).to(parkingLotExchange());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper localMapper = new ObjectMapper();
        localMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(localMapper);
    }

    /*
     * Inbound Configuration
     * We are using Annotation-Driven-Message-Listening, described here
     * http://docs.spring.io/spring-amqp/reference/htmlsingle/#async-annotation-driven
     */
    @Autowired
    public ConnectionFactory connectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public MessageHandler eventResultHandler() {
        return new MessageHandler();
    }

    @Override
    public void configureRabbitListeners(
            RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }


    @Bean
    public ErrorHandler errorHandler() {
        return new ConnexErrorHandler();
    }

    public static class ConnexErrorHandler extends ConditionalRejectingErrorHandler {
        @Override
        public void handleError(Throwable t) {
            throw new AmqpRejectAndDontRequeueException("Error Handler converted exception to fatal", t);
        }
    }
}
