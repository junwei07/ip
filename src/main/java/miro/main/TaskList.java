package miro.main;

import java.util.ArrayList;
import java.util.List;

import miro.task.Task;

/**
 * Represents a task list.
 */
public class TaskList {
    private final List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns the array of tasks.
     *
     * @return The array of tasks.
     */
    public List<Task> getTaskList() {
        return taskList;
    }

    public void add(Task task) {
        taskList.add(task);
    }

    /**
     * Deletes a task from the task list.
     */
    public void delete(int index) {
        assert index >= 0 && index < taskList.size() : "task number out of range";

        taskList.remove(index);
    }

    /**
     * Returns the task at a given index.
     *
     * @return The index of the task.
     */
    public Task get(int index) {
        assert index >= 0 && index < taskList.size() : "task number out of range";

        return taskList.get(index);
    }

    public int size() {
        return taskList.size();
    }
}
