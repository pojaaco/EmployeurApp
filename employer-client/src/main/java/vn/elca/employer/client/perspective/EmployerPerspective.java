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
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.callback.GetCallBack;
import vn.elca.employer.client.component.CreationHelper;
import vn.elca.employer.client.component.EmployeeInputComponent;
import vn.elca.employer.client.component.EmployerInputComponent;
import vn.elca.employer.client.component.EmployerTableComponent;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.message.MessageType;
import vn.elca.employer.client.model.view.EmployerView;

@Perspective(id = EmployerPerspective.ID,
        name = EmployerPerspective.ID,
        components = {
                EmployerInputComponent.ID,
                EmployerTableComponent.ID,
                GetCallBack.ID,
                DeleteCallBack.ID
        },
        viewLocation = "/fxml/perspective/EmployerPerspective.fxml")
public class EmployerPerspective implements FXPerspective {
    public static final String ID = "EmployerPerspective";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerPerspective.class);
    private static final String BUNDLE_BUTTON_ADD = "Button.add";

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
    private Button btnAdd;

    @FXML
    private VBox vbTableContainer;

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {
    }

    @PostConstruct
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout componentLayout) {
        JACPToolBar toolBar = componentLayout.getRegisteredToolBar(ToolbarPosition.NORTH);
        creationHelper.addLanguageSwitcher(toolBar);

        bindLanguage();
        setupEventHandlers();

        perspectiveLayout.registerRootComponent(mainLayout);
        perspectiveLayout.registerTargetLayoutComponent(EmployerJacpfxConfig.TARGET_TOP_CONTAINER, vbInputContainer);
        perspectiveLayout.registerTargetLayoutComponent(EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER, vbTableContainer);

        LOGGER.debug("Init");
    }

    private void bindLanguage() {
        btnAdd.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_BUTTON_ADD));
    }

    private void setupEventHandlers() {
        btnAdd.setOnAction(this::handleAddButton);
    }

    private void handleAddButton(ActionEvent actionEvent) {
        context.send(EmployeePerspective.ID, MessageType.SHOW);
        context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInputComponent.ID), new EmployerView());
    }
}
