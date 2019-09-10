package todolist;

import todolist.TodoList.Item;

public interface IPlanToDoThings {

	void addItem(String itemText);

	void removeItem(int itemNumber);

	void editItem(int itemNumber, String newText);

	Item item(int itemNumber);

	int numberOfItems();

}