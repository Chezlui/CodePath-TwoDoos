package es.quizit.twodoos.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.quizit.twodoos.R;
import es.quizit.twodoos.Utils;
import es.quizit.twodoos.models.TodoItem;

/**
 * Created by Chezlui on 13/01/2016.
 */
public class TodoItemAdapter extends ArrayAdapter<TodoItem> {
	public TodoItemAdapter(Context context, int resource, List<TodoItem> objects) {
		super(context, resource, objects);
	}

	public TodoItemAdapter(Context context, List<TodoItem> objects) {
		super(context, -1, objects);
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_todo_list, parent, false);
		TextView tvToDoTitle = (TextView) rowView.findViewById(R.id.todo_name);
		TextView tvDueDate = (TextView) rowView.findViewById(R.id.todo_dueDate);

		TodoItem todoItem = getItem(position);
		tvToDoTitle.setText(todoItem.getTitle());

		if(todoItem.getDueDate() != 0) {
			tvDueDate.setText(Utils.convertDate2FriendlyString(todoItem.getDueDate()));
		}

		if(todoItem.getCompletion() != 1) {
			highlightCalendarIcon(rowView, todoItem.getPriority());
		} else {
			ImageView imageCalendar = (ImageView) rowView.findViewById(R.id.imageViewCalendar);
			Drawable drawable;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				drawable = getContext().getResources().getDrawable(
						R.drawable.ic_done_black_24dp, getContext().getTheme());
			} else {
				drawable = getContext().getResources().getDrawable(
						R.drawable.ic_done_black_24dp
				);
			}
			imageCalendar.setImageDrawable(drawable);
			tvDueDate.setText("");
			tvToDoTitle.setPaintFlags(tvToDoTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}

		return rowView;
	}


	/**
	 * Change the color of the calendar icon to indicate priority to the user
	 * @param containerView
	 * @param priority
	 */
	private void highlightCalendarIcon(View containerView, int priority) {
		Drawable drawable = ((ImageView)containerView.findViewById(R.id.imageViewCalendar))
				.getDrawable();

		int highlight = chooseColorbyPriority(priority);
		if (drawable != null) {
			drawable.setColorFilter(highlight, PorterDuff.Mode.SRC_ATOP);
		}

		ImageView imageCalendar = (ImageView) containerView.findViewById(R.id.imageViewCalendar);
		imageCalendar.setImageDrawable(drawable);
	}

	@SuppressWarnings("ResourceType")
	private int chooseColorbyPriority(int priority) {
		switch (priority) {
			case 0: return Color.parseColor(getContext()
					.getResources().getString(R.color.event_green));
			case 1: return Color.parseColor(getContext()
					.getResources().getString(R.color.event_yellow));
			case 2: return Color.parseColor(getContext()
					.getResources().getString(R.color.event_red));
			default: return Color.parseColor(getContext()
					.getResources().getString(android.R.color.black));
		}

	}

}
