package vn.elca.employer.client.component;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.elca.employer.client.callback.ImportCallBack;
import vn.elca.employer.client.callback.SetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.fragment.EmployeeImportFragment;
import vn.elca.employer.client.fragment.EmployeeTableFragment;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.perspective.EmployeePerspective;

import java.util.ArrayList;
import java.util.List;

@DeclarativeView(id = EmployeeImportComponent.ID,
        name = EmployeeImportComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER,
        viewLocation = "/fxml/component/EmployeeImportComponent.fxml")
public class EmployeeImportComponent implements FXComponent {
    public static final String ID = "EmployeeImportComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeImportComponent.class);

    @Resource
    private Context context;

    @FXML
    private VBox vBox;

    private ManagedFragmentHandler<EmployeeImportFragment> importFragment;

    private ManagedFragmentHandler<EmployeeTableFragment> tableFragment;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        String sourceId = message.getSourceId();
        if (sourceId.endsWith(EmployeePerspective.ID)) {
            if (message.getTypedMessageBody(String.class).equals("reset")) {
                importFragment.getController().reset();
                tableFragment.getController().resetData();
            }
        } else if (sourceId.endsWith(EmployeeInputComponent.ID)) {
            if (message.getTypedMessageBody(String.class).equals("save")) {
                context.send(EmployeePerspective.ID.concat(".").concat(SetCallBack.ID), tableFragment.getController().getData());
            }
        } else if (sourceId.endsWith(ImportCallBack.ID)) {
            boolean existInvalidEmployee = false;
            List<EmployeeView> uncheckedResults = ((ObservableList<EmployeeView>) message.getMessageBody());
            List<EmployeeView> checkedResults = new ArrayList<>();
            for (EmployeeView employeeView : uncheckedResults) {
                if (tableFragment.getController().validateEmployeeView(employeeView)) {
                    checkedResults.add(employeeView);
                } else {
                    existInvalidEmployee = true;
                }
            }
            if (existInvalidEmployee) {
                showWarningInvalidDateDialog();
            }
            tableFragment.getController().updateData(checkedResults);
        }
        return null;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        importFragment = context.getManagedFragmentHandler(EmployeeImportFragment.class);
        final EmployeeImportFragment controllerImport = importFragment.getController();
        controllerImport.init();

        tableFragment = context.getManagedFragmentHandler(EmployeeTableFragment.class);
        final EmployeeTableFragment controllerTable = tableFragment.getController();
        controllerTable.init();

        vBox.getChildren().addAll(importFragment.getFragmentNode(), tableFragment.getFragmentNode());
    }

    private void showWarningInvalidDateDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Employee Import");
        alert.setHeaderText("There are some invalid employees. Only valid data is imported. Please check again.");
        alert.showAndWait();
    }
}
