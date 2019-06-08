package DataBaseConnection;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * SqliteDbConnection is an implementation of IdbConnection interface.
 * communicates with a local database, which it's name and location is configured in resources/config.properties.
 *
 * contains two private fields - props which is the properties of the data base from the said configuration file.
 * conn = the Connection object to the db iteslef.
 *
 * this class uses the sqlite-jdbc-3.23.1 drivers module in order to communicate with a database.
 *
 * for more thorough documentation on the function of the class:
 * @see IdbConnection
 *
 */

public class SqliteDbConnection implements IdbConnection {

    private Properties props;
    private Connection conn;
    private static SqliteDbConnection instance =null;

    private SqliteDbConnection() throws Exception{
        props = new Properties();
        String propFileName = "config.properties";
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                props.load(inputStream);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            if(props!=null){

                String url = props.getProperty("dbUrl");

                Connection conn = DriverManager.getConnection(url) ;
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }
            }
            this.instance = this;
        }
    }
    public static SqliteDbConnection getInstance() throws Exception {
        if(instance!=null){
            return instance;
        }
        instance = new SqliteDbConnection();
        return instance;
    }

    @Override
    public void connectToDb() throws Exception {
        conn = null;
        try {
            // db parameters
            String url = this.props.getProperty("dbUrl");
            // create a connection to the database
            conn = DriverManager.getConnection(url, "yishaiazabary", "Yi82Za65");

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void createNewTable(String tableName, String[] columnTitles, String primaryKeyName) throws Exception {
        this.connectToDb();

        // SQLite connection string

        String tableColumnsSql ="" ;

        //creating the sql string part with all the the column titles
        for (String str :
                columnTitles) {
            tableColumnsSql += str + " text" + " NOT NULL,\n";
        }

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" ("
                +tableColumnsSql
                + "PRIMARY KEY ("+primaryKeyName+"));";

        try (Connection tempConn=this.conn;
             Statement stmt = tempConn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("//////////FOR DEBUGGING////////dont forget to connect to db! ");
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public LinkedList<Map<String, String>> getAllFromTable(String tableName) throws Exception {
        this.connectToDb();

        LinkedList<Map<String, String>> ans=new LinkedList<Map<String, String>>();

        Map<String, String> singleEntryMap = new HashMap<>();

        String sql = "SELECT * FROM "+tableName;

        try (Connection tempConn = this.conn;
             Statement stmt  = tempConn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()){
                HashMap row = new HashMap(columns);
                for(int i=1; i<=columns; ++i){
                    row.put(md.getColumnName(i),rs.getObject(i));
                }
                ans.add(row);
            }

            return ans;

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void insert(String tableName, String[] columnValues) throws Exception {
        this.connectToDb();


//        String fieldNamesForSql=convertArrayToSingleStr(columnValues);

        String fieldValuesForSql=convertArrayToSingleStr(columnValues);

        String sql = "INSERT INTO "+tableName+ " VALUES("+fieldValuesForSql+")";

        try (Connection conn = this.conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void updateEntry(String tableName, String[] columnValues, String primaryKey) throws Exception {

    }

    @Override
    public void deleteAllFromTable(String tableName) throws Exception {

    }

    @Override
    public void deleteDb(String dbName) throws Exception {

    }

    @Override
    public void deleteById(String tableName, String primaryKey) throws Exception {

    }

    @Override
    public Map<String, String> getEntryData(String tableName, String primaryKey) throws Exception {
        return null;
    }

    @Override
    public void closeConnection() throws Exception {

    }

    @Override
    public void deleteTable(String tableName) throws Exception {

    }

    private String convertArrayToSingleStr(String[] array){
        String ans ="";
        for(String str: array){
            ans = ans + "'" +str + "'"+ ',';
        }
        ans = ans.substring(0,ans.length()-1);
        return ans;
    }
}
//
//public class SqliteDbConnection implements IdbConnection {

//
//    @Override
//    public void insert(IEntry entry) throws Exception{

//    }
//
//    @Override
//    public String[] getEntryById(String entryId, IEntry entry)throws Exception {
//        this.connectToDb();
//        entryId="'"+entryId+"'";
//
//        String[] ans=null;
//
//        if (conn == null) {
//            System.out.println("you have to connect to the DB first, use [dbInstance].connectToDb() function");
//        }
//        else{
//            ans=new String[entry.getColumnsTitles().length];
//            String sql = "SELECT * FROM "+entry.getTableName()+" WHERE "+entry.getIdentifiers()+"="+entryId+";";
//            String[] columnsNames= entry.getColumnsTitles();
//            try (Connection tempConn = this.conn;
//                 Statement stmt  = tempConn.createStatement();
//                 ResultSet rs    = stmt.executeQuery(sql)){
//                ans[0]=rs.getString(entry.getIdentifiers());
//                for (int i = 0; i < columnsNames.length; i++) {
//                    ans[i]=rs.getString(columnsNames[i]);
//                }
//            } catch (SQLException e) {
////                System.out.println(e.getMessage());
//                throw new Exception(e.getMessage());
//            }
//            return ans;
//        }
//        return null;
//    }
//
//
//    @Override
//    public void deleteAllFromTable(String tableName)throws Exception {
//        this.connectToDb();
//
//        String sql = "DELETE FROM "+tableName;
//
//        try (Connection conn = this.conn;
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            // execute the delete statement
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
////            System.out.println(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteDb(String dbName) throws Exception{
//        throw new NotImplementedException();
//    }
//
//    @Override
//    public void updateEntry(IEntry entry, String[] newValues)throws Exception {
//        this.connectToDb();
//
//        String fieldNamesForSql=createSqlStringForEditing(entry,newValues);
//        //validate function and
//        int i=0;
//
//        String sql = "UPDATE "+entry.getTableName()+" SET "+fieldNamesForSql
//                + " WHERE "+entry.getIdentifiers()+"='"+entry.getIdentifierValue()+"';";
//
//        try (Connection tempConn = this.conn;
//             PreparedStatement pstmt = tempConn.prepareStatement(sql)) {
//            // update
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
////            System.out.println(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteById(IEntry entry) throws Exception{
//        this.connectToDb();
//
//        String sql = "DELETE FROM "+entry.getTableName()+" WHERE "+entry.getIdentifiers()+" = " +"'"+entry.getIdentifierValue()+"';";
//
//        try (Connection conn = this.conn;
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            // execute the delete statement
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
////            System.out.println(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//    }
//
//
//    @Override
//    public LinkedList<String[]> getAllFromTable(IEntry entry) throws Exception{
//
//    }
//
//    @Override
//    public ArrayList<String> getSpecificData(IEntry entry, String entryId, String[] namesOfSpecificField)throws Exception {
//        ArrayList<String> ans=new ArrayList<>();
//        this.connectToDb();
//        entryId="'"+entryId+"'";
//
////        String[] allEntryData=getEntryById(entryId,entry);
//        if (conn == null) {
//            System.out.println("you have to connect to the DB first, use [dbInstance].connectToDb() function");
//        }
//        else{
//            String sql = "SELECT * FROM "+entry.getTableName()+" WHERE "+entry.getIdentifiers()+"="+entryId+";";
//            String[] columnsNames= entry.getColumnsTitles();
//            try (Connection tempConn = this.conn;
//                 Statement stmt  = tempConn.createStatement();
//                 ResultSet rs    = stmt.executeQuery(sql)){
//                for (int i = 0; i < namesOfSpecificField.length; i++) {
//                    ans.add(rs.getString(namesOfSpecificField[i]));
//                }
//            } catch (SQLException e) {
////                System.out.println(e.getMessage());
//                throw new Exception(e.getMessage());
//            }
//            return ans;
//        }
//
//
//        return null;
//    }
//
//    @Override
//    public void closeConnection() throws Exception{
//        try {
//            if (this.conn != null) {
//                this.conn.close();
//            }
//
//        } catch (SQLException e) {
////            System.out.println(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//    }
//
//
//    /**
//     * a private function to create a single string, suitable for Sqlite queries.
//     * @param entry - IEntry
//     * @return String, the names of the columns separated by a comma
//     */
//    private String createSqlStringColumns(IEntry entry){
//        String ans="" ;
//        for (String s:entry.getColumnsTitles()
//        ) {
//            ans+=s+",";
//        }
//        return ans.substring(0,ans.length()-1);
//    }
//
//    /**
//     * a private function to create a single string, suitable for Sqlite queries.
//     * @param entry - IEntry
//     * @return String, the values of all the fields of the given entry
//     * @throws Exception - an SQL type exception
//     */
//    private String createSqlStringValues(IEntry entry) {
//        String ans="" ;
//        for (String s:entry.getAllData()
//        ) {
//            ans+="'"+s+"',";
//        }
//        return ans.substring(0,ans.length()-1);
//    }
//
//    /**
//     * a private function to create a single string, suitable for Sqlite queries.
//     * this returns a string in the following structure: "[nameOField]=[ValueOfField], [nameOField]=[ValueOfField] ,....."
//     * @param entry - IEntry
//     * @param newValues - String[] - all the new values, include all values, including ones who don't need to change.
//     * @return
//     */
//    private String createSqlStringForEditing(IEntry entry,String[] newValues){
//        String ans="";
//        int i=0;
//        String[] columns = entry.getColumnsTitles();
//        for (String value :
//                newValues) {
//            ans+=columns[i]+"='"+value+"',";
//            i++;
//        }
//        return ans.substring(0,ans.length()-1);
//    }
//
//    @Override
//    public void createNewTable(String tableName, String[] columnTitles, String identifier) throws Exception {
//        this.connectToDb();
//
//        // SQLite connection string
//
//        String tableColumnsSql ="" ;
//
//        //creating the sql string part with all the the column titles
//        for (String str :
//                columnTitles) {
//            tableColumnsSql += str + " text" + " NOT NULL,\n";
//        }
//
//        // SQL statement for creating a new table
//        String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" ("
//                +tableColumnsSql
//                + "PRIMARY KEY ("+identifier+"));";
//
//        try (Connection tempConn=this.conn;
//             Statement stmt = tempConn.createStatement()) {
//            // create a new table
//            stmt.execute(sql);
//        } catch (SQLException e) {
//            System.out.println("//////////FOR DEBUGGING////////dont forget to connect to db! ");
////            System.out.println(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//
//
//    }
//
//    @Override
//    public void insertToDbByTableName(String tablename, IEntry entry) throws Exception{
//        this.connectToDb();
//
//        String fieldNamesForSql=createSqlStringColumns(entry);
//
//        String fieldValuesForSql=createSqlStringValues(entry) ;
//
//        String sql = "INSERT INTO "+tablename+"("+fieldNamesForSql+") VALUES("+fieldValuesForSql+")";
//
//        try (Connection conn = this.conn;
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
////            System.out.println(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//    }
//}
