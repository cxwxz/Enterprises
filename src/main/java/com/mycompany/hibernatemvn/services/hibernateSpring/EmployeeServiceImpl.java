/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.services.hibernateSpring;

import com.mycompany.hibernatemvn.DB.DAO.DepartmentDAO;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeDAO;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import com.mycompany.hibernatemvn.services.EmployeeService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service("employeeService")
@Repository
@Transactional
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private EmployeeTypeDAO employeeTypeDAO;
    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public Employee findById(long id) {
        return employeeDAO.findById(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeDAO.delete(employee);
    }

    @Override
    public void updateEmployee(String firstname, String lastname, String birthday, String type, Employee employee) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Date d = null;

        try {
            d = df.parse(birthday);
        } catch (ParseException ex) {
        }

        employee.setFirstName(firstname);
        employee.setLastName(lastname);
        employee.setBirthday(d);

        EmployeeType employeeType = employee.getType();
        employeeType.setName(type);
        employeeTypeDAO.update(employeeType);

        employee.setType(employeeType);

        employeeDAO.update(employee);
    }

    @Override
    public void addToDepartment(String department, Employee employee, long enterpriseId) {

        List<Department> list = departmentDAO.findByName(department, enterpriseId);

        Department dep = list.get(0);
        dep.setEmployees(employee);
        employee.setDepartments(dep);

        departmentDAO.update(dep);
        employeeDAO.update(employee);



    }

    @Override
    public void removeFromDepartment(Department department, Employee employee) {


        department.getEmployees().remove(employee);
        departmentDAO.update(department);
        employee.getDepartments().remove(department);
        employeeDAO.update(employee);
    }
}
