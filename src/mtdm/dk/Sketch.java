package mtdm.dk;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import mtdm.dk.labyrinth.Labyrinth;
import mtdm.dk.labyrinth.LabyrinthGen;
import mtdm.dk.solvers.WallFollower;

public class Sketch extends PApplet{

  final int width = 700;
  final int heigth = 400;
  static PGraphics g;
  static int sqrWidth;
  static int sqrHeigth;
  static Labyrinth maze = LabyrinthGen.maze(g);
  PImage path;
  PImage wall;
  WallFollower solver = new WallFollower(maze, 1, 1, maze.width-1, maze.height-1);

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
  }
  @Override
  public void draw(){
    drawLaborinth();
    move();
  }
  public void drawLaborinth(){
    for(int i = 0; i < maze.width;i++){
      for(int j = 0; j < maze.height;j++){
        if (maze.isPath(i,j)){
          g.image(path, i*sqrWidth, j*sqrHeigth,sqrWidth,sqrHeigth);
        }else{
          g.fill(0,0,255);
          g.rect(i*sqrWidth, j*sqrHeigth, sqrWidth, sqrHeigth);
        }
      }
    }
  }
  public void move(){
    solver.move(1);
  }
}