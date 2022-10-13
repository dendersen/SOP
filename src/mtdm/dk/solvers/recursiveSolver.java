package mtdm.dk.solvers;

import java.util.ArrayList;

import mtdm.dk.Point;
import mtdm.dk.labyrinth.Labyrinth;
import processing.core.PGraphics;

public class recursiveSolver {
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
  
  public recursiveSolver(Labyrinth labyrinth, int startX, int startY,int endX, int endY){
    this.maze = labyrinth;
    this.X = startX;
    this.Y = startY;
    this.startX = startX;
    this.startY = startY;
    this.goalX = endX;
    this.goalY = endY;
    currentPoints.add(new Point(startY,startX));
  }
  public void move(int steps){
    if (!begun){
      begun = true;
      return;
    }
    for (int i = 0; i < steps && !succes;i++){
      movement();
    }
  }
  private void movement(){
    ArrayList<Point> newPoints = new ArrayList<Point>(); 
    while(currentPoints.size() > 0){
      Point current = currentPoints.remove(0);
      if (checkPoint(current)){
        addPrepPoints(newPoints, current);
      }
    }
    currentPoints = newPoints;
  }
  private boolean checkPoint(Point current) {
    return maze.isPath(current.X, current.Y)
    && 
    !accesedPoints.contains(current);
  }
  private void addPrepPoints(ArrayList<Point> newPoints, Point current) {
    newPoints.add(new Point(current.X+1,current.Y));
    newPoints.add(new Point(current.X-1,current.Y));
    newPoints.add(new Point(current.X,current.Y+1));
    newPoints.add(new Point(current.X,current.Y-1));
  }
  public void draw(PGraphics g,  int sqrWidth, int sqrHeigth){
    g.fill(80,0,0);
    g.rect(startX*sqrWidth, startY*sqrHeigth, sqrWidth, sqrHeigth);
    g.fill(230,0,0);
    g.rect(goalX*sqrWidth, goalY*sqrHeigth, sqrWidth, sqrHeigth);
    
    g.fill(255,0 ,10,255/2);
    for (int i = 0; i < accesedPoints.size();i++){
      g.rect(accesedPoints.get(i).X * sqrWidth, accesedPoints.get(i).Y*sqrHeigth, sqrWidth, sqrHeigth);
    }
    g.fill(255,0 ,10,255/2);
    for (int i = 0; i < currentPoints.size();i++){
      g.rect(currentPoints.get(i).X * sqrWidth, currentPoints.get(i).Y*sqrHeigth, sqrWidth, sqrHeigth);
    }
  }
}