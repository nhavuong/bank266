package com.example.bank266;

import org.apache.commons.lang3.math.NumberUtils;

// Domain Primitive
public class ValidAmount {

    private Double amount;

    public ValidAmount(String amount) {
        if ((amount == null) || !amount.matches("([1-9][0-9]*|0)\\.[0-9]{2}")) {
            this.amount = null;
        } else {
            this.amount = NumberUtils.toDouble(amount);
        }
    }

    public Double getAmount() {
        return amount;
    }

}
