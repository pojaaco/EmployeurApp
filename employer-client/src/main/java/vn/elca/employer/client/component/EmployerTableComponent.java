package vn.elca.employer.client.component;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.callback.GetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.converter.EnumStringConverterFactory;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.fragment.EmployerDeleteFragment;
import vn.elca.employer.client.fragment.EmployerDetailsFragment;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.common.Fund;

@View(id = EmployerTableComponent.ID,
        name = EmployerTableComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER)
public class EmployerTableComponent implements FXComponent {
    public static final String ID = "EmployerTableComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerTableComponent.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    private Node pane;

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        String sourceId = message.getSourceId();
        if (sourceId.endsWith(GetCallBack.ID)) {
            ObservableList<EmployerView> results = (ObservableList<EmployerView>) message.getMessageBody();
            ((TableView<EmployerView>) pane.lookup("#employerTable")).setItems(results);
        } else if (sourceId.endsWith(DeleteCallBack.ID) || sourceId.endsWith(EmployerTableComponent.ID)) {
            if (message.isMessageBodyTypeOf(EmployerView.class)) {
                EmployerView delete = message.getTypedMessageBody(EmployerView.class);
                ((TableView<EmployerView>) pane.lookup("#employerTable")).getItems().remove(delete);
            }
        }
        return pane;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        VBox vBox = new VBox();
        vBox.getChildren().add(createEmployerTable());
        pane = vBox;
    }

    private TableView<EmployerView> createEmployerTable() {
        TableView<EmployerView> tableResult = new TableView<>();
        tableResult.setId("employerTable");
        tableResult.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableResult.getColumns().addAll(
                createFundTableColumn("fund"),
                createTableColumn("number"),
                createTableColumn("numberIde"),
                createTableColumn("name"),
                createTableColumn("startDate"),
                createTableColumn("endDate"),
                createActionTableColumn()
        );

        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> tableResult.refresh()));
        return tableResult;
    }

    private TableColumn<EmployerView, Fund> createFundTableColumn(String property) {
        TableColumn<EmployerView, Fund> column = new TableColumn<>();
        column.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer." + property));
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setCellFactory(TextFieldTableCell.forTableColumn(EnumStringConverterFactory.getConverter(Fund.class, observableResourceFactory)));
        return column;
    }

    private TableColumn<EmployerView, String> createTableColumn(String property) {
        TableColumn<EmployerView, String> column = new TableColumn<>();
        column.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer." + property));
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private TableColumn<EmployerView, EmployerView> createActionTableColumn() {
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
