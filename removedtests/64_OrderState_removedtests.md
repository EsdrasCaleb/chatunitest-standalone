### Failed Test: `OrderState_equals_0_0_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_codestral-latest/tullibee/com/ib/client/failedtests/OrderState_equals_0_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testEquals_DifferentClass() {
       assertFalse(orderState1.equals(new Object()));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testEquals_DifferentCommission() {
         orderState2.m_commission = 2.0;
         assertFalse(orderState1.equals(orderState2));
     }
 
     @Test
// BEGIN DIFF
   void testEquals_DifferentStatus() {
       try (MockedStatic<Util> mockedUtil = Mockito.mockStatic(Util.class)) {
           mockedUtil.when(() -> Util.StringCompare("active", "inactive")).thenReturn(1);
           assertFalse(orderState1.equals(orderState2));
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testEquals_DifferentInitMargin() {
       try (MockedStatic<Util> mockedUtil = Mockito.mockStatic(Util.class)) {
           mockedUtil.when(() -> Util.StringCompare("1000", "2000")).thenReturn(1);
           orderState2.m_initMargin = "2000";
           assertFalse(orderState1.equals(orderState2));
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testEquals_DifferentMaintMargin() {
       try (MockedStatic<Util> mockedUtil = Mockito.mockStatic(Util.class)) {
           mockedUtil.when(() -> Util.StringCompare("500", "1000")).thenReturn(1);
           orderState2.m_maintMargin = "1000";
           assertFalse(orderState1.equals(orderState2));
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testEquals_DifferentEquityWithLoan() {
       try (MockedStatic<Util> mockedUtil = Mockito.mockStatic(Util.class)) {
           mockedUtil.when(() -> Util.StringCompare("1500", "2000")).thenReturn(1);
           orderState2.m_equityWithLoan = "2000";
           assertFalse(orderState1.equals(orderState2));
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testEquals_DifferentCommissionCurrency() {
       try (MockedStatic<Util> mockedUtil = Mockito.mockStatic(Util.class)) {
           mockedUtil.when(() -> Util.StringCompare("USD", "EUR")).thenReturn(1);
           orderState2.m_commissionCurrency = "EUR";
           assertFalse(orderState1.equals(orderState2));
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testEquals_AllFieldsEqual() {
         assertTrue(orderState1.equals(orderState2));
     }
```

### Failed Test: `OrderState_equals_0_0_Test.java`

**Model:** gpt-4o-mini

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_gpt-4o-mini/tullibee/com/ib/client/failedtests/OrderState_equals_0_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   public void testEquals_DifferentClass() {
       assertFalse(orderState1.equals("Not an OrderState"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testEquals_EqualObjects() {
         assertTrue(orderState1.equals(orderState2));
     }
     @Test
     public void testEquals_DifferentMaxCommission() {
         orderState3.m_maxCommission = 20.0;
// BEGIN DIFF
       assertFalse(orderState1.equals(orderState3));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testEquals_DifferentStatus() {
// END DIFF
         assertFalse(orderState1.equals(orderState3));
     }
 
```

### Failed Test: `OrderState_equals_0_0_Test.java`

**Model:** meta-llama/Llama-3.2-3B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Llama-3.2-3B-Instruct/tullibee/com/ib/client/failedtests/OrderState_equals_0_0_Test.java`

```java
--- 
+++ 
 @ExtendWith(MockitoExtension.class)
 public class OrderState_equals_0_0_Test {
 
// BEGIN DIFF
   @Mock
   private OrderState orderStateMock;
// END DIFF
+
     @InjectMocks
     private OrderState orderState;
 
     @Test
     public void testEquals_SameObject_ReturnsTrue() {
// BEGIN DIFF
//        assertEquals(true, orderState.equals(orderState));
       assertTrue(orderState.equals(orderState));
// END DIFF
     }
 
     @Test
     public void testEquals_NullObject_ReturnsFalse() {
// BEGIN DIFF
//        OrderState other = null;
//        assertFalse(orderState.equals(other));
       assertFalse(orderState.equals(null));
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testEquals_DifferentObjects_ReturnsFalse() {
//        OrderState other = new OrderState("differentStatus", "differentInitMargin", "differentMaintMargin", "differentEquityWithLoan", 1.0, 2.0, 3.0, "differentCommissionCurrency", "differentWarningText");
//        assertFalse(orderState.equals(other));
   public void testEquals_DifferentObject_ReturnsFalse() {
       when(orderStateMock.m_commission).thenReturn(10.0);
       when(orderStateMock.m_minCommission).thenReturn(10.0);
       when(orderStateMock.m_maxCommission).thenReturn(10.0);
       when(orderStateMock.m_status).thenReturn("status");
       when(orderStateMock.m_initMargin).thenReturn("initMargin");
       when(orderStateMock.m_maintMargin).thenReturn("maintMargin");
       when(orderStateMock.m_equityWithLoan).thenReturn("equityWithLoan");
       when(orderStateMock.m_commissionCurrency).thenReturn("currency");
       OrderState otherOrderState = new OrderState("status", "initMargin", "maintMargin", "equityWithLoan", 10.0, 10.0, 10.0, "currency", "warningText");
       assertFalse(orderState.equals(otherOrderState));
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testEquals_MatchesAllFields_ReturnsTrue() {
//        OrderState other = new OrderState("status", "initMargin", "maintMargin", "equityWithLoan", 1.0, 2.0, 3.0, "commissionCurrency", "warningText");
//        assertTrue(orderState.equals(other));
//    }
// END DIFF
-
// BEGIN DIFF
//    @Test
//    public void testEquals_MismatchedFields_ReturnsFalse() {
//        OrderState other = new OrderState("status", "initMargin", "maintMargin", "equityWithLoan", 1.0, 2.0, 3.0, "differentCommissionCurrency", "warningText");
//        assertFalse(orderState.equals(other));
   public void testEquals_SameFields_ReturnsTrue() {
       when(orderStateMock.m_commission).thenReturn(10.0);
       when(orderStateMock.m_minCommission).thenReturn(10.0);
       when(orderStateMock.m_maxCommission).thenReturn(10.0);
       when(orderStateMock.m_status).thenReturn("status");
       when(orderStateMock.m_initMargin).thenReturn("initMargin");
       when(orderStateMock.m_maintMargin).thenReturn("maintMargin");
       when(orderStateMock.m_equityWithLoan).thenReturn("equityWithLoan");
       when(orderStateMock.m_commissionCurrency).thenReturn("currency");
       OrderState otherOrderState = new OrderState("status", "initMargin", "maintMargin", "equityWithLoan", 10.0, 10.0, 10.0, "currency", "warningText");
       assertTrue(orderState.equals(otherOrderState));
// END DIFF
     }
 }
```

### Failed Test: `OrderState_equals_0_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-32B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Qwen2.5-Coder-32B-Instruct/tullibee/com/ib/client/failedtests/OrderState_equals_0_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   public void testEquals_DifferentClass_ReturnsFalse() {
       assertFalse(orderState.equals(new Object()));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testEquals_SameValues_ReturnsTrue() {
         OrderState other = new OrderState("status", "initMargin", "maintMargin", "equityWithLoan", 1.0, 2.0, 3.0, "currency", "warning");
         assertTrue(orderState.equals(other));
// BEGIN DIFF
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testEquals_DifferentStatus_ReturnsFalse() {
       OrderState other = new OrderState("differentStatus", "initMargin", "maintMargin", "equityWithLoan", 1.0, 2.0, 3.0, "currency", "warning");
       assertFalse(orderState.equals(other));
// END DIFF
     }
 
     @Test
         OrderState other = new OrderState("status", "initMargin", "maintMargin", "equityWithLoan", 1.0, 2.0, 6.0, "currency", "warning");
         assertFalse(orderState.equals(other));
     }
+
// BEGIN DIFF
   // Mocking Util.StringCompare to return 0 (equal) for all string comparisons
   @Test
   public void testEquals_StringCompareMock_ReturnsTrue() {
       OrderState other = new OrderState("status", "initMargin", "maintMargin", "equityWithLoan", 1.0, 2.0, 3.0, "currency", "warning");
       when(utilMock.StringCompare(anyString(), anyString())).thenReturn(0);
       assertTrue(orderState.equals(other));
   }
// END DIFF
 }
```

## Source File: `../SF110/1_tullibee/src/main/java/com/ib/client/OrderState.java`

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

/*
 * OrderState.java
 */
package com.ib.client;

public class OrderState {

	public String m_status;

	public String m_initMargin;
	public String m_maintMargin;
	public String m_equityWithLoan;

	public double m_commission;
	public double m_minCommission;
	public double m_maxCommission;
	public String m_commissionCurrency;
	
	public String m_warningText;

	OrderState() {
		this (null, null, null, null, 0.0, 0.0, 0.0, null, null);
	}

	OrderState(String status, String initMargin, String maintMargin,
			String equityWithLoan, double commission, double minCommission,
			double maxCommission, String commissionCurrency, String warningText) {

		m_initMargin = initMargin;
		m_maintMargin = maintMargin;
		m_equityWithLoan = equityWithLoan;
		m_commission = commission;
		m_minCommission = minCommission;
		m_maxCommission = maxCommission;
		m_commissionCurrency = commissionCurrency;
		m_warningText = warningText;
	}

	public boolean equals(Object other) {

        if (this == other)
            return true;

        if (other == null)
            return false;

        OrderState state = (OrderState)other;

        if (m_commission != state.m_commission ||
        	m_minCommission != state.m_minCommission ||
        	m_maxCommission != state.m_maxCommission) {
        	return false;
        }

        if (Util.StringCompare(m_status, state.m_status) != 0 ||
        	Util.StringCompare(m_initMargin, state.m_initMargin) != 0 ||
        	Util.StringCompare(m_maintMargin, state.m_maintMargin) != 0 ||
        	Util.StringCompare(m_equityWithLoan, state.m_equityWithLoan) != 0 ||
        	Util.StringCompare(m_commissionCurrency, state.m_commissionCurrency) != 0) {
        	return false;
        }

        return true;
	}
}

```



=============================================================

