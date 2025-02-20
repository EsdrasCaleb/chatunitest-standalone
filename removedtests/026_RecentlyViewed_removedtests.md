### Failed Test: `RecentlyViewed_isInList_3_0_Test.java`

**Model:** gemini-1.5-flash

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash/a4j/net/kencochrane/a4j/beans/failedtests/RecentlyViewed_isInList_3_0_Test.java`

```java
--- 
+++ 
         }
     }
 
// BEGIN DIFF
   @Test
   void testIsInList_productNotFound() {
       recentlyViewed.getProducts().add(new MiniProduct("B01ABC123", "Product 1"));
       assertFalse(recentlyViewed.isInList("B07XYZ123"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_productFound_exactMatch() {
       recentlyViewed.getProducts().add(new MiniProduct("B01ABC123", "Product 1"));
       assertTrue(recentlyViewed.isInList("B01ABC123"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_productFound_caseInsensitiveMatch() {
       recentlyViewed.getProducts().add(new MiniProduct("b01abc123", "Product 1"));
       assertTrue(recentlyViewed.isInList("B01ABC123"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_productFound_withWhitespace() {
       recentlyViewed.getProducts().add(new MiniProduct(" B01ABC123 ", "Product 1"));
       assertTrue(recentlyViewed.isInList("B01ABC123"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_nullAsin() {
       recentlyViewed.getProducts().add(new MiniProduct("B01ABC123", "Product 1"));
       assertFalse(recentlyViewed.isInList(null));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_emptyAsin() {
       recentlyViewed.getProducts().add(new MiniProduct("B01ABC123", "Product 1"));
       assertFalse(recentlyViewed.isInList(""));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_nullAsinInList() {
       recentlyViewed.getProducts().add(new MiniProduct(null, "Product 1"));
       assertFalse(recentlyViewed.isInList("B01ABC123"));
   }
// END DIFF
+
     // Inner class for MiniProduct (replace with your actual class if different)
     class MiniProduct {
 
```

### Failed Test: `RecentlyViewed_isInList_3_0_Test.java`

**Model:** gpt-4o-mini

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gpt-4o-mini/a4j/net/kencochrane/a4j/beans/failedtests/RecentlyViewed_isInList_3_0_Test.java`

```java
--- 
+++ 
     @BeforeEach
     void setUp() {
         recentlyViewed = new RecentlyViewed();
// BEGIN DIFF
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_ProductExists() {
       MiniProduct product = new MiniProduct("12345");
       addProductToRecentlyViewed(product);
       assertTrue(recentlyViewed.isInList("12345"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_ProductExists_CaseInsensitive() {
       MiniProduct product = new MiniProduct("12345");
       addProductToRecentlyViewed(product);
       assertTrue(recentlyViewed.isInList("12345 "));
       assertTrue(recentlyViewed.isInList(" 12345"));
       assertTrue(recentlyViewed.isInList(" 12345 "));
       assertTrue(recentlyViewed.isInList("12345"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_ProductDoesNotExist() {
       MiniProduct product = new MiniProduct("12345");
       addProductToRecentlyViewed(product);
       assertFalse(recentlyViewed.isInList("67890"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_NullAsin() {
       MiniProduct product = new MiniProduct("12345");
       addProductToRecentlyViewed(product);
       assertFalse(recentlyViewed.isInList(null));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_EmptyAsin() {
       MiniProduct product = new MiniProduct("12345");
       addProductToRecentlyViewed(product);
       assertFalse(recentlyViewed.isInList(""));
// END DIFF
     }
 
     @Test
```

### Failed Test: `RecentlyViewed_isInList_3_0_Test.java`

**Model:** open-codestral-mamba

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_open-codestral-mamba/a4j/net/kencochrane/a4j/beans/failedtests/RecentlyViewed_isInList_3_0_Test.java`

```java
--- 
+++ 
         productsField.set(recentlyViewed, null);
         assertFalse(recentlyViewed.isInList("12345"));
     }
+
// BEGIN DIFF
   @Test
   void isInList_WithProductInList_ReturnsTrue() {
       MiniProduct mp = new MiniProduct();
       when(mp.getAsin()).thenReturn("12345");
       products.add(mp);
       assertTrue(recentlyViewed.isInList("12345"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void isInList_WithProductNotInList_ReturnsFalse() {
       MiniProduct mp = new MiniProduct();
       when(mp.getAsin()).thenReturn("67890");
       products.add(mp);
       assertFalse(recentlyViewed.isInList("12345"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void isInList_WithNullAsin_ReturnsFalse() {
       MiniProduct mp = new MiniProduct();
       when(mp.getAsin()).thenReturn(null);
       products.add(mp);
       assertFalse(recentlyViewed.isInList("12345"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void isInList_WithWhitespaceAsin_ReturnsFalse() {
       MiniProduct mp = new MiniProduct();
       when(mp.getAsin()).thenReturn(" ");
       products.add(mp);
       assertFalse(recentlyViewed.isInList("12345"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void isInList_WithMixedCaseAsin_ReturnsTrue() {
       MiniProduct mp = new MiniProduct();
       when(mp.getAsin()).thenReturn("AbCdE");
       products.add(mp);
       assertTrue(recentlyViewed.isInList("abcde"));
   }
// END DIFF
 }
```

### Failed Test: `RecentlyViewed_isInList_3_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-32B-Instruct

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_Qwen2.5-Coder-32B-Instruct/a4j/net/kencochrane/a4j/beans/failedtests/RecentlyViewed_isInList_3_0_Test.java`

```java
--- 
+++ 
         assertFalse(recentlyViewed.isInList("testAsin"));
     }
 
// BEGIN DIFF
   @Test
   void testIsInList_ProductListContainsMatchingAsin() {
       // Arrange
       ArrayList<MiniProduct> products = new ArrayList<>();
       MiniProduct mockMiniProduct = mock(MiniProduct.class);
       when(mockMiniProduct.getAsin()).thenReturn("testAsin");
       products.add(mockMiniProduct);
       Field productsField;
       try {
           productsField = RecentlyViewed.class.getDeclaredField("products");
           productsField.setAccessible(true);
           productsField.set(recentlyViewed, products);
       } catch (NoSuchFieldException | IllegalAccessException e) {
           fail("Reflection failed: " + e.getMessage());
       }
       // Act & Assert
       assertTrue(recentlyViewed.isInList("testAsin"));
       verify(mockMiniProduct).getAsin();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_ProductListContainsNonMatchingAsin() {
       // Arrange
       ArrayList<MiniProduct> products = new ArrayList<>();
       MiniProduct mockMiniProduct = mock(MiniProduct.class);
       when(mockMiniProduct.getAsin()).thenReturn("anotherAsin");
       products.add(mockMiniProduct);
       Field productsField;
       try {
           productsField = RecentlyViewed.class.getDeclaredField("products");
           productsField.setAccessible(true);
           productsField.set(recentlyViewed, products);
       } catch (NoSuchFieldException | IllegalAccessException e) {
           fail("Reflection failed: " + e.getMessage());
       }
       // Act & Assert
       assertFalse(recentlyViewed.isInList("testAsin"));
       verify(mockMiniProduct).getAsin();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_ProductListContainsNullMiniProduct() {
       // Arrange
       ArrayList<MiniProduct> products = new ArrayList<>();
       products.add(null);
       products.add(mock(MiniProduct.class));
       Field productsField;
       try {
           productsField = RecentlyViewed.class.getDeclaredField("products");
           productsField.setAccessible(true);
           productsField.set(recentlyViewed, products);
       } catch (NoSuchFieldException | IllegalAccessException e) {
           fail("Reflection failed: " + e.getMessage());
       }
       // Act & Assert
       assertFalse(recentlyViewed.isInList("testAsin"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_ProductListContainsMiniProductWithNullAsin() {
       // Arrange
       ArrayList<MiniProduct> products = new ArrayList<>();
       MiniProduct mockMiniProduct = mock(MiniProduct.class);
       when(mockMiniProduct.getAsin()).thenReturn(null);
       products.add(mockMiniProduct);
       Field productsField;
       try {
           productsField = RecentlyViewed.class.getDeclaredField("products");
           productsField.setAccessible(true);
           productsField.set(recentlyViewed, products);
       } catch (NoSuchFieldException | IllegalAccessException e) {
           fail("Reflection failed: " + e.getMessage());
       }
       // Act & Assert
       assertFalse(recentlyViewed.isInList("testAsin"));
       verify(mockMiniProduct).getAsin();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testIsInList_ProductListContainsMiniProductWithWhitespaceAsin() {
       // Arrange
       ArrayList<MiniProduct> products = new ArrayList<>();
       MiniProduct mockMiniProduct = mock(MiniProduct.class);
       when(mockMiniProduct.getAsin()).thenReturn(" testAsin ");
       products.add(mockMiniProduct);
       Field productsField;
       try {
           productsField = RecentlyViewed.class.getDeclaredField("products");
           productsField.setAccessible(true);
           productsField.set(recentlyViewed, products);
       } catch (NoSuchFieldException | IllegalAccessException e) {
           fail("Reflection failed: " + e.getMessage());
       }
       // Act & Assert
       assertTrue(recentlyViewed.isInList("testAsin"));
       verify(mockMiniProduct).getAsin();
   }
// END DIFF
+
     static class MiniProduct {
 
         private String asin;
```

### Failed Test: `RecentlyViewed_addProduct_0_4_Test.java`

**Model:** meta-llama/Llama-3.2-3B-Instruct

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_Llama-3.2-3B-Instruct/a4j/net/kencochrane/a4j/beans/failedtests/RecentlyViewed_addProduct_0_4_Test.java`

```java
--- 
+++ 
     public void testAddProduct_NullProduct() {
         assertDoesNotThrow(() -> recentlyViewed.addProduct(null));
     }
+
// BEGIN DIFF
   @Test
   public void testAddProduct_AlreadyInList() {
       MiniProduct prod = new MiniProduct("12345");
       recentlyViewed.addProduct(prod);
       assertDoesNotThrow(() -> recentlyViewed.addProduct(prod));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testAddProduct_NotInList() {
       MiniProduct prod1 = new MiniProduct("12345");
       MiniProduct prod2 = new MiniProduct("67890");
       recentlyViewed.addProduct(prod1);
       recentlyViewed.addProduct(prod2);
       assertEquals(2, recentlyViewed.getNumProducts());
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testAddProduct_EmptyList() {
       assertEquals(0, recentlyViewed.getNumProducts());
       MiniProduct prod = new MiniProduct("12345");
       recentlyViewed.addProduct(prod);
       assertEquals(1, recentlyViewed.getNumProducts());
   }
// END DIFF
 }
 
 class MiniProduct {
```

### Failed Test: `RecentlyViewed_isInList_3_0_Test.java`

**Model:** meta-llama/Llama-3.2-3B-Instruct

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_Llama-3.2-3B-Instruct/a4j/net/kencochrane/a4j/beans/failedtests/RecentlyViewed_isInList_3_0_Test.java`

```java
--- 
+++ 
     private ArrayList<MiniProduct> products;
 
     @Test
// BEGIN DIFF
   public void testIsInList_EmptyList() {
       assertTrue(recentlyViewed.isInList("1234567890"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testIsInList_NotFound() {
       recentlyViewed.getProducts().add(new MiniProduct("1234567890"));
       assertFalse(recentlyViewed.isInList("0987654321"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testIsInList_Found() {
       recentlyViewed.getProducts().add(new MiniProduct("1234567890"));
       assertTrue(recentlyViewed.isInList("1234567890"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testIsInList_NullList() {
         assertFalse(recentlyViewed.isInList("1234567890"));
// BEGIN DIFF
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testIsInList_NullAsin() {
       recentlyViewed.getProducts().add(new MiniProduct("1234567890"));
       assertFalse(recentlyViewed.isInList(null));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testIsInList_EmptyString() {
       recentlyViewed.getProducts().add(new MiniProduct("1234567890"));
       assertFalse(recentlyViewed.isInList(""));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testIsInList_CaseInsensitive() {
       recentlyViewed.getProducts().add(new MiniProduct("1234567890"));
       assertTrue(recentlyViewed.isInList("1234567890"));
       assertTrue(recentlyViewed.isInList("1234567890"));
       assertTrue(recentlyViewed.isInList("1 2 3 4 5 6 7 8 9 0"));
// END DIFF
     }
 }
 
```

### Failed Test: `RecentlyViewed_addProduct_0_0_Test.java`

**Model:** meta-llama/Llama-3.2-1B-Instruct

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_Llama-3.2-1B-Instruct/a4j/net/kencochrane/a4j/beans/failedtests/RecentlyViewed_addProduct_0_0_Test.java`

```java
--- 
+++ 
         // Assert
         verify(mockMiniProduct).getAsin();
     }
+
// BEGIN DIFF
   @Test
   public void testAddProduct_Null() {
       // Arrange
       mockMiniProduct = new MiniProduct();
       // Act
       recentlyViewed.addProduct(mockMiniProduct);
       // Assert
       verify(mockMiniProduct, times(1)).getAsin();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testAddProduct_EmptyList() {
       // Arrange
       List<MiniProduct> products = new ArrayList<>();
       // Act
       recentlyViewed.addProduct(new MiniProduct());
       // Assert
       verify(mockMiniProduct, times(1)).getAsin();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testAddProduct_NullProduct() {
       // Arrange
       mockMiniProduct = new MiniProduct();
       // Act
       recentlyViewed.addProduct(mockMiniProduct);
       // Assert
       verify(mockMiniProduct, times(1)).getAsin();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testAddProduct_InList() {
       // Arrange
       List<MiniProduct> products = new ArrayList<>();
       products.add(new MiniProduct());
       // Act
       recentlyViewed.addProduct(new MiniProduct());
       // Assert
       verify(mockMiniProduct, times(1)).getAsin();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testAddProduct_InList_Null() {
       // Arrange
       List<MiniProduct> products = new ArrayList<>();
       products.add(new MiniProduct());
       // Act
       recentlyViewed.addProduct(null);
       // Assert
       verify(mockMiniProduct, times(1)).getAsin();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testAddProduct_InList_Empty() {
       // Arrange
       List<MiniProduct> products = new ArrayList<>();
       // Act
       recentlyViewed.addProduct(new MiniProduct());
       // Assert
       verify(mockMiniProduct, times(1)).getAsin();
   }
// END DIFF
 }
```

## Source File: `../SF110/2_a4j/src/main/java/net/kencochrane/a4j/beans/RecentlyViewed.java`

```java
/*
Copyright (c) 2003, Ken Cochrane
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted
provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    * Neither the name of Ken Cochrane nor the names of its contributors may be used to endorse
    or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package net.kencochrane.a4j.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: Jul 6, 2003
 * Time: 5:00:56 PM
 *
 *
 */
public class RecentlyViewed implements Serializable {

    private ArrayList products = new ArrayList();

    public void addProduct(MiniProduct miniProd) {
        if (miniProd != null && !(isInList(miniProd.getAsin()))) {
            products.add(miniProd);
        }
    }

    public ArrayList getProducts() {
        return products;
    }

    public int getNumProducts() {
        return products.size();
    }

    public boolean isInList(String asin) {
        boolean isIn = false;

        if (products != null && products.size() > 0) {
            MiniProduct mp = null;
            for (int x = 0; x < products.size(); x++) {
                mp = (MiniProduct) products.get(x);
                if (mp != null) {
                    if (mp.getAsin() != null && mp.getAsin().trim().equalsIgnoreCase(asin.trim())) {
                        isIn = true;
                        break;
                    }
                }
            }
        }

        return isIn;
    }
}

```



=============================================================

