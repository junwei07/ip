package miro.task;

import miro.exception.MiroException;

/**
 * Represents a task.
 * The <code>description</code> field stores the description of the task.
 * The <code>isDone</code> field indicates if the task has been marked as done.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * The constructor for the task.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        String box = isDone ? "[X]" : "[ ]";
        return box + " " + description;
    }

    /**
     * Returns a string that details the type, state (marked/ unmarked) and
     * description of a task.
     *
     * @return The task information to be stored local file.
     */
    public String getOutputFormat() {
        int marked = isDone ? 1 : 0;
        return marked + " | " + description;
    }

    public void update(String[] words) throws MiroException {

    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
