package mtdm.dk.labyrinth;

import mtdm.dk.Point;
import mtdm.dk.bigO;

public class Labyrinth{
  /**@param labyrinthTile [x][y]: true = path*/
  private boolean[][] labyrinthTile;
  /**@param width the width of the labyrinth in tiles*/
  public int width;
  /**@param width the height of the labyrinth in tiles*/
  public int height;
  /**
   * @param maze each string represents different Y all strings must be the same length
   * @param regex chars that are to be considered paths, may not be special characters or å
   */
  public Labyrinth(String[] maze,String regex){
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
  /**
   * @param x the x coordinate to be checked
   * @param y the y coordinate to be checked
   * @return whether or not the given coordinates contain a path
   */
  public boolean isPath(int x,int y){
    bigO.pathCheck++;
    if(x > width-1 || x < 0 || y > height-1 || y < 0){
      return false;
    }
    return labyrinthTile[x][y];
  }
  /**
   * inds a path 
   * @param up whether it goes up or down (true starts at bottom and goes up, flase the other way)
   * @return a point in the labyrinth
   */
  private Point findPath(boolean up){
    if (up){
      return angledBrute(width-4,height-4,true);
    }else{
      return angledBrute(4,4,false);
    }
  }
  /**
   * @return "findPath(true)"
   */
  public Point findPath(){
    return findPath(true);
  }
  /**
   * 
   * @param notSame a point that is not allowed to be identical to output
   * @return "findPath(false) != notSame"
   */
  public Point findPath(Point notSame){
    Point pos = findPath(false);
    if((notSame.X == pos.X && notSame.Y == pos.Y)){
      pos = new Point(0, 0);
      while ((notSame.X == pos.X && notSame.Y == pos.Y)) {
        pos = straightBrute(pos.X, pos.Y, false);
      }
    }
    return pos;
  }
  /**
   * searches for a point using an angel of 45 degrees
   * @param x the x to start at
   * @param y the y to start at
   * @param up if it goes up
   * @return a point that is a path
   */
  private Point angledBrute (int x,int y, boolean up){
    while(true){
      bigO.pathCheck--;//prevent incorecct pathCheck
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
        return straightBrute(x,y,up);
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
  /**
   * searches for a point using an angel of 0 degrees
   * @param x the x to start at
   * @param y the y to start at
   * @param up if it goes up
   * @return a point that is a path
   */
  private Point straightBrute(int x, int y, boolean up) {
    while(true){
      bigO.pathCheck--;//prevent incorecct pathCheck
      if(isPath(x, y)){
        return new Point(x, y);
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

  /**
   * @param X the X coordinate of the place you want to change
   * @param Y the Y coordinate of the place you want to change
   * @param path whether or not it will become a path
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
        temp += (isPath(i,j) ? " ":"@");
        bigO.pathCheck--;//prevent incorecct pathCheck
      }
      out += "\"" + temp + "\",";
      out += "\n";
    }
    return out + "}";
  }
  /**
   * calls a saving class that will save the labyrinth
   * @param regex does not do anything at the moment
   */
  public void saveLaborinthToFile(String regex){
    System.out.println("starting labyrinthSaver");
    
    labyrinthSaver writer = new labyrinthSaver();
    writer.start(toString());
    Thread t = new Thread(writer);
    t.start();
    System.out.println("labyrinthSaver running");
  }
}
