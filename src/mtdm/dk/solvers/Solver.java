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
  public abstract void move(int steps);
  public abstract void draw(PGraphics g,double sqrWidth, double sqrHeigth);
  public abstract boolean complete();
  public abstract Thread callMovement();
  public abstract Thread callDrawing();
  public abstract int getLength();
  public void swapPoints(){};
}