package service;

import entities.Director;

public interface DirectorService {
  public void createArea(Director director, String name, String startSection);
  public void showArea();
  public void startGame();
  public void endGame();
  // public void createNPC(String name, String job);
  // public void showNPC();
  // public void createPlayer();
}