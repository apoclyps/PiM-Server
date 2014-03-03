package uk.co.kyleharrison.test.pim;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupURLReader {

	private static int pages = 5005;
	private static int page_counter =0;
	private static int saved = 0;

	public static void main(String[] arguments) {

		for (int i = 0; i < 2; i++) {

			try {
				final Document doc = Jsoup
						.connect(
								"http://www.asylum-booksandgames.com/shop/index.php?page="+page_counter+"&searchStr=&act=viewCat&Submit=Go")
						.get();

				Element table = doc.select("table").get(1);

				Elements rows = table.select("tr");
				Elements columns = rows.select("td");

				for (Element column : columns) {
					if (column.text().startsWith("Buy")
							|| column.text().startsWith("More")) {
						try {
							Element buddynameInput = column.select(
									"input[type=hidden]").get(1);
							String buddyname = buddynameInput.attr("value");
							System.out.println("Quantity : " + buddyname);

							Element quantityInput = column.select(
									"input[type=hidden]").get(0);
							String quantity = quantityInput.attr("value");
							System.out.println("Product ID : " + quantity);
						} catch (Exception e) {

						}
					} else if (column.text().length() <= 8) {
						try {
							double value = Double.parseDouble(column.text());
							System.out.println("Cost £ : " + value);
						} catch (Exception e) {

						}
					} else if(column.text().equals("Description")){
						//Ignore
					}else{
						System.out.println("\n" + "Description : \t"
								+ column.text());

						try {
							String[] splitString = column.text().split(
									"Title: 'Nam,");
							String[] name = splitString[1]
									.split("Issue Number: ");
							String[] issue = name[1].split("Volume: ");
							String volume = issue[1];

							System.out.println("Title : \t" + name[0]);
							System.out.println("Issue Number : \t" + issue[0]);
							System.out.println("Volume : \t" + volume);
						} catch (Exception e) {
						}
						saved++;
					}
				}
				System.out.println("\nSaved : " + saved);
				Thread.sleep(5000);
			} catch (IOException e) {
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
