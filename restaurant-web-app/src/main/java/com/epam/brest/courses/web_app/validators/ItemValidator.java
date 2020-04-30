package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.util.StringUtils;

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME_SIZE;

/**
 * This class validate item objects.
 */
@Component
public class ItemValidator implements Validator {

    /**
     * Determines if the validator supports this class.
     * @param clazz Item.class
     * @return true if support Item.class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.equals(clazz);
    }

    /**
     * This method checks the following conditions:
     * - name length limit;
     * - positive value of price;
     *
     * @param target the object that is to be validated.
     * @param errors stores and exposes information about data-binding
     *               and validation errors for a specific object.
     */
    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "itemName", "itemName.empty");
        Item item = (Item) target;

        if( StringUtils.hasLength(item.getItemName())
            && item.getItemName().length() > ITEM_NAME_SIZE) {
            errors.rejectValue("itemName", "itemName.maxSize");
        }

        if(item.getItemPrice().intValue() < 0){
            errors.rejectValue("itemPrice", "lessThanZero");
        }
    }
}
