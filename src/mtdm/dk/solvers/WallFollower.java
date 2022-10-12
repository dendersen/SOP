package mtdm.dk.solvers;

import java.util.ArrayList;

import mtdm.dk.labyrinth.Labyrinth;
import processing.core.PGraphics;

public class WallFollower {
  
  private Labyrinth maze;
  byte orientation = 0;//0 = up follows the clock as it increases
  public ArrayList <Integer[]> path = new ArrayList <Integer[]>();
  int X;
  int Y;
  int goalX;
  int goalY;
  int startX;
  int startY;

  public WallFollower(Labyrinth labyrinth, int startX,int startY,int endX,int endY){
    this.maze = labyrinth;
    this.X = startX;
    this.Y = startY;
    this.startX = startX;
    this.startY = startY;
    this.goalX = endX;
    this.goalY = endY;
    Integer[] start = {X,Y,0,0};
    path.add(start);
  }
  public void move(int maxSteps) {
    for (int i = 0; i < maxSteps && !(X == goalX && Y == goalY); i++){
      movement();
    }
  }
  private void movement() {
    turnWithClock();
    while (!canMove()) {
      turnAgainstClock();
      savePoint(false);
    }
    // savePoint(true);
    doMovement();
    savePoint(true);
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
      return maze.isPath(X-1, Y);
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
  public void draw(PGraphics g,int sqrWidth,int sqrHeigth){
    g.strokeWeight(5);
    g.stroke(0, 255, 0);
    for (int i = 1; i < path.size();i++){
      Integer[] point1 = path.get(i);
      Integer[] point2 = path.get(i-1);
      int offsetX1 = 0;
      int offsetY1 = 0;
      int offsetX2 = 0;
      int offsetY2 = 0;
      
      switch (point1[2]) {
        case 0:
          offsetX1 = sqrWidth;
          offsetY1 = sqrHeigth/2;
        break;
        case 1:
          offsetX1 = sqrWidth/2;
          offsetY1 = sqrHeigth;
        break;
        case 2:
          offsetX1 = 0;
          offsetY1 = sqrHeigth/2;
        break;
        case 3:
          offsetX1 = sqrWidth/2;
          offsetY1 = 0;
        break;
      }
      switch (point2[2]) {
        case 0:
          offsetX2 = sqrWidth;
          offsetY2 = sqrHeigth/2;
        break;
        case 1:
          offsetX2 = sqrWidth/2;
          offsetY2 = sqrHeigth;
        break;
        case 2:
          offsetX2 = 0;
          offsetY2 = sqrHeigth/2;
        break;
        case 3:
          offsetX2 = sqrWidth/2;
          offsetY2 = 0;
        break;
      }
      g.line(
        (point1[0]*sqrWidth) + offsetX1, 
        (point1[1]*sqrHeigth) + offsetY1, 
        (point2[0]*sqrWidth) + offsetX2,
        (point2[1]*sqrHeigth) + offsetY2
      );
    }//draw start and end points
    {
      g.strokeWeight(12);
      g.stroke(0,255,255);
      g.point(startX*sqrWidth + sqrWidth/2, startY*sqrHeigth + sqrHeigth/2);
      g.stroke(255,255,255);
      g.point(goalX*sqrWidth + sqrWidth/2, goalY*sqrHeigth + sqrHeigth/2);
    }
  }
}