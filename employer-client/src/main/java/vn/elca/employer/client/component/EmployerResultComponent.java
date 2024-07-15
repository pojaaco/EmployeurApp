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
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.callback.GetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.converter.EnumStringConverter;
import vn.elca.employer.client.converter.EnumStringConverterFactory;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.fragment.EmployerDeleteFragment;
import vn.elca.employer.client.fragment.EmployerDetailsFragment;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.Fund;

@View(id = EmployerResultComponent.ID,
        name = EmployerResultComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER)
public class EmployerResultComponent implements FXComponent {
    public static final String ID = "EmployerResultComponent";

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
        String sourceId = message.getSourceId();
        if (sourceId.equals(EmployerPerspective.ID.concat(".").concat(GetCallBack.ID))) {
            ObservableList<EmployerView> results = (ObservableList<EmployerView>) message.getMessageBody();
            ((TableView<EmployerView>) pane.lookup("#employerTable")).setItems(results);
        } else if (sourceId.equals(EmployerPerspective.ID.concat(".").concat(DeleteCallBack.ID))) {
            EmployerView delete = message.getTypedMessageBody(EmployerView.class);
            ((TableView<EmployerView>) pane.lookup("#employerTable")).getItems().remove(delete);
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
        // TODO: can degrade performance
        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> tableResult.refresh()));
        return tableResult;
    }

    public TableColumn<EmployerView, String> createTableColumn(String property) {
        TableColumn<EmployerView, String> column = new TableColumn<>();
        column.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer." + property));
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    public TableColumn<EmployerView, Fund> createFundTableColumn(String property) {
        TableColumn<EmployerView, Fund> column = new TableColumn<>();
        column.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer." + property));
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setCellFactory(TextFieldTableCell.forTableColumn(EnumStringConverterFactory.getConverter(Fund.class, observableResourceFactory)));
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
