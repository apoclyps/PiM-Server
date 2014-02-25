package uk.co.kyleharrison.pim.service.model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.interfaces.ControllerServiceInterface;

import com.mlesniak.amazon.backend.AmazonItem;
import com.mlesniak.amazon.backend.AmazonItemConverter;
import com.mlesniak.amazon.backend.AmazonRequest;
import com.mlesniak.amazon.backend.AmazonRequestBuilder;
import com.mlesniak.amazon.backend.SearchIndex;

public class AmazonService implements ControllerServiceInterface {

	@SuppressWarnings("unchecked")
	public String testAmazonProductAPI(String query) {
        try{
            AmazonRequest request = AmazonRequestBuilder.init()
                    .addKeywords(query)
                    .addSearchIndex(SearchIndex.DVD)
                    .addMaximumPrice(10000)
                    .addMinimumPrice(1000)
                    .build();

            List<AmazonItem> amazonItems = AmazonItemConverter.convertFull(request);
            
            JSONArray itemArray = new JSONArray();
            for (AmazonItem amazonItem : amazonItems) {
                System.out.println(amazonItem);
        		JSONObject item = new JSONObject();
        		item.put("asin", amazonItem.getAsin());
        		item.put("resource_type", SearchIndex.DVD.toString());
        		item.put("title", amazonItem.getTitle());
        		item.put("price", amazonItem.getPrice());
        		item.put("URL", amazonItem.getURL());
        		item.put("imageURL", amazonItem.getImageURL());
        		item.put("reviewURL", amazonItem.getReviewURL());
                itemArray.add(item);
            }
              
            JSONObject results = new JSONObject();
            results.put("Amazon", itemArray);
            return results.toString();
        }catch(NoSuchFieldError nsfe){
        	System.out.println("No such field error ");
        }
        return null;
	}

	@Override
	public String executeQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject executeJSONQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean searchItemExists(String item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cacheItem(String item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cacheResults(ArrayList<Object> results) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
