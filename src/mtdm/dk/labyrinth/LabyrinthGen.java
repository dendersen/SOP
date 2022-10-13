package mtdm.dk.labyrinth;

import processing.core.PGraphics;

public class LabyrinthGen {
  public static Labyrinth maze(PGraphics g){
    String regex = " ";
    String[] out = {
      "  █ █    ██  ",
      "█     ███   █ ",
      "  █ █   █ █  █",
      "█ █ █  █   █ █",
      "█ █ █ █ ██ █  ",
      "█ ██ █  ██ █ █",
      "█     █    █ █",
      "  █ █   █ █   "
    };
    return new Labyrinth(out, regex,g);
  }
}