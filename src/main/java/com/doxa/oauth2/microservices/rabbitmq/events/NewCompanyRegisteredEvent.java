package com.doxa.oauth2.microservices.rabbitmq.events;

import com.doxa.oauth2.config.AppConfig;
import com.doxa.oauth2.microservices.rabbitmq.Queues;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewCompanyRegisteredEvent {

    @Bean
    public DirectExchange newCompanyCreatedExchange() {
        return new DirectExchange(Queues.NEW_COMPANY_CREATED_EXCHANGE);
    }

    @Bean
    public DirectExchange newCompanyCreatedDLX() {
        return new DirectExchange(Queues.ENTITY_NEW_COMPANY_CREATED_DLX);
    }

    @Bean
    public Queue newCompanyCreatedQueue() {
        return QueueBuilder.durable(Queues.ENTITY_NEW_COMPANY_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", Queues.ENTITY_NEW_COMPANY_CREATED_DLX)
                .withArgument("x-dead-letter-routing-key", Queues.ENTITY_NEW_COMPANY_CREATED_DLQ)
                .withArgument("x-message-ttl", AppConfig.MQ_DELAYED_RETRY_TIME)
                .build();
    }

    @Bean
    public Queue newCompanyCreatedDLQ() {
        return new Queue(Queues.ENTITY_NEW_COMPANY_CREATED_DLQ, true);
    }

    @Bean
    public Binding bindEntityNewCompanyCreated() {
        return BindingBuilder.bind(newCompanyCreatedQueue()).to(newCompanyCreatedExchange()).with(Queues.ENTITY_NEW_COMPANY_CREATED_QUEUE);
    }

    @Bean
    public Binding bindDLQEntityNewCompanyCreated() {
        return BindingBuilder.bind(newCompanyCreatedDLQ()).to(newCompanyCreatedDLX()).with(Queues.ENTITY_NEW_COMPANY_CREATED_DLQ);
    }
}
