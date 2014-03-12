package uk.co.kyleharrison.pim.service.scrapper;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

/**
 * @author apoclyps
 * Kyle Harrison
 *
 */
public class AsylumService {

	private int totalPages = 5005;
	private int pageCounter = 550;
	private int pageStop = 1000;
	private int pagesSaved = 0;
	private int nextRequestDelay = 5000;
	private MySQLFacade mySQLFacade = new MySQLFacade();
	private int count = 0;
	private boolean priceFlag;
	private boolean detailsFlag;
	private boolean quantityFlag;
	private Document doc;
	private Map<String, String> entry;

	public AsylumService() {
		super();
		this.priceFlag = false;
		this.detailsFlag = false;
		this.quantityFlag = false;
		this.entry = new HashMap<String, String>();
	}
	
	public AsylumService(int start, int end) {
		super();
		this.priceFlag = false;
		this.detailsFlag = false;
		this.quantityFlag = false;
		this.entry = new HashMap<String, String>();
		this.pageCounter=start;
		this.pageStop = end;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageCounter() {
		return pageCounter;
	}

	public void setPageCounter(int pageCounter) {
		this.pageCounter = pageCounter;
	}

	public int getPageStop() {
		return pageStop;
	}

	public void setPageStop(int pageStop) {
		this.pageStop = pageStop;
	}

	public int getPagesSaved() {
		return pagesSaved;
	}

	public void setPagesSaved(int pagesSaved) {
		this.pagesSaved = pagesSaved;
	}

	public MySQLFacade getMySQLFacade() {
		return mySQLFacade;
	}

	public void setMySQLFacade(MySQLFacade mySQLFacade) {
		this.mySQLFacade = mySQLFacade;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isPriceFlag() {
		return priceFlag;
	}

	public void setPriceFlag(boolean priceFlag) {
		this.priceFlag = priceFlag;
	}

	public boolean isDetailsFlag() {
		return detailsFlag;
	}

	public void setDetailsFlag(boolean detailsFlag) {
		this.detailsFlag = detailsFlag;
	}

	public boolean isQuantityFlag() {
		return quantityFlag;
	}

	public void setQuantityFlag(boolean quantityFlag) {
		this.quantityFlag = quantityFlag;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Map<String, String> getEntry() {
		return entry;
	}

	public void setEntry(Map<String, String> entry) {
		this.entry = entry;
	}

	private Document generateDocument() throws IOException{
		return doc = Jsoup.connect("http://www.asylum-booksandgames.com/shop/index.php?page="+ pageCounter+ "&searchStr=&act=viewCat&Submit=Go").get();
	}
	
	private void parseHTML(Elements columns){
		this.entry = new HashMap<String, String>();

		for (Element column : columns) {

			if (column.text().startsWith("Buy") || column.text().startsWith("More")) {
				praseIDQuantity(column);
			} else if (column.text().length() <= 8) {
				parseCost(column);
			} else if (column.text().equals("Description")) {
				// Ignore and reset
				count = 0;
			} else {
				System.out.println("\n" + "Description : \t"+ column.text());
				// pass column text
				parseDescription(column);
			}
			if (priceFlag && quantityFlag && detailsFlag) {

				System.out.println("Inserting Record "+ this.entry.get("id") + " : Page " + this.pageCounter);
				boolean inserted = mySQLFacade.insertAsylumRecord(this.entry);
				System.out.println("Success : " + inserted);

				this.entry = new HashMap<String, String>();
				this.priceFlag = false;
				this.quantityFlag = false;
				this.detailsFlag = false;
			}
		}
	}

	private void praseIDQuantity(Element column){
		try {
			Element quantityInput = column.select("input[type=hidden]").get(1);
			String quantity = quantityInput.attr("value");
			// System.out.println("Quantity : " + quantity);
			this.entry.put("quantity", quantity);
		
			Element idInput = column.select("input[type=hidden]").get(0);
			String id = idInput.attr("value");
			// System.out.println("Product ID : " + id);
			this.entry.put("id", id.toString());

			// System.out.println("id test"+ entry.get("id"));
			this.quantityFlag = true;
		} catch (Exception e) {
			this.entry.put("quantity", "0");
			//System.out.println("Quantity set to 0");
			
			this.entry.put("id", "unknown");
			//System.out.println("Id set to unknown");
			//this.quantityFlag = true;
		}
		
	}
	
	private void parseCost(Element column){
		try {
			double value = Double.parseDouble(column.text());
			// System.out.println("Cost £ : " + value);
			this.entry.put("cost", Double.toString(value));
			priceFlag = true;
		} catch (Exception e) {

		}
	}
	
	private void parseDescription(Element column){
		// Split Strings
		String[] name = null;
		String[] publisher = null;
		String[] issue = null;
		String volume = null;

		try {
			String[] splitString = column.text().split(
					"Title: ");
			try {
				name = splitString[1].split("Publisher: ");
				this.entry.put("title", name[0]);
				try {
					publisher = name[1].split("Issue Number: ");
					this.entry.put("publisher", publisher[0]);
					try {
						issue = publisher[1].split("Volume: ");
						this.entry.put("issue", issue[0]);
						try {
							volume = issue[1];
							this.entry.put("volume", volume);
						} catch (Exception e) {
							volume = null;
							System.out
									.println("Volume exception");
							this.entry.put("volume", volume);
							e.printStackTrace();
						}
					} catch (Exception e) {
						issue[0] = null;
						System.out.println("issue exception");
						this.entry.put("issue", "null");
						e.printStackTrace();
					}
				} catch (Exception e) {
					publisher[0] = null;
					System.out.println("publisher exception");
					this.entry.put("publisher", "null");
					//e.printStackTrace();
					
					this.entry = new HashMap<String, String>();
					this.priceFlag = false;
					this.quantityFlag = false;
					this.detailsFlag = false;
					
					System.out.println("Skipped records : "+column.text());
					
				}
			} catch (Exception e) {
				// System.out.println("split "+column.text());
				// name = column.text().
				try{
				Element title = column.select("strong").first();
				// System.out.println("Title" + title.text());
				// name[0] = title.text();
				this.entry.put("title", title.text());
				// e.printStackTrace();
				}catch(NullPointerException ex){
					this.entry = new HashMap<String, String>();
					this.priceFlag = false;
					this.quantityFlag = false;
					this.detailsFlag = false;
					System.out.println("Skipped records : "+column.text());
				}
			}
			// Save strings
			if(this.entry.containsKey("title")){
				System.out.println("Title : \t"+ this.entry.get("title"));
				System.out.println("Issue Number : \t"+ this.entry.get("issue"));
				System.out.println("Publisher : \t"+ this.entry.get("publisher"));
				System.out.println("Volume : \t"+ this.entry.get("volume"));
			}
			// Sub title, author, format
			detailsFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	
	/**
	 * Returns an Image object that can then be painted on the screen. 
	 * The url argument must specify an absolute {@link URL}. The name
	 * argument is a specifier that is relative to the url argument. 
	 * <p>
	 *
	 * @param  url  an absolute URL giving the base location of the image
	 * @param  name the location of the image, relative to the url argument
	 * @return      the image at the specified URL
	 * @see         Image
	 */
	public boolean executeQuery() {
		do {
			try {
				Document doc = generateDocument();

				Element table = doc.select("table").get(1);
				Elements rows = table.select("tr");
				Elements columns = rows.select("td");
				
				parseHTML(columns);

				pageCounter++;
				System.out.println("Increment counter : " + pageCounter);

				Thread.sleep(nextRequestDelay);
			} catch (SocketTimeoutException e) {
				// pageCounter--;
				System.out.println("Time Out : Page " + pageCounter);
				try {
					Thread.sleep(nextRequestDelay);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed on page counter : " + pageCounter);
				System.out.println("Program Stopped");
				return false;
				//break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Failed on page counter : " + pageCounter);
				System.out.println("Program Stopped");
				return false;
				//break;
			}

		} while (pageCounter <= pageStop);
		System.out.println("Program Completed");
		return true;
	}

	public int getNextRequestDelay() {
		return nextRequestDelay;
	}

	public void setNextRequestDelay(int nextRequestDelay) {
		this.nextRequestDelay = nextRequestDelay;
	}
}
