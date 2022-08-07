package com.doxa.oauth2.microservices.rabbitmq;

public class Queues {
    /**
     * Parking lot, where all the bad messages are stored
     */
    public static final String CONNEX_PARKING_LOT = System.getenv("CONNEX_PARKING_LOT") != null ? System.getenv("CONNEX_PARKING_LOT") : "Connex.parking.lot";
    public static final String CONNEX_PARKING_LOT_DLX = CONNEX_PARKING_LOT + ".exchange.dlx";

    // Exchange
    public static final String USER_DETAILS_UPDATED_EXCHANGE = "exchange.819.user_details.updated";
    public static final String NEW_COMPANY_CREATED_EXCHANGE = "exchange.company.created";
    public static final String NEW_USER_CREATED_EXCHANGE = "exchange.1.user.created";

    // Queues
    public static final String SENDING_EMAIL = "queue.email-service.new_email.sent";
    public static final String USER_UPDATED_REQUISITION = "queue.requisition.user.updated";
    public static final String USER_UPDATED_ORDERS = "queue.orders.user.updated";

    // New company is created
    public static final String ENTITY_NEW_COMPANY_CREATED_QUEUE = "queue.entity.company.created";
    public static final String ENTITY_NEW_COMPANY_CREATED_DLX = NEW_COMPANY_CREATED_EXCHANGE + ".dlx";
    public static final String ENTITY_NEW_COMPANY_CREATED_DLQ = ENTITY_NEW_COMPANY_CREATED_QUEUE + ".dlq";

    //New user is created
    public static final String ENTITY_NEW_USER_CREATED_QUEUE = "queue.1.entity.user.created";
    public static final String ENTITY_NEW_USER_CREATED_DLX = NEW_USER_CREATED_EXCHANGE + ".dlx";
    public static final String ENTITY_NEW_USER_CREATED_DLQ = ENTITY_NEW_USER_CREATED_QUEUE + ".dlq";
    
	public static final String NEW_FI_USER_CREATED_EXCHANGE = "exchange.fi.user.created";
	public static final String NEW_FI_USER_CREATED_QUEUE = "queue.fi.user.created";
	public static final String NEW_FI_USER_CREATED_DLX = NEW_FI_USER_CREATED_EXCHANGE + ".dlx";
    public static final String NEW_FI_USER_CREATED_DLQ = NEW_FI_USER_CREATED_QUEUE + ".dlq";
    
	public static final String FI_USER_UPDATED_EXCHANGE = "exchange.fi.user.updated";
	public static final String FI_USER_UPDATED_QUEUE = "queue.fi.user.updated";
	public static final String FI_USER_UPDATED_DLX = FI_USER_UPDATED_EXCHANGE + ".dlx";
    public static final String FI_USER_UPDATED_DLQ = FI_USER_UPDATED_QUEUE + ".dlq";
    
	public static final String NEW_FI_CREATED_EXCHANGE = "exchange.financial.institution.created";
	public static final String NEW_FI_CREATED_DLX = NEW_FI_CREATED_EXCHANGE + ".dlx";
	public static final String NEW_FI_CREATED_QUEUE = "queue.financial.institution.created";
	public static final String NEW_FI_CREATED_DLQ = NEW_FI_CREATED_QUEUE + ".dlq";;
	
	public static final String UPDATE_FI_QUEUE = "queue.financial.institution.update";
    public static final String UPDATE_FI_DLQ = UPDATE_FI_QUEUE + ".dlq";
    public static final String UPDATE_FI_EXCHANGE = "exchange.financial.institution.update";
    public static final String UPDATE_FI_DLX = UPDATE_FI_EXCHANGE + ".dlx";
}
