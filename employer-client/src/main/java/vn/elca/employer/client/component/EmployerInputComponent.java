package vn.elca.employer.client.component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.util.Pair;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.elca.employer.client.fragment.EmployerInputFragment;
import vn.elca.employer.client.config.EmployerJacpfxConfig;

@DeclarativeView(id = EmployerInputComponent.ID,
        name = EmployerInputComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_TOP_CONTAINER,
        viewLocation = "/fxml/component/EmployerInputComponent.fxml")
public class EmployerInputComponent extends AbstractComponent {
    public static final String ID = "EmployerInputComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerInputComponent.class);

    @Resource
    private Context context;

    @FXML
    private VBox vBox;

    private EmployerInputFragment inputFragment;

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
        Pair<EmployerInputFragment, Node> inputPair = registerFragment(context, EmployerInputFragment.class);
        inputFragment = inputPair.getKey();

        vBox.getChildren().addAll(inputPair.getValue());
    }
}