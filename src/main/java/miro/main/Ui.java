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

    public void greet() {
        append("Hello! I'm miro.main.Miro.");
        append("What can I do for you?");

        outputMessage();
        reset();
    };

    public void output(String message) {
        append(message);

        outputMessage();
        reset();
    }

    // main output message
    public void outputMessage() {
        Hbar();
        System.out.print(output.toString());
        Hbar();
    }

    public void printTaskList(ArrayList<Task> taskList) {
        append("Here are the list of tasks:");

        for (int i = 0; i < taskList.size(); i++) {
            String str = String.format("%d. %s", i + 1, taskList.get(i).toString());

            append(str);
        }

        outputMessage();
        reset();
    }

    public void addTaskSuccess(Task task, int taskCount) {
        append("Got it. I've added this task:");
        append(task.toString());
        append(String.format("Now you have %d tasks in the list.", taskCount));

        outputMessage();
        reset();
    }

    public void deleteTaskSuccess(Task task) {
        append("Noted. I've removed this task from the list:");
        append(task.toString());

        outputMessage();
        reset();
    }

    public void markTask(Task task) {
        append("Good job! I've marked this task as done");
        append(task.toString());

        outputMessage();
        reset();
    }
    public void unmarkTask(Task task) {
        append("Ok, I've unmarked this task");
        append(task.toString());

        outputMessage();
        reset();
    }

}
