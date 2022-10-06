package mtdm.dk;

import mtdm.dk.labyrinth.Labyrinth;
import mtdm.dk.labyrinth.LabyrinthGen;
import mtdm.dk.wallFollower.Follower;

public class test {
  public static void main(String[] args) {
    Labyrinth maze = new Labyrinth(LabyrinthGen.g(100, 100));
    Follower solve = new Follower(maze,1,1,2,3,true);
    int[][] a = solve.run(15);
    String e = "";
    for (int i = 0; i < a.length; i++){
      e += "(";
      for (int j = 0; j < a[i].length;j++){
        e += (a[i][j]);
        e += ",";
      }
      e += "),";
    }
    System.out.println(e);
  }
}
