package guitests;

import org.junit.Test;

import seedu.doit.testutil.TestTask;
import seedu.doit.testutil.TypicalTestTasks;

public class UndoCommandTest extends TaskManagerGuiTest {

    public static final String MESSAGE_UNDO_COMMAND = "undo";
    public static final String MESSAGE_TEST_CLEAR_COMMAND = "clear";
    public static final String MESSAGE_TEST_EDIT_COMMAND = "edit 2 t/hi";
    public static final String MESSAGE_TEST_DONE_COMMAND = "done 5";
    public static final String MESSAGE_TEST_DELETE_COMMAND = "delete 7";

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = this.td.getTypicalTasks();

    @Test
    public void undo_add_success() throws Exception {
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        this.commandBox.runCommand(taskToAdd.getAddCommand());
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
    }

    @Test
    public void undo_delete_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_DELETE_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
    }

    @Test
    public void undo_edit_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_EDIT_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
    }

    @Test
    public void undo_done_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_DONE_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
    }

    @Test
    public void undo_clear_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_CLEAR_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
    }

    private void assertUndoSuccess() {
        // confirm the list now contains all previous tasks plus the new task
        assertAllPanelsMatch(expectedTasksList);
    }

}
