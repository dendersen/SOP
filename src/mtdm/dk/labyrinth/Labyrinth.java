package mtdm.dk.labyrinth;

import java.util.regex.Pattern;

import processing.core.PGraphics;

public class Labyrinth{
  
  private boolean[][] labyrinthTile;
  int width;
  int height;
  PGraphics g;

  public Labyrinth(boolean[][] walls, PGraphics g){//[x][y] true = path
    this.g = g; 
    for (int i = 0;i < walls.length; i++){
      if(walls[0].length != walls[i].length){
        this.labyrinthTile = new boolean[0][0];
        return;
      }
      
      for (int j = 0;j < walls.length; j++){
        
      }
    }
    this.labyrinthTile = walls;
  }

  /**
   * @param maze each string represents different Y all strings must be the same length
   * @param regex chars that are to be considered paths, may not be special characters
   */
  public Labyrinth(String[] maze,String regex){
    char[] chars = regex.toCharArray();
    for (int j = 0; j < maze.length; j++){
      maze[j].replace(" ", "\\\\")
      for (int i = 0; i < chars.length; i++){
        maze[j].replace(chars[i], " ")
      }
    }
  }
  public Labyrinth(int Width, int Height){
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

  public boolean isPath(int...pos){
    return !this.labyrinthTile[pos[0]][pos[1]];
  }
}
