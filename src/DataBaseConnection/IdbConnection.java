package DataBaseConnection;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * an interface to define the basic functions required from a database communicating module.
 * i.e a communication module with Sqlite local db, or a mongoDB JSON cloud based data base.
 * all functions are able to throw an exception according to their implementation in the inheriting class.
 * as an example,
 * @see SqliteDbConnection
 */
public interface IdbConnection {

    /**
     * connects to the DB instance, implemented in the class inheriting lvl
     * @throws Exception - an SQL type exceptio
     */
    void connectToDb() throws Exception;
    /**
     * creates a new table according to the given name of the table.
     * this receives the column titles and the identifier field of table entries in order to construct the sql query.
     * @param tableName - type String
     * @param columnTitles - type String[], the names of the fields.
     * @param primaryKeyName - the name of the identifier field of the entries.
     * @throws Exception - an SQL type exception
     */
    void createNewTable(String tableName, String[] columnTitles, String primaryKeyName) throws Exception;
    /**
     * returns all the entries in a table given one entry of the table
     * (the function uses the entry type to know from which table to get the data)
     * @param tableName - type IEntry
     * @return LinkeList of Maps of key,value
     * @throws Exception - an SQL type exception
     */
    LinkedList<Map<String, String>> getAllFromTable(String tableName) throws Exception;
    /**
     * inserts an entry to the appropriate table, if an error occurs throws an exception.
     * @param tableName - string
     * @param columnValues - string array of all the column values in the order,
     * @throws Exception - an SQL type exception
     */
    void insert(String tableName, String[] columnValues) throws Exception;//should db connection use be implemented inside an entry object?
    /**
     *
     * @param tableName - string
     * @param columnValues - string array.
     * @param primaryKey - the id of the entry to update
     * @throws Exception
     */
    void updateEntry(String tableName, String[] columnValues, String primaryKeyValue, String primaryKeyName) throws Exception;

    /**
     * deletes all data from given table, but not the table itself
     * @param tableName - String
     * @throws Exception - an SQL type exception
     */
    void deleteAllFromTable(String tableName) throws Exception;

    /**
     * Throws not implemented exception! not implemented right now.
     * @param dbName
     * @throws Exception - NotImplementedException
     */
    void deleteDb(String dbName) throws Exception;

    /**
     * deletes an entry from DB given the Entry primaryKey required for deletion.
     * @param tableName - String
     * @throws Exception - an SQL type exception
     */
    void deleteById(String tableName, String primaryKeyValue, String primaryKeyName) throws Exception;

    /**
     * returns data of specific fields of an entry, i.e a user's password.
     * it can return multiple specific data fields, when each string inside String[] namesOfSpecificFields is a field
     * it's data the user would like to receive in return.
     * if an error occurred, this function throws an SQL type exception.
     * this uses the method IEntry.getTableName() in order to get the Entry's table name.
     *
     * @param entry - type IEntry
     * @param entryId - String
     * @param namesOfSpecificField - String[]
     * @return Arraylist of Strings
     * @throws Exception - an SQL type exception
     */
    /**
     *
     * @param tableName
     * @param primaryKey
     * @return Map <key,value>
     * @throws Exception
     */
    Map<String, String> getEntryData(String tableName, String primaryKeyValue, String primaryKeyName) throws Exception;

    /**
     * closes the connection to the DB, to be used if the user doesn't need any more communications with the DB itself.
     *
     * @throws Exception - an SQL type exception
     */
    void closeConnection() throws Exception;

    void deleteTable(String tableName) throws Exception;

}