package com.doxa.oauth2.microservices.rabbitmq.events;

import com.doxa.oauth2.config.AppConfig;
import com.doxa.oauth2.microservices.rabbitmq.Queues;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewUserCreatedEvent {

    @Bean
    public DirectExchange newUserCreatedExchange() {
        return new DirectExchange(Queues.NEW_USER_CREATED_EXCHANGE);
    }

    @Bean
    public DirectExchange newUserCreatedDLX() {
        return new DirectExchange(Queues.ENTITY_NEW_USER_CREATED_DLX);
    }

    @Bean
    public Queue newUserCreatedQueue() {
        return QueueBuilder.durable(Queues.ENTITY_NEW_USER_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", Queues.ENTITY_NEW_USER_CREATED_DLX)
                .withArgument("x-dead-letter-routing-key", Queues.ENTITY_NEW_USER_CREATED_DLQ)
                .withArgument("x-message-ttl", AppConfig.MQ_DELAYED_RETRY_TIME)
                .build();
    }

    @Bean
    public Queue newUserCreatedDLQ() {
        return new Queue(Queues.ENTITY_NEW_USER_CREATED_DLQ, true);
    }

    @Bean
    public Binding bindEntityNewUserCreated() {
        return BindingBuilder.bind(newUserCreatedQueue()).to(newUserCreatedExchange()).with(Queues.ENTITY_NEW_USER_CREATED_QUEUE);
    }

    @Bean
    public Binding bindDLQEntityNewUserCreated() {
        return BindingBuilder.bind(newUserCreatedDLQ()).to(newUserCreatedDLX()).with(Queues.ENTITY_NEW_USER_CREATED_DLQ);
    }
}
