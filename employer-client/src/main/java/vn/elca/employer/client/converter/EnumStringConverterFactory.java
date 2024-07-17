package vn.elca.employer.client.converter;

import vn.elca.employer.client.language.ObservableResourceFactory;

import java.util.HashMap;
import java.util.Map;

public class EnumStringConverterFactory {
    private static final Map<String, EnumStringConverter> converterCache = new HashMap<>();

    public static <T extends Enum<T>> EnumStringConverter getConverter(Class<T> enumType, ObservableResourceFactory resourceFactory) {
        if (converterCache.containsKey(enumType.getName())) {
            return converterCache.get(enumType.getName());
        } else {
            EnumStringConverter<T> converter = new EnumStringConverter<>(enumType, resourceFactory);
            converterCache.put(enumType.getName(), converter);
            return converter;
        }
    }
}
