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
  
  public static Labyrinth maze(PGraphics g,int width, int height, byte density,int scramble){
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
    maze.modifyLaborinth(randX, randY, true);
    while(true){
    ArrayList<Point> activeTemp = new ArrayList<Point>();
      while(active.size() > 0){
        activeTemp.addAll(modelMaze(maze, active.remove(0), density, scramble));
      }
      active.addAll(activeTemp);
      active = shuffle(active);
      if(active.size()<1){
        break;
      }
    }
    return labyrinthTest(maze, g, width, height, density, scramble);
  }

  private static Labyrinth labyrinthTest(Labyrinth maze, PGraphics g,int width, int height, byte density,int scramble) {
    int sum = 0;
    for (int i = 0; i < maze.width; i++) {
      for (int j = 0; j < maze.height; j++) {
        sum += maze.isPath(i, j) ? 1 : 0;
      }
    }
    if((float) sum / (maze.width * maze.height) > 0.35){
      return maze;
    }
    return maze(g, width, height, density, scramble);
  }

  private static ArrayList<Point> shuffle(ArrayList<Point> active) {
    for (int i = 0; i < 4; i++){
      for (int j = 0; j < active.size(); j++) {
        Point a = active.remove((int) (Math.floor(Math.random()*active.size())));
        active.add((int) (Math.floor(Math.random()*active.size())), a);
      }
    }
    return active;
  }

  private static ArrayList<Point> modelMaze(Labyrinth maze,Point p, byte sprawl, int scramble){
    // if (density >= 2 && (int) Math.floor(Math.random()) == 12){
    //   maze.modifyLaborinth(p.X, p.Y, true);
    // }
    byte paths = 0;
    paths += (maze.isPath(p.X+1, p.Y) ? 1 : 0);
    paths += (maze.isPath(p.X-1, p.Y) ? 1 : 0);
    paths += (maze.isPath(p.X, p.Y+1) ? 1 : 0);
    paths += (maze.isPath(p.X, p.Y-1) ? 1 : 0);
    ArrayList<Point> a = new ArrayList<Point>();

    if((scramble == 1 || (int) Math.floor(Math.random()*(scramble*5)) != 0)&&
    !(paths <= 1)) return a;
    if((int) Math.floor(Math.random()*(scramble)) == 0) return a;
    if(!maze.modifyLaborinth(p.X, p.Y, true))return a;

    a.add(new Point(p.X, p.Y+1));
    a.add(new Point(p.X+1, p.Y));
    a.add(new Point(p.X-1, p.Y));
    a.add(new Point(p.X, p.Y-1));
    return a;
  }
}