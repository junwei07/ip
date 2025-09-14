package miro.main;

import java.util.List;

import miro.task.Task;

/**
 * Represents a UI of the app.
 * The <code>output</code> field is of type <code>StringBuilder</code> which
 * receives the message to be printed on screen.
 */
public class Ui {
    private StringBuilder output;

    public Ui() {
        this.output = new StringBuilder();
    }

    private void append(String str) {
        output.append(str);
        output.append("\n");
    }

    private void reset() {
        output.setLength(0);
    }

    /**
     * Prints greeting message when chatbot is launched.
     *
     */
    public String greet() {
        append("Meow! I'm Miro, you purr-sonal assistant.");
        append("What can I do for you?");

        return outputMessage();
    }

    /**
     * Prints message on screen.
     *
     * @param message The message to output.
     */
    public String output(String message) {
        append(message);

        return outputMessage();
    }

    /**
     * Prints the output message from string builder on screen.
     *
     */
    public String outputMessage() {
        String finalOutput = output.toString();
        reset();
        return finalOutput;
    }

    /**
     * Prints the task list.
     *
     * @param taskList The task list to output.
     */
    public String printTaskList(List<Task> taskList) {
        append("Here are your tasks:");

        for (int i = 0; i < taskList.size(); i++) {
            String str = String.format("%d. %s", i + 1, taskList.get(i).toString());
            append(str);
        }

        if (taskList.size() > 5) {
            append("That's a lot of mice to chase!");
        }

        return outputMessage();

    }

    /**
     * Prints message when task is added to task list.
     *
     * @param task The task being added to the task list.
     * @param taskCount The number of tasks in the task list.
     */
    public String addTaskSuccess(Task task, int taskCount) {
        append("Purr-fect! I've added this task:");
        append(task.toString());
        append(String.format("Now you have %d tasks.", taskCount));

        return outputMessage();
    }

    /**
     * Prints message when task is deleted from task list.
     *
     * @param task The task being deleted from the task list.
     */
    public String deleteTaskSuccess(Task task) {
        append("Noted. I've removed this task from the list:");
        append(task.toString());

        return outputMessage();
    }

    /**
     * Prints message when task is marked as done.
     *
     * @param task The marked task.
     */
    public String markTask(Task task) {
        append("Good job! I've marked this task as done:");
        append(task.toString());
        append("Time for a cat nap?");

        return outputMessage();
    }

    /**
     * Prints message when task is marked as not done.
     *
     * @param task The unmarked task.
     */
    public String unmarkTask(Task task) {
        append("Ok, I've unmarked this task:");
        append(task.toString());
        append("Back to the hunt!");

        return outputMessage();
    }

    /**
     * Prints message when task is marked as not done.
     *
     * @param tasklist The task list.
     *
     * @return A string representation of the task list.
     */
    public String searchedTasks(List<Task> tasklist) {
        if (!tasklist.isEmpty()) {
            append("Here are the matching tasks in your list:");
            for (Task task : tasklist) {
                append(task.toString());
            }
        } else {
            append("Oops! No such task found.");
        }

        return outputMessage();
    }

    /**
     * Prints message when task is marked as not done.
     *
     * @param task The updated task.
     *
     * @return A string representation of the task list.
     */
    public String updatedTaskSuccess(Task task) {
        append("Ok, I've updated this task:");
        append(task.toString());

        return outputMessage();
    }
}
