package mtdm.dk.solvers;

import java.util.ArrayList;

import mtdm.dk.Point;
import mtdm.dk.Thread;
import mtdm.dk.labyrinth.Labyrinth;

public class ManhattanRecurse extends recursiveSolver{
  // protected ArrayList<Point> currentPoints = new ArrayList<Point>();
  /**
   * @param labyrinth
   * @param startX
   * @param startY
   * @param endX
   * @param endY
   */
  public ManhattanRecurse(Labyrinth labyrinth, int startX, int startY, int endX, int endY) {
    super(labyrinth, startX, startY, endX, endY);
    this.maze = labyrinth;
    this.X = startX;
    this.Y = startY;
    this.startX = startX;
    this.startY = startY;
    this.goalX = endX;
    this.goalY = endY;
    this.currentPoints.add(new Point(startX,startY));
  }

  @Override
  public Thread callMovement() {
    return new moveer();
  }

  public class moveer extends Thread {
    int steps; 
    boolean isAlive = true;
    public void start(int step){
      steps = step;
    }
    public void run(){
      isAlive = true;
      if (!begun){
        begun = true;
        return;
      }
      if(finished()){
        end = finishedPoint();
      }else{
        int index = 0;
        int dist = (int)1e300;
        for(int j = 1; j < currentPoints.size(); j++){
          int tempDist = (currentPoints.get(j).X - goalX) + (currentPoints.get(j).Y - goalY);
          if(tempDist < dist){
            dist = tempDist;
            index = j;
          }
        }
        {
          try {
            Point current = currentPoints.remove(index);
            if (checkPoint(current)){
              newPoints.addAll(addPrepPoints(current));
              accesedPoints.add(current);
            }
            toBeDrawn.add(current);
          }catch(Exception e){System.out.println("empty");}
        }
      }
      isAlive = false;
    }
  }
}
