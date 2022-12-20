package mtdm.dk.solvers;

import mtdm.dk.Point;
import mtdm.dk.Thread;
import mtdm.dk.bigO;
import mtdm.dk.labyrinth.Labyrinth;

public class ManhattanRecurse extends recursiveSolver{
  /**
   * the depth to which the program will search the point queue for the best option
   */
  private int maxDepth = 10;
  /**
   * @param labyrinth the labyrinth that the program should work on
   * @param startX the starting x coordinate of the algorithm
   * @param startY the starting y coordinate of the algorithm
   * @param endX the ending x coordinate of the algorithm
   * @param endY the ending y coordinate of the algorithm
   */
  public ManhattanRecurse(Labyrinth labyrinth, int startX, int startY, int endX, int endY) {
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
    return new moveer();
  }
    /**
   * the thread that handles movement for this algorithm
   */
  public class moveer extends Thread {
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
        for(int j = currentPoints.size()-1; j >= currentPoints.size()-maxDepth && j >= 0; j--){
          int tempDist = (currentPoints.get(j).X - goalX) + (currentPoints.get(j).Y - goalY);
            bigO.arrayAcces+=2;
            if(tempDist < dist){
            dist = tempDist;
            index = j;
          }
        }
        try {
          Point current = currentPoints.remove(index);
          if (checkPoint(current)){
            newPoints.addAll(addPrepPoints(current));
            bigO.arrayAcces++;
            accesedPoints.add(current);
          }
          toBeDrawn.addAll(currentPoints);
        }catch(Exception e){
          System.out.println("empty");
        }
      }
      isAlive = false;
    }
  }
}
