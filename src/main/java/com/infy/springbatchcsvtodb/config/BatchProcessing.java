package com.infy.springbatchcsvtodb.config;

import com.infy.springbatchcsvtodb.model.Student;
import org.springframework.batch.item.ItemProcessor;

public class BatchProcessing implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student item) throws Exception {
        return item;
    }
}
