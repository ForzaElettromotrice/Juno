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

1. Started working on the GUI: created controllers, view and FXML files for the various menus.
2. Designed Start menu and implemented exit, stats and settings buttons: the last two change scene,
    the first one closes the game.
3. Designed stats menu with back button and created the GenView: it is a class that includes
    the methods shared between the various views, such as change scene and the loader.
4. Started designing settings with back button.

##09-11/05/2022

1. Chosen images to use in StartMenu layout, for example the background.
2. Designed the game logo.
3. Finished designing StartMenu layout: four buttons (exit, stats, settings and play) that have
    a lights border appearing on mouse passing.

##12/05/2022

1. Created the login scene: if it is the first access or if data is corrupted, it will ask to 
    choose a username and a standard avatar. It also saves them in user.txt.
2. Created NonExistingSceneException and DataCorruptedException.
3. Finished designing StatsMenu layout: it shows and lets you change avatar and username; it
    also shows victories, defeats, total matches, current level and experience gained. The avatar
    can be chosen from the images on your pc, and you can't choose other types of file.
4. Created a stylesheet to set backgrounds.
5. Made border appear when mouse passes on buttons in all menus.
6. Finished designing Login layout.

##13-15/05/2022

1. Finished designing Settings layout: it lets you change music and sound volume; delete your
    account; reset your level.
2. Created the AudioPlayer class that onluy has the method to play sounds and music.
3. Chose music and sounds and implemented music menu.
4. Designed the ChooseMode layout: it lets you choose between classic, trade and reflex modes.
    On mouse passing, it shows a brief description of modes.
5. Removed the various views since we only need GenView for now.
6. Added a new font to be used in the whole project.

##18 to 20/05/2022

1. Added transitions between scenes when changed.
2. Added some decorative images on mouse passing in ChooseMode.
3. Now the user can set the avatar in stats as one of the default avatars or choose one
    from their pc.
4. Added sounds to menus.
5. Converted sound compendium to enum.

##21/05/2022

1. Changed cards design.
2. Renamed and refactored some files.
3. Modified AudioPlayer as a Singleton and added some useful methods (stop, setVolume etc).
4. Created the gameplay classic scene: it only shows the user cards, while bots cards are 
    shown backwards. In the center there are the discard and draw pile. The players hands 
    automatically resize when a card is addded and when it is a player's turn the corrispondent
    circle is colored with yellow.
5. Created the datapackage: it contains various classes and records that deliver different messages
    from model to view. The Data interface models a general message; DiscardData is a record that 
    says a card has been discarded; and so on. Every record has different arguments to show
    what the view needs to change. Then there is the BuildMP that is the constructor of the records:
    it has three enums for the current player, for actions and for card effects, and it is a Singleton.
6. Now GenView is Observer: when it receives a message it updates the view transferring the message
    to the Gameplay Controller.
7. Player, Bot and Table are Observable: when something changes inside them, they send a message
    to GenView with notifyObservers.
8. Now the game plays on multiple threads. It is starting to work!

##22/05/2022

1. Changed music when the game starts.
2. Created the methods that change the view on notify: turn, discard, draw, effect (effects are for 
    example when the user has only one card or when the match has ended), choosing a color when a 
    +4 or a jolly are played, pass and say Uno. Also created methods for when a card is clicked.
3. Created the points overview for when a match has ended, and you can start the next match or go to
    main menu.
4. Created missing methods and fields in Bot, Player, TurnOrder and Table.
5. Resolved some bugs: added a Platform.runLater to remove problems with different threads, added a 
    thread sleep to give time to the observers to notify the view of card played, etc.
6. Now the game works fine!!

##TODO:

1. add sound effects to game
2. add animations
3. change back cards and colored wild cards
4. add different modes
5. gameplay background
6. make the buttons in points overview work...!

