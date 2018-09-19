/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luisjjavier.excelreader.web;

import java.io.Serializable;

/**
 *
 * @author LuisJavier
 */
public class Employee implements Serializable {

    private String name;
    private String lastname;
    private double salary;
    private String department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Employee(String name, String lastname, double salary, String department) {
        this.name = name;
        this.lastname = lastname;
        this.salary = salary;
        this.department = department;
    }
}
