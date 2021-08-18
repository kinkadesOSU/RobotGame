/* Proj09Robots
 *
 * CSc 127a Spring 16 - Project 9
 *
 * Author: Sam Kinkade
 * TA: Zach Tranahan
 *
 * This class is designed to be used with the Robots.java main method created by Professor Lewis. It contains all
 * of the "brains" of the game and coordinates the placement and movement of robots, rubble, the player, 
 * drawing of the screen, and deciding if the game is over. 
 */
import java.util.Random;

public class Proj10GameState {

	//empty=0; robots=1, rubble=2
	private int[][] robots;
	private int[][] gameBoard;
	private int boardSize;
	private int robotCount;
	private int xPos; //x position of player
	private int yPos; //y position of the player
	private boolean isGameOver;
	int moveCount;
	
	//This constructor initializes the game board size, the robots array, and starts the player in the middle of the board
	public Proj10GameState(int boardSize)
	{
		if (boardSize <5)
			throw new IllegalArgumentException();
		else
		{
			robots=new int[boardSize][boardSize];
			gameBoard=new int[boardSize][boardSize];
			this.boardSize=boardSize;
			robotCount=0;
			moveCount=0;
			xPos=Math.round(boardSize/2);
			yPos=Math.round(boardSize/2);
			StdDraw.setXscale(0, boardSize*4);
			//The +20 is the margin at the top
			StdDraw.setYscale(0,(boardSize*4)+15);
			isGameOver=false;
			StdDraw.show(0);
		}
	}
	
	//This method takes a number of robots from main and randomly places them on the board (without putting on where the player starts)
	//This method adds robots to the board
	public void addRobots(int robotsToAdd)
	{
		robotCount=robotsToAdd;
		Random rand=new Random();
		int randX;
		int randY;
		//starts with no robots on the board
		for (int i=0;i<robots.length;i++)
		{
			for (int j=0;j<robots[0].length;j++)
			{
				robots[i][j]=0;
			}
		}
		
		//randomly places robots throughout the board
		for (int i=0;i<robotsToAdd;i++)
		{
			randX=rand.nextInt(boardSize);
			randY=rand.nextInt(boardSize);
			if(robots[randY][randX]==0 && randX!= xPos && randY!= yPos)
				robots[randY][randX]=1;
			//decrements i if there is a duplicate to make sure the number of robots asked for are placed on the board
			else
				i--; 
		}
	}
	
	//This method checks if the game is over
	
