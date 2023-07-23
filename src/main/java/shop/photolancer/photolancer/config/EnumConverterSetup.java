package shop.photolancer.photolancer.config;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import shop.photolancer.photolancer.domain.enums.Purpose;

import java.beans.PropertyEditorSupport;

@ControllerAdvice
public class EnumConverterSetup {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Purpose.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(Purpose.valueOf(text.toUpperCase()));
            }
        });
    }
}