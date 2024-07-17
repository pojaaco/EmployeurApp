package vn.elca.employer.client.fragment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.GetCallBack;
import vn.elca.employer.client.component.CreationHelper;
import vn.elca.employer.client.component.Validator;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.Fund;

@Fragment(id = EmployerInputFragment.ID,
        viewLocation = "/fxml/fragment/EmployerInputFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployerInputFragment {
    public static final String ID = "EmployerInputFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerInputFragment.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private CreationHelper creationHelper;

    @Resource
    private Context context;

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label lblFund;

    @FXML
    private Label lblNumber;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNumberIde;

    @FXML
    private Label lblEndDate;

    @FXML
    private ComboBox<Fund> cbxFund;

    @FXML
    private TextField txfNumber;

    @FXML
    private DatePicker dpkStartDate;

    @FXML
    private TextField txfName;

    @FXML
    private TextField txfNumberIde;

    @FXML
    private DatePicker dpkEndDate;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnReset;

    public void init() {
        bindLanguage();
        setupInputFields();
        setupAppearance();
        setupEventHandlers();
        LOGGER.debug("init");
    }

    private void bindLanguage() {
        lblFund.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.fund"));
        lblNumber.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.number"));
        lblStartDate.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.startDate"));
        lblName.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.name"));
        lblNumberIde.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.numberIde"));
        lblEndDate.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.endDate"));

        btnSearch.textProperty().bind(observableResourceFactory.getStringBinding("Button.search"));
        btnReset.textProperty().bind(observableResourceFactory.getStringBinding("Button.reset"));
    }

    private void setupInputFields() {
        creationHelper.createFundComboBox(cbxFund);
        creationHelper.createValidatedTextField(txfNumber, Validator::isValidTypedNumber, "Format.number");
        creationHelper.createDatePicker(dpkStartDate);
        creationHelper.createValidatedTextField(txfNumberIde, Validator::isValidTypedNumberIde, "Format.numberIde");
        creationHelper.createDatePicker(dpkEndDate);
    }

    private void setupAppearance() {
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(5));
    }

    private void setupEventHandlers() {
        btnSearch.setOnAction(this::handleSearchButton);
        btnReset.setOnAction(this::handleResetButton);
    }

    private void handleSearchButton(ActionEvent event) {
        EmployerView employerView = new EmployerView();

        if (!cbxFund.getSelectionModel().isEmpty()) {
            employerView.setFund(cbxFund.getValue());
        }

        if (!txfNumber.getText().isEmpty()) {
            employerView.setNumber(txfNumber.getText());
        }

        if (!txfName.getText().isEmpty()) {
            employerView.setName(txfName.getText());
        }

        if (!txfNumberIde.getText().isEmpty()) {
            employerView.setNumberIde(txfNumberIde.getText());
        }

        if (dpkStartDate.getValue() != null) {
            employerView.setStartDate(creationHelper.dateFormatter.format(dpkStartDate.getValue()));
        }

        if (dpkEndDate.getValue() != null) {
            employerView.setEndDate(creationHelper.dateFormatter.format(dpkEndDate.getValue()));
        }

        context.send(EmployerPerspective.ID.concat(".").concat(GetCallBack.ID), employerView);
    }

    private void handleResetButton(ActionEvent event) {
        cbxFund.getSelectionModel().selectFirst();
        txfNumber.clear();
        txfName.clear();
        txfNumberIde.clear();
        dpkStartDate.setValue(null);
        dpkEndDate.setValue(null);
    }
}
