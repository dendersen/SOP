package mtdm.dk;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Sketch extends PApplet{

  static int width = 1000;
  static int heigth = 1000;
  static PGraphics g;
  static int sqrWidth;
  static int sqrHeigth;
  static Labyrinth a = new Labyrinth(100,50);

  public void main() {
    PApplet.main("Sketch");
  }
  
  @Override
  public void settings() {
    size(width, heigth);
  }
  
  @Override
  public void setup() {
    sqrWidth = width/a.width;
    sqrHeigth = heigth/a.height;
    g = getGraphics();
    strokeWeight(2);
  }
  @Override
  public void draw(){
    run();
  }
  public static void run(){
    for(int i = 0; i < a.width;i++){
      for(int j = 0; j < a.height;j++){
        if (a.isPath(i,j)){
          g.fill(255,0,0);
        }else{
          g.fill(0,0,255);
        }
        g.rect(i*sqrWidth, j*sqrHeigth, sqrWidth, sqrHeigth);
      }
    }
  }
}