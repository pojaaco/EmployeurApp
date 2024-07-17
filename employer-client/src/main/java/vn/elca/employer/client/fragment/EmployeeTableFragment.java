package vn.elca.employer.client.fragment;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
                && employeeView.getNumberAvs().matches("756\\.\\d{4}\\.\\d{4}\\.\\d{2}"), null, null);
        isValid &= Validator.checkCondition(employeeView.getLastName() != null, null, null);
        isValid &= Validator.checkCondition(employeeView.getFirstName() != null, null, null);
        isValid &= Validator.checkCondition(employeeView.getStartDate() != null, null, null);
        isValid &= Validator.checkCondition(employeeView.getEndDate() != null, null, null);
        isValid &= Validator.checkCondition(LocalDate.parse(employeeView.getEndDate(), creationHelper.dateFormatter)
                .isAfter(LocalDate.parse(employeeView.getStartDate(), creationHelper.dateFormatter)), null, null);
        return isValid;
    }

    private void bindLanguage() {
        column1.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee.numberAvs"));
        column2.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee.lastName"));
        column3.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee.firstName"));
        column4.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee.startDate"));
        column5.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee.endDate"));
        column6.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee.avsAiApg"));
        column7.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee.ac"));
        column8.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employee.af"));
    }

    private void setupTableFormat() {
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
            pagination.setPageCount((int) Math.ceil(data.size() / (double) EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE)); // Math.ceil
            pagination.setCurrentPageIndex(0);
            pagination.setPageFactory(pageIndex -> {
                int fromIndex = pageIndex * EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE;
                int toIndex = Math.min(fromIndex + EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE, data.size());
                employeeTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
                return new Pane(); // refresh pagination
            });
        });
    }
}
