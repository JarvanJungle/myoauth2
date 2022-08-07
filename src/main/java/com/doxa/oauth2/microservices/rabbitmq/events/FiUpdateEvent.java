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
public class FiUpdateEvent {
     @Bean
        public DirectExchange fiUpdateExchange() {
            return new DirectExchange(Queues.UPDATE_FI_EXCHANGE);
        }
        @Bean
        public DirectExchange fiUpdateDLX() {
            return new DirectExchange(Queues.UPDATE_FI_DLQ);
        }
        @Bean
        public Queue fiUpdateQueue() {
            return QueueBuilder.durable(Queues.UPDATE_FI_QUEUE)
                    .withArgument("x-dead-letter-exchange", Queues.UPDATE_FI_DLX)
                    .withArgument("x-dead-letter-routing-key", Queues.UPDATE_FI_DLQ)
                    .withArgument("x-message-ttl", AppConfig.MQ_DELAYED_RETRY_TIME)
                    .build();
        }
        @Bean
        public Queue fiUpdateDLQ() {
            return new Queue(Queues.UPDATE_FI_DLQ, true);
        }
        @Bean
        public Binding bindEntityFiUpdate() {
            return BindingBuilder.bind(fiUpdateQueue()).to(fiUpdateExchange()).with(Queues.UPDATE_FI_QUEUE);
        }
        @Bean
        public Binding bindDLQEntityFiUpdate() {
            return BindingBuilder.bind(fiUpdateDLQ()).to(fiUpdateDLX()).with(Queues.UPDATE_FI_DLQ);
        }
}
