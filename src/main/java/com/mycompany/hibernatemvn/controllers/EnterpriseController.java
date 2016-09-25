package com.mycompany.hibernatemvn.controllers;

import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.services.EnterpriseService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
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
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @RequestMapping(value = "enterprises", method = RequestMethod.GET)
    public String showEnterprises(ModelMap model) {
       /*try {
            Class.forName("com.mycompany.hibernatemvn.DB.Entities.Enterprise");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EnterpriseController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        List<Enterprise> enterpriseList = enterpriseService.findAll();
        model.addAttribute("enterpriseList", enterpriseList);
        

        return "enterprise";
    }

    @RequestMapping(value = "enterprisePage/{id}", method = RequestMethod.GET)
    public String getEnterprisePage(
            @PathVariable long id, ModelMap model, HttpServletRequest request) {
        Enterprise enterprise = enterpriseService.findById(id);

        model.addAttribute(enterprise);
        HttpSession session = request.getSession(true);
        session.setAttribute("sessionEnterprise", enterprise);
        return "enterprisePage";
    }

    @RequestMapping(value = "createEnterprise", method = RequestMethod.POST)
    public String createEnterprise(
            @RequestParam(value = "enterpriseName") String name,
            @RequestParam(value = "foundationDate") String date,
            @RequestParam(value = "enterpriseType") String type, ModelMap model) {

        enterpriseService.saveEnterprise(name, date, type);

        return "redirect:enterprises";
    }

    @RequestMapping(value = "createDepartment", method = RequestMethod.POST)
    public String createDepartment(
            @RequestParam(value = "departmentName") String name,
            @RequestParam(value = "departmentType") String type,
            @RequestParam(value = "id") long id,
            Model model) {
        Enterprise enterprise = enterpriseService.findById(id);
        enterpriseService.saveDepartment(name, type, enterprise);


        return "redirect:enterprisePage/" + id;

    }

    @RequestMapping(value = "editEnterprise", method = RequestMethod.GET)
    public String editEnterprise(
            @RequestParam(value = "id") long id, Model model) {

        Enterprise enterprise = enterpriseService.findById(id);

        model.addAttribute(enterprise);


        return "editEnterprise";

    }

    @RequestMapping(value = "editEnterprise", method = RequestMethod.POST)
    public String saveEditEnterprise(
            @RequestParam(value = "enterpriseName") String name,
            @RequestParam(value = "foundationDate") String date,
            @RequestParam(value = "enterpriseType") String type,
            @RequestParam(value = "id") long id, Model model) {

        Enterprise enterprise = enterpriseService.findById(id);
        enterpriseService.updateEnterprise(name, date, type, enterprise);
        //model.addAttribute(enterprise);


        return "redirect:enterprisePage/" + id;

    }

    @RequestMapping(value = "deleteEnterprise", method = RequestMethod.GET)
    public String deleteEnterprise(
            long id) {

        Enterprise enterprise = enterpriseService.findById(id);

        enterpriseService.deleteEnterprise(enterprise);


        return "redirect:enterprises";

    }
}
