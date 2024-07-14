package vn.elca.employer.client.component;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.callback.SearchCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.fragment.EmployerDeleteFragment;
import vn.elca.employer.client.fragment.EmployerDetailsFragment;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;

import javax.xml.soap.Detail;
import java.util.Optional;

@View(id = EmployerResultComponent.ID,
        name = EmployerResultComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER)
public class EmployerResultComponent implements FXComponent {
    public static final String ID = "EmployerResultComponent";
    public static final String RESOURCE_ROOT = "EmployerPerspective.EmployerResultComponent";

    @Autowired
    ObservableResourceFactory observableResourceFactory;

    @Resource
    Context context;

    private Node pane;

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            if (message.getSourceId().equals(EmployerPerspective.ID.concat(".").concat(SearchCallBack.ID))) {
                ObservableList<EmployerView> results = (ObservableList<EmployerView>) message.getMessageBody();
                ((TableView<EmployerView>) pane.lookup("#tableResult")).setItems(results);
            } else if (message.getSourceId().equals(EmployerPerspective.ID.concat(".").concat(DeleteCallBack.ID))) {
                ((TableView<EmployerView>) pane.lookup("#tableResult")).getItems().remove(message.getTypedMessageBody(EmployerView.class));
            } else if (message.getSourceId().equals(EmployeePerspective.ID.concat(".").concat(EmployeeInfoComponent.ID))) {
                if (message.getTypedMessageBody(String.class) == "refresh") {
                    ((TableView<EmployerView>) pane.lookup("#tableResult")).refresh();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Employer Saved!");
                    alert.showAndWait();
                }
            }
        }
        return pane;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        VBox vBox = new VBox();
        vBox.getChildren().add(createResultTable());
        pane = vBox;
    }

    private TableView<EmployerView> createResultTable() {
        TableView<EmployerView> tableResult = new TableView<>();
        tableResult.setId("tableResult");
        tableResult.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableResult.getColumns().addAll(
                createTableColumn("label.caisse", "caisse"),
                createTableColumn("label.number", "number"),
                createTableColumn("label.numberIDE", "numberIDE"),
                createTableColumn("label.name", "name"),
                createTableColumn("label.startingDate", "startingDate"),
                createTableColumn("label.endDate", "endDate"),
                createActionTableColumn()
        );
        return tableResult;
    }

    private TableColumn<EmployerView, String> createTableColumn(String labelKey, String property) {
        TableColumn<EmployerView, String> column = new TableColumn<>();
        column.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + "." + labelKey));
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private TableColumn<EmployerView, EmployerView>  createActionTableColumn() {
        TableColumn<EmployerView, EmployerView> column = new TableColumn<>();

        column.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue()));

        column.setCellFactory(col -> {
            TableCell<EmployerView, EmployerView> cell = new TableCell<>();

            cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    HBox hbox = new HBox();
                    HBox.setHgrow(hbox, Priority.ALWAYS);
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    final ManagedFragmentHandler<EmployerDetailsFragment> details = context.getManagedFragmentHandler(EmployerDetailsFragment.class);
                    final EmployerDetailsFragment controllerDetails = details.getController();
                    controllerDetails.init(newValue);

                    final ManagedFragmentHandler<EmployerDeleteFragment> delete = context.getManagedFragmentHandler(EmployerDeleteFragment.class);
                    final EmployerDeleteFragment controllerDelete = delete.getController();
                    controllerDelete.init(newValue);

                    hbox.getChildren().addAll(details.getFragmentNode(), spacer, delete.getFragmentNode());

                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(hbox));
                }
            });

            return cell;
        });

        return column;
    }
}
