package mtdm.dk.solvers;

import mtdm.dk.Point;
import mtdm.dk.Thread;
import mtdm.dk.bigO;
import mtdm.dk.labyrinth.Labyrinth;

public class RandomRecurse extends recursiveSolver{
  // protected ArrayList<Point> currentPoints = new ArrayList<Point>();
  /**
   * @param labyrinth
   * @param startX
   * @param startY
   * @param endX
   * @param endY
   */
  public RandomRecurse(Labyrinth labyrinth, int startX, int startY, int endX, int endY) {
    super(labyrinth, startX, startY, endX, endY);
    this.maze = labyrinth;
    this.startX = startX;
    this.startY = startY;
    this.goalX = endX;
    this.goalY = endY;
    this.currentPoints.add(new Point(startX,startY));
  }

  @Override
  public Thread callMovement() {
    return new moverr();
  }

  public class moverr extends Thread {
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
        {
          try {
            Point current = currentPoints.remove((int) Math.floor(Math.random()*currentPoints.size()));
            if (checkPoint(current)){
              newPoints.addAll(addPrepPoints(current));
            bigO.arrayAcces++;
            accesedPoints.add(current);
            }
            toBeDrawn.addAll(currentPoints);
          }catch(Exception e){System.out.println("empty");}
        }
      }
      isAlive = false;
    }
  }
}
