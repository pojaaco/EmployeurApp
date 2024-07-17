package vn.elca.employer.client.component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
import vn.elca.employer.client.callback.SetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.fragment.EmployeeInputFragment;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.message.MessageType;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;

@DeclarativeView(id = EmployeeInputComponent.ID,
        name = EmployeeInputComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER,
        viewLocation = "/fxml/component/EmployeeInputComponent.fxml")
public class EmployeeInputComponent implements FXComponent {
    private static String DIALOG_NOT_SAVE_TITLE = "Dialog.Warning.Employer.NotSave.title";
    private static String DIALOG_NOT_SAVE_HEADER = "Dialog.Warning.Employer.NotSave.header";
    private static String DIALOG_SAVE_TITLE = "Dialog.Information.Employer.Save.title";
    private static String DIALOG_SAVE_HEADER = "Dialog.Information.Employer.Save.header";

    public static final String ID = "EmployeeInputComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInputComponent.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private VBox vBox;

    private ManagedFragmentHandler<EmployeeInputFragment> inputFragment;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        String sourceId = message.getSourceId();
        if (sourceId.endsWith(EmployerPerspective.ID) || sourceId.endsWith(EmployerTableComponent.ID)) { // Add or Details
            inputFragment.getController().resetError();
            inputFragment.getController().updateInputFields(message.getTypedMessageBody(EmployerView.class));
        } else if (sourceId.endsWith(EmployeePerspective.ID)) { // Save
            if (message.getTypedMessageBody(MessageType.class).equals(MessageType.SAVE)) {
                EmployerView newEmployer = inputFragment.getController().extractEmployerView(true);
                if (newEmployer != null) {
                    context.send(EmployeePerspective.ID.concat(".").concat(SetCallBack.ID), newEmployer);
                    context.send(EmployeePerspective.ID.concat(".").concat(EmployeeImportComponent.ID), MessageType.SAVE);
                }
            }
        } else if (sourceId.endsWith(SetCallBack.ID)) { // Result of saving
            if (message.getMessageBody() != null) {
                EmployerView newEmployer = message.getTypedMessageBody(EmployerView.class);
                showSavedInfoDialog(newEmployer);
            } else {
                showNotSavedInfoDialog();
            }
        }
        return null;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        inputFragment = context.getManagedFragmentHandler(EmployeeInputFragment.class);
        final EmployeeInputFragment controllerInput = inputFragment.getController();
        controllerInput.init();

        vBox.getChildren().add(inputFragment.getFragmentNode());
    }

    private void showSavedInfoDialog(EmployerView employer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(observableResourceFactory.getResources().getString(DIALOG_SAVE_TITLE));
        alert.setHeaderText(String.format(observableResourceFactory.getResources().getString(DIALOG_SAVE_HEADER), employer.getNumberIde()));
        alert.showAndWait();
    }

    private void showNotSavedInfoDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(observableResourceFactory.getResources().getString(DIALOG_NOT_SAVE_TITLE));
        alert.setHeaderText(observableResourceFactory.getResources().getString(DIALOG_NOT_SAVE_HEADER));
        alert.showAndWait();
    }
}