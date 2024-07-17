package vn.elca.employer.client.fragment;

import com.google.protobuf.Int64Value;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.component.EmployeeInputComponent;
import vn.elca.employer.client.component.EmployerTableComponent;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.converter.EnumStringConverterFactory;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.stub.EmployerServiceGrpcStub;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.EmployerGetRequest;
import vn.elca.employer.common.EmployerGetResponse;
import vn.elca.employer.common.Fund;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Fragment(id = EmployerTableFragment.ID,
        viewLocation = "/fxml/fragment/EmployerTableFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployerTableFragment {
    public static final String ID = "EmployerTableFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerTableFragment.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private EmployerServiceGrpcStub stub;

    @Resource
    private Context context;

    @FXML
    private TableView<EmployerView> employerTable;

    @FXML
    private TableColumn<EmployerView, Fund> column1;

    @FXML
    private TableColumn<EmployerView, String> column2;

    @FXML
    private TableColumn<EmployerView, String> column3;

    @FXML
    private TableColumn<EmployerView, String> column4;

    @FXML
    private TableColumn<EmployerView, String> column5;

    @FXML
    private TableColumn<EmployerView, String> column6;

    @FXML
    private TableColumn<EmployerView, EmployerView> column7;

    @FXML
    private Pagination pagination;

    private final ObservableList<EmployerView> data = FXCollections.observableArrayList(new ArrayList<>());

    public void init() {
        bindLanguage();
        setupTableFormat();
        setupColumnFormat();
        setupPagination();
        LOGGER.debug("init");
    }

    public void updateData(List<EmployerView> newData) {
        data.setAll(newData);
    }

    public void removeItem(EmployerView item) {
        data.remove(item);
    }

    private void bindLanguage() {
        column1.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.fund"));
        column2.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.number"));
        column3.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.numberIde"));
        column4.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.name"));
        column5.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.startDate"));
        column6.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer.endDate"));
    }

    private void setupTableFormat() {
        employerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> employerTable.refresh()));
    }

    private void setupColumnFormat() {
        setupFundColumn();
        setupNormalColumn();
        setupActionColumn();
    }

    private void setupFundColumn() {
        column1.setCellValueFactory(new PropertyValueFactory<>("fund"));
        column1.setCellFactory(TextFieldTableCell.forTableColumn(EnumStringConverterFactory.getConverter(Fund.class, observableResourceFactory)));
    }

    private void setupNormalColumn() {
        column2.setCellValueFactory(new PropertyValueFactory<>("number"));
        column3.setCellValueFactory(new PropertyValueFactory<>("numberIde"));
        column4.setCellValueFactory(new PropertyValueFactory<>("name"));
        column5.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        column6.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    private void setupActionColumn() {
        column7.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue()));
        column7.setCellFactory(col -> {
            TableCell<EmployerView, EmployerView> cell = new TableCell<>();
            cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    cell.graphicProperty().bind(
                            Bindings.when(cell.emptyProperty())
                                    .then((Node) null)
                                    .otherwise(createActionButtons(newValue))
                    );
                }
            });
            return cell;
        });
    }

    private HBox createActionButtons(EmployerView employerView) {
        HBox hbox = new HBox();
        HBox.setHgrow(hbox, Priority.ALWAYS);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbox.getChildren().addAll(createDetailsButton(employerView), spacer, createDeleteButton(employerView));
        return hbox;
    }

    private Button createDetailsButton(EmployerView employerView) {
        Button btnDetails = new Button();
        btnDetails.textProperty().bind(observableResourceFactory.getStringBinding("Button.details"));
        btnDetails.setOnAction(event -> {
            context.send(EmployeePerspective.ID, "show");
            context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInputComponent.ID), employerView);
        });
        return btnDetails;
    }

    private Button createDeleteButton(EmployerView employerView) {
        Button btnDelete = new Button();
        btnDelete.textProperty().bind(observableResourceFactory.getStringBinding("Button.delete"));
        btnDelete.setOnAction(event -> {
            if (verifyBeforeDeletion(employerView)) {
                Optional<ButtonType> option = showConfirmationDialog();
                if (option.isPresent() && option.get() == ButtonType.OK) {
                    context.send(EmployerPerspective.ID.concat(".").concat(DeleteCallBack.ID), employerView);
                }
            } else {
                showInformationDialog();
                context.send(EmployerPerspective.ID.concat(".").concat(EmployerTableComponent.ID), employerView);
            }
        });
        return btnDelete;
    }

    private boolean verifyBeforeDeletion(EmployerView employer) {
        EmployerGetRequest request = EmployerGetRequest.newBuilder()
                .setId(Int64Value.of(employer.getId()))
                .build();
        EmployerGetResponse response = stub.getEmployer(request);
        return !response.getEmployersList().isEmpty();
    }

    private Optional<ButtonType> showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Employer Deletion");
        alert.setHeaderText("Are you sure to delete this employer from database?");
        return alert.showAndWait();
    }

    private void showInformationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Employer Deletion");
        alert.setHeaderText("This employer is not in database!");
        alert.showAndWait();
    }

    private void setupPagination() {
        pagination.setPageCount(1);
        data.addListener((ListChangeListener<? super EmployerView>) c -> {
            pagination.setPageCount(data.size() / EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE); // Math.ceil
            pagination.setCurrentPageIndex(0);
            pagination.setPageFactory(pageIndex -> {
                int fromIndex = pageIndex * EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE;
                int toIndex = Math.min(fromIndex + EmployerJacpfxConfig.PAGINATION_ROW_PER_PAGE, data.size());
                employerTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
                return new Pane(); // refresh pagination
            });
        });
    }
}
