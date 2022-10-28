package mtdm.dk.solvers;

import processing.core.PGraphics;
import mtdm.dk.Thread;

public class Solver {
  public Thread Calc;
  public Thread Draw;
  public void move(int steps){
    System.out.println("please use extended classes");
  }
  public void draw(PGraphics g,double sqrWidth, double sqrHeigth){
    System.out.println("please use extended classes");
  }
  public boolean complete(){
    System.out.println("please use extended classes");
    return false;
  }
  public class mover extends Thread{}
  public class drawer extends Thread{}

}