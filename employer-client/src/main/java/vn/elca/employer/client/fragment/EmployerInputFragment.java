package vn.elca.employer.client.fragment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class EmployerInputFragment implements CustomFragment {
    public static final String ID = "EmployerInputFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerInputFragment.class);
    private static final String BUNDLE_PROPERTY_EMPLOYER = "Property.Employer";
    private static final String BUNDLE_BUTTON_SEARCH = "Button.search";
    private static final String BUNDLE_BUTTON_RESET = "Button.reset";
    private static final String BUNDLE_FORMAT_NUMBER = "Format.number";
    private static final String BUNDLE_FORMAT_NUMBER_IDE = "Format.numberIde";
    private static final String PROPERTY_NUMBER = "number";
    private static final String PROPERTY_NAME = "name";
    private static final String PROPERTY_FUND = "fund";
    private static final String PROPERTY_NUMBER_IDE = "numberIde";
    private static final String PROPERTY_START_DATE = "startDate";
    private static final String PROPERTY_END_DATE = "endDate";

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
        setupEventHandlers();
        LOGGER.debug("init");
    }

    public void reset() {
    }

    private void bindLanguage() {
        lblFund.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_FUND));
        lblNumber.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_NUMBER));
        lblStartDate.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_START_DATE));
        lblName.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_NAME));
        lblNumberIde.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_NUMBER_IDE));
        lblEndDate.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_END_DATE));

        btnSearch.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_BUTTON_SEARCH));
        btnReset.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_BUTTON_RESET));
    }

    private void setupInputFields() {
        creationHelper.createFundComboBox(cbxFund);
        creationHelper.createValidatedTextField(txfNumber, Validator::isValidTypedNumber, BUNDLE_FORMAT_NUMBER);
        creationHelper.createDatePicker(dpkStartDate);
        creationHelper.createValidatedTextField(txfName, null, null);
        creationHelper.createValidatedTextField(txfNumberIde, Validator::isValidTypedNumberIde, BUNDLE_FORMAT_NUMBER_IDE);
        creationHelper.createDatePicker(dpkEndDate);
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
