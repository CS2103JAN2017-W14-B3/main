// @@author A0139399J
package seedu.doit.model.comparators;

import seedu.doit.model.item.ReadOnlyTask;

/**
 * Compares ReadOnlyTasks by their endTime
 */
public class EndTimeComparator implements TaskComparator {

    private static final int CURR_BIGGER_THAN_OTHER = 1;
    private static final int CURR_SMALLER_THAN_OTHER = -1;

    @Override
    public int compare(ReadOnlyTask t1, ReadOnlyTask t2) {
        return compareDone(t1, t2);
    }

    /**
     * Compares the current task with another Task other.
     * The current item is considered to be less than the other item
     * if it is done and the other is not done.
     */
    private int compareDone(ReadOnlyTask curr, ReadOnlyTask other) {
        if (curr.getIsDone() && !other.getIsDone()) {
            return CURR_BIGGER_THAN_OTHER;
        } else if (!curr.getIsDone() && other.getIsDone()) {
            return CURR_SMALLER_THAN_OTHER;
        }
        return compareItems(curr, other);
    }

    /**
     * Compares the current item with another item other. returns -1 if other
     * item is greater than current item return 0 is both items are equal return
     * 1 if other item is smaller than current item The ranking are as follows
     * from highest: 1) tasks 2) events 3) floating tasks If both have same
     * rankings, then compare endTime or name if endTime == null
     */
    private int compareItems(ReadOnlyTask curr, ReadOnlyTask other) {
        Integer currType = curr.getItemType();
        Integer otherType = other.getItemType();
        int compareInt = currType.compareTo(otherType);

        if (compareInt == 0) {
            switch(currType) {
            case 1:
                //fallthrough
            case 2:
                return compareEndTime(curr, other);
            default:
                return compareName(curr, other);
            }
        }
        return compareInt;
    }

    private int compareEndTime(ReadOnlyTask curr, ReadOnlyTask other) {
        return curr.getDeadline().compareTo(other.getDeadline());
    }

    private int compareName(ReadOnlyTask curr, ReadOnlyTask other) {
        return curr.getName().toString().compareToIgnoreCase(other.getName().toString());
    }

}