	//This method checks to see if the game is over by checking if the player is on a square that has a robot or rubble on it
	public boolean isGameOver()
	{
		//Game is never assumed to be over until everything has been checked
		isGameOver=false;
		for (int i=0;i<gameBoard.length;i++)
		{
			for (int j=0;j<gameBoard[0].length;j++)
			{
				if(robots[i][j]!=0 && i==yPos && j==xPos)
					isGameOver=true;
			}
		}
		return isGameOver;
	}
	
	
	/*This method takes the key that was pressed by the user (any lower or uppercase variants of q, w, e, a, s, d, z, x, c or 1-9)
	* and moves the player accordingly one unit up, down, or diagonally.
	*/ 
	public boolean handleKeyTyped(char playerMove)
	{
		boolean validMove=false;
		if(playerMove=='w' ||playerMove=='W' || playerMove=='8')
		{
			//Checks if there is room behind the player and the edge of the board to move rubble
			if (yPos<boardSize-2)
			{
				yPos++;
				if (robots[yPos][xPos]==2 && robots[yPos+1][xPos]!=2)
				{
					robots[yPos+1][xPos]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				//Can't move if there are two pieces of rubble in a row
				else if (robots[yPos][xPos]==2 && robots[yPos+1][xPos]==2)
				{
					yPos--;
					validMove=false;
				}
				else
					validMove=true;
			}
			//If there is only one space between the player and the edge of the board
			else if (yPos<boardSize-1)
			{
				yPos++;
				//Can't move if the rubble is up against the edge
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					yPos--;
					validMove=false;
				}
				else
					validMove=true;
			}
		}
		else if (playerMove=='a' ||playerMove=='A' || playerMove=='4')
		{
			if (xPos>=2)
			{
				xPos--;
				if (robots[yPos][xPos]==2 && robots[yPos][xPos-1]!=2)
				{
					robots[yPos][xPos-1]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				else if (robots[yPos][xPos]==2 && robots[yPos][xPos-1]==2)
				{
					xPos++;
					validMove=false;
				}
				else
					validMove=true;
			}
			else if (xPos>=1)
			{
				xPos--;
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					xPos++;
					validMove=false;
				}
				else
					validMove=true;
			}
		}
		else if (playerMove=='s' ||playerMove=='S' || playerMove=='5')
		{
			//Checks if there is room behind the player and the edge of the board to move rubble
			if (yPos<boardSize-2)
			{
				yPos--;
				if (robots[yPos][xPos]==2 && robots[yPos+1][xPos]!=2)
				{
					robots[yPos+1][xPos]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				//Can't move if there are two pieces of rubble in a row
				else if (robots[yPos][xPos]==2 && robots[yPos+1][xPos]==2)
				{
					yPos++;
					validMove=false;
				}
				else
					validMove=true;
			}
			//If there is only one space between the player and the edge of the board
			else if (yPos<boardSize-1)
			{
				yPos++;
				//Can't move if the rubble is up against the edge
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					yPos--;
					validMove=false;
				}
				else
					validMove=true;
			}
		}
		else if (playerMove=='d' ||playerMove=='D' || playerMove=='6')
		{
			if (xPos<boardSize-2)
			{
				xPos++;
				if (robots[yPos][xPos]==2 && robots[yPos][xPos+1]!=2)
				{
					robots[yPos][xPos+1]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				else if (robots[yPos][xPos]==2 && robots[yPos][xPos+1]==2)
				{
					xPos--;
					validMove=false;
				}
				else
					validMove=true;
			}
			else if (xPos<boardSize-1)
			{
				xPos++;
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					xPos--;
					validMove=false;
				}
				else
					validMove=true;
			}
		}
		else if (playerMove=='x' ||playerMove=='X' || playerMove=='2')
		{
			if (yPos>=2)
			{
				yPos--;
				if (robots[yPos][xPos]==2 && robots[yPos-1][xPos]!=2)
				{
					robots[yPos-1][xPos]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				else if (robots[yPos][xPos]==2 && robots[yPos-1][xPos]==2)
				{
					yPos++;
					validMove=false;
				}
				else
					validMove=true;
			}
			else if (yPos>=1)
			{
				yPos--;
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					yPos++;
					validMove=false;
				}
				else
					validMove=true;
			}
		}
		else if (playerMove=='q' ||playerMove=='Q' || playerMove=='7')
		{
			if(xPos>=2 & yPos<boardSize-2)
			{
				xPos--;
				yPos++;
				if (robots[yPos][xPos]==2 && robots[yPos+1][xPos-1]!=2)
				{
					robots[yPos+1][xPos-1]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				else if (robots[yPos][xPos]==2 && robots[yPos+1][xPos-1]==2)
				{
					xPos++;
					yPos--;
					validMove=false;
				}
				else
					validMove=true;
			}
			else if (xPos>=1 & yPos<boardSize-1)
			{
				xPos--;
				yPos++;
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					xPos++;
					yPos--;
					validMove=false;
				}
				else
					validMove=true;
			}			
		}
		else if (playerMove=='e' ||playerMove=='E' || playerMove=='9')
		{
			if(xPos<boardSize-2 & yPos<boardSize-2)
			{
				xPos++;
				yPos++;
				if (robots[yPos][xPos]==2 && robots[yPos+1][xPos+1]!=2)
				{
					robots[yPos+1][xPos+1]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				else if (robots[yPos][xPos]==2 && robots[yPos+1][xPos+1]==2)
				{
					xPos--;
					yPos--;
					validMove=false;
				}
				else
					validMove=true;
			}
			else if (xPos<boardSize-1 & yPos<boardSize-1)
			{
				xPos++;
				yPos++;
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					xPos--;
					yPos--;
					validMove=false;
				}
				else
					validMove=true;
			}			
		}
		else if (playerMove=='z' ||playerMove=='Z' || playerMove=='1')
		{
			if(xPos>=2 & yPos>=2)
			{
				xPos--;
				yPos--;
				if (robots[yPos][xPos]==2 && robots[yPos-1][xPos-1]!=2)
				{
					robots[yPos-1][xPos-1]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				else if (robots[yPos][xPos]==2 && robots[yPos-1][xPos-1]==2)
				{
					xPos++;
					yPos++;
					validMove=false;
				}
				else
					validMove=true;
			}
			else if (xPos>0 & yPos>0)
			{
				xPos--;
				yPos--;
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					xPos++;
					yPos++;
					validMove=false;
				}
				else
					validMove=true;
			}			
		}
		else if (playerMove=='c' ||playerMove=='C' || playerMove=='3')
		{
			//Checks to see if there is room behind the player and the edge of the board
			if(xPos<boardSize-2 & yPos>=2)
			{
				xPos++;
				yPos--;
				if (robots[yPos][xPos]==2 && robots[yPos-1][xPos+1]!=2)
				{
					robots[yPos-1][xPos+1]=2;
					robots[yPos][xPos]=0;
					validMove=true;
				}
				//Reverses the player movement if it can't move the rubble
				else if (robots[yPos][xPos]==2 && robots[yPos-1][xPos+1]==2)
				{
					xPos--;
					yPos++;
					validMove=false;
				}
				else
					validMove=true;
			}
			//Checks there's only one space between the player and the edge of the board
			else if (xPos<boardSize-1 & yPos>=1)
			{
				xPos++;
				yPos--;
				//If there's rubble, it can't be moved
				if (robots[yPos][xPos]==2)
				{
					System.out.println("Cannot move");
					xPos--;
					yPos++;
					validMove=false;
				}
				else
					validMove=true;
			}		
		}
		if (validMove==true)
			moveCount++;
		return validMove;
	}
	//This method moves the robots anytime the player moves. 
	
