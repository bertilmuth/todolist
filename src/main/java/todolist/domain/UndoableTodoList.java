package todolist.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class UndoableTodoList implements IPlanToDoThings {
	private final TodoList todoList;
	private final Deque<Consumer<TodoList>> todoListUndoCommands;
	private final Deque<Consumer<TodoList>> todoListRedoCommands;

	public UndoableTodoList() {
		this.todoList = new TodoList();
		this.todoListRedoCommands = new ArrayDeque<>();
		this.todoListUndoCommands = new ArrayDeque<>();
	}

	@Override
	public void addItem(String itemText) {
		final int newItemNumber = numberOfItems();

		final Consumer<TodoList> command = todoList -> todoList.insertItem(newItemNumber, itemText);
		executeOnTodoList(command);
		todoListRedoCommands.push(command);

		final Consumer<TodoList> undoCommand = todoList -> todoList.removeItem(newItemNumber);
		todoListUndoCommands.push(undoCommand);
	}

	@Override
	public void removeItem(int itemNumber) {
		final String itemToBeRemovedText = todoList.item(itemNumber).text();
		
		final Consumer<TodoList> command = todoList -> todoList.removeItem(itemNumber);
		executeOnTodoList(command);
		todoListRedoCommands.push(command);
		
		final Consumer<TodoList> undoCommand = todoList -> todoList.insertItem(itemNumber, itemToBeRemovedText);
		todoListUndoCommands.push(undoCommand);
	}

	@Override
	public void editItem(int itemNumber, String newText) {
		final String oldText = todoList.itemText(itemNumber);
		
		final Consumer<TodoList> command = todoList -> todoList.editItem(itemNumber, newText);
		executeOnTodoList(command);
		todoListRedoCommands.push(command);
		
		final Consumer<TodoList> undoCommand = todoList -> todoList.editItem(itemNumber, oldText);
		todoListUndoCommands.push(undoCommand);
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
		if (todoListUndoCommands.isEmpty()) {
			return;
		}

		Consumer<TodoList> undoCommand = todoListUndoCommands.pop();
		executeOnTodoList(undoCommand);
	}

	public void redo() {
		if (todoListRedoCommands.isEmpty()) {
			return;
		}

		Consumer<TodoList> redoCommand = todoListRedoCommands.pop();
		executeOnTodoList(redoCommand);
	}

	private void executeOnTodoList(Consumer<TodoList> todoListCommand) {
		todoListCommand.accept(todoList);
	}
	
	@Override
	public String toString() {
		return todoList.toString();
	}
}
