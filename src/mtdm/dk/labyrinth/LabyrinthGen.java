package mtdm.dk.labyrinth;

public class LabyrinthGen {
  public static Labyrinth maze(){
    String regex = " ";
    String[] out = {
      "█ ████████████",
      "█     █ █   ██",
      "█ █ █   █ █   ",
      "█   █ ██   █ █",
      "█ █ █ █ ██ █ █",
      "█ █████ ██ █  ",
      "█          █ █",
      "██████████████"
    };
    return new Labyrinth(out, regex);
  }
}