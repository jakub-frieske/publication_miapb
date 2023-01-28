package com.projekt;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.RequiredHistoryLevel;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.assertEquals;

@RequiredHistoryLevel(ProcessEngineConfiguration.HISTORY_AUDIT)
public class WycenaTest {

	  @Rule
	  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

	  @Test
	  @Deployment(resources = {"Wycena.bpmn"})
	  public void testWycenaPromocja() {
	    RuntimeService runtimeService = processEngineRule.getRuntimeService();
	    HistoryService historyService = processEngineRule.getHistoryService();
	    VariableMap variablesIn = Variables.createVariables()
	  	      .putValue("cena_wydawnicta", 200)
				.putValue("cena_okladki", 150)
				.putValue("cena_redakcji_korekty", 100)
				.putValue("cena_ebook", 50)
				.putValue("umowa_lb_egzemplarzy", 100);
	    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Wycena", variablesIn);
	    
	    assertThat(processInstance).isEnded();
	    
	     List<HistoricVariableInstance> cena_sumaList = historyService
				 .createHistoricVariableInstanceQuery()
				 .variableName("cena_suma").list();

	     HistoricVariableInstance cena_suma = cena_sumaList.get(cena_sumaList.size()-1);
	     assertEquals(475.0, cena_suma.getValue());
	    
	  }


	  @Test
	  @Deployment(resources = {"OcenaKsiazki.dmn", "Publikacja.bpmn"})
	  public void testPublikacjaKategoriiHorror() throws InterruptedException {
	    RuntimeService runtimeService = processEngineRule.getRuntimeService();
	    VariableMap variablesIn = Variables.createVariables()
	    	  .putValue("podanie_autor", "John")
	    	  .putValue("podanie_tytul", "Ark")
	    	  .putValue("podanie_opis", "Dawno dawno temu")
				.putValue("podanie_kategoria", "horror");
	    
	    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Publikacja", variablesIn);
	    
	    assertThat(processInstance).isWaitingAt("Odebranie_anulowanej_decyzji");
		assertThat(processInstance).variables().containsEntry("status", "anulowano");
		assertThat(processInstance).variables().containsEntry("powodAnulowania", "Nasze wydawnictwo nie publikuje ksiazek z kategorii horror.");
		complete(task(processInstance));
		assertThat(processInstance).isEnded();
	  }
	  
	}
