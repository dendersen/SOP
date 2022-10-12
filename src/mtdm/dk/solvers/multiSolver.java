package mtdm.dk.solvers;

import mtdm.dk.labyrinth.Labyrinth;
import processing.core.PGraphics;

public class multiSolver {
  WallFollower wall;
  recursiveSolver recurse;
  byte ID;
  public multiSolver(byte solveID,Labyrinth maze, int startX,int startY,int endX,int endY){
    ID = solveID;
    switch(solveID){
      case 0:{
        wall = new WallFollower(maze, startX, startY, endX, endY);
      }break;
      case 1:{
        recurse = new recursiveSolver(maze, startX, startY, endX, endY);
      }break;
    }
  }
  public void move(int steps){
    switch(this.ID){
      case 0:{
        wall.move(1);
      }break;
      case 1:{
        recurse.move(1);
      }break;
    }
  }
  public void draw(PGraphics g,int sqrWidth, int sqrHeigth){
    switch(this.ID){
      case 0:{
        wall.draw(g, sqrWidth,sqrHeigth);
      }break;
      case 1:{
        recurse.draw(g, sqrWidth,sqrHeigth);
      }break;
    }
  }
}