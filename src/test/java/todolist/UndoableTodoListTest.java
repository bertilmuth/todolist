package todolist;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UndoableTodoListTest {
	private static final String FIRST_ITEM = "First item";
	private static final String SECOND_ITEM = "Second item";
	private static final String OLD_TEXT = "Old text";
	private static final String NEW_TEXT = "New text";
	private static final String ANOTHER_NEW_TEXT = "Another new text";
	
	private UndoableTodoList todoList;

	@Before
	public void setUp() throws Exception {
		todoList = new UndoableTodoList();
	}

	@Test
	public void new_todolist_is_empty() {
		assertEquals(0, todoList.numberOfItems());
	}
	
	@Test
	public void adding_one_item_works() {
		todoList.addItem(FIRST_ITEM);
		assertEquals(1, todoList.numberOfItems());
		assertEquals(FIRST_ITEM, todoList.itemText(0));
	}
	
	@Test
	public void adding_and_removing_item_and_undoing_leaves_item_in_list() {
		todoList.addItem(FIRST_ITEM);
		todoList.removeItem(0);
		todoList.undo();
		assertEquals(1, todoList.numberOfItems());
		assertEquals(FIRST_ITEM, todoList.itemText(0));
	}
	
	@Test
	public void undoing_adding_one_item_and_redoing_works() {
		todoList.addItem(FIRST_ITEM);
		todoList.undo();
		todoList.redo();
		assertEquals(1, todoList.numberOfItems());
		assertEquals(FIRST_ITEM, todoList.itemText(0));
	}
	
	@Test
	public void undoing_adding_one_item_works() {
		todoList.addItem(FIRST_ITEM);
		todoList.undo();
		assertEquals(0, todoList.numberOfItems());
	}
	
	@Test
	public void undoing_adding_one_item_twice_is_silent() {
		todoList.addItem(FIRST_ITEM);
		todoList.undo();
		todoList.undo();
		assertEquals(0, todoList.numberOfItems());
	}
	
	@Test
	public void adding_two_item_works() {
		todoList.addItem(FIRST_ITEM);
		todoList.addItem(SECOND_ITEM);
		assertEquals(2, todoList.numberOfItems());
		assertEquals(FIRST_ITEM, todoList.itemText(0));
		assertEquals(SECOND_ITEM, todoList.itemText(1));
	}
	
	@Test
	public void adding_and_removing_item_leaves_list_empty() {
		todoList.addItem(FIRST_ITEM);
		todoList.removeItem(0);
		assertEquals(0, todoList.numberOfItems());
	}
	
	@Test
	public void adding_two_items_and_removing_second_item_leaves_first_item() {
		todoList.addItem(FIRST_ITEM);
		todoList.addItem(SECOND_ITEM);
		todoList.removeItem(1);
		assertEquals(1, todoList.numberOfItems());
		assertEquals(FIRST_ITEM, todoList.itemText(0));
	}
	
	@Test
	public void adding_two_items_and_undoing_leaves_first_item() {
		todoList.addItem(FIRST_ITEM);
		todoList.addItem(SECOND_ITEM);
		todoList.undo();
		assertEquals(1, todoList.numberOfItems());
		assertEquals(FIRST_ITEM, todoList.itemText(0));
	}
	
	@Test
	public void adding_two_items_and_removing_first_item_leaves_second_item() {
		todoList.addItem(FIRST_ITEM);
		todoList.addItem(SECOND_ITEM);
		todoList.removeItem(0);
		assertEquals(1, todoList.numberOfItems());
		assertEquals(SECOND_ITEM, todoList.itemText(0));
	}
	
	@Test
	public void editing_item_works() {
		todoList.addItem(OLD_TEXT);
		todoList.editItem(0, NEW_TEXT);
		assertEquals(1, todoList.numberOfItems());
		assertEquals(NEW_TEXT, todoList.itemText(0));
	}
	
	@Test
	public void editing_item_and_undoing_leaves_old_item_text() {
		todoList.addItem(OLD_TEXT);
		todoList.editItem(0, NEW_TEXT);
		todoList.undo();
		assertEquals(1, todoList.numberOfItems());
		assertEquals(OLD_TEXT, todoList.itemText(0));
	}
	
	@Test
	public void editing_item_undoing_and_redoing_leaves_new_item_text() {
		todoList.addItem(OLD_TEXT);
		todoList.editItem(0, NEW_TEXT);
		todoList.undo();
		todoList.redo();
		assertEquals(1, todoList.numberOfItems());
		assertEquals(NEW_TEXT, todoList.itemText(0));
	}
	
	@Test
	public void editing_twice_and_undoing_leaves_first_edit() {
		todoList.addItem(OLD_TEXT);
		todoList.editItem(0, NEW_TEXT);
		todoList.editItem(0, ANOTHER_NEW_TEXT);
		todoList.undo();
		assertEquals(1, todoList.numberOfItems());
		assertEquals(NEW_TEXT, todoList.itemText(0));
	}
	
	@Test
	public void editing_twice_and_undoing_twice_leaves_original_item() {
		todoList.addItem(OLD_TEXT);
		todoList.editItem(0, NEW_TEXT);
		todoList.editItem(0, ANOTHER_NEW_TEXT);
		todoList.undo();
		todoList.undo();
		assertEquals(1, todoList.numberOfItems());
		assertEquals(OLD_TEXT, todoList.itemText(0));
	}
	
	@Test
	public void complex_adding_editing_and_removing_can_be_undone_step_by_step() {
		// Add first item - it will not survive long
		todoList.addItem(OLD_TEXT);
		todoList.editItem(0, NEW_TEXT);
		todoList.removeItem(0);

		// The list is now empty. Add a new item with a different text.
		// Then assert we are in the right state, before continuing.
		todoList.addItem(ANOTHER_NEW_TEXT);
		assertEquals(1, todoList.numberOfItems());
		assertEquals(ANOTHER_NEW_TEXT, todoList.itemText(0));
		
		// Undo once. Assert that the list is empty again.
		todoList.undo();
		assertEquals(0, todoList.numberOfItems());

		// Another undo, and the item is resurrected with new text.
		todoList.undo();
		assertEquals(NEW_TEXT, todoList.itemText(0));
		
		// Another undo, and we're back to the old text.
		todoList.undo();
		assertEquals(OLD_TEXT, todoList.itemText(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void accessing_item_with_too_low_number_throws_exception() {
		todoList.itemText(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void accessing_item_with_too_high_number_throws_exception() {
		todoList.addItem(FIRST_ITEM);
		todoList.itemText(1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removing_item_with_too_low_number_throws_exception() {
		todoList.removeItem(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removing_item_with_too_high_number_throws_exception() {
		todoList.addItem(FIRST_ITEM);
		todoList.removeItem(1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void editing_item_with_too_low_number_throws_exception() {
		todoList.editItem(-1, NEW_TEXT);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void editing_item_with_too_high_number_throws_exception() {
		todoList.addItem(FIRST_ITEM);
		todoList.editItem(1, NEW_TEXT);
	}
}
