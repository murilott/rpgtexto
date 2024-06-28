package entities;

import java.util.ArrayList;
import entities.NPC;
import entities.Area;
import service.DirectorService;
import java.util.Scanner;

public class Director implements DirectorService {
  ArrayList<NPC> npcList = new ArrayList<NPC>();
  ArrayList<Area> areaList = new ArrayList<Area>();
  public boolean running = false;
  public boolean menu = true;
  public Scanner scan = new Scanner(System.in);  


  public void createArea(Director director, String name, String startSection) {
    // System.out.println("CreateArea()");

    Area newArea = new Area(director, name, startSection);

    areaList.add(newArea);
  }

  public void showArea() {
    for (int i=0; i < areaList.size(); i++) {
      System.out.println("Área: " + areaList.get(i).name);
    }
  }

  public Area getArea(String name) {
    for (int i=0; i < areaList.size(); i++) {
      if ( areaList.get(i).name.equals(name) ) {
        return areaList.get(i);
      }
    }

    return null;
  }

  public void startGame() {
    running = true;
    System.out.println(" -- The Lost Watch -- ");
    this.getArea("Menu").playArea(this.getArea("Menu").startSection, "00");;
  }

  public void endGame() {
    System.out.println("Fim de jogo.");
    System.out.println("Obrigado por jogar!");
  }
  

  // public void goToArea(Area area, String section) {
  // }

  // public void createNPC(String name, String job) {
  //   System.out.println("CreateNPC()");
    
  //   NPC newNPC = new NPC();
  //   newNPC.name = name;
  //   newNPC.job = job;
  //   newNPC.inventory.put("Chave", "Chave do castelo");
      
  //   npcList.add(newNPC);
  // }

  // public void showNPC() {
  //   for (int i=0; i < npcList.size(); i++) {
  //     System.out.println("NPC: " + npcList.get(i).name + ", profissão.: " + npcList.get(i).job + ", Inventário: " + npcList.get(i).inventory.get("Chave"));
  //   }
  // }
  
  // public void createPlayer() {
  //   System.out.println("CreatePlayer()");
  // }
  
  // public void message(String mes) {
  //   // java print string slowly
  //   // https://stackoverflow.com/questions/58089090/creating-a-custom-method-to-slowly-print-text-in-java
  //   System.out.println(mes);
  // }

  // public void choose(String area, String op) {
  //   Area areaObj = this.getArea(area);
  //   this.message("Você está na área " + areaObj.name);
  //   this.message("Opções:");
  //   String[] options = op.split(", ");
    
  //   for (int i=0; i<options.length; i++) {
  //     this.message(i+1 + " - " + options[i]);
  //   }
    
  //   int inp = this.getInput(options);
  //   this.message("Seu input é " + inp);
  // }

  // public int getInput(String[] opt) {
  //   int input = scan.nextInt();;
  //   boolean fail = false;

  //   if (input != (int)input) {
  //     System.out.println(input + ": Entrada não é válida. Tente novamente.");
  //     this.getInput(opt);
  //   }

  //   return input;
  // }
}