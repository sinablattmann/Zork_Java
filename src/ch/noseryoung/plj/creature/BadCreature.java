package ch.noseryoung.plj.creature;

import ch.noseryoung.plj.Item;

public class BadCreature extends Creature {
  private Item defeatItem;

  public BadCreature(Item defeatItem) {
    this.defeatItem = defeatItem;
  }

  public void attack() {
    System.out.println(this.getDescription() + "\nTo defeat this " + this.getName() + " you need a " + this.getDefeatItem().getName() + ".");
  }

  public Item getDefeatItem() {
    return defeatItem;
  }

  public BadCreature setDefeatItem(Item defeatItem) {
    this.defeatItem = defeatItem;
    return this;
  }
}
