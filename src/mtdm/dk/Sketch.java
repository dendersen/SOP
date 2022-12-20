package mtdm.dk;

import processing.core.PApplet;
import processing.core.PGraphics;
import mtdm.dk.labyrinth.Labyrinth;
import mtdm.dk.labyrinth.LabyrinthGen;
import mtdm.dk.solvers.Solver;

public class Sketch extends PApplet{

  private LabDraw[] labo;
  private Thread[] movers;
  private Thread[] drawers;
  
  static PGraphics g;
  
  private static double sqrWidth;
  private static double sqrHeigth;
  private static Labyrinth maze;
  
  private Solver solver;
  
  private int Height;
  private int Width;
  private int desire;
  private int numberOfDraw;
  
  /**
   * prepares the program so that it can begin the processing of the algorithms
   * @param solverID the solver to use
   * @param density controlls the use of density in labyrinth generation, if less than 0 no ekstra paths will be forced
   * @param scramble controlles how random the turns of the laborinth will seem
   * @param Width the number of squares to be placed along the x axis
   * @param Height the number of squares to be placed along the y axis
   * @param desire the desired size of the window
   * @param Loader loads old labyrinths unless = -1
   * @param processingThreadCount how many threads should be used to go through the algorithm
   * @param drawingThreadCount how many threads should be used to draw the labyrinth
   */
  public Sketch(byte solverID, int density, int scramble,int Width,int Height,int desire,int Loader,int processingThreadCount,int drawingThreadCount){
    numberOfDraw = drawingThreadCount;
    movers = new Thread[processingThreadCount];
    drawers = new Thread[Math.max(processingThreadCount/10,1)];


    if(Width * Height > 800){
      System.out.println("creating labyrinth, this might take a while");
    }
    if (Loader < 0){
    maze = LabyrinthGen.maze(Width, Height, density, scramble);
    }else{
      maze = LabyrinthGen.LoadLaborinthFromFile(Loader);
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
  
  /**
   * an unused method that allows the PApplet to start the program
   */
  public void main() {
    PApplet.main("Sketch");
  }
  /**
   * runs before the sketch actually starts
   */
  @Override
  public void settings() {
    size((int) Math.floor(desire/maze.width) * maze.width, (int) (Math.floor(desire/maze.height)) * maze.height);
    Height = height;
    Width = width;
  }
  /**
   * runs when the window opens
   */
  @Override
  public void setup() {
    // surface.setResizable(true);
    SetSquares();
    g = getGraphics();
    strokeWeight(2);
    frameRate(60);
    // System.out.println(maze.toString());
    maze.saveLaborinthToFile("@");
  }
  /**
   * runs every frame
   */
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
/**
 * starts the Labo-Threads so they can draw the labyrinth 
 */
  private void drawLaborinth(){
    for (int i = 0; i < labo.length; i++) {
      labo[i].run();
    }
  }
  /**
   * draws the labyrinth
   */
  public class LabDraw extends Thread {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    public boolean isRunning = false;
    /**
     * starts prepares the LabDraw for run
     * @param sX the lowest x to be run
     * @param sY the lowest y to be run 
     * @param eX the highest x to be run
     * @param eY the highest y to be run
     */
    public void start(int sX, int sY, int eX, int eY){
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
    }
    /**
     * draws the labyrinth
     */
    public void run(){
      isRunning = true;
      for(int i = startX; i < endX;i++){
        for(int j = startY; j < endY;j++){
          g.strokeWeight(0);
          bigO.pathCheck--;//prevent incorecct pathCheck
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
  /**
   * updates the size of the squares in the labyrinth
   */
  private void SetSquares(){
    sqrWidth = width / ((double)maze.width);
    sqrHeigth = height/((double)maze.height);
  }
  
  /**
   * checks if any of the threads in an array are currently running
   * @param threads the thread array to be checked
   * @return
   */
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