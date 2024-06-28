import entities.Director;

public class Main {
  public static void main(String[] args) {
    Director dir =  new Director();

    dir.createArea(dir, "Menu", "01"); 
    dir.createArea(dir, "Bar", "01"); 
    dir.createArea(dir, "Praça", "01"); 
    dir.createArea(dir, "Boca", "01"); 
    // dir.getArea("Bar").getAreaOptions();; 
    dir.startGame();
  }
}