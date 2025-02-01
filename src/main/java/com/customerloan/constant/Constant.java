package com.customerloan.constant;

public final class Constant {
	public static final int INT_ZERO = 0;
	public static final int INT_ONE = 1;
	public static final int INT_TWO = 2;
	public static final int INT_THREE = 3;
	public final class EntityName {
		public static final String CUSTOMER_ENTITY= "CUSTOMER";
		public static final String LOAN_ENTITY= "LOAN";
		public static final String LOAN_INSTALLMENT_ENTITY="LOAN_INSTALLMENT";
	}
	
	public final class SequenceName {
		public static final String CUSTOMER_ENTITY_SEQUENCE= "CUSTOMER_SEQ";
		public static final String LOAN_ENTITY_SEQUENCE= "LOAN_SEQ";
		public static final String LOAN_INSTALLMENT_ENTITY_SEQUENCE="LOAN_INSTALLMENT_SEQ";
	}
	
	public final class CustomerEntityFieldName {
		public static final String ID= "ID";
		public static final String NAME= "NAME";
		public static final String SURNAME= "SURNAME";
		public static final String CREDIT_LIMIT= "CREDIT_LIMIT";
		public static final String USED_CREDIT_LIMIT= "USED_CREDIT_LIMIT";
	}
	
	public final class LoanEntityFieldName {
		public static final String CUSTOMER_ID= "CUSTOMER_ID";
		public static final String LOAN_AMOUNT= "LOAN_AMOUNT";
		public static final String NUMBER_OF_INSTALLMENT= "NUMBER_OF_INSTALLMENT";
		public static final String CREATE_DATE= "CREATE_DATE";
		public static final String IS_PAID= "IS_PAID";
	}
	
	public final class LoanInstallmentEntityFieldName {
		public static final String LOAN_ID= "LOAN_ID";
		public static final String AMOUNT= "AMOUNT";
		public static final String PAID_AMOUNT= "PAID_AMOUNT";
		public static final String DUE_DATE= "DUE_DATE";
		public static final String PAYMENT_DATE= "PAYMENT_DATE";
		public static final String IS_PAID= "IS_PAID";
	}
	
	
}