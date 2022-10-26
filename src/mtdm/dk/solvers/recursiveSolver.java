package mtdm.dk.solvers;

import java.util.ArrayList;

import mtdm.dk.Point;
import mtdm.dk.labyrinth.Labyrinth;
import processing.core.PGraphics;

public class recursiveSolver extends Solver{
  Labyrinth maze;
  int X;
  int Y;
  int goalX;
  int goalY;
  int startX;
  int startY;
  ArrayList<Point> accesedPoints = new ArrayList<Point>();
  ArrayList<Point> currentPoints = new ArrayList<Point>();
  boolean begun = false;
  boolean succes = false;
  int end = -1;
  
  /**
   * @param labyrinth
   * @param startX
   * @param startY
   * @param endX
   * @param endY
   */
  public recursiveSolver(Labyrinth labyrinth, int startX, int startY,int endX, int endY){
    this.maze = labyrinth;
    this.X = startX;
    this.Y = startY;
    this.startX = startX;
    this.startY = startY;
    this.goalX = endX;
    this.goalY = endY;
    currentPoints.add(new Point(startX,startY));
  }
  private class calc extends Thread{
    int steps;
    public calc(int  steps){
      this.steps = steps;
    }
    public void run(){
      if (!begun){
        begun = true;
        return;
      }
      if(finished()){
        end = finishedPoint();
      }else{
        for (int i = 0; i < steps && !succes;i++){
          movement();
        }
      }
    }
  }
  public void move(int steps){
    calc mover = new calc(steps);
    Thread t = mover;
    t.run();
  }
  private void movement(){
      ArrayList<Point> newPoints = new ArrayList<Point>(); 
    while(currentPoints.size() > 0){
      Point current = currentPoints.remove(0);
      if (checkPoint(current)){
        newPoints = addPrepPoints(newPoints, current);
        accesedPoints.add(current);
      }
    }
    currentPoints = newPoints;
  }
  private boolean checkPoint(Point current) {
    boolean bool = true;
    for (int i = 0; i < accesedPoints.size() && bool;i++){
      bool = !(accesedPoints.get(i).X == current.X && accesedPoints.get(i).Y == current.Y);
    }
    return maze.isPath(current.X, current.Y) && bool;
  }
  private ArrayList<Point> addPrepPoints(ArrayList<Point> newPoints, Point current) {
    newPoints.add(new Point(current.X+1,current.Y,current));
    newPoints.add(new Point(current.X-1,current.Y,current));
    newPoints.add(new Point(current.X,current.Y+1,current));
    newPoints.add(new Point(current.X,current.Y-1,current));
    return newPoints;
  }
  @Override
  public void draw(PGraphics g,  double sqrWidth, double sqrHeigth){
    if (this.end == -1){
      
      g.fill(0, 255, 0,200f);
      for (int i = 0; i < currentPoints.size();i++){
        g.rect((float) (currentPoints.get(i).X * sqrWidth),(float) (currentPoints.get(i).Y*sqrHeigth), (float) sqrWidth,(float) sqrHeigth);
      }
      g.fill(0,0 ,255,200f);
      for (int i = 0; i < accesedPoints.size();i++){
        g.rect((float) (accesedPoints.get(i).X * sqrWidth),(float) (accesedPoints.get(i).Y*sqrHeigth), (float) sqrWidth,(float) sqrHeigth);
      }
    }else{
      g.fill(0,0 ,255,100f);
      for (int i = 0; i < accesedPoints.size();i++){
        g.rect((float) (accesedPoints.get(i).X * sqrWidth),(float) (accesedPoints.get(i).Y*sqrHeigth), (float) sqrWidth,(float) sqrHeigth);
      }
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
  }
  private void drawRecurse(PGraphics g,Point start,double sqrWidth,double sqrHeigth) {
    if (start.path.X == -1) return;
    drawRecurse(g, start.path, sqrWidth, sqrHeigth);
    g.fill(255);
    g.rect((float) (start.X * sqrWidth),(float) (start.Y*sqrHeigth),(float) sqrWidth,(float) sqrHeigth);
  }
  private boolean finished(){
    for (int i = 0; i < currentPoints.size();i++){
      if (currentPoints.get(i).X == goalX && currentPoints.get(i).Y == goalY){
        return true;
      }
    }
    return false;
  }
  private int finishedPoint(){
    for (int i = 0; i < currentPoints.size();i++){
      if (currentPoints.get(i).X == goalX && currentPoints.get(i).Y == goalY){
        return i;
      }
    }
    return -1;
  }
  public boolean complete(){
    return end != -1;
  }
}