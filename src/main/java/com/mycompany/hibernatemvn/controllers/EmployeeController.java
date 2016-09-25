package com.mycompany.hibernatemvn.controllers;

import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.Employee;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.services.EmployeeService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Denis
 */
@Controller
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "employeePage/{id}", method = RequestMethod.GET)
    public String getEmployeePage(
            @PathVariable long id, ModelMap model, HttpSession session) {

        Employee employee = employeeService.findById(id);
        session.setAttribute("sessionEmployee", employee);
        model.addAttribute(employee);

        return "employeePage";
    }

    @RequestMapping(value = "editEmployee", method = RequestMethod.GET)
    public String editEmployee(
            long id, Model model) {

        Employee employee = employeeService.findById(id);

        model.addAttribute(employee);


        return "editEmployee";

    }

    @RequestMapping(value = "editEmployee", method = RequestMethod.POST)
    public String saveEditEmployee(
            @RequestParam(value = "firstName") String firstname,
            @RequestParam(value = "lastName") String lastname,
            @RequestParam(value = "birthday") String birthday,
            @RequestParam(value = "employeeType") String type,
            long id, Model model) {

        Employee employee = employeeService.findById(id);
        employeeService.updateEmployee(firstname, lastname, birthday, type, employee);
        // model.addAttribute(department);


        return "redirect:employeePage/" + id;

    }

    @RequestMapping(value = "deleteEmployee/{id}", method = RequestMethod.GET)
    public String deleteEmployee(
            @PathVariable long id, HttpSession session) {
        Department department = (Department) session.getAttribute("sessionDepartment");
        Employee employee = employeeService.findById(id);

        employeeService.deleteEmployee(employee);


        return "redirect:/departmentPage/" + department.getId();

    }

    @RequestMapping(value = "addToDepartment", method = RequestMethod.POST)
    public String addToDepartment(
            @RequestParam(value = "departmentName") String departmentName,
            HttpSession session) {
        Employee employee = (Employee) session.getAttribute("sessionEmployee");
        Enterprise enterprise = (Enterprise)session.getAttribute("sessionEnterprise");
        System.err.print(enterprise.getId());
        employeeService.addToDepartment(departmentName, employee, enterprise.getId());


        return "redirect:/employeePage/" + employee.getId();

    }

    @RequestMapping(value = "removeFromDepartment", method = RequestMethod.GET)
    public String removeFromDepartment(
            HttpSession session) {
        Employee employee = (Employee) session.getAttribute("sessionEmployee");
        Department department = (Department) session.getAttribute("sessionDepartment");

        employeeService.removeFromDepartment(department, employee);


        return "redirect:/employeePage/" + employee.getId();

    }
}
