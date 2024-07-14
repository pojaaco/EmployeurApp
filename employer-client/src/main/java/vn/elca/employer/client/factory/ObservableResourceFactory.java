package vn.elca.employer.client.factory;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import vn.elca.employer.client.component.importer.EmployeeImporter;
import vn.elca.employer.client.converter.EnumStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ObservableResourceFactory {
    public static final String RESOURCE_BUNDLE_NAME = "bundles/languageBundle";
    private final ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();
    private final List<EnumStringConverter> converters = new ArrayList<>();
    private final List<EmployeeImporter> importers = new ArrayList<>();

    public void addConverter(EnumStringConverter converter) {
        converters.add(converter);
    }

    public void addImporter(EmployeeImporter importer) {
        importers.add(importer);
    }

    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    public final void switchResourceByLanguage(Language language) {
        resourcesProperty().set(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, language.getLocale()));
        converters.forEach(e -> e.initializeReverseLookupMap(getResources()));
        importers.forEach(e -> e.initializeReverseLookupMap(getResources()));
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
