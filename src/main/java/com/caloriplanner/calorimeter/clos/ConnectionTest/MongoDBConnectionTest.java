package com.caloriplanner.calorimeter.clos.ConnectionTest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoDBConnectionTest implements CommandLineRunner {

    @Autowired
    private MongoClient mongoClient;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Get the database
            MongoDatabase database = mongoClient.getDatabase("caloriestrackker");
            // Send a ping to confirm a successful connection
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


