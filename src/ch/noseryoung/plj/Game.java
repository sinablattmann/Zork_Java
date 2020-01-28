package ch.noseryoung.plj;

import ch.noseryoung.plj.creature.BadCreature;
import ch.noseryoung.plj.creature.GoodCreature;

import java.util.ArrayList;

/**
 * This class is used for each game
 *
 * @author sina
 */
public class Game {
  private Parser parser;
  private Room currentRoom;
  private ArrayList<Item> currentItems;
  private int life;

  public Game() {
    createRooms();
    parser = new Parser();
    currentItems = new ArrayList<>();
    life = 3;
  }

  /**
   * creates all the rooms at the beginning
   */
  public void createRooms() {
    Room library, outside, livingRoom, bedRoom, cellar, smallBathRoom, bigBathRoom, secretRoom, hiddenGarden, wallRoom;
    Item sword, hammer, key;
    library = new Room("The library has loads of books, sit down and relax for a while!", "You entered the library");
    outside = new Room("Outside", "You left the house. ");
    livingRoom = new Room("This is the living room", "You are now in the living room");
    bedRoom = new Room("This is the bedroom", "You entered the bedroom");
    cellar = new Room("This is the cellar", "You are now in the dark and quiet cellar...");
    smallBathRoom = new Room("This is the bathroom", "You just entered the small bathroom");
    bigBathRoom = new Room("This is the big bathroom", "You just entered the big bathroom");
    secretRoom = new Room("This is a secret room", "Congrats, you found the secret Room");
    hiddenGarden = new Room("This is the hidden garden", "All kinds of creatures hide in the secret garden");
    wallRoom = new Room("This is a room, hidden in the wall", "You just found the room hidden in the wall");

    library.setExits(bedRoom, outside, livingRoom, null);
    outside.setExits(null, null, hiddenGarden, library);
    livingRoom.setExits(library, null, smallBathRoom, cellar);
    bedRoom.setExits(null, bigBathRoom, library, null);
    bigBathRoom.setExits(null, null, null, bedRoom);
    hiddenGarden.setExits(outside, null, wallRoom, null);
    wallRoom.setExits(hiddenGarden, null, null, smallBathRoom);
    smallBathRoom.setExits(livingRoom, wallRoom, null, null);
    cellar.setExits(secretRoom, livingRoom, null, null);
    secretRoom.setExits(null, null, cellar, null);

    sword = new Item("Sword", "This item is used to kill monsters");
    library.setItem(sword);
    hammer = new Item("Hammer", "This item is used to hammer");
    key = new Item("Key", "Is used to open doors.");
    livingRoom.setItem(hammer);
    cellar.setCreature(new BadCreature(sword).setName("Dragon").setDescription("This monster is able to spit fire"));
    bigBathRoom.setItem(key);
    currentRoom = outside;
    library.setLocked(true);
  }

  /**
   * starts the game
   */
  public void play() {
    printWelcomeMessage();
    boolean finished = true;
    while (finished) {
      try {
        Command command = parser.getCommand();
        finished = processCommand(command);
      }catch (CommandNotValidException e){
        System.out.println("This command isn't valid, try again.");
      }

    }
    System.out.println("Goodbye!");
  }

  /**
   * checks if the command is valid
   *
   * @param command
   * @return returns a boolean
   */
  public boolean processCommand(Command command) {
    if (!command.isUnknown()) {
      String commandWord = command.getCommandWord();
      switch (commandWord) {
        case "help":
          printHelp();
          break;
        case "go":
          goToRoom(command);
          break;
        case "exit":
          return false;
        case "show":
          showCurrentItems();
          break;
        default:
          break;
      }
    } else {
      System.out.println("This command isn't valid");
    }
    return true;
  }

  /**
   * Takes the given command and checks if the room has monsters
   *
   * @param command
   */
  private void goToRoom(Command command) {
    if (command.hasSecondWord()) {
      String direction = command.getSecondWord();
      Room nextRoom = currentRoom.nextRoom(direction);
      if (nextRoom == null) {
        System.out.println("There is no door!");
      } else {
        if (!nextRoom.isLocked()) {
          currentRoom = nextRoom;
          System.out.println(nextRoom.getWelcomeMessage());
          addCurrentItems(nextRoom.getItems());
          if (nextRoom.getCreature() != null) {
            if (nextRoom.getCreature() instanceof BadCreature) {
              attacks((BadCreature) nextRoom.getCreature());
            } else {
              gives((GoodCreature) nextRoom.getCreature());
            }
          }
        } else {
          if (currentItems.size() > 0) {
            for (Item item : currentItems) {
              if (item.getName().equals("Key")) {
                System.out.println("The door is locked, but luckily you had the key to unlock it.");
              } else {
                System.out.println("The door is locked. Unfortunately you don't have the key to open it. ");
              }
            }
          } else {
            System.out.println("The door is locked. Unfortunately you don't have the key to open it. ");
          }
        }
      }
    } else {
      System.out.println("Go where?");
    }
  }

  /**
   * simulates an attack of a creature
   *
   * @param creature
   */
  public void attacks(BadCreature creature) {
    creature.attack();
    if (isItemAlreadyAdded(creature.getDefeatItem())) {
      System.out.println("\nYou were able to defeat the " + creature.getName() + ", as you had the needed item in you inventory. Congrats!");
      currentRoom.setCreature(null);
    } else {
      if (life > 1) {
        System.out.println("You just lost a life\n" +
                "You don't have the needed Item. You have to go get a " + creature.getDefeatItem().getName() + "!");
        life--;
      } else {
        System.out.println("You just died because you didn't have the needed item.\nGood luck next time!");
        System.exit(0);
      }
    }
  }

  /**
   * This methods checks if the parameter gives life and adds life.
   *
   * @param creature
   */
  public void gives(GoodCreature creature) {
    if (creature.isGivesLife()) {
      life++;
    } else {
      System.out.println("This creature is useless, go find another creature, if you're looking for more life.");
    }
  }

  /**
   * @param item
   * @return true if the item is already added
   */
  public boolean isItemAlreadyAdded(Item item) {
    for (Item currentItem : currentItems) {
      if (currentItem.equals(item)) {
        return true;
      }
    }
    return false;
  }

  /**
   * adds an Item to the current Items List
   *
   * @param items
   */
  public void addCurrentItems(ArrayList<Item> items) {
    for (Item item : items) {
      if (!isItemAlreadyAdded(item))
        System.out.println("You found a " + item.getName() + ". " + item.getDescription());
      currentItems.add(item);
    }
  }

  /**
   * prints out the welcome message
   */
  public void printWelcomeMessage() {
    System.out.println("Welcome to this adventure game,  \nyou can navigate through the house with the following commands:");
    System.out.println("\nTo move around, type something like \"go west\" or \"go south\". " +
            "\nTo look at your current items type \"show items\"" +
            "\nTo exit the game just type \"exit\"." +
            "\nFor help just type \"help\"" +
            "\nHave fun!!");
  }

  /**
   * prints out the help menu
   */
  public void printHelp() {
    System.out.println("This is the help text");
    CommandWords commands = new CommandWords();
    commands.showAll();
  }

  /**
   * displays the current items list
   */
  public void showCurrentItems() {
    for (Item currentItem : currentItems) {
      System.out.println("- " + currentItem.getName());
    }
  }
}


