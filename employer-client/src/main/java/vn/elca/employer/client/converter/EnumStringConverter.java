package vn.elca.employer.client.converter;

import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import vn.elca.employer.client.factory.ObservableResourceFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EnumStringConverter<T extends Enum<T>> extends StringConverter<T> {
    private final Class<T> enumType;
    private ResourceBundle resourceBundle;
    private Map<String, String> reverseLookupMap; // value to key

    public EnumStringConverter(Class<T> enumType, ObservableResourceFactory resourceFactory) {
        this.enumType = enumType;
        resourceFactory.addConverter(this);
        initializeReverseLookupMap(resourceFactory.getResources());
    }

    public void initializeReverseLookupMap(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;

        String enumKey = "Enum." + StringUtils.capitalize(StringUtils.substringAfterLast(enumType.getName(), "."));
        reverseLookupMap = resourceBundle.keySet().stream()
                .filter(key -> key.contains(enumKey))
                .collect(Collectors.toMap(resourceBundle::getString, key -> key));
    }

    @Override
    public String toString(T object) {
        if (object == null) {
            return "";
        }

        String enumName = Arrays.stream(object.toString().toLowerCase().split("_"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining("."));
        return resourceBundle.getString("Enum." + enumName);
    }

    @Override
    public T fromString(String string) {
        try {
            String enumKey = reverseLookupMap.get(string)
                    .substring(5)
                    .toUpperCase()
                    .replace(".", "_");
            return Enum.valueOf(enumType, enumKey);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
