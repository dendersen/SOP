package mtdm.dk.solvers;

import processing.core.PGraphics;
import mtdm.dk.Thread;
import mtdm.dk.labyrinth.Labyrinth;

public abstract class Solver {
  public Thread Calc;
  public Thread Draw;
  public int startX;
  public int startY;
  public int goalX;
  public int goalY;
  /**
   * @param maze the labyrinth that the program should work on
   * @param startX the starting x coordinate of the algorithm
   * @param startY the starting y coordinate of the algorithm
   * @param endX the ending x coordinate of the algorithm
   * @param endY the ending y coordinate of the algorithm
   * @param id the ID of the chosen class
   * @return the class that coresponds to given ID
   */
  public static Solver generator(int id, Labyrinth maze, int startX,int startY,int endX,int endY){
    switch (id) {
      case 0:
        return new WallFollower(maze, startX, startY, endX, endY);
      case 1:
        return new recursiveSolver(maze, startX, startY, endX, endY);
      case 2:
        return new ManhattanRecurse(maze, startX, startY, endX, endY);
      case 3:
        return new RandomRecurse(maze, startX, startY, endX, endY);
      case 4:
        return new DepthFirstSearch(maze, startX, startY, endX, endY);
      case 5:
        return new WidthFirstSearch(maze, startX, startY, endX, endY);
      default:
        return new recursiveSolver(maze, startX, startY, endX, endY);
    }
  }
  /**
   * calls the algorithm
   * @param steps how many times the algorithm should run
   */
  public abstract void move(int steps);
  /**
   * 
   * @param g the graphics objekt on which there should be drawn
   * @param sqrWidth the width of every squares that will be drawn
   * @param sqrHeigth the height of every squares that will be drawn
   */
  public abstract void draw(PGraphics g,double sqrWidth, double sqrHeigth);
  /**
   * @return wether the algorithm has finished
   */
  public abstract boolean complete();
  /**
   * @return a Thread capable of moving the algorithm
   */
  public abstract Thread callMovement();
  /**
   * @return a Thread capable of drawing the algorithm
   */
  public abstract Thread callDrawing();
  /**
   * @return the of the rute the algorithm took from start to finish
   */
  public abstract int getLength();
  /**
   * a method that is only used by some algorithems
   * it moves points from a pool of calculated point to a pool of drawable points
   */
  public void swapPoints(){};
}