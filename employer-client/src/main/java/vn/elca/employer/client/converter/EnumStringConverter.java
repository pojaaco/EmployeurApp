package vn.elca.employer.client.converter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import vn.elca.employer.client.factory.ObservableResourceFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EnumStringConverter<T extends Enum<T>> extends StringConverter<T> {
    private final Class<T> enumType;
    private final ObjectProperty<ResourceBundle> resourceBundle = new SimpleObjectProperty<>();
    private Map<String, String> reverseLookupMap; // value to key

    public EnumStringConverter(Class<T> enumType, ObservableResourceFactory resourceFactory) {
        this.enumType = enumType;
        this.resourceBundle.bind(resourceFactory.resourcesProperty());
        this.resourceBundle.addListener(((observable, oldValue, newValue) -> updateReverseLookupMap()));
    }

    private void updateReverseLookupMap() {
        String enumKey = "Enum." + StringUtils.substringAfterLast(enumType.getName(), ".");
        reverseLookupMap = resourceBundle.get().keySet().stream()
                .filter(key -> key.contains(enumKey))
                .collect(Collectors.toMap(key -> resourceBundle.get().getString(key), key -> key));
    }

    @Override
    public String toString(T object) {
        if (object == null) {
            return "";
        }

        String enumName = Arrays.stream(object.toString().toLowerCase().split("_"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining("."));
        return resourceBundle.get().getString("Enum." + enumName);
    }

    @Override
    public T fromString(String string) {
        try {
            String enumKey = reverseLookupMap.get(string)
                    .substring(5) // Remove prefix Enum.
                    .toUpperCase()
                    .replace(".", "_");
            return Enum.valueOf(enumType, enumKey);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
