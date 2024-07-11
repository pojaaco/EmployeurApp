package vn.elca.frontend.callback;

import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.common.EmployeurSearchRequest;
import vn.elca.frontend.config.BasicConfig;
import vn.elca.frontend.model.stub.EmployeurServiceGrpcStub;

@Component(id = BasicConfig.CALLBACK_SEARCH,
        name = "searchCallback",
        active = true,
        localeID = "en_US",
        resourceBundleLocation = "bundles.languageBundle")
public class CallBackSearch implements CallbackComponent {
    @Resource
    private Context context;

    @Autowired
    private EmployeurServiceGrpcStub stub;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            context.setReturnTarget(BasicConfig.PERSPECTIVE_EMPLOYEUR.concat(".").concat(BasicConfig.COMPONENT_EMPLOYEUR_BOTTOM));
            return stub.search(message.getTypedMessageBody(EmployeurSearchRequest.class));
        }
        return null;
    }
}
