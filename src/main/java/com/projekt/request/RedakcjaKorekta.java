package com.projekt.request;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;


public class RedakcjaKorekta implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {

		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put("parentBussinesKey",  execution.getProcessBusinessKey());

		runtimeService.startProcessInstanceByMessage("RedakcjaKorektaMsg", processVariables);

	}

}
