package mtdm.dk;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import mtdm.dk.labyrinth.Labyrinth;
import mtdm.dk.labyrinth.LabyrinthGen;
import mtdm.dk.solvers.multiSolver;

public class Sketch extends PApplet{

  final int width = 700;
  final int heigth = 700;
  static PGraphics g;
  static int sqrWidth;
  static int sqrHeigth;
  static Labyrinth maze;
  PImage path;
  PImage wall;
  multiSolver solver;
  byte solverID;

  public Sketch(byte solverID, byte MaksimumBranches){
    this.solverID = solverID;
    maze = LabyrinthGen.maze(g, 20, 20, MaksimumBranches);
    Point start = maze.findPath();
    Point end = maze.findPath();
    solver = new multiSolver(solverID,maze, start.X, start.Y, end.X, end.Y);
  }
  public void main() {
    PApplet.main("Sketch");
  }
  @Override
  public void settings() {
    path = this.loadImage("icons/path.png");
    size(width, heigth);
  }
  @Override
  public void setup() {
    sqrWidth = width/maze.width;
    sqrHeigth = heigth/maze.height;
    g = getGraphics();
    strokeWeight(2);
    frameRate(10);
    System.out.println(maze.toString());
    maze.saveLaborinth("@");
  }
  
  @Override
  public void draw(){
    drawLaborinth();
    move();
    drawSolver();
  }
  private void drawLaborinth(){
    for(int i = 0; i < maze.width;i++){
      for(int j = 0; j < maze.height;j++){
        g.strokeWeight(0);
        if (maze.isPath(i,j)){
          g.image(path, i*sqrWidth, j*sqrHeigth,sqrWidth,sqrHeigth);
        }else{
          g.fill(75);
          g.rect(i*sqrWidth, j*sqrHeigth, sqrWidth, sqrHeigth);
        }
      }
    }
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