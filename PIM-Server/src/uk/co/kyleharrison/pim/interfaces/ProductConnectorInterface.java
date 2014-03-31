package uk.co.kyleharrison.pim.interfaces;

public interface ProductConnectorInterface {

	public boolean addComic();
	public boolean addDvd();
	public boolean addCd();
	public boolean addBook();
	public boolean addGame();
	public boolean addOther();
	
	public boolean findComic();
	public boolean findDvd();
	public boolean findCd();
	public boolean findBook();
	public boolean findGame();
	public boolean findOther();
	
	public boolean compareComic();
	public boolean compareDvd();
	public boolean compareCd();
	public boolean compareBook();
	public boolean compareGame();
	public boolean compareOther();
	
	public boolean updateComic();
	public boolean updateDvd();
	public boolean updateCD();
	public boolean updateBook();
	public boolean updateGame();
	public boolean updateOther();
	
	public boolean removeComic();
	public boolean removeDvd();
	public boolean removeCd();
	public boolean removeBook();
	public boolean removeGame();
	public boolean removeOther();
	
	
}
