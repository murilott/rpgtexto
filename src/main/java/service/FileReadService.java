package service;

import java.util.ArrayList;
import entities.Option;

public interface FileReadService {
  public String convertOption(int option);
  public void exit();
  public void travel(String area, String section, String option);
  public void talk(String section, String option);
  public String[] decision(String initSection, String initOption);
  public int choose();
  public StringBuilder fetchLine(String option);
  public ArrayList<Option> setOptionsList();
}