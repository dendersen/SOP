package mtdm.dk.solvers;

import mtdm.dk.Point;
import mtdm.dk.Thread;
import mtdm.dk.bigO;
import mtdm.dk.labyrinth.Labyrinth;

public class WidthFirstSearch extends recursiveSolver{

  public WidthFirstSearch(Labyrinth labyrinth, int startX, int startY, int endX, int endY) {
    super(labyrinth, startX, startY, endX, endY);
  }

  @Override
  public Thread callMovement() {
    return new mover();
  }
  
  public class mover extends Thread{
    int steps;
    boolean isAlive = false;
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
