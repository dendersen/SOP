package mtdm.dk;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import mtdm.dk.labyrinth.Labyrinth;
import mtdm.dk.labyrinth.LabyrinthGen;
import mtdm.dk.solvers.Solver;

public class Sketch extends PApplet{

  LabDraw[] labo;
  final int threadCount;
  Thread[] movers;
  Thread[] drawers;

  static PGraphics g;
  static double sqrWidth;
  static double sqrHeigth;
  static Labyrinth maze;
  PImage path;
  PImage wall;
  Solver solver;
  byte solverID;
  int Height;
  int Width;
  int desire;
  int numberOfDraw;
  
  /**
   * @param solverID the solver to use
   * @param density controlls the use of density in labyrinth generation, if less than 0 no ekstra paths will be forced
   * @param scramble
   * @param Width
   * @param Height
   * @param desire the desired size of the window
   * @param Loader loads old labyrinths unless = -1
   * @param processingThreadCount
   * @param drawingThreadCount
   */
  public Sketch(byte solverID, int density, int scramble,int Width,int Height,int desire,int Loader,int processingThreadCount,int drawingThreadCount){
    numberOfDraw = drawingThreadCount;
    this.threadCount = processingThreadCount;
    movers = new Thread[processingThreadCount];
    drawers = new Thread[Math.max(processingThreadCount/10,1)];


    this.solverID = solverID;
    if(Width * Height > 800){
      System.out.println("creating labyrinth, this might take a while");
    }
    if (Loader < 0){
    maze = LabyrinthGen.maze(g, Width, Height, density, scramble);
    }else{
      maze = LabyrinthGen.LoadLaborinthFromFile(Loader, g);
    }
    System.out.println("labyrinth finished");
    Point start = maze.findPath();
    Point end = maze.findPath(start);
    solver = Solver.generator(solverID,maze, start.X, start.Y, end.X, end.Y);
    this.desire  = desire;

    labo = new LabDraw[numberOfDraw*numberOfDraw];
    for (int i = 0; i < numberOfDraw; i++) {
      for (int j = 0; j < numberOfDraw; j++) {
        labo[i*numberOfDraw+j] = new LabDraw();
        labo[i*numberOfDraw+j].start(maze.width/numberOfDraw*i-1,maze.width/numberOfDraw*j-1,maze.height/numberOfDraw*(i+1)+1,maze.height/numberOfDraw*(j+1)+1);
      };
    }
  }
  public void main() {
    PApplet.main("Sketch");
  }
  @Override
  public void settings() {
    path = this.loadImage("icons/path.png");
    size((int) Math.floor(desire/maze.width) * maze.width, (int) (Math.floor(desire/maze.height)) * maze.height);
    Height = height;
    Width = width;
  }
  @Override
  public void setup() {
    // surface.setResizable(true);
    SetSquares();
    g = getGraphics();
    strokeWeight(2);
    frameRate(60);
    // System.out.println(maze.toString());
    maze.saveLaborinthFromFile("@");
  }
  
  @Override
  public void draw(){
    if (Height  != height || Width != height){
      Height = height;
      Width = width;
      SetSquares();
    }
    drawLaborinth();
    move();
    drawSolver();
  }

  private void drawLaborinth(){
    for (int i = 0; i < labo.length; i++) {
      labo[i].run();
    }
  }
  public class LabDraw extends Thread {
    int startX;
    int startY;
    int endX;
    int endY;
    boolean isRunning = false;
    
    public void start(int sX, int sY, int eX, int eY){
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
    }
    
    public void run(){
      isRunning = true;
      for(int i = startX; i < endX;i++){
        for(int j = startY; j < endY;j++){
          g.strokeWeight(0);
          if (maze.isPath(i,j)){
            g.fill(255,128,0);
            g.rect((float) (i*sqrWidth),(float) (j*sqrHeigth),(float)sqrWidth, (float)sqrHeigth);
          }else{
            g.fill(75);
            g.rect((float) (i*sqrWidth),(float) (j*sqrHeigth),(float) sqrWidth, (float)sqrHeigth);
          }
        }
      }
      isRunning = false;
    }
  }
  private void SetSquares(){
    sqrWidth = width / ((double)maze.width);
    sqrHeigth = height/((double)maze.height);
  }
  private boolean threadRunning(Thread[] threads){
    for (int i = 0; i < threads.length; i++){
      if (threads[i].isAlive) {
        return true;
      }
    }
    return false;
  }

  private void move(){
    try {
      if(threadRunning(movers)) return;
    } catch (Exception e) {
      for (int i = 0; i <movers.length; i++){
        movers[i] = solver.callMovement();
        movers[i].start(1);
        movers[i].run();
      }
    }
    solver.swapPoints();
    for (int i = 0; i < movers.length; i ++){
      try{
          movers[i].start(1);
          movers[i].run();
      }catch(Exception e){
        movers[i] = solver.callMovement();
        movers[i].start(1);
        movers[i].run();
      }
    }
  }
  private void drawSolver(){
    if(solver.complete()){
      drawers[0].start(g,sqrWidth,sqrHeigth);
      drawers[0].run();
      return;
    }


    try {
      if(threadRunning(drawers)) return;
    } catch (Exception e) {
      for (int i = 0; i <drawers.length; i++){
        drawers[i] = solver.callDrawing();
        drawers[i].start(g,sqrWidth,sqrHeigth);
        drawers[i].run();
      }
    }
    solver.swapPoints();
    for (int i = 0; i < drawers.length; i ++){
      try{
          drawers[i].start(g,sqrWidth,sqrHeigth);
          drawers[i].run();
      }catch(Exception e){
        drawers[i] = solver.callDrawing();
        drawers[i].start(g,sqrWidth,sqrHeigth);
        drawers[i].run();
      }
    }
  }
  public boolean goal(){
    return solver.complete();
  }
  public int getLength() {
    return solver.getLength();
  }
  public int optimalLength(){
    int x = abs(solver.goalX - solver.startX);
    int y = abs(solver.goalY - solver.startY);
    return x+y;
  }
}