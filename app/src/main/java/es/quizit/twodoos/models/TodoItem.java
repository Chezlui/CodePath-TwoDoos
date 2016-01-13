package es.quizit.twodoos.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.StringTokenizer;

/**
 * Created by Chezlui on 13/01/2016.
 */
@Table(name = "TodoItems")
public class TodoItem extends Model{
	@Column(name = "title")
	String title;

	@Column(name = "description")
	String description;

	@Column(name = "dueDate")
	long dueDate;

	@Column(name = "priority")
	int priority;

	@Column(name = "status")
	int status;

	@Column(name = "completion")
	float completion;

	public TodoItem() {
		super();
	}


	public TodoItem(String title) {
		super();
		this.title = title;
	}

	public float getCompletion() {
		return completion;
	}

	public void setCompletion(float completion) {
		this.completion = completion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDueDate() {
		return dueDate;
	}

	public void setDueDate(long dueDate) {
		this.dueDate = dueDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
