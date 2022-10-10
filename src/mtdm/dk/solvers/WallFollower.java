package mtdm.dk.solvers;

import java.util.ArrayList;

import mtdm.dk.labyrinth.Labyrinth;

public class WallFollower {
  
  private Labyrinth maze;
  byte orientation = 0;//0 = up follows the clock as it increases
  ArrayList <Integer[]> path = new ArrayList <Integer[]>();
  int X;
  int Y;
  int goalX;
  int goalY;

  public WallFollower(Labyrinth labyrinth, int startX,int startY,int endX,int endY){
    this.maze = labyrinth;
    this.X = startX;
    this.Y = startY;
  }
  public void move(int maxSteps) {
    for (int i = 0; i < maxSteps; i++){
      movement();
    }
  }
  private void movement() {
    turnWithClock();
    while (!canMove()) {
      turnAgainstClock();
      savePoint(false);
    }
    savePoint(true);
    doMovement();
  }
  /**
   * @param didMove
   */
  private void savePoint(Boolean didMove) {
    int move = (didMove ? 1 : 0);
    Integer[] state;
    state = new Integer[]{X,Y,(int) orientation, move};
    path.add(state);
  }
  private boolean canMove() {
    switch(orientation){
      case 0:
      return maze.isPath(X, Y-1);
      case 1:
      return maze.isPath(X+1, Y);
      case 2:
      return maze.isPath(X, Y+1);
      case 3:
      return maze.isPath(X, Y+1);
    }
    System.out.println("error in orientation");
    System.out.print(orientation);
    System.out.println(" is not an orientation");
    return false;
  }
  private void doMovement() {
    switch(orientation){
      case 0:
      Y-=1;
      return;
      case 1:
      X+=1;
      return;
      case 2:
      Y+=1;
      return;
      case 3:
      X-=1;
      return;
    }
    System.out.println("error in commiting movement");
    System.out.print(orientation);
    System.out.println(" is not an orientation");
  }
  private void turnAgainstClock() {
    orientation = (byte) ((orientation+3)%4);
  }
  private void turnWithClock() {
    orientation = (byte) ((orientation+1)%4);
  }
}