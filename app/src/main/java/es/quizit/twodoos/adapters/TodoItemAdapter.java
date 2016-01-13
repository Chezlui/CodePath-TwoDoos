package es.quizit.twodoos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.quizit.twodoos.R;
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
		TextView textView = (TextView) rowView.findViewById(R.id.todo_name);
		TodoItem todoItem = getItem(position);
		textView.setText(todoItem.getTitle());

		return rowView;
	}
}
