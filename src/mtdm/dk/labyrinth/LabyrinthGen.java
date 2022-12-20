package mtdm.dk.labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import mtdm.dk.Point;
import mtdm.dk.bigO;

public class LabyrinthGen {
  /**
   * @return a premade test labyrinth
   */
  public static Labyrinth maze(){
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
    return new Labyrinth(out, regex);
  }
  /**
   * @param width the number of points the labyrinth should be wide
   * @param height the number of points the labyrinth should be tall
   * @param density the parameter that controlls wether or not and how likely a point is to skip the path check
   * @param scramble controlls the likelyhood of a point not getting a tile even if it is otherwise supposed to
   * @return a newly generated labyrinth
   */
  public static Labyrinth maze(int width, int height, int density,int scramble){
    String[] out = new String[height];
    for (int i = 0; i < height; i++){
      String temp = "";
      for (int j = 0; j < width; j++){
        temp += "a";
      }
      out[i] = temp;
    }
    
    Labyrinth maze = new Labyrinth(out, " ");
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
    return labyrinthTest(maze, width, height, density, scramble);
  }
/**
 * test if a maze has enough tiles to be considered good
 * @param maze the labyrinth to be tested
 * @param width the width of the labyrinth (in case it failes)
 * @param height the height of the labyrinth (in case it failes)
 * @param density the density of the labyrinth (in case it failes)
 * @param scramble the scramble of the labyrinth (in case it failes)
 * @return a passing labyrinth
 */
  private static Labyrinth labyrinthTest(Labyrinth maze,int width, int height, int density,int scramble) {
    int sum = 0;
    for (int i = 0; i < maze.width; i++) {
      for (int j = 0; j < maze.height; j++) {
        sum += maze.isPath(i, j) ? 1 : 0;
        bigO.pathCheck--;//prevent incorecct pathCheck
      }
    }
    if((float) sum / (maze.width * maze.height) > 0.35f){
      return maze;
    }
    return maze(width, height, density, scramble);
  }
/**
 * shuffles an Arraylist to make sure the order that points are added down not effekt the order in which they are used
 * @param active the points to be shuffled
 * @return shuffled points
 */
  private static ArrayList<Point> shuffle(ArrayList<Point> active) {
    for (int i = 0; i < 4; i++){
      for (int j = 0; j < active.size(); j++) {
        Point a = active.remove((int) (Math.floor(Math.random()*active.size())));
        active.add((int) (Math.floor(Math.random()*active.size())), a);
      }
    }
    return active;
  }

  /**
   * check if a specific point should become a path
   * @param maze the mace being modified
   * @param p the point to be checked
   * @param density chances the chance of a path is placed, even if there are more than one path connected
   * @param scramble sets a chance a path that would otherwise have been places is not placed
   * @return new points to be checked
   */
  private static ArrayList<Point> modelMaze(Labyrinth maze,Point p, int density, int scramble){
    // if (density >= 2 && (int) Math.floor(Math.random()) == 12){
    //   maze.modifyLaborinth(p.X, p.Y, true);
    // }
    byte paths = 0;
    paths += (maze.isPath(p.X+1, p.Y) ? 1 : 0);
    paths += (maze.isPath(p.X-1, p.Y) ? 1 : 0);
    paths += (maze.isPath(p.X, p.Y+1) ? 1 : 0);
    paths += (maze.isPath(p.X, p.Y-1) ? 1 : 0);
      bigO.pathCheck-=4;//prevent incorecct pathCheck
      ArrayList<Point> a = new ArrayList<Point>();

    if((density < 1 ||
    (int) Math.floor(Math.random()*(density*5)) != 0)&&
    (paths > 1)) return a;

    if((int) Math.floor(Math.random()*(scramble)) <= 0) return a;
    
    if(!maze.modifyLaborinth(p.X, p.Y, true))return a;

    a.add(new Point(p.X, p.Y+1));
    a.add(new Point(p.X+1, p.Y));
    a.add(new Point(p.X-1, p.Y));
    a.add(new Point(p.X, p.Y-1));
    return a;
  }
/**
 * loads previusly saved labyriths from a file
 * @param ID the number of laboriths to be skiped before loading one
 * @return a labyrinth from a file
 * @know_bug flips around x and y every time, unkown if in load, save or both
 */
  public static Labyrinth LoadLaborinthFromFile(int ID){
    int fileID = -1;
    int index = 0;
    String[] temp = new String[1];
    while (ID>=index) {
      String data = "";
      try {
        File file;
        if(fileID == -1){
          file = new File("Save.txt");
        }else{
          file = new File("Save" + fileID + ".txt");
        }
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
          data += myReader.nextLine();
        }
        myReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      temp = data.replaceAll("\n", " ").split("\\{");
      if(temp.length - 1 + index >= ID){
        temp = temp[ID-index+1].split(",");
      }
      fileID++;
      index += temp.length;
    }
    String[] out = new String[temp.length-1];
    {
      for (int i = 0; i < temp.length-1; i++) {
        out[i] = "";
        try(Scanner myReader = new Scanner(temp[i])){
          while (myReader.hasNextLine()) {
            String part = myReader.nextLine();
            for (int j = 0; j < part.length(); j++) {
              if (part.charAt(j) != '\"' && part.charAt(j) != '\n') {
                out[i] += part.charAt(j);
              }
            }
          }
        }
      }
    }
    return new Labyrinth(out," ");
  }
}