	//This method moves the robots after every valid player move
	public void moveRobots()
	{
		robotCount=0;
		//creates the copy of the robots
		int [][] tempRobots=new int [boardSize][boardSize];
		for (int i=0;i<tempRobots.length;i++)
		{
			for (int j=0;j<tempRobots[0].length;j++)
			{
				//checks for rubble
				if (robots[i][j]==2)
				{
					tempRobots[i][j]=2;
				}
				//checks for a robot
				if (robots[i][j]==1)
				{
					/*figure out where it goes. First a check is conducted to see if the robot is above or below
					 * then every combination of left or right is conducted. When that is determined, the robot moves 
					 * one unit accordingly.
					 */
					if (i<yPos)
					{
						if (j<xPos)
						{
							if (tempRobots[i+1][j+1]==0)
								tempRobots[i+1][j+1]=1;
							else
								tempRobots[i+1][j+1]=2;
						}
						else if (j> xPos)
						{
							if (tempRobots[i+1][j-1]==0)
								tempRobots[i+1][j-1]=1;
							else
								tempRobots[i+1][j-1]=2;
						}
						else
						{
							if (tempRobots[i+1][j]==0)
								tempRobots[i+1][j]=1;
							else
								tempRobots[i+1][j]=2;
						}
					}
					else if (i>yPos)
					{
						if (j<xPos)
						{
							if (tempRobots[i-1][j+1]==0)
								tempRobots[i-1][j+1]=1;
							else
								tempRobots[i-1][j+1]=2;
						}
						else if (j> xPos)
						{
							if (tempRobots[i-1][j-1]==0)
								tempRobots[i-1][j-1]=1;
							else
								tempRobots[i-1][j-1]=2;
						}
						else
						{
							if (tempRobots[i-1][j]==0)
								tempRobots[i-1][j]=1;
							else
								tempRobots[i-1][j]=2;
						}
					}
					else if (i==yPos)
					{
						if (j<xPos)
						{
							if (tempRobots[i][j+1]==0)
								tempRobots[i][j+1]=1;
							else
								tempRobots[i][j+1]=2;
						}
						else if (j> xPos)
						{
							if (tempRobots[i][j-1]==0)
								tempRobots[i][j-1]=1;
							else
								tempRobots[i][j-1]=2;
						}
						else
						{
							if (tempRobots[i][j]==0)
								tempRobots[i][j]=1;
							else
								tempRobots[i][j]=2;
						}
					}
				}
			}
		}
		
		//Stores the new copied array to the instance variable "robots" for later use.
		robots=tempRobots.clone();
		//Counts how many robots are left on the board
		for (int i=0;i<robots.length;i++)
		{
			for (int j=0;j<robots[0].length;j++)
			{
				if (robots[i][j]==1)
					robotCount++;
			}
		}
	}
		
