package mtdm.dk.solvers;

import java.util.ArrayList;
import java.util.Iterator;

import mtdm.dk.Point;
import mtdm.dk.labyrinth.Labyrinth;
import processing.core.PGraphics;
import mtdm.dk.Thread;
import mtdm.dk.bigO;

public class recursiveSolver extends Solver{
  protected Labyrinth maze;
  /**
   * the points that have been accesed before
   */
  protected ArrayList<Point> accesedPoints = new ArrayList<Point>();
  /**
   * the points that have yet to be used
   */
  protected ArrayList<Point> currentPoints = new ArrayList<Point>();
  /**
   * points that are ready to be added to "currentPoints"
   */
  protected ArrayList<Point> newPoints = new ArrayList<Point>();
  /**
   * points ready to be drawn on the screen
   */
  protected ArrayList<Point> toBeDrawn = new ArrayList<Point>();
  
  protected boolean begun = false;
  protected int end = -1;

  //legacy support
  public Thread Calc;
  public Thread Draw;

  /**
   * @param labyrinth the labyrinth that the program should work on
   * @param startX the starting x coordinate of the algorithm
   * @param startY the starting y coordinate of the algorithm
   * @param endX the ending x coordinate of the algorithm
   * @param endY the ending y coordinate of the algorithm
   */
  public recursiveSolver(Labyrinth labyrinth, int startX, int startY,int endX, int endY){
    this.maze = labyrinth;
    this.startX = startX;
    this.startY = startY;
    this.goalX = endX;
    this.goalY = endY;
    currentPoints.add(new Point(startX,startY));
  }
  
  /**
   * legacy but should still work
   * just not very well...
   */
  public void move(int steps){
    this.Calc = new mover();
    this.Calc.start(steps);
    this.Calc.run();
  }
  /**
   * checks if a point has been acced before, 
   * then if it is a path
   * @param current the point being checked
   * @return
   */
  protected boolean checkPoint(Point current) {
    boolean bool = true;
    for (int i = 0; i < accesedPoints.size() && bool;i++){
      bigO.arrayAcces+=2;
      bool = !(accesedPoints.get(i).X == current.X && accesedPoints.get(i).Y == current.Y);
    }
    return maze.isPath(current.X, current.Y) && bool;
  }
  /**
   * creates and adds the neighbors of a point to an ArrayList
   * @param current
   * @return
   */
  protected ArrayList<Point> addPrepPoints(Point current) {
    ArrayList<Point> temp = new ArrayList<Point>();
    bigO.arrayAcces+=4;
    temp.add(new Point(current.X+1,current.Y,current));
    temp.add(new Point(current.X-1,current.Y,current));
    temp.add(new Point(current.X,current.Y+1,current));
    temp.add(new Point(current.X,current.Y-1,current));
    
    return temp;
  }
  
  /**
   * legacy but should still work
   * just not very well...
   */
  @Override
  public void draw(PGraphics g,  double sqrWidth, double sqrHeigth){
    drawer draw = new drawer();
    draw.start(g,sqrWidth,sqrHeigth);
    draw.run();
  }
  /**
   * @return wether a point has reached the end
   */
  protected boolean finished(){
    for (int i = 0; i < currentPoints.size();i++){
        bigO.arrayAcces+=2;
        if (currentPoints.get(i).X == goalX && currentPoints.get(i).Y == goalY){
          return true;
      }
    }
    return false;
  }
  /**
   * @return the point that reached the end if there is one
   */
  protected int finishedPoint(){
    for (int i = 0; i < currentPoints.size();i++){
      bigO.arrayAcces+=2;
      if (currentPoints.get(i).X == goalX && currentPoints.get(i).Y == goalY){
        return i;
      }
    }
    return -1;
  }
  public boolean complete(){
    return end != -1;
  }
  public Thread callMovement(){
    return new mover();
  }
  public Thread callDrawing(){
    return new drawer();
  }

