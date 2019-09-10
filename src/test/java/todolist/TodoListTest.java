package todolist;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TodoListTest {
	private static final String FIRST_ITEM = "First item";
	private static final String SECOND_ITEM = "Second item";
	private static final String OLD_TEXT = "Old text";
	private static final String NEW_TEXT = "New text";
	
	private IPlanToDoThings todoList;

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
		assertEquals(FIRST_ITEM, todoList.item(0).text());
	}
	
	@Test
	public void adding_two_item_works() {
		todoList.addItem(FIRST_ITEM);
		todoList.addItem(SECOND_ITEM);
		assertEquals(2, todoList.numberOfItems());
		assertEquals(FIRST_ITEM, todoList.item(0).text());
		assertEquals(SECOND_ITEM, todoList.item(1).text());
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
		assertEquals(FIRST_ITEM, todoList.item(0).text());
	}
	
	@Test
	public void adding_two_items_and_removing_first_item_leaves_second_item() {
		todoList.addItem(FIRST_ITEM);
		todoList.addItem(SECOND_ITEM);
		todoList.removeItem(0);
		assertEquals(1, todoList.numberOfItems());
		assertEquals(SECOND_ITEM, todoList.item(0).text());
	}
	
	@Test
	public void editing_item_works() {
		todoList.addItem(OLD_TEXT);
		todoList.editItem(0, NEW_TEXT);
		assertEquals(1, todoList.numberOfItems());
		assertEquals(NEW_TEXT, todoList.item(0).text());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void accessing_item_with_too_low_number_throws_exception() {
		todoList.item(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void accessing_item_with_too_high_number_throws_exception() {
		todoList.addItem(FIRST_ITEM);
		todoList.item(1);
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
