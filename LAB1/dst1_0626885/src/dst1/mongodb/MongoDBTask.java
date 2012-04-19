package dst1.mongodb;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.*;

public class MongoDBTask {

	private Mongo mongoDB;
	private DB db;
	
	private String map = "function() {" +
							"for(var i in this) {" +
							"	if(i == \"_id\"  ||  i == \"job_id\"  ||  i == \"last_update\") {" +
							"		continue;" +
							"	}" +
							"	var val = {};" +
							"	val[\"val\"] = 1;" +
							"	emit(i, val);" +
							"}" +
						"}";
	private String reduce = "function(key, values) {" +
							"	var out = {};" +
							"	function merge(a,b) {" +
							"		for (var k in b) { " +
							"			if (!b.hasOwnProperty(k)) { "+
							"				continue;"+ 
      						"			} " +
      						"			a[k] = (a[k] || 0) + b[k];" + 
    						"		} " +
  							"	}" +
  							"	for(var i = 0; i < values.length; i++) {" +
  							"		merge(out, values[i]);" +
  							"	}" +
  							"	return out;" +
  							"}";
	
	public static final int log_record = 1;
	public static final int matrix_record = 2;
	public static final int chromosome_record = 3;
	public static final int menu_record = 4;
	public static final int person_record = 5;
	
