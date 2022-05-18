##TIMELINE:

#15/05/2022

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

#TODO:

1. Implement User contructor
2. Implement getLevel method in User class
3. Implement load and save in User class
4. Implement load and save in Level class