package mtdm.dk.labyrinth;

import processing.core.PGraphics;

public class Labyrinth{
  
  private boolean[][] labyrinthTile;
  public int width;
  public int height;
  PGraphics g;
  /**
   * @param maze each string represents different Y all strings must be the same length
   * @param regex chars that are to be considered paths, may not be special characters
   */
  public Labyrinth(String[] maze,String regex){
    char[] chars = regex.toCharArray();
    for (int j = 0; j < maze.length; j++){
      maze[j].replace(" ", "\\\\");
      for (int i = 0; i < chars.length; i++){
        maze[j] = maze[j].replace(chars[i], ' ');
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
  public boolean isPath(int x,int y){
    return labyrinthTile[x][y];
  }
}
