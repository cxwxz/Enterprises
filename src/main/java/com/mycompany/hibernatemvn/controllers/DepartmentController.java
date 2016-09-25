package com.mycompany.hibernatemvn.controllers;

import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.services.DepartmentService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    @RequestMapping(value = "departmentPage/{id}", method = RequestMethod.GET)
    public String getDepartmentPage(
        @PathVariable long id, Model model, HttpSession session
        ) 
    {    
        Department department = departmentService.findById(id);
        session.setAttribute("sessionDepartment",department);

        model.addAttribute(department);
  
    
        return "departmentPage";
    }
    
    @RequestMapping(value = "createEmployee", method = RequestMethod.POST)
     public String createEmployee(
            @RequestParam(value = "firstName") String firstname,
            @RequestParam(value = "lastName") String lastname,
            @RequestParam(value = "birthday") String birthday,
            @RequestParam(value = "employeeType") String type,
            
            @RequestParam(value = "id") long id, 
           
            Model model) {
        
        Department department = departmentService.findById(id);
        
        departmentService.saveEmployee(firstname, lastname, birthday, type, department);
        //model.addAttribute(eId);
   
        return "redirect:departmentPage/"+id;
    
}
    
    @RequestMapping(value = "editDepartment", method = RequestMethod.GET)
     public String editDepartment(
            
            long id, Model model ) {
        
        Department department = departmentService.findById(id);
        
        model.addAttribute(department);
  
        
        return "editDepartment";
    
    }
    
    @RequestMapping(value = "editDepartment", method = RequestMethod.POST)
     public String saveEditDepartment(
            @RequestParam(value = "departmentName") String name,
            
            @RequestParam(value = "departmentType") String type,
            long id, Model model ) {
        
        Department department = departmentService.findById(id);
        departmentService.updateDepartment(name, type, department);
       // model.addAttribute(department);
  
        
        return "redirect:departmentPage/"+id;
    
    }
    
    @RequestMapping(value = "deleteDepartment/{id}", method = RequestMethod.GET)
     public String deleteDepartment(
        
           @PathVariable long id, HttpSession session ) {
        
        Department department = departmentService.findById(id);
        Enterprise enterprise = (Enterprise) session.getAttribute("sessionEnterprise");
        departmentService.deleteDepartment(department);
  
        return "redirect:/enterprisePage/"+enterprise.getId();
    
    }
     

 
    
}
