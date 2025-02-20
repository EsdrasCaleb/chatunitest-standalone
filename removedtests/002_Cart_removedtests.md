### Failed Test: `Cart_addToExistingCart_1_0_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_codestral-latest/a4j/net/kencochrane/a4j/DAO/failedtests/Cart_addToExistingCart_1_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testAddToExistingCart() throws FileNotFoundException, IOException {
       String cartId = "123";
       String hmac = "abc";
       String asin = "456";
       String quantity = "2";
       String queryString = "queryString";
       File file = mock(File.class);
       FileInputStream fin = mock(FileInputStream.class);
       JOXBeanInputStream joxIn = mock(JOXBeanInputStream.class);
       ShoppingCartResponse cartBean = mock(ShoppingCartResponse.class);
       ShoppingCart shoppingCart = mock(ShoppingCart.class);
       when(query.AddToExistingCart(asin, quantity, cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(file);
       when(new FileInputStream(file)).thenReturn(fin);
       when(new JOXBeanInputStream(fin)).thenReturn(joxIn);
       when(joxIn.readObject(ShoppingCartResponse.class)).thenReturn(cartBean);
       when(cartBean.getShoppingCart()).thenReturn(shoppingCart);
       ShoppingCart result = cart.addToExistingCart(cartId, hmac, asin, quantity);
       assertNotNull(result);
       assertEquals(shoppingCart, result);
       verify(query).AddToExistingCart(asin, quantity, cartId, hmac);
       verify(fileUtil).downloadCart(queryString);
       verify(joxIn).close();
       verify(fin).close();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testAddToExistingCartFileNull() {
       String cartId = "123";
       String hmac = "abc";
       String asin = "456";
       String quantity = "2";
       String queryString = "queryString";
       when(query.AddToExistingCart(asin, quantity, cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(null);
       ShoppingCart result = cart.addToExistingCart(cartId, hmac, asin, quantity);
       assertNull(result);
       verify(query).AddToExistingCart(asin, quantity, cartId, hmac);
       verify(fileUtil).downloadCart(queryString);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testAddToExistingCartCartBeanNull() throws FileNotFoundException, IOException {
       String cartId = "123";
       String hmac = "abc";
       String asin = "456";
       String quantity = "2";
       String queryString = "queryString";
       File file = mock(File.class);
       FileInputStream fin = mock(FileInputStream.class);
       JOXBeanInputStream joxIn = mock(JOXBeanInputStream.class);
       when(query.AddToExistingCart(asin, quantity, cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(file);
       when(new FileInputStream(file)).thenReturn(fin);
       when(new JOXBeanInputStream(fin)).thenReturn(joxIn);
       when(joxIn.readObject(ShoppingCartResponse.class)).thenReturn(null);
       ShoppingCart result = cart.addToExistingCart(cartId, hmac, asin, quantity);
       assertNull(result);
       verify(query).AddToExistingCart(asin, quantity, cartId, hmac);
       verify(fileUtil).downloadCart(queryString);
       verify(joxIn).close();
       verify(fin).close();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testAddToExistingCartShoppingCartNull() throws FileNotFoundException, IOException {
         String cartId = "123";
         String hmac = "abc";
```

### Failed Test: `Cart_modifyCart_3_4_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_codestral-latest/a4j/net/kencochrane/a4j/DAO/failedtests/Cart_modifyCart_3_4_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   public void testModifyCart_QuantityZero() throws Exception {
       String hmac = "testHmac";
       String cartId = "testCartId";
       String itemId = "testItemId";
       String quantity = "0";
       ShoppingCart expectedCart = new ShoppingCart();
       when(cart.RemoveFromCart(hmac, cartId, itemId)).thenReturn(expectedCart);
       ShoppingCart result = cart.modifyCart(hmac, cartId, itemId, quantity);
       assertEquals(expectedCart, result);
       verify(cart, times(1)).RemoveFromCart(hmac, cartId, itemId);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testModifyCart_QuantityNotZero() throws Exception {
       String hmac = "testHmac";
       String cartId = "testCartId";
       String itemId = "testItemId";
       String quantity = "2";
       String queryString = "testQueryString";
       File file = mock(File.class);
       FileInputStream fin = mock(FileInputStream.class);
       JOXBeanInputStream joxIn = mock(JOXBeanInputStream.class);
       ShoppingCartResponse cartBean = mock(ShoppingCartResponse.class);
       ShoppingCart expectedCart = new ShoppingCart();
       when(query.ModifyCart(itemId, quantity, cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(file);
       when(new FileInputStream(file)).thenReturn(fin);
       when(new JOXBeanInputStream(fin)).thenReturn(joxIn);
       when(joxIn.readObject(ShoppingCartResponse.class)).thenReturn(cartBean);
       when(cartBean.getShoppingCart()).thenReturn(expectedCart);
       ShoppingCart result = cart.modifyCart(hmac, cartId, itemId, quantity);
       assertEquals(expectedCart, result);
       verify(query, times(1)).ModifyCart(itemId, quantity, cartId, hmac);
       verify(fileUtil, times(1)).downloadCart(queryString);
       verify(joxIn, times(1)).readObject(ShoppingCartResponse.class);
       verify(joxIn, times(1)).close();
       verify(fin, times(1)).close();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testModifyCart_FileNull() throws Exception {
       String hmac = "testHmac";
       String cartId = "testCartId";
       String itemId = "testItemId";
       String quantity = "2";
       String queryString = "testQueryString";
       when(query.ModifyCart(itemId, quantity, cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(null);
       ShoppingCart result = cart.modifyCart(hmac, cartId, itemId, quantity);
       assertNull(result);
       verify(query, times(1)).ModifyCart(itemId, quantity, cartId, hmac);
       verify(fileUtil, times(1)).downloadCart(queryString);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testModifyCart_FileNotFoundException() throws Exception {
       String hmac = "testHmac";
       String cartId = "testCartId";
       String itemId = "testItemId";
       String quantity = "2";
       String queryString = "testQueryString";
       File file = mock(File.class);
       FileInputStream fin = mock(FileInputStream.class);
       when(query.ModifyCart(itemId, quantity, cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(file);
       when(new FileInputStream(file)).thenThrow(FileNotFoundException.class);
       ShoppingCart result = cart.modifyCart(hmac, cartId, itemId, quantity);
       assertNull(result);
       verify(query, times(1)).ModifyCart(itemId, quantity, cartId, hmac);
       verify(fileUtil, times(1)).downloadCart(queryString);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testModifyCart_IOException() throws Exception {
         String hmac = "testHmac";
         String cartId = "testCartId";
```

### Failed Test: `Cart_GetItemsFromCart_4_2_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_codestral-latest/a4j/net/kencochrane/a4j/DAO/failedtests/Cart_GetItemsFromCart_4_2_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testGetItemsFromCart() throws FileNotFoundException, IOException {
       String hmac = "testHmac";
       String cartId = "testCartId";
       String queryString = "testQueryString";
       File file = mock(File.class);
       ShoppingCartResponse cartBean = mock(ShoppingCartResponse.class);
       ShoppingCart shoppingCart = mock(ShoppingCart.class);
       when(query.GetItemsFromCart(cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(file);
       when(new FileInputStream(file)).thenReturn(mock(FileInputStream.class));
       when(cartBean.getShoppingCart()).thenReturn(shoppingCart);
       ShoppingCart result = cart.GetItemsFromCart(hmac, cartId);
       assertNotNull(result);
       assertEquals(shoppingCart, result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testGetItemsFromCart_FileNull() {
         String hmac = "testHmac";
         String cartId = "testCartId";
         ShoppingCart result = cart.GetItemsFromCart(hmac, cartId);
         assertNull(result);
     }
+
// BEGIN DIFF
   @Test
   void testGetItemsFromCart_CartBeanNull() throws FileNotFoundException, IOException {
       String hmac = "testHmac";
       String cartId = "testCartId";
       String queryString = "testQueryString";
       File file = mock(File.class);
       when(query.GetItemsFromCart(cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(file);
       when(new FileInputStream(file)).thenReturn(mock(FileInputStream.class));
       ShoppingCart result = cart.GetItemsFromCart(hmac, cartId);
       assertNull(result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testGetItemsFromCart_Exception() throws FileNotFoundException, IOException {
       String hmac = "testHmac";
       String cartId = "testCartId";
       String queryString = "testQueryString";
       File file = mock(File.class);
       when(query.GetItemsFromCart(cartId, hmac)).thenReturn(queryString);
       when(fileUtil.downloadCart(queryString)).thenReturn(file);
       when(new FileInputStream(file)).thenThrow(new FileNotFoundException());
       ShoppingCart result = cart.GetItemsFromCart(hmac, cartId);
       assertNull(result);
   }
// END DIFF
 }
```

### Failed Test: `Cart_AddtoCart_0_1_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash-8b/a4j/net/kencochrane/a4j/DAO/failedtests/Cart_AddtoCart_0_1_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void addToCart_success() throws IOException {
       // Mock the successful scenario.
       File file = Mockito.mock(File.class);
       Mockito.when(fileUtil.downloadCart("someQueryString")).thenReturn(file);
       ShoppingCartResponse cartBean = new ShoppingCartResponse();
       // Mock a valid ShoppingCart
       cartBean.setShoppingCart(new ShoppingCart());
       // Mock the necessary object to avoid null pointer exception
       JOXBeanInputStream joxIn = Mockito.mock(JOXBeanInputStream.class);
       Mockito.when(joxIn.readObject(ShoppingCartResponse.class)).thenReturn(cartBean);
       ShoppingCart shoppingCart = cart.AddtoCart("asin123", "1");
       assertNotNull(shoppingCart, "ShoppingCart should not be null");
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void addToCart_fileNotFound() {
         Mockito.when(fileUtil.downloadCart(Mockito.anyString())).thenReturn(null);
         ShoppingCart shoppingCart = cart.AddtoCart("asin123", "1");
         ShoppingCart shoppingCart = cart.AddtoCart("asin123", "1");
         assertNull(shoppingCart, "ShoppingCart should be null if cartBean is null");
     }
+
// BEGIN DIFF
   // Add more tests for different error scenarios (e.g., IOException)
   @Test
   void addToCart_IOException() {
       Mockito.when(fileUtil.downloadCart(Mockito.anyString())).thenThrow(new IOException("Simulated IO exception"));
       ShoppingCart shoppingCart = cart.AddtoCart("asin123", "1");
       // ShoppingCart should be null if IO exception occurs
       assertNull(shoppingCart);
   }
// END DIFF
 }
```

### Failed Test: `Cart_addToExistingCart_1_1_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash-8b/a4j/net/kencochrane/a4j/DAO/failedtests/Cart_addToExistingCart_1_1_Test.java`

```java
--- 
+++ 
 Cart_addToExistingCart_1_1_Test {
 
     @Test
// BEGIN DIFF
   void addToExistingCart_success() throws IOException, ClassNotFoundException {
       // Mock dependencies
       Query query = Mockito.mock(Query.class);
       FileUtil fileUtil = Mockito.mock(FileUtil.class);
       ShoppingCartResponse cartBean = new ShoppingCartResponse();
       ShoppingCart shoppingCart = new ShoppingCart();
       cartBean.setShoppingCart(shoppingCart);
       // Setup expected behavior for mocks
       Mockito.when(query.AddToExistingCart(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn("someQueryString");
       File file = Mockito.mock(File.class);
       Mockito.when(fileUtil.downloadCart("someQueryString")).thenReturn(file);
       Mockito.when(fileUtil.downloadCart(Mockito.anyString())).thenReturn(file);
       Mockito.when(file.exists()).thenReturn(true);
       Mockito.when(fileUtil.downloadCart(Mockito.anyString())).thenReturn(file);
       Mockito.when(file.length()).thenReturn(10L);
       Mockito.when(file.canRead()).thenReturn(true);
       try (FileInputStream fin = new FileInputStream(file)) {
           // Mock JOXBeanInputStream
           JOXBeanInputStream joxIn = Mockito.mock(JOXBeanInputStream.class);
           Mockito.when(joxIn.readObject(ShoppingCartResponse.class)).thenReturn(cartBean);
           // Create Cart instance
           Cart cart = new Cart();
           cart.addToExistingCart("cartId", "hmac", "asin", "quantity");
           // Assertions
           Mockito.verify(query).AddToExistingCart("asin", "quantity", "cartId", "hmac");
           Mockito.verify(fileUtil).downloadCart("someQueryString");
           Mockito.verify(joxIn).readObject(ShoppingCartResponse.class);
           assertNotNull(cart.addToExistingCart("cartId", "hmac", "asin", "quantity"));
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void addToExistingCart_fileNotFound() {
         // Mock dependencies
         Query query = Mockito.mock(Query.class);
```

### Failed Test: `Cart_modifyCart_3_2_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash-8b/a4j/net/kencochrane/a4j/DAO/failedtests/Cart_modifyCart_3_2_Test.java`

```java
--- 
+++ 
 import net.kencochrane.a4j.file.FileUtil;
 
 class Cart_modifyCart_3_2_Test {
+
// BEGIN DIFF
   @Test
   void modifyCart_zeroQuantity_removesFromCart() {
       Cart cart = new Cart();
       // Mock necessary dependencies
       Query query = Mockito.mock(Query.class);
       FileUtil fileUtil = Mockito.mock(FileUtil.class);
       Mockito.when(query.ModifyCart(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn("mockQueryString");
       Mockito.when(fileUtil.downloadCart("mockQueryString")).thenReturn(new File("mockFile"));
       // Mock the return of RemoveFromCart
       ShoppingCart mockShoppingCart = new ShoppingCart();
       ShoppingCart mockRemoved = Mockito.mock(ShoppingCart.class);
       Mockito.when(cart.RemoveFromCart(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(mockRemoved);
       ShoppingCart result = cart.modifyCart("hmac", "cartId", "itemId", "0");
       // Verify that RemoveFromCart was called
       Mockito.verify(cart).RemoveFromCart("hmac", "cartId", "itemId");
       assertNotNull(result);
       assertEquals(mockRemoved, result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void modifyCart_nonZeroQuantity_updatesCart() {
       Cart cart = new Cart();
       Query query = Mockito.mock(Query.class);
       FileUtil fileUtil = Mockito.mock(FileUtil.class);
       // Mock the necessary parts for the non-zero quantity case
       Mockito.when(query.ModifyCart(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn("mockQueryString");
       Mockito.when(fileUtil.downloadCart("mockQueryString")).thenReturn(new File("mockFile"));
       ShoppingCartResponse mockShoppingCartResponse = Mockito.mock(ShoppingCartResponse.class);
       ShoppingCart mockShoppingCart = Mockito.mock(ShoppingCart.class);
       Mockito.when(mockShoppingCartResponse.getShoppingCart()).thenReturn(mockShoppingCart);
       Mockito.when(mockShoppingCartResponse.getShoppingCart()).thenReturn(mockShoppingCart);
       ShoppingCart result = cart.modifyCart("hmac", "cartId", "itemId", "10");
       assertNotNull(result);
       assertEquals(mockShoppingCart, result);
   }
// END DIFF
 
     @Test
     void modifyCart_downloadError_returnsNull() {
```

### Failed Test: `Cart_GetItemsFromCart_4_0_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash-8b/a4j/net/kencochrane/a4j/DAO/failedtests/Cart_GetItemsFromCart_4_0_Test.java`

```java
--- 
+++ 
 class Cart_GetItemsFromCart_4_0_Test {
 
     @Test
// BEGIN DIFF
   void testGetItemsFromCart_success() throws IOException, ClassNotFoundException {
       // Mock dependencies
       Query query = Mockito.mock(Query.class);
       FileUtil fileUtil = Mockito.mock(FileUtil.class);
       ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
       ShoppingCart shoppingCart = new ShoppingCart();
       shoppingCartResponse.setShoppingCart(shoppingCart);
       // Setup expected behavior
       Mockito.when(query.GetItemsFromCart("cartId", "hmac")).thenReturn("queryString");
       File file = Mockito.mock(File.class);
       Mockito.when(fileUtil.downloadCart("queryString")).thenReturn(file);
       Mockito.when(file.exists()).thenReturn(true);
       Mockito.when(file.canRead()).thenReturn(true);
       try (FileInputStream fin = Mockito.mock(FileInputStream.class)) {
           Mockito.when(fin.available()).thenReturn(100);
       } catch (IOException e) {
       }
       JOXBeanInputStream joxIn = Mockito.mock(JOXBeanInputStream.class);
       Mockito.when(joxIn.readObject(ShoppingCartResponse.class)).thenReturn(shoppingCartResponse);
       // Create Cart instance
       Cart cart = new Cart();
       // Execute the method under test
       ShoppingCart result = cart.GetItemsFromCart("hmac", "cartId");
       // Assertions
       assertNotNull(result);
       assertEquals(shoppingCart, result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testGetItemsFromCart_fileNotFound() {
         // Mock dependencies
         Query query = Mockito.mock(Query.class);
         // Assertions
         assertNull(result);
     }
+
// BEGIN DIFF
   @Test
   void testGetItemsFromCart_ioException() {
       // Mock dependencies
       Query query = Mockito.mock(Query.class);
       FileUtil fileUtil = Mockito.mock(FileUtil.class);
       File file = Mockito.mock(File.class);
       Mockito.when(query.GetItemsFromCart("cartId", "hmac")).thenReturn("queryString");
       Mockito.when(fileUtil.downloadCart("queryString")).thenReturn(file);
       Mockito.when(file.exists()).thenReturn(true);
       Mockito.when(file.canRead()).thenReturn(true);
       Mockito.doThrow(new IOException("Simulated IO exception")).when(fileUtil).downloadCart("queryString");
       Cart cart = new Cart();
       // Execute the method under test
       ShoppingCart result = cart.GetItemsFromCart("hmac", "cartId");
       // Assertions
       assertNull(result);
   }
// END DIFF
 }
```

### Failed Test: `Cart_modifyCart_3_0_Test.java`

**Model:** open-codestral-mamba

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_open-codestral-mamba/a4j/net/kencochrane/a4j/DAO/failedtests/Cart_modifyCart_3_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testModifyCartFileNotFound() {
       String hmac = "testHmac";
       String cartId = "testCartId";
       String itemId = "testItemId";
       String quantity = "5";
       ShoppingCart expectedCart = null;
       when(fileUtil.downloadCart(Mockito.anyString())).thenThrow(new FileNotFoundException());
       ShoppingCart actualCart = cart.modifyCart(hmac, cartId, itemId, quantity);
       assertEquals(expectedCart, actualCart);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testModifyCartIOException() {
         String hmac = "testHmac";
         String cartId = "testCartId";
```

## Source File: `../SF110/2_a4j/src/main/java/net/kencochrane/a4j/DAO/Cart.java`

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
package net.kencochrane.a4j.DAO;

import com.wutka.jox.JOXBeanInputStream;
import net.kencochrane.a4j.beans.ShoppingCart;
import net.kencochrane.a4j.beans.ShoppingCartResponse;
import net.kencochrane.a4j.data.Query;
import net.kencochrane.a4j.file.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import org.apache.log4j.Logger;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 24, 2003
 * Time: 12:55:37 PM
 *
 *
 */
public class Cart {
    //Logger log = Logger.getLogger(this.getClass());

    public ShoppingCart AddtoCart(String asin, String quantity) {
        //   log.debug("In Cart");
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;

        String queryString = query.AddtoCart(asin, quantity);
        //    log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);

        if (file != null) {
            //     log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //        log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //         log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    public ShoppingCart addToExistingCart(String cartId, String hmac, String asin, String quantity) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        String queryString = query.AddToExistingCart(asin, quantity, cartId, hmac);
        //    log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //      log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //        log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //          log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }


    public ShoppingCart clearCart(String hmac, String cartId) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        String queryString = query.ClearCart(cartId, hmac);
        //    log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //       log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //        log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //          log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    public ShoppingCart modifyCart(String hmac, String cartId, String itemId, String quantity) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        if (quantity.equalsIgnoreCase("0")) {
            //if they want to set quantity to 0 then remove from cart.
            return RemoveFromCart(hmac, cartId, itemId);
        }
        String queryString = query.ModifyCart(itemId, quantity, cartId, hmac);
        //    log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //        log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //          log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //           log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    public ShoppingCart GetItemsFromCart(String hmac, String cartId) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        String queryString = query.GetItemsFromCart(cartId, hmac);
        //     log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //         log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //          log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //           log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    public ShoppingCart RemoveFromCart(String hmac, String cartId, String itemId) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        String queryString = query.RemoveFromCart(itemId, cartId, hmac);
        //     log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //         log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //           log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //             log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }
}

```



=============================================================

