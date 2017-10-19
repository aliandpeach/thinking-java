package examples.mongo;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Set;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2014/12/7
 */
public class ConnectToMongoDB {
    MongoClient m = null;
    DB db;

    public void connect() {
        try {
            m = new MongoClient("localhost", 27017);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public void listAllCollections(String dbName) {
        if (m != null) {
            db = m.getDB(dbName);
            Set<String> collections = db.getCollectionNames();
            for (String s : collections) {
                System.out.println(s);
            }
        }
    }

    public void listLocationCollectionDocuments() {
        if (m != null) {
            db = m.getDB("prefs");
            DBCollection collection = db.getCollection("location");
            DBCursor cur = collection.find();
            while (cur.hasNext()) {
                System.out.println(cur.next());
            }
        } else {
            System.out.println("please connect db first.");
        }
    }

    public static void main(String[] args) {
        ConnectToMongoDB connectToMongoDB = new ConnectToMongoDB();
        connectToMongoDB.connect();
        connectToMongoDB.listAllCollections("prefs");
        connectToMongoDB.listLocationCollectionDocuments();
    }
}
