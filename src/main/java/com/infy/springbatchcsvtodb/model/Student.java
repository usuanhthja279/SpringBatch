package com.infy.springbatchcsvtodb.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter
@ToString
@Entity
@Table(name="Student")
public class Student {
    @Id
    private int Id;
    private String first_name;
    private String last_name;
    private String email;

    public static String[] columns(){
        return new String[]{"id","first_name","last_name","email"};
    }
}
