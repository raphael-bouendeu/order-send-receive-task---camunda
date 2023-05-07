package com.itbcafrica.ordersendreceivetask.camunda.services;


import javax.annotation.Resource;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("sendOrderDelegate")
public class SendOrderDelegate implements JavaDelegate{
	
	 private final Logger LOGGER = LoggerFactory.getLogger(SendOrderDelegate.class);
	 
	 @Resource
	 private RuntimeService runtimeService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("SendOrderDelegate ");
		MessageCorrelationResult result = runtimeService.createMessageCorrelation("messageShipment")
				.processInstanceBusinessKey(execution.getBusinessKey())
				.setVariable("caller", SendOrderDelegate.class.getName()).correlateWithResult();
		DelegateExecution shipmentExecution = (DelegateExecution) result.getExecution();
		
		LOGGER.info("result- {} ",shipmentExecution.getVariables());
	}

}
