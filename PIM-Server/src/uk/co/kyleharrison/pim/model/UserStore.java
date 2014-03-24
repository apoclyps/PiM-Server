package uk.co.kyleharrison.pim.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.kyleharrison.pim.utilities.SHAEncrypt;

public class UserStore extends User {

	private String followees = "0";
	private String followers = "0";
	private String joined;
	private boolean loggedIn = false;
	private String inventory = "0";
	private boolean created = false;
	private boolean exists = false;

	public UserStore() {
		super();
	}

	public boolean encryptPassword(){
		this.password = SHAEncrypt.encrypt(this.password);
		if(this.password!=null)
			return true;
		else
			return false;
	}
	
	public String getFollowees() {
		return followees;
	}

	public void setFollowees(String followees) {
		this.followees = followees;
	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}

	public String getJoined() {
		return joined;
	}

	public void setJoined() {
		Date now = new Date();
		this.joined = new SimpleDateFormat("dd MMMMM yyyy").format(now);
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	
	@Override
	public String toString(){
		return "{ \"username\" : \""+this.username+"\",\"password\" : \""+this.password+"\"}";
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

}
