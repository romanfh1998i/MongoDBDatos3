

import com.mongodb.*;

import java.net.UnknownHostException;

public class MongoDB {

    public static void main(String[] args) throws UnknownHostException {

        User user = createUser();
        DBObject doc = createDBObject(user);

        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("journaldev");

        DBCollection col = db.getCollection("users");

        //create user
        WriteResult result = col.insert(doc);
        System.out.println(result.getUpsertedId());
        System.out.println(result.getN());
        System.out.println(result.isUpdateOfExisting());
     //   System.out.println(result.getLastConcern());

        //read example
        DBObject query = BasicDBObjectBuilder.start().add("_id", user.getId()).get();
        DBCursor cursor = col.find(query);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

        //update example
        user.setName("Pankaj Kumar");
        doc = createDBObject(user);
        result = col.update(query, doc);
        System.out.println(result.getUpsertedId());
        System.out.println(result.getN());
        System.out.println(result.isUpdateOfExisting());
       // System.out.println(result.getLastConcern());

        //delete example
        result = col.remove(query);
        System.out.println(result.getUpsertedId());
        System.out.println(result.getN());
        System.out.println(result.isUpdateOfExisting());
       // System.out.println(result.());

        //close resources
        mongo.close();
    }

    private static DBObject createDBObject(User user) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("_id", user.getId());
        docBuilder.append("name", user.getName());
        docBuilder.append("role", user.getRole());
        docBuilder.append("isStudent", user.isStudent());
        return docBuilder.get();
    }

    private static User createUser() {
        User u = new User();
        u.setId(2);
        u.setName("Franco");
        u.setStudent(true);
        u.setRole("Student");
        return u;
    }
}