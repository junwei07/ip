package miro.task;

/**
 * Represents a task.
 * The <code>description</code> field stores the description of the task.
 * The <code>isDone</code> field indicates if the task has been marked as done.
 */
public class Task {
    private final String description;
    private boolean isDone;

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
}
