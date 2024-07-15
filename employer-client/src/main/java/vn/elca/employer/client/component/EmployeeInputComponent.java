package vn.elca.employer.client.component;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.SetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.Fund;

import java.time.LocalDate;

@View(id = EmployeeInputComponent.ID,
        name = EmployeeInputComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER)
public class EmployeeInputComponent implements FXComponent {
    public static final String ID = "EmployeeInputComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInputComponent.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private CreationHelper creationHelper;

    @Resource
    private Context context;

    private Node pane;

    private EmployerView employer;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        String sourceId = message.getSourceId();
        if (sourceId.startsWith(EmployerPerspective.ID)) { // Add or Details
            updateInfoFields(message.getTypedMessageBody(EmployerView.class));
        } else if (sourceId.endsWith(EmployeePerspective.ID)) { // Save
            if (message.getTypedMessageBody(String.class).equals("save")) {
                EmployerView newEmployer = extractEmployerView();
                if (validateEmployerView(newEmployer)) {
                    context.send(EmployeePerspective.ID.concat(".").concat(SetCallBack.ID), newEmployer);
                } else {
                    context.send(EmployeePerspective.ID.concat(".").concat(SetCallBack.ID), "not_save");
                    showNotSavedInfoDialog(newEmployer);
                }
            }
        } else if (sourceId.contains(SetCallBack.ID)) { // Result of Saving
            if (message.getMessageBody() != null) {
                EmployerView newEmployer = message.getTypedMessageBody(EmployerView.class);
                updateEmployerInfo(newEmployer);
                showSavedInfoDialog(newEmployer);
            } else {
                LOGGER.debug("Cannot save the employer.");
            }
        }
        return pane;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        VBox vBox = new VBox();
        vBox.getChildren().add(createInfoFields());
        pane = vBox;
    }

    private GridPane createInfoFields() {
        GridPane gridPane = new GridPane();
        configureGridPane(gridPane);

        createRowToGrid(gridPane, 0, "number", new Label());
        createRowToGrid(gridPane, 1, "name", new TextField());
        createRowToGrid(gridPane, 2, "fund", creationHelper.createFundComboBox());
        createRowToGrid(gridPane, 3, "numberIde", new TextField());
        createRowToGrid(gridPane, 4, "startDate", creationHelper.createDatePicker());
        createRowToGrid(gridPane, 5, "endDate", creationHelper.createDatePicker());

        return gridPane;
    }

    private void configureGridPane(GridPane gridPane) {
        gridPane.setVgap(5);
        gridPane.setHgap(10);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(10);
        gridPane.getColumnConstraints().add(col1);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        gridPane.getColumnConstraints().add(col2);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(65);
        gridPane.getColumnConstraints().add(col3);

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(80./6.);
            gridPane.getRowConstraints().add(row);
        }
    }

    private void createRowToGrid(GridPane gridPane, int rowIndex, String labelKey, Control control) {
        Label title = new Label();
        title.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer." + labelKey));
        Label error = new Label();
        error.textProperty().bind(observableResourceFactory.getStringBinding("Label.Error.Employer." + labelKey));
        error.setId("error_" + labelKey);
        error.setVisible(false);
        control.setId(labelKey);
        control.setOnKeyTyped(event -> {
            error.setVisible(false);
        });
        gridPane.add(title, 0, rowIndex);
        gridPane.add(control, 1, rowIndex);
        gridPane.add(error, 2, rowIndex);
    }

    private void updateInfoFields(EmployerView view) {
        this.employer = view;
        if (view.getNumber() != null) {
            ((Label) pane.lookup("#number")).setText(view.getNumber());
        } else {
            ((Label) pane.lookup("#number")).setText("------");
        }
        if (view.getFund() != null) {
            ((ComboBox<Fund>) pane.lookup("#fund")).getSelectionModel().select(view.getFund());
        }
        ((TextField) pane.lookup("#name")).setText(view.getName());
        ((TextField) pane.lookup("#numberIde")).setText(view.getNumberIde());
        if (view.getStartDate() != null) {
            ((DatePicker) pane.lookup("#startDate")).setValue(LocalDate.parse(view.getStartDate(), creationHelper.dateFormatter));
        }
        if (view.getEndDate() != null) {
            ((DatePicker) pane.lookup("#endDate")).setValue(LocalDate.parse(view.getEndDate(), creationHelper.dateFormatter));
        }
    }

    private EmployerView extractEmployerView() {
        EmployerView newEmployer = new EmployerView();
        newEmployer.setId(employer.getId());
        newEmployer.setNumber(employer.getNumber());
        newEmployer.setName(((TextField) pane.lookup("#name")).getText());
        newEmployer.setFund(((ComboBox<Fund>) pane.lookup("#fund")).getSelectionModel().getSelectedItem());
        newEmployer.setNumberIde(((TextField) pane.lookup("#numberIde")).getText());
        if (((DatePicker) pane.lookup("#startDate")).getValue() != null) {
            newEmployer.setStartDate(creationHelper.dateFormatter.format(((DatePicker) pane.lookup("#startDate")).getValue()));
        } else {
            newEmployer.setStartDate(null);
        }
        if (((DatePicker) pane.lookup("#endDate")).getValue() != null) {
            newEmployer.setEndDate(creationHelper.dateFormatter.format(((DatePicker) pane.lookup("#endDate")).getValue()));
        } else {
            newEmployer.setEndDate(null);
        }
        newEmployer.setEmployees(employer.getEmployees());
        return newEmployer;
    }

    private boolean validateEmployerView(EmployerView employerView) {
        boolean isValid = true;

        if (employerView.getName() == null) {
            pane.lookup("#error_name").setVisible(true);
            isValid = false;
        }
        if (employerView.getNumberIde() == null
                || !employerView.getNumberIde().matches("(ADM|CHE)-\\d{3}\\.\\d{3}\\.\\d{3}")) {
            pane.lookup("#error_numberIde").setVisible(true);
            isValid = false;
        }
        if (employerView.getStartDate() == null
                || !employerView.getStartDate().matches("(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d{2}")) {
            pane.lookup("#error_startDate").setVisible(true);
            isValid = false;
        }
        if (employerView.getEndDate() != null
                && !employerView.getEndDate().matches("(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d{2}")) {
            pane.lookup("#error_endDate").setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    private void updateEmployerInfo(EmployerView newEmployer) {
        employer.setId(newEmployer.getId());
        employer.setNumber(newEmployer.getNumber());
        employer.setName(newEmployer.getName());
        employer.setFund(newEmployer.getFund());
        employer.setNumberIde(newEmployer.getNumberIde());
        employer.setStartDate(newEmployer.getStartDate());
        employer.setEndDate(newEmployer.getEndDate());
        employer.setEmployees(newEmployer.getEmployees());
    }

    private void showSavedInfoDialog(EmployerView employer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Employer Save");
        alert.setHeaderText("Employer " + employer.getName() + "has been saved.");
        alert.showAndWait();
    }

    private void showNotSavedInfoDialog(EmployerView employer) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Employer Save");
        alert.setHeaderText("Employer " + employer.getName() + "cannot be saved.");
        alert.showAndWait();
    }
}
