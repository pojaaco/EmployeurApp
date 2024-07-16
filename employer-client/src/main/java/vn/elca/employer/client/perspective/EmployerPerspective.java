package vn.elca.employer.client.perspective;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.api.util.ToolbarPosition;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.components.toolBar.JACPToolBar;
import org.jacpfx.rcp.perspective.FXPerspective;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.callback.GetCallBack;
import vn.elca.employer.client.component.CreationHelper;
import vn.elca.employer.client.component.EmployerInputComponent;
import vn.elca.employer.client.component.EmployerTableComponent;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.factory.ObservableResourceFactory;

import java.util.ResourceBundle;

@Perspective(id = EmployerPerspective.ID,
        name = EmployerPerspective.ID,
        components = {
                EmployerInputComponent.ID,
                EmployerTableComponent.ID,
                GetCallBack.ID,
                DeleteCallBack.ID
        })
public class EmployerPerspective implements FXPerspective {
    public static final String ID = "EmployerPerspective";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private CreationHelper creationHelper;

    private GridPane mainLayout;

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {
    }

    @PostConstruct
    /**
     * @PostConstruct annotated method will be executed when component is activated.
     * @param perspectiveLayout , the perspective layout let you register target layouts
     * @param layout, the component layout contains references to the toolbar and the menu
     * @param resourceBundle
     */
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout componentLayout,
                                   final ResourceBundle resourceBundle) {
        JACPToolBar toolBar = componentLayout.getRegisteredToolBar(ToolbarPosition.NORTH);
        creationHelper.addLanguageSwitcher(toolBar);

        mainLayout = createMainLayout();
        mainLayout.setPadding(new Insets(5));

        VBox infoContainer = new VBox();
        mainLayout.add(infoContainer, 0, 0);

        VBox resultContainer = new VBox();
        mainLayout.add(resultContainer, 0, 1);

        perspectiveLayout.registerRootComponent(mainLayout);
        perspectiveLayout.registerTargetLayoutComponent(EmployerJacpfxConfig.TARGET_TOP_CONTAINER, infoContainer);
        perspectiveLayout.registerTargetLayoutComponent(EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER, resultContainer);
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
