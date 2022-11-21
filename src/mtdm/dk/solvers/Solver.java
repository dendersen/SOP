package mtdm.dk.solvers;

import processing.core.PGraphics;
import mtdm.dk.Thread;
import mtdm.dk.labyrinth.Labyrinth;

public abstract class Solver {
  public Thread Calc;
  public Thread Draw;
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
      default:
        return new recursiveSolver(maze, startX, startY, endX, endY);
    }
  }
  public void move(int steps){
    System.out.println("this  method has not  been  implemented in  this class");
  }
  public void draw(PGraphics g,double sqrWidth, double sqrHeigth){
    System.out.println("this  method has not  been  implemented in  this class");
  }
  public boolean complete(){
    System.out.println("this  method has not  been  implemented in  this class");
    return false;
  }
  public abstract Thread callMovement();
  public abstract Thread callDrawing();
  public void swapPoints() {}
  //TODO: recurse but random
  //TODO: just random
}