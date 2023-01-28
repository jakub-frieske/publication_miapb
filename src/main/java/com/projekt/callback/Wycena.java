package com.projekt.callback;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Wycena implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		runtimeService.createMessageCorrelation("WycenaMsg")
			.processInstanceBusinessKey(execution.getProcessBusinessKey())
			.setVariable("umowa_lb_egzemplarzy", execution.getVariable("umowa_lb_egzemplarzy"))
			.setVariable("cena_wydawnicta", execution.getVariable("cena_wydawnicta"))
			.setVariable("cena_redakcji_korekty", execution.getVariable("cena_redakcji_korekty"))
			.setVariable("cena_okladki", execution.getVariable("cena_okladki"))
			.correlateWithResult();

	}
}
