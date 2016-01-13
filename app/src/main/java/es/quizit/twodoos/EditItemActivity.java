package es.quizit.twodoos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.Date;

import es.quizit.twodoos.models.TodoItem;

public class EditItemActivity extends AppCompatActivity {

	EditText etTitle;
	EditText etDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);

		TodoItem todoItem = TodoItem.load(TodoItem.class,
				getIntent().getLongExtra(ToDoActivity.INDEX_EXTRA, 0));

		etTitle = (EditText) findViewById(R.id.etEditItem);
		EditText etDescription = (EditText) findViewById(R.id.etEditDescription);
		etDate = (EditText) findViewById(R.id.etPickDate);

		// Callback for the Date Picker
		final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				etDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
			}
		};

		etDate.setClickable(true);
		etDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment dateFragment = new DatePickerFragment();
				dateFragment.show(getSupportFragmentManager(), "Pick a date");
			}
		});
		Spinner spPriority = (Spinner) findViewById(R.id.spPriority);
		Spinner spStatus = (Spinner) findViewById(R.id.spStatus);
		SeekBar sbCompletion = (SeekBar) findViewById(R.id.sbCompletion);

		if (todoItem != null) {
			etTitle.setText(todoItem.getTitle());
			etDescription.setText(todoItem.getDescription());
			etDate.setText((new Date(todoItem.getDueDate())).toString());
			spPriority.setSelection(todoItem.getPriority());
			spStatus.setSelection(todoItem.getStatus());
			sbCompletion.setProgress((int) (100 *  todoItem.getCompletion()));
		}
	}

	public void onSave(View view) {
		Intent intent = new Intent();

		TodoItem todoItem = TodoItem.load(TodoItem.class,
				getIntent().getLongExtra(ToDoActivity.INDEX_EXTRA, 0));

		if (todoItem != null) {
			todoItem.setTitle(etTitle.getText().toString());
			todoItem.save();
		}

		setResult(RESULT_OK, intent);
		finish();
	}
}
