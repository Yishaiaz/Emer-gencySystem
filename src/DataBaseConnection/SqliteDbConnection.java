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
                    row.put(md.getColumnName(i),rs.getObject(i).toString());
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
    public void updateEntry(String tableName, String[] columnValues, String primaryKeyValue, String primaryKeyName) throws Exception {
        String fieldValuesForSql=createSqlStringForEditing(tableName, columnValues);

        this.connectToDb();

        String sql = "UPDATE "+tableName+ " SET "+fieldValuesForSql+" WHERE "+primaryKeyName+"='"+primaryKeyValue+"';";

        try (Connection conn = this.conn;
             Statement stmt  = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public void deleteAllFromTable(String tableName) throws Exception {
        this.connectToDb();

        String sql = "DELETE FROM "+tableName;

        try (Connection conn = this.conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteDb(String dbName) throws Exception {
        throw new Exception("Not Implemented");
    }

    @Override
    public void deleteById(String tableName, String primaryKeyValue, String primaryKeyName) throws Exception {
        this.connectToDb();

        String sql = "DELETE FROM "+tableName+" WHERE "+primaryKeyName+" = " +"'"+primaryKeyValue+"';";

        try (Connection conn = this.conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Map<String, String> getEntryData(String tableName, String primaryKeyValue, String primaryKeyName) throws Exception {

        this.connectToDb();

        Map<String, String> ans=new HashMap<>();

        String sql = "SELECT * FROM "+tableName+" WHERE "+primaryKeyName+" = '"+primaryKeyValue+"';";

        try (Connection tempConn = this.conn;
             Statement stmt  = tempConn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()){
                for(int i=1; i<=columns; ++i){
                    ans.put(md.getColumnName(i),rs.getObject(i).toString());
                }
            }

            return ans;

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void closeConnection() throws Exception {
        try {
            if (this.conn != null) {
                this.conn.close();
            }

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteTable(String tableName) throws Exception {

        String sql = "DROP TABLE "+tableName+";";

        try (Connection tempConn = this.conn;
             Statement stmt  = tempConn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){


        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    private String convertArrayToSingleStr(String[] array){
        String ans ="";
        for(String str: array){
            ans = ans + "'" +str + "'"+ ',';
        }
        ans = ans.substring(0,ans.length()-1);
        return ans;
    }
    private String createSqlStringForEditing(String tableName,String[] newValues) throws Exception{
        String ans="";
        int i=0;
        String[] columns = getColumnsTitles(tableName);
        for (String value :
                newValues) {
            ans+=columns[i]+"='"+value+"',";
            i++;
        }
        return ans.substring(0,ans.length()-1);
    }
    private String[] getColumnsTitles(String tableName) throws Exception{
        String sql = "SELECT * FROM "+tableName;
        this.connectToDb();

        try (Connection tempConn = this.conn;
             Statement stmt  = tempConn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            int j =0;
            String[] ans = new String[columns];
            for(int i=1; i<=columns; ++i){
                ans[j] = md.getColumnName(i);
                j++;
            }
            return ans;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}