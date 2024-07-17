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
import vn.elca.employer.client.callback.SetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.fragment.EmployeeInputFragment;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;

@DeclarativeView(id = EmployeeInputComponent.ID,
        name = EmployeeInputComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER,
        viewLocation = "/fxml/component/EmployeeInputComponent.fxml")
public class EmployeeInputComponent implements FXComponent {
    public static final String ID = "EmployeeInputComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInputComponent.class);

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
            if (message.getTypedMessageBody(String.class).equals("save")) {
                EmployerView newEmployer = inputFragment.getController().extractEmployerView(true);
                if (newEmployer != null) {
                    context.send(EmployeePerspective.ID.concat(".").concat(SetCallBack.ID), newEmployer);
                    context.send(EmployeePerspective.ID.concat(".").concat(EmployeeImportComponent.ID), "save");
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
        alert.setTitle("Employer Save");
        alert.setHeaderText("Employer " + employer.getNumberIde() + " has been saved.");
        alert.showAndWait();
    }

    private void showNotSavedInfoDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Employer Save");
        alert.setHeaderText("The employer cannot be saved.");
        alert.showAndWait();
    }
}