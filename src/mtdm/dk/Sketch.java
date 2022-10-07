package mtdm.dk;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import mtdm.dk.labyrinth.Labyrinth;
import mtdm.dk.labyrinth.LabyrinthGen;

public class Sketch extends PApplet{

  static int width = 800;
  static int heigth = 600;
  static PGraphics g;
  static int sqrWidth;
  static int sqrHeigth;
  static Labyrinth maze = LabyrinthGen.maze(g);
  PImage path;
  PImage wall;

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
    run();
    loadImage(DXF);
  }
  public void run(){
    for(int i = 0; i < maze.width;i++){
      for(int j = 0; j < maze.height;j++){
        if (maze.isPath(i,j)){
          g.fill(255,0,0);
          g.image(g, i, j);
        }else{
          g.fill(0,0,255);
        }
        g.rect(i*sqrWidth, j*sqrHeigth, sqrWidth, sqrHeigth);
      }
    }
  }
}