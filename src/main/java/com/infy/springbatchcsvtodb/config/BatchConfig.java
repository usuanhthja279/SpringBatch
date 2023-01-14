package com.infy.springbatchcsvtodb.config;

import com.infy.springbatchcsvtodb.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfig {
    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private DataSource dataSource;

    @Bean
    public FlatFileItemReader<Student> itemReader(){
        FlatFileItemReader<Student> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/read.txt"));
        itemReader.setName("InitialFile");
        itemReader.setStrict(false);
//        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapperMethod());
        return itemReader;
    }

    private LineMapper<Student> lineMapperMethod() {
        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setNames(Student.columns());
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);

        //lineTokenizer.setNames("id","first_name","last_name","email");

        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public BatchProcessing processing(){
        return new BatchProcessing();
    }

    @Bean
    public JdbcBatchItemWriter<Student> itemWriter(){
        JdbcBatchItemWriter<Student> itemWriter = new JdbcBatchItemWriter<Student>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into Student (id,first_name,last_name,email) values(:id,:first_name,:last_name,:email)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Student>());
        return itemWriter;
    }

    @Bean
    public Step Step1(){
        return stepBuilderFactory.get("StepExecution").<Student,Student>chunk(5)
                .reader(itemReader())
                .processor(processing())
                .writer(itemWriter())
                .build();
    }
    @Bean
    public Job job1(){
        return jobBuilderFactory.get("JobExecutor").flow(Step1()).end().build();
    }
}
