package es.quizit.twodoos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import es.quizit.twodoos.models.TodoItem;

public class EditItemActivity extends AppCompatActivity {

	EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);

		TodoItem todoItem = TodoItem.load(TodoItem.class,
				getIntent().getLongExtra(ToDoActivity.INDEX_EXTRA, 0));

		editText = (EditText) findViewById(R.id.etEditItem);
		if (todoItem != null) {
			editText.setText(todoItem.getTitle());
		}
	}

	public void onSave(View view) {
		Intent intent = new Intent();

		TodoItem todoItem = TodoItem.load(TodoItem.class,
				getIntent().getLongExtra(ToDoActivity.INDEX_EXTRA, 0));

		if (todoItem != null) {
			todoItem.setTitle(editText.getText().toString());
			todoItem.save();
		}

		setResult(RESULT_OK, intent);
		finish();
	}
}
