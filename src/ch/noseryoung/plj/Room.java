package ch.noseryoung.plj;

import ch.noseryoung.plj.creature.Creature;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * In this class you can set the exits of Rooms as well as assigning an Item or a creature
 *
 * @author Sina
 */
public class Room {
  private String welcomeMessage;
  private String description;
  private HashMap exits;
  private ArrayList<Item> items;
  private Creature creature;
  private boolean isLocked;

  public Room(String description, String welcomeMessage) {
    this.description = description;
    this.welcomeMessage = welcomeMessage;
    this.items = new ArrayList<Item>();
    exits = new HashMap();
  }

  /**
   * is called to set the exits of each room
   *
   * @param north
   * @param east
   * @param south
   * @param west
   */
  public void setExits(Room north, Room east, Room south, Room west) {
    if (north != null)
      exits.put("north", north);
    if (east != null)
      exits.put("east", east);
    if (south != null)
      exits.put("south", south);
    if (west != null)
      exits.put("west", west);
  }

  /**
   * assigns an item to a room
   *
   * @param item
   */
  public void setItem(Item item) {
    if (item != null)
      items.add(item);
  }

  /**
   * is used to go to the next room
   *
   * @param direction
   * @return
   */
  public Room nextRoom(String direction) {
    return (Room) exits.get(direction);
  }

  public String getWelcomeMessage() {
    return welcomeMessage;
  }

  public Room setWelcomeMessage(String welcomeMessage) {
    this.welcomeMessage = welcomeMessage;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Room setDescription(String description) {
    this.description = description;
    return this;
  }

  public HashMap getExits() {
    return exits;
  }

  public Room setExits(HashMap exits) {
    this.exits = exits;
    return this;
  }

  public ArrayList<Item> getItems() {
    return items;
  }

  public Room setItems(ArrayList<Item> items) {
    this.items = items;
    return this;
  }

  public Creature getCreature() {
    return creature;
  }

  public Room setCreature(Creature creature) {
    this.creature = creature;
    return this;
  }

  public boolean isLocked() {
    return isLocked;
  }

  public Room setLocked(boolean locked) {
    isLocked = locked;
    return this;
  }
}

