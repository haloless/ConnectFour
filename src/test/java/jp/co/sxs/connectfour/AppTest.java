package jp.co.sxs.connectfour;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    
    public void testPlayer() {
    	System.out.println(Player.Player1.getColorAbbr());
    	System.out.println(Player.Player2.getColorAbbr());
    }
    
    /**
     * This is the sample run contained in connect4-readme.txt.
     * Two players are simulated.
     * Player 1 wins and exits.
     */
    public void testSimulatedRun() {
    	// commands of 
    	String commands = 
    			"4\n" + "4\n" + 
    			"5\n" + "5\n" +
    			"3\n" + "2\n" +
    			"6\n" + 
    			"q\n";
    	
    	InputStream in = new ByteArrayInputStream(commands.getBytes());
    	
    	ConsoleGame cg = new ConsoleGame();
    	
    	cg.setInput(in);
    	
    	cg.init();
    	cg.loop();
    	
    }
}
