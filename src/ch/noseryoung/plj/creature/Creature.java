package ch.noseryoung.plj.creature;

public class Creature {
  private String name;
  private String description;


  public String getName() {
    return name;
  }

  public Creature setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Creature setDescription(String description) {
    this.description = description;
    return this;
  }
}
