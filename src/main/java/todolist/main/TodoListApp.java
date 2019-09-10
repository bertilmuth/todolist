package todolist.main;

import todolist.domain.UndoableTodoList;

public class TodoListApp {

	public static void main(String[] args) {
		UndoableTodoList todoList = new UndoableTodoList();
		todoList.addItem("Do the dishes");
		todoList.addItem("Do laundry");
		todoList.addItem("Mow the lawn");
		todoList.undo();
		System.out.println(todoList);
	}

}
