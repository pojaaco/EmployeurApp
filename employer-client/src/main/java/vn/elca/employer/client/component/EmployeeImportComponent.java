package vn.elca.employer.client.component;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.callback.ImportCallBack;
import vn.elca.employer.client.callback.SaveCallBack;
import vn.elca.employer.client.callback.SearchCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.client.workbench.EmployerWorkbench;

import java.io.File;

@View(id = EmployeeImportComponent.ID,
        name = EmployeeImportComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER)
public class EmployeeImportComponent implements FXComponent {
    public static final String ID = "EmployeeImportComponent";
    public static final String RESOURCE_ROOT = "EmployeePerspective.EmployeeImportComponent";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    private Node pane;

    private final File[] selectedFile = {null};

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        ((TableView<EmployeeView>) pane.lookup("#importTable")).getItems().clear();
        ((Label) pane.lookup("#chooserLabel")).setText("Choose File");
        selectedFile[0] = null;

        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            if (message.getSourceId().equals(EmployeePerspective.ID.concat(".").concat(ImportCallBack.ID))) {
                ObservableList<EmployeeView> results = (ObservableList<EmployeeView>) message.getMessageBody();
                ((TableView<EmployeeView>) pane.lookup("#importTable")).setItems(results);
            } else if (message.getSourceId().equals(EmployerWorkbench.ID.concat(".").concat(EmployeePerspective.ID))) {
                if (message.getTypedMessageBody(String.class).equals("save")) {
                    context.send(EmployeePerspective.ID.concat(".").concat(SaveCallBack.ID),
                            ((TableView<EmployeeView>) pane.lookup("#importTable")).getItems());
                }
            }
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
        label.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + ".label.title"));
        vBox.getChildren().add(label);
        vBox.getChildren().add(createImportChooser());
        vBox.getChildren().add(createImportTable());
        pane = vBox;
    }

    private HBox createImportChooser() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Label label = new Label();
        label.setId("chooserLabel");
        label.setStyle("-fx-border-color: black; -fx-max-width:200px;");
        label.setOnMouseClicked(event -> {
            Window stage = ((Node) event.getSource()).getScene().getWindow();
            selectedFile[0] = fileChooser.showOpenDialog(stage);
            if (selectedFile[0] != null) {
                label.setText(selectedFile[0].getAbsolutePath());
            }
        });

        Button button = new Button();
        button.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + ".button.import"));
        button.setOnAction(event -> {
            if (selectedFile[0] != null) {
                context.send(EmployeePerspective.ID.concat(".").concat(ImportCallBack.ID), selectedFile[0].getAbsolutePath());
            }
        });
        hBox.getChildren().addAll(label, button);

        return hBox;
    }

    private TableView<EmployeeView> createImportTable() {
        TableView<EmployeeView> importTable = new TableView<>();
        importTable.setId("importTable");
        importTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        importTable.getColumns().addAll(
                createTableColumn("label.numberAVS", "numberAVS"),
                createTableColumn("label.lastName", "lastName"),
                createTableColumn("label.firstName", "firstName"),
                createTableColumn("label.startingDate", "startingDate"),
                createTableColumn("label.endDate", "endDate"),
                createTableColumn("label.amountOfAssuranceAVS", "amountOfAssuranceAVS"),
                createTableColumn("label.amountOfAssuranceAC", "amountOfAssuranceAC"),
                createTableColumn("label.amountOfAssuranceAF", "amountOfAssuranceAF")
        );
        return importTable;
    }

    private TableColumn<EmployeeView, String> createTableColumn(String labelKey, String property) {
        TableColumn<EmployeeView, String> column = new TableColumn<>();
        column.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + "." + labelKey));
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }
}
