package mtdm.dk.labyrinth.labGen;
import mtdm.dk.bigO;
import mtdm.dk.labyrinth.Labyrinth;
import processing.core.PApplet;

public class GeneratorDraw extends PApplet{
  Labyrinth maze;
  int width;
  int height;
  int sqrWidth;
  int sqrHeigth;
  private LabDraw[] labo;

  public GeneratorDraw(Labyrinth maze,int width,int height){
    this.maze = maze;
    this.width = width;
    this.height = height;
    sqrWidth = 1000/width;
    sqrHeigth = 1000/height;
    labo = new LabDraw[100];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        labo[i*10+j] = new LabDraw();
        labo[i*10+j].start(maze.width/10*i-1,maze.width/10*j-1,maze.height/10*(i+1)+1,maze.height/10*(j+1)+1);
      };
    }
  }
  
  @Override
  public void settings() {
    size(1000,1000);
  }

  @Override
  public void draw(){
    for(int i = 0; i < width;i++){
      for(int j = 0; j < width;j++){
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
  }

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
}
