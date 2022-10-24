package mtdm.dk;

import java.time.Duration;
import java.time.Instant;

import processing.core.PApplet;

public class Main {
  static Sketch draw;
  public static void main(String[] args) throws InterruptedException {
    Instant start = Instant.now();

    String[] processingArgs = {"Sketch"};
    draw = new Sketch((byte) 1,(byte) 1,20,20);
    PApplet.runSketch(processingArgs,draw);
    
    //your code
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