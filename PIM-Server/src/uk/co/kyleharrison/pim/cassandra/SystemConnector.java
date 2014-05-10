package uk.co.kyleharrison.pim.cassandra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.eaio.uuid.UUID;

import uk.co.kyleharrison.pim.model.Product;

public class SystemConnector extends CassandraConnector {

	public SystemConnector() {
		super();
		this.database = "comicvine";
	}

	public boolean insertItem(Product currentProduct){
		String product = currentProduct.getId() + ",'"+currentProduct.getBarcode()+"','"+currentProduct.getName()+"'";
		String insertItemStatement = "INSERT INTO items (item_id, barcode, item_name) VALUES ("+product+");";
		System.out.println(insertItemStatement);
		
		Statement statement;
		try {
			statement = this.connection.createStatement();
			return statement.execute(insertItemStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateItem(Product currentProduct){
		String product = currentProduct.getId() + ",'"+currentProduct.getBarcode()+"','"+currentProduct.getName()+"'";
		String updateStatement = "UPDATE items SET issueid='"+currentProduct.getIssueID()+"' WHERE item_id = "+currentProduct.getId()+" AND barcode = '"+currentProduct.getBarcode()+"';";
		System.out.println(updateStatement);
		
		Statement statement;
		try {
			statement = this.connection.createStatement();
			return statement.execute(updateStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Product retrieveItem(long barcode ){
		Product foundProduct = null;

		String retrieveItemStatement = "SELECT * from items where barcode = '75960607909302011' limit 1 allow filtering;";
		System.out.println(retrieveItemStatement);
		
		Statement statement;
		ResultSet resultSet;
		try {
			statement = this.connection.createStatement();
			resultSet = statement.executeQuery(retrieveItemStatement);
			
			while (resultSet.next()) {
				foundProduct = new Product();
				foundProduct.setId(new UUID(resultSet.getString("item_id")));
				foundProduct.setBarcode(Long.parseLong( resultSet.getString("barcode"),10));
				foundProduct.setName(resultSet.getString("item_name"));
				foundProduct.setIssueID(resultSet.getString("issueID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundProduct;
	}
	
	
	
	public boolean deleteItem(Product currentProduct){
		String deleteItemStatement = "DELETE FROM items WHERE barcode = '"+currentProduct.getBarcode()+"' and item_id = "+currentProduct.getId()+";";
		System.out.println(deleteItemStatement);
		
		Statement statement;
		try {
			statement = this.connection.createStatement();
			return statement.execute(deleteItemStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
	
