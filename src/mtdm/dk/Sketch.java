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

  public Sketch(byte solverID, byte MaksimumBranches,int Width,int Height){
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
        solver = new Solver();
      break;
    }
  }
  public void main() {
    //TODO make multi threaded draw and calc
    PApplet.main("Sketch");
  }
  @Override
  public void settings() {
    path = this.loadImage("icons/path.png");
    if(maze.width > 500 || maze.height > 500){
      size(1000, 1000);
    }else if (maze.width > 333 || maze.height > 333){
      size(width*2, height*2);
    }else if (maze.width > 250 || maze.height > 250){
      size(width*3, height*3);
    }else if (maze.width > 200 || maze.height > 200){
      size(width*4, height*4);
    }else if (maze.width > 167 || maze.height > 167){
      size(width*5, height*5);
    }else{
      size(width*6, height*6);
    }
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
    System.out.println(maze.toString());
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
    solver.move(1);
  }
  private void drawSolver(){
    solver.draw(g,sqrWidth,sqrHeigth);
  }
  public boolean goal(){
    return solver.complete();
  }
}