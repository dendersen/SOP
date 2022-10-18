package mtdm.dk;

public class Point {
  public int X;
  public int Y;
  public Point path;
  public Point(int x,int y){
    this.X = x;
    this.Y = y;
    this.path = new Point();
  }
  public Point(int x,int y,Point previusPoint){
    this.X = x;
    this.Y = y;
    this.path = previusPoint;
  }
  public Point(){
    this.X = -1;
    this.Y = -1;
  }
}
