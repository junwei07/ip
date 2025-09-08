package miro.task;

import miro.exception.MiroException;

/**
 * Represents a to-do task.
 */
public class ToDoTask extends Task {

    public ToDoTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }

    @Override
    public String getOutputFormat() {
        return "T | " + super.getOutputFormat();
    }

    @Override
    public void update(String[] words) throws MiroException {
        // check params of input
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < words.length; i++) {
            sb.append(words[i]);
            sb.append(" ");
        }

        if (!sb.toString().isEmpty()) {
            super.updateDescription(sb.toString().strip());

        } else {
            throw new MiroException("Nothing to update!");
        }

    }
}
