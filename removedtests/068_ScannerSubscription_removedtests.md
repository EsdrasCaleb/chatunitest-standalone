### Failed Test: `ScannerSubscription_numberOfRows_0_0_Test.java`

**Model:** meta-llama/Llama-3.2-3B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Llama-3.2-3B-Instruct/tullibee/com/ib/client/failedtests/ScannerSubscription_numberOfRows_0_0_Test.java`

```java
--- 
+++ 
 package com.ib.client;
 
+import java.lang.reflect.Field;
 import org.mockito.*;
 import org.junit.jupiter.api.*;
 import static org.mockito.Mockito.*;
 public class ScannerSubscription_numberOfRows_0_0_Test {
 
     @Test
// BEGIN DIFF
//    public void testNumberOfRows_ReturnsNO_ROW_NUMBER_SPECIFIED() {
   public void testNumberOfRows() {
       // Arrange
// END DIFF
         ScannerSubscription subscription = new ScannerSubscription();
// BEGIN DIFF
//        assertEquals(ScannerSubscription.NO_ROW_NUMBER_SPECIFIED, subscription.numberOfRows());
       subscription.numberOfRows(10);
       // Act
       int rows = subscription.numberOfRows();
       // Assert
       assertEquals(10, rows);
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testNumberOfRows_ReturnsCorrectValue() {
   public void testNumberOfRowsDefault() {
       // Arrange
// END DIFF
         ScannerSubscription subscription = new ScannerSubscription();
// BEGIN DIFF
//        subscription.numberOfRows(10);
//        assertEquals(10, subscription.numberOfRows());
       // Act
       int rows = subscription.numberOfRows();
       // Assert
       assertEquals(ScannerSubscription.NO_ROW_NUMBER_SPECIFIED, rows);
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testNumberOfRows_ReturnsCorrectValueWithMultipleInvocations() {
   public void testNumberOfRowsThrowWhenSet() {
       // Arrange
// END DIFF
         ScannerSubscription subscription = new ScannerSubscription();
// BEGIN DIFF
//        subscription.numberOfRows(10);
//        subscription.numberOfRows(20);
//        assertEquals(20, subscription.numberOfRows());
       // Act and Assert
       assertThrows(NullPointerException.class, () -> subscription.numberOfRows());
// END DIFF
     }
 
     @Test
// BEGIN DIFF
//    public void testNumberOfRows_ThrowsNullPointerException_WhenNullSubscription() {
//        ScannerSubscription subscription = null;
//        assertThrows(NullPointerException.class, () -> subscription.numberOfRows());
   public void testNumberOfRowsThrowWhenGet() throws Exception {
       // Arrange
       ScannerSubscription subscription = new ScannerSubscription();
       // Act and Assert
       Field field = ScannerSubscription.class.getDeclaredField("m_numberOfRows");
       field.setAccessible(true);
       field.set(subscription, null);
       int rows = subscription.numberOfRows();
       assertEquals(ScannerSubscription.NO_ROW_NUMBER_SPECIFIED, rows);
// END DIFF
     }
 }
```

### Failed Test: `ScannerSubscription_spRatingBelow_13_0_Test.java`

**Model:** meta-llama/Llama-3.2-3B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Llama-3.2-3B-Instruct/tullibee/com/ib/client/failedtests/ScannerSubscription_spRatingBelow_13_0_Test.java`

```java
--- 
+++ 
         // Assert
         assertEquals(expected, actual);
     }
+
// BEGIN DIFF
   @Test
   public void testSpRatingBelow_Null() {
       // Arrange
       when(scannerSubscription.spRatingBelow()).thenReturn(null);
       // Act and Assert
       assertThrows(NullPointerException.class, () -> scannerSubscription.spRatingBelow());
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testSpRatingBelow_InvalidValue() {
       // Arrange
       when(scannerSubscription.spRatingBelow()).thenReturn("invalid value");
       // Act and Assert
       assertThrows(IllegalArgumentException.class, () -> scannerSubscription.spRatingBelow());
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testSpRatingBelow_Empty() {
       scannerSubscription = new ScannerSubscription();
       String result = scannerSubscription.spRatingBelow();
       assertTrue(result.isEmpty());
   }
// END DIFF
 }
```

### Failed Test: `ScannerSubscription_abovePrice_4_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-1.5B-Instruct

**Failed Test File:** `../SF110/1_tullibee/chatunitest-tests_Qwen2.5-Coder-1.5B-Instruct/tullibee/com/ib/client/failedtests/ScannerSubscription_abovePrice_4_0_Test.java`

```java
--- 
+++ 
         // Assert
         assertEquals(200.5, result);
     }
+
// BEGIN DIFF
   @Test
   public void testAbovePriceReturnsDefaultValueWhenNotSet() {
       // Arrange
       ScannerSubscription scannerSubscription = mock(ScannerSubscription.class);
       // Use null as default value
       when(scannerSubscription.abovePrice()).thenReturn(null);
       // Act
       double result = scannerSubscription.abovePrice();
       // Assert
       assertEquals(Double.MAX_VALUE, result);
   }
// END DIFF
 }
```

## Source File: `../SF110/1_tullibee/src/main/java/com/ib/client/ScannerSubscription.java`

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

public class ScannerSubscription {
    public final static int NO_ROW_NUMBER_SPECIFIED = -1;

    private int m_numberOfRows = NO_ROW_NUMBER_SPECIFIED;
    private String m_instrument;
    private String m_locationCode;
    private String m_scanCode;
    private double m_abovePrice = Double.MAX_VALUE;
    private double m_belowPrice = Double.MAX_VALUE;
    private int m_aboveVolume = Integer.MAX_VALUE;
    private int m_averageOptionVolumeAbove = Integer.MAX_VALUE;
    private double m_marketCapAbove = Double.MAX_VALUE;
    private double m_marketCapBelow = Double.MAX_VALUE;
    private String m_moodyRatingAbove;
    private String m_moodyRatingBelow;
    private String m_spRatingAbove;
    private String m_spRatingBelow;
    private String m_maturityDateAbove;
    private String m_maturityDateBelow;
    private double m_couponRateAbove = Double.MAX_VALUE;
    private double m_couponRateBelow = Double.MAX_VALUE;
    private String m_excludeConvertible;
    private String m_scannerSettingPairs;
    private String m_stockTypeFilter;

    // Get
    public int numberOfRows()                   { return m_numberOfRows; }
    public String instrument()                  { return m_instrument; }
    public String locationCode()                { return m_locationCode; }
    public String scanCode()                    { return m_scanCode; }
    public double abovePrice()                  { return m_abovePrice; }
    public double belowPrice()                  { return m_belowPrice; }
    public int aboveVolume()                    { return m_aboveVolume; }
    public int averageOptionVolumeAbove()       { return m_averageOptionVolumeAbove; }
    public double marketCapAbove()              { return m_marketCapAbove; }
    public double marketCapBelow()              { return m_marketCapBelow; }
    public String moodyRatingAbove()            { return m_moodyRatingAbove; }
    public String moodyRatingBelow()            { return m_moodyRatingBelow; }
    public String spRatingAbove()               { return m_spRatingAbove; }
    public String spRatingBelow()               { return m_spRatingBelow; }
    public String maturityDateAbove()           { return m_maturityDateAbove; }
    public String maturityDateBelow()           { return m_maturityDateBelow; }
    public double couponRateAbove()             { return m_couponRateAbove; }
    public double couponRateBelow()             { return m_couponRateBelow; }
    public String excludeConvertible()          { return m_excludeConvertible; }
    public String scannerSettingPairs()         { return m_scannerSettingPairs; }
    public String stockTypeFilter()             { return m_stockTypeFilter; }

    // Set
    public void numberOfRows(int num)          { m_numberOfRows = num; }
    public void instrument(String txt)         { m_instrument = txt; }
    public void locationCode(String txt)       { m_locationCode = txt; }
    public void scanCode(String txt)           { m_scanCode = txt; }
    public void abovePrice(double price)       { m_abovePrice = price; }
    public void belowPrice(double price)       { m_belowPrice = price; }
    public void aboveVolume(int volume)        { m_aboveVolume = volume; }
    public void averageOptionVolumeAbove(int volume) { m_averageOptionVolumeAbove = volume; }
    public void marketCapAbove(double cap)     { m_marketCapAbove = cap; }
    public void marketCapBelow(double cap)     { m_marketCapBelow = cap; }
    public void moodyRatingAbove(String r)     { m_moodyRatingAbove = r; }
    public void moodyRatingBelow(String r)     { m_moodyRatingBelow = r; }
    public void spRatingAbove(String r)        { m_spRatingAbove = r; }
    public void spRatingBelow(String r)        { m_spRatingBelow = r; }
    public void maturityDateAbove(String d)    { m_maturityDateAbove = d; }
    public void maturityDateBelow(String d)    { m_maturityDateBelow = d; }
    public void couponRateAbove(double r)      { m_couponRateAbove = r; }
    public void couponRateBelow(double r)      { m_couponRateBelow = r; }
    public void excludeConvertible(String c)   { m_excludeConvertible = c; }
    public void scannerSettingPairs(String val) { m_scannerSettingPairs = val; }
    public void stockTypeFilter(String val)    { m_stockTypeFilter = val; }
}

```



=============================================================

