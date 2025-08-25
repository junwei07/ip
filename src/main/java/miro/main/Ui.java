package miro.main;

import miro.task.Task;

import java.util.ArrayList;

public class Ui {
    StringBuilder output;

    public Ui() {
        this.output = new StringBuilder();
    }

    private String space() {
        return "    ";
    }

    private void Hbar() {
        System.out.println(space() + "───────────────────────────────");
    }

    private void append(String str) {
        output.append(space());
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
    public void greet() {
        append("Hello! I'm Miro.");
        append("What can I do for you?");

        outputMessage();
        reset();
    };

    /**
     * Prints message on screen.
     *
     * @param message The message to output.
     */
    public void output(String message) {
        append(message);

        outputMessage();
        reset();
    }

    /**
     * Prints the output message from string builder on screen.
     *
     */
    public void outputMessage() {
        Hbar();
        System.out.print(output.toString());
        Hbar();
    }

    /**
     * Prints the task list.
     *
     * @param taskList The task list to output.
     */
    public void printTaskList(ArrayList<Task> taskList) {
        append("Here are the list of tasks:");

        for (int i = 0; i < taskList.size(); i++) {
            String str = String.format("%d. %s", i + 1, taskList.get(i).toString());

            append(str);
        }

        outputMessage();
        reset();
    }

    /**
     * Prints message when task is added to task list.
     *
     * @param task The task being added to the task list.
     * @param taskCount The number of tasks in the task list.
     */
    public void addTaskSuccess(Task task, int taskCount) {
        append("Got it. I've added this task:");
        append(task.toString());
        append(String.format("Now you have %d tasks in the list.", taskCount));

        outputMessage();
        reset();
    }

    /**
     * Prints message when task is deleted from task list.
     *
     * @param task The task being deleted from the task list.
     */
    public void deleteTaskSuccess(Task task) {
        append("Noted. I've removed this task from the list:");
        append(task.toString());

        outputMessage();
        reset();
    }

    /**
     * Prints message when task is marked as done.
     *
     * @param task The marked task.
     */
    public void markTask(Task task) {
        append("Good job! I've marked this task as done");
        append(task.toString());

        outputMessage();
        reset();
    }

    /**
     * Prints message when task is marked as not done.
     *
     * @param task The unmarked task.
     */
    public void unmarkTask(Task task) {
        append("Ok, I've unmarked this task");
        append(task.toString());

        outputMessage();
        reset();
    }

}
