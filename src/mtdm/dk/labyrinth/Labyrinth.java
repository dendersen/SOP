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
  public Point findPath(){
    while (true){
      int x = (int) Math.floor(Math.random()*width);
      int y = (int) Math.floor(Math.random()*height);
      if(isPath(x, y)){
        Point pos = new Point(x, y);
        return pos;
      }
    }
  }
  public Point findPath(Point notSame){
    while (true){
      Point pos = findPath();
      if(isPath(pos.X, pos.Y) && !(notSame.X == pos.X && notSame.Y == pos.Y)){
        return pos;
      }
    }
  }
}