	public void init() {

		try {
			mongoDB = new Mongo();
			db = mongoDB.getDB("dst_m");
			
//			System.out.println("all databases: ");
//			for(String dbname : mongoDB.getDatabaseNames())
//				System.out.println(dbname);
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
		
		BasicDBList list = new BasicDBList();
		list.add("char");
		list.add(5);
		list.add(9.22209);
		
		info.put("list", list);
		
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
		DBCollection collection = db.getCollection("testCollection");
		
		System.out.println("coll count : "+collection.getCount());
		
		collection.createIndex(new BasicDBObject("i", -1));
		List<DBObject> indexList = collection.getIndexInfo();
		
		for(DBObject obj : indexList) 
			System.out.println(obj);

		BasicDBObject query = new BasicDBObject();
		query.put("i", new BasicDBObject("$gt", 10).append("$lte", 20));
		
		DBCursor cursor = collection.find(query);
		
		while(cursor.hasNext())
			System.out.println(cursor.next());
	}
	
	public void listQuery() {
		
		DBCollection collection = db.getCollection("testCollection");
		
		BasicDBList values = new BasicDBList();
		values.add(2);
		values.add(10);
		
		BasicDBObject in = new BasicDBObject("$in", values);
		
		DBCursor results = collection.find(new BasicDBObject("i", in));
		while(results.hasNext())
			System.out.println(results.next());
	}
	
	/**
	 * Create a workflow for a job with a given JobId
	 */
	public void createWorkflow(Long jobId, int workflow) {
		DBCollection workflowCollection = db.getCollection("workflows");
		
		BasicDBObject workflowObject = new BasicDBObject();
		
		workflowObject.put("job_id", jobId);
		
		switch(workflow) {
		
//		{ 
//		  "job_id" : 2,
//		  "last_updated" : 1329469221,
//		  "logs" : { 
//		    "log_set" : [ "Starting" , "Interrupted" , "Running" , "Finished"]}
//		}
		
		case log_record:
				workflowObject.put("last_update", 1329469221L);
			
				BasicDBList statusList = new BasicDBList();
				statusList.add("Starting");
				statusList.add("Interrupted");
				statusList.add("Running");
				statusList.add("Finished");
				BasicDBObject log_set = new BasicDBObject("log_set", statusList);
				
				workflowObject.put("logs", log_set);
				workflowCollection.insert(workflowObject);
				break;
				
//		{
//		  "job_id" : 3,
//		  "last_updated" :  1326830283L,
//		  "result_matrix" : { 
//		    "matrix" : [ [ 4 , 8 , 10 , 2] , [ 12 , 5 , 9 , 1] , [ 3 , 4 , 5 , 11] , [ 7 , 13 , 6 , 2]]
//		  },
//		  "type" : "integer_matrix"
//		}
		
		case matrix_record: 
				workflowObject.put("last_update", 1326830283L);
			
				BasicDBList matrix = new BasicDBList();
				BasicDBList row1 = new BasicDBList();
				BasicDBList row2 = new BasicDBList();
				BasicDBList row3 = new BasicDBList();
				BasicDBList row4 = new BasicDBList();
				
				row1.add(4);
				row1.add(8);
				row1.add(10);
				row1.add(2);
				
				row2.add(12);
				row2.add(5);
				row2.add(9);
				row2.add(1);
				
				row3.add(3);
				row3.add(4);
				row3.add(5);
				row3.add(11);
				
				row4.add(7);
				row4.add(13);
				row4.add(6);
				row4.add(2);
				
				matrix.add(row1);
				matrix.add(row2);
				matrix.add(row3);
				matrix.add(row4);
				
				workflowObject.put("result_matrix", matrix);
				workflowObject.put("type", "integer_matrix");
				
				workflowCollection.insert(workflowObject);
				break;
				
//		{
//		  "job_id" : 4,
//		  "last_updated" :  1323376688L,
//		  "alignment_block" : { 
//		    "alignment_nr" : 1,
//		    "primary" : {
//		      "chromosome" : "chr11",
//		      "start" : 3001012,
//		      "end" : 3001075
//		    },
//		    "align" : {
//		      "chromosome" : "chr13",
//		      "start" : 70568380,
//		      "end" : 70568443
//		    },
//		    "blastz" : 3500,
//		    "seq" : [
//		      "TCAGCTCATAAATCACCTCCTGCCACAAGCCTGGCCTGGTCCCAGGAGAGTGTCCAGGCTCAGA",
//		      "TCTGTTCATAAACCACCTGCCATGACAAGCCTGGCCTGTTCCCAAGACAATGTCCAGGCTCAGA"
//		     ]
//		  }
//		}

		case chromosome_record:
				workflowObject.put("last_update", 1323376688L);
			
				BasicDBObject alignmentBlock = new BasicDBObject();
				alignmentBlock.put("alignment_nr", 1);
				
				BasicDBObject primary = new BasicDBObject();
				primary.put("chromosome", "chr11");
				primary.put("start", 3001012);
				primary.put("end", 3001075);
				alignmentBlock.put("primary", primary);
				
				BasicDBObject align = new BasicDBObject();
				align.put("chromosome", "chr13");
				align.put("start", 70568380);
				align.put("end", 70568443);
				alignmentBlock.put("align", align);
				
				alignmentBlock.put("blastz", 3500);
				
				BasicDBList seqList = new BasicDBList();
				seqList.add("TCAGCTCATAAATCACCTCCTGCCACAAGCCTGGCCTGGTCCCAGGAGAGTGTCCAGGCTCAGA");
				seqList.add("TCTGTTCATAAACCACCTGCCATGACAAGCCTGGCCTGTTCCCAAGACAATGTCCAGGCTCAGA");
				alignmentBlock.put("seq", seqList);
				
				workflowObject.put("alignment_block", alignmentBlock);
				workflowCollection.insert(workflowObject);
				break;
				
		case menu_record:

				workflowObject.put("last_update", 1321738551L);
				workflowObject.put("menu_id", "file");
				workflowObject.put("value", "File");
				
				BasicDBObject popup = new BasicDBObject();
				BasicDBList menuItem = new BasicDBList();
				
				BasicDBObject item1 = new BasicDBObject();
				BasicDBObject item2 = new BasicDBObject();
				BasicDBObject item3 = new BasicDBObject();
				
				item1.put("value", "New");
				item1.put("onClick", "CreateNewDoc()");
				item2.put("value", "Open");
				item2.put("onClick", "OpenDoc()");
				item3.put("value", "Close");
				item3.put("onClick", "CloseDoc()");
				
				menuItem.add(item1);
				menuItem.add(item2);
				menuItem.add(item3);
				
				popup.put("menuitem", menuItem);
				
				workflowObject.put("popup", popup);
				workflowCollection.insert(workflowObject);
				break;
				
		case person_record:
			
//		"job_id" : 4,
//		"last_updated" :  1328097386L,
//		"alignment_block" :
//			{
//			     "firstName": "John",
//			     "lastName" : "Manson",
//			     "age"      : 30,
//			     "address"  :
//			     {
//			         "streetAddress": "405 Carter Street",
//			         "city"         : "Vidalia, LA ",
//			         "state"        : "California",
//			         "postalCode"   : "71373"
//			     },
//			     "phoneNumber":
//			     [
//			         {
//			           "type"  : "home",
//			           "number": "(714) 842-2001"
//			         },
//			         {
//			           "type"  : "fax",
//			           "number": "(715) 842-2002"
//			         }
//			     ]
//			 }
				
				workflowObject.put("last_update", 1328097386L);
			
				BasicDBObject alignment = new BasicDBObject();
				
				alignment.put("firstName", "John");
				alignment.put("lastName", "Manson");
				alignment.put("age", 30);
				
				BasicDBObject address = new BasicDBObject();
				address.put("street", "405 Carter Street");
				address.put("city", "Vidalia, LA ");
				address.put("state", "California");
				address.put("postal_code", "71373");
				alignment.put("address", address);
			
				BasicDBList phoneList = new BasicDBList();
				BasicDBObject phone1 = new BasicDBObject();
				BasicDBObject phone2 = new BasicDBObject();
				
				phone1.put("type", "home");
				phone1.put("number", "(714) 842-2001");
				
				phone2.put("type", "fax");
				phone2.put("number", "(715) 842-2002");
				
				phoneList.add(phone1);
				phoneList.add(phone2);
				
				alignment.put("phone_number", phoneList);
				workflowObject.put("alignment_block", alignment);
				workflowCollection.insert(workflowObject);
				break;
			
		default: System.out.println("Unsupported workflow");
		}
	}
	
	/**
	 * Creates an index on the property job_id
	 */
	public void createWorkflowIndex() {
		DBCollection workflowCollection = db.getCollection("workflows");
		workflowCollection.createIndex(new BasicDBObject("job_id", 1));
		
		List<DBObject> indexList = workflowCollection.getIndexInfo();
		
		System.out.println("All indices on the collection \"workflows\":");
		for(DBObject obj : indexList) 
			System.out.println(obj);
	}
	
	/**
	 * Gets the document for a given jobId
	 * @param jobId the jobId of the document to retrieve
	 */
	public void getDocumentForJob(Long jobId) {
		DBCollection workflowCollection = db.getCollection("workflows");
		
		BasicDBObject query = new BasicDBObject();
		query.put("job_id", jobId);
		DBCursor cursor = workflowCollection.find(query);
		
		while(cursor.hasNext())
			System.out.println(cursor.next());
	}
	
	/**
	 * Gets all documents that are updated after a given timestamp
	 * @param timestamp the timestamp to filter the documents
	 */
	public void getLastUpdatedAfter(Long timestamp) {
		DBCollection workflowCollection = db.getCollection("workflows");
		
		BasicDBObject query = new BasicDBObject();
		query.put("last_updated", new BasicDBObject("$gt", timestamp));
		
		// filter out the first three attributes
		BasicDBObject filter = new BasicDBObject();
		filter.put("_id", 0);
		filter.put("job_id", 0);
		filter.put("last_updated", 0);
		DBCursor cursor = workflowCollection.find(query, filter);
		while(cursor.hasNext())
			System.out.println(cursor.next());
	}
	
	public void mapReduce() {
		DBCollection collection = db.getCollection("workflows");
		
		MapReduceOutput out = collection.mapReduce(map, reduce, null, 
									MapReduceCommand.OutputType.INLINE, null);
		
		for(DBObject obj : out.results()) {
			System.out.println(obj);
		}
	}
	
	/**
	 * Prints all elements of the "workflows" collection to stdout
	 */
	public void printAllWorkflows() {
		
		DBCollection workflowCollection = db.getCollection("workflows");
		DBCursor cursor = workflowCollection.find();
		
		int count = 0;
		System.out.println("\nPrint all workflows:");
		while(cursor.hasNext()) {
			++count;
			System.out.println("workflow"+count+": "+cursor.next());
		}
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
