package mtdm.dk;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import mtdm.dk.labyrinth.Labyrinth;
import mtdm.dk.labyrinth.LabyrinthGen;
import mtdm.dk.solvers.multiSolver;

public class Sketch extends PApplet{

  final int width = 700;
  final int heigth = 400;
  static PGraphics g;
  static int sqrWidth;
  static int sqrHeigth;
  static Labyrinth maze = LabyrinthGen.maze(g);
  PImage path;
  PImage wall;
  multiSolver solver;;

  public void main() {
    PApplet.main("Sketch");
  }
  
  @Override
  public void settings() {
    path = this.loadImage("icons/path.png");
    size(width, heigth);
    Point start = maze.findPath();
    Point end = maze.findPath();
    solver = new multiSolver((byte) 1,maze, start.X, start.Y, end.X, end.Y);
  }
  
  @Override
  public void setup() {
    sqrWidth = width/maze.width;
    sqrHeigth = heigth/maze.height;
    g = getGraphics();
    strokeWeight(2);
    frameRate(10);
  }
  @Override
  public void draw(){
    drawLaborinth();
    move();
    drawSolver();
  }
  public void drawLaborinth(){
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
  public void move(){
    solver.move(1);
  }
  public void drawSolver(){
    solver.draw(g,sqrWidth,sqrHeigth);
  }
}