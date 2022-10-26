package mtdm.dk;

import java.time.Duration;
import java.time.Instant;

import processing.core.PApplet;
public class test {
  static Sketch draw;
  static Sketch draw2;
  public static void main(String[] args) throws InterruptedException {
    Instant start = Instant.now();

    String[] processingArgs = {"Sketch"};
    draw = new Sketch((byte) 1,(byte) 1,750,750);
    PApplet.runSketch(processingArgs,draw);
    
    while(!draw.goal()){
      Thread.sleep(2);
    }
    if(draw.goal()){
      Instant end = Instant.now();
      Duration timeElapsed = Duration.between(start, end);
      System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
    }
  }
}