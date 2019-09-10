package todolist;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

import todolist.TodoList.Item;

public class UndoableTodoList implements IPlanToDoThings {
	private final TodoList todoList;
	private final Deque<Consumer<TodoList>> todoListUndoCommands;

	public UndoableTodoList() {
		this.todoList = new TodoList();
		this.todoListUndoCommands = new ArrayDeque<>();
	}

	@Override
	public void addItem(String itemText) {
		todoList.addItem(itemText);
		final int addedItemNumber = numberOfItems() - 1;
		todoListUndoCommands.push(todoList -> todoList.removeItem(addedItemNumber));
	}

	@Override
	public void removeItem(int itemNumber) {
		final Item itemToBeRemoved = todoList.item(itemNumber);
		todoListUndoCommands.push(todoList -> todoList.insertItem(itemNumber, itemToBeRemoved));
		todoList.removeItem(itemNumber);
	}

	@Override
	public void editItem(int itemNumber, String newText) {
		final String oldText = todoList.itemText(itemNumber);
		todoList.editItem(itemNumber, newText);
		todoListUndoCommands.push(todoList -> todoList.editItem(itemNumber, oldText));
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
		undoCommand.accept(todoList);
	}
}
