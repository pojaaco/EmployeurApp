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
import vn.elca.employer.client.callback.GetCallBack;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.client.config.EmployerJacpfxConfig;

@View(id = EmployerInfoComponent.ID,
        name = EmployerInfoComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER)
public class EmployerInfoComponent implements FXComponent {
    public static final String ID = "EmployerInfoComponent";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private Helper helper;

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
//        borderPane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        borderPane.setCenter(createInfoFields());
        vBox.getChildren().add(borderPane);

        StackPane stackPane = new StackPane();
        Button button = new Button();
        button.textProperty().bind(observableResourceFactory.getStringBinding("Button.add"));
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

        // TODO: Add Date Picker
        VBox column1Row1 = createColumn("fund", helper.createFundComboBox());
        VBox column2Row1 = createColumn("number", new TextField());
        VBox column3Row1 = createColumn("startDate", new TextField());
        VBox column1Row2 = createColumn("name", new TextField());
        VBox column2Row2 = createColumn("numberIde", new TextField());
        VBox column3Row2 = createColumn("endDate", new TextField());

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
            col.setPercentWidth(100./3.);
            gridPane.getColumnConstraints().add(col);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100./3.);
            gridPane.getRowConstraints().add(row);
        }
    }

    private VBox createColumn(String labelKey, Control control) {
        VBox column = new VBox(10);
        Label label = new Label();
        label.textProperty().bind(observableResourceFactory.getStringBinding("Property.Employer." + labelKey));
        control.setId(labelKey);
        column.getChildren().addAll(label, control);
        return column;
    }

    private void addColumnsToGrid(GridPane gridPane, VBox column1, VBox column2, VBox column3, int rowIndex) {
        gridPane.add(column1, 0, rowIndex);
        gridPane.add(column2, 1, rowIndex);
        gridPane.add(column3, 2, rowIndex);
    }

    private HBox createButtonBox() {
        HBox hBoxForButton = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button button1 = new Button();
        button1.textProperty().bind(observableResourceFactory.getStringBinding("Button.search"));
        Button button2 = new Button();
        button2.textProperty().bind(observableResourceFactory.getStringBinding("Button.reset"));

        button1.setOnAction(this::handleSearchButton);
        button2.setOnAction(this::handleResetButton);

        hBoxForButton.getChildren().addAll(button1, spacer, button2);
        hBoxForButton.setAlignment(Pos.BOTTOM_CENTER);
        return hBoxForButton;
    }

    private void handleSearchButton(ActionEvent event) {
        EmployerView employerView = new EmployerView();

        ComboBox<String> fund = (ComboBox<String>) pane.lookup("#fund");
        if (!fund.getSelectionModel().isEmpty()) {
            employerView.setFund(fund.getValue());
        }

        TextField number = (TextField) pane.lookup("#number");
        if (!number.getText().isEmpty()) {
            employerView.setNumber(number.getText());
        }

        TextField name = (TextField) pane.lookup("#name");
        if (!name.getText().isEmpty()) {
            employerView.setName(name.getText());
        }

        TextField numberIde = (TextField) pane.lookup("#numberIde");
        if (!numberIde.getText().isEmpty()) {
            employerView.setNumberIde(numberIde.getText());
        }

        TextField startingDate = (TextField) pane.lookup("#startDate");
        if (!startingDate.getText().isEmpty()) {
            employerView.setStartDate(startingDate.getText());
        }

        TextField endDate = (TextField) pane.lookup("#endDate");
        if (!endDate.getText().isEmpty()) {
            employerView.setEndDate(endDate.getText());
        }

        context.send(EmployerPerspective.ID.concat(".").concat(GetCallBack.ID), employerView);
    }

    private void handleResetButton(ActionEvent event) {
        ((ComboBox<String>) pane.lookup("#fund")).getSelectionModel().selectFirst();
        ((TextField) pane.lookup("#number")).clear();
        ((TextField) pane.lookup("#name")).clear();
        ((TextField) pane.lookup("#numberIde")).clear();
        ((TextField) pane.lookup("#startDate")).clear();
        ((TextField) pane.lookup("#endDate")).clear();
    }
}
