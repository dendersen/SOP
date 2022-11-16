package mtdm.dk.labyrinth;

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
      return brute(width-4,height-4,true);
    }else{
      return brute(4,4,false);
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
      if(x > width|| y > height || x < 0|| y < 0){
        if(up){
          x = width;
          y = height;
        }else{
          x = 0;
          y = 0;
        }
        while(true){
          if(
            isPath(x, y) || 
            isPath(x+1, y) || 
            isPath(x-1, y) || 
            isPath(x, y+1) || 
            isPath(x, y-1)
          ){
            modifyLaborinth(x, y, true);
            return new Point(x,y);
          }
          if(up){
            x--;
            if(x < 0){
              x = width;
              y--;
            }
          }else{
            x++;
            if(x > width){
              x = 0;
              y++;
            }
          }
        }
      }
      if(up){
        x--;
        y--;
      }else{
        x++;
        y++;
      }
    }
  }
  public Point findPath(Point notSame){
    while (true){
      Point pos = brute(4,4,false);
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
    System.out.println("start");
    
    labyrinthSaver writer = new labyrinthSaver();
    writer.start(toString(), regex);
    Thread t = new Thread(writer);
    t.start();
    System.out.println("end");
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
