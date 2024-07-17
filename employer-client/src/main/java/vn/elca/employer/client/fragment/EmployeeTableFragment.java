package vn.elca.employer.client.fragment;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.component.CreationHelper;
import vn.elca.employer.client.component.Validator;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Fragment(id = EmployeeTableFragment.ID,
        viewLocation = "/fxml/fragment/EmployeeTableFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployeeTableFragment {
    private static final String NUMBER_AVS_FORMAT = "756\\.\\d{4}\\.\\d{4}\\.\\d{2}";
    private static final String DATE_FORMAT = "\\d{0,2}\\.\\d{0,2}\\.\\d{0,4}";
    private static final String EMPTY_TABLE_PROMPT = "Prompt.Table.empty";
    private static final String EMPLOYEE_PROPERTY = "Property.Employee";
    private static final double STRETCH_TABLE_COEFFICIENT = 1.0;

    public static final String ID = "EmployeeTableFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeTableFragment.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private CreationHelper creationHelper;

    @FXML
    private TableView<EmployeeView> employeeTable;

    @FXML
    private TableColumn<EmployeeView, String> column1;

    @FXML
    private TableColumn<EmployeeView, String> column2;

    @FXML
    private TableColumn<EmployeeView, String> column3;

    @FXML
    private TableColumn<EmployeeView, String> column4;

    @FXML
    private TableColumn<EmployeeView, String> column5;

    @FXML
    private TableColumn<EmployeeView, String> column6;

    @FXML
    private TableColumn<EmployeeView, String> column7;

    @FXML
    private TableColumn<EmployeeView, String> column8;

    @FXML
    private Pagination pagination;

    private final ObservableList<EmployeeView> data = FXCollections.observableArrayList(new ArrayList<>());

    public void init() {
        bindLanguage();
        setupTableFormat();
        setupColumnFormat();
        setupPagination();
        LOGGER.debug("init");
    }

    public void updateData(List<EmployeeView> newData) {
        data.setAll(newData);
    }

    public void resetData() {
        data.clear();
    }

    public ObservableList<EmployeeView> getData() {
        return data;
    }

    public boolean validateEmployeeView(EmployeeView employeeView) {
        boolean isValid;
        isValid = Validator.checkCondition(employeeView.getNumberAvs() != null
                && employeeView.getNumberAvs().matches(NUMBER_AVS_FORMAT), null, null);
        isValid &= Validator.checkCondition(isValid && employeeView.getLastName() != null, null, null);
        isValid &= Validator.checkCondition(isValid && employeeView.getFirstName() != null, null, null);
        isValid &= Validator.checkCondition(isValid && employeeView.getStartDate() != null
                && employeeView.getStartDate().matches(DATE_FORMAT), null, null);
        isValid &= Validator.checkCondition(isValid && employeeView.getEndDate() != null
                && employeeView.getEndDate().matches(DATE_FORMAT), null, null);
        isValid &= Validator.checkCondition(isValid && LocalDate.parse(employeeView.getEndDate(), creationHelper.dateFormatter)
                    .isAfter(LocalDate.parse(employeeView.getStartDate(), creationHelper.dateFormatter)), null, null);
        return isValid;
    }

    private void bindLanguage() {
        // TODO Add Property Constants
        column1.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYEE_PROPERTY + "." + "numberAvs"));
        column2.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYEE_PROPERTY + "." + "lastName"));
        column3.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYEE_PROPERTY + "." + "firstName"));
        column4.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYEE_PROPERTY + "." + "startDate"));
        column5.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYEE_PROPERTY + "." + "endDate"));
        column6.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYEE_PROPERTY + "." + "avsAiApg"));
        column7.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYEE_PROPERTY + "." + "ac"));
        column8.textProperty().bind(observableResourceFactory.getStringBinding(EMPLOYEE_PROPERTY + "." + "af"));
    }

    private void setupTableFormat() {
        Label placeholderLabel = new Label();
        placeholderLabel.textProperty().bind(observableResourceFactory.getStringBinding(EMPTY_TABLE_PROMPT));
        employeeTable.setPlaceholder(placeholderLabel);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupColumnFormat() {
        column1.setCellValueFactory(new PropertyValueFactory<>("numberAvs"));
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column4.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        column5.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        column6.setCellValueFactory(new PropertyValueFactory<>("avsAiApg"));
        column7.setCellValueFactory(new PropertyValueFactory<>("ac"));
        column8.setCellValueFactory(new PropertyValueFactory<>("af"));
    }

    private void setupPagination() {
        pagination.setPageCount(1);
        data.addListener((ListChangeListener<? super EmployeeView>) c -> {
            if (data.isEmpty()) {
                pagination.setPageCount(1);
            } else {
                pagination.setPageCount((int) Math.ceil(data.size() / (double) EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE)); // Math.ceil
            }
            pagination.setCurrentPageIndex(0);
            pagination.setPageFactory(pageIndex -> {
                int fromIndex = pageIndex * EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE;
                int toIndex = Math.min(fromIndex + EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE, data.size());
                employeeTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
//                employeeTable.setPrefHeight((toIndex - fromIndex + STRETCH_TABLE_COEFFICIENT) * employeeTable.getFixedCellSize());
                return new Pane(); // refresh pagination
            });
        });
    }
}
