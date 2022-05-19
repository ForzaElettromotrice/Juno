#TIMELINE:

##15/05/2022

1. Defined project specifics:

   *Three game modes -> JUno standard (piling and challenge(?), JUno Trade (7 and 0 have special uses)
   and JUno Reflex (jump in and infinite draw).

   *We will use JavaFX for the GUI and MVC, so the project will be divided in three phases:
   model, controller and view.

2. Started to program the model:

   *User class -> player profile. It has 5 private fields: String nickname, String avatar, int victories,
   int defeats and final Level level. Each field has a getter and a setter; in addiction there are
   the getTotalMatches method (victories+defeats), the load and save methods (save/load profile
   specifics on a txt document when the game is opened/closed) and the addVictories and
   addDefeats methods (used by save and load).

   *Level class -> an object that manages itself level and exp. It has 2 private fields: int level and
   long exp. Each field has a getter and a setter; in addiction there are the addExp method (which
   also converts exp in levels) and the save and load methods (used by the User class).

   *Created the user.txt document which will contain the profile specifics.

##18/05/2022

1. Defined the advance level algorithm: 500*(4+current level). The maximum amount of exp that can be gained from
   a match is 500.

2. Finished programming the User class:

   * Rebased the Level class as a private nested class: it is private as all of its methods are, because even if
      it doesn't include elements from the master class (hence it is static), it is ONLY used by the master class,
      so other classes can't access it. 
     - Removed load/save methods, getters and setters. 
     - Created a constructor (because private) and the checkLevel method.
     - Changed exp type to int.
   * Removed setters for victories, defeats and level, since they can't be managed by the User.
   * Implemented User constructor, getLevel, load (only way to set level, defeats and victories), save and addExp.
   
3. Implemented different type of cards and decks:

   * Created a generic class Card, which includes two protected enum: Value and Color. The class has two fields, 
      color and (final) value, and it is constructed with these two parameters.
      - Created getters, toString and isValid methods.
   * Created the class WildCard that extends Card: the constructor only requires a value since the color is always 
      black. The constructor is the super one.
      - Created setColor method (the only card that can change color).
   * Created an abstract class Deck, that has a private field deck (LinkedList<Card>). The constructor 
      creates an instance of deck.
      - Created the abstract method reset.
   * Created the class DiscardPile that extends Deck: it's a singleton, so there is only an instance of 
      DiscardPile at any given moment. The constructor is the super one. 
      - Created the getInstance method that is required by the singleton.
      - Created the add (the player can only add cards) and restart (when there are no more 
        cards in DrawPile) methods.
      - Implemented reset method that is called in the constructor.
   * Created the class DrawPile that extends Deck: it's a singleton, so there is only an instance of
     DiscardPile at any given moment. The constructor is the super one.
      - Created the getInstance method that is required by the singleton.
      - Created the draw (the player can only remove cards), shuffle and addAll (used when there are 
        no more cards in the deck) methods.
      - Implemented reset method that is called in the constructor: it creates a complete deck.

##TODO:

1. 