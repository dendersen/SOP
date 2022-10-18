package mtdm.dk.labyrinth;

import mtdm.dk.Point;
import processing.core.PGraphics;

public class LabyrinthGen {
  public static Labyrinth maze(PGraphics g){
    String regex = " ";
    String[] out = {
      "  █ █    ██   ",
      "█     ███   █ ",
      "  █ █   █ █  █",
      "█ █ █  █   █ █",
      "█ █ █ █ ██ █  ",
      "█ ██ █  ██ █ █",
      "█     █    █ █",
      "  █ █   █ █   "
    };
    return new Labyrinth(out, regex,g);
  }
  
  public static Labyrinth maze(PGraphics g,int width, int height, byte density){
    String[] out = new String[height];
    for (int i = 0; i < width; i++){
      String temp = "";
      for (int j = 0; j < height; j++){
        temp += "a";
      }
      out[i] = temp;
    }
    
    Labyrinth maze = new Labyrinth(out, " ",g);
    int randX = (int) Math.floor(Math.random() * (width -4) + 2);
    int randY = (int) Math.floor(Math.random() * (height-4) + 2);
    
    modelMaze(maze,new Point(randX, randY),density);
    return maze;
  }
  private static void modelMaze(Labyrinth maze,Point p, byte density){
    // if (density >= 2 && (int) Math.floor(Math.random()) == 12){
    //   maze.modifyLaborinth(p.X, p.Y, true);
    // }
    byte paths = 0;
    paths += (maze.isPath(p.X+1, p.Y) ? 1 : 0);
    paths += (maze.isPath(p.X-1, p.Y) ? 1 : 0);
    paths += (maze.isPath(p.X, p.Y+1) ? 1 : 0);
    paths += (maze.isPath(p.X, p.Y-1) ? 1 : 0);
    if (paths <= density){
      
      if ((int) Math.floor(Math.random()*(3+density)) == 0 || !maze.modifyLaborinth(p.X, p.Y, true)) return;
      //TODO fix density > 1
      modelMaze(maze, new Point(p.X+1, p.Y), density);
      modelMaze(maze, new Point(p.X-1, p.Y), density);
      modelMaze(maze, new Point(p.X, p.Y+1), density);
      modelMaze(maze, new Point(p.X, p.Y-1), density);
      return;
    }
  }
}