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

@View(id = EmployeeInfoComponent.ID,
        name = EmployeeInfoComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER)
public class EmployeeInfoComponent implements FXComponent {
    public static final String ID = "EmployeeInfoComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInfoComponent.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private Helper helper;

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
                context.send(EmployeePerspective.ID.concat(".").concat(SetCallBack.ID), newEmployer);
            }
        } else if (sourceId.contains(SetCallBack.ID)) { // Result of Saving
            if (message.getMessageBody() != null) {
                EmployerView newEmployer = message.getTypedMessageBody(EmployerView.class);
                updateEmployerInfo(newEmployer);
                showInfoDialog(newEmployer);
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
        HBox hBox = new HBox();
        hBox.getChildren().add(createInfoFields());
        pane = hBox;
    }

    private GridPane createInfoFields() {
        GridPane gridPane = new GridPane();
        configureGridPane(gridPane);

        // TODO: Add date picker
        createRowToGrid(gridPane, 0, "number", new Label());
        createRowToGrid(gridPane, 1, "name", new TextField());
        createRowToGrid(gridPane, 2, "fund", helper.createFundComboBox());
        createRowToGrid(gridPane, 3, "numberIde", new TextField());
        createRowToGrid(gridPane, 4, "startDate", new TextField());
        createRowToGrid(gridPane, 5, "endDate", new TextField());

        return gridPane;
    }

    private void configureGridPane(GridPane gridPane) {
        gridPane.setVgap(5);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        gridPane.getColumnConstraints().add(col1);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        gridPane.getColumnConstraints().add(col2);

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100./6.);
            gridPane.getRowConstraints().add(row);
        }
    }

    private void createRowToGrid(GridPane gridPane, int rowIndex, String labelKey, Control control) {
        Label label = new Label();
        label.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer." + labelKey));
        control.setId(labelKey);
        gridPane.add(label, 0, rowIndex);
        gridPane.add(control, 1, rowIndex);
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
        ((TextField) pane.lookup("#startDate")).setText(view.getStartDate());
        ((TextField) pane.lookup("#endDate")).setText(view.getEndDate());
    }

    private EmployerView extractEmployerView() {
        // TODO: validate user input in this component
        EmployerView newEmployer = new EmployerView();
        newEmployer.setId(employer.getId());
        newEmployer.setNumber(employer.getNumber());
        newEmployer.setName(((TextField) pane.lookup("#name")).getText());
        newEmployer.setFund(((ComboBox<Fund>) pane.lookup("#fund")).getSelectionModel().getSelectedItem());
        newEmployer.setNumberIde(((TextField) pane.lookup("#numberIde")).getText());
        newEmployer.setStartDate(((TextField) pane.lookup("#startDate")).getText());
        newEmployer.setEndDate(((TextField) pane.lookup("#endDate")).getText());
        newEmployer.setEmployees(employer.getEmployees());
        return newEmployer;
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

    private void showInfoDialog(EmployerView employer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Employer Save");
        alert.setHeaderText("Employer " + employer.getName() + "has been saved.");
        alert.showAndWait();
    }
}
