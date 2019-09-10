package todolist;

import java.util.ArrayList;
import java.util.List;

public class TodoList implements IPlanToDoThings {
	private final List<Item> items;

	public TodoList() {
		this.items = new ArrayList<>();
	}

	@Override
	public void addItem(String itemText) {
		items.add(new Item(itemText));
	}
	
	@Override
	public void removeItem(int itemNumber) {
		ifInvalidThrowIllegalArgumentException(itemNumber);
		items.remove(itemNumber);
	}
	
	@Override
	public void editItem(int itemNumber, String newText) {
		ifInvalidThrowIllegalArgumentException(itemNumber);
		Item newItem = new Item(newText);
		items.set(itemNumber, newItem);
	}

	@Override
	public Item item(int itemNumber) {
		ifInvalidThrowIllegalArgumentException(itemNumber);
		return items.get(itemNumber);
	}

	@Override
	public int numberOfItems() {
		return items.size();
	}
	
	private void ifInvalidThrowIllegalArgumentException(int itemNumber) {
		if (isItemNumberInvalid(itemNumber)) {
			throw new IllegalArgumentException("Item number " + itemNumber + " is invalid");
		}
	}
	private boolean isItemNumberInvalid(int itemNumber) {
		return itemNumber < 0 || itemNumber >= numberOfItems();
	}

	public class Item {
		private final String text;

		public Item(String text) {
			this.text = text;
		}

		public String text() {
			return text;
		}
	}
}
