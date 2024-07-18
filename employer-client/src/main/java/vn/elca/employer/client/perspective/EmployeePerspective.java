package vn.elca.employer.client.perspective;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.api.util.ToolbarPosition;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.components.toolBar.JACPToolBar;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.perspective.FXPerspective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.ImportCallBack;
import vn.elca.employer.client.callback.SetCallBack;
import vn.elca.employer.client.component.CreationHelper;
import vn.elca.employer.client.component.EmployeeImportComponent;
import vn.elca.employer.client.component.EmployeeInputComponent;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.message.MessageType;

import java.util.ResourceBundle;

@Perspective(id = EmployeePerspective.ID,
        name = EmployeePerspective.ID,
        components = {
                EmployeeInputComponent.ID,
                EmployeeImportComponent.ID,
                ImportCallBack.ID,
                SetCallBack.ID
        },
        viewLocation = "/fxml/perspective/EmployeePerspective.fxml")
public class EmployeePerspective implements FXPerspective {
    public static final String ID = "EmployeePerspective";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePerspective.class);
    private static final String BUNDLE_BUTTON_BACK = "Button.back";
    private static final String BUNDLE_BUTTON_SAVE = "Button.save";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private CreationHelper creationHelper;

    @Resource
    private Context context;

    @FXML
    private GridPane mainLayout;

    @FXML
    private VBox vbInputContainer;

    @FXML
    private VBox vbImportContainer;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {
        if (message.isMessageBodyTypeOf(MessageType.class)) {
            if (message.getTypedMessageBody(MessageType.class).equals(MessageType.SHOW)) {
                context.send(EmployeePerspective.ID.concat(".").concat(EmployeeImportComponent.ID), MessageType.RESET);
            }
        }
    }

    @PostConstruct
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout componentLayout) {
        JACPToolBar toolBar = componentLayout.getRegisteredToolBar(ToolbarPosition.NORTH);
        creationHelper.addLanguageSwitcher(toolBar);

        bindLanguage();
        setupEventHandlers();

        perspectiveLayout.registerRootComponent(mainLayout);
        perspectiveLayout.registerTargetLayoutComponent(EmployerJacpfxConfig.TARGET_TOP_CONTAINER, vbInputContainer);
        perspectiveLayout.registerTargetLayoutComponent(EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER, vbImportContainer);

        LOGGER.debug("Init");
    }

    private void bindLanguage() {
        btnBack.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_BUTTON_BACK));
        btnSave.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_BUTTON_SAVE));
    }

    private void setupEventHandlers() {
        btnBack.setOnAction(this::handleBackButton);
        btnSave.setOnAction(this::handleSaveButton);
    }

    private void handleBackButton(ActionEvent event) {
        context.send(EmployerPerspective.ID, MessageType.SHOW);
    }

    private void handleSaveButton(ActionEvent event) {
        context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInputComponent.ID), MessageType.SAVE);
    }
}
