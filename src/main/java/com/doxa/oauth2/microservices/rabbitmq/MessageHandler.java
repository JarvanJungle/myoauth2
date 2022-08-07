package com.doxa.oauth2.microservices.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageHandler {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);
    private static final String X_RETRIES_HEADER = "x-retries";
    private static final Integer MAX_RETRIES_COUNT = 5;
    
    @RabbitListener(queues = Queues.ENTITY_NEW_COMPANY_CREATED_DLQ)
    public void handleMessage(Message failedMessage) throws JsonProcessingException {
        processFailedMessageRetryWithParkingLot(failedMessage);
    }

    private void processFailedMessageRetryWithParkingLot(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties()
                .getHeaders().get(X_RETRIES_HEADER);
        if (retriesCnt == null) retriesCnt = 1;
        if (retriesCnt > MAX_RETRIES_COUNT) {
            LOG.info("Sending message to the update invoice parking lot queue");

            // send to parking lot queue
            rabbitTemplate.send(Queues.CONNEX_PARKING_LOT_DLX,"", failedMessage);
            return;
        }
        // update header retries
        failedMessage.getMessageProperties()
                .getHeaders().put(X_RETRIES_HEADER, ++retriesCnt);

        LOG.info("Retrying sending message for the {} time", retriesCnt);
        LOG.info("routing key is {}", failedMessage.getMessageProperties().getReceivedRoutingKey());

        // send to original queue
        String receivedRoutingKey = failedMessage.getMessageProperties().getReceivedRoutingKey();
        String receivedExchange = failedMessage.getMessageProperties().getReceivedExchange();
        int lastIndexOfDot = receivedRoutingKey.lastIndexOf(".");
        int lastIndexOfExchange = receivedExchange.lastIndexOf(".");
        String originalRoutingKey = receivedRoutingKey.substring(0, lastIndexOfDot);
        String originalExchange = failedMessage.getMessageProperties().getReceivedExchange().substring(0, lastIndexOfExchange);
        LOG.info("Original exchange {}", originalExchange);
        rabbitTemplate.send(originalExchange, originalRoutingKey, failedMessage);
    }
}