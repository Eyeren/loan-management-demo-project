package com.customerloan.controller.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseInstallmentListInquiryResultByLoan {
    private List<ResponseLoanInstallmentDetail> installmentList;
}