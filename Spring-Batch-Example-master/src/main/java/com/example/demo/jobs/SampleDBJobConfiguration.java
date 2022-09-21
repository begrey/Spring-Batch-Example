package com.example.demo.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.example.demo.domain.Employee;
import com.example.demo.items.SampleDBItem;
import com.example.demo.listener.SampleJobListener;
import com.example.demo.listener.SampleStepListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SampleDBJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory; // Job 빌더 생성용
    private final StepBuilderFactory stepBuilderFactory; // Step 빌더 생성용
    private final SampleDBItem sampleDBItem;
  
    @Bean    
    public Job sampleDb2FileChunkJob(SampleJobListener jobListener, Step sampleDb2FileChunkStep) {
    	return jobBuilderFactory.get("sampleDb2FileChunkJob")                   
    			.listener(jobListener)       
    			.flow(sampleDb2FileChunkStep)     
    			.end()        
    			.build();  
    }    
    
    @Bean   
    public Step sampleDb2FileChunkStep(JdbcCursorItemReader<Employee> jdbcCursorItemReader, SampleStepListener stepListener, FlatFileItemWriter<Employee> flatFileItemWriter) {  
    	return stepBuilderFactory.get("sampleDb2FileChunkStep")         
    			.listener(stepListener)        
    			.<Employee, Employee> chunk(10)  
    			.reader(jdbcCursorItemReader)    
    			.processor(sampleDBItem.processor())
    			.writer(flatFileItemWriter)     
    			.build();    
    }        


	
    
}
