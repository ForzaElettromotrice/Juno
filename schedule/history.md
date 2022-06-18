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

##25/05/2022

1. Minor changes to some classes:
   * Added values to Enums in Card class (we will need them to sort the players' hands)
               and added constructors and getters. Value fields are final.
   * Added getFirst method in DiscardPile class (needed to check the validity of the card played)
        and changed some methods' names for clarity’s sake.
   * Added the shuffle method call when creating a new DRAW_PILE.

2. Created a new DataStructure, CircularLinkedList, to be used to manage turns: it is a LinkedList
    where the last element is linked to the first. WARNING: NOT COMPLETE AND TO BE USED ONLY IN THIS
    PROJECT: IT MISSES SOME CORE METHODS!!
    * Created the inner class Node with three fields: value, next and prev, and a constructor that takes a 
        value T.
    * Created three Nodes, head, tail and currentNode, and int size fields.
    * Created the add, addFirst (uses add), addLast (uses add), add(a certain position is specified),
        isEmpty, getSize, getFirst, getLast, getNext, get and clear methods.

4. Created the table package: it will define how the game works, bots and players, just like
    a game table does.
    * Created the Table class: a Singleton with a private constructors that uses DISCARD_PILE, 
        DRAW_PILE, Bot and Player. It manages the game flow and has isInverted, endGame and turnOrder (CircularLL!)
        fields.
        - Created the isValid (uses the isValid method in Card), getINSTANCE, reset (creates a Player and four bots
            and basically sets everything up for a new match), turn and starMatch methods.

5. Created the sub-package player, that contains Bot and Player.
    * Created the Player class, that uses DRAW_PILE, DISCARD_PILE, TABLE and has private fields
        called hand (LinkedList), saidUno (boolean), chosenCard (if the player has chosen a card
        to play) and endTurn. It defines the player.
        - Created the constructor (initializes a new hand and draws 7 cards from DRAW_PILE).
        - Created the sort (sorts the hand by color), draw (draws a certain number of cards, sorts them and sets saidUno
          to false), draw (draws a single card), discard (only discards a card if it is valid, sets the chosenCard
          field), sayUno, notUno (setters for saidUno), getChosenCard, setChosenCard (get/set for chosenCard),
            setEndTurn and getEndTurn (setters and getters for endTurn) methods.
    * Created the Bot class that extends Player. It has a private Random rand field to decide if the bot says Uno.
        - Created the randColor (decides what color the black card should assume based on the cards in
          the hand), discard (overrides Player's method by adding a method that in 80% cases says
          Uno when it only has one card on hand) and chooseCard (plays the first possible card and eventually
          draws a new one) methods.

##28/05/2022-29/05/2022

1. Reformatted all files and deleted Player, Bot, CircularLinkedList and Table.
2. Imported javafx libraries and moved the project on a Maven project.
3. Added a new folder in model called table: it will contain Table, Player, Bot and TurnOrder.

##29/05/2022

1. Recreated Player.
    * Added method names (to be implemented later) used inside Table: reset, draw, hasChosen, 
      hasPassed, getChosenCard, chooseColor, hasChosenColor, saidUno, calculatePoints, addPoints, 
      getPoints, getSizeHand.
2. Recreated Table: it is a Singleton and manages a complete match.
    * Added the static fields Table, DiscardPile, DrawPile and TurnOrder. 
    * Added the fields plus2, plus4, stop, endGame and endMatch.
    * Implemented the methods getINSTANCE, startMatch (ends when a player has 500 points),
      checkEffect (checks a card effect and modifies the field), turn and checkPoints.
3. Created TurnOrder instead of CircularLinkedList.
    * Created the static fields TurnOrder and Random.
    * Created the fields players, currentPlayer and isInverted.
    * Created the constructor (3 bots and 1 player).
    * Implemented the methods getINSTANCE, reset, nextPlayer and reverseTurnOrder.

##30/05/2022

1. Implemented Player. It works with fields that change when the player makes a decision
    otherwise the program waits until they change.
    * Created the static field DrawPile.
    * Created the fields hand, points, chosenCard, hasPassed and saidUno.
    * Created the constructor (creates the hand).
    * Implemented the methods getSizeHand, draw (draws a specified number of cards), sort,
        hasChosen, hasPassed, getChosenCard, chooseColor, saidUno, calculatePoints (of the
        remained cards), addPoints, getPoints, resetTurn, resetMatch and resetGame.

2. Implemented Bot. Extends Player but automates some methods and makes instant decisions.
    * Created the static fields DiscardPile and Random.
    * Created the constructor (super).
    * Overridden the methods hasChosen (chooses the first valid card, draws or passes) and
       saidUno (says Uno in 80% cases).
    * Implemented the method draw (only needed by the bot to draw a single card when it doesn't
        have playable cards).

3. Implemented new methods in TurnOrder: resetMatch, resetGame and getPlayers.

4. Redefined Table: it manages turns, matches (ends when someone finishes all cards) and games
   (ends when someone reaches 500 points).
    * Added field winner and rebased endGame and endMatch as inner method fields.
    * Added methods startGame, checkPoints and reset.
    * Modified methods startMatch and checkPoints (now updatePoints, adds points to the winning
        player) to be valid for changes.
        
##07-08/05/2022

##09-11/05/2022

##12-13/05/2022

##15/05/2022


##TODO:

1. history.md
2. change 'change avatar' in Stats
3. transitions between scenes
4. add images in hboxes in ChooseMode
5. add sound effects
6. add animations
7. gui of the game

