package es.quizit.twodoos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import es.quizit.twodoos.adapters.TodoItemAdapter;
import es.quizit.twodoos.models.TodoItem;

public class ToDoActivity extends AppCompatActivity {
	private static final String TODO_FILE = "todo.txt";
	private static final int REQUEST_CODE = 1;
	public static final String INDEX_EXTRA = "task_position";
	public static final String TASK_VALUE_ARG = "task_value";
	ArrayList<TodoItem> items;
	ListView lvItems;
	private TodoItemAdapter todoItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		lvItems = (ListView) findViewById(R.id.listView);
		items = new ArrayList<TodoItem>();
		todoItemAdapter = new TodoItemAdapter(this, items);

		List<TodoItem> queryResults = new Select().from(TodoItem.class)
				.execute();

		todoItemAdapter.addAll(queryResults);

		lvItems.setAdapter(todoItemAdapter);
		setupListViewListener();
	}

	public void onAddItem(View view) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		if(etNewItem.getText().length()== 0) return;
		String itemText = etNewItem.getText().toString();
		TodoItem todoItem = new TodoItem(itemText);
		todoItem.save();
		todoItemAdapter.add(todoItem);
		etNewItem.setText("");
	}

	private void setupListViewListener() {
		// Delete items when clicked
		lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				TodoItem item2delete = items.get(position);
				item2delete.delete();
				todoItemAdapter.remove(item2delete);
				return true;
			}
		});

		lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TodoItem item2delete = items.get(position);
				Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
				intent.putExtra(INDEX_EXTRA, item2delete.getId());
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			List<TodoItem> queryResults = new Select().from(TodoItem.class)
					.execute();

			todoItemAdapter.clear();
			todoItemAdapter.addAll(queryResults);
		}
	}
}
