package mtdm.dk;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import mtdm.dk.labyrinth.Labyrinth;
import mtdm.dk.labyrinth.LabyrinthGen;
import mtdm.dk.solvers.WallFollower;
import mtdm.dk.solvers.Solver;
import mtdm.dk.solvers.recursiveSolver;

public class Sketch extends PApplet{

  Thread t1;
  Thread t2;

  static PGraphics g;
  static double sqrWidth;
  static double sqrHeigth;
  static Labyrinth maze;
  PImage path;
  PImage wall;
  Solver solver;
  byte solverID;
  int Height;
  int Width;
  int desire;

  public Sketch(byte solverID, byte MaksimumBranches,int Width,int Height,int desire){
    this.solverID = solverID;
    if(Width * Height > 800){
      System.out.println("creating labyrinth, this might take a while");
    }
    maze = LabyrinthGen.maze(g, Width, Height, MaksimumBranches);
    System.out.println("labyrinth finished");
    Point start = maze.findPath();
    Point end = maze.findPath(start);
    switch (solverID) {
      case 0:
        solver = new WallFollower(maze, start.X, start.Y, end.X, end.Y);
        break;
      case 1:
        solver = new recursiveSolver(maze, start.X, start.Y, end.X, end.Y);
        break;
      default:
        solver = new recursiveSolver(maze, start.X, start.Y, end.X, end.Y);
      break;
    }
    this.desire  = desire;
  }
  public void main() {
    //TODO make multi threaded draw and calc
    PApplet.main("Sketch");
  }
  @Override
  public void settings() {
    path = this.loadImage("icons/path.png");
    size((int) Math.floor(desire/maze.width) * maze.width, (int) (Math.floor(desire/maze.height)) * maze.height);
    Height = height;
    Width = width;
  }
  @Override
  public void setup() {
    // surface.setResizable(true);
    SetSquares();
    g = getGraphics();
    strokeWeight(2);
    frameRate(60);
    // System.out.println(maze.toString());
    maze.saveLaborinth("@");
  }
  
  @Override
  public void draw(){
    if (Height  != height || Width != height){
      Height = height;
      Width = width;
      SetSquares();
    }
    drawLaborinth();
    move();
    drawSolver();
  }
  private void drawLaborinth(){
    for(int i = 0; i < maze.width;i++){
      for(int j = 0; j < maze.height;j++){
        g.strokeWeight(0);
        if (maze.isPath(i,j)){
          g.fill(255,128,0);
          g.rect((float) (i*sqrWidth),(float) (j*sqrHeigth),(float)sqrWidth, (float)sqrHeigth);
        }else{
          g.fill(75);
          g.rect((float) (i*sqrWidth),(float) (j*sqrHeigth),(float) sqrWidth, (float)sqrHeigth);
        }
      }
    }
  }
  private void SetSquares(){
    sqrWidth = width / ((double)maze.width);
    sqrHeigth = height/((double)maze.height);
  }
  private void move(){
    try{
      if (!t1.isAlive){
        t1.start(1);
        t1.run();
      }
    }catch(Exception e){
      t1 = solver.callMovement();
      t1.start(1);
      t1.run();
    }
  }
  private void drawSolver(){
    try{
      if (!t2.isAlive){
        t2.start(g,sqrWidth,sqrHeigth);
        t2.run();
      }
    }catch(Exception e){
      t2 = solver.callDrawing();
      t2.start(g,sqrWidth,sqrHeigth);
      t2.run();
    }
  }
  public boolean goal(){
    return solver.complete();
  }
}