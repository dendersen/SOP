package mtdm.dk.solvers;

import mtdm.dk.Point;
import mtdm.dk.Thread;
import mtdm.dk.bigO;
import mtdm.dk.labyrinth.Labyrinth;

public class RandomRecurse extends recursiveSolver{
  /**
   * @param labyrinth the labyrinth that the program should work on
   * @param startX the starting x coordinate of the algorithm
   * @param startY the starting y coordinate of the algorithm
   * @param endX the ending x coordinate of the algorithm
   * @param endY the ending y coordinate of the algorithm
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
  /**
 * the thread that handles movement for this algorithm
 */
  public class moverr extends Thread {
    int steps; 
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
