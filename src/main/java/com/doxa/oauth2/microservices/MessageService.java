package com.doxa.oauth2.microservices;

import com.doxa.oauth2.microservices.interfaces.IMessageService;
import com.doxa.oauth2.microservices.rabbitmq.MessageSender;
import com.doxa.oauth2.microservices.rabbitmq.Queues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService implements IMessageService {
    @Autowired
    MessageSender messageSender;

    @Override
    public void sendToEmailQueue(Object event) {
        try{
            messageSender.sendMessage(Queues.SENDING_EMAIL, event);
        } catch (Exception e) {
            log.error("Unable to execute command: sendToEmailQueue" );
            e.printStackTrace();
        }
    }

    @Override
    public void sendToUserUpdatedExchange(Object event) {

    }

    @Override
    public boolean notifyNewCompanyCreated(Object event) {
        try {
            messageSender.sendMessage(Queues.NEW_COMPANY_CREATED_EXCHANGE, Queues.ENTITY_NEW_COMPANY_CREATED_QUEUE, event);
            return true;
        }catch (Exception e) {
            log.error("Unable to execute command: noifiyNewCompanyCreated" );
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean notifyNewUserCreated(Object event) {
        try {
            messageSender.sendMessage(Queues.NEW_USER_CREATED_EXCHANGE, Queues.ENTITY_NEW_USER_CREATED_QUEUE, event);
            return true;
        }catch (Exception e) {
            log.error("Unable to execute command: notifiyNewUserCreated" );
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public boolean notifyFiUserCreated(Object event) {
		try {
			messageSender.sendMessage(Queues.NEW_FI_USER_CREATED_EXCHANGE, Queues.NEW_FI_USER_CREATED_QUEUE, event);
			return true;
		} catch (Exception e) {
			log.error("Unable to execute command: notifiyNewFiUserCreated");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean notifyFiUserUpdated(Object event) {
        try{
            messageSender.sendMessage(Queues.FI_USER_UPDATED_EXCHANGE, Queues.FI_USER_UPDATED_QUEUE, event);
        } catch (Exception e) {
            log.error("Unable to execute command: notifiyFiUserUpdated" );
            e.printStackTrace();
        }
		return false;
    }

	@Override
	public boolean notifyFiCreated(Object event) {
        try{
            messageSender.sendMessage(Queues.NEW_FI_CREATED_EXCHANGE, Queues.NEW_FI_CREATED_QUEUE, event);
            System.out.println("the sender event"+event);
        } catch (Exception e) {
            log.error("Unable to execute command: notifyFiCreated" );
            e.printStackTrace();
        }
		return false;
    }

	@Override
	public boolean notifyFiUpdated(Object event) {
		try {
			messageSender.sendMessage(Queues.FI_USER_UPDATED_EXCHANGE, Queues.FI_USER_UPDATED_QUEUE, event);
		} catch (Exception e) {
			log.error("Unable to execute command: notifiyFiUserUpdated");
			e.printStackTrace();
		}
		return false;
	}

}
