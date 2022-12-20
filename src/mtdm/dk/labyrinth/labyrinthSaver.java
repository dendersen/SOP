package mtdm.dk.labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 * saves a given string, expects labyrinths
 */
public class labyrinthSaver extends Thread{
/**
 * the string to be writen
 */
  private String mazeString;
  /**
   * prepares the thread
   * @param labyrinth the string to be wrtien to a file
   */
  public void start(String labyrinth){
    mazeString = labyrinth;
  }

  public void run() {
    String data = "";
    File file = new File("Save.txt");
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
    System.out.println(data.length());
    if(data.length() > 1_000_000){
      System.out.println("archiving");
      archive(data,0);
      data = "";
    }
    try {
      FileWriter myWriter = new FileWriter("Save.txt");
      myWriter.write(mazeString + "\n\n" + data);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    System.out.println("safe to close program");
  }
  /**
   * archives text when the file gets too large
   * @param out the string to be saved in this file
   * @param index the index of the file that will be writen to
   */
  private void archive(String out,int index){
    File file = new File("Save"+ index +".txt");
    try {
      Scanner myReader = new Scanner(file);
      myReader.close();
      archive(out, index+1);
    } catch (FileNotFoundException e) {
      try {
        System.out.println("archiving to file" + index);
        file.createNewFile();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    try {
      FileWriter myWriter = new FileWriter("Save"+ index +".txt");
      myWriter.write(out);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
