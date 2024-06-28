package entities;

import entities.Director;
import entities.Option;
import service.AreaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Area implements AreaService {
  public String name;
  public String startSection;
  public FileRead file;
  public ArrayList<Option> optionList;
  public Director director;
  // public Map<Integer, String> optionsDict = new HashMap<Integer, String>();

  public Area(Director director, String name, String startSection) {
    this.name = name;
    this.startSection = startSection;
    this.file = new FileRead("area"+name, this);
    this.optionList = this.file.setOptionsList();
    this.director = director;
  }

  public void playArea(String initSection, String initOption) {
  // System.out.println("initSection" + initSection);
  // System.out.println("initOption" + initOption);
  if (director.running) {
    String[] res = file.decision(initSection, initOption);
    
    // System.out.println(res[0]);
    // System.out.println(res[1]);
    director.getArea(res[0]).playArea(res[1], res[2]);
    }

    // System.out.println("fora do running");
    // System.out.println(director.running);

    director.endGame();
  }

  // public void getAreaOptions() {
  //   System.out.println("Area: " + name + ", options:");
  //   for (Integer keys : optionsDict.keySet()) {
  //     System.out.println(keys + " - "+ optionsDict.get(keys));
  //   }
  // }

  public void getOptionsList() {
    System.out.println("Area: " + name + ", options:");
    for (Option opt : optionList) {
      opt.getOption();
    }
  }
}
