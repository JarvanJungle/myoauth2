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
public class NewFiCreated {

    @Bean
    public DirectExchange fiCreatedExchange() {
        return new DirectExchange(Queues.NEW_FI_CREATED_EXCHANGE);
    }

    @Bean
    public DirectExchange fiCreatedDLX() {
        return new DirectExchange(Queues.NEW_FI_CREATED_DLX);
    }

    @Bean
    // quere newFiCreatedQueue = new Queue();
    public Queue fiCreatedQueue() {
        return QueueBuilder.durable(Queues.NEW_FI_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", Queues.NEW_FI_CREATED_DLX)
                .withArgument("x-dead-letter-routing-key", Queues.NEW_FI_CREATED_DLQ)
                .withArgument("x-message-ttl", AppConfig.MQ_DELAYED_RETRY_TIME)
                .build();
    }

    @Bean
    public Queue fiCreatedDLQ() {
        return new Queue(Queues.NEW_FI_CREATED_DLQ, true);
    }

    @Bean
    public Binding bindEntityFiCreated() {
        return BindingBuilder.bind(fiCreatedQueue()).to(fiCreatedExchange()).with(Queues.NEW_FI_CREATED_QUEUE);
    }

    @Bean
    public Binding bindDLQEntityFiCreated() {
        return BindingBuilder.bind(fiCreatedDLQ()).to(fiCreatedDLX()).with(Queues.NEW_FI_CREATED_DLQ);
    }
}