	//This method checks to see if there are any robots left on the board
	
	//This method checks to see if all of the robots have been destroyed
	public boolean allRobotsDestroyed()
	{
		for (int i=0;i<robots.length;i++)
		{
			for(int j=0;j<robots[0].length;j++)
			{
				if (robots[i][j]==1)
					return false;
			}
		}
		return true;
	}
	
	//This method duplicates the old game object to a new one
	
	//This method duplicates the game object everytime the player levels up or teleports
	public Proj10GameState dup()
	{
		Proj10GameState game= new Proj10GameState(boardSize);
		game.boardSize=this.boardSize;
		game.gameBoard=this.gameBoard;
		game.isGameOver=this.isGameOver;
		game.robotCount=this.robotCount;
		game.moveCount=this.moveCount+1;
		game.robots=this.robots;
		game.xPos=this.xPos;
		game.yPos=this.yPos;
		return game;
	}
	
	//This method teleports the player to a random location
	
	//This method randomly teleports the player to a new location
	public void doTeleport()
	{
		boolean validTransport=false;
		Random rand=new Random();
		//Loop to make sure the player isn't being teleported on top of rubble
		while (validTransport==false)
		{
			xPos=rand.nextInt(boardSize);
			yPos=rand.nextInt(boardSize);
			if(robots[yPos][xPos]==0)
				validTransport=true;
		}
	}
	
	//This method draws the game board after every move

	//This method draws the player, rubble, and robots after every move	
	public void draw(int level, int safeTeleports)
	{
		StdDraw.clear();
		for(int i=0;i<gameBoard.length;i++)
		{
			for (int j=0;j<gameBoard[1].length;j++)
			{	
				//Draws all of the game squares
				StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
				StdDraw.filledRectangle((j*4)+2, 2+(i*4), 1.95, 1.95);
				
				//draws player
				if (yPos==i && xPos==j) //draws player
				{
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.filledCircle((j*4)+2, 2+(i*4),1.5);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle((j*4)+2, 2+(i*4),1);
					StdDraw.setPenColor(StdDraw.MAGENTA);
					StdDraw.filledSquare((j*4)+2, 2+(i*4),.55);
				}
				
				//draws robots
				if (robots[i][j]==1)
				{
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle((j*4)+2, (i*4)+2, 1);
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.filledCircle((j*4)+2, (i*4)+2, .75);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle((j*4)+2, (i*4)+2, .25);
				}
				
				//draws rubble
				else if (robots[i][j]==2)
				{
					StdDraw.setPenColor(StdDraw.PINK);
					StdDraw.filledCircle((j*4)+2, (i*4)+2, 1);
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.filledCircle((j*4)+2, (i*4)+2, .75);
					StdDraw.setPenColor(StdDraw.YELLOW);
					StdDraw.filledCircle((j*4)+2, (i*4)+2, .5);
				}
			}
		}
		//updates text at the top of the board
		StdDraw.setPenColor(StdDraw.BOOK_BLUE);
		StdDraw.text(boardSize*.75, boardSize*4+10, "Level: " + level);
		StdDraw.text(boardSize*1.75, boardSize*4+10, "Robots: " + robotCount);
		StdDraw.text(boardSize*3, boardSize*4+10, "Safe Teleports: " + safeTeleports);
		StdDraw.text(boardSize*1.75, boardSize*4+4, "Moves: " + moveCount);
		StdDraw.show(10);
	}
}
