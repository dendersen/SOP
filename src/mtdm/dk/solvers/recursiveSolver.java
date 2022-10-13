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
    for (int i = 0; i < steps && !succes;i++){
      movement();
    }
  }
  private void movement(){
    ArrayList<Point> newPoints = new ArrayList<Point>(); 
    while(currentPoints.size() > 0){
      Point current = currentPoints.remove(0);
      if (checkPoint(current)){
        newPoints = addPrepPoints(newPoints, current);
      }
      accesedPoints.add(current);
    }
    currentPoints = newPoints;
  }
  private boolean checkPoint(Point current) {
    return maze.isPath(current.X, current.Y)
    && 
    !accesedPoints.contains(current);
  }
  private ArrayList<Point> addPrepPoints(ArrayList<Point> newPoints, Point current) {
    newPoints.add(new Point(current.X+1,current.Y));
    newPoints.add(new Point(current.X-1,current.Y));
    newPoints.add(new Point(current.X,current.Y+1));
    newPoints.add(new Point(current.X,current.Y-1));
    return newPoints;
  }
  public void draw(PGraphics g,  int sqrWidth, int sqrHeigth){
    
  }
}