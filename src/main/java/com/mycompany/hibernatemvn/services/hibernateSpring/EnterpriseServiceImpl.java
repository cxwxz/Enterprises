/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.services.hibernateSpring;

import com.mycompany.hibernatemvn.DB.DAO.DepartmentDAO;
import com.mycompany.hibernatemvn.DB.DAO.DepartmentTypeDAO;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeDAO;
import com.mycompany.hibernatemvn.DB.DAO.EmployeeTypeDAO;
import com.mycompany.hibernatemvn.DB.DAO.EnterpriseDAO;
import com.mycompany.hibernatemvn.DB.DAO.EnterpriseTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import com.mycompany.hibernatemvn.services.EnterpriseService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service("enterpriseService")
@Repository
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseDAO enterpriseDAO;
    @Autowired
    private EnterpriseTypeDAO enterpriseTypeDAO;
    @Autowired
    private DepartmentDAO departmentDAO;
    @Autowired
    private DepartmentTypeDAO departmentTypeDAO;
    @Autowired
    private EmployeeDAO employeeDAO;

    @Override//return filled enterprise 
    public void saveEnterprise(String name, String date, String type) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Date d = null;

        try {
            d = df.parse(date);
        } catch (ParseException ex) {
        }

        Enterprise enterprise = new Enterprise();
        enterprise.setName(name);
        enterprise.setFoundationDate(d);

        EnterpriseType enterpriseType = new EnterpriseType();
        enterpriseType.setName(type);
        enterpriseTypeDAO.save(enterpriseType);

        enterprise.setType(enterpriseType);

        enterpriseDAO.save(enterprise);
    }

    @Override
    public Enterprise findById(long id) {

        return enterpriseDAO.findById(id);

    }

    @Override
    public List<Enterprise> findAll() {//check returned value is not null ( if(null) return new ArrayList<> )
        List<Enterprise> list = (List<Enterprise>)enterpriseDAO.findAll();
        for(Enterprise e : list)System.err.println(e.getId());
        return list;
    }

    @Override
    public void saveDepartment(String name, String type, /*long enterpriseId*/Enterprise enterprise) {

        Department department = new Department();
        department.setName(name);
     
        department.setEnterprise(enterprise);

        DepartmentType departmentType = new DepartmentType();
        departmentType.setName(type);
        departmentTypeDAO.save(departmentType);
        //check if departmentType is persistent
        department.setType(departmentType);

        departmentDAO.save(department);
        enterprise.setDepartments(department);
        enterpriseDAO.update(enterprise);
    }

    @Override
    public void updateEnterprise(Enterprise enteprise) {
        enterpriseDAO.update(enteprise);
    }

    @Override
    public void deleteEnterprise(Enterprise enteprise) {
/*
        List<Department> deps = enteprise.getDepartments();
        List<Employee> emps = null;

        for (Department department : deps) {


            emps = department.getEmployees();



            for (Employee employee : emps) {
                employee.getDepartments().remove(department);

               
                employeeDAO.update(employee);
           
            }

          
            departmentDAO.delete(department);
        }
        for (Employee employee : emps) {
            if (employee.getDepartments().isEmpty()) {
                employeeDAO.delete(employee);
            }
        }
        
        */
        enterpriseDAO.delete(enteprise);
    }

    @Override//if too many params
    public void updateEnterprise(String name, String date, String type, Enterprise enterprise) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Date d = null;

        try {
            d = df.parse(date);
        } catch (ParseException ex) {
        }
        enterprise.setName(name);
        enterprise.setFoundationDate(d);

        EnterpriseType enterpriseType = enterprise.getType();
        enterpriseType.setName(type);
        enterpriseTypeDAO.update(enterpriseType);

        enterprise.setType(enterpriseType);

        enterpriseDAO.update(enterprise);

    }
}
