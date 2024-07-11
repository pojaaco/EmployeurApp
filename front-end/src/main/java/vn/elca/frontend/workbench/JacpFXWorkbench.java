package vn.elca.frontend.workbench;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jacpfx.api.annotations.workbench.Workbench;
import org.jacpfx.api.componentLayout.WorkbenchLayout;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.workbench.FXWorkbench;
import vn.elca.frontend.config.BasicConfig;

@Workbench(id = BasicConfig.WORKBENCH,
        name = "workbench",
        perspectives = {
                BasicConfig.PERSPECTIVE_EMPLOYEUR
        })
public class JacpFXWorkbench implements FXWorkbench {

    @Override
    public void handleInitialLayout(Message<Event, Object> message, WorkbenchLayout<Node> workbenchLayout, Stage stage) {
        workbenchLayout.setWorkbenchXYSize(600, 400);
        workbenchLayout.setStyle(StageStyle.DECORATED);
    }

    @Override
    public void postHandle(FXComponentLayout componentLayout) {

    }
}
