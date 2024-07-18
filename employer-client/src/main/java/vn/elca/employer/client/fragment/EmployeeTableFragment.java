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
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Fragment(id = EmployeeTableFragment.ID,
        viewLocation = "/fxml/fragment/EmployeeTableFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployeeTableFragment {
    public static final String ID = "EmployeeTableFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeTableFragment.class);
    private static final String NUMBER_AVS_FORMAT = "756\\.\\d{4}\\.\\d{4}\\.\\d{2}";
    private static final String DATE_FORMAT = "\\d{0,2}\\.\\d{0,2}\\.\\d{0,4}";
    private static final String BUNDLE_PROMPT_EMPTY_TABLE = "Prompt.Table.empty";
    private static final String BUNDLE_PROPERTY_EMPLOYEE = "Property.Employee";
    private static final String PROPERTY_NUMBER_AVS = "numberAvs";
    private static final String PROPERTY_LAST_NAME = "lastName";
    private static final String PROPERTY_FIRST_NAME = "firstName";
    private static final String PROPERTY_START_DATE = "startDate";
    private static final String PROPERTY_END_DATE = "endDate";
    private static final String PROPERTY_AVS_AI_APG = "avsAiApg";
    private static final String PROPERTY_AC = "ac";
    private static final String PROPERTY_AF = "af";
    private static final double STRETCH_TABLE_COEFFICIENT = 1.0;
    private static final int PAGINATION_ROW_PER_PAGE = 5;

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

    public void reset() {
        resetData();
    }

    private void resetData() {
        data.clear();
    }

    public void updateData(List<EmployeeView> newData) {
        data.setAll(newData);
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
        column1.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYEE + "." + PROPERTY_NUMBER_AVS));
        column2.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYEE + "." + PROPERTY_LAST_NAME));
        column3.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYEE + "." + PROPERTY_FIRST_NAME));
        column4.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYEE + "." + PROPERTY_START_DATE));
        column5.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYEE + "." + PROPERTY_END_DATE));
        column6.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYEE + "." + PROPERTY_AVS_AI_APG));
        column7.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYEE + "." + PROPERTY_AC));
        column8.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYEE + "." + PROPERTY_AF));
    }

    private void setupTableFormat() {
        Label placeholderLabel = new Label();
        placeholderLabel.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROMPT_EMPTY_TABLE));
        employeeTable.setPlaceholder(placeholderLabel);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupColumnFormat() {
        column1.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_NUMBER_AVS));
        column2.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_LAST_NAME));
        column3.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_FIRST_NAME));
        column4.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_START_DATE));
        column5.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_END_DATE));
        column6.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_AVS_AI_APG));
        column7.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_AC));
        column8.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_AF));
    }

    private void setupPagination() {
        // TODO Improve Pagination
        pagination.setPageCount(1);
        data.addListener((ListChangeListener<? super EmployeeView>) c -> {
            if (data.isEmpty()) {
                pagination.setPageCount(1);
            } else {
                pagination.setPageCount((int) Math.ceil(data.size() / (double) PAGINATION_ROW_PER_PAGE)); // Math.ceil
            }
            pagination.setCurrentPageIndex(0);
            pagination.setPageFactory(pageIndex -> {
                int fromIndex = pageIndex * PAGINATION_ROW_PER_PAGE;
                int toIndex = Math.min(fromIndex + PAGINATION_ROW_PER_PAGE, data.size());
                employeeTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
                employeeTable.setPrefHeight((toIndex - fromIndex + STRETCH_TABLE_COEFFICIENT) * employeeTable.getFixedCellSize());
                return new Pane(); // refresh pagination
            });
        });
    }
}
