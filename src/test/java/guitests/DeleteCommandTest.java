package guitests;

import static seedu.doit.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.doit.testutil.TestTask;
import seedu.doit.testutil.TestUtil;

public class DeleteCommandTest extends TaskManagerGuiTest {
    // @@author: A0160076L
    @Test
    public void delete() {

        //delete the first in the list
        TestTask[] currentList = this.td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     *
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList           A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestUtil.sortTasks(currentList);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        this.commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertAllPanelsMatch(expectedRemainder);

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
