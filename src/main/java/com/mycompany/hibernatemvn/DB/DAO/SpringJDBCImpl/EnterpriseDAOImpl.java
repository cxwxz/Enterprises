/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringJDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.EnterpriseDAO;
import com.mycompany.hibernatemvn.DB.Entities.Department;
import com.mycompany.hibernatemvn.DB.Entities.DepartmentType;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
//@Repository()
public class EnterpriseDAOImpl implements EnterpriseDAO {

    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) AS Count FROM Enterprise";
    private static final String QUERY_DELETE_ENTITY = "DELETE from %s WHERE id=?";
    private static final String QUERY_DELETE_UNEMPLOYED = "delete from employee where id not in (select employeeid from employees_departments)";
    private static final String QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS =
            "SELECT E.id, E.foundationDate, E.info, E.name, ET.id, ET.name, D.id, D.departmentName, DT.id, DT.name FROM Enterprise as E "
            + "LEFT JOIN enterprisetype as ET "
            + "ON E.Enterprisetypeid = ET.id "
            + "LEFT JOIN department as D "
            + "ON E.id = D.Enterpriseid "
            + "LEFT JOIN departmentType as DT "
            + "ON D.departmenttypeid = DT.id";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @PostConstruct
    public void init() {
        simpleJdbcInsert.withTableName("Enterprise").usingGeneratedKeyColumns("id");
    }

    @Override
    public long getCount()  {

        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);

    }

    @Override
    public void save(Enterprise enterprise) {
        Map params = new LinkedHashMap();
       
        params.put("foundationDate", new java.sql.Date(enterprise.getFoundationDate().getYear(), enterprise.getFoundationDate().getMonth(), enterprise.getFoundationDate().getDay()));
        params.put("info", enterprise.getInfo());
        params.put("name", enterprise.getName());
        params.put("EnterpriseTypeID", enterprise.getType().getId());
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        enterprise.setId((Long) id);
        
    }

    @Override
    public void update(Enterprise enterprise) {
      

        String query1 = "UPDATE Enterprise SET name=?, foundationDate=?, info=?, EnterpriseTypeID=? WHERE id=?;";
        String query2 = "UPDATE Department SET EnterpriseID=? WHERE id=?;";
        jdbcTemplate.update(query1, new Object[]{enterprise.getName(),
            new java.sql.Date(enterprise.getFoundationDate().getYear(), enterprise.getFoundationDate().getMonth(), enterprise.getFoundationDate().getDay()),
            enterprise.getInfo(),
            enterprise.getType().getId(),
            enterprise.getId()});

        List<Object[]> list = new LinkedList<Object[]>();

        for (Department d : enterprise.getDepartments()) {
            list.add(new Object[]{enterprise.getId(), d.getId()});
        }

        jdbcTemplate.batchUpdate(query2, list);

    }

    @Override
    public void delete(Enterprise enterprise) {

        String query1 = "DELETE FROM employees_departments where DepartmentID = ?";
        String query2 = String.format(QUERY_DELETE_ENTITY, "Department");
        String query3 = QUERY_DELETE_UNEMPLOYED;
        String query4 = String.format(QUERY_DELETE_ENTITY, "Enterprise");

        List<Object[]> args = new LinkedList<Object[]>();


        for (Department department : enterprise.getDepartments()) {

            args.add(new Object[]{department.getId()});

        }
        jdbcTemplate.batchUpdate(query1, args);
        jdbcTemplate.batchUpdate(query2, args);

        jdbcTemplate.update(query3);

        jdbcTemplate.update(query4, enterprise.getId());
    }

    @Override
    public List<Enterprise> findAll() {

        return jdbcTemplate.query(QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS, new EnterpriseSetExtractor());

    }

    @Override
    public Enterprise findById(long id) {
        Enterprise enterprise;

        List<Enterprise> l = jdbcTemplate.query(QUERY_SELECT_ALL_ENTERPRISES_WITH_DEPARTMENTS + " where E.id = ?", new Object[]{id}, new EnterpriseSetExtractor());
        enterprise = l.get(0);


        return enterprise;
    }

    @Override
    public List<Enterprise> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        try {
            if (id == 0) {
                throw new IllegalArgumentException(
                        "Id for entity cannot be null!");
            }
            // Getting entity by id
            Enterprise savedEntity = findById(id);

            delete(savedEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteAll(Collection<Enterprise> entities) {

        if (!entities.isEmpty()) {

            for (Enterprise entity : entities) {

                if (entity != null) {
                    delete(entity);
                } else {
                    //Entity is not found
                }
            }
        }
    }

    @Override
    public List<Enterprise> getSorted(String propertySortBy, boolean asc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static final class EnterpriseSetExtractor
            implements ResultSetExtractor<List<Enterprise>> {

        @Override
        public List<Enterprise> extractData(ResultSet rs)
                throws SQLException, DataAccessException {

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
}
