package vn.elca.employer.client.fragment;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.component.CreationHelper;
import vn.elca.employer.client.component.Validator;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.common.Fund;

import java.time.LocalDate;

@Fragment(id = EmployeeInputFragment.ID,
        viewLocation = "/fxml/fragment/EmployeeInputFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployeeInputFragment implements CustomFragment {
    public static final String ID = "EmployeeInputFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInputFragment.class);
    private static final String CORRECT_NUMBER_IDE_FORMAT = "(ADM|CHE)-\\d{3}\\.\\d{3}\\.\\d{3}";
    private static final String CORRECT_DATE_FORMAT = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d{2}";
    private static final String BUNDLE_NUMBER_IDE_FORMAT = "Format.numberIde";
    private static final String BUNDLE_EMPLOYER_PROPERTY = "Property.Employer";
    private static final String BUNDLE_EMPLOYER_ERROR = "Label.Error.Employer";
    private static final String CSS_RED_BORDER = "red-border";
    private static final String DISPLAY_UNDEFINED_NUMBER = "XXXXXX";
    private static final String PROPERTY_NUMBER = "number";
    private static final String PROPERTY_NAME = "name";
    private static final String PROPERTY_FUND = "fund";
    private static final String PROPERTY_NUMBER_IDE = "numberIde";
    private static final String PROPERTY_START_DATE = "startDate";
    private static final String PROPERTY_END_DATE = "endDate";

    @Autowired
    ObservableResourceFactory observableResourceFactory;

    @Autowired
    CreationHelper creationHelper;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label lblNumber;

    @FXML
    private Label fldNumber;

    @FXML
    private Label errNumber;

    @FXML
    private Label lblName;

    @FXML
    private TextField txfName;

    @FXML
    private Label errName;

    @FXML
    private Label lblFund;

    @FXML
    private ComboBox<Fund> cbxFund;

    @FXML
    private Label errFund;

    @FXML
    private Label lblNumberIde;

    @FXML
    private TextField txfNumberIde;

    @FXML
    private Label errNumberIde;

    @FXML
    private Label lblStartDate;

    @FXML
    private DatePicker dpkStartDate;

    @FXML
    private Label errStartDate;

    @FXML
    private Label lblEndDate;

    @FXML
    private DatePicker dpkEndDate;

    @FXML
    private Label errEndDate;

    private EmployerView managedView;

    public void init() {
        bindLanguage();
        setupInputFields();
        setupEventHandlers();
        LOGGER.debug("init");
    }

    public void reset() {
        resetError();
        resetInputFields();
    }

    private void resetError() {
        errName.setVisible(false);
        errNumberIde.setVisible(false);
        errStartDate.setVisible(false);
        errEndDate.setVisible(false);

        txfName.getStyleClass().remove(CSS_RED_BORDER);
        txfNumberIde.getStyleClass().remove(CSS_RED_BORDER);
        dpkStartDate.getStyleClass().remove(CSS_RED_BORDER);
        dpkEndDate.getStyleClass().remove(CSS_RED_BORDER);
    }

    private void resetInputFields() {
        fldNumber.setText(DISPLAY_UNDEFINED_NUMBER);
        cbxFund.getSelectionModel().selectFirst();
        txfName.setText(null);
        txfNumberIde.setText(null);
        dpkStartDate.setValue(null);
        dpkEndDate.setValue(null);
    }

    public void updateInputFields(EmployerView employerView) {
        managedView = employerView;
        if (employerView.getNumber() != null) {
            fldNumber.setText(employerView.getNumber());
        }
        if (employerView.getFund() != null) {
            cbxFund.getSelectionModel().select(employerView.getFund());
        }
        txfName.setText(employerView.getName());
        txfNumberIde.setText(employerView.getNumberIde());
        if (employerView.getStartDate() != null) {
            dpkStartDate.setValue(LocalDate.parse(employerView.getStartDate(), creationHelper.dateFormatter));
        }
        if (employerView.getEndDate() != null) {
            dpkEndDate.setValue(LocalDate.parse(employerView.getEndDate(), creationHelper.dateFormatter));
        }
    }

    public EmployerView extractInputFields() {
        EmployerView newEmployer = new EmployerView();
        newEmployer.setId(managedView.getId());
        newEmployer.setNumber(managedView.getNumber());
        newEmployer.setName(txfName.getText());
        newEmployer.setFund(cbxFund.getSelectionModel().getSelectedItem());
        newEmployer.setNumberIde(txfNumberIde.getText());
        if (dpkStartDate.getValue() != null) {
            newEmployer.setStartDate(creationHelper.dateFormatter.format(dpkStartDate.getValue()));
        }
        if (dpkEndDate.getValue() != null) {
            newEmployer.setEndDate(creationHelper.dateFormatter.format(dpkEndDate.getValue()));
        }
        newEmployer.setEmployees(managedView.getEmployees());
        return newEmployer;
    }

    public boolean validateInputFields() {
        boolean isValid;
        isValid = Validator.checkCondition(managedView.getName() != null && !managedView.getName().isEmpty(), errName, txfName);
        isValid &= Validator.checkCondition(managedView.getNumberIde() != null
                && managedView.getNumberIde().matches(CORRECT_NUMBER_IDE_FORMAT), errNumberIde, txfNumberIde);
        isValid &= Validator.checkCondition(managedView.getStartDate() != null
                && managedView.getStartDate().matches(CORRECT_DATE_FORMAT), errStartDate, dpkStartDate);
        isValid &= Validator.checkCondition(managedView.getEndDate() == null
                || managedView.getEndDate().matches(CORRECT_DATE_FORMAT), errEndDate, dpkEndDate);
        isValid &= Validator.checkCondition(managedView.getStartDate() == null
                || managedView.getEndDate() == null
                || LocalDate.parse(managedView.getEndDate(), creationHelper.dateFormatter).isAfter(LocalDate.parse(managedView.getStartDate(), creationHelper.dateFormatter)), errEndDate, dpkEndDate);
        return isValid;
    }

    private void bindLanguage() {
        lblNumber.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_PROPERTY + "." + PROPERTY_NUMBER));
        lblName.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_PROPERTY + "." + PROPERTY_NAME));
        lblFund.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_PROPERTY + "." + PROPERTY_FUND));
        lblNumberIde.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_PROPERTY + "." + PROPERTY_NUMBER_IDE));
        lblStartDate.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_PROPERTY + "." + PROPERTY_START_DATE));
        lblEndDate.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_PROPERTY + "." + PROPERTY_END_DATE));

        errNumber.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_ERROR + "." + PROPERTY_NUMBER));
        errName.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_ERROR + "." + PROPERTY_NAME));
        errFund.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_ERROR + "." + PROPERTY_FUND));
        errNumberIde.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_ERROR + "." + PROPERTY_NUMBER_IDE));
        errStartDate.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_ERROR + "." + PROPERTY_START_DATE));
        errEndDate.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EMPLOYER_ERROR + "." + PROPERTY_END_DATE));
    }

    private void setupInputFields() {
        creationHelper.createValidatedTextField(txfName, null, null);
        creationHelper.createFundComboBox(cbxFund);
        creationHelper.createValidatedTextField(txfNumberIde, Validator::isValidTypedNumberIde, BUNDLE_NUMBER_IDE_FORMAT);
        creationHelper.createDatePicker(dpkStartDate);
        creationHelper.createDatePicker(dpkEndDate);
    }

    private void setupEventHandlers() {
        txfName.textProperty().addListener((observable, oldValue, newValue) -> {
            errName.setVisible(false);
            txfName.getStyleClass().remove(CSS_RED_BORDER);
        });
        txfNumberIde.textProperty().addListener((observable, oldValue, newValue) -> {
            errNumberIde.setVisible(false);
            txfNumberIde.getStyleClass().remove(CSS_RED_BORDER);
        });
        dpkStartDate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            errStartDate.setVisible(false);
            dpkStartDate.getStyleClass().remove(CSS_RED_BORDER);
        });
        dpkEndDate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            errEndDate.setVisible(false);
            dpkEndDate.getStyleClass().remove(CSS_RED_BORDER);
        });
    }
}
