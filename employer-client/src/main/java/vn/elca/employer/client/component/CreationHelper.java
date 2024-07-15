package vn.elca.employer.client.component;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.client.converter.EnumStringConverterFactory;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.common.ConstantContainer;
import vn.elca.employer.common.Fund;

import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreationHelper {
    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    public final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ConstantContainer.DATE_FORMAT);

    public ComboBox<Fund> createFundComboBox() {
        ComboBox<Fund> fundComboBox = new ComboBox<>();
        List<Fund> fundValue = Arrays.stream(Fund.values())
                .filter(e -> e != Fund.UNRECOGNIZED)
                .collect(Collectors.toList());
        fundComboBox.setMaxWidth(Double.MAX_VALUE);
        fundComboBox.getItems().setAll(fundValue);
        fundComboBox.setValue(Fund.FUND_CANTONAL);
        fundComboBox.setConverter(EnumStringConverterFactory.getConverter(Fund.class, observableResourceFactory));

        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
            fundComboBox.getItems().setAll(fundValue); // Refresh Combo Box
        }));
        return fundComboBox;
    }

    public DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
            datePicker.setChronology(null); // Reset Chronology
            datePicker.setChronology(Chronology.ofLocale(observableResourceFactory.getResources().getLocale()));
        }));
        return datePicker;
    }
}
