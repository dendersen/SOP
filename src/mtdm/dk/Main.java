package mtdm.dk;

import java.time.Duration;
import java.time.Instant;

import processing.core.PApplet;

public class Main {
  static Sketch draw;
  public static void main(String[] args) throws InterruptedException {
    Instant start = Instant.now();
    
    String[] processingArgs = {"Sketch"};
    draw = new Sketch((byte) 1,5,250,300,1200,0,30);
    PApplet.runSketch(processingArgs,draw);
    while (!draw.goal()) {
      System.out.print("");
    }
    
    Instant end = Instant.now();
    Duration timeElapsed = Duration.between(start, end);
    System.out.println("\nTime taken: "+ timeElapsed.toMillis() +" milliseconds");
    
    int real = draw.getLength();
    int opti = draw.optimalLength();
    
    System.out.println("real length: " + real);
    System.out.println("best length: " + opti);
    System.out.println("effeciency: " + ((float)opti/(float)real));
    System.out.println("path checks: " + bigO.pathCheck);
    System.out.println("total array acces: " + (bigO.arrayAcces+bigO.arrayAcces));
  }
}