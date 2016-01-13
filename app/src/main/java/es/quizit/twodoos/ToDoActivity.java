package es.quizit.twodoos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {
	private static final String TODO_FILE = "todo.txt";
	private static final int REQUEST_CODE = 1;
	public static final String TASK_POSITION_ARG = "task_position";
	public static final String TASK_VALUE_ARG = "task_value";
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		lvItems = (ListView) findViewById(R.id.listView);
		readItems();
		itemsAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, items);

		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
	}

	public void onAddItem(View view) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		if(etNewItem.getText().length()== 0) return;
		String itemText = etNewItem.getText().toString();
		itemsAdapter.add(itemText);
		etNewItem.setText("");
		writeItems();
	}

	private void setupListViewListener() {
		// Delete items when clicked
		lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});

		lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
				intent.putExtra(TASK_POSITION_ARG, position);
				intent.putExtra(TASK_VALUE_ARG, itemsAdapter.getItem(position));
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, TODO_FILE);
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
		}
	}

	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, TODO_FILE);
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			String edittedTask = data.getExtras().getString(TASK_VALUE_ARG);
			int position = data.getExtras().getInt(TASK_POSITION_ARG);
			items.set(position, edittedTask);
			itemsAdapter.notifyDataSetChanged();
			writeItems();
		}
	}
}
