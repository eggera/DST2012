package dst1.mongodb;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.*;

public class MongoDBUtil {

	private Mongo mongoDB;
	private DB db;
	
	public void init() {

		try {
			mongoDB = new Mongo();
			db = mongoDB.getDB("dst_m");
			
			System.out.println("all databases: ");
			for(String dbname : mongoDB.getDatabaseNames())
				System.out.println(dbname);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void freeResources() {
		mongoDB.close();
	}
	
	public void insert() {
		
		Set<String> collections = db.getCollectionNames();
		for(String coll : collections)
			System.out.println("name: "+coll);
				
		DBCollection collection = db.getCollection("testCollection");
		
		BasicDBObject doc = new BasicDBObject();
		
		doc.put("name", "mongodb");
		doc.put("type", "database");
		doc.put("count", 1);
		
		BasicDBObject info = new BasicDBObject();
		
		info.put("val1", 123);
		info.put("val2", 456);
		
		doc.put("info", info);
		
		collection.insert(doc);
		
		for(int i = 0; i < 100; ++i) 
			collection.insert(new BasicDBObject().append("i", i));
		
		System.out.println("Inserted "+collection.getCount()+" elements");
		
	}
	
	public void findFirst() {
		
		DBCollection collection = db.getCollection("testCollection");
		DBObject myDoc = collection.findOne();
		System.out.println("myDoc: "+myDoc);
	}
	
	public void findAll() {
		DBCollection collection = db.getCollection("testCollection");
		DBCursor cursor = collection.find();
		while(cursor.hasNext())
			System.out.println(cursor.next());
	}
	
	public void basicQuery() {
		BasicDBObject query = new BasicDBObject();
		DBCollection collection = db.getCollection("testCollection");
		
		query.put("i", 71);
		DBCursor cursor = collection.find(query);
		
		while(cursor.hasNext())
			System.out.println(cursor.next());
	}
	
	public void anotherQuery() {
		BasicDBObject query = new BasicDBObject();
		DBCollection collection = db.getCollection("testCollection");
		
		System.out.println("coll count : "+collection.getCount());
		
		collection.createIndex(new BasicDBObject("i", -1));
		List<DBObject> indexList = collection.getIndexInfo();
		
		for(DBObject obj : indexList) 
			System.out.println(obj);

		query.put("i", new BasicDBObject("$gt", 10).append("$lte", 20));
		
		DBCursor cursor = collection.find(query);
		
		while(cursor.hasNext())
			System.out.println(cursor.next());
	}
	
	public void dropDB() {
		try {
			mongoDB = new Mongo();
			db = mongoDB.getDB("dst_m");
			db.dropDatabase();
			mongoDB.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
}
