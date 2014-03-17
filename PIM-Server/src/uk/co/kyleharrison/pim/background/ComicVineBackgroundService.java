package uk.co.kyleharrison.pim.background;

import java.util.Date;

import uk.co.kyleharrison.pim.service.model.ComicVineService;

public class ComicVineBackgroundService implements Runnable {

	private ComicVineService comicVineService;
	
    public ComicVineBackgroundService() {
		super();
		this.comicVineService = new ComicVineService();
	}

	@Override
    public void run() {
        // Do your job here.
    	System.out.println("Background process "+new Date().toString());
    	this.comicVineService.executeSimpleQuery("avengers");
    	//this.comicVineService.cacheAllResults();
    	this.comicVineService.cacheAllResultsCassandra();
    	try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Background update complete");
    	//this.comicVineService.cacheResults();
    	
    }

}

