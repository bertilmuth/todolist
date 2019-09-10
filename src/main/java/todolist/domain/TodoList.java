package todolist.domain;

import java.util.ArrayList;
import java.util.List;

class TodoList implements IPlanToDoThings {
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
	public String itemText(int itemNumber) {
		ifInvalidThrowIllegalArgumentException(itemNumber);
		return item(itemNumber).text();
	}
	Item item(int itemNumber) {
		ifInvalidThrowIllegalArgumentException(itemNumber);
		return items.get(itemNumber);
	}

	@Override
	public int numberOfItems() {
		return items.size();
	}
	
	void insertItem(int itemNumber, String itemText) {
		Item item = new Item(itemText);
		items.add(itemNumber, item);
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

		private Item(String text) {
			this.text = text;
		}

		public String text() {
			return text;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Your Todos Are:\n");
		for (Item item : items) {
			stringBuilder.append(item.text());
			stringBuilder.append('\n');
		}
		return stringBuilder.toString();
	}
}
