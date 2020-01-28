package ch.noseryoung.plj.creature;

public class GoodCreature extends Creature {
  private boolean givesLife;

  public boolean isGivesLife() {
    return givesLife;
  }

  public GoodCreature setGivesLife(boolean givesLife) {
    this.givesLife = givesLife;
    return this;
  }
}