  @Override
  public void swapPoints() {
    currentPoints.addAll(newPoints);
    newPoints.clear();
  }
    /**
   * the thread that handles movement for this algorithm
   */
  public class mover extends Thread{
    int steps;
    boolean isAlive = false;
    public void start(int steps){
      this.steps = steps;
      isAlive = false;
    }
    public void run(){
      isAlive = true;
      if (!begun){
        begun = true;
        return;
      }
      if(finished()){
        end = finishedPoint();
      }else{
        for (int i = 0; i < steps;i++){
          while(currentPoints.size() > 0){
            Point current = currentPoints.remove(0);
            if (checkPoint(current)){
              newPoints.addAll(addPrepPoints(current));
            bigO.arrayAcces++;
            accesedPoints.add(current);
            }
            toBeDrawn.add(current);
          }
        }
      }
      isAlive = false;
    }
  }
  /**
   * the Thread capable of drawing this algorithm to the screen 
   */
  public class drawer extends Thread{
    boolean isAlive = false;
    private PGraphics g;
    private double sqrWidth;
    private double sqrHeigth;
    public void start(PGraphics g,  double sqrWidth, double sqrHeigth){
      this.g = g;
      this.sqrWidth = sqrWidth;
      this.sqrHeigth = sqrHeigth;
    }
    
    public void run(){
      Iterator<Point> point = toBeDrawn.iterator();
      isAlive = true;
      
      g.fill(0,0 ,255,200f);
      if (end != -1){
        g.fill(0,0 ,255,100f);
      }
      for (int i = 0; i < accesedPoints.size();i++){
        g.rect((float) (accesedPoints.get(i).X * sqrWidth),(float) (accesedPoints.get(i).Y*sqrHeigth), (float) sqrWidth,(float) sqrHeigth);
      }
        g.fill(0, 255, 0,200f);
        if (end != -1){
          g.fill(0,255 ,0,100f);
        }
        while (point.hasNext()){
          try {
            Point p = point.next();
            g.rect((float) (p.X * sqrWidth),(float) (p.Y*sqrHeigth), (float) sqrWidth,(float) sqrHeigth);
          } catch (IndexOutOfBoundsException e) {}
        }
        if (end != -1){
        // g.fill(0, 255, 0,100f);
        // for (int i = 0; i < currentPoints.size();i++){
        //   g.rect(currentPoints.get(i).X * sqrWidth, currentPoints.get(i).Y*sqrHeigth, sqrWidth, sqrHeigth);
        // }
        drawRecurse(g,currentPoints.get(end),sqrWidth,sqrHeigth);
      }
      g.fill(255,0,0);
      g.rect((float) (startX*sqrWidth),(float) (startY*sqrHeigth),(float) sqrWidth,(float) sqrHeigth);
      g.fill(0,255,255);
      g.rect((float) (goalX*sqrWidth),(float) (goalY*sqrHeigth),(float) sqrWidth,(float) sqrHeigth);
      isAlive = false;
      toBeDrawn.clear();
    }
    /**
     * 
     * @param start the point that should be recursivly drawn from
     * @param g the graphics objekt on which there should be drawn
     * @param sqrWidth the width of every squares that will be drawn
     * @param sqrHeigth the width of every squares that will be drawn
     */
    private void drawRecurse(PGraphics g,Point start,double sqrWidth,double sqrHeigth) {
      if (start.recursivePoint.X == -1) return;
      drawRecurse(g, start.recursivePoint, sqrWidth, sqrHeigth);
      g.fill(255);
      g.rect((float) (start.X * sqrWidth),(float) (start.Y*sqrHeigth),(float) sqrWidth,(float) sqrHeigth);
    }
  }

  @Override
  public int getLength() {
    int length = 0;
    Point temp = currentPoints.get(end);
    while (temp.recursivePoint.X != -1 && temp.recursivePoint.Y != -1){
      length++;
      temp = temp.recursivePoint;
    }
    
    return length;
  }
}