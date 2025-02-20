### Failed Test: `ExecutionFilter_equals_0_0_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_codestral-latest/tullibee/com/ib/client/failedtests/ExecutionFilter_equals_0_0_Test.java`

```java
--- 
+++ 
     @Test
     void testEquals_NullObject() {
         assertFalse(executionFilter.equals(null));
// BEGIN DIFF
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testEquals_DifferentClass() {
       assertFalse(executionFilter.equals(new Object()));
// END DIFF
     }
 
     @Test
```

### Failed Test: `ExecutionFilter_equals_0_0_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_gemini-1.5-flash-8b/tullibee/com/ib/client/failedtests/ExecutionFilter_equals_0_0_Test.java`

```java
--- 
+++ 
     void testEquals_nullObject() {
         ExecutionFilter filter = new ExecutionFilter(1, "acct1", "time1", "symbol1", "secType1", "exchange1", "buy");
         assertFalse(filter.equals(null));
// BEGIN DIFF
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testEquals_differentObject() {
       ExecutionFilter filter1 = new ExecutionFilter(1, "acct1", "time1", "symbol1", "secType1", "exchange1", "buy");
       Object otherObject = new Object();
       assertFalse(filter1.equals(otherObject));
// END DIFF
     }
 
     @Test
```

### Failed Test: `ExecutionFilter_equals_0_0_Test.java`

**Model:** gpt-4o-mini

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_gpt-4o-mini/tullibee/com/ib/client/failedtests/ExecutionFilter_equals_0_0_Test.java`

```java
--- 
+++ 
     public void testEquals_DifferentValues() {
         assertFalse(filter1.equals(filter3), "Should return false when values are different");
     }
+
// BEGIN DIFF
   @Test
   public void testEquals_DifferentObjectType() {
       assertFalse(filter1.equals("Some String"), "Should return false when comparing to a different object type");
   }
// END DIFF
 }
```

### Failed Test: `ExecutionFilter_equals_0_0_Test.java`

**Model:** meta-llama/Llama-3.2-3B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Llama-3.2-3B-Instruct/tullibee/com/ib/client/failedtests/ExecutionFilter_equals_0_0_Test.java`

```java
--- 
+++ 
 package com.ib.client;
 
+import org.junit.jupiter.api.extension.ExtendWith;
+import org.mockito.junit.jupiter.MockitoExtension;
 import org.mockito.*;
 import org.junit.jupiter.api.*;
 import static org.mockito.Mockito.*;
 import static org.junit.jupiter.api.Assertions.*;
-import org.junit.jupiter.api.extension.ExtendWith;
-import org.mockito.junit.jupiter.MockitoExtension;
 
+@ExtendWith(MockitoExtension.class)
 public class ExecutionFilter_equals_0_0_Test {
 
// BEGIN DIFF
   @Mock
   private Object objectToCompare;
// END DIFF
+
     @Test
// BEGIN DIFF
//    public void testEquals_Null() {
//        ExecutionFilter filter1 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
//        ExecutionFilter filter2 = null;
//        assertFalse(filter1.equals(filter2));
   public void testEquals_NullObject() {
       ExecutionFilter executionFilter = new ExecutionFilter();
       boolean result = executionFilter.equals(objectToCompare);
       assert result == false;
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testEquals_Self() {
//        ExecutionFilter filter1 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
//        ExecutionFilter filter2 = filter1;
//        assertTrue(filter1.equals(filter2));
   public void testEquals_SameObject() {
       ExecutionFilter executionFilter = new ExecutionFilter();
       boolean result = executionFilter.equals(executionFilter);
       assert result == true;
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testEquals_MatchAllFields() {
//        ExecutionFilter filter1 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
//        ExecutionFilter filter2 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
//        assertTrue(filter1.equals(filter2));
   public void testEquals_DifferentObjects() {
       ExecutionFilter executionFilter1 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
       ExecutionFilter executionFilter2 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
       boolean result = executionFilter1.equals(executionFilter2);
       assert result == true;
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testEquals_DifferentFields() {
//        ExecutionFilter filter1 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
//        ExecutionFilter filter2 = new ExecutionFilter(2, "67890", "2022-01-01", "GOOG", "Stock", "NASDAQ", "Sell");
//        assertFalse(filter1.equals(filter2));
   public void testEquals_DifferentProperties() {
       ExecutionFilter executionFilter1 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
       ExecutionFilter executionFilter2 = new ExecutionFilter(2, "67890", "2022-01-01", "GOOG", "Stock", "NASDAQ", "Sell");
       boolean result = executionFilter1.equals(executionFilter2);
       assert result == false;
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testEquals_MismatchCase() {
//        ExecutionFilter filter1 = new ExecutionFilter(1, "12345", "2022-01-01", "aapl", "Stock", "NYSE", "Buy");
//        ExecutionFilter filter2 = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "buy");
//        assertTrue(filter1.equals(filter2));
   public void testEquals_NullStringComparison() {
       ExecutionFilter executionFilter = new ExecutionFilter(1, "12345", "2022-01-01", "AAPL", "Stock", "NYSE", "Buy");
       boolean result = executionFilter.equals(null);
       assert result == false;
// END DIFF
     }
 }
```

### Failed Test: `ExecutionFilter_equals_0_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-32B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Qwen2.5-Coder-32B-Instruct/tullibee/com/ib/client/failedtests/ExecutionFilter_equals_0_0_Test.java`

```java
--- 
+++ 
     public void testEqualsWithDifferentCaseValues() {
         assertTrue(filter1.equals(filter4));
     }
+
// BEGIN DIFF
   @Test
   public void testEqualsWithNonExecutionFilterObject() {
       assertFalse(filter1.equals(nonFilterObject));
   }
// END DIFF
 }
```

## Source File: `../SF110/1_tullibee/src/main/java/com/ib/client/ExecutionFilter.java`

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
 * ExecutionFilter.java
 *
 */
package com.ib.client;

public class ExecutionFilter{
    public int 		m_clientId;
    public String 	m_acctCode;
    public String 	m_time;
    public String 	m_symbol;
    public String 	m_secType;
    public String 	m_exchange;
    public String 	m_side;

    public ExecutionFilter() {
        m_clientId = 0;
    }

    public ExecutionFilter( int p_clientId, String p_acctCode, String p_time,
    		String p_symbol, String p_secType, String p_exchange, String p_side) {
        m_clientId = p_clientId;
        m_acctCode = p_acctCode;
        m_time = p_time;
        m_symbol = p_symbol;
        m_secType = p_secType;
        m_exchange = p_exchange;
        m_side = p_side;
    }

    public boolean equals(Object p_other) {
        boolean l_bRetVal = false;

        if ( p_other == null ) {
            l_bRetVal = false;
		}
        else if ( this == p_other ) {
            l_bRetVal = true;
        }
        else {
            ExecutionFilter l_theOther = (ExecutionFilter)p_other;
            l_bRetVal = (m_clientId == l_theOther.m_clientId &&
                    m_acctCode.equalsIgnoreCase( l_theOther.m_acctCode) &&
                    m_time.equalsIgnoreCase( l_theOther.m_time) &&
                    m_symbol.equalsIgnoreCase( l_theOther.m_symbol) &&
                    m_secType.equalsIgnoreCase( l_theOther.m_secType) &&
                    m_exchange.equalsIgnoreCase( l_theOther.m_exchange) &&
                    m_side.equalsIgnoreCase( l_theOther.m_side) );
        }
        return l_bRetVal;
    }
}
```



=============================================================

