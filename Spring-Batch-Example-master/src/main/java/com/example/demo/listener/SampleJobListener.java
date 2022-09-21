package com.example.demo.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SampleJobListener extends JobExecutionListenerSupport {

	// step 실행 전에 처리할 일
	@Override    
	public void beforeJob(JobExecution jobExecution) {   
		if(jobExecution.getStatus() == BatchStatus.STARTED) {    
			log.info("chunkJob start! ");  
		}  
	}
	
	// step 실행 후에 처리할 일
	@Override    
	public void afterJob(JobExecution jobExecution) {     
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {      
			log.info("chunkJob successed! ");    
			} 
		else if (jobExecution.getStatus() == BatchStatus.FAILED) { 
			log.info("chunkJob failed! "); 
			}  
	}

}
