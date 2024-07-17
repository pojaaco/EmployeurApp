package vn.elca.employer.client.fragment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.ImportCallBack;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.perspective.EmployeePerspective;

import java.io.File;

@Fragment(id = EmployeeImportFragment.ID,
        viewLocation = "/fxml/fragment/EmployeeImportFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployeeImportFragment {
    public static final String ID = "EmployeeImportFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeImportFragment.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblChooser;

    @FXML
    private Button btnImport;

    private final File[] selectedFile = {null};

    public void init() {
        bindLanguage();
        setupEventHandlers();
    }

    public void reset() {
        lblChooser.textProperty().bind(observableResourceFactory.getStringBinding("Label.Importer.chooseFile"));
        selectedFile[0] = null;
    }

    private void bindLanguage() {
        lblTitle.textProperty().bind(observableResourceFactory.getStringBinding("Label.Importer.title"));
        lblChooser.textProperty().bind(observableResourceFactory.getStringBinding("Label.Importer.chooseFile"));
        btnImport.textProperty().bind(observableResourceFactory.getStringBinding("Button.import"));
    }

    private void setupEventHandlers() {
        lblChooser.setOnMouseClicked(this::handleChooserLabel);
        btnImport.setOnAction(this::handleImportButton);
    }

    private void handleChooserLabel(MouseEvent mouseEvent) {
        Window stage = ((Node) mouseEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        selectedFile[0] = fileChooser.showOpenDialog(stage);

        if (selectedFile[0] != null) {
            lblChooser.textProperty().unbind();
            lblChooser.setText(selectedFile[0].getAbsolutePath());
        }
    }

    private void handleImportButton(ActionEvent actionEvent) {
        if (selectedFile[0] != null) {
            context.send(EmployeePerspective.ID.concat(".").concat(ImportCallBack.ID), selectedFile[0].getAbsolutePath());
        }
    }
}
