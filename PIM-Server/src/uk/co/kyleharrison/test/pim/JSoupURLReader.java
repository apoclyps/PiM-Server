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
	private static int page_counter =1;
	private static int saved = 0;
	private static MySQLFacade mysqlFacade = new MySQLFacade();
	static int count =0;
	static boolean priceBol = false, detailsBol = false, quantityBol = false;

	public static void main(String[] arguments) {

		for (int i = 0; i < 1; i++) {

			try {
				final Document doc = Jsoup
						.connect(
								"http://www.asylum-booksandgames.com/shop/index.php?page=2&searchStr=&act=viewCat&Submit=Go")
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
							System.out.println("Quantity : " + quantity);
							entry.put("quantity", quantity);

							Element idInput = column.select(
									"input[type=hidden]").get(0);
							String id = idInput.attr("value");
							System.out.println("Product ID : " + id);
							entry.put("id", id.toString());
						//	System.out.println("id test"+ entry.get("id"));
							quantityBol=true;
						} catch (Exception e) {

						}
					} else if (column.text().length() <= 8) {
						try {
							double value = Double.parseDouble(column.text());
							System.out.println("Cost £ : " + value);
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

						try {
							String[] splitString = column.text().split(
									"Title: ");
							String [] name = splitString[1].split("Publisher: ");
							String[] publisher = name[1].split("Issue Number: ");
							String[] issue = publisher[1].split("Volume: ");
							String volume = issue[1];

							System.out.println("Title : \t" + name[0]);
							System.out.println("Issue Number : \t" + issue[0]);
							System.out.println("Publisher : \t"+publisher[0]);
							System.out.println("Volume : \t" + volume);
							
							entry.put("title", name[0]);
							entry.put("issue", issue[0]);
							entry.put("volume", volume);
							entry.put("publisher", publisher[0]);
							detailsBol=true;
						} catch (Exception e) {
							e.printStackTrace();
						}
						saved++;
							
					}
					if(priceBol && quantityBol && detailsBol){
						System.out.println("Inserting Records");
						mysqlFacade.insertAsylumRecord(entry);
						entry = new HashMap<String, String>();
						priceBol = false;
						quantityBol = false;
						detailsBol = false;
					}
				}

				System.out.println("\nSaved : " + saved);
				Thread.sleep(5000);
			} catch(SocketTimeoutException e){
				System.out.println("Time Out");
			}catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed on page counter : "+page_counter);
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Failed on page counter : "+page_counter);
				break;
			}
		}
	}
}
