package vn.elca.frontend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BasicConfig {
    public static final String WORKBENCH = "id01";

    public static final String PERSPECTIVE_EMPLOYEUR = "idPone";
    public static final String COMPONENT_EMPLOYEUR_TOP = "id11";
    public static final String COMPONENT_EMPLOYEUR_BOTTOM = "id12";

    public static final String PERSPECTIVE_EMPLOYEE = "idPtwo";
    public static final String COMPONENT_EMPLOYEE_TOP = "id21";
    public static final String COMPONENT_EMPLOYEE_BOTTOM = "id22";

    public static final String TARGET_CONTAINER_TOP = "PTop";
    public static final String TARGET_CONTAINER_BOTTOM = "PBottom";

    public static final String CALLBACK_SEARCH = "id13";
}
