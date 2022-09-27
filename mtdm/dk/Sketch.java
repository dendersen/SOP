package mtdm.dk;

import processing.core.PApplet;

public class Sketch extends PApplet{

  int width = 100;
  int heigth = 100;

  public void main() {
    PApplet.main("Sketch");
  }
  
  @Override
  public void settings() {
    size(width, height);
  }
  
  @Override
  public void setup() {
    strokeWeight(2);
  }
  @Override
  public void draw(){

  }
}