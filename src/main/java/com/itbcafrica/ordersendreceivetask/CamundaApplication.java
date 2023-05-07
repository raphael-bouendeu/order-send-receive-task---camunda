package com.itbcafrica.ordersendreceivetask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableProcessApplication("order-send-receive-task")
public class CamundaApplication implements CommandLineRunner {

	@Resource
	private RuntimeService runtimeService;

	public static void main(String... args) {
		SpringApplication.run(CamundaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final String businessKey = UUID.randomUUID().toString();
		
		Map<String, Object>processOrderData= new HashMap<>();
		processOrderData.put("customerName", "Raphael Bouendeu");
		processOrderData.put("item", "Mobile");
		processOrderData.put("giftPackagingIsNeeded", true);
		processOrderData.put("zipcode", "1234567");
		
		Map<String, Object>processOrderForShipment= new HashMap<>();
		processOrderForShipment.put("customerName", "Raphael Bouendeu");
		processOrderForShipment.put("item", "Mobile");
		processOrderForShipment.put("zipcode", "1234567");
		
		// starting the first order-send-receive-task BPMN
		runtimeService.startProcessInstanceByKey("order-send-receive-task",businessKey,processOrderData);
		
		//starting the order-receive BPMN with same business  Key
		runtimeService.startProcessInstanceByKey("order-receive",businessKey,processOrderForShipment);
		
	}

	
}
