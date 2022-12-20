package mtdm.dk;

import processing.core.PGraphics;

/**
 * a custom version of the Thread class that allows for use of premade start methods and moving the "alive" parameter to a more granular controlled parameter called "isAlive"
 */
public abstract class Thread extends java.lang.Thread{
  /**
   * the number of times which the algorithm will run
   */
  public int steps = 1; 
  /**
   * says if the thread is currently running
   */
  public boolean isAlive = false;
  /**
   * prepares a Thread prepared for drawing to the screen
   * @param g the graphics objekt on which there should be drawn
   * @param sqrWidth the width of every squares that will be drawn
   * @param sqrHeigth the width of every squares that will be drawn
   */
  public void start(PGraphics g,  double sqrWidth, double sqrHeigth){};
  /**
   * starts a movement based Thread
   * @param steps how many times the thread should progress
   */
  public void start(int steps){};
  /**
   * runs the Thread
   */
  abstract public void run();
}