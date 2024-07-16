package vn.elca.employer.client.component;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.ImportCallBack;
import vn.elca.employer.client.callback.SetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.perspective.EmployeePerspective;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@View(id = EmployeeImportComponent.ID,
        name = EmployeeImportComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER)
public class EmployeeImportComponent implements FXComponent {
    public static final String ID = "EmployeeImportComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeImportComponent.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    private Node pane;

    private final File[] selectedFile = {null};

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        String sourceId = message.getSourceId();
        if (sourceId.endsWith(EmployeePerspective.ID)) {
            if (message.getTypedMessageBody(String.class).equals("reset")) {
                ((TableView<EmployeeView>) pane.lookup("#employeeTable")).getItems().clear();
                ((Label) pane.lookup("#importerLabel")).textProperty().bind(observableResourceFactory.getStringBinding("Label.Importer.chooseFile"));
                selectedFile[0] = null;
            }
        } else if (sourceId.endsWith(EmployeeInputComponent.ID)) {
            if (message.getTypedMessageBody(String.class).equals("save")) {
                context.send(EmployeePerspective.ID.concat(".").concat(SetCallBack.ID),
                        ((TableView<EmployeeView>) pane.lookup("#employeeTable")).getItems());
            }
        } else if (sourceId.endsWith(ImportCallBack.ID)) {
            boolean existInvalidEmployee = false;
            List<EmployeeView> results = ((ObservableList<EmployeeView>) message.getMessageBody());
            List<EmployeeView> checkedResults = new ArrayList<>();
            for (EmployeeView employeeView : results) {
                if (Validator.validateEmployeeView(null, employeeView)) {
                    checkedResults.add(employeeView);
                } else {
                    existInvalidEmployee = true;
                }
            }
            if (existInvalidEmployee) {
                showWarningInvalidDateDialog();
            }
            ((TableView<EmployeeView>) pane.lookup("#employeeTable")).getItems().setAll(checkedResults);
        }
        return pane;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        Label label = new Label();
        label.textProperty().bind(observableResourceFactory.getStringBinding("Label.Importer.title"));
        vBox.getChildren().addAll(label, createImporter(), createEmployeeTable());

        pane = vBox;
    }

    private HBox createImporter() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Label label = new Label();
        label.setId("importerLabel");
        label.setOnMouseClicked(event -> {
            Window stage = ((Node) event.getSource()).getScene().getWindow();
            selectedFile[0] = fileChooser.showOpenDialog(stage);
            if (selectedFile[0] != null) {
                label.textProperty().unbind();
                label.setText(selectedFile[0].getAbsolutePath());
            }
        });

        Button button = new Button();
        button.textProperty().bind(observableResourceFactory.getStringBinding("Button.import"));
        button.setOnAction(event -> {
            if (selectedFile[0] != null) {
                context.send(EmployeePerspective.ID.concat(".").concat(ImportCallBack.ID), selectedFile[0].getAbsolutePath());
            }
        });
        hBox.getChildren().addAll(label, button);

        return hBox;
    }

    private TableView<EmployeeView> createEmployeeTable() {
        TableView<EmployeeView> importTable = new TableView<>();
        importTable.setId("employeeTable");
        importTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        importTable.getColumns().addAll(
                createTableColumn("numberAvs"),
                createTableColumn("lastName"),
                createTableColumn("firstName"),
                createTableColumn("startDate"),
                createTableColumn("endDate"),
                createTableColumn("avsAiApg"),
                createTableColumn("ac"),
                createTableColumn("af")
        );
        return importTable;
    }

    private TableColumn<EmployeeView, String> createTableColumn(String property) {
        TableColumn<EmployeeView, String> column = new TableColumn<>();
        column.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee." + property));
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private void showWarningInvalidDateDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Employee Import");
        alert.setHeaderText("There are some invalid employees. Only valid data is imported. Please check again.");
        alert.showAndWait();
    }
}
