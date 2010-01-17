package uk.ac.cam.md481.fjava.tick5;

import java.sql.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Date;
import uk.ac.cam.cl.fjava.messages.RelayMessage;

public class Database {
  private Connection connection;
  public Database(String databasePath) throws SQLException, ClassNotFoundException {
    Class.forName("org.hsqldb.jdbcDriver");
    this.connection = DriverManager.getConnection("jdbc:hsqldb:file:" + databasePath, "SA", "");
    
    Statement statement = this.connection.createStatement();
    try {
      statement.execute("SET WRITE_DELAY FALSE"); // Always update data on disk
    } finally {
      statement.close();
    }
    
    this.connection.setAutoCommit(false);
    
    statement = connection.createStatement();
    try {
     statement.execute("CREATE TABLE messages(nick VARCHAR(255) NOT NULL," +
                       "message VARCHAR(4096) NOT NULL,timeposted BIGINT NOT NULL)");
    } catch(SQLException e) {
      System.out.println("Warning: Database table \"messages\" already exists.");
    } finally {
     statement.close();
    }
    
    statement = connection.createStatement();
    try {
      statement.execute("CREATE TABLE statistics(key VARCHAR(255),value INT)");
    } catch(SQLException e){
      System.out.println("Warning: Database table \"statistics\" already exists.");
    } finally {
      statement.close();
    }
    
    this.connection.commit();
  }
  
  public void close() throws SQLException {
    this.connection.close();
  }
  
  public void incrementLogins() throws SQLException {
    Statement statement = this.connection.createStatement();
    try {
      statement.execute("UPDATE statistics SET value = value + 1 WHERE key = 'Total logins'");
    } finally {
      statement.close();
    }
    this.connection.commit();
  }
  
  public void addMessage(RelayMessage m) throws SQLException {
    String sql = "INSERT INTO MESSAGES(nick, message, timeposted) VALUES (?,?,?)";
    PreparedStatement insertStatement = connection.prepareStatement(sql);
    try {
      insertStatement.setString(1, m.getFrom());
      insertStatement.setString(2, m.getMessage());
      insertStatement.setLong(3, m.getTime().getTime());
      insertStatement.executeUpdate();
    } finally {
      insertStatement.close();
    }
    
    Statement updateStatement = this.connection.createStatement();
    try {
      updateStatement.execute("UPDATE statistics SET value = value + 1 WHERE key = 'Total messages'");
    } finally {
      updateStatement.close();
    }
    
    this.connection.commit();
  }
  
  public List<RelayMessage> getRecent() throws SQLException {
    LinkedList<RelayMessage> messages = new LinkedList<RelayMessage>();
    
    String sql = "SELECT nick, message, timeposted FROM messages ORDER BY timeposted DESC LIMIT 10";
    PreparedStatement statement = connection.prepareStatement(sql);
    
    try {
      ResultSet results = statement.executeQuery();
      try {
        while (results.next())
          messages.addFirst(new RelayMessage(results.getString(1), results.getString(2), new Date(results.getLong(3))));
      } finally {
        results.close();
      }
    } finally {
      statement.close();
    }
    
    return (List<RelayMessage>) messages;
  }
    
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    if(args.length == 0){
      System.err.println("Usage: java uk.ac.cam.md481.fjava.tick5.Database <database name>");
      return;
    }
    
    Class.forName("org.hsqldb.jdbcDriver");
    Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:" + args[0], "SA", "");

    Statement delayStmt = connection.createStatement();
    try {
      delayStmt.execute("SET WRITE_DELAY FALSE"); // Always update data on disk
    } finally {
      delayStmt.close();
    }
    
    connection.setAutoCommit(false);
    
    Statement sqlStmt = connection.createStatement();
    try {
     sqlStmt.execute("CREATE TABLE messages(nick VARCHAR(255) NOT NULL," + 
                     "message VARCHAR(4096) NOT NULL,timeposted BIGINT NOT NULL)");
    } catch(SQLException e) {
     System.out.println("Warning: Database table \"messages\" already exists.");
    } finally {
     sqlStmt.close();
    }
    
    String stmt = "INSERT INTO MESSAGES(nick,message,timeposted) VALUES (?,?,?)";
    PreparedStatement insertMessage = connection.prepareStatement(stmt);
    try {
      insertMessage.setString(1, "Alastair"); //set value of first "?" to "Alastair"
      insertMessage.setString(2, "Hello, Andy");
      insertMessage.setLong(3, System.currentTimeMillis());
      insertMessage.executeUpdate();
    } finally { //Notice use of finally clause here to finish statement
      insertMessage.close();
    }
    
    connection.commit();
    
    stmt = "SELECT nick,message,timeposted FROM messages ORDER BY timeposted DESC LIMIT 10";
    PreparedStatement recentMessages = connection.prepareStatement(stmt);
    try {
      ResultSet rs = recentMessages.executeQuery();
      try {
        while (rs.next())
          System.out.println(rs.getString(1) + ": " + rs.getString(2) + " [" + rs.getLong(3) + "]");
      } finally {
        rs.close();
      }
    } finally {
      recentMessages.close();
    }
    
    connection.close();
  }
}