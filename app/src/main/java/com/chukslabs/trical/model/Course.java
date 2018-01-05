package com.chukslabs.trical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by echuquilin on 12/06/17.
 */
@SuppressWarnings("serial")
public class Course implements Serializable {

    private String code;
    private String name;
    private List<Exam> exams;
    private int minGrade;
    private int maxGrade;
    private CalcMethod calcMethod;
    private int finalGrade;

    public Course(String code, String name, int minGrade, int maxGrade, CalcMethod calcMethod) {
        this.name = name;
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
        this.calcMethod = calcMethod;
        this.exams = new ArrayList<>();
        this.finalGrade = -1;

        // optional fields
        this.code = code.isEmpty() ? null : code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public int getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    public CalcMethod getCalcMethod() {
        return calcMethod;
    }

    public void setCalcMethod(CalcMethod calcMethod) {
        this.calcMethod = calcMethod;
    }

    public int updateGrade(String examName, int gradeIndex, int grade) {
        int index = 0;
        while (!exams.get(index).getName().equalsIgnoreCase(examName)) {
            index++;
        }
        exams.get(index).getGrades().set(gradeIndex, grade);
        return index;
    }

    public int getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(int finalGrade) {
        this.finalGrade = finalGrade;
    }

}
