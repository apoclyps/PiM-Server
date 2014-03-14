package uk.co.kyleharrison.pim.background;

import java.util.Date;

public class UpdateCounts implements Runnable {

    @Override
    public void run() {
        // Do your job here.
    	System.out.println("Background process "+new Date().toString());
    }

}

