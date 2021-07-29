package com.mtbs.exceptionhandler;


public enum ErrorResponseCodes 
{
	  MAX_SEATS("001", "Maximum 6 seats can be blocked"),
	  MAX_SEATS_SHOW("002", "Maximum 6 seats can be blocked per show"),
	  INVALID_SEAT("003", "Invalid seat selection"),
	  DUPLICATE_SESSION("004", "Duplicate session: user needs to wait for 2 minutes"),
	  BLOCKED_SEAT("005", "Requested seats are blocked"),
	  USER_EXISTS("006", "This user already exists"),
	  SERVER_ERROR("007", "Unexpected server error"),
	  PAYMENT_FAILED("008", "Payment failed. Try after sometime ! (Java Random You can hardcode true - PaymentGatewayCall.class)"),
	  SUCCESS("009","Success"),
	  SEATS_NA("010", "Requested seats are not available"),
	  ERROR_BLOCKING_SEAT("011", "Error blocking seats");
	
	  private final String errorCode;
	  private final String errorMessage;

	  private ErrorResponseCodes(String errorCode, String errorMessage) {
	    this.errorCode = errorCode;
	    this.errorMessage = errorMessage;
	  }

	  public String getErrorMessage() {
	     return errorMessage;
	  }

	  public String getErrorCode() {
	     return errorCode;
	  }

	  @Override
	  public String toString() {
	    return "Error Code: "+errorCode + "| Error Description: " + errorMessage;
	  }
}
