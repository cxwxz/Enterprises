/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.services.hibernateSpring;

import com.mycompany.hibernatemvn.DB.DAO.DepartmentDAO;
import com.mycompany.hibernatemvn.DB.DAO.DepartmentTypeDAO;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeDAO;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import com.mycompany.hibernatemvn.services.DepartmentService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service("departmentService")
@Repository
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDAO departmentDAO;
    @Autowired
    private DepartmentTypeDAO departmentTypeDAO;
    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private EmployeeTypeDAO employeeTypeDAO;

    @Override
    public Department findById(long id) {

        return departmentDAO.findById(id);

    }

    @Override
    public List<Department> findAll() {
        return departmentDAO.findAll();
    }

    @Override
    public void saveEmployee(String firstname, String lastname, String birthday, String type, Department department) {

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Date d = null;

        try {
            d = df.parse(birthday);
        } catch (ParseException ex) {
        }

        Employee employee = new Employee();
        employee.setFirstName(firstname);
        employee.setLastName(lastname);
        employee.setBirthday(d);
        //employee.setEnterpriseId(department.getEnterpriseId());


        EmployeeType employeeType = new EmployeeType();
        employeeType.setName(type);
        employeeTypeDAO.save(employeeType);

        employee.setType(employeeType);


        employee.setDepartments(department);
        department.setEmployees(employee);
        employeeDAO.save(employee);
//
        departmentDAO.update(department);//
    }

    @Override
    public void updateDepartment(Department department) {
        departmentDAO.update(department);
    }

    @Override
    public void deleteDepartment(Department department) {

        List<Employee> emps = department.getEmployees();
            
        for (Employee employee : emps) {
            employee.getDepartments().remove(department);
            
           /* if(employee.getDepartments().isEmpty())
                employeeDAO.delete(employee);
            else*/
                employeeDAO.update(employee);
           //System.err.print(employee.getDepartments().size());
        }
       
        //if CascadeType.REFRESH missing
          /*department.getEmployees().clear();
         departmentDAO.update(department);*/
      
        departmentDAO.delete(department);
        
        for (Employee employee : emps) 
            if(employee.getDepartments().isEmpty())
                employeeDAO.delete(employee);
    }

    @Override
    public void updateDepartment(String name, String type, Department department) {

        department.setName(name);


        DepartmentType departmentType = department.getType();
        departmentType.setName(type);
        departmentTypeDAO.update(departmentType);

        department.setType(departmentType);

        departmentDAO.update(department);
    }
}
