package vn.elca.frontend.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.util.FXUtil;
import vn.elca.common.EmployeurProto;
import vn.elca.common.EmployeurSearchResponse;
import vn.elca.frontend.config.BasicConfig;

import java.util.List;
import java.util.ResourceBundle;

@View(id = BasicConfig.COMPONENT_EMPLOYEUR_BOTTOM,
        name = "resultList",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_BOTTOM)
public class ComponentEmployeurBottom implements FXComponent {
    private Node pane;

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            EmployeurSearchResponse response = message.getTypedMessageBody(EmployeurSearchResponse.class);
            ObservableList<EmployeurProto> data = FXCollections.observableArrayList(response.getEmployeursList());

            TableView<EmployeurProto> tableView = (TableView<EmployeurProto>) ((VBox) pane).getChildren().get(0);
            tableView.setItems(data);
        }
        return pane;
    }

    @PostConstruct
    public void onPostConstructComponent(final FXComponentLayout componentLayout,
                                         final ResourceBundle resourceBundle) {
        VBox vBox = new VBox();

        vBox.getChildren().add(createResultTable(resourceBundle));

        pane = vBox;
    }

    private TableView<EmployeurProto> createResultTable(ResourceBundle resourceBundle) {
        TableView<EmployeurProto> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<EmployeurProto, Object> colC1 = new TableColumn<>(resourceBundle.getString("label.type"));
        colC1.setCellValueFactory(new PropertyValueFactory<>("caisse"));

        TableColumn<EmployeurProto, Object> colC2 = new TableColumn<>(resourceBundle.getString("label.number"));
        colC2.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<EmployeurProto, Object> colC3 = new TableColumn<>(resourceBundle.getString("label.numberIDE"));
        colC3.setCellValueFactory(new PropertyValueFactory<>("numberIDE"));

        TableColumn<EmployeurProto, Object> colC4 = new TableColumn<>(resourceBundle.getString("label.name"));
        colC4.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<EmployeurProto, Object> colC5 = new TableColumn<>(resourceBundle.getString("label.startingDate"));
        colC5.setCellValueFactory(new PropertyValueFactory<>("startingDate"));

        TableColumn<EmployeurProto, Object> colC6 = new TableColumn<>(resourceBundle.getString("label.endDate"));
        colC6.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        TableColumn<EmployeurProto, Object> colC7 = new TableColumn<>();

        tableView.getColumns().addAll(colC1, colC2, colC3, colC4, colC5, colC6, colC7);

        return tableView;
    }
}
