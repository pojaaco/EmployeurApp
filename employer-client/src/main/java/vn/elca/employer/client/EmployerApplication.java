package vn.elca.employer.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.jacpfx.spring.launcher.AFXSpringJavaConfigLauncher;
import vn.elca.employer.client.config.EmployerApplicationConfig;
import vn.elca.employer.client.workbench.EmployerWorkbench;

public class EmployerApplication extends AFXSpringJavaConfigLauncher {
    // TODO: Add Unit Test + Logger
    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class<?>[]{EmployerApplicationConfig.class};
    }

    @Override
    protected Class<? extends FXWorkbench> getWorkbenchClass() {
        return EmployerWorkbench.class;
    }

    @Override
    protected String[] getBasePackages() {
        return new String[]{"vn.elca.employer.client"};
    }

    @Override
    protected void postInit(Stage stage) {
        Scene scene = stage.getScene();
        scene.getStylesheets().addAll(
                EmployerApplication.class
                        .getResource("/styles/default.css")
                        .toExternalForm());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
