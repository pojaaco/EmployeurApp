package vn.elca.employer.client.component;

import javafx.event.ActionEvent;
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
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.SearchCallBack;
import vn.elca.employer.client.converter.EnumStringConverter;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.Caisse;
import vn.elca.employer.client.config.EmployerJacpfxConfig;

import java.util.Arrays;
import java.util.stream.Collectors;

@View(id = EmployerInfoComponent.ID,
        name = EmployerInfoComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER)
public class EmployerInfoComponent implements FXComponent {
    public static final String ID = "EmployerInfoComponent";
    public static final String RESOURCE_ROOT = "EmployerPerspective.EmployerInfoComponent";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private EnumStringConverter<Caisse> caisseConverter;

    @Resource
    private Context context;

    private Node pane;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return pane;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        VBox vBox = new VBox();
        vBox.setSpacing(5);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        borderPane.setCenter(createInfoFields());
        vBox.getChildren().add(borderPane);

        StackPane stackPane = new StackPane();
        Button button = new Button();
        button.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + ".button.add"));
        button.setOnAction(event -> {
            context.send(EmployeePerspective.ID, "show");
            context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInfoComponent.ID), new EmployerView());
        });
        StackPane.setAlignment(button, Pos.BOTTOM_RIGHT);
        stackPane.getChildren().add(button);
        vBox.getChildren().add(stackPane);

        pane = vBox;
    }

    private GridPane createInfoFields() {
        GridPane gridPane = new GridPane();
        configureGridPane(gridPane);

        VBox column1Row1 = createColumn("label.caisse", createCaisseComboBox());
        VBox column2Row1 = createColumn("label.number", new TextField());
        VBox column3Row1 = createColumn("label.startingDate", new TextField());
        VBox column1Row2 = createColumn("label.name", new TextField());
        VBox column2Row2 = createColumn("label.numberIDE", new TextField());
        VBox column3Row2 = createColumn("label.endDate", new TextField());

        addColumnsToGrid(gridPane, column1Row1, column2Row1, column3Row1, 0);
        addColumnsToGrid(gridPane, column1Row2, column2Row2, column3Row2, 1);

        HBox buttonBox = createButtonBox();
        gridPane.add(buttonBox, 0, 2);

        return gridPane;
    }

    private void configureGridPane(GridPane gridPane) {
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(5));

        for (int i = 0; i < 3; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(33);
            gridPane.getColumnConstraints().add(col);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(33);
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

    private VBox createColumn(String labelKey, Control control) {
        VBox column = new VBox(10);
        Label label = new Label();
        label.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + "." + labelKey));
        control.setId(labelKey.substring(6));
        column.getChildren().addAll(label, control);
        return column;
    }

    private void addColumnsToGrid(GridPane gridPane, VBox column1, VBox column2, VBox column3, int rowIndex) {
        gridPane.add(column1, 0, rowIndex);
        gridPane.add(column2, 1, rowIndex);
        gridPane.add(column3, 2, rowIndex);
    }

    private HBox createButtonBox() {
        HBox hboxForButton = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button button1 = new Button();
        button1.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + ".button.search"));
        Button button2 = new Button();
        button2.textProperty().bind(observableResourceFactory.getStringBinding(RESOURCE_ROOT + ".button.reset"));

        button1.setOnAction(this::handleSearchButton);
        button2.setOnAction(this::handleResetButton);

        hboxForButton.getChildren().addAll(button1, spacer, button2);
        hboxForButton.setAlignment(Pos.BOTTOM_CENTER);
        return hboxForButton;
    }

    private void handleSearchButton(ActionEvent event) {
        EmployerView employerView = new EmployerView();

        ComboBox<String> caisse = (ComboBox<String>) pane.lookup("#caisse");
        if (!caisse.getSelectionModel().isEmpty()) {
            employerView.setCaisse(caisse.getValue());
        }

        TextField number = (TextField) pane.lookup("#number");
        if (!number.getText().isEmpty()) {
            employerView.setNumber(number.getText());
        }

        TextField name = (TextField) pane.lookup("#name");
        if (!name.getText().isEmpty()) {
            employerView.setName(name.getText());
        }

        TextField numberIde = (TextField) pane.lookup("#numberIDE");
        if (!numberIde.getText().isEmpty()) {
            employerView.setNumberIDE(numberIde.getText());
        }

        TextField startingDate = (TextField) pane.lookup("#startingDate");
        if (!startingDate.getText().isEmpty()) {
            employerView.setStartingDate(startingDate.getText());
        }

        TextField endDate = (TextField) pane.lookup("#endDate");
        if (!endDate.getText().isEmpty()) {
            employerView.setEndDate(endDate.getText());
        }

        context.send(EmployerPerspective.ID.concat(".").concat(SearchCallBack.ID), employerView);
    }

    private void handleResetButton(ActionEvent event) {
        ((ComboBox<String>) pane.lookup("#caisse")).getSelectionModel().selectFirst();
        ((TextField) pane.lookup("#number")).clear();
        ((TextField) pane.lookup("#name")).clear();
        ((TextField) pane.lookup("#numberIDE")).clear();
        ((TextField) pane.lookup("#startingDate")).clear();
        ((TextField) pane.lookup("#endDate")).clear();
    }
}
