package mtdm.dk.labyrinth;

import processing.core.PGraphics;
import processing.core.PImage;

public class Labyrinth{
  /**@param labyrinthTile [x][y]: true = path*/
  private boolean[][] labyrinthTile;
  /**@param width the width of the labyrinth in tiles*/
  public int width;
  /**@param width the height of the labyrinth in tiles*/
  public int height;
  PGraphics g;
  public PImage path;
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
   * 
   * @param x
   * @param y
   * @return whether or not the given coordinates contain a path
   */
  public boolean isPath(int x,int y){
    return labyrinthTile[x][y];
  }
}
