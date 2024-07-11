package vn.elca.frontend;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.jacpfx.spring.launcher.AFXSpringJavaConfigLauncher;
import vn.elca.frontend.config.BasicConfig;
import vn.elca.frontend.config.GrpcConfig;
import vn.elca.frontend.workbench.JacpFXWorkbench;

public class ClientLauncher extends AFXSpringJavaConfigLauncher {

    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class<?>[]{BasicConfig.class, GrpcConfig.class};
    }

    @Override
    protected Class<? extends FXWorkbench> getWorkbenchClass() {
        return JacpFXWorkbench.class;
    }

    @Override
    protected String[] getBasePackages() {
        return new String[]{"vn.elca.frontend"};
    }

    @Override
    protected void postInit(Stage stage) {

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
