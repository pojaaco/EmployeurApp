package vn.elca.frontend.perspective;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.perspective.FXPerspective;
import vn.elca.frontend.config.BasicConfig;

import java.util.ResourceBundle;

@Perspective(id = BasicConfig.PERSPECTIVE_EMPLOYEUR,
        name = "employeurPerspective",
        components = {
                BasicConfig.COMPONENT_EMPLOYEUR_TOP,
                BasicConfig.COMPONENT_EMPLOYEUR_BOTTOM,
                BasicConfig.CALLBACK_SEARCH
        },
        resourceBundleLocation = "bundles.languageBundle")
public class PerspectiveEmployeur implements FXPerspective {

    private GridPane mainLayout;

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {

    }

    @PostConstruct
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout,
                                   final FXComponentLayout componentLayout,
                                   final ResourceBundle resourceBundle) {
        mainLayout = createMainLayout();
        mainLayout.setPadding(new Insets(10));

        VBox infoView = new VBox();
        VBox listView = new VBox();

        mainLayout.add(infoView, 0, 0);
        mainLayout.add(listView, 0, 1);

        perspectiveLayout.registerRootComponent(mainLayout);
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_TOP, infoView);
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_BOTTOM, listView);
    }

    private GridPane createMainLayout() {
        GridPane gridPane = new GridPane();

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setPercentWidth(100);
        gridPane.getColumnConstraints().add(colConstraints);

        RowConstraints row1Constraints = new RowConstraints();
        row1Constraints.setPercentHeight(60);

        RowConstraints row2Constraints = new RowConstraints();
        row2Constraints.setPercentHeight(40);

        gridPane.getRowConstraints().addAll(row1Constraints, row2Constraints);

        return gridPane;
    }
}
