/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.services;

import com.mycompany.hibernatemvn.DB.DAO.*;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import java.util.List;

/**
 *
 * @author Denis
 */
public interface EmployeeService {
void addToDepartment(String department, Employee employee, long enterpriseId);
void removeFromDepartment(Department department, Employee employee);
Employee findById(long id);
void updateEmployee(Employee employee);
void deleteEmployee(Employee employee);
void updateEmployee(String firstname, String lastname, String birthday, String type, Employee employee);
}

