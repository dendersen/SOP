package mtdm.dk.labyrinth;

public class LabyrinthGen {
  public static boolean[][] g(int length, int height){//true = walls
    boolean w = true;
    boolean p = false;
    boolean[][] a = {
      {w,w,w,w},
      {w,p,p,w},
      {w,w,w,w}
    };
    return a;
  }
  
}