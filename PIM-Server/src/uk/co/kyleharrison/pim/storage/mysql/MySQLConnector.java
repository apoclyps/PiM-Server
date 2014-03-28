package uk.co.kyleharrison.pim.storage.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class MySQLConnector {
	protected Connection connection = null;
	private Statement statement = null;
	// private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private DataSource datasource;
	private String database_name = "PIM";

	public MySQLConnector() {
		System.out
				.println("MySQL Connection Opened : " + new Date().toString());
		try {

			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			this.datasource = (DataSource)envContext.lookup("jdbc/remote_jdbc_db");
			this.connection = datasource.getConnection();
			
		} catch (NameNotFoundException nnfe) {
			System.out
					.println("MySQLConnector : File not found Exception in DatabaseConnector.java");
			nnfe.printStackTrace();
		} catch (NamingException ne) {
			System.out.println("Naming Exception in DatabaseConnector.java");
			ne.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception in DatabaseConnector.java");
			e.printStackTrace();
		}
	}

	public boolean checkConnection() {
		try {
			this.connection.close();
			this.connection = datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// /*
	// public MySQLConnector() {
	// System.out.println("MySQL Connection Opened : " +new Date().toString());
	// try {
	// /*datasource = (DataSource) new InitialContext()
	// .lookup("java:/comp/env/jdbc/" + database_name);*/
	//
	// String driver = "com.mysql.jdbc.Driver";
	// Class.forName(driver);
	// String url = "jdbc:mysql://137.117.146.199:3306/comicdb";
	// connection = DriverManager.getConnection(url, "admin", "Atmitwwsu007");
	// //connection = datasource.getConnection();
	// } catch(CommunicationsException ce){
	// System.out.println("MySQL Server : Offline");
	// ce.getMessage();
	// }catch (SQLException | ClassNotFoundException e) {
	// System.out.println("SQL Exception in MySQLConnector.java");
	// //e.printStackTrace();
	// Log.info("Connection not available or login details are incorrect");
	// }
	// }
	//
	// public boolean checkConnection() {
	// try {
	// connection.close();
	// connection =
	// DriverManager.getConnection("jdbc:mysql://137.117.146.199:3306/comicdb",
	// "admin", "Atmitwwsu007");
	// } catch (SQLException e) {
	// e.printStackTrace();
	// return false;
	// }
	// return true;
	// }
	//

	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public String getDatabase_name() {
		return database_name;
	}

	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
	}

}
