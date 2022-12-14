package mtdm.dk.solvers;

import java.util.ArrayList;


import mtdm.dk.Thread;
import mtdm.dk.bigO;
import mtdm.dk.labyrinth.Labyrinth;
import processing.core.PGraphics;

public class WallFollower extends Solver{
  
  private int X;
  private int Y;
  private Labyrinth maze;
  private byte orientation = 0;//0 = up follows the clock as it increases
  private ArrayList <Integer[]> path = new ArrayList <Integer[]>();
  private boolean isGenerated = false;
  private boolean isGenerated2 = false;
/**
   * @param labyrinth the labyrinth that the program should work on
   * @param startX the starting x coordinate of the algorithm
   * @param startY the starting y coordinate of the algorithm
   * @param endX the ending x coordinate of the algorithm
   * @param endY the ending y coordinate of the algorithm
 */
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
  /**
   * does a movement
   */
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
   * saves a point to the path
   * @param didMove tells the method if the point to be saved had a movement in it
   */
  private void savePoint(Boolean didMove) {
    int move = (didMove ? 1 : 0);
    Integer[] state;
    state = new Integer[]{X,Y,(int) orientation, move};
    path.add(state);
  }
  /**
   * @return wether or not there is a wall in front of the algorithm
   */
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
  /**
   * commits to a movement
   */
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
  /**
   * turns the algoritmn against the clock
   */
  private void turnAgainstClock() {
    orientation = (byte) ((orientation+3)%4);
  }
  /**
   * turns the algoritmn with the clock
   */
  private void turnWithClock() {
    orientation = (byte) ((orientation+1)%4);
  }
  /**
   * tells you wether the program has found a solution
   */
  @Override
  public boolean complete(){
    return X == goalX && Y == goalY;
  }
  /**
   * returns a movement
   */
  @Override
  public Thread callMovement() {
    mover a = new mover(!isGenerated);
    isGenerated=true;
    return a;
  }
  /**
   * returns a Thread capable of drawing this algorithm to the screen 
   */
  @Override
  public Thread callDrawing() {
    drawer a = new drawer(!isGenerated2);
    isGenerated2 = true;
    return a;
  }
  /**
   * the thread that handles movement for this algorithm
   */
  public class mover extends Thread {
    /**
     * the number of times the calculations should repeat per call
     */
    int step;
    /**
     * whether this thread will do anything
     */
    boolean isReal;
    /**
     * creates the Thread
     * @param real wether this Thread will be real
     */
    public mover(boolean real){
      isReal = real;
    }
    /**
     * prepares for calculations
     */
    @Override
    public void start(int steps){
      this.step = steps;
    }
    /**
     * starts calculations
     */
    public void run(){
      if(isReal){
        for (int i = 0; i < this.step && !(X == goalX && Y == goalY); i++){
          movement();
        }
      }
    }
  }
/**
 * the thread that draws this algorithm to the screen
 */
  public class drawer extends Thread {
    /**
     * whether this thread will do anything
     */
    boolean isReal;
    PGraphics g;
    double sqrWidth;
    double sqrHeigth;
    /**
     * @param real wether this Thread will be real
     */
    public drawer(boolean real){
      this.isReal = real;
    }
    @Override
    public void start(PGraphics g,  double sqrWidth, double sqrHeigth){
      this.g = g;
      this.sqrWidth = sqrWidth;
      this.sqrHeigth = sqrHeigth;
    }
    
    public void run(){
      if(isReal){
        g.strokeWeight(5);
        g.stroke(0, 255, 0);
        for (int i = 1; i < path.size();i++){
          bigO.arrayAcces+=2;
          Integer[] point1 = path.get(i);
          Integer[] point2 = path.get(i-1);
          double offsetX1 = 0;
          double offsetY1 = 0;
          double offsetX2 = 0;
          double offsetY2 = 0;
          
          bigO.arrayAcces++;
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
          bigO.arrayAcces++;
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
            (float) ((point1[0]*sqrWidth) + offsetX1), 
            (float) ((point1[1]*sqrHeigth) + offsetY1), 
            (float) ((point2[0]*sqrWidth) + offsetX2),
            (float) ((point2[1]*sqrHeigth) + offsetY2)
          );
        }//draw start and end points
        {
          g.strokeWeight(12);
          g.stroke(0,255,255);
          g.point((float) (startX*sqrWidth + sqrWidth/2),(float) (startY*sqrHeigth + sqrHeigth/2));
          g.stroke(255,255,255);
          g.point((float) (goalX*sqrWidth + sqrWidth/2),(float) (goalY*sqrHeigth + sqrHeigth/2));
        }
      }
    }
  }

  @Override
  public int getLength() {
    int length = 1;
    bigO.arrayAcces++;
    Integer[] temp = path.get(0);
    for (int i = 1; i < path.size();i++){
      bigO.arrayAcces+=2;
      if(temp[0] != path.get(i)[0] || temp[2] != path.get(i)[2]){
        length++;
        bigO.arrayAcces++;
        temp = path.get(i);
      }
    }
    return length;
  }
  @Override
  public void move(int steps) {
    System.out.println("this method does not work for WallFollower");
  }
  @Override
  public void draw(PGraphics g, double sqrWidth, double sqrHeigth) {
    System.out.println("this method does not work for WallFollower");
  }
}