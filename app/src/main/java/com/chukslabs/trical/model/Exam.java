package com.chukslabs.trical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by educhuks on 10/4/17.
 */
@SuppressWarnings("serial")
public class Exam implements Serializable {

    private String name;
    private int quantity;
    private int weight;
    private boolean removeMin;
    private List<Integer> grades;

    public Exam(String name, int weight, int quantity, boolean removeMin) {
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
        this.removeMin = removeMin;
        grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isRemoveMin() {
        return removeMin;
    }

    public void setRemoveMin(boolean removeMin) {
        this.removeMin = removeMin;
    }

}
