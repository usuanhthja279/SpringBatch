package com.infy.batchprocessing2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="Student_Table")
public class Student {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
