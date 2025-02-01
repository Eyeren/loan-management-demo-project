package com.customerloan.controller.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponsePaymentStatus {
    private boolean paymentCompleted;
    private String paymentStatusDescription;
    private Integer paidInstallmentCount;
    private BigDecimal totalAmountSpent;
    private boolean allInstallmentsPaidCompletely;
}