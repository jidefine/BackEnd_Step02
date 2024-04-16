package org.zerock.springex.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import jdk.vm.ci.meta.Local;
import org.springframework.format.Formatter;

public class CheckboxFormatter  implements Formatter<Boolean> {

    @Override
    public Boolean parse(String text, Locale locale) throws ParseException {
        if (text == null) {
            return false;
        }
        return text.equals("on");
    }

    @Override
    public String print(Boolean object, Locale locale){
        return object.toString();
    }

}
