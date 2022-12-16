package mtdm.dk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import processing.core.PApplet;

public class Main {
  static Sketch draw;
  public static void main(String[] args) throws InterruptedException {
    Instant start = Instant.now();
    
    String[] processingArgs = {"Sketch"};

    int sizeX = 435;
    int sizeY = 435;

    draw = new Sketch((byte) 1,10,4,sizeX,sizeY,1200,-1,5,4);
    PApplet.runSketch(processingArgs,draw);

    while (!draw.goal()) {
      Thread.sleep(100);
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
    System.out.println("total array acces: " + (bigO.arrayAcces + (int)(Math.floor(bigO.pathCheck*1.5))));
    {
      String text = sizeX*sizeY + "," + bigO.pathCheck + "," + bigO.arrayAcces + (int)(Math.floor(bigO.pathCheck*1.5));
      String data = "";
      File file = new File("note.csv");
      try (Scanner myReader = new Scanner(file)) {
        while (myReader.hasNextLine()) {
          data += myReader.nextLine();
          data += "\n";
        }
      } catch (FileNotFoundException e) {
        try {
          file.createNewFile();
        } catch (IOException e1) {}
      }
      try {
        FileWriter myWriter = new FileWriter("note.csv");
        myWriter.write(data + text);
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
  }
}