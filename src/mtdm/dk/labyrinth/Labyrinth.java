package mtdm.dk.labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import mtdm.dk.Point;
import processing.core.PGraphics;

public class Labyrinth{
  /**@param labyrinthTile [x][y]: true = path*/
  private boolean[][] labyrinthTile;
  /**@param width the width of the labyrinth in tiles*/
  public int width;
  /**@param width the height of the labyrinth in tiles*/
  public int height;
  PGraphics g;
  /**
   * @param maze each string represents different Y all strings must be the same length
   * @param regex chars that are to be considered paths, may not be special characters or å
   */
  public Labyrinth(String[] maze,String regex,PGraphics g){
    this.g = g;
    width = maze[0].length();
    height = maze.length;
    labyrinthTile = new boolean[maze[0].length()][maze.length];
    char[] chars = regex.toCharArray();
    for (int j = 0; j < height; j++){
      if (!regex.contains(" ")){
        maze[j].replace(" ", "å");
      }
      for (int i = 0; i < chars.length; i++){
        maze[j] = maze[j].replace(chars[i], ' ');
      }
      for (int i = 0; i < width; i++){
        labyrinthTile[i][j] = maze[j].charAt(i) == ' ';
      }
    }
  }
  public Labyrinth(PGraphics g,int Width, int Height){
    this.g = g;
    width = Height;
    height = Width;
    this.labyrinthTile = new boolean[width][height];
    for (int x = 0; x < width; x++){
      for (int y = 0; y < height; y++){
        if(x % 2 == 0){
          this.labyrinthTile[x][y] = true; //true = path
        }
      }
    }
  }
  /**
   * @param x
   * @param y
   * @return whether or not the given coordinates contain a path
   */
  public boolean isPath(int x,int y){
    if(x > width-1 || x < 0 || y > height-1 || y < 0){
      return false;
    }
    return labyrinthTile[x][y];
  }
  public Point findPath(boolean up){
    if (up){
      return brute(width,height,true);
    }else{
      return brute(0,0,false);
    }
  }
  public Point findPath(){
    
    return findPath(true);
  }
  private Point brute (int x,int y, boolean up){
    while(true){
      if (isPath(x, y)){
        return new Point(x,y);
      }
      if (up){
        return brute(x-1,y-1,true);
      }else{
        return brute(x+1,y+1,false);
      }
    }
  }
  public Point findPath(Point notSame){
    while (true){
      Point pos = brute(0,0,false);
      if(isPath(pos.X, pos.Y) && !(notSame.X == pos.X && notSame.Y == pos.Y)){
        return pos;
      }
    }
  }

  /**
   * @param X
   * @param Y
   * @param path whether or not i will become a path
   * @return succes
   */
  public boolean modifyLaborinth(int X, int Y, boolean path){
    if(X < 0 || Y < 0) return false;
    if(X >= width || Y >= height) return false;

    labyrinthTile[X][Y] = path;
    return true;
  }

  public String toString(){
    String out = "{\n";
    for (int i = 0; i < width; i++){
      String temp = "";
      for (int j = 0; j < height; j++){
        temp += (isPath(i,j) ? " ":"█");
      }
      out += "\"" + temp + "\",";
      out += "\n";
    }
    return out + "}";
  }

  public void saveLaborinth(String regex){
    String maze = toString().replaceAll("█", regex);
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
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      try {
        FileWriter myWriter = new FileWriter("Save.txt");
        myWriter.write(data + "\n\n" + maze);
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
  }

  // public int sum(){
  //   int out = 0; 
  //   for (int i = 0; i < width; i++){
  //     for (int j = 0; j < width; j++){

  //     }
  //   }
  //   return 
  // }
}
