/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.services;

import com.mycompany.hibernatemvn.DB.DAO.*;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Denis
 */
public interface DepartmentService {
Department findById(long id);
List<Department> findAll();
void saveEmployee(String firstname, String lastname, String birthday, String type, Department department);
void updateDepartment(Department department);
void deleteDepartment(Department department);
void updateDepartment(String name, String type, Department department);
}
