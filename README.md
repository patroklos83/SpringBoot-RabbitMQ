# SpringBoot-RabbitMQ


Example of SpringBoot integration with RabbitMQ messaging
------------------------------------------------------------

RabbitMQ broker instance required to be running... (https://www.rabbitmq.com/)


1.Start Application as a producer and a consumer
2.Enter a url for example http://localhost:8080/add2queue/producer?empName=EmployeeName&empId=5000 
This will send the employee message to a queue ready to be consumed

if the employee id is equal to 1000, the consumer listener will throw an exception, the message will be
retried 6 times and will be added to a DeadLetter Queue


3.There is a second listener which will consume the messages from the DeadLetter Queue.

