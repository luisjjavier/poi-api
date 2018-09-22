/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luisjjavier.excelreader.web;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 *
 * @author LuisJavier
 */
@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeesRepository employeeRepository;

    public EmployeesController() {
        employeeRepository = new EmployeesRepository("employeesDB.xlsx");
    }

    @RequestMapping(method = GET)
    @CrossOrigin
    public List<Employee> list() {
        return this.employeeRepository.getEmployees();
    }

    @RequestMapping(value = "/{id}", method = GET)
    @CrossOrigin
    public ResponseEntity<Employee> get(@PathVariable int id) {
        Employee employee = this.employeeRepository.getEmployee(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = POST, consumes = "application/json")
    @CrossOrigin
    public ResponseEntity<Employee> post(@RequestBody EmployeeDto input) {
        Employee employee = this.employeeRepository.addEmployee(new Employee(input.getName(),
                input.getLastname(), input.getSalary(), input.getDepartment()));

        this.employeeRepository.saveChanges();
        return ResponseEntity.ok(employee);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    @CrossOrigin
    public ResponseEntity<Employee> delete(@PathVariable int id) {
        Employee employee = this.employeeRepository.getEmployee(id);
        if (employee != null) {
            this.employeeRepository.deleteEmployee(id);
            this.employeeRepository.saveChanges();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = PUT, value = "/{id}",consumes = "application/json")
    @CrossOrigin
    public ResponseEntity<Employee>put(@PathVariable int id, @RequestBody EmployeeDto input) {
        Employee employee = employeeRepository.getEmployee(id);
        employee.setSalary(input.getSalary());
        employee.setName(input.getName());
        employee.setLastname(input.getLastname());
        employee.setDepartment(input.getDepartment());
        this.employeeRepository.saveChanges();
        return ResponseEntity.ok(employee);
    }
}
