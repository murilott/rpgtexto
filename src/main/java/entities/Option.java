package entities;

import service.OptionService;

public class Option implements OptionService {
  public int number;
  public String section;
  public String option;
  public String body;

  public Option(int number, String section, String option, String body) {
    this.number = number;
    this.section = section;
    this.option = option;
    this.body = body;
  }

  public void getOption() {
    System.out.println(number + ":" + section + "-" + option + ": " + body);
  }

  public void showOption() {
    System.out.println(number + " - " + body);
  }
}