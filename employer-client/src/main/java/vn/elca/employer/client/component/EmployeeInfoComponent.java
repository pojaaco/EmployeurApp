package vn.elca.employer.client.component;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.ImportCallBack;
import vn.elca.employer.client.callback.SaveCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.converter.EnumStringConverter;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.client.workbench.EmployerWorkbench;
import vn.elca.employer.common.Caisse;

import java.util.Arrays;
import java.util.stream.Collectors;

@View(id = EmployeeInfoComponent.ID,
        name = EmployeeInfoComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER)
public class EmployeeInfoComponent implements FXComponent {
    public static final String ID = "EmployeeInfoComponent";
    public static final String RESOURCE_ROOT = "EmployeePerspective.EmployeeInfoComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInfoComponent.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private EnumStringConverter<Caisse> caisseConverter;

    @Resource
    private Context context;

    private Node pane;

    private EmployerView employer;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            if (message.getSourceId().equals(EmployerPerspective.ID.concat(".").concat(EmployerInfoComponent.ID))
                    || message.getSourceId().equals(EmployerPerspective.ID.concat(".").concat(EmployerResultComponent.ID))) {
                employer = message.getTypedMessageBody(EmployerView.class);
                if (employer.getNumber() != null) {
                    ((Label) pane.lookup("#number")).setText(employer.getNumber());
                } else {
                    ((Label) pane.lookup("#number")).setText("------");
                }
                ((TextField) pane.lookup("#name")).setText(employer.getName());
                if (employer.getCaisse() != null) {
                    ((ComboBox<String>) pane.lookup("#caisse")).getSelectionModel().select(employer.getCaisse());
                }
                ((TextField) pane.lookup("#numberIDE")).setText(employer.getNumberIDE());
                ((TextField) pane.lookup("#startingDate")).setText(employer.getStartingDate());
                ((TextField) pane.lookup("#endDate")).setText(employer.getEndDate());
            } else if (message.getSourceId().equals(EmployerWorkbench.ID.concat(".").concat(EmployeePerspective.ID))) {
                if (message.getTypedMessageBody(String.class).equals("save")) {

                    // TODO: validate user input in this component
                    EmployerView newEmployer = new EmployerView();
                    newEmployer.setId(employer.getId());
                    newEmployer.setNumber(employer.getNumber());
                    newEmployer.setName(((TextField) pane.lookup("#name")).getText());
                    newEmployer.setCaisse(((ComboBox<String>) pane.lookup("#caisse")).getSelectionModel().getSelectedItem());
                    newEmployer.setNumberIDE(((TextField) pane.lookup("#numberIDE")).getText());
                    newEmployer.setStartingDate(((TextField) pane.lookup("#startingDate")).getText());
                    newEmployer.setEndDate(((TextField) pane.lookup("#endDate")).getText());
                    newEmployer.setEmployees(employer.getEmployees());

                    context.send(EmployeePerspective.ID.concat(".").concat(SaveCallBack.ID), newEmployer);
                }
            } else if (message.getSourceId().equals(EmployeePerspective.ID.concat(".").concat(SaveCallBack.ID))) {
                if (message.getMessageBody() == null) {
                    LOGGER.debug("Can't save!");
                }

                EmployerView newEmployer = message.getTypedMessageBody(EmployerView.class);

                employer.setId(newEmployer.getId());
                employer.setNumber(newEmployer.getNumber());
                employer.setName(newEmployer.getName());
                employer.setCaisse(newEmployer.getCaisse());
                employer.setNumberIDE(newEmployer.getNumberIDE());
                employer.setStartingDate(newEmployer.getStartingDate());
                employer.setEndDate(newEmployer.getEndDate());
                employer.setEmployees(newEmployer.getEmployees());

                context.send(EmployerPerspective.ID.concat(".").concat(EmployerResultComponent.ID), "refresh");
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
        createRowToGrid(gridPane, 0, "label.number", new Label());
        createRowToGrid(gridPane, 1, "label.name", new TextField());
        createRowToGrid(gridPane, 2, "label.caisse", createCaisseComboBox());
        createRowToGrid(gridPane, 3, "label.numberIDE", new TextField());
        createRowToGrid(gridPane, 4, "label.startingDate", new TextField());
        createRowToGrid(gridPane, 5, "label.endDate", new TextField());

        return gridPane;
    }

    private void configureGridPane(GridPane gridPane) {
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        gridPane.getColumnConstraints().add(col1);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(35);
        gridPane.getColumnConstraints().add(col2);

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100./6.);
            gridPane.getRowConstraints().add(row);
        }
    }

    private ComboBox<String> createCaisseComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.getItems().addAll(
                Arrays.stream(Caisse.values())
                        .filter(e -> e != Caisse.UNRECOGNIZED)
                        .map(caisseConverter::toString)
                        .collect(Collectors.toList())
        );
        comboBox.setValue(caisseConverter.toString(Caisse.CAISSE_CANTONALE));
        return comboBox;
    }

    private void createRowToGrid(GridPane gridPane, int rowIndex, String labelKey, Control control) {
        Label label = new Label();
        label.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + "." + labelKey));
        control.setId(labelKey.substring(6));
        gridPane.add(label, 0, rowIndex);
        gridPane.add(control, 1, rowIndex);
    }
}
