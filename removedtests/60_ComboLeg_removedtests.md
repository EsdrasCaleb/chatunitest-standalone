### Failed Test: `ComboLeg_equals_0_0_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_codestral-latest/tullibee/com/ib/client/failedtests/ComboLeg_equals_0_0_Test.java`

```java
--- 
+++ 
     @Test
     void testEquals_NullObject() {
         assertFalse(comboLeg1.equals(null));
// BEGIN DIFF
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testEquals_DifferentClass() {
       assertFalse(comboLeg1.equals(new Object()));
// END DIFF
     }
 
     @Test
     void testEquals_EqualObjects() {
         assertTrue(comboLeg1.equals(comboLeg2));
     }
+
// BEGIN DIFF
   @Test
   void testEquals_StringCompareIgnCase() {
       try (MockedStatic<Util> mockedUtil = Mockito.mockStatic(Util.class)) {
           mockedUtil.when(() -> Util.StringCompareIgnCase(anyString(), anyString())).thenReturn(1);
           assertFalse(comboLeg1.equals(comboLeg2));
       }
   }
// END DIFF
 }
```

### Failed Test: `ComboLeg_equals_0_0_Test.java`

**Model:** gemini-1.5-flash

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_gemini-1.5-flash/tullibee/com/ib/client/failedtests/ComboLeg_equals_0_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testEqualsDifferentClass() {
       ComboLeg leg1 = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
       assertFalse(leg1.equals("test"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testEqualsDifferentConId() {
         ComboLeg leg1 = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
         ComboLeg leg2 = new ComboLeg(2, 2, "BUY", "NYSE", ComboLeg.OPEN);
```

### Failed Test: `ComboLeg_equals_0_0_Test.java`

**Model:** open-codestral-mamba

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_open-codestral-mamba/tullibee/com/ib/client/failedtests/ComboLeg_equals_0_0_Test.java`

```java
--- 
+++ 
     public void testEquals_Null_ReturnsFalse() {
         assertFalse(comboLeg.equals(null));
     }
+
// BEGIN DIFF
   @Test
   public void testEquals_DifferentClass_ReturnsFalse() {
       assertFalse(comboLeg.equals(new Object()));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testEquals_SameAttributes_ReturnsTrue() {
       otherComboLeg.m_conId = comboLeg.m_conId;
       otherComboLeg.m_ratio = comboLeg.m_ratio;
       otherComboLeg.m_openClose = comboLeg.m_openClose;
       otherComboLeg.m_shortSaleSlot = comboLeg.m_shortSaleSlot;
       otherComboLeg.m_action = comboLeg.m_action;
       otherComboLeg.m_exchange = comboLeg.m_exchange;
       otherComboLeg.m_designatedLocation = comboLeg.m_designatedLocation;
       when(utilMock.StringCompareIgnCase(anyString(), anyString())).thenReturn(0);
       assertTrue(comboLeg.equals(otherComboLeg));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testEquals_DifferentAttributes_ReturnsFalse() {
       otherComboLeg.m_conId = comboLeg.m_conId + 1;
       when(utilMock.StringCompareIgnCase(anyString(), anyString())).thenReturn(0);
       assertFalse(comboLeg.equals(otherComboLeg));
   }
// END DIFF
 }
```

### Failed Test: `ComboLeg_equals_0_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-32B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Qwen2.5-Coder-32B-Instruct/tullibee/com/ib/client/failedtests/ComboLeg_equals_0_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   public void testEquals_DifferentClass_ReturnsFalse() {
       assertFalse(comboLeg.equals(new Object()));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testEquals_AllFieldsEqual_ReturnsTrue() {
         ComboLeg otherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 0, "LOC");
         assertTrue(comboLeg.equals(otherComboLeg));
     }
 
     @Test
// BEGIN DIFF
   public void testEquals_DifferentAction_ReturnsFalse() {
       ComboLeg otherComboLeg = new ComboLeg(1, 2, "SELL", "NYSE", ComboLeg.OPEN, 0, "LOC");
       when(mockUtil.StringCompareIgnCase("BUY", "SELL")).thenReturn(1);
       assertFalse(comboLeg.equals(otherComboLeg));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testEquals_DifferentExchange_ReturnsFalse() {
       ComboLeg otherComboLeg = new ComboLeg(1, 2, "BUY", "NASDAQ", ComboLeg.OPEN, 0, "LOC");
       when(mockUtil.StringCompareIgnCase("NYSE", "NASDAQ")).thenReturn(1);
       assertFalse(comboLeg.equals(otherComboLeg));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testEquals_DifferentOpenClose_ReturnsFalse() {
         ComboLeg otherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.CLOSE, 0, "LOC");
         assertFalse(comboLeg.equals(otherComboLeg));
         ComboLeg otherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "LOC");
         assertFalse(comboLeg.equals(otherComboLeg));
     }
+
// BEGIN DIFF
   @Test
   public void testEquals_DifferentDesignatedLocation_ReturnsFalse() {
       ComboLeg otherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 0, "OTHER_LOC");
       when(mockUtil.StringCompareIgnCase("LOC", "OTHER_LOC")).thenReturn(1);
       assertFalse(comboLeg.equals(otherComboLeg));
   }
// END DIFF
 }
```

## Source File: `../SF110/1_tullibee/src/main/java/com/ib/client/ComboLeg.java`

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
 * ComboLeg.java
 *
 */
package com.ib.client;


public class ComboLeg {
    public final static int 	SAME = 0; 	// open/close leg value is same as combo
    public final static int 	OPEN = 1;
    public final static int 	CLOSE = 2;
    public final static int 	UNKNOWN = 3;

    public int 					m_conId;
    public int 					m_ratio;
    public String 				m_action; // BUY/SELL/SSHORT
    public String 				m_exchange;
    public int 					m_openClose;
    
    // for stock legs when doing short sale
    public int                  m_shortSaleSlot; // 1 = clearing broker, 2 = third party
    public String               m_designatedLocation;

    public ComboLeg() {
    	this(/* conId */ 0, /* ratio */ 0, /* action */ null,
    		/* exchange */ null, /* openClose */ 0,
    		/* shortSaleSlot */ 0, /* designatedLocation*/ null);
    }

    public ComboLeg(int p_conId, int p_ratio, String p_action, String p_exchange, int p_openClose) {
    	this(p_conId, p_ratio, p_action, p_exchange, p_openClose,
    		/* shortSaleSlot */ 0, /* designatedLocation*/ null);

    }
    
    public ComboLeg(int p_conId, int p_ratio, String p_action, String p_exchange,
    		int p_openClose, int p_shortSaleSlot, String p_designatedLocation) {
        m_conId = p_conId;
        m_ratio = p_ratio;
        m_action = p_action;
        m_exchange = p_exchange;
        m_openClose = p_openClose;
        m_shortSaleSlot = p_shortSaleSlot;
        m_designatedLocation = p_designatedLocation;
    }

    public boolean equals(Object p_other) {
        if ( this == p_other ) {
            return true;
        }
        else if ( p_other == null ) {
            return false;
        }

        ComboLeg l_theOther = (ComboLeg)p_other;
        
        if (m_conId != l_theOther.m_conId ||
        	m_ratio != l_theOther.m_ratio ||
        	m_openClose != l_theOther.m_openClose ||
        	m_shortSaleSlot != l_theOther.m_shortSaleSlot) {
        	return false;
        }

        if (Util.StringCompareIgnCase(m_action, l_theOther.m_action) != 0 ||
        	Util.StringCompareIgnCase(m_exchange, l_theOther.m_exchange) != 0 ||
        	Util.StringCompareIgnCase(m_designatedLocation, l_theOther.m_designatedLocation) != 0) {
        	return false;
        }

        return true;
    }
}
```



=============================================================

