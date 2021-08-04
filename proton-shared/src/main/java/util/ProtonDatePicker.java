package util;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.util.Arrays;

public class ProtonDatePicker extends DatePicker {

    public ProtonDatePicker() {
	super();
	this.setI18n(new DatePicker.DatePickerI18n().setWeek("неделя").setCalendar("календарь").setClear("очистить")
		.setToday("Cегодня").setCancel("Закрыть").setFirstDayOfWeek(1)
		.setMonthNames(Arrays.asList("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
			"Сентябрь", "Октябрь", "Ноябрь", "Декабрь"))
		.setWeekdays(Arrays.asList("Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница",
			"Суббота"))
		.setWeekdaysShort(Arrays.asList("Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб")));

    }
    
    public ProtonDatePicker(String label) {
        this();
        setLabel(label);
    }
    

}
