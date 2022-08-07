package com.doxa.oauth2.microservices.rabbitmq.events;

import com.doxa.oauth2.microservices.rabbitmq.Queues;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class EmailEvent {
    /**
     * @author vuducnoi
     * @description Sending emails
     */
    @Bean
    public Queue emailQueue() {
        return new Queue(Queues.SENDING_EMAIL, true);
    }
}
