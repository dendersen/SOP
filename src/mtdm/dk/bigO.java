package mtdm.dk;

public class bigO {
  /**
   * har many times "isPath(x,y)" is called by Solvers
   * any non solver class that calls "isPath(x,y)" should remove 1 from this parameter for every call
   */
  public static long pathCheck = 0;
  /**
   * how many times arrays (aside fro, "isPath(x,y)") is read by a Solver
   */
  public static long arrayAcces = 0;
}
