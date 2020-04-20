package com.epam.brest.courses.web_app.validator;

import com.epam.brest.courses.model.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.util.StringUtils;

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME_SIZE;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "itemName", "itemName.empty");
        Item item = (Item) target;

        if( StringUtils.hasLength(item.getItemName())
            && item.getItemName().length() > ITEM_NAME_SIZE) {
            errors.rejectValue("itemName", "itemName.maxSize");
        }


    }
}
