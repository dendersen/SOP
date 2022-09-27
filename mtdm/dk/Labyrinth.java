package mtdm.dk;

public class Labyrinth{
  
  private boolean[][] labyrinthTile;
  
  public Labyrinth(boolean[][] walls){//[x][y] true = wall
    for (int i = 0;i < walls.length; i++){
      if(walls[0].length != walls[i].length){
        this.labyrinthTile = new boolean[0][0];
        return;
      }
      
      for (int j = 0;j < walls.length; j++){
        
      }
    }
    this.labyrinthTile = walls;
  }

  public Labyrinth(String str){
    
  }

  public boolean isPath(int...pos){
    return this.labyrinthTile[pos[0]][pos[1]];
  }

}
