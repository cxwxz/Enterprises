/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCImpl;

import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

final class EnterpriseCreator {

    public static List<Enterprise> formEnterprises(ResultSet rs) throws SQLException {
       Map<Long, Enterprise> map = new HashMap<Long, Enterprise>();
            Enterprise enterprise = null;

            while (rs.next()) {

                Long enterpriseId = rs.getLong(1);

                if (enterpriseId == null) {
                    continue;
                }

                enterprise = map.get(enterpriseId);

                if (enterprise == null) {
                    enterprise = new Enterprise();
                    enterprise.setId(enterpriseId);
                    enterprise.setName(rs.getString(4));
                    enterprise.setFoundationDate(rs.getDate(2));
                    enterprise.setInfo(rs.getString(3));
                    enterprise.setType(new EnterpriseType(rs.getLong(5), rs.getString(6)));

                    map.put(enterpriseId, enterprise);

                }

                Long departmentId = rs.getLong(7);
                if (departmentId > 0) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString(8));
                    department.setType(new DepartmentType(rs.getLong(9), rs.getString(10)));
                    enterprise.setDepartments(department);
                }
            }
            return new ArrayList<Enterprise>(map.values());
    }
}

final class EnterpriseTypeCreator {

        public static EnterpriseType formEnterpriseType(ResultSet set) throws SQLException {
        EnterpriseType type = null;
        if (set.next()) {
            type = getEnterpriseType(set);
        }

        return type;
    }

    public static List<EnterpriseType> formEnterpriseTypes(ResultSet set) throws SQLException {
        List<EnterpriseType> result = new ArrayList<EnterpriseType>();
        while (set.next()) {
            result.add(getEnterpriseType(set));
        }

        return result;
    }

    private static EnterpriseType getEnterpriseType(ResultSet set) throws SQLException {
        long id = set.getLong("id");
        String name = set.getString("name");
        
        return new EnterpriseType(id, name);
    }
}

final class DepartmentCreator {

    public static List<Department> formDepartments(ResultSet rs)
                throws SQLException, DataAccessException {

            Map<Long, Department> map = new HashMap<Long, Department>();
            Department department = null;

            while (rs.next()) {

                Long departmentId = rs.getLong(1);

                if (departmentId == null) {
                    continue;
                }

                department = map.get(departmentId);

                if (department == null) {
                    department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString(2));
                    department.setType(new DepartmentType(rs.getLong(3), rs.getString(4)));

                    map.put(departmentId, department);

                }

                Long employeeId = rs.getLong(5);
                if (employeeId > 0) {
                    Employee employee = new Employee();
                    employee.setId(employeeId);
                    employee.setFirstName(rs.getString(6));
                    employee.setLastName(rs.getString(7));
                    employee.setBirthday(rs.getDate(8));
                    employee.setType(new EmployeeType(rs.getLong(9), rs.getString(10)));
                    department.setEmployees(employee);
                }
            }
            return new ArrayList<Department>(map.values());
        }
    
}

final class DepartmentTypeCreator {

    public static DepartmentType formDepartmentType(ResultSet set) throws SQLException {
        DepartmentType type = null;
        if (set.next()) {
            type = getDepartmentType(set);
        }

        return type;
    }

    public static List<DepartmentType> formDepartmentTypes(ResultSet set) throws SQLException {
        List<DepartmentType> result = new ArrayList<DepartmentType>();
        while (set.next()) {
            result.add(getDepartmentType(set));
        }

        return result;
    }

    private static DepartmentType getDepartmentType(ResultSet set) throws SQLException {
        long id = set.getLong("id");
        String name = set.getString("name");

      
        return new DepartmentType(id, name);
    }
}

final class EmployeeCreator {

    public static List<Employee> formEmployees(ResultSet rs)
                throws SQLException, DataAccessException {

            Map<Long, Employee> map = new HashMap<Long, Employee>();
            Employee employee = null;

            while (rs.next()) {

                Long employeeId = rs.getLong(1);

                if (employeeId == null) {
                    continue;
                }

                employee = map.get(employeeId);

                if (employee == null) {
                    employee = new Employee();
                    employee.setId(employeeId);
                    employee.setFirstName(rs.getString(2));
                    employee.setLastName(rs.getString(3));
                    employee.setBirthday(rs.getDate(4));
                    employee.setType(new EmployeeType(rs.getLong(5), rs.getString(6)));

                    map.put(employeeId, employee);

                }

                Long departmentId = rs.getLong(7);
                if (employeeId > 0) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString(8));
                    department.setType(new DepartmentType(rs.getLong(9), rs.getString(10)));
                    employee.setDepartments(department);
                }
            }
            return new ArrayList<Employee>(map.values());
        }
}

final class EmployeeTypeCreator {

    public static EmployeeType formEmployeeType(ResultSet set) throws SQLException {
        
        EmployeeType type = null;
        if (set.next()) {
            type = getEmployeeType(set);
        }

        return type;
    }

    public static List<EmployeeType> formEmployeeTypes(ResultSet set) throws SQLException {
        List<EmployeeType> result = new ArrayList<EmployeeType>();
        while (set.next()) {
            result.add(getEmployeeType(set));
        }

        return result;
    }

    private static EmployeeType getEmployeeType(ResultSet set) throws SQLException {
        long id = set.getLong("id");
        String name = set.getString("name");

      
        return new EmployeeType(id, name);
    }
}
