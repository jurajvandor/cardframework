# cardframework
framework for creating multiplayer card games and implementation of game Rummy in Java using JavaFX for GUI

Use:
To play Basic Rummy implementation packed in .jar file you need to have JRE version 8 or higher installed. Then you can just run jar files of client and server from file explorer. Those .jar files are located in out/artifacts/ folder of this repository.

As developer you need to import this Maven project to IDE. It was created in IDEA from JetBrains, in which it was tested (on Windows 10 and Ubuntu 16.04)

To create your own game you need to copy BlankUI package and rename it and its mentions in files insied or just rename it and insert your code to this template primary where //TODO is written. If you decide to do a copy of this package you need to copy GameGUI.fxml as well and change path to Controller class inside this file. 

To deploy in IDEA you can add artifacts to build using File > Project Structure.. > Artifacts > green + > JavaFX application > choose GameGUI class and the same thing for ServerGUI class. Then open JavaFX tab for both and insert name of these classes. Then you just build them using Build > Build Artifacts > All artifacts. 

Already built .jar files for Basic Rummy game can be found in out/ folder.

