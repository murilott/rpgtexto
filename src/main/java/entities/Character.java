package entities;

import java.util.HashMap;
import entities.Area;

public class Character {
  public String name;
  public HashMap<String, String> inventory = new HashMap<String, String>();
  public Area currentArea;
  
}