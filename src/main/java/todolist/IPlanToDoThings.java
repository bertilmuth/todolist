package todolist;

public interface IPlanToDoThings {

	void addItem(String itemText);

	void removeItem(int itemNumber);

	void editItem(int itemNumber, String newText);

	String itemText(int itemNumber);

	int numberOfItems();

}