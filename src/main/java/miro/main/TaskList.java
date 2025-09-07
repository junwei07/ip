package miro.main;

import miro.task.Task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns the array of tasks.
     *
     * @return The array of tasks.
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void add(Task task) {
        taskList.add(task);
    }

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
