package es.quizit.twodoos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

	EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);

		String taskText = getIntent().getStringExtra(ToDoActivity.TASK_VALUE_ARG);

		editText = (EditText) findViewById(R.id.etEditItem);
		editText.setText(taskText);
	}

	public void onSave(View view) {
		Intent intent = new Intent();
		intent.putExtra(ToDoActivity.TASK_POSITION_ARG,
				getIntent().getIntExtra(ToDoActivity.TASK_POSITION_ARG, 0));
		intent.putExtra(ToDoActivity.TASK_VALUE_ARG, editText.getText().toString());

		setResult(RESULT_OK, intent);
		finish();
	}
}
