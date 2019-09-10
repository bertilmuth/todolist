# What this project is about
[![Build Status](https://travis-ci.org/bertilmuth/todolist.svg?branch=master)](https://travis-ci.org/bertilmuth/todolist)

This project is a small to do list app that allows users to add, remove and edit to do list items.
The whole purpose of this app is to showcase which patterns to use for undoing/redoing user actions,
without changing  existing domain classes.

# How to run it
1. Go to the console and enter `java -version`. It should show that Java is installed and a version >= 8. You can get Java [here](https://jdk.java.net/12/).
2. Download the zip file contained in the `binaries` folder of this project, and unzip it to your hard drive.
3. Using the console, go to the unzipped folder on your hard drive. Enter the `bin` folder. 
5. Run the `todolistapp_java` script (Unix) or the `todolistapp_java.bat` script (Windows).


# How it works internally
* The [TodoList class](https://github.com/bertilmuth/todolist/blob/master/src/main/java/todolist/TodoList.java) represents the domain knowledge:
you can add, remove and edit items. A simple, zero-based index called _itemNumber_ is used to access specific items. 
* The [UndoableTodoList class](https://github.com/bertilmuth/todolist/blob/master/src/main/java/todolist/UndoableTodoList.java) is an adapter to the `TodoList` class that implements the same interface. Besides delegating to its own `TodoList` instance, it also keeps tracks of undo/redo commands.
