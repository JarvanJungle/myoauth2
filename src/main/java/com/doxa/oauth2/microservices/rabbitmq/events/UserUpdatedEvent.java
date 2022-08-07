package com.doxa.oauth2.microservices.rabbitmq.events;

import com.doxa.oauth2.microservices.rabbitmq.Queues;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUpdatedEvent {
    /**
     * Exchange configuration for user updated event
     */
    @Bean
    public FanoutExchange userUpdatedExchange() {
        return new FanoutExchange(Queues.USER_DETAILS_UPDATED_EXCHANGE);
    }

    /**
     * @author vuducnoi
     * @description Send to requistion service whenever user details updated
     */
    @Bean
    public Queue userUpdatedRequistion() {
        return new Queue(Queues.USER_UPDATED_REQUISITION, true);
    }

    /**
     * @author vuducnoi
     * @description Send to order service whenever user details updated
     */
    @Bean
    public Queue userUpdatedOrders() {
        return new Queue(Queues.USER_UPDATED_ORDERS, true);
    }

    @Bean
    public Binding bindUserUpdatedRequistion() {
        return BindingBuilder.bind(userUpdatedExchange()).to(userUpdatedExchange());
    }

    @Bean
    public Binding bindUserUpdatedOrder() {
        return BindingBuilder.bind(userUpdatedOrders()).to(userUpdatedExchange());
    }

}
