package entities;

import entities.Director;
import entities.Area;
import entities.Option;
import service.FileReadService;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileRead implements FileReadService {
  public String file_name;
  public File file;
  public File options_file;
  
  public String section; // = "01"
  public String currentSection;
  public Area area;
  public boolean jumpTo = false;
  public boolean exitArea = false;
  public String nextSec;
  public String nextOpt;
  public String skipArea;
  public String skipSection;
  public String skipOption;
  Map<Integer, String> currentOptions;
  ArrayList<Option> optionList;

  // formato: travel(area, section)
  // formato: talk(section, option) ou 
  // talk(section, option, section, option)
  // formato: addInventory(item)
  
  public FileRead(String file_name, Area area) {
    this.file_name = file_name;
    this.file = new File("src/main/java/data/" + file_name + ".txt");
    this.options_file = new File("src/main/java/data/" + file_name + "Options.txt");
    this.area = area;
    this.section = area.startSection;
    this.currentSection = area.startSection;
    optionList = this.setOptionsList();
  }

  public String convertOption(int option) {
    String returnValue = "-1";
    String body = "";

    for (int key : currentOptions.keySet()) {
      // System.out.println(key + " - "+ currentOptions.get(key));
      if (key == option) {
        body = currentOptions.get(key);
      }
    }

    
    for (Option opt : optionList) {
      // System.out.println("number " + opt.number);
      // System.out.println("body " + opt.body);
      // System.out.println("section " + opt.section);
      // System.out.println("currentSection " + currentSection);
      // System.out.println("option " + opt.option);
      // System.out.println("option body " + opt.body.equals(body));
      // System.out.println("option section " + opt.section.equals(currentSection));
      
      if (opt.body.equals(body) && opt.section.equals(currentSection)) {
        opt.showOption();
        returnValue = opt.option;
      }
      // opt.getOption();
    }

    if (body.equals("")) {
      System.out.println("Opção inválida (vazio)");
    }
    if (returnValue.equals("-1")){
      System.out.println("Opção inválida (nulo)");
    }
    
    // System.out.println(optionList);
    return returnValue;
  }

  public void exit() {
    area.director.running = false;
    exitArea = true;
    skipArea = "Menu";
    skipSection = "00";
    skipOption = "00";
  }

  public void travel(String area, String section, String option) {
    exitArea = true;
    skipArea = area;
    skipSection = section;
    skipOption = option;
  }

  public void talk(String section, String option) {
    jumpTo = true;
    nextOpt = option;
    this.section = section;
  }

  public String[] decision(String initSection, String initOption) {
    currentSection = initSection;
    System.out.println(fetchLine(initOption));
      
    while (!exitArea) {
      if (jumpTo) {
        currentSection = section;
        // System.out.println("jumpTo, seção " + currentSection + " opção " + nextOpt);
        System.out.println(fetchLine(nextOpt));
        nextOpt = "-1";
        jumpTo = false;
      } else {
        String opt = convertOption(this.choose());
        // System.out.println("current seção decision " + currentSection);
        // System.out.println("current opção decision " + opt);
        
        System.out.println(fetchLine(opt));
      }
    }
    
    // System.out.println("Saindo da área " + area.name);
    // System.out.println("Próxima área " + skipArea);
    // System.out.println("Próxima seção " + skipSection);
    // System.out.println("Próxima opção " + skipOption);
      exitArea = false;

      return new String[] {skipArea, skipSection, skipOption};
    }

  public int choose() {
    Scanner scan = new Scanner(System.in);
    int number = -1;

    try { 
      System.out.print("Opção: ");
      number = scan.nextInt(); 
    } catch (InputMismatchException e) { 
      System.out.println("Opção inválida");
      scan.nextLine(); // Consume the invalid input
    } 
    
    return number;
  }
  
  public StringBuilder fetchLine(String option) {
    Scanner sc = null;
    String sec = "";
    String opt = "";
    boolean save = false;
    StringBuilder saveLine = new StringBuilder();
    // Map<String, String> options = new HashMap<String, String>();
    currentOptions = new HashMap<Integer, String>();

    try {
        sc = new Scanner(file);

        while (sc.hasNextLine()) {
          // System.out.println("lendo linha");
          String line = sc.nextLine();

          if (line.startsWith("s")) {
              String number = line.substring(1, 3);
              sec = number;
            }
          
          if (line.startsWith("o")) {
            String number = line.substring(1, 3);
            opt = number;
          }

          if (save) {
            if (!line.contains("@")) {
              if (line.startsWith("{")) {
                String[] array1 = line.split(";");

                for (int i=0; i<array1.length; i++) {
                  array1[i] = array1[i].replaceAll("\\{", "").replaceAll("\\}", "");
                  String[] array2 = array1[i].split(" - ");
                  // System.out.println(line);
                  // System.out.println(array2[0]);
                  // System.out.println(array2[1]);
                  currentOptions.put(Integer.parseInt(array2[0]), array2[1]);
                }
              }

              if (line.contains("talk")) {
                String functLine = line.replaceAll("talk", "").replaceAll("\\(", "").replaceAll("\\)", "");
                String[] array1 = functLine.split(",");
                String secTalk = array1[0];
                String optTalk = array1[1];
                // System.out.println(secTalk + " "+ optTalk);
                this.talk(secTalk, optTalk);
              }

              if (line.contains("travel")) {
                String functLine = line.replaceAll("travel", "").replaceAll("\\(", "").replaceAll("\\)", "");
                String[] array1 = functLine.split(",");
                String newArea = array1[0];
                String secTravel = array1[1];
                String optTravel = array1[2];
                // System.out.println(secTalk + " "+ optTalk);
                this.travel(newArea, secTravel, optTravel);
              }

              if (line.startsWith("exit")) {
                this.exit();
              }

              if (line.startsWith("[")) {
                // if (!saveLine.toString().contains(line + "\n")) {
                saveLine.append(line + "\n");
                // }
              }
            }
          }
         
          if (opt.equals(option) && sec.equals(currentSection)) {
            save = true;
          }

          if (line.contains("@")) {
            save = false;
          }        
        }  
      } catch (FileNotFoundException e) {
        System.out.println("File  '"+file+"' not found.");
      } 
      finally {
          if (sc != null) {
            sc.close();
          }
        }
    
      // System.out.println(saveLine);
      // for (String keys : options.keySet()) {
      //    System.out.println(keys + " - "+ options.get(keys));
      // }

    saveLine.append("\n");
    for (int keys : currentOptions.keySet()) {
      // System.out.println(keys + " - "+ options.get(keys));
      saveLine.append(keys + " - " + currentOptions.get(keys) + "\n");
    }
    
    return saveLine;
  }

  public ArrayList<Option> setOptionsList() {
    Scanner sc = null;
    ArrayList<Option> options = new ArrayList<Option>();
    
    try {
      sc = new Scanner(options_file);

      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] split = line.split(";");
        // System.out.println(line);
        // options.put(Integer.parseInt(split[0]), split[1]);
        Option opt = new Option(Integer.parseInt(split[0]),
                                split[1].substring(0, 2),
                                split[1].substring(3, 5), split[2]);
        options.add(opt);
      }

      return options;
    } catch (FileNotFoundException e) {
      System.out.println("File '"+ options_file +"' not found." + e.getMessage());
      return new ArrayList<Option>();
    } 
    finally {
      if (sc != null) {
        sc.close();
      }
    }
  }
  
}