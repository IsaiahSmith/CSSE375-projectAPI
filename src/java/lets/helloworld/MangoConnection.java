/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

/**
 *
 * @author hamiltjc
 */
import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;



public class MangoConnection {

	private static final ServerAddress address = new ServerAddress("ds037283.mongolab.com", 37283);
	private final MongoClient mClient;
	private final MongoDatabase mDB;
	
	public MangoConnection(String database, String user, String pass)
	{
		
		List<MongoCredential> creds = new ArrayList<>();
		MongoCredential cred = MongoCredential.createCredential(user, database, pass.toCharArray());
		creds.add(cred);
		mClient = new MongoClient(address, creds);
		mDB = mClient.getDatabase(database);
	}
	
	public MongoClient getClient()
	{
		return mClient;
	}
	
	public MongoDatabase getDB()
	{
		return mDB;
	}
}
