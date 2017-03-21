package seedu.doit.model.item;

import java.util.Objects;

import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<ReadOnlyTask> {

    private Name name;
    private Priority priority;
    private StartTime startTime;
    private EndTime endTime;
    private Description description;
    private boolean isDone;
    private UniqueTagList tags;

    // ================ Constructor methods ==============================

    /**
     * Event Constructor where every field must be present and not null.
     */
    public Task(Name name, Priority priority, StartTime startTime, EndTime endTime, Description description,
            UniqueTagList tags, boolean isDone) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime);
        this.name = name;
        this.priority = priority;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.isDone = isDone;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Event Constructor where every field must be present except isDone.
     */
    public Task(Name name, Priority priority, StartTime startTime, EndTime endTime, Description description,
            UniqueTagList tags) {
        this(name, priority, startTime, endTime, description, tags, false);
    }

    /**
     * Task Constructor every field must be present except for startTime and
     * isDone.
     */
    public Task(Name name, Priority priority, EndTime endTime, Description description, UniqueTagList tags,
            boolean isDone) {
        this(name, priority, new StartTime(), endTime, description, tags, isDone);
    }

    /**
     * Task Constructor every field must be present except for startTime.
     */
    public Task(Name name, Priority priority, EndTime endTime, Description description, UniqueTagList tags) {
        this(name, priority, new StartTime(), endTime, description, tags);
        assert !CollectionUtil.isAnyNull(name, endTime);
    }

    /**
     * FloatingTask Constructor every field must be present except for
     * startTime, endTime and isDone.
     */
    public Task(Name name, Priority priority, Description description, UniqueTagList tags, boolean isDone) {
        this(name, priority, new StartTime(), new EndTime(), description, tags, isDone);
    }

    /**
     * FloatingTask Constructor every field must be present except for startTime
     * and endTime.
     */
    public Task(Name name, Priority priority, Description description, UniqueTagList tags) {
        this(name, priority, new StartTime(), new EndTime(), description, tags);
        assert !CollectionUtil.isAnyNull(name);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority(), source.getDescription(), source.getTags());
        this.startTime = source.getStartTime();
        this.endTime = source.getEndTime();
        this.isDone = source.getIsDone();
    }

    // ================ Getter and Setter methods ==============================

    @Override
    public Name getName() {
        return this.name;
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public boolean getIsDone() {
        return this.isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public StartTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(StartTime startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    @Override
    public EndTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(EndTime endTime) {
        assert endTime != null;
        this.endTime = endTime;
    }

    @Override
    public Description getDescription() {
        return this.description;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public boolean hasStartTime() {
        if (this.startTime == null) {
            return false;
        } else if (this.startTime.value == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasEndTime() {
        if (this.endTime == null) {
            return false;
        } else if (this.endTime.value == null) {
            return false;
        }
        return true;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(this.tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        this.tags.setTags(replacement);
    }

    /**
     * Indicates if this item is an event
     */
    @Override
    public boolean isEvent() {
        return (hasStartTime() && hasEndTime());
    }

    /**
     * Indicates if this item is a floatingTask
     */
    @Override
    public boolean isFloatingTask() {
        return (!hasStartTime() && !hasEndTime());
    }

    /**
     * Indicates if this item is a task
     */
    @Override
    public boolean isTask() {
        return (!hasStartTime() && hasEndTime());
    }

    // ================ Misc methods ==============================

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriority(replacement.getPriority());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setIsDone(replacement.getIsDone());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(this.name, this.priority, this.endTime, this.description, this.tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }


    // ================ Sort methods ==============================

    @Override
    public boolean equals(Object other) {
        return (other == this // short circuit if same object
                    ) || ((other instanceof ReadOnlyTask // instanceof handles nulls
                    ) && this.isSameStateAs((ReadOnlyTask) other));
    }

    /**
     * Compares the current task with another Task other. The current task is
     * considered to be less than the other task if 1) This item has a earlier
     * start time associated 2) both items are not events but this item has a
     * later end time 3) but this task has a lexicographically smaller name
     * (useful when sorting tasks in testing)
     */
    @Override
    public int compareTo(ReadOnlyTask other) {
        return compareDone(other);
    }

    private int compareName(ReadOnlyTask other) {
        return this.getName().toString().compareToIgnoreCase(other.getName().toString());
    }

    /**
     * Compares the current task with another Task other. The current item is
     * considered to be less than the other item if it is done and the other is
     * not done Items that have equal done status will be compared using
     * compareItems
     */
    private int compareDone(ReadOnlyTask other) {
        if ((this.getIsDone() == true) && (other.getIsDone() == true)) {
            compareItems(other);
        } else if ((this.getIsDone() == true) && (other.getIsDone() == false)) {
            return 1;
        } else if ((this.getIsDone() == false) && (other.getIsDone() == true)) {
            return -1;
        }
        return compareItems(other);
    }

    /**
     * Compares the current item with another item other. returns -1 if other
     * item is greater than current item return 0 is both items are equal return
     * 1 if other item is smaller than current item The ranking are as follows
     * from highest: 1) tasks 2) events 3) floating tasks If both have same
     * rankings, then compare names
     */
    public int compareItems(ReadOnlyTask other) {

        if (this.isTask() && other.isTask()) {
            return compareName(other);
        } else if (this.isTask() && other.isEvent()) {
            return -1;
        } else if (this.isTask() && other.isFloatingTask()) {
            return -1;
        }

        if (this.isEvent() && other.isEvent()) {
            return compareName(other);
        } else if (this.isEvent() && other.isTask()) {
            return 1;
        } else if (this.isEvent() && other.isFloatingTask()) {
            return -1;
        }

        if (this.isFloatingTask() && other.isFloatingTask()) {
            return compareName(other);
        } else if (this.isFloatingTask() && other.isTask()) {
            return 1;
        } else if (this.isFloatingTask() && other.isEvent()) {
            return 1;
        }

        // Should never reach this
        return 0;
    }
}
