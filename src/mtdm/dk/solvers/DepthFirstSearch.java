package mtdm.dk.solvers;

import mtdm.dk.Point;
import mtdm.dk.Thread;
import mtdm.dk.bigO;
import mtdm.dk.labyrinth.Labyrinth;

public class DepthFirstSearch extends recursiveSolver{
  /**
   * @param labyrinth the labyrinth that the program should work on
   * @param startX the starting x coordinate of the algorithm
   * @param startY the starting y coordinate of the algorithm
   * @param endX the ending x coordinate of the algorithm
   * @param endY the ending y coordinate of the algorithm
   */
  public DepthFirstSearch(Labyrinth labyrinth, int startX, int startY, int endX, int endY) {
    super(labyrinth, startX, startY, endX, endY);
  }
  @Override
  public Thread callMovement() {
    return new mover();
  }
  
  /**
 * the thread that handles movement for this algorithm
 */
  public class mover extends Thread{
    public void start(int steps){
      this.steps = steps;
      isAlive = false;
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
        try {
          Point current = currentPoints.remove(currentPoints.size()-1);
          if (checkPoint(current)){
            newPoints.addAll(addPrepPoints(current));
            bigO.arrayAcces++;
            accesedPoints.add(current);
          }
          toBeDrawn.addAll(currentPoints);
        } catch (Exception e) {
          System.out.println("empty");
        }
      }
      isAlive = false;
    }
  }
}
