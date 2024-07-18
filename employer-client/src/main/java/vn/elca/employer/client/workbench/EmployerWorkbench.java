package vn.elca.employer.client.workbench;

import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jacpfx.api.annotations.workbench.Workbench;
import org.jacpfx.api.componentLayout.WorkbenchLayout;
import org.jacpfx.api.message.Message;
import org.jacpfx.api.util.ToolbarPosition;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;

@Workbench(id = EmployerWorkbench.ID,
        name = EmployerWorkbench.ID,
        perspectives = {
                EmployeePerspective.ID,
                EmployerPerspective.ID
        })
public class EmployerWorkbench implements FXWorkbench {
    public static final String ID = "EmployerWorkbench";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerWorkbench.class);

    @Override
    public void postHandle(FXComponentLayout fxComponentLayout) {
    }

    @Override
    public void handleInitialLayout(Message<Event, Object> message, WorkbenchLayout<Node> workbenchLayout, Stage stage) {
        Resolution resolution = resolutionByPrimaryScreenBounds();
        workbenchLayout.setWorkbenchXYSize(resolution.width(), resolution.height());
        workbenchLayout.registerToolBar(ToolbarPosition.NORTH);
        workbenchLayout.setStyle(StageStyle.DECORATED);
        workbenchLayout.setMenuEnabled(false);
        stage.setMaximized(true);
        stage.setTitle("Employer Management");
        stage.getIcons().add(new Image("images/icon.png"));
    }

    private Resolution resolutionByPrimaryScreenBounds() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        LOGGER.debug("Screen size: " + primScreenBounds.getMaxX() + "x" + primScreenBounds.getMaxY());
        if (primScreenBounds.getMaxX() >= Resolution.RECOMMENDED.width()) {
            return Resolution.RECOMMENDED;
        }
        return Resolution.DEGRADED;
    }

    private enum Resolution {
        RECOMMENDED(1920, 960), DEGRADED(1280, 720);

        private final int width;
        private final int height;

        Resolution(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * @return the width
         */
        public int width() {
            return width;
        }

        /**
         * @return the height
         */
        public int height() {
            return height;
        }
    }
}
