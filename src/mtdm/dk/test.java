package mtdm.dk;

import processing.core.PApplet;

public class test {
  static Sketch draw;
  public static void main(String[] args) {
    String[] processingArgs = {"Sketch"};
    draw = new Sketch();
    PApplet.runSketch(processingArgs,draw);
    
  }
}
