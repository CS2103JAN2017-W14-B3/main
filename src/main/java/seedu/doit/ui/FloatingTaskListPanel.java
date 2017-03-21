package seedu.doit.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.doit.commons.util.FxViewUtil;
import seedu.doit.model.item.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class FloatingTaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FloatingTaskListPanel.class);

    private static ObservableList<ReadOnlyTask> mainTaskList;
    @FXML
    private ListView<ReadOnlyTask> taskListView;


    public FloatingTaskListPanel(AnchorPane placeholder, ObservableList<ReadOnlyTask> floatingTaskList) {
        super(FXML);
        setConnections(floatingTaskList);
        addToPlaceholder(placeholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> floatingTaskList) {
        mainTaskList = floatingTaskList;
        this.taskListView.setItems(floatingTaskList.filtered(task -> !task.hasStartTime()
                                   && !task.hasEndTime() && !task.getIsDone()));
        this.taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        this.taskListView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                    raise(new TaskPanelSelectionChangedEvent(newValue));
                }
            });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            this.taskListView.scrollTo(index);
            this.taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask floatingTask, boolean empty) {
            super.updateItem(floatingTask, empty);

            if (empty || floatingTask == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(floatingTask, mainTaskList.indexOf(floatingTask) + 1).getRoot());
            }
        }
    }

}
