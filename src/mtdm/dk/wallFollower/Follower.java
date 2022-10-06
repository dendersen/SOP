package mtdm.dk.wallFollower;

import mtdm.dk.labyrinth.Labyrinth;

public class Follower {
  
  private Labyrinth maze;
  byte orientation = 0;//0 = up
  int[][] path;
  int X;
  int Y;
  int goalX;
  int goalY;

  public Follower(Labyrinth labyrinth, int startX,int startY,int endX,int endY, boolean rigth) {
    this.maze = labyrinth;
    this.X = startX;
    this.Y = startY;
    path = new int[11][2];
    path[0][0] = 0;
  }

  public int[][] run(int maxSteps){
    int p = path[0][0];
    while (!(X == goalX || Y == goalY) && maxSteps > path[0][0]-p){
      storePath();
      nextStep();
    }
    return path;
  }
  
  private void storePath(){
    if (path.length - 1 < path[0][0]){
      int[][] a = new int[path.length + 10][2];
      for (int i = 0;i <= path[0][0];i++){
        a[i] = path[i];
      }
      path = a;
    }
    path[path[0][0]] = pos();
    path[0][0] += 1;
  }

  public int[] pos(){
    int[] pos = {X,Y};
    return pos;
  }

  public int[] nextStep(){
    int[] pos = pos();
    turnRight();
    if (isClear()){
      X = nextPoint()[0];
      Y = nextPoint()[1];
      return pos;
    }
    for (byte i = 0; i < 3;i++){
      turnLeft();
      if (isClear()){
        X = nextPoint()[0];
        Y = nextPoint()[1];
        return pos;
      }
    }
    System.out.println("error, there is no escape");
    return pos;
  }

  private boolean isClear(){
    return maze.isPath(nextPoint());
  }

  private int[] nextPoint() {
    int[] a = pos();
    switch (orientation) {
      case 0:
        a[1] -= 1;
      case 1:
        a[0] += 1;
      case 2:
        a[1] += 1;
      case 3:
        a[0] -= 1;
    }
    return a;
  }
  
  private void turnRight(){
    orientation = (byte) ((orientation + 1) % 4);
  }
  private void turnLeft(){
    orientation = (byte) ((orientation + 3) % 4);
  }
}