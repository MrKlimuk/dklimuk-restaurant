package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.model.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;

/**
 * This class validate order objects.
 */
@Component
public class OrderValidator implements Validator {

    /**
     * Determines if the validator supports this class.
     * @param clazz Order.class
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
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "orderName", "orderName.empty");
        Order order = (Order) target;

        if( StringUtils.hasLength(order.getOrderName())
                && order.getOrderName().length() > ORDER_NAME_SIZE) {
            errors.rejectValue("orderName", "orderName.maxSize");
        }

        if(order.getOrderPrice() != null
                && order.getOrderPrice().intValue() < 0){
            errors.rejectValue("orderPrice", "orderPrice.lessThanZero");
        }
    }
}
