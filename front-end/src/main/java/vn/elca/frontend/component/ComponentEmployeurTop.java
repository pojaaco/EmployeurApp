package vn.elca.frontend.component;

import com.google.protobuf.StringValue;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.context.Context;
import vn.elca.common.Caisse;
import vn.elca.common.EmployeurProto;
import vn.elca.common.EmployeurSearchRequest;
import vn.elca.frontend.config.BasicConfig;

import java.util.ResourceBundle;

@View(id = BasicConfig.COMPONENT_EMPLOYEUR_TOP,
        name = "informationFields",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_TOP)
public class ComponentEmployeurTop implements FXComponent {
    @Resource
    Context context;

    private Node pane;

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return pane;
    }

    @PostConstruct
    public void onPostConstructComponent(final FXComponentLayout componentLayout,
                                         final ResourceBundle resourceBundle) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        borderPane.setCenter(createInfoFields(resourceBundle));

        vBox.getChildren().add(borderPane);

        StackPane stackPane = new StackPane();
        Button button = new Button(resourceBundle.getString("button.add"));
        StackPane.setAlignment(button, Pos.BOTTOM_RIGHT);
        stackPane.getChildren().add(button);

        vBox.getChildren().add(stackPane);

        pane = vBox;
    }

    private GridPane createInfoFields(final ResourceBundle resourceBundle) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(33);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(33);

        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(33);

        gridPane.getRowConstraints().addAll(row1, row2, row3);

        VBox column1 = new VBox();
        column1.setSpacing(10);
        Label label1 = new Label(resourceBundle.getString("label.type"));
        ComboBox<Caisse> comboBox1 = new ComboBox<>();
        comboBox1.setMaxWidth(Double.MAX_VALUE);
        comboBox1.getItems().addAll(
                Caisse.CAISSE_UNKNOWN,
                Caisse.CAISSE_CANTONALE,
                Caisse.CAISSE_PROFESSIONNELLE
        );
        comboBox1.setConverter(new StringConverter<Caisse>() {
            @Override
            public String toString(Caisse object) {
                if (object == Caisse.CAISSE_CANTONALE) {
                    return "Caisse cantonale";
                } else if (object == Caisse.CAISSE_UNKNOWN) {
                    return "";
                } else {
                    return "Caisse professionnelle";
                }
            }

            @Override
            public Caisse fromString(String string) {
                if (string.compareTo("Caisse cantonale") == 0) {
                    return Caisse.CAISSE_CANTONALE;
                } else if (string.compareTo("Caisse professionnelle") == 0) {
                    return Caisse.CAISSE_PROFESSIONNELLE;
                } else {
                    return Caisse.CAISSE_UNKNOWN;
                }
            }
        });

        column1.getChildren().addAll(label1, comboBox1);
        gridPane.add(column1, 0, 0);

        VBox column2 = new VBox();
        column2.setSpacing(10);
        Label label2 = new Label(resourceBundle.getString("label.number"));
        TextField textField2 = new TextField();
        column2.getChildren().addAll(label2, textField2);
        gridPane.add(column2, 1, 0);

        VBox column3 = new VBox();
        column3.setSpacing(10);
        Label label3 = new Label(resourceBundle.getString("label.startingDate"));
        TextField textField3 = new TextField();
        column3.getChildren().addAll(label3, textField3);
        gridPane.add(column3, 2, 0);

        VBox column1Row2 = new VBox();
        column1Row2.setSpacing(10);
        Label label1Row2 = new Label(resourceBundle.getString("label.name"));
        TextField textField1Row2 = new TextField();
        column1Row2.getChildren().addAll(label1Row2, textField1Row2);
        gridPane.add(column1Row2, 0, 1);

        VBox column2Row2 = new VBox();
        column2Row2.setSpacing(10);
        Label label2Row2 = new Label(resourceBundle.getString("label.numberIDE"));
        TextField textField2Row2 = new TextField();
        column2Row2.getChildren().addAll(label2Row2, textField2Row2);
        gridPane.add(column2Row2, 1, 1);

        VBox column3Row2 = new VBox();
        column3Row2.setSpacing(10);
        Label label3Row2 = new Label(resourceBundle.getString("label.endDate"));
        TextField textField3Row2 = new TextField();
        column3Row2.getChildren().addAll(label3Row2, textField3Row2);
        gridPane.add(column3Row2, 2, 1);

        HBox hboxForButton = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button button1 = new Button(resourceBundle.getString("button.search"));
        Button button2 = new Button(resourceBundle.getString("button.reset"));
        hboxForButton.getChildren().addAll(button1, spacer, button2);
        hboxForButton.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.add(hboxForButton, 0, 2);

        button1.setOnAction(event -> {
            EmployeurSearchRequest.Builder request = EmployeurSearchRequest.newBuilder();

            if (!comboBox1.getSelectionModel().isEmpty()) {
                request.setCaisse(comboBox1.getValue());
            }
            if (!textField2.getText().isEmpty()) {
                request.setNumber(StringValue.of(textField2.getText()));
            }
            if (!textField3.getText().isEmpty()) {
                request.setStartingDate(StringValue.of(textField3.getText()));
            }
            if (!textField1Row2.getText().isEmpty()) {
                request.setName(StringValue.of(textField1Row2.getText()));
            }
            if (!textField2Row2.getText().isEmpty()) {
                request.setNumberIDE(StringValue.of(textField2Row2.getText()));
            }
            if (!textField3Row2.getText().isEmpty()) {
                request.setEndDate(StringValue.of(textField3Row2.getText()));
            }

            context.send(BasicConfig.PERSPECTIVE_EMPLOYEUR.concat(".").concat(BasicConfig.CALLBACK_SEARCH),
                    request.build());
        });

        button2.setOnAction(event -> {
            comboBox1.getSelectionModel().selectFirst();
            textField2.clear();
            textField3.clear();
            textField1Row2.clear();
            textField2Row2.clear();
            textField3Row2.clear();
        });

        return gridPane;
    }
}
