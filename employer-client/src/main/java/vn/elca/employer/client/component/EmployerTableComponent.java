package vn.elca.employer.client.component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.callback.GetCallBack;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.fragment.EmployerTableFragment;
import vn.elca.employer.client.model.view.EmployerView;

import java.util.List;

@DeclarativeView(id = EmployerTableComponent.ID,
        name = EmployerTableComponent.ID,
        initialTargetLayoutId = EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER,
        viewLocation = "/fxml/component/EmployerTableComponent.fxml")
public class EmployerTableComponent extends AbstractComponent {
    public static final String ID = "EmployerTableComponent";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerTableComponent.class);

    @Resource
    private Context context;

    @FXML
    private VBox vBox;

    private EmployerTableFragment tableFragment;

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        String sourceId = message.getSourceId();
        if (sourceId.endsWith(GetCallBack.ID)) {
            tableFragment.updateData((List<EmployerView>) message.getMessageBody());
        } else if (sourceId.endsWith(DeleteCallBack.ID)) {
            if (message.isMessageBodyTypeOf(EmployerView.class)) {
                tableFragment.removeItem(message.getTypedMessageBody(EmployerView.class));
            }
        }
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        Pair<EmployerTableFragment, Node> tablePair = registerFragment(context, EmployerTableFragment.class);
        tableFragment = tablePair.getKey();

        vBox.getChildren().add(tablePair.getValue());
    }
}