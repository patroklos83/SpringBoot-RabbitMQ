package com.patroclos.messagequeues;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.queue}")
	String queueName;

	@Value("${rabbitmq.exchange}")
	String exchange;

	@Value("${rabbitmq.routingkey}")
	private String routingkey;
	

	@Value("${rabbitmq.dlqqueue}")
	String dlqqueueName;

	@Value("${rabbitmq.dlqexchange}")
	String dlqexchange;

	@Value("${rabbitmq.dlqroutingkey}")
	private String dlqroutingkey;

	@Bean
	Queue queue() {
		//return a durable fault tolerant queue, after restart of rabbitmq server
		return QueueBuilder.durable(queueName)
				.withArgument("x-dead-letter-exchange", dlqexchange)
				.withArgument("x-dead-letter-routing-key", dlqroutingkey)
				.build();
	}
	
	@Bean
	Queue dlq() {
		return QueueBuilder.durable(dlqqueueName).build();
	}
	

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
		
	}

	
	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange(dlqexchange);
	}
	
	@Bean
	Binding DLQbinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with(dlqroutingkey);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}


	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}



}