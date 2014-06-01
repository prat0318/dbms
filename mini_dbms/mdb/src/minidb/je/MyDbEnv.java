// file MyDbEnv.java

package minidb.je;

import com.sleepycat.je.*;

import java.io.File;

public class MyDbEnv {

    private Environment myEnv;

    // The databases that our application uses
//    private Database relationDB;
//    private Database columnDB;
//    private Database tupleDB;
//    private SecondaryDatabase itemNameIndexDb;

    // Needed for object serialization
//    private StoredClassCatalog classCatalog;

    // Our constructor does nothing
    public MyDbEnv() {}

    public Database getDB(String dbName, boolean readOnly) {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setReadOnly(readOnly);
        dbConfig.setTransactional(!readOnly);
        dbConfig.setAllowCreate(!readOnly);
        return myEnv.openDatabase(null, dbName, dbConfig);
    }

    // The setup() method opens all our databases and the environment
    // for us.
    public void setup(File envHome, boolean readOnly)
        throws DatabaseException {

        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        DatabaseConfig myDbConfig = new DatabaseConfig();
        SecondaryConfig mySecConfig = new SecondaryConfig();

        // If the environment is read-only, then
        // make the databases read-only too.
        myEnvConfig.setReadOnly(readOnly);
        myDbConfig.setReadOnly(readOnly);
        mySecConfig.setReadOnly(readOnly);

        // If the environment is opened for write, then we want to be
        // able to create the environment and databases if
        // they do not exist.
        myEnvConfig.setAllowCreate(!readOnly);
        myDbConfig.setAllowCreate(!readOnly);
        mySecConfig.setAllowCreate(!readOnly);

        // Allow transactions if we are writing to the database
        myEnvConfig.setTransactional(!readOnly);
        myDbConfig.setTransactional(!readOnly);
        mySecConfig.setTransactional(!readOnly);

        // Open the environment
        myEnv = new Environment(envHome, myEnvConfig);


        // Now open, or create and open, our databases
        // Open the relationDB, tupleDB databases

//        relationDB = myEnv.openDatabase(null,
//                                      "relationDB",
//                                       myDbConfig);

//        columnDB = myEnv.openDatabase(null,
//                                        "columnDB",
//                                         myDbConfig);
//
//        tupleDB = myEnv.openDatabase(null,
//                                        "tupleDB",
//                                         myDbConfig);

        // Open the class catalog db. This is used to
        // optimize class serialization.
//        classCatalogDb =
//            myEnv.openDatabase(null,
//                               "ClassCatalogDB",
//                               myDbConfig);

        // Create our class catalog
//        classCatalog = new StoredClassCatalog(classCatalogDb);

        // Need a tuple binding for the Inventory class.
        // We use the InventoryBinding class
        // that we implemented for this purpose.
//        TupleBinding inventoryBinding = new InventoryBinding();

        // Open the secondary database. We use this to create a
        // secondary index for the inventory database

        // We want to maintain an index for the inventory entries based
        // on the item name. So, instantiate the appropriate key creator
        // and open a secondary database.
//        ItemNameKeyCreator keyCreator =
//            new ItemNameKeyCreator(inventoryBinding);

        // Set up additional secondary properties
        // Need to allow duplicates for our secondary database
//        mySecConfig.setSortedDuplicates(true);
//        mySecConfig.setAllowPopulate(true); // Allow autopopulate
//        mySecConfig.setKeyCreator(keyCreator);

        // Now open it
//        itemNameIndexDb =
//            myEnv.openSecondaryDatabase(
//                    null,
//                    "itemNameIndex", // index name
//                    inventoryDb,     // the primary db that we're indexing
//                    mySecConfig);    // the secondary config
    }

   // getter methods

    // Needed for things like beginning transactions
    public Environment getEnv() {
        return myEnv;
    }

//    public Database getRelationDB() {
//        return relationDB;
//    }

//    public Database getTupleDB() {
//        return tupleDB;
//    }

//    public SecondaryDatabase getNameIndexDB() {
//        return itemNameIndexDb;
//    }
//
//    public StoredClassCatalog getClassCatalog() {
//        return classCatalog;
//    }

    //Close the environment
    public void close() {
        if (myEnv != null) {
            try {
                //Close the secondary before closing the primaries
//                relationDB.close();
//                tupleDB.close();
//                columnDB.close();
//                vendorDb.close();
//                inventoryDb.close();
//                classCatalogDb.close();

                // Finally, close the environment.
                myEnv.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing MyDbEnv: " +
                                    dbe.toString());
               System.exit(-1);
            }
        }
    }
}