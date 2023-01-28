package com.projekt.request;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;


public class Okladka implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put("parentBussinesKey",  execution.getProcessBusinessKey());
		processVariables.put("podanie_autor", execution.getVariable("podanie_autor"));
		processVariables.put("podanie_tytul", execution.getVariable("podanie_tytul"));
		processVariables.put("podanie_opis", execution.getVariable("podanie_opis"));
		processVariables.put("uzasadnienie", execution.getVariable("uzasadnienie"));
		processVariables.put("decyzja", execution.getVariable("decyzja"));
		processVariables.put("status", execution.getVariable("status"));

		runtimeService.startProcessInstanceByMessage("OkladkaMsg", processVariables);

	}



}
