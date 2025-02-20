### Failed Test: `EWrapperMsgGenerator_scannerData_29_1_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_codestral-latest/tullibee/com/ib/client/failedtests/EWrapperMsgGenerator_scannerData_29_1_Test.java`

```java
--- 
+++ 
 
     @Mock
     private Contract contract;
+
// BEGIN DIFF
   @BeforeEach
   public void setUp() {
       when(contractDetails.m_summary).thenReturn(contract);
       when(contract.m_symbol).thenReturn("AAPL");
       when(contract.m_secType).thenReturn("STK");
       when(contract.m_expiry).thenReturn("20231231");
       when(contract.m_strike).thenReturn(150.0);
       when(contract.m_right).thenReturn("C");
       when(contract.m_exchange).thenReturn("SMART");
       when(contract.m_currency).thenReturn("USD");
       when(contract.m_localSymbol).thenReturn("AAPL");
       when(contractDetails.m_marketName).thenReturn("US Stocks");
       when(contractDetails.m_tradingClass).thenReturn("AAPL");
   }
// END DIFF
 
     @Test
     public void testScannerData() {
```

### Failed Test: `EWrapperMsgGenerator_fundamentalData_32_0_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_codestral-latest/tullibee/com/ib/client/failedtests/EWrapperMsgGenerator_fundamentalData_32_0_Test.java`

```java
--- 
+++ 
         String result = EWrapperMsgGenerator.fundamentalData(reqId, data);
         assertEquals(expected, result);
     }
+
// BEGIN DIFF
   @Test
   public void testFundamentalDataWithNullData() {
       int reqId = 3;
       String data = null;
       String expected = "id  = 3 len = 4\nnull";
       String result = EWrapperMsgGenerator.fundamentalData(reqId, data);
       assertEquals(expected, result);
   }
// END DIFF
 }
```

### Failed Test: `EWrapperMsgGenerator_receiveFA_25_0_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_gemini-1.5-flash-8b/tullibee/com/ib/client/failedtests/EWrapperMsgGenerator_receiveFA_25_0_Test.java`

```java
--- 
+++ 
         assertEquals(expectedOutput, actualOutput);
     }
 
// BEGIN DIFF
   // Crucial: Mock EClientSocket.faMsgTypeName to avoid external dependency issues
   @Test
   void testReceiveFA_MockedDataType() {
       String xmlData = "<xmlData />";
       int dataType = 4;
       String mockedMsgTypeName = "MOCKED_TYPE_NAME";
       EClientSocket mockEClientSocket = Mockito.mock(EClientSocket.class);
       Mockito.when(mockEClientSocket.faMsgTypeName(dataType)).thenReturn(mockedMsgTypeName);
       String expectedOutput = EWrapperMsgGenerator.FINANCIAL_ADVISOR + " " + mockedMsgTypeName + " " + xmlData;
       String actualOutput = EWrapperMsgGenerator.receiveFA(dataType, xmlData);
       assertEquals(expectedOutput, actualOutput);
   }
// END DIFF
+
     // Additional test for edge case.
     @Test
     void testReceiveFA_LargeDataType() {
```

