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

    public String getOutputFormat() {
        int marked = isDone ? 1 : 0;
        return marked + " | " + description;
    }
}
