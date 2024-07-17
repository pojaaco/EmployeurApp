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
    private static final String EMPLOYER_PROPERTY = "Property.Employer";
    private static final String BUTTON_SEARCH = "Button.search";
    private static final String BUTTON_RESET = "Button.reset";
    private static final String NUMBER_FORMAT = "Format.number";
    private static final String NUMBER_IDE_FORMAT = "Format.numberIde";

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
        lblFund.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "fund"));
        lblNumber.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "number"));
        lblStartDate.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "startDate"));
        lblName.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "name"));
        lblNumberIde.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "numberIde"));
        lblEndDate.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "endDate"));

        btnSearch.textProperty().bind(observableResourceFactory.getStringBinding(BUTTON_SEARCH));
        btnReset.textProperty().bind(observableResourceFactory.getStringBinding(BUTTON_RESET));
    }

    private void setupInputFields() {
        creationHelper.createFundComboBox(cbxFund);
        creationHelper.createValidatedTextField(txfNumber, Validator::isValidTypedNumber, NUMBER_FORMAT);
        creationHelper.createDatePicker(dpkStartDate);
        creationHelper.createValidatedTextField(txfName, null, null);
        creationHelper.createValidatedTextField(txfNumberIde, Validator::isValidTypedNumberIde, NUMBER_IDE_FORMAT);
        creationHelper.createDatePicker(dpkEndDate);
    }

    private void setupAppearance() {
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
