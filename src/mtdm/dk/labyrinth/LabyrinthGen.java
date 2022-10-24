package mtdm.dk.labyrinth;

import java.util.ArrayList;

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
    for (int i = 0; i < height; i++){
      String temp = "";
      for (int j = 0; j < width; j++){
        temp += "a";
      }
      out[i] = temp;
    }
    
    Labyrinth maze = new Labyrinth(out, " ",g);
    int randX = (int) Math.floor(Math.random() * (width -4) + 2);
    int randY = (int) Math.floor(Math.random() * (height-4) + 2);
    ArrayList<Point> active = new ArrayList<Point>();
    active.add(new Point(randX,randY));
    while(true){
    ArrayList<Point> activeTemp = new ArrayList<Point>();
      while(active.size() > 0){
        activeTemp.addAll(modelMaze(maze, active.remove(0), density));
      }
      active.addAll(activeTemp);
      if(active.size()<1){
        break;
      }
    }
    return maze;
  }
  private static ArrayList<Point> modelMaze(Labyrinth maze,Point p, byte density){
    // if (density >= 2 && (int) Math.floor(Math.random()) == 12){
    //   maze.modifyLaborinth(p.X, p.Y, true);
    // }
    byte paths = 0;
    paths += (maze.isPath(p.X+1, p.Y) ? 1 : 0);
    paths += (maze.isPath(p.X-1, p.Y) ? 1 : 0);
    paths += (maze.isPath(p.X, p.Y+1) ? 1 : 0);
    paths += (maze.isPath(p.X, p.Y-1) ? 1 : 0);
    ArrayList<Point> a = new ArrayList<Point>();
    if(!(paths <= density)) return a;
    if((int) Math.floor(Math.random()*(3+density)) == 0) return a;
    if(!maze.modifyLaborinth(p.X, p.Y, true))return a;
    //TODO fix density > 1
    a.add(new Point(p.X, p.Y+1));
    a.add(new Point(p.X+1, p.Y));
    a.add(new Point(p.X-1, p.Y));
    a.add(new Point(p.X, p.Y-1));
    return a;
  }
}