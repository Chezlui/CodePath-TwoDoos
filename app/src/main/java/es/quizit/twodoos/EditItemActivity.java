package es.quizit.twodoos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import es.quizit.twodoos.models.TodoItem;

public class EditItemActivity extends AppCompatActivity {

	EditText etTitle;
	TextView tvDate;
	private TextView tvCompletion;
	private Spinner spStatus;
	private EditText etDescription;
	private Spinner spPriority;
	private SeekBar sbCompletion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);

		TodoItem todoItem = TodoItem.load(TodoItem.class,
				getIntent().getLongExtra(ToDoActivity.INDEX_EXTRA, 0));

		etTitle = (EditText) findViewById(R.id.etEditItem);
		etDescription = (EditText) findViewById(R.id.etEditDescription);
		tvDate = (TextView) findViewById(R.id.tvDueDate);

		Button btPickDate = (Button) findViewById(R.id.btnPickDate);
		btPickDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment dateFragment = new DatePickerFragment();
				Bundle bundle = new Bundle();
				bundle.putInt(DatePickerFragment.DATE_VIEW, tvDate.getId());
				dateFragment.setArguments(bundle);
				dateFragment.show(getSupportFragmentManager(), "Pick a date");
			}
		});

		spPriority = (Spinner) findViewById(R.id.spPriority);
		spStatus = (Spinner) findViewById(R.id.spStatus);
		tvCompletion = (TextView) findViewById(R.id.tvCompletion);
		sbCompletion = (SeekBar) findViewById(R.id.sbCompletion);


		spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					sbCompletion.setProgress(0);
				}

				if (position == 2) {
					sbCompletion.setProgress(100);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});






		sbCompletion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				tvCompletion.setText("Completion: " + progress + "%");

				// Change status according to completion
				if (progress == 0) {
					spStatus.setSelection(0);
				} else if (progress == 100) {
					spStatus.setSelection(2);
				} else {
					spStatus.setSelection(1);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		if (todoItem != null) {
			etTitle.setText(todoItem.getTitle());
			etDescription.setText(todoItem.getDescription());
			if (todoItem.getDueDate() != 0) {
				tvDate.setText(Utils.convertDate2FriendlyString(todoItem.getDueDate()));
			} else {
				tvDate.setText(Utils.convertDate2FriendlyString(System.currentTimeMillis()));
			}
			spPriority.setSelection(todoItem.getPriority());
			spStatus.setSelection(todoItem.getStatus());
			sbCompletion.setProgress((int) (100 * todoItem.getCompletion()));
		}
	}

	public void onSave(View view) {
		Intent intent = new Intent();

		TodoItem todoItem = TodoItem.load(TodoItem.class,
				getIntent().getLongExtra(ToDoActivity.INDEX_EXTRA, 0));

		if (todoItem != null) {
			todoItem.setTitle(etTitle.getText().toString());
			todoItem.setDescription(etDescription.getText().toString());
			todoItem.setDueDate(Utils.convertFriendlyDate2Milliseconds(
					tvDate.getText().toString()));
			todoItem.setPriority(spPriority.getSelectedItemPosition());
			todoItem.setStatus(spStatus.getSelectedItemPosition());
			todoItem.setCompletion(sbCompletion.getProgress() / (float)100);
			todoItem.save();
		}

		setResult(RESULT_OK, intent);
		finish();
	}
}
