package com.example.demo.items;


import javax.sql.DataSource;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SampleDBItem {
	
	private Resource outputResource = new FileSystemResource("data/output/employee.txt");
	
	@Bean
    public ItemProcessor<Employee, Employee> processor() {
		log.info("<<Enter to ItemProcessor>>");
        return employ -> {
        	employ.setUserName(employ.getUserName()+"#");
            return employ;
        };
    }

    @Bean    
    public JdbcCursorItemReader<Employee> jdbcCursorItemReader(DataSource dataSource) { 
    	return new JdbcCursorItemReaderBuilder<Employee>()        
    			.name("jdbcCursorItemReader")              
    			.fetchSize(100)          
    			.dataSource(dataSource)    
    			.rowMapper(new BeanPropertyRowMapper<>(Employee.class))    
    			.sql("SELECT user_id, user_name, user_gender, department FROM BATCH_SAMPLE_EMPLOYEE")
    			.build();  
    }  
    
    @Bean   
    public FlatFileItemWriter<Employee> flatFileItemWriter() {
    	FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<>();     
    	writer.setResource(outputResource);    
    	writer.setAppendAllowed(true);    
    	writer.setLineAggregator(new DelimitedLineAggregator<Employee>() 
    	{  
    		{                
    			setDelimiter(",");   
    			setFieldExtractor(new BeanWrapperFieldExtractor<Employee>() {   
    			{                     
    				setNames(new String[] {
    						"userId", "userName", "userGender", "department" 
    						});   
    			}       
    			});        
    		}     
    	});     
    	return writer;  
    }
}
