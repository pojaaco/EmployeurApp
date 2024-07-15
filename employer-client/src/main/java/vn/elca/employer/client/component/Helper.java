package vn.elca.employer.client.component;

import javafx.scene.control.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.client.converter.EnumStringConverterFactory;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.common.Fund;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class Helper {
    @Autowired
    ObservableResourceFactory observableResourceFactory;

    public ComboBox<Fund> createFundComboBox() {
        ComboBox<Fund> fundComboBox = new ComboBox<>();
        fundComboBox.setMaxWidth(Double.MAX_VALUE);
        fundComboBox.getItems().addAll(
                Arrays.stream(Fund.values())
                        .filter(e -> e != Fund.UNRECOGNIZED)
                        .collect(Collectors.toList())
        );
        fundComboBox.setValue(Fund.FUND_CANTONAL);
        fundComboBox.setConverter(EnumStringConverterFactory.getConverter(Fund.class, observableResourceFactory));
        return fundComboBox;
    }
}
