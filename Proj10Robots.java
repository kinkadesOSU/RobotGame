/* Proj10Robots
 *
 * CSc 127a Spring 16 - Project 10
 *
 * Author: Russ Lewis
 * TA:     n/a
 *
 * This is the main() method for Robots (Project 10 version).  It has the main
 * game loop, and very basic game logic; most of the logic for this program is
 * buried in the Proj10GameState class.
 */

import java.awt.Font;

public class Proj10Robots
{
    public static void main(String[] args)
    {
        int board_size;
        if (args.length == 0)
            board_size = 35;
        else if (args.length == 1)
            board_size = Integer.parseInt(args[0]);
        else
        {
            System.out.println("ERROR: No more than 1 command-line argument is allowed.");
            return;
        }
        
        // this version of the game has multiple levels of difficulty; when
        // you clear the board, we start a new game, with more robot	s.
        int level = 1;
        int safeTeleports = 20;
		StdDraw.setCanvasSize(700, 700);
        // each pass of this loop represents one level of the game.  We create
        // a fresh object, which we'll use *ONLY* through this iteration of
        // the outer loop.  If the level is completed, then we'll loop back
        // here and create a new Game State object; if the game ends, then
        // we'll break out of this loop manually.
        while (true)
        {
            // create the Game State object; fill it with robots, and
            // then draw the initial state.
            Proj10GameState game = new Proj10GameState(board_size);
            game.addRobots(level*10);
            game.draw(level, safeTeleports);
            
            // the inner loop drives the game.  We continue until either the
            // player dies, or the last of the robots is destroyed.
            //
            // After the inner loop ends, we'll decide which of the two
            // conditions occurred, and respond appropriately.
            while (game.isGameOver() == false && game.allRobotsDestroyed() == false)
            {
                while (StdDraw.hasNextKeyTyped() == false)
                    StdDraw.show(10);
                
                // now that the user has typed something, read it, and
                // pass it to the game object - EXCEPT that we'll handle
                // teleports in main(), instead of sending that to the
                // game object.
                char key = StdDraw.nextKeyTyped();
                
                if (key == ' ' || key == '0')
                {
                    // if there are no safe teleports left, then simply do the
                    // teleport, and see what happens.  But if there *are*
                    // some left, then loop trying teleports over and over,
                    // until something works.
                    if (safeTeleports == 0)
                    {
                        game.doTeleport();
                        game.moveRobots();
                        game.draw(level, safeTeleports);
                        
                        // jump back to the beginning of the inner loop;
                        // maybe the game just ended, or (less likely) all
                        // of the robots died.
                        continue;
                    }
                    else
                    {
                        safeTeleports--;
                        
                        while (true)
                        {
                            Proj10GameState dup = game.dup();
                            dup.doTeleport();
                            dup.moveRobots();
                            if (dup.isGameOver() == false)
                            {
                                game = dup;
                                break;
                            }
                            // else loop back, make another dup, and try again
                        }
                        
                        // when we get here, we've found a safe teleport
                        // location.  We redraw the screen, and then jump back
                        // to the beginning of the inner loop.  It's possible
                        // (though unlikely) that all of the robots have been
                        // destroyed.
                        game.draw(level, safeTeleports);
                        StdDraw.show(1000);
                        continue;
                    }
                }
                
                // handleKeyTyped() should return 'true' if the player
                // moved (or choose not to move), so that the robots will
                // move and the board will be redrawn.
                //
                // It should return 'false' if the key wasn't recognized,
                // or if no move was possible.
                if (game.handleKeyTyped(key))
                {
                    // something happened; move all the robots, and
                    // redraw the board.
                    game.moveRobots();
                    game.draw(level, safeTeleports);
                }
            }
            
            // when we get here, either the game is over, or all of the
            // robots have been destroyed and it's time to start another
            // level.
            if (game.isGameOver())
                break;   // end the outer loop
            
            // the player won the level!
            
            // draw the text "CONGRATULATIONS" over the top of the last board
            // which was drawn; wait for 1 second.
            StdDraw.setScale(0,1);
            
            StdDraw.setPenColor(StdDraw.BLACK);
            Font save = StdDraw.getFont();
            StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            StdDraw.text(.5,.5, "Congratulations!");
            StdDraw.setFont(save);
            StdDraw.show(1000);
            
            // now, save off the game state before we end this iteration; this
            // information will be used to create a new game object later.
            level++;
            if (safeTeleports<100)
            	safeTeleports+=safeTeleports;
            else
            	safeTeleports=safeTeleports/2;
        }
        
        // draw the text "GAME OVER" over the top of the last board
        // which was drawn.
        StdDraw.setScale(0,1);
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        StdDraw.text(.5,.5, "Game Over!");
        StdDraw.show(0);
        
        // program ends
    }
}