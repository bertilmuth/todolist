package todolist.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class UndoableTodoList implements IPlanToDoThings {
	private final TodoList todoList;
	private final Deque<UndoRedoCommand> undoRedoCommands;
	private final Deque<Consumer<TodoList>> redoCommands;

	public UndoableTodoList() {
		this.todoList = new TodoList();
		this.undoRedoCommands = new ArrayDeque<>();
		this.redoCommands = new ArrayDeque<>();
	}
	
	void process(final Consumer<TodoList> undoCommand, final Consumer<TodoList> redoCommand) {
		executeOnTodoList(redoCommand);
		UndoRedoCommand undoRedoCommand = new UndoRedoCommand(undoCommand, redoCommand);
		undoRedoCommands.push(undoRedoCommand);
	}

	@Override
	public void addItem(String itemText) {
		final int newItemNumber = numberOfItems();

		final Consumer<TodoList> undoCommand = todoList -> todoList.removeItem(newItemNumber);
		final Consumer<TodoList> redoCommand = todoList -> todoList.addItem(itemText);

		process(undoCommand, redoCommand);
	}



	@Override
	public void removeItem(int itemNumber) {
		final String itemToBeRemovedText = todoList.item(itemNumber).text();
		
		final Consumer<TodoList> undoCommand = todoList -> todoList.insertItem(itemNumber, itemToBeRemovedText);
		final Consumer<TodoList> redoCommand = todoList -> todoList.removeItem(itemNumber);
		process(undoCommand, redoCommand);
	}

	@Override
	public void editItem(int itemNumber, String newText) {
		final String oldText = todoList.itemText(itemNumber);
		
		final Consumer<TodoList> undoCommand = todoList -> todoList.editItem(itemNumber, oldText);
		final Consumer<TodoList> redoCommand = todoList -> todoList.editItem(itemNumber, newText);
		process(undoCommand, redoCommand);
	}

	@Override
	public String itemText(int itemNumber) {
		return todoList.itemText(itemNumber);
	}

	@Override
	public int numberOfItems() {
		return todoList.numberOfItems();
	}

	public void undo() {
		if (undoRedoCommands.isEmpty()) {
			return;
		}

		UndoRedoCommand undoRedoCommand = undoRedoCommands.pop();
		Consumer<TodoList> undoCommand = undoRedoCommand.undoCommmand();
		Consumer<TodoList> redoCommand = undoRedoCommand.redoCommmand();
		
		executeOnTodoList(undoCommand);
		redoCommands.push(redoCommand);
	}

	public void redo() {
		if (redoCommands.isEmpty()) {
			return;
		}

		Consumer<TodoList> redoCommand = redoCommands.pop();
		executeOnTodoList(redoCommand); 
	}

	private void executeOnTodoList(Consumer<TodoList> todoListCommand) {
		todoListCommand.accept(todoList);
	}
	
	@Override
	public String toString() {
		return todoList.toString();
	}
	
	private class UndoRedoCommand{
		private final Consumer<TodoList> undoCommand;
		private final Consumer<TodoList> redoCommand;

		private UndoRedoCommand(Consumer<TodoList> undoCommand, Consumer<TodoList> redoCommand) {
			this.undoCommand = undoCommand;
			this.redoCommand = redoCommand;
		}
		
		private Consumer<TodoList> undoCommmand(){
			return undoCommand;
		}
		
		private Consumer<TodoList> redoCommmand(){
			return redoCommand;
		}
	}
}
