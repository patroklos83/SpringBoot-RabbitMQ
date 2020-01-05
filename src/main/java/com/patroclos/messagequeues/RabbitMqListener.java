package com.patroclos.messagequeues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.patroclos.model.Employee;

@Component
public class RabbitMqListener {

	
	/***
	 * Primary Listener
	 * @param employee
	 * @throws Exception
	 */
    @RabbitListener(queues="${rabbitmq.queue}")
    public void listen(Employee employee) throws Exception {
        System.out.println("Received a new notification...");
        
        if (employee.getId() == 1000 ) {
			throw new Exception("Invalid employee");
		}
        
        System.out.println(employee.getFirstName());
    }
    
    /***
     * Listener to consume failed messages from Dead Letter Queue
     * @param employee
     * @throws Exception
     */
    @RabbitListener(queues="${rabbitmq.dlqqueue}")
    public void listenFromDeadLetterQueue(Employee employee) throws Exception {
        System.out.println("Received a new notification from Dead Letter Queue...");
        System.out.println(employee.getFirstName());
    }
}