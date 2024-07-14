package vn.elca.employer.client.component;

import javafx.scene.control.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.client.converter.EnumStringConverter;
import vn.elca.employer.common.Fund;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class Helper {
    @Autowired
    private EnumStringConverter<Fund> fundConverter;

    public ComboBox<String> createFundComboBox() {
        ComboBox<String> fundComboBox = new ComboBox<>();
        fundComboBox.setMaxWidth(Double.MAX_VALUE);
        fundComboBox.getItems().addAll(
                Arrays.stream(Fund.values())
                        .filter(e -> e != Fund.UNRECOGNIZED)
                        .map(fundConverter::toString)
                        .collect(Collectors.toList())
        );
        fundComboBox.setValue(fundConverter.toString(Fund.FUND_CANTONAL));
        return fundComboBox;
    }
}
