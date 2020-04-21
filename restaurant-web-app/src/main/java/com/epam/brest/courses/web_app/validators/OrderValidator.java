package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.model.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;

@Component
public class OrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.equals(clazz);
    }

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
