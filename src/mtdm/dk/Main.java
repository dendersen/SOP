package mtdm.dk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import processing.core.PApplet;
/**
 * this class is the runnable class of this program
 */
public class Main {
  /**
   * this contains the program and runns the algorithms
   */
  private static Sketch draw;
  
  /**
   * the main method that runs and manages all other parts of the program
   * @param args un implimented
   * @throws InterruptedException should not be throw but would indicate a fatal error happening in a thread
   */
  public static void main(String[] args) throws InterruptedException {
    Instant start = Instant.now();
    
    String[] processingArgs = {"Sketch"};

    int sizeX = 100;
    byte ID = 0;
    int loader = -1;
    // 50,100,150,200,250,300
    int sizeY = sizeX;
    draw = new Sketch(ID,-1,4,sizeX,sizeY,1200,loader,6,4);
    PApplet.runSketch(processingArgs,draw);

    while (!draw.goal()) {
      Thread.sleep(100);
    }
    
    Instant end = Instant.now();
    Duration timeElapsed = Duration.between(start, end);
    System.out.println("\nTime taken: "+ timeElapsed.toMillis() +" milliseconds");
    
    int real = draw.getLength();
    int opti = draw.optimalLength();
    
    textOutput(real, opti);
    
    BigOContoller(sizeX, sizeY,ID,draw.getLength());
    
  }
  /**
   * writes some information about the solution to the console
   * @param real the length of the solution found by the algorithm
   * @param opti the smallest posible solution for a labyrinth of this size
   */
  private static void textOutput(int real, int opti) {
    System.out.println("real length: " + real);
    System.out.println("best length: " + opti);
    System.out.println("effeciency: " + ((float)opti/(float)real));
    System.out.println("path checks: " + bigO.pathCheck);
    System.out.println("total array acces: " + (bigO.arrayAcces + (int)(Math.floor(bigO.pathCheck*1.5))));
  }
  /**
   * saves some information about the solution to a text file so that it can be interpreted at a later time, in the same document
   * @param sizeX the number of tiles the labyrinth had along the x aksis
   * @param sizeY the number of tiles the labyrinth had along the Y aksis
   * @param ID the ID of the solver used
   * @param length the length of the solution the found by thee algorithm
   */
  private static void BigOContoller(int sizeX, int sizeY, byte ID,int length) {
    String text = sizeX*sizeY + "," + bigO.pathCheck + "," + bigO.arrayAcces + (int)(Math.floor(bigO.pathCheck*1.5))+ "," + ID + "," + length;
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