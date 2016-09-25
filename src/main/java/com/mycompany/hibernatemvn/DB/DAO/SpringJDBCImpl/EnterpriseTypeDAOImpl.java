/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.SpringJDBCImpl;

import com.mycompany.hibernatemvn.DB.DAO.EnterpriseTypeDAO;
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
import org.springframework.stereotype.Repository;

/**
 *
 * @author Denis
 */
//@Repository()
public class EnterpriseTypeDAOImpl implements EnterpriseTypeDAO {

    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) AS Count FROM EnterpriseType";
    private static final String QUERY_SELECT_ALL_FROM_TABLE = "SELECT * FROM EnterpriseType";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @PostConstruct
    public void init() {
        simpleJdbcInsert.withTableName("EnterpriseType").usingGeneratedKeyColumns("id");
    }

    @Override
    public long getCount() {

        return jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);

    }

    @Override
    public void save(EnterpriseType type) {
        Map params = new LinkedHashMap();
        params.put("name", type.getName());
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        type.setId((Long) id);

    }

    @Override
    public void update(EnterpriseType type) {

        String query = "UPDATE EnterpriseType SET name=? WHERE id=?;";

        jdbcTemplate.update(query, new Object[]{type.getName(),
            type.getId()
        });

    }

    @Override
    public void delete(EnterpriseType type) {

        String query = "DELETE FROM EnterpriseType WHERE id = ?";

        jdbcTemplate.update(query, new Object[]{type.getId()});

    }

    @Override
    public List<EnterpriseType> findAll() {
        String query = QUERY_SELECT_ALL_FROM_TABLE;

        return jdbcTemplate.query(query, new EnterpriseTypeRowMapper());
    }

    @Override
    public EnterpriseType findById(long id) {

        String query = QUERY_SELECT_ALL_FROM_TABLE + " where id = ?";

        return jdbcTemplate.queryForObject(query, new Object[]{id}, new EnterpriseTypeRowMapper());
    }

    @Override
    public List<EnterpriseType> findByName(String name) {
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
            EnterpriseType savedEntity = findById(id);

            delete(savedEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteAll(Collection<EnterpriseType> entities) {

        if (!entities.isEmpty()) {

            for (EnterpriseType entity : entities) {

                if (entity != null) {
                    delete(entity);
                } else {
                    //Entity is not found
                }
            }
        }
    }

    @Override
    public List<EnterpriseType> getSorted(String propertySortBy, boolean asc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static final class EnterpriseTypeRowMapper
            implements RowMapper<EnterpriseType> {

        @Override
        public EnterpriseType mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");

            return new EnterpriseType(id, name);

        }
    }
}
