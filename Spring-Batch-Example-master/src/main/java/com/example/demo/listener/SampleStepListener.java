package com.example.demo.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SampleStepListener extends StepExecutionListenerSupport {
	// step 실행 전에 처리할 일
	@Override    
	public void beforeStep(StepExecution stepExecution) {   
		if(stepExecution.getStatus() == BatchStatus.STARTED) {    
			log.info("chunkStep start! ");  
		}  
	}
	
	// step 실행 후에 처리할 일
	@Override    
	public ExitStatus afterStep(StepExecution stepExecution) {     
		BatchStatus batchStatus = stepExecution.getStatus();
		if(batchStatus.equals(BatchStatus.COMPLETED)) {      
			log.info("chunkStep successed! ");    
			} 
		else if (stepExecution.getStatus() == BatchStatus.FAILED) { 
			log.info("chunkStep failed! "); 
			}
		return stepExecution.getExitStatus();  
	}
}
