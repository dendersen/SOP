package mtdm.dk;
/**
 * a class that can represent a point on a grid, it can also recursivly save points inside
 * does work with negative numbers but a point without a given storage value will be given a recursive point in (-1,-1)
 */
public class Point {
  /**
   * the X coordinate of the point
   */
  public int X;
  /**
   * the Y coordinate of the point
   */
  public int Y;
  /**
   * the point that this point stores
   */
  public Point recursivePoint;
  /**
   * saves a point that does not have a point inside
   * @param x the X coordinate of the point
   * @param y the Y coordinate of the point
   */
  public Point(int x,int y){
    this.X = x;
    this.Y = y;
    this.recursivePoint = new Point();
  }
  /**
   * saves a point that does have a point inside
   * @param x the X coordinate of the point
   * @param y the Y coordinate of the point
   * @param recursivePoint the point to be stored inside
   */
  public Point(int x,int y,Point recursivePoint){
    this.X = x;
    this.Y = y;
    this.recursivePoint = recursivePoint;
  }
  /**
   * generates the equvilant of a null hitting the grid at (-1, -1)
   */
  public Point(){
    this.X = -1;
    this.Y = -1;
  }
}
