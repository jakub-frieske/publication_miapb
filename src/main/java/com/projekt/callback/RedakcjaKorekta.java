package com.projekt.callback;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RedakcjaKorekta implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		runtimeService.createMessageCorrelation("WynikRedakcjiKorektyMsg")
			.processInstanceBusinessKey(execution.getVariable("parentBussinesKey").toString())
			.setVariable("cena_redakcji_korekty", execution.getVariable("cena_redakcji_korekty"))
			.correlateWithResult();

	}
}
