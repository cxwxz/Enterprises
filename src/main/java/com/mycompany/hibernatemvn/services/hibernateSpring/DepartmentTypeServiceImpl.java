/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.services.hibernateSpring;

import com.mycompany.hibernatemvn.services.DepartmentTypeService;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Denis
 */
@Service("departmentTypeService") 
@Repository 
@Transactional
public class DepartmentTypeServiceImpl implements DepartmentTypeService {

}
