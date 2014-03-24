package minidb.je;

import com.sleepycat.je.*;
import mdb.AstNode;
import mdb.Equ;
import mdb.Rel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecuteHelpers {
    public static final boolean READ_ONLY = false;        //Temporarily made it false, insert was failing.
    public static final boolean READ_WRITE = false;

    public static File myDbEnvPath = new File("JEDB");
    public static ArrayList<String>[] allRelations = getAllRowsOfTable("relationDB");

    public static boolean isTablePresent(Database relationDB, String relationName) {
        return isTablePresent(relationDB, relationName, new DatabaseEntry());
    }

    public static List<String> getAllIndexes(String relationName) {
        List<String> relatedIndex =  new ArrayList<String>();
        for(String relation: allRelations[1])
            if(relation.startsWith(relationName+"."))
                relatedIndex.add(relation);
        return relatedIndex;
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

    public static ArrayList<String>[] getSelectData(String relationData, List<AstNode> clauses) {
        if(clauses == null)
            return getSelectData(relationData);
        MyDbEnv myDbEnv = new MyDbEnv();
        myDbEnv.setup(ExecuteHelpers.myDbEnvPath, READ_ONLY);
        Database relationDB = myDbEnv.getDB("relationDB", READ_ONLY);

        for(AstNode clause: clauses) {
            Rel operator = (Rel) clause.arg[1];
            String colName = clause.arg[0].toString().trim();
            String rhs = clause.arg[2].toString().trim().replaceAll(",", "&&");
            String relationName = relationData.split(",")[0];
            colName = sanitizeColumn(colName, relationName);
            if(operator instanceof Equ && isTablePresent(relationDB, colName)) {
//                System.out.println("Going to use index... " + colName);
                ArrayList<String>[] returnVal = new ArrayList[2];
                returnVal[0] = new ArrayList<String>(); returnVal[1] = new ArrayList<String>();
                returnVal[0].add(relationData);
                DatabaseEntry tempData = new DatabaseEntry();
                Database indexDB = myDbEnv.getDB(colName + "DB", READ_ONLY);
                Database indexedRelnDB = myDbEnv.getDB(relationName + "DB", READ_ONLY);
                try {
                    DatabaseEntry theRelKey = new DatabaseEntry(bytify(rhs));
                    indexDB.get(null, theRelKey, tempData, LockMode.DEFAULT);
                    if(tempData.getSize() == 0) {
//                        System.out.println("No results from index!");
                        return returnVal;
                    }
                    ByteArrayInputStream bais = new ByteArrayInputStream(tempData.getData());
                    DataInputStream in = new DataInputStream(bais);
                    while (in.available() > 0) {
                        String element = in.readUTF();
//                        System.out.println("---> : " + element);
                        returnVal[1].add(element);
                        DatabaseEntry pm_key = new DatabaseEntry(bytify(element));
                        indexedRelnDB.get(null, pm_key, tempData, LockMode.DEFAULT);
//                        System.out.println("===> : " + stringify(tempData));
                        if(tempData.getSize() != 0) returnVal[0].add(stringify(tempData));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    indexDB.close();
                    indexedRelnDB.close();
                    relationDB.close();
                    myDbEnv.close();
                }
                return returnVal;
            }
        }
        return getSelectData(relationData);
    }

    public static String sanitizeColumn(String colName, String relationName) {
        return colName.contains(".") ? colName : relationName + "." + colName;
    }

    /*
    Output: ReturnVal = [ArrayList of Data of every Row, ArrayList of IDS]
        Data : ["dept, deptno:int, chair:str",
                "3,\"CS\",\"Bruce\"",
                "3,\"CS\",\"Mike\""]
        ArrayList of Ids : ["1223232:3,\"CS\",\"Bruce\"", "12232321:3,\"CS\",\"Mike\""]
     */
    public static ArrayList<String>[] getSelectData(String relationData) {
        String[] columnTypes = new String[relationData.split(",").length];
        ArrayList<String> displayList = new ArrayList<String>();
        ArrayList[] returnVal = new ArrayList[2];
        String[] columnData = relationData.split(",");
        displayList.add(relationData);

        String dbName = columnData[0]+"DB";
        try{
            ArrayList<String> tuples[] = ExecuteHelpers.getAllRowsOfTable(dbName);
            returnVal[1] = tuples[1];
            for(String s : tuples[0])
                displayList.add(s);
        } catch(DatabaseNotFoundException e) {
            e.printStackTrace();
        }
        returnVal[0] = displayList;
        return returnVal;
    }

    public static ArrayList<String>[] getAllRowsOfTable(String relation)
            throws DatabaseException {
        //Second param of getAllRowsOfTable is not yet implemented
        return getAllRowsOfTable(relation, new String[0]);
    }

    /*
    Output: ReturnVal = [ArrayList of Data of every Row, ArrayList of IDS, ]
        Data : ["3,\"CS\",\"Bruce\"",
                "3,\"CS\",\"Mike\""]
        ArrayList of Ids : ["1223232:3,\"CS\",\"Bruce\"", "12232321:3,\"CS\",\"Mike\""]
     */
    public static ArrayList<String>[] getAllRowsOfTable(String relation, String[] columnTypes)
            throws DatabaseException {
        ArrayList<String> tuples = new ArrayList<String>();
        ArrayList<String> tuplesKey = new ArrayList<String>();
        ArrayList[] returnVal = new ArrayList[2];
        returnVal[0] = tuples; returnVal[1] = tuplesKey;
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
                String data =  new String(foundData.getData(), "UTF-8");

                tuples.add(data);
                tuplesKey.add(key);
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
        return returnVal;
    }

    public static void populateIndex(String indexName, DatabaseEntry relMetaData) {
        String rel = indexName.split("\\.")[0];
        String col = indexName.split("\\.")[1];

        List<String>[] allRows = getAllRowsOfTable(rel+"DB");

        List<String> relData = allRows[0];
        List<String> relIds = allRows[1];

        String[] columns = stringify(relMetaData).split(",");
        //find column index number
        int colNum = -1;
        for(int i = 1; i < columns.length; i++)
            if(columns[i].split(":")[0].equals(col))
                colNum = i-1;

        if(colNum == -1) System.err.println("Index Column not found!");

        Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for(int i = 0; i < relData.size(); i++) {
            String colValue = relData.get(i).split(",")[colNum];
            if(!map.containsKey(colValue))
                map.put(colValue, new ArrayList<String>());
            map.get(colValue).add(relIds.get(i));
        }

        MyDbEnv myDbEnv = new MyDbEnv();
        myDbEnv.setup(ExecuteHelpers.myDbEnvPath, READ_WRITE);

        Database insertDB = null;
        insertDB = myDbEnv.getDB(indexName + "DB", READ_WRITE);
        String somekey = "";
        try {
            for(String key: map.keySet()) {
                somekey = key;
                DatabaseEntry theKey = new DatabaseEntry((key).getBytes("UTF-8"));
                ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bOutput);
                for (String element : map.get(key)) {
                    out.writeUTF(element);
                }
                DatabaseEntry theData = new DatabaseEntry(bOutput.toByteArray());
                insertDB.put(null, theKey, theData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            insertDB.close();
            myDbEnv.close();
        }
    }

    public static String stringify(DatabaseEntry data) {
        try {
            return new String(data.getData(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] bytify(String data) {
        try {
            return (data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
