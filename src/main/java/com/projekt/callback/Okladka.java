package com.projekt.callback;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Okladka implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		runtimeService.createMessageCorrelation("WynikOkladkaMsg")
			.processInstanceBusinessKey(execution.getVariable("parentBussinesKey").toString())
			.setVariable("podanie_autor", execution.getVariable("podanie_autor"))
			.setVariable("podanie_tytul", execution.getVariable("podanie_tytul"))
			.setVariable("podanie_opis", execution.getVariable("podanie_opis"))
			.setVariable("uzasadnienie", execution.getVariable("uzasadnienie"))
			.setVariable("decyzja", execution.getVariable("decyzja"))
			.setVariable("status", execution.getVariable("status"))
			.setVariable("status_okladki", execution.getVariable("status_okladki"))
			.setVariable("cena_okladki", execution.getVariable("cena_okladki"))
			.correlateWithResult();

	}
}