### Failed Test: `EWrapperMsgGenerator_scannerData_29_1_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_gemini-1.5-flash-8b/tullibee/com/ib/client/failedtests/EWrapperMsgGenerator_scannerData_29_1_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testScannerData() {
       // Arrange
       when(eWrapperMsgGenerator.scannerData(0, 1, contractDetails, "SPY", "STK", "2024-07-02", "CALL")).thenReturn("Expected Output");
       String actualString = eWrapperMsgGeneratorImpl.scannerData(0, 1, contractDetails, "SPY", "STK", "2024-07-02", "CALL");
       // Assert
       assertEquals("Expected Output", actualString);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testScannerData_emptyContract() {
       // Arrange
       Contract emptyContract = new Contract();
       emptyContract.m_symbol = "";
       emptyContract.m_secType = "";
       emptyContract.m_expiry = "";
       emptyContract.m_strike = 0.0;
       emptyContract.m_right = "";
       emptyContract.m_exchange = "";
       emptyContract.m_currency = "";
       emptyContract.m_localSymbol = "";
       ContractDetails emptyContractDetails = new ContractDetails();
       emptyContractDetails.m_summary = emptyContract;
       emptyContractDetails.m_marketName = "";
       emptyContractDetails.m_tradingClass = "";
       when(eWrapperMsgGenerator.scannerData(0, 1, emptyContractDetails, "", "", "", "")).thenReturn("Expected Output for empty contract");
       String actualString = eWrapperMsgGeneratorImpl.scannerData(0, 1, emptyContractDetails, "", "", "", "");
       // Assert
       assertEquals("Expected Output for empty contract", actualString);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void scannerData_validInput_returnsExpectedString() {
         String distance = "10";
         String benchmark = "VIX";
```

### Failed Test: `EWrapperMsgGenerator_fundamentalData_32_0_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_gemini-1.5-flash-8b/tullibee/com/ib/client/failedtests/EWrapperMsgGenerator_fundamentalData_32_0_Test.java`

```java
--- 
+++ 
 class EWrapperMsgGenerator_fundamentalData_32_0_Test {
 
     @Test
// BEGIN DIFF
   void fundamentalData_validInput_returnsCorrectString() {
       int reqId = 123;
       String data = "some fundamental data";
       String expected = "id  = 123 len = 20\n" + data;
       String actual = EWrapperMsgGenerator.fundamentalData(reqId, data);
       assertEquals(expected, actual);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void fundamentalData_emptyData_returnsCorrectString() {
         int reqId = 456;
         String data = "";
         String actual = EWrapperMsgGenerator.fundamentalData(reqId, data);
         assertEquals(expected, actual);
     }
+
// BEGIN DIFF
   @Test
   void fundamentalData_nullData_returnsCorrectString() {
       int reqId = 789;
       String data = null;
       String expected = "id  = 789 len = 0\n";
       // Crucial:  Handle potential NullPointerException
       String actual = EWrapperMsgGenerator.fundamentalData(reqId, data);
       assertEquals(expected, actual);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void fundamentalData_largeData_returnsCorrectString() {
       int reqId = 10;
       String data = "This is a very long string that will test the length calculation.";
       String expected = "id  = 10 len = 67\n" + data;
       String actual = EWrapperMsgGenerator.fundamentalData(reqId, data);
       assertEquals(expected, actual);
   }
// END DIFF
 }
```

### Failed Test: `EWrapperMsgGenerator_contractMsg_16_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-32B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Qwen2.5-Coder-32B-Instruct/tullibee/com/ib/client/failedtests/EWrapperMsgGenerator_contractMsg_16_0_Test.java`

```java
--- 
+++ 
 
     @InjectMocks
     private Contract contract;
+
// BEGIN DIFF
   @BeforeEach
   void setUp() {
       when(mockContract.m_conId).thenReturn(12345);
       when(mockContract.m_symbol).thenReturn("AAPL");
       when(mockContract.m_secType).thenReturn("STK");
       when(mockContract.m_expiry).thenReturn("20231020");
       when(mockContract.m_strike).thenReturn(150.0);
       when(mockContract.m_right).thenReturn("C");
       when(mockContract.m_multiplier).thenReturn("100");
       when(mockContract.m_exchange).thenReturn("NASDAQ");
       when(mockContract.m_primaryExch).thenReturn("ISLAND");
       when(mockContract.m_currency).thenReturn("USD");
       when(mockContract.m_localSymbol).thenReturn("AAPL231020C00150000");
       when(mockContract.m_comboLegs).thenReturn(new Vector<>());
       when(mockContract.m_underComp).thenReturn(null);
   }
// END DIFF
 
     @Test
     void testEqualsSameObject() {
```

### Failed Test: `EWrapperMsgGenerator_execDetails_19_1_Test.java`

**Model:** Qwen/Qwen2.5-Coder-32B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Qwen2.5-Coder-32B-Instruct/tullibee/com/ib/client/failedtests/EWrapperMsgGenerator_execDetails_19_1_Test.java`

```java
--- 
+++ 
     private EWrapperMsgGenerator eWrapperMsgGenerator;
 
     private static final String FIELD_SEP = "\t";
+
// BEGIN DIFF
   @BeforeEach
   public void setUp() throws Exception {
       MockitoAnnotations.openMocks(this);
       eWrapperMsgGenerator = new EWrapperMsgGenerator();
       // Setting up the mock objects with expected values
       when(mockContract.m_symbol).thenReturn("AAPL");
       when(mockContract.m_secType).thenReturn("STK");
       when(mockContract.m_expiry).thenReturn("20231020");
       when(mockContract.m_strike).thenReturn(150.0);
       when(mockContract.m_right).thenReturn("CALL");
       when(mockContract.m_exchange).thenReturn("NASDAQ");
       when(mockContract.m_currency).thenReturn("USD");
       when(mockContract.m_localSymbol).thenReturn("AAPL231020C00150000");
       when(mockExecution.m_orderId).thenReturn(12345);
       when(mockExecution.m_clientId).thenReturn(67890);
       when(mockExecution.m_execId).thenReturn("123456789");
       when(mockExecution.m_time).thenReturn("20231010 10:00:00");
       when(mockExecution.m_acctNumber).thenReturn("U123456789");
       when(mockExecution.m_exchange).thenReturn("SMART");
       when(mockExecution.m_side).thenReturn("BUY");
       when(mockExecution.m_shares).thenReturn(100);
       when(mockExecution.m_price).thenReturn(149.5);
       when(mockExecution.m_permId).thenReturn(98765);
       when(mockExecution.m_liquidation).thenReturn(0);
       when(mockExecution.m_cumQty).thenReturn(100);
       when(mockExecution.m_avgPrice).thenReturn(149.5);
       // Accessing the private FIELD_SEP field from EWrapperMsgGenerator
       Field field = EWrapperMsgGenerator.class.getDeclaredField("FIELD_SEP");
       field.setAccessible(true);
       // No need to reassign FIELD_SEP as it's already defined as a static final in the class
   }
// END DIFF
 
     @Test
     public void testExecDetails() {
```

### Failed Test: `EWrapperMsgGenerator_updateAccountValue_9_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-1.5B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Qwen2.5-Coder-1.5B-Instruct/tullibee/com/ib/client/failedtests/EWrapperMsgGenerator_updateAccountValue_9_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testUpdateAccountValueWithValidParameters() {
       // Given
       when(eWrapperMsgGenerator.updateAccountValue("key1", "value1", "USD", "account1")).thenReturn("updateAccountValue: key1 value1 USD account1");
       // When
       String result = eWrapperMsgGenerator.updateAccountValue("key1", "value1", "USD", "account1");
       // Then
       assertEquals("updateAccountValue: key1 value1 USD account1", result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testUpdateAccountValueWithInvalidParameters() {
         // Given
         // When
```

## Source File: `../SF110/1_tullibee/src/main/java/com/ib/client/EWrapperMsgGenerator.java`

```java
// Copyright 2010-2012 Christopher Redekop
//  
// This file is part of the Tullibee API, a modified version of Interactive
// Brokers' Java API (the IB API).
//  
// The Tullibee API is free software: you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or (at your
// option) any later version.
//  
// The Tullibee API is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
// or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
// License for more details.
//  
// You should have received a copy of the GNU Lesser General Public License
// along with the Tullibee API.  If not, see <http://www.gnu.org/licenses/>.

package com.ib.client;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

public class EWrapperMsgGenerator extends AnyWrapperMsgGenerator {
    public static final String SCANNER_PARAMETERS = "SCANNER PARAMETERS:";
    public static final String FINANCIAL_ADVISOR = "FA:";
    
	static public String tickPrice( int tickerId, int field, double price, int canAutoExecute) {
    	return "id=" + tickerId + "  " + TickType.getField( field) + "=" + price + " " + 
        ((canAutoExecute != 0) ? " canAutoExecute" : " noAutoExecute");
    }
	
    static public String tickSize( int tickerId, int field, int size) {
    	return "id=" + tickerId + "  " + TickType.getField( field) + "=" + size;
    }
    
    static public String tickOptionComputation( int tickerId, int field, double impliedVol,
    		double delta, double modelPrice, double pvDividend) {
    	String toAdd = "id=" + tickerId + "  " + TickType.getField( field) +
		   ": vol = " + ((impliedVol >= 0 && impliedVol != Double.MAX_VALUE) ? Double.toString(impliedVol) : "N/A") +
		   " delta = " + ((Math.abs(delta) <= 1) ? Double.toString(delta) : "N/A");
    	if (field == TickType.MODEL_OPTION) {
    		toAdd += ": modelPrice = " + ((modelPrice >= 0 && modelPrice != Double.MAX_VALUE) ? Double.toString(modelPrice) : "N/A");
    		toAdd += ": pvDividend = " + ((pvDividend >= 0 && pvDividend != Double.MAX_VALUE) ? Double.toString(pvDividend) : "N/A");
    	}
		return toAdd;
    }
    
    static public String tickGeneric(int tickerId, int tickType, double value) {
    	return "id=" + tickerId + "  " + TickType.getField( tickType) + "=" + value;
    }
    
    static public String tickString(int tickerId, int tickType, String value) {
    	return "id=" + tickerId + "  " + TickType.getField( tickType) + "=" + value;
    }
    
    static public String tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry) {
    	return "id=" + tickerId + "  " + TickType.getField(tickType)
		+ ": basisPoints = " + basisPoints + "/" + formattedBasisPoints
		+ " impliedFuture = " + impliedFuture + " holdDays = " + holdDays +
		" futureExpiry = " + futureExpiry + " dividendImpact = " + dividendImpact +
		" dividends to expiry = "	+ dividendsToExpiry;
    }
    
    static public String orderStatus( int orderId, String status, int filled, int remaining,
            double avgFillPrice, int permId, int parentId, double lastFillPrice,
            int clientId, String whyHeld) {
    	return "order status: orderId=" + orderId + " clientId=" + clientId + " permId=" + permId +
        " status=" + status + " filled=" + filled + " remaining=" + remaining +
        " avgFillPrice=" + avgFillPrice + " lastFillPrice=" + lastFillPrice +
        " parent Id=" + parentId + " whyHeld=" + whyHeld;
    }
    
    static public String openOrder( int orderId, Contract contract, Order order, OrderState orderState) {
        String msg = "open order: orderId=" + orderId +
        " action=" + order.m_action +
        " quantity=" + order.m_totalQuantity +
        " symbol=" + contract.m_symbol +
        " exchange=" + contract.m_exchange +
        " secType=" + contract.m_secType +
        " type=" + order.m_orderType +
        " lmtPrice=" + order.m_lmtPrice +
        " auxPrice=" + order.m_auxPrice +
        " TIF=" + order.m_tif +
        " localSymbol=" + contract.m_localSymbol +
        " client Id=" + order.m_clientId +
        " parent Id=" + order.m_parentId +
        " permId=" + order.m_permId +
        " outsideRth=" + order.m_outsideRth +
        " hidden=" + order.m_hidden +
        " discretionaryAmt=" + order.m_discretionaryAmt +
        " triggerMethod=" + order.m_triggerMethod +
        " goodAfterTime=" + order.m_goodAfterTime +
        " goodTillDate=" + order.m_goodTillDate +
        " faGroup=" + order.m_faGroup +
        " faMethod=" + order.m_faMethod +
        " faPercentage=" + order.m_faPercentage +
        " faProfile=" + order.m_faProfile +
        " shortSaleSlot=" + order.m_shortSaleSlot +
        " designatedLocation=" + order.m_designatedLocation +
        " ocaGroup=" + order.m_ocaGroup +
        " ocaType=" + order.m_ocaType +
        " rule80A=" + order.m_rule80A +
        " allOrNone=" + order.m_allOrNone +
        " minQty=" + order.m_minQty +
        " percentOffset=" + order.m_percentOffset +
        " eTradeOnly=" + order.m_eTradeOnly +
        " firmQuoteOnly=" + order.m_firmQuoteOnly +
        " nbboPriceCap=" + order.m_nbboPriceCap +
        " auctionStrategy=" + order.m_auctionStrategy +
        " startingPrice=" + order.m_startingPrice +
        " stockRefPrice=" + order.m_stockRefPrice +
        " delta=" + order.m_delta +
        " stockRangeLower=" + order.m_stockRangeLower +
        " stockRangeUpper=" + order.m_stockRangeUpper +
        " volatility=" + order.m_volatility +
        " volatilityType=" + order.m_volatilityType +
        " deltaNeutralOrderType=" + order.m_deltaNeutralOrderType +
        " deltaNeutralAuxPrice=" + order.m_deltaNeutralAuxPrice +
        " continuousUpdate=" + order.m_continuousUpdate +
        " referencePriceType=" + order.m_referencePriceType +
        " trailStopPrice=" + order.m_trailStopPrice +
        " scaleInitLevelSize=" + Util.IntMaxString(order.m_scaleInitLevelSize) +
        " scaleSubsLevelSize=" + Util.IntMaxString(order.m_scaleSubsLevelSize) +
        " scalePriceIncrement=" + Util.DoubleMaxString(order.m_scalePriceIncrement) +
        " account=" + order.m_account +
        " settlingFirm=" + order.m_settlingFirm +
        " clearingAccount=" + order.m_clearingAccount +
        " clearingIntent=" + order.m_clearingIntent +
        " notHeld=" + order.m_notHeld +
        " whatIf=" + order.m_whatIf
        ;

        if ("BAG".equals(contract.m_secType)) {
        	if (contract.m_comboLegsDescrip != null) {
        		msg += " comboLegsDescrip=" + contract.m_comboLegsDescrip;
        	}
        	if (order.m_basisPoints != Double.MAX_VALUE) {
        		msg += " basisPoints=" + order.m_basisPoints;
        		msg += " basisPointsType=" + order.m_basisPointsType;
        	}
        }
        
    	if (contract.m_underComp != null) {
    		UnderComp underComp = contract.m_underComp;
    		msg +=
    			" underComp.conId =" + underComp.m_conId +
    			" underComp.delta =" + underComp.m_delta +
    			" underComp.price =" + underComp.m_price ;
    	}
    	
    	if (!Util.StringIsEmpty(order.m_algoStrategy)) {
    		msg += " algoStrategy=" + order.m_algoStrategy;
    		msg += " algoParams={";
    		if (order.m_algoParams != null) {
    			Vector algoParams = order.m_algoParams;
    			for (int i = 0; i < algoParams.size(); ++i) {
    				TagValue param = (TagValue)algoParams.elementAt(i);
    				if (i > 0) {
    					msg += ",";
    				}
    				msg += param.m_tag + "=" + param.m_value;
    			}
    		}
    		msg += "}";
    	}
    
        String orderStateMsg =
        	" status=" + orderState.m_status
        	+ " initMargin=" + orderState.m_initMargin
        	+ " maintMargin=" + orderState.m_maintMargin
        	+ " equityWithLoan=" + orderState.m_equityWithLoan
        	+ " commission=" + Util.DoubleMaxString(orderState.m_commission)
        	+ " minCommission=" + Util.DoubleMaxString(orderState.m_minCommission)
        	+ " maxCommission=" + Util.DoubleMaxString(orderState.m_maxCommission)
        	+ " commissionCurrency=" + orderState.m_commissionCurrency
        	+ " warningText=" + orderState.m_warningText
		;

        return msg + orderStateMsg;
    }
    
    static public String openOrderEnd() {
    	return " =============== end ===============";
    }
    
    static public String updateAccountValue(String key, String value, String currency, String accountName) {
    	return "updateAccountValue: " + key + " " + value + " " + currency + " " + accountName;
    }
    
    static public String updatePortfolio(Contract contract, int position, double marketPrice,
    									 double marketValue, double averageCost, double unrealizedPNL,
    									 double realizedPNL, String accountName) {
    	String msg = "updatePortfolio: "
    		+ contractMsg(contract)
    		+ position + " " + marketPrice + " " + marketValue + " " + averageCost + " " + unrealizedPNL + " " + realizedPNL + " " + accountName;
    	return msg;
    }
    
    static public String updateAccountTime(String timeStamp) {
    	return "updateAccountTime: " + timeStamp;
    }
    
    static public String accountDownloadEnd(String accountName) {
    	return "accountDownloadEnd: " + accountName;
    }
    
    static public String nextValidId( int orderId) {
    	return "Next Valid Order ID: " + orderId;
    }
    
    static public String contractDetails(int reqId, ContractDetails contractDetails) {
    	Contract contract = contractDetails.m_summary;
    	String msg = "reqId = " + reqId + " ===================================\n"
    		+ " ---- Contract Details begin ----\n"
    		+ contractMsg(contract) + contractDetailsMsg(contractDetails)
    		+ " ---- Contract Details End ----\n";
    	return msg;
    }
    
    private static String contractDetailsMsg(ContractDetails contractDetails) {
    	String msg = "marketName = " + contractDetails.m_marketName + "\n"
        + "tradingClass = " + contractDetails.m_tradingClass + "\n"
        + "minTick = " + contractDetails.m_minTick + "\n"
        + "price magnifier = " + contractDetails.m_priceMagnifier + "\n"
        + "orderTypes = " + contractDetails.m_orderTypes + "\n"
        + "validExchanges = " + contractDetails.m_validExchanges + "\n"
        + "underConId = " + contractDetails.m_underConId + "\n"
        + "longName = " + contractDetails.m_longName + "\n"
        + "contractMonth = " + contractDetails.m_contractMonth + "\n"
        + "industry = " + contractDetails.m_industry + "\n"
        + "category = " + contractDetails.m_category + "\n"
        + "subcategory = " + contractDetails.m_subcategory + "\n"
        + "timeZoneId = " + contractDetails.m_timeZoneId + "\n"
        + "tradingHours = " + contractDetails.m_tradingHours + "\n"
        + "liquidHours = " + contractDetails.m_liquidHours + "\n";
    	return msg;
    }
    
	static public String contractMsg(Contract contract) {
    	String msg = "conid = " + contract.m_conId + "\n"
        + "symbol = " + contract.m_symbol + "\n"
        + "secType = " + contract.m_secType + "\n"
        + "expiry = " + contract.m_expiry + "\n"
        + "strike = " + contract.m_strike + "\n"
        + "right = " + contract.m_right + "\n"
        + "multiplier = " + contract.m_multiplier + "\n"
        + "exchange = " + contract.m_exchange + "\n"
        + "primaryExch = " + contract.m_primaryExch + "\n"
        + "currency = " + contract.m_currency + "\n"
        + "localSymbol = " + contract.m_localSymbol + "\n";
    	return msg;
    }
	
    static public String bondContractDetails(int reqId, ContractDetails contractDetails) {
        Contract contract = contractDetails.m_summary;
        String msg = "reqId = " + reqId + " ===================================\n"	
        + " ---- Bond Contract Details begin ----\n"
        + "symbol = " + contract.m_symbol + "\n"
        + "secType = " + contract.m_secType + "\n"
        + "cusip = " + contractDetails.m_cusip + "\n"
        + "coupon = " + contractDetails.m_coupon + "\n"
        + "maturity = " + contractDetails.m_maturity + "\n"
        + "issueDate = " + contractDetails.m_issueDate + "\n"
        + "ratings = " + contractDetails.m_ratings + "\n"
        + "bondType = " + contractDetails.m_bondType + "\n"
        + "couponType = " + contractDetails.m_couponType + "\n"
        + "convertible = " + contractDetails.m_convertible + "\n"
        + "callable = " + contractDetails.m_callable + "\n"
        + "putable = " + contractDetails.m_putable + "\n"
        + "descAppend = " + contractDetails.m_descAppend + "\n"
        + "exchange = " + contract.m_exchange + "\n"
        + "currency = " + contract.m_currency + "\n"
        + "marketName = " + contractDetails.m_marketName + "\n"
        + "tradingClass = " + contractDetails.m_tradingClass + "\n"
        + "conid = " + contract.m_conId + "\n"
        + "minTick = " + contractDetails.m_minTick + "\n"
        + "orderTypes = " + contractDetails.m_orderTypes + "\n"
        + "validExchanges = " + contractDetails.m_validExchanges + "\n"
        + "nextOptionDate = " + contractDetails.m_nextOptionDate + "\n"
        + "nextOptionType = " + contractDetails.m_nextOptionType + "\n"
        + "nextOptionPartial = " + contractDetails.m_nextOptionPartial + "\n"
        + "notes = " + contractDetails.m_notes + "\n"
        + "longName = " + contractDetails.m_longName + "\n"
        + " ---- Bond Contract Details End ----\n";
        return msg;
    }
    
    static public String contractDetailsEnd(int reqId) {
    	return "reqId = " + reqId + " =============== end ===============";
    }
    
    static public String execDetails( int reqId, Contract contract, Execution execution) {
        String msg = " ---- Execution Details begin ----\n"
        + "reqId = " + reqId + "\n"
        + "orderId = " + execution.m_orderId + "\n"
        + "clientId = " + execution.m_clientId + "\n"
        + "symbol = " + contract.m_symbol + "\n"
        + "secType = " + contract.m_secType + "\n"
        + "expiry = " + contract.m_expiry + "\n"
        + "strike = " + contract.m_strike + "\n"
        + "right = " + contract.m_right + "\n"
        + "contractExchange = " + contract.m_exchange + "\n"
        + "currency = " + contract.m_currency + "\n"
        + "localSymbol = " + contract.m_localSymbol + "\n"
        + "execId = " + execution.m_execId + "\n"
        + "time = " + execution.m_time + "\n"
        + "acctNumber = " + execution.m_acctNumber + "\n"
        + "executionExchange = " + execution.m_exchange + "\n"
        + "side = " + execution.m_side + "\n"
        + "shares = " + execution.m_shares + "\n"
        + "price = " + execution.m_price + "\n"
        + "permId = " + execution.m_permId + "\n"
        + "liquidation = " + execution.m_liquidation + "\n"
        + "cumQty = " + execution.m_cumQty + "\n"
        + "avgPrice = " + execution.m_avgPrice + "\n"
        + " ---- Execution Details end ----\n";
        return msg;
    }
    
    static public String execDetailsEnd(int reqId) {
    	return "reqId = " + reqId + " =============== end ===============";
    }
    
    static public String updateMktDepth( int tickerId, int position, int operation, int side,
    									 double price, int size) {
    	return "updateMktDepth: " + tickerId + " " + position + " " + operation + " " + side + " " + price + " " + size;
    }
    
    static public String updateMktDepthL2( int tickerId, int position, String marketMaker,
    									   int operation, int side, double price, int size) {
    	return "updateMktDepth: " + tickerId + " " + position + " " + marketMaker + " " + operation + " " + side + " " + price + " " + size;
    }
    
    static public String updateNewsBulletin( int msgId, int msgType, String message, String origExchange) {
    	return "MsgId=" + msgId + " :: MsgType=" + msgType +  " :: Origin=" + origExchange + " :: Message=" + message;
    }
    
    static public String managedAccounts( String accountsList) {
    	return "Connected : The list of managed accounts are : [" + accountsList + "]";
    }
    
    static public String receiveFA(int faDataType, String xml) {
    	return FINANCIAL_ADVISOR + " " + EClientSocket.faMsgTypeName(faDataType) + " " + xml;
    }
    
    static public String historicalData(int reqId, String date, double open, double high, double low,
                      					double close, int volume, int count, double WAP, boolean hasGaps) {
    	return "id=" + reqId +
        " date = " + date +
        " open=" + open +
        " high=" + high +
        " low=" + low +
        " close=" + close +
        " volume=" + volume +
        " count=" + count +
        " WAP=" + WAP +
        " hasGaps=" + hasGaps;
    }
	public static String realtimeBar(int reqId, long time, double open,
			double high, double low, double close, long volume, double wap, int count) {
        return "id=" + reqId +
        " time = " + time +
        " open=" + open +
        " high=" + high +
        " low=" + low +
        " close=" + close +
        " volume=" + volume +
        " count=" + count +
        " WAP=" + wap;
	}
	
    static public String scannerParameters(String xml) {
    	return SCANNER_PARAMETERS + "\n" + xml;
    }
    
    static public String scannerData(int reqId, int rank, ContractDetails contractDetails,
    								 String distance, String benchmark, String projection,
    								 String legsStr) {
        Contract contract = contractDetails.m_summary;
    	return "id = " + reqId +
        " rank=" + rank +
        " symbol=" + contract.m_symbol +
        " secType=" + contract.m_secType +
        " expiry=" + contract.m_expiry +
        " strike=" + contract.m_strike +
        " right=" + contract.m_right +
        " exchange=" + contract.m_exchange +
        " currency=" + contract.m_currency +
        " localSymbol=" + contract.m_localSymbol +
        " marketName=" + contractDetails.m_marketName +
        " tradingClass=" + contractDetails.m_tradingClass +
        " distance=" + distance +
        " benchmark=" + benchmark +
        " projection=" + projection +
        " legsStr=" + legsStr;
    }
    
    static public String scannerDataEnd(int reqId) {
    	return "id = " + reqId + " =============== end ===============";
    }
    
    static public String currentTime(long time) {
		return "current time = " + time +
		" (" + DateFormat.getDateTimeInstance().format(new Date(time * 1000)) + ")";
    }

    static public String fundamentalData(int reqId, String data) {
		return "id  = " + reqId + " len = " + data.length() + '\n' + data;
    }
    
    static public String deltaNeutralValidation(int reqId, UnderComp underComp) {
    	return "id = " + reqId
    	+ " underComp.conId =" + underComp.m_conId
    	+ " underComp.delta =" + underComp.m_delta
    	+ " underComp.price =" + underComp.m_price;
    }
    static public String tickSnapshotEnd(int tickerId) {
    	return "id=" + tickerId + " =============== end ===============";
    }
}
```



=============================================================

