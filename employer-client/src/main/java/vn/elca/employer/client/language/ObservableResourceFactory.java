package vn.elca.employer.client.language;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;
import java.util.ResourceBundle;

public class ObservableResourceFactory {
    public static final String RESOURCE_BUNDLE_NAME = "bundles/languageBundle";
    private final ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    public final void switchResourceByLanguage(Language language) {
        resourcesProperty().set(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, language.getLocale()));
        Locale.setDefault(Locale.Category.FORMAT, language.getLocale());
    }

    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }

            @Override
            public String computeValue() {
                return getResources().getString(key);
            }
        };
    }

    public enum Language {
        EN(Locale.ENGLISH), FR(Locale.FRENCH);
        private final Locale locale;

        Language(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }
}
