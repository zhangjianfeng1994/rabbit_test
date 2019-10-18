package com.sltas.flow.rabbit.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;

public interface ProducerAckHandler {

	void ack(CorrelationData correlationData);

	void nackConfirm(CorrelationData correlationData,String cause);

	void nackReturn(Message message, int replyCode, String replyText, String exchange, String routingKey);

}
