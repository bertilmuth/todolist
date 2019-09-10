package todolist;

import todolist.TodoList.Item;

public class UndoableTodoList implements IPlanToDoThings {
	private final TodoList todoList;

	public UndoableTodoList() {
		this.todoList = new TodoList();
	}

	@Override
	public void addItem(String itemText) {
		todoList.addItem(itemText);
	}

	@Override
	public void removeItem(int itemNumber) {
		todoList.removeItem(itemNumber);
	}

	@Override
	public void editItem(int itemNumber, String newText) {
		todoList.editItem(itemNumber, newText);
	}

	@Override
	public Item item(int itemNumber) {
		return todoList.item(itemNumber);
	}

	@Override
	public int numberOfItems() {
		return todoList.numberOfItems();
	}
}
