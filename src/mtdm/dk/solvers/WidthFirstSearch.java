package mtdm.dk.solvers;

import mtdm.dk.Point;
import mtdm.dk.Thread;
import mtdm.dk.bigO;
import mtdm.dk.labyrinth.Labyrinth;
/**
 * the class that runs the WidthFirst algorithm 
 */
public class WidthFirstSearch extends recursiveSolver{

  /**
   * @param labyrinth the labyrinth that the program should work on
   * @param startX the starting x coordinate of the algorithm
   * @param startY the starting y coordinate of the algorithm
   * @param endX the ending x coordinate of the algorithm
   * @param endY the ending y coordinate of the algorithm
   */
  public WidthFirstSearch(Labyrinth labyrinth, int startX, int startY, int endX, int endY) {
    super(labyrinth, startX, startY, endX, endY);
  }

  @Override
  /**
   * returns a thread that moves acording to this algorithm
   */
  public Thread callMovement() {
    return new mover();
  }
  /**
   * the thread that handles movement for this algorithm
   */
  public class mover extends Thread{
    /**
     * the number of times the calculations should repeat per call
     */
    int steps;
    /**
     * tells if the thread is currently doing stuff
     */
    boolean isAlive = false;
    /**
     * prepares for calculations
     */
    public void start(int steps){
      this.steps = steps;
      isAlive = false;
    }
    /**
     * starts calculations
     */
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
          Point current = currentPoints.remove(0);
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
