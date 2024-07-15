package vn.elca.employer.client.perspective;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.perspective.FXPerspective;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.ImportCallBack;
import vn.elca.employer.client.callback.SetCallBack;
import vn.elca.employer.client.component.EmployeeImportComponent;
import vn.elca.employer.client.component.EmployeeInputComponent;
import vn.elca.employer.client.config.EmployerJacpfxConfig;
import vn.elca.employer.client.factory.ObservableResourceFactory;

import java.util.ResourceBundle;

@Perspective(id = EmployeePerspective.ID,
        name = EmployeePerspective.ID,
        components = {
                EmployeeInputComponent.ID,
                EmployeeImportComponent.ID,
                ImportCallBack.ID,
                SetCallBack.ID
        })
public class EmployeePerspective implements FXPerspective {
    public static final String ID = "EmployeePerspective";

    @Autowired
    ObservableResourceFactory observableResourceFactory;

    @Resource
    Context context;

    private GridPane mainLayout;

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {
        if (message.isMessageBodyTypeOf(String.class)) {
            if (message.getTypedMessageBody(String.class).equals("show")) {
                context.send(EmployeePerspective.ID.concat(".").concat(EmployeeImportComponent.ID), "reset");
            }
        }
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
        mainLayout = createMainLayout();
        mainLayout.setPadding(new Insets(10));

        VBox infoContainer = new VBox();
        mainLayout.add(infoContainer, 0, 0);

        VBox importContainer = new VBox();
        mainLayout.add(importContainer, 0, 1);

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        Button button1 = new Button();
        button1.textProperty().bind(observableResourceFactory.getStringBinding("Button.back"));
        button1.setOnAction(event -> context.send(EmployerPerspective.ID, "show"));
        Button button2 = new Button();
        button2.textProperty().bind(observableResourceFactory.getStringBinding("Button.save"));
        button2.setOnAction(event -> {
            context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInputComponent.ID), "save");
        });
        buttonContainer.getChildren().addAll(button1, button2);
        buttonContainer.setSpacing(20);
        mainLayout.add(buttonContainer, 0, 2);

        perspectiveLayout.registerRootComponent(mainLayout);
        perspectiveLayout.registerTargetLayoutComponent(EmployerJacpfxConfig.TARGET_TOP_CONTAINER, infoContainer);
        perspectiveLayout.registerTargetLayoutComponent(EmployerJacpfxConfig.TARGET_BOTTOM_CONTAINER, importContainer);
    }

    private GridPane createMainLayout() {
        GridPane gridPane = new GridPane();

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setPercentWidth(100);
        gridPane.getColumnConstraints().add(colConstraints);

        RowConstraints row1Constraints = new RowConstraints();
        row1Constraints.setPercentHeight(50);

        RowConstraints row2Constraints = new RowConstraints();
        row2Constraints.setPercentHeight(40);

        RowConstraints row3Constraints = new RowConstraints();
        row3Constraints.setPercentHeight(10);

        gridPane.getRowConstraints().addAll(row1Constraints, row2Constraints, row3Constraints);

        return gridPane;
    }
}
