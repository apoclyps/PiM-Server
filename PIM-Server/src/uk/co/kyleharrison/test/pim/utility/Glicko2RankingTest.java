/*
 * Copyright (C) 2013 Jeremy Gooch <http://www.linkedin.com/in/jeremygooch/>
 *
 * The licence covering the contents of this file is described in the file LICENCE.txt,
 * which should have been included as part of the distribution containing this file.
 */
package uk.co.kyleharrison.test.pim.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.goochjs.glicko2.Rating;
import org.goochjs.glicko2.RatingCalculator;
import org.goochjs.glicko2.RatingPeriodResults;
import org.junit.Test;

/**
 * @author Jeremy Gooch
 *
 */
public class Glicko2RankingTest {

	private RatingCalculator ratingSystem = new RatingCalculator(0.06, 0.5);
	private RatingPeriodResults results = new RatingPeriodResults();
	private Rating player1 = new Rating("player1", ratingSystem); // the main player of Glickman's example
	private Rating player2 = new Rating("player2", ratingSystem); 
	private Rating player3 = new Rating("player3", ratingSystem);
	private Rating player4 = new Rating("player4", ratingSystem);
	private Rating player5 = new Rating("player5", ratingSystem); // this player won't compete during the test
	private ArrayList<Rating> players = new ArrayList<Rating>();
	
	/**
	 * This test uses the values from the example towards the end of Glickman's paper as a simple test of the calculation engine
	 * In addition, we have another player who doesn't compete, in order to test that their deviation will have increased over time.
	 */
	@Test
	public void test() {
		initialise();
		printResults("Before");

		// test that the scaling works
		assertEquals( 0, player1.getGlicko2Rating(), 0.00001 );
		assertEquals( 1.1513, player1.getGlicko2RatingDeviation(), 0.00001 );

		results.addResult(player1, player2); // player1 beats player 2
		results.addResult(player3, player1); // player3 beats player 1
		results.addResult(player4, player1); // player4 beats player 1
		
		sortByRank();
		ratingSystem.updateRatings(results);
		
		// Testing Results - Player 5 should become top search
		results.addResult(player5,player1);
		results.addResult(player5,player1);
		results.addResult(player5,player1);
		results.addResult(player5,player1);
		results.addResult(player5,player1);
		
		sortByRank();
		ratingSystem.updateRatings(results);
		
		//Player 2 should increase in rank
		results.addResult(player2,player5);
		results.addResult(player2,player5);
		results.addResult(player2,player5);
		
		sortByRank();
		ratingSystem.updateRatings(results);
		
		printResults("\nAfter");
		
		//Sort Results by Rank
		sortByRank();
		
		// test that the player1's new rating and deviation have been calculated correctly
		assertEquals( 1464.06, player1.getRating(), 0.01 );
		assertEquals( 151.52, player1.getRatingDeviation(), 0.01 );
		assertEquals( 0.05999, player1.getVolatility(), 0.01 );

		// test that opponent 4 has had appropriate calculations applied
		assertEquals( ratingSystem.getDefaultRating(), player5.getRating(), 0 );  // rating should be unaffected
		assertTrue( ratingSystem.getDefaultRatingDeviation() < player5.getRatingDeviation() );  // rating deviation should have grown
		assertEquals( ratingSystem.getDefaultVolatility(), player5.getVolatility(), 0 );  // volatility should be unaffected
	}
	
	private void initialise() {
		player1.setRating(1500);
		player2.setRating(1500);
		player3.setRating(1500);
		player4.setRating(1500);
		
		player1.setRatingDeviation(200);
		player2.setRatingDeviation(200);
		player3.setRatingDeviation(200);
		player4.setRatingDeviation(200);

		results.addParticipants(player5);  // the other players will be added to the participants list automatically
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		players.add(player5);
	}
	
	private void sortByRank(){
		Collections.sort(players, new Comparator<Rating>() {
			public int compare(Rating object1, Rating object2) {
		        if (object1.getRating() > object2.getRating()) return -1;
		        if (object1.getRating() < object2.getRating()) return 1;
		        return 0;
		    }
		});
		
		System.out.println("\nSorted Results");
		for (Rating str : players) {
			  System.out.println(str.toString());
			}
	}

	private void printResults(String text) {
		System.out.println(text + "...");
		System.out.println(player1);
		System.out.println(player2);
		System.out.println(player3);
		System.out.println(player4);
		System.out.println(player5);
	}
}
