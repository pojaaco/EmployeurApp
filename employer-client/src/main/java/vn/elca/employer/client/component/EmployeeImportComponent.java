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
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.ImportCallBack;
import vn.elca.employer.client.callback.SetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.fragment.EmployeeImportFragment;
import vn.elca.employer.client.fragment.EmployeeTableFragment;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.message.MessageType;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.perspective.EmployeePerspective;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DeclarativeView(id = EmployeeImportComponent.ID,
        name = EmployeeImportComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER,
        viewLocation = "/fxml/component/EmployeeImportComponent.fxml")
public class EmployeeImportComponent implements FXComponent {
    private static final String DIALOG_INVALID_DATA_TITLE = "Dialog.Warning.Employee.InvalidData.title";
    private static final String DIALOG_INVALID_DATA_HEADER = "Dialog.Warning.Employee.InvalidData.header";

    public static final String ID = "EmployeeImportComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeImportComponent.class);

    @Autowired
    ObservableResourceFactory observableResourceFactory;

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
            if (message.getTypedMessageBody(MessageType.class).equals(MessageType.RESET)) {
                importFragment.getController().reset();
                tableFragment.getController().resetData();
            }
        } else if (sourceId.endsWith(EmployeeInputComponent.ID)) {
            if (message.getTypedMessageBody(MessageType.class).equals(MessageType.SAVE)) {
                context.send(EmployeePerspective.ID.concat(".").concat(SetCallBack.ID), tableFragment.getController().getData());
            }
        } else if (sourceId.endsWith(ImportCallBack.ID)) {
            List<EmployeeView> uncheckedResults = ((ObservableList<EmployeeView>) message.getMessageBody());
            List<EmployeeView> checkedResults = uncheckedResults.stream()
                    .filter(e -> tableFragment.getController().validateEmployeeView(e))
                    .collect(Collectors.toList());
            if (checkedResults.size() != uncheckedResults.size()) {
                showWarningInvalidDataDialog();
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

    private void showWarningInvalidDataDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(observableResourceFactory.getResources().getString(DIALOG_INVALID_DATA_TITLE));
        alert.setHeaderText(observableResourceFactory.getResources().getString(DIALOG_INVALID_DATA_HEADER));
        alert.showAndWait();
    }
}
