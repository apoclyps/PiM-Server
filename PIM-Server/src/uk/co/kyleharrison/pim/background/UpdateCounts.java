package uk.co.kyleharrison.pim.background;

import java.util.Date;

import uk.co.kyleharrison.pim.service.model.ComicVineService;

public class UpdateCounts implements Runnable {

	private ComicVineService comicVineService;
	
    public UpdateCounts() {
		super();
		this.comicVineService = new ComicVineService();
	}

	@Override
    public void run() {
        // Do your job here.
    	System.out.println("Background process "+new Date().toString());
    	this.comicVineService.executeQueryAllResults("Conan");
    	System.out.println("Background update complete");
    	//this.comicVineService.cacheResults();
    	
    }

}

