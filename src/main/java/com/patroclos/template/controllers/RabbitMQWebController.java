package com.patroclos.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patroclos.messagequeues.RabbitMQSender;
import com.patroclos.model.Employee;

@RestController
@RequestMapping(value = "/add2queue/")
public class RabbitMQWebController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("empName") String empName,@RequestParam("empId") String empId) {
	
	Employee emp=new Employee();
	emp.setFirstName(empName);
	emp.setId(Long.parseLong(empId));
	rabbitMQSender.send(emp);

		return "Message sent to RabbitMQ Successfully";
	}

}