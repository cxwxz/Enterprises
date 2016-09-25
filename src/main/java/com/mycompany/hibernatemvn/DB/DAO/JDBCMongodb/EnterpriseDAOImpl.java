/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatemvn.DB.DAO.JDBCMongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mycompany.hibernatemvn.DB.DAO.EnterpriseDAO;
import com.mycompany.hibernatemvn.DB.Entities.Enterprise;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Denis
 */
//@Repository()
public class EnterpriseDAOImpl extends GenericDAOImpl<Enterprise> implements EnterpriseDAO {

    @Override
    public List<Enterprise> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   /* @Autowired
    private MongoClient mongoClient;
    
    private DB db;
    private DBCollection personCollection;
    private MongoEnterpriseCreator mongoPersonCreator = new MongoEnterpriseCreator();
    
    @PostConstruct
    private void initDB(){
        db = mongoClient.getDB("hibernate");
        personCollection = db.getCollection("Enterprise");
    }
    
    @Override
    public void add(Enterprise enterprise) {
        DBObject enterpriseDBO = new BasicDBObject();
        enterpriseDBO.put("name", enterprise.getName());
        enterpriseDBO.put("foundationDate", enterprise.getFoundationDate());
        enterpriseDBO.put("type", enterprise.getType().getName());
        personCollection.insert(enterpriseDBO);
        enterprise.setId(enterpriseDBO.get("_id").hashCode());
    }

    @Override
    public List<Enterprise> getByName(String enterpriseName) {
        DBObject dbObject = new BasicDBObject("name",enterpriseName);
        DBCursor cursor = personCollection.find(dbObject);
        return mongoPersonCreator.formEnterprises(cursor);
    }

    @Override
    public List<Enterprise> getList() {
        DBCursor cursor = personCollection.find();
        return mongoPersonCreator.formEnterprises(cursor);
    }

    @Override
    public void delete(Enterprise enterprise) {
        DBObject document = new BasicDBObject("_id",enterprise.getId());
	personCollection.remove(document);
    }

    @Override
    public void update(Enterprise enterprise) {
        DBObject enterpriseDBO = new BasicDBObject();
        enterpriseDBO.put("name", enterprise.getName());
        enterpriseDBO.put("foundationDate", enterprise.getFoundationDate());
        enterpriseDBO.put("type", enterprise.getType().getName());
        
        DBObject searchQuery = new BasicDBObject("_id",enterprise.getId());
        
        personCollection.update(searchQuery, enterpriseDBO);
    }

    @Override
    public Enterprise findById(long id) {
        DBObject dbObject = new BasicDBObject("_id",id);
        DBCursor cursor = personCollection.find(dbObject); 
        return mongoPersonCreator.formEnterprise(cursor);
    }
    

}

class MongoEnterpriseCreator{
    public Enterprise formEnterprise(DBCursor cursor){
        Enterprise result = null;
        if (cursor.hasNext()){
            result = createEnterpriseFromCurrentCursorPosition(cursor.next());
        }

        return result;
    }

    public List<Enterprise> formEnterprises(DBCursor cursor) {
        List<Enterprise> result = new ArrayList<Enterprise>();
        while (cursor.hasNext()) {
            result.add(createEnterpriseFromCurrentCursorPosition(cursor.next()));
        }

        return result;
    }

    private Enterprise createEnterpriseFromCurrentCursorPosition(DBObject temp){
        Enterprise result = new Enterprise();
        result.setId(temp.get("_id").hashCode());
        result.setName((String) temp.get("name"));
        result.setFoundationDate((Date) temp.get("date"));
        return result;
    }
*/
}
