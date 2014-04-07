package uk.co.kyleharrison.pim.storage.mysql.connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uk.co.kyleharrison.pim.interfaces.UserConnectorInterface;
import uk.co.kyleharrison.pim.model.UserStore;
import uk.co.kyleharrison.pim.storage.mysql.MySQLConnector;

public class UserConnectorMySQL extends MySQLConnector implements UserConnectorInterface {

	private PreparedStatement preparedStatement = null; 
	
	public UserConnectorMySQL() {
		super();
	}

	@Override
	public boolean addUser(UserStore user) {
		//user.encryptPassword();
			if (this.checkConnection()) {
				try {
					preparedStatement = connection
							.prepareStatement("INSERT into pim.users"
									+ "(username,password)"
									+ " values  (?,?)");
					
					preparedStatement.setString(1, user.getUsername());
					preparedStatement.setString(2, user.getPassword());

					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			} else {
				System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
			return true;
	}

	@Override
	public boolean checkUserExists(UserStore userStore) {
		boolean flag = false;
		if (this.checkConnection()) {
			try {
				preparedStatement = connection
						.prepareStatement("SELECT * FROM pim.users WHERE Username = ? LIMIT 1;");
				
				preparedStatement.setString(1, userStore.getUsername());
				ResultSet results = preparedStatement.executeQuery();
				if(results.first()){
					flag = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}

		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return flag;
	}

	@Override
	public boolean searchUserByEmail(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean searchUserByUsername(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkPasswordExists(UserStore userStore) {
		boolean flag = false;
		if (this.checkConnection()) {
			try {
				preparedStatement = connection
						.prepareStatement("SELECT * FROM pim.users WHERE Username = ? and Password = ? LIMIT 1;");
				
				preparedStatement.setString(1, userStore.getUsername());
				preparedStatement.setString(2, userStore.getPassword());
				ResultSet results = preparedStatement.executeQuery();
				System.out.println("Results : "+results.toString());
				if(results.first()){
					flag = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}

		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return flag;
	}

}

