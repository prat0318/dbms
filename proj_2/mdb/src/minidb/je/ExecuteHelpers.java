package minidb.je;

import com.sleepycat.je.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ExecuteHelpers {
    public static final boolean READ_ONLY = false;        //Temporarily made it false, insert was failing.
    public static final boolean READ_WRITE = false;

    public static File myDbEnvPath = new File("JEDB");

    public static boolean isTablePresent(Database relationDB, String relationName) {
        return isTablePresent(relationDB, relationName, new DatabaseEntry());
    }

    public static boolean isTablePresent(Database relationDB, String relationName, DatabaseEntry tempData) {

        DatabaseEntry theRelKey = null;
        try {
            theRelKey = new DatabaseEntry((relationName).getBytes("UTF-8"));
            relationDB.get(null, theRelKey, tempData, LockMode.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return tempData.getSize() != 0;
    }

    public static String getSelectData(String relationData) {
        String[] columnTypes = new String[relationData.split(",").length];
        StringBuffer displayString = new StringBuffer();
        String[] columnData = relationData.split(",");
        displayString.append(columnData[0]+ " (");
        for(int j = 1; j < columnData.length; j++) {
            columnTypes[j-1] = columnData[j].split(":")[1];
            displayString.append(columnData[j].split(":")[0]+",");
        }
        displayString.append(")");
        displayString.append("\n");

        String dbName = columnData[0]+"DB";
        try{
            ArrayList<String> tuples = ExecuteHelpers.getAllRowsOfTable(dbName, columnTypes);
            for(String s : tuples)
                displayString.append(s+"\n");
        } catch(DatabaseNotFoundException e){}
        return displayString.toString();
    }

    public static ArrayList<String> getAllRowsOfTable(String relation)
            throws DatabaseException {
        //Second param of getAllRowsOfTable is not yet implemented
        return getAllRowsOfTable(relation, new String[0]);
    }

    public static ArrayList<String> getAllRowsOfTable(String relation, String[] columnTypes)
            throws DatabaseException {
        ArrayList<String> tuples = new ArrayList<String>();
        MyDbEnv myDbEnv = new MyDbEnv();
        myDbEnv.setup(myDbEnvPath, READ_ONLY);

        // Get a cursor
        Database database = myDbEnv.getDB(relation, READ_ONLY);
        Cursor cursor = database.openCursor(null, null);

        // DatabaseEntry objects used for reading records
        DatabaseEntry foundKey = new DatabaseEntry();
        DatabaseEntry foundData = new DatabaseEntry();

        try { // always want to make sure the cursor gets closed
            while (cursor.getNext(foundKey, foundData,
                    LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                String key =  new String(foundKey.getData(), "UTF-8");
                String data =  new String(foundData.getData(), "UTF-8").replaceAll("&&",",");

                tuples.add(data);
            }
        } catch (Exception e) {
            System.err.println("Error on relation cursor:");
            System.err.println(e.toString());
            e.printStackTrace();
        } finally {
            cursor.close();
            database.close();
            myDbEnv.close();
        }
        return tuples;
    }

}
