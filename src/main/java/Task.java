public class Task {
    private final String task;
    private boolean isDone;

    public Task(String task) {
        this.task = task;
        this.isDone = false;
    }

    public String getName() {
        return this.task;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String toString() {
        String box = isDone ? "[X]" : "[ ]";
        return box + " " + task;
    }
}
