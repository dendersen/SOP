package mtdm.dk;

import processing.core.PApplet;

public class Main {
  public static void main(String[] args) {
    String[] processingArgs = {"Sketch"};
    Sketch draw = new Sketch();
    PApplet.runSketch(processingArgs,draw);
  }
}