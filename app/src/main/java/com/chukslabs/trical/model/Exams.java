package com.chukslabs.trical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by educhuks on 10/9/17.
 */
@SuppressWarnings("serial")
public class Exams implements Serializable {

    private List<Exam> exams;

    public Exams() {
        exams = new ArrayList<>();
    }

    public Exams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

}
