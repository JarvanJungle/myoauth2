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
public class FiUserUpdated {

    @Bean
    public DirectExchange fiUserUpdatedExchange() {
        return new DirectExchange(Queues.FI_USER_UPDATED_EXCHANGE);
    }

    @Bean
    public DirectExchange fiUserUpdatedDLX() {
        return new DirectExchange(Queues.FI_USER_UPDATED_DLX);
    }

    @Bean
    public Queue fiUserUpdatedQueue() {
        return QueueBuilder.durable(Queues.FI_USER_UPDATED_QUEUE)
                .withArgument("x-dead-letter-exchange", Queues.FI_USER_UPDATED_DLX)
                .withArgument("x-dead-letter-routing-key", Queues.FI_USER_UPDATED_DLQ)
                .withArgument("x-message-ttl", AppConfig.MQ_DELAYED_RETRY_TIME)
                .build();
    }

    @Bean
    public Queue fiUserUpdatedDLQ() {
        return new Queue(Queues.FI_USER_UPDATED_DLQ, true);
    }

    @Bean
    public Binding bindEntityFiUserUpdated() {
        return BindingBuilder.bind(fiUserUpdatedQueue()).to(fiUserUpdatedExchange()).with(Queues.FI_USER_UPDATED_QUEUE);
    }

    @Bean
    public Binding bindDLQEntityFiUserUpdated() {
        return BindingBuilder.bind(fiUserUpdatedDLQ()).to(fiUserUpdatedDLX()).with(Queues.FI_USER_UPDATED_DLQ);
    }
}
