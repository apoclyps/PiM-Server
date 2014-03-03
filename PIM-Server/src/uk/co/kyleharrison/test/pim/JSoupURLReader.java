package uk.co.kyleharrison.test.pim;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

public class JSoupURLReader {

	private static int pages = 5005;
	private static int page_counter =10;
	private static int page_stop=100;
	private static int saved = 0;
	private static MySQLFacade mysqlFacade = new MySQLFacade();
	static int count =0;
	static boolean priceBol = false, detailsBol = false, quantityBol = false;

	public static void main(String[] arguments) {

		do{
			try {
				final Document doc = Jsoup
						.connect(
								"http://www.asylum-booksandgames.com/shop/index.php?page="+page_counter+"&searchStr=&act=viewCat&Submit=Go")
						.get();

				Element table = doc.select("table").get(1);

				Elements rows = table.select("tr");
				Elements columns = rows.select("td");

				Map<String, String> entry = new HashMap<String, String>();
							
				for (Element column : columns) {
					
					if (column.text().startsWith("Buy")
							|| column.text().startsWith("More")) {
						try {
							Element quantityInput = column.select(
									"input[type=hidden]").get(1);
							String quantity = quantityInput.attr("value");
							//System.out.println("Quantity : " + quantity);
							entry.put("quantity", quantity);

							Element idInput = column.select(
									"input[type=hidden]").get(0);
							String id = idInput.attr("value");
							//System.out.println("Product ID : " + id);
							entry.put("id", id.toString());
							
							//System.out.println("id test"+ entry.get("id"));
							quantityBol=true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (column.text().length() <= 8) {
						try {
							double value = Double.parseDouble(column.text());
							//System.out.println("Cost £ : " + value);
							entry.put("cost", Double.toString(value));
							priceBol=true;
						} catch (Exception e) {
							
						}
					} else if(column.text().equals("Description")){
						//Ignore and reset
						count =0;
						
					}else{
						System.out.println("\n" + "Description : \t"
								+ column.text());

						String [] name = null;
						String[] publisher = null;
						String[] issue = null;
						String volume = null;
											
						try {
							String[] splitString = column.text().split("Title: ");
							try{
								name = splitString[1].split("Publisher: ");
								entry.put("title", name[0]);
								try{
									publisher = name[1].split("Issue Number: ");
									entry.put("publisher", publisher[0]);
									try{
										issue = publisher[1].split("Volume: ");
										entry.put("issue", issue[0]);
										try{
											volume = issue[1];
											entry.put("volume", volume);
										}catch(Exception e){
											volume =null;
											System.out.println("Volume exception");
											entry.put("volume", volume);
											e.printStackTrace();
										}
									}catch(Exception e){
										issue[0] = null;
										System.out.println("issue exception");
										entry.put("issue", "null");
										e.printStackTrace();
									}
								}catch(Exception e){
									publisher[0]=null;
									System.out.println("publisher exception");
									entry.put("publisher", "null");
									e.printStackTrace();
								}
							}catch(Exception e){
							//	System.out.println("split "+column.text());
								//name = column.text().
								Element title = column.select("strong").first();
							//	System.out.println("Title" + title.text());
								//name[0] = title.text();
								entry.put("title", title.text());
								//e.printStackTrace();
							}
							
							System.out.println("Title : \t" + entry.get("title"));
							System.out.println("Issue Number : \t" + entry.get("issue"));
							System.out.println("Publisher : \t"+entry.get("publisher"));
							System.out.println("Volume : \t" + entry.get("volume"));
							
							// Sub title, author, format
						
							detailsBol=true;
						} catch (Exception e) {
							e.printStackTrace();
						}
						saved++;
					}
					if(priceBol && quantityBol && detailsBol){
						
						System.out.println("Inserting Record "+ entry.get("id") + " : Page "+page_counter);
						boolean inserted = mysqlFacade.insertAsylumRecord(entry);
						System.out.println("Success : "+inserted);
						
						entry = new HashMap<String, String>();
						priceBol = false;
						quantityBol = false;
						detailsBol = false;
					}
				}

				//System.out.println("\nSaved : " + saved);
				page_counter++;
				System.out.println("Increment counter : " + page_counter);
				
				Thread.sleep(10000);
			} catch(SocketTimeoutException e){
				//page_counter--;
				System.out.println("Time Out : Page "+page_counter);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//break;
			}catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed on page counter : "+page_counter);
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Failed on page counter : "+page_counter);
				break;
			}

		}while(page_counter<=page_stop);
		System.out.println("Program Stopped");
	}
}
