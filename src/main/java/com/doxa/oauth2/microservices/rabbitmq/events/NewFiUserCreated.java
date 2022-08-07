package com.doxa.oauth2.microservices.rabbitmq.events;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.doxa.oauth2.config.AppConfig;
import com.doxa.oauth2.microservices.rabbitmq.Queues;

@Configuration
public class NewFiUserCreated {

    @Bean
    public DirectExchange newFiUserCreatedExchange() {
        return new DirectExchange(Queues.NEW_FI_USER_CREATED_EXCHANGE);
    }

    @Bean
    public DirectExchange newFiUserCreatedDLX() {
        return new DirectExchange(Queues.NEW_FI_USER_CREATED_DLX);
    }

    @Bean
    public Queue newFiUserCreatedQueue() {
        return QueueBuilder.durable(Queues.NEW_FI_USER_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", Queues.NEW_FI_USER_CREATED_DLX)
                .withArgument("x-dead-letter-routing-key", Queues.NEW_FI_USER_CREATED_DLQ)
                .withArgument("x-message-ttl", AppConfig.MQ_DELAYED_RETRY_TIME)
                .build();
    }

    @Bean
    public Queue newFiUserCreatedDLQ() {
        return new Queue(Queues.NEW_FI_USER_CREATED_DLQ, true);
    }

    @Bean
    public Binding bindEntityNewFiUserCreated() {
        return BindingBuilder.bind(newFiUserCreatedQueue()).to(newFiUserCreatedExchange()).with(Queues.NEW_FI_USER_CREATED_QUEUE);
    }

    @Bean
    public Binding bindDLQEntityNewFiUserCreated() {
        return BindingBuilder.bind(newFiUserCreatedDLQ()).to(newFiUserCreatedDLX()).with(Queues.NEW_FI_USER_CREATED_DLQ);
    }
}
