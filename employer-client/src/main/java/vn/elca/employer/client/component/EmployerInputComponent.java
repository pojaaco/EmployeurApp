package vn.elca.employer.client.component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import vn.elca.employer.client.fragment.EmployerInputFragment;
import vn.elca.employer.client.config.EmployerJacpfxConfig;

@DeclarativeView(id = EmployerInputComponent.ID,
        name = EmployerInputComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER,
        viewLocation = "/fxml/component/EmployerInputComponent.fxml")
public class EmployerInputComponent implements FXComponent {
    public static final String ID = "EmployerInputComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerInputComponent.class);

    @Resource
    private Context context;

    @FXML
    private VBox vBox;

    private ManagedFragmentHandler<EmployerInputFragment> inputFragment;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return null;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        inputFragment = context.getManagedFragmentHandler(EmployerInputFragment.class);
        final EmployerInputFragment controllerInput = inputFragment.getController();
        controllerInput.init();

        vBox.getChildren().addAll(inputFragment.getFragmentNode());
    }
}