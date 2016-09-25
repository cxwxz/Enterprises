/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringJDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.EmployeeTypeDAO;
import com.mycompany.hibernatemvn.DB.Entities.EmployeeType;
import com.mycompany.hibernatemvn.DB.Entities.EnterpriseType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
//@Repository()
public class EmployeeTypeDAOImpl implements EmployeeTypeDAO {

    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) AS Count FROM EmployeeType";
    private static final String QUERY_SELECT_ALL_FROM_TABLE = "SELECT * FROM EmployeeType";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @PostConstruct
    public void init() {
        simpleJdbcInsert.withTableName("EmployeeType").usingGeneratedKeyColumns("id");
    }

    @Override
    public long getCount() {

        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);

    }

    @Override
    public void save(EmployeeType type) {
        Map params = new LinkedHashMap();
        params.put("name", type.getName());
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        type.setId((Long) id);

    }

    @Override
    public void update(EmployeeType type) {

        String query = "UPDATE EmployeeType SET name=? WHERE id=?;";

        jdbcTemplate.update(query, new Object[]{type.getName(),
            type.getId()
        });

    }

    @Override
    public void delete(EmployeeType type) {

        String query = "DELETE FROM EmployeeType WHERE id = ?";

        jdbcTemplate.update(query, new Object[]{type.getId()});

    }

    @Override
    public List<EmployeeType> findAll() {
        String query = QUERY_SELECT_ALL_FROM_TABLE;

        return jdbcTemplate.query(query, new EmployeeTypeRowMapper());
    }

    @Override
    public EmployeeType findById(long id) {

        String query = QUERY_SELECT_ALL_FROM_TABLE + " where id = ?";

        return jdbcTemplate.queryForObject(query, new Object[]{id}, new EmployeeTypeRowMapper());
    }

    @Override
    public List<EmployeeType> getSorted(String propertySortBy, boolean asc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EmployeeType> findByName(String name) {
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
            EmployeeType savedEntity = findById(id);

            delete(savedEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteAll(Collection<EmployeeType> entities) {

        if (!entities.isEmpty()) {

            for (EmployeeType entity : entities) {

                if (entity != null) {
                    delete(entity);
                } else {
                    //Entity is not found
                }
            }
        }
    }

    private static final class EmployeeTypeRowMapper
            implements RowMapper<EmployeeType> {

        @Override
        public EmployeeType mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");

            return new EmployeeType(id, name);

        }
    }
}
