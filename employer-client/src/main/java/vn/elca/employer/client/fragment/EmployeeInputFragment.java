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
public class EmployeeInputFragment {
    private static final String NUMBER_IDE_FORMAT = "Format.numberIde";
    private static final String CORRECT_NUMBER_IDE_FORMAT = "(ADM|CHE)-\\d{3}\\.\\d{3}\\.\\d{3}";
    private static final String CORRECT_DATE_FORMAT = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d{2}";
    private static final String EMPLOYER_PROPERTY = "Property.Employer";
    private static final String EMPLOYER_ERROR = "Label.Error.Employer";

    public static final String ID = "EmployeeInputFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInputFragment.class);

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
        setupAppearance();
        setupEventHandlers();
        LOGGER.debug("init");
    }

    public void resetError() {
        errName.setVisible(false);
        errNumberIde.setVisible(false);
        errStartDate.setVisible(false);
        errEndDate.setVisible(false);
    }

    public void updateInputFields(EmployerView employerView) {
        managedView = employerView;
        if (employerView.getNumber() != null) {
            fldNumber.setText(employerView.getNumber());
        } else {
            fldNumber.setText("XXXXXX");
        }
        if (employerView.getFund() != null) {
            cbxFund.getSelectionModel().select(employerView.getFund());
        } else {
            cbxFund.getSelectionModel().selectFirst();
        }
        txfName.setText(employerView.getName());
        txfNumberIde.setText(employerView.getNumberIde());
        if (employerView.getStartDate() != null) {
            dpkStartDate.setValue(LocalDate.parse(employerView.getStartDate(), creationHelper.dateFormatter));
        } else {
            dpkStartDate.setValue(null);
        }
        if (employerView.getEndDate() != null) {
            dpkEndDate.setValue(LocalDate.parse(employerView.getEndDate(), creationHelper.dateFormatter));
        } else {
            dpkEndDate.setValue(null);
        }
    }

    public EmployerView extractEmployerView(boolean validated) {
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

        if (validated) {
            if (validateEmployerView(newEmployer)) {
                return newEmployer;
            } else {
                return null;
            }
        }
        return newEmployer;
    }

    private boolean validateEmployerView(EmployerView employerView) {
        boolean isValid;
        isValid = Validator.checkCondition(employerView.getName() != null && !employerView.getName().isEmpty(), errName, txfName);
        isValid &= Validator.checkCondition(employerView.getNumberIde() != null
                && employerView.getNumberIde().matches(CORRECT_NUMBER_IDE_FORMAT), errNumberIde, txfNumberIde);
        isValid &= Validator.checkCondition(employerView.getStartDate() != null
                && employerView.getStartDate().matches(CORRECT_DATE_FORMAT), errStartDate, dpkStartDate);
        isValid &= Validator.checkCondition(employerView.getEndDate() == null
                || employerView.getEndDate().matches(CORRECT_DATE_FORMAT), errEndDate, dpkEndDate);
        isValid &= Validator.checkCondition(employerView.getStartDate() == null
                || employerView.getEndDate() == null
                || LocalDate.parse(employerView.getEndDate(), creationHelper.dateFormatter).isAfter(LocalDate.parse(employerView.getStartDate(), creationHelper.dateFormatter)), errEndDate, dpkEndDate);
        return isValid;
    }

    private void bindLanguage() {
        // TODO Add Property Constants
        lblNumber.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "number"));
        lblName.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "name"));
        lblFund.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "fund"));
        lblNumberIde.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "numberIde"));
        lblStartDate.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "startDate"));
        lblEndDate.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_PROPERTY + "." + "endDate"));

        errNumber.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_ERROR + "." + "number"));
        errName.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_ERROR + "." + "name"));
        errFund.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_ERROR + "." + "fund"));
        errNumberIde.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_ERROR + "." + "numberIde"));
        errStartDate.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_ERROR + "." + "startDate"));
        errEndDate.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYER_ERROR + "." + "endDate"));
    }

    private void setupInputFields() {
        creationHelper.createFundComboBox(cbxFund);
        creationHelper.createValidatedTextField(txfNumberIde, Validator::isValidTypedNumberIde, NUMBER_IDE_FORMAT);
        creationHelper.createDatePicker(dpkStartDate);
        creationHelper.createDatePicker(dpkEndDate);
    }

    private void setupAppearance() {
        gridPane.setVgap(5);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(5));
    }

    private void setupEventHandlers() {
        txfName.textProperty().addListener((observable, oldValue, newValue) -> errName.setVisible(false));
        txfNumberIde.textProperty().addListener((observable, oldValue, newValue) -> errNumberIde.setVisible(false));
        dpkStartDate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> errStartDate.setVisible(false));
        dpkEndDate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> errEndDate.setVisible(false));
    }
}
