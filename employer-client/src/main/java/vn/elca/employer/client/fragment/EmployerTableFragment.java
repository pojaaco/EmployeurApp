package vn.elca.employer.client.fragment;

import com.google.protobuf.Int64Value;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import vn.elca.employer.client.converter.EnumStringConverterFactory;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.message.MessageType;
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
public class EmployerTableFragment implements AbstractFragment {
    public static final String ID = "EmployerTableFragment";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerTableFragment.class);
    private static final String BUNDLE_PROPERTY_EMPLOYER = "Property.Employer";
    private static final String BUNDLE_PROMPT_EMPTY_TABLE = "Prompt.Table.empty";
    private static final String BUNDLE_BUTTON_DETAILS = "Button.details";
    private static final String BUNDLE_BUTTON_DELETE = "Button.delete";
    private static final String BUNDLE_DIALOG_NOT_DELETE_TITLE = "Dialog.Information.Employer.NotDelete.title";
    private static final String BUNDLE_DIALOG_NOT_DELETE_HEADER = "Dialog.Information.Employer.NotDelete.header";
    private static final String BUNDLE_DIALOG_DELETE_TITLE = "Dialog.Confirmation.Employer.Delete.title";
    private static final String BUNDLE_DIALOG_DELETE_HEADER = "Dialog.Confirmation.Employer.Delete.header";
    private static final String PROPERTY_NUMBER = "number";
    private static final String PROPERTY_NAME = "name";
    private static final String PROPERTY_FUND = "fund";
    private static final String PROPERTY_NUMBER_IDE = "numberIde";
    private static final String PROPERTY_START_DATE = "startDate";
    private static final String PROPERTY_END_DATE = "endDate";
    private static final double STRETCH_TABLE_COEFFICIENT = 1.0;
    private static final int PAGINATION_ROW_PER_PAGE = 6;

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
        reset();
        LOGGER.debug("init");
    }

    public void reset() {
        displayTableAndPagination(false);
    }

    public void updateData(List<EmployerView> newData) {
        data.setAll(newData);
    }

    public void removeItem(EmployerView item) {
        data.remove(item);
    }

    private void bindLanguage() {
        column1.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_FUND));
        column2.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_NUMBER));
        column3.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_NUMBER_IDE));
        column4.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_NAME));
        column5.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_START_DATE));
        column6.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROPERTY_EMPLOYER + "." + PROPERTY_END_DATE));
    }

    private void setupTableFormat() {
        Label placeholderLabel = new Label();
        placeholderLabel.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_PROMPT_EMPTY_TABLE));
        employerTable.setPlaceholder(placeholderLabel);
        employerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> employerTable.refresh()));
    }

    private void setupColumnFormat() {
        setupFundColumn();
        setupNormalColumn();
        setupActionColumn();
        setupColumnWidth();
    }

    private void setupFundColumn() {
        column1.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_FUND));
        column1.setCellFactory(TextFieldTableCell.forTableColumn(EnumStringConverterFactory.getConverter(Fund.class, observableResourceFactory)));
    }

    private void setupNormalColumn() {
        column2.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_NUMBER));
        column3.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_NUMBER_IDE));
        column4.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_NAME));
        column5.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_START_DATE));
        column6.setCellValueFactory(new PropertyValueFactory<>(PROPERTY_END_DATE));
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

    private void setupColumnWidth() {
        column1.maxWidthProperty().bind(employerTable.widthProperty().multiply(0.12));
        column2.maxWidthProperty().bind(employerTable.widthProperty().multiply(0.1));
        column3.maxWidthProperty().bind(employerTable.widthProperty().multiply(0.15));
        column4.maxWidthProperty().bind(employerTable.widthProperty().multiply(0.15));
        column5.maxWidthProperty().bind(employerTable.widthProperty().multiply(0.11));
        column6.maxWidthProperty().bind(employerTable.widthProperty().multiply(0.11));
        column7.maxWidthProperty().bind(employerTable.widthProperty().multiply(0.26));
        employerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private HBox createActionButtons(EmployerView employerView) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        Region spacer = new Region();
        spacer.prefWidthProperty().bind(hbox.widthProperty().multiply(0.1));
        hbox.getChildren().addAll(createDetailsButton(employerView), spacer, createDeleteButton(employerView));
        return hbox;
    }

    private Button createDetailsButton(EmployerView employerView) {
        Button btnDetails = new Button();
        btnDetails.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_BUTTON_DETAILS));
        btnDetails.setOnAction(event -> {
            context.send(EmployeePerspective.ID, MessageType.SHOW);
            context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInputComponent.ID), employerView);
        });
        return btnDetails;
    }

    private Button createDeleteButton(EmployerView employerView) {
        Button btnDelete = new Button();
        btnDelete.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_BUTTON_DELETE));
        btnDelete.setOnAction(event -> {
            if (verifyBeforeDelete(employerView)) {
                Optional<ButtonType> option = showConfirmationDialog();
                if (option.isPresent() && option.get() == ButtonType.OK) {
                    context.send(EmployerPerspective.ID.concat(".").concat(DeleteCallBack.ID), employerView);
                }
            } else {
                showInformationDialog();
                removeItem(employerView);
            }
        });
        return btnDelete;
    }

    private boolean verifyBeforeDelete(EmployerView employer) {
        EmployerGetRequest request = EmployerGetRequest.newBuilder()
                .setId(Int64Value.of(employer.getId()))
                .build();
        EmployerGetResponse response = stub.getEmployer(request);
        return !response.getEmployersList().isEmpty();
    }

    private Optional<ButtonType> showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(observableResourceFactory.getResources().getString(BUNDLE_DIALOG_DELETE_TITLE));
        alert.setHeaderText(observableResourceFactory.getResources().getString(BUNDLE_DIALOG_DELETE_HEADER));
        return alert.showAndWait();
    }

    private void showInformationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(observableResourceFactory.getResources().getString(BUNDLE_DIALOG_NOT_DELETE_TITLE));
        alert.setHeaderText(observableResourceFactory.getResources().getString(BUNDLE_DIALOG_NOT_DELETE_HEADER));
        alert.showAndWait();
    }

    private void setupPagination() {
        pagination.setPageCount(1);
        data.addListener((ListChangeListener<? super EmployerView>) c -> {
            if (data.isEmpty()) {
                displayTableAndPagination(false);
            } else {
                pagination.setPageCount((int) Math.ceil(data.size() / (double) PAGINATION_ROW_PER_PAGE));
                pagination.setCurrentPageIndex(0);
                pagination.setPageFactory(pageIndex -> {
                    int fromIndex = pageIndex * PAGINATION_ROW_PER_PAGE;
                    int toIndex = Math.min(fromIndex + PAGINATION_ROW_PER_PAGE, data.size());
                    employerTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
                    employerTable.setPrefHeight((toIndex - fromIndex + STRETCH_TABLE_COEFFICIENT) * employerTable.getFixedCellSize());
                    return new Pane(); // refresh pagination
                });
                displayTableAndPagination(true);
            }
        });
    }

    private void displayTableAndPagination(boolean visible) {
        if (visible) {
            employerTable.setVisible(true);
            pagination.setVisible(true);
        } else {
            employerTable.setVisible(false);
            pagination.setVisible(false);
        }
    }
}
