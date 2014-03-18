package uk.co.kyleharrison.pim.cassandra;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;

public class ComicvineConnector extends CassandraConnector {
	
	public ComicvineConnector() {
		super();
		this.database = "ComicVine";
		this.connectionString = "jdbc:cassandra://localhost:9160/Comicvine";
	}

	public int insertVolumes(ArrayList<ComicVineVolume> volumeList) throws SQLException {
		String data = "BEGIN BATCH \n";
		
		for(ComicVineVolume cvv : volumeList){
			//System.out.println(""+cvv.getName());	
			cvv.getImage().setComicVineImages();
			//System.out.println(cvv.getImage().getCassandraMap());
			try{
				
				String name = cvv.getName().replaceAll("[()?:!.,;{}']", " ");
				System.out.println(cvv.getName().replaceAll("[()?:!.,;{}']", " "));
				data +=  "insert into volumes (id,name,count_of_issues,start_year,image,first_issue) values ("+cvv.getId()+",'"+name
						+"',"+cvv.getCount_of_issues()+","+ cvv.getStart_year() +","+cvv.getImage().getCassandraMap() +","+cvv.getFirst_issue().getCassandraMap()+") \n";
				//System.out.println("insert into comicvinevolumes (created, id,name,count_of_issues,start_year,image) values ("+java.util.UUID.fromString(new com.eaio.uuid.UUID().toString())+","+cvv.getId()+",'"+cvv.getName().replaceAll("'", " ")
				//+"',"+cvv.getCount_of_issues()+","+ cvv.getStart_year() +","+cvv.getImage().getCassandraMap() +") \n");
			}catch(Exception e){
				System.out.println("Default insert");
				data +=  "insert into volumes (id,name,count_of_issues,start_year) values ("+cvv.getId()+",'"+cvv.getName().replaceAll("'", " ")
						+"',"+cvv.getCount_of_issues()+","+ cvv.getStart_year() +") \n";
			}
		}
		data += "APPLY BATCH;";
		
		Statement st = this.connection.createStatement();
		return st.executeUpdate(data);
	}
	
}
