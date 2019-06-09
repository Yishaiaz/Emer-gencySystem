package Objects.event;

import DataBaseConnection.SqliteDbConnection;
import Objects.Organization.EmergencyForce;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Date;

public class EventUpdate {

    SqliteDbConnection db;
    String publishedBy;
    String event;
    String content;
    String date;
    String version;

    public EventUpdate(String publishedBy,String event,String content) throws Exception {

        db = SqliteDbConnection.getInstance();

        date = new Date().toString();
        version = "version 1";
        this.publishedBy = publishedBy;
        this.event = event;
        this.content = content;

        addToDb();


    }

    private void addToDb() throws Exception {

        String[] entry = {date, publishedBy,event,content,version};
        db.insert("Updates",entry);

    }
    public void printUpdae(){
        System.out.println("update: "+ content);
    }

}
