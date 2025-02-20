### Failed Test: `Search_Keyword_1_1_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_codestral-latest/a4j/net/kencochrane/a4j/DAO/failedtests/Search_Keyword_1_1_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   public void testKeyword_FileFound() throws Exception {
       String searchTerm = "example";
       String productLine = "line";
       String type = "type";
       String page = "1";
       FileInputStream mockFileInputStream = mock(FileInputStream.class);
       when(fileUtil.fetchKeywordSearchFile(searchTerm, productLine, type, page)).thenReturn(mockFileInputStream);
       ProductInfo expectedProductInfo = new ProductInfo();
       JOXBeanInputStream joxIn = mock(JOXBeanInputStream.class);
       when(joxIn.readObject(ProductInfo.class)).thenReturn(expectedProductInfo);
       ProductInfo result = search.Keyword(searchTerm, productLine, type, page);
       assertNotNull(result);
       assertEquals(expectedProductInfo, result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testKeyword_FileNotFound() throws Exception {
         String searchTerm = "example";
         String productLine = "line";
         ProductInfo result = search.Keyword(searchTerm, productLine, type, page);
         assertNull(result);
     }
+
// BEGIN DIFF
   @Test
   public void testKeyword_ExceptionThrown() throws Exception {
       String searchTerm = "example";
       String productLine = "line";
       String type = "type";
       String page = "1";
       when(fileUtil.fetchKeywordSearchFile(searchTerm, productLine, type, page)).thenThrow(new FileNotFoundException());
       ProductInfo result = search.Keyword(searchTerm, productLine, type, page);
       assertNull(result);
   }
// END DIFF
 }
```

### Failed Test: `Search_Generic_2_1_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_codestral-latest/a4j/net/kencochrane/a4j/DAO/failedtests/Search_Generic_2_1_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testGeneric_Success() throws IOException, ClassNotFoundException {
       String searchType = "type";
       String searchTerm = "term";
       String mode = "mode";
       String type = "type";
       String page = "page";
       String offer = "offer";
       FileInputStream mockFileInputStream = mock(FileInputStream.class);
       JOXBeanInputStream mockJOXBeanInputStream = mock(JOXBeanInputStream.class);
       ProductInfo mockProductInfo = mock(ProductInfo.class);
       when(fileUtil.fetchGenericSearchFile(searchType, searchTerm, mode, type, page, offer)).thenReturn(mockFileInputStream);
       when(mockJOXBeanInputStream.readObject(ProductInfo.class)).thenReturn(mockProductInfo);
       ProductInfo result = search.Generic(searchType, searchTerm, mode, type, page, offer);
       assertNotNull(result);
       assertEquals(mockProductInfo, result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testGeneric_FileInputNull() throws IOException {
         String searchType = "type";
         String searchTerm = "term";
         ProductInfo result = search.Generic(searchType, searchTerm, mode, type, page, offer);
         assertNull(result);
     }
+
// BEGIN DIFF
   @Test
   void testGeneric_Exception() throws IOException {
       String searchType = "type";
       String searchTerm = "term";
       String mode = "mode";
       String type = "type";
       String page = "page";
       String offer = "offer";
       when(fileUtil.fetchGenericSearchFile(searchType, searchTerm, mode, type, page, offer)).thenThrow(new IOException());
       ProductInfo result = search.Generic(searchType, searchTerm, mode, type, page, offer);
       assertNull(result);
   }
// END DIFF
 }
```

### Failed Test: `Search_ThirdParty_11_1_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_codestral-latest/a4j/net/kencochrane/a4j/DAO/failedtests/Search_ThirdParty_11_1_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   public void testThirdParty_Success() throws Exception {
       String sellerId = "123";
       String type = "type1";
       String page = "1";
       String status = "active";
       FileInputStream mockFileInputStream = mock(FileInputStream.class);
       when(fileUtil.fetchThirdPartySearchFile(sellerId, type, page, status)).thenReturn(mockFileInputStream);
       SellerSearch sellerSearch = new SellerSearch();
       JOXBeanInputStream joxIn = mock(JOXBeanInputStream.class);
       when(joxIn.readObject(SellerSearch.class)).thenReturn(sellerSearch);
       SellerSearch result = search.ThirdParty(sellerId, type, page, status);
       assertNotNull(result);
       assertEquals(sellerSearch, result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testThirdParty_FileNotFound() throws Exception {
         String sellerId = "123";
         String type = "type1";
         SellerSearch result = search.ThirdParty(sellerId, type, page, status);
         assertNull(result);
     }
+
// BEGIN DIFF
   @Test
   public void testThirdParty_Exception() throws Exception {
       String sellerId = "123";
       String type = "type1";
       String page = "1";
       String status = "active";
       when(fileUtil.fetchThirdPartySearchFile(sellerId, type, page, status)).thenThrow(new FileNotFoundException());
       SellerSearch result = search.ThirdParty(sellerId, type, page, status);
       assertNull(result);
   }
// END DIFF
 }
```

### Failed Test: `Search_AuthorSearch_5_0_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash-8b/a4j/net/kencochrane/a4j/DAO/failedtests/Search_AuthorSearch_5_0_Test.java`

```java
--- 
+++ 
     private ProductInfo productInfoMock;
 
     @Test
// BEGIN DIFF
   public void testAuthorSearch_validInput() {
       String authorName = "Jane Austen";
       String page = "1";
       when(search.Generic("AuthorSearch", authorName, "books", "lite", page, "all")).thenReturn(productInfoMock);
       ProductInfo result = search.AuthorSearch(authorName, page);
       assertEquals(productInfoMock, result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testAuthorSearch_nullAuthorName() {
         String authorName = null;
         String page = "1";
```

### Failed Test: `Search_DirectorSearch_6_1_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash-8b/a4j/net/kencochrane/a4j/DAO/failedtests/Search_DirectorSearch_6_1_Test.java`

```java
--- 
+++ 
 
     @InjectMocks
     private Search search = new Search();
+
// BEGIN DIFF
   @Test
   void directorSearch_validInput_returnsProductInfo() {
       // Arrange
       String directorName = "John Doe";
       String mode = "basic";
       String page = "1";
       when(search.Generic("DirectorSearch", directorName, mode, "lite", page, "all")).thenReturn(productInfoMock);
       // Act
       ProductInfo result = search.DirectorSearch(directorName, mode, page);
       // Assert
       assertNotNull(result);
       verify(search, times(1)).Generic("DirectorSearch", directorName, mode, "lite", page, "all");
   }
// END DIFF
 
     @Test
     void directorSearch_nullDirectorName_throwsIllegalArgumentException() {
```

### Failed Test: `Search_ThirdParty_11_3_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash-8b/a4j/net/kencochrane/a4j/DAO/failedtests/Search_ThirdParty_11_3_Test.java`

```java
--- 
+++ 
 class Search_ThirdParty_11_3_Test {
 
     @Test
// BEGIN DIFF
   void testThirdParty_fileExists() throws IOException {
       FileUtil fileUtil = Mockito.mock(FileUtil.class);
       FileInputStream fileIn = Mockito.mock(FileInputStream.class);
       JOXBeanInputStream joxIn = Mockito.mock(JOXBeanInputStream.class);
       // Initialize a SellerSearch object
       SellerSearch sellerDetails = new SellerSearch();
       Mockito.when(fileUtil.fetchThirdPartySearchFile("seller123", "typeA", "page1", "statusActive")).thenReturn(fileIn);
       Mockito.when(joxIn.readObject(SellerSearch.class)).thenReturn(sellerDetails);
       Search search = new Search();
       SellerSearch result = search.ThirdParty("seller123", "typeA", "page1", "statusActive");
       assertNotNull(result);
       assertEquals(sellerDetails, result);
       Mockito.verify(fileUtil).fetchThirdPartySearchFile("seller123", "typeA", "page1", "statusActive");
       Mockito.verify(joxIn).readObject(SellerSearch.class);
       Mockito.verifyNoMoreInteractions(fileUtil, joxIn);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testThirdParty_fileDoesNotExist() throws IOException {
       FileUtil fileUtil = Mockito.mock(FileUtil.class);
       Mockito.when(fileUtil.fetchThirdPartySearchFile("seller456", "typeB", "page2", "statusInactive")).thenReturn(null);
       Search search = new Search();
       SellerSearch result = search.ThirdParty("seller456", "typeB", "page2", "statusInactive");
       assertNull(result);
       Mockito.verify(fileUtil).fetchThirdPartySearchFile("seller456", "typeB", "page2", "statusInactive");
       Mockito.verifyNoMoreInteractions(fileUtil);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testThirdParty_exceptionDuringRead() {
         FileUtil fileUtil = Mockito.mock(FileUtil.class);
         FileInputStream fileIn = Mockito.mock(FileInputStream.class);
```

### Failed Test: `Search_Keyword_1_0_Test.java`

**Model:** gemini-1.5-flash

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash/a4j/net/kencochrane/a4j/DAO/failedtests/Search_Keyword_1_0_Test.java`

```java
--- 
+++ 
     Search search;
 
     @Test
// BEGIN DIFF
   void testKeyword_fileFound() throws Exception {
       FileInputStream mockFileInputStream = mock(FileInputStream.class);
       ProductInfo mockProductInfo = new ProductInfo();
       when(fileUtil.fetchKeywordSearchFile(anyString(), anyString(), anyString(), anyString())).thenReturn(mockFileInputStream);
       // Simplified mocking
       when(search.processInputStream(mockFileInputStream)).thenReturn(mockProductInfo);
       ProductInfo result = search.Keyword("testTerm", "testLine", "testType", "1");
       assertNotNull(result);
       verify(fileUtil).fetchKeywordSearchFile("testTerm", "testLine", "testType", "1");
       verify(search).processInputStream(mockFileInputStream);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testKeyword_fileNotFound() throws Exception {
         when(fileUtil.fetchKeywordSearchFile(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
// BEGIN DIFF
       ProductInfo result = search.Keyword("testTerm", "testLine", "testType", "1");
       assertNull(result);
       verify(fileUtil).fetchKeywordSearchFile("testTerm", "testLine", "testType", "1");
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testKeyword_exception() throws Exception {
       FileInputStream mockFileInputStream = mock(FileInputStream.class);
       when(fileUtil.fetchKeywordSearchFile(anyString(), anyString(), anyString(), anyString())).thenReturn(mockFileInputStream);
       // Simulate exception during read
       doThrow(new FileNotFoundException()).when(mockFileInputStream).read();
// END DIFF
         ProductInfo result = search.Keyword("testTerm", "testLine", "testType", "1");
         assertNull(result);
         verify(fileUtil).fetchKeywordSearchFile("testTerm", "testLine", "testType", "1");
```

### Failed Test: `Search_Generic_2_0_Test.java`

**Model:** gemini-1.5-flash

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash/a4j/net/kencochrane/a4j/DAO/failedtests/Search_Generic_2_0_Test.java`

```java
--- 
+++ 
         verify(fileUtil).fetchGenericSearchFile("type", "term", "mode", "type", "page", "offer");
     }
 
// BEGIN DIFF
   @Test
   void testGeneric_exception() throws Exception {
       FileInputStream mockFileInputStream = mock(FileInputStream.class);
       doThrow(new Exception("Simulated Exception")).when(mockFileInputStream.read());
       when(fileUtil.fetchGenericSearchFile(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(mockFileInputStream);
       JOXBeanInputStream mockJoxBeanInputStream = mock(JOXBeanInputStream.class);
       Field field = Search.class.getDeclaredField("joxIn");
       field.setAccessible(true);
       field.set(search, mockJoxBeanInputStream);
       ProductInfo result = search.Generic("type", "term", "mode", "type", "page", "offer");
       assertNull(result);
       verify(fileUtil).fetchGenericSearchFile("type", "term", "mode", "type", "page", "offer");
   }
// END DIFF
+
     static class Search {
 
         private JOXBeanInputStream joxIn;
```

### Failed Test: `Search_DirectorSearch_6_1_Test.java`

**Model:** gemini-1.5-flash

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash/a4j/net/kencochrane/a4j/DAO/failedtests/Search_DirectorSearch_6_1_Test.java`

```java
--- 
+++ 
     private Search searchUnderTest = new Search();
 
     @Test
// BEGIN DIFF
   void DirectorSearch_ValidInput_ReturnsProductInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       // Arrange
       String directorName = "Christopher Nolan";
       String mode = "title";
       String page = "1";
       // Replace with your actual ProductInfo object creation
       ProductInfo expectedProductInfo = new ProductInfo();
       Method genericMethod = Search.class.getDeclaredMethod("Generic", String.class, String.class, String.class, String.class, String.class, String.class);
       genericMethod.setAccessible(true);
       when(genericMethod.invoke(searchUnderTest, "DirectorSearch", directorName, mode, "lite", page, "all")).thenReturn(expectedProductInfo);
       // Act
       ProductInfo actualProductInfo = searchUnderTest.DirectorSearch(directorName, mode, page);
       // Assert
       assertEquals(expectedProductInfo, actualProductInfo);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void DirectorSearch_NullDirectorName_ReturnsProductInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       // Arrange
       String directorName = null;
       String mode = "title";
       String page = "1";
       // Replace with your actual ProductInfo object creation
       ProductInfo expectedProductInfo = new ProductInfo();
       Method genericMethod = Search.class.getDeclaredMethod("Generic", String.class, String.class, String.class, String.class, String.class, String.class);
       genericMethod.setAccessible(true);
       when(genericMethod.invoke(searchUnderTest, "DirectorSearch", directorName, mode, "lite", page, "all")).thenReturn(expectedProductInfo);
       // Act
       ProductInfo actualProductInfo = searchUnderTest.DirectorSearch(directorName, mode, page);
       // Assert
       assertEquals(expectedProductInfo, actualProductInfo);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void DirectorSearch_EmptyDirectorName_ReturnsProductInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       // Arrange
       String directorName = "";
       String mode = "title";
       String page = "1";
       // Replace with your actual ProductInfo object creation
       ProductInfo expectedProductInfo = new ProductInfo();
       Method genericMethod = Search.class.getDeclaredMethod("Generic", String.class, String.class, String.class, String.class, String.class, String.class);
       genericMethod.setAccessible(true);
       when(genericMethod.invoke(searchUnderTest, "DirectorSearch", directorName, mode, "lite", page, "all")).thenReturn(expectedProductInfo);
       // Act
       ProductInfo actualProductInfo = searchUnderTest.DirectorSearch(directorName, mode, page);
       // Assert
       assertEquals(expectedProductInfo, actualProductInfo);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void DirectorSearch_NullMode_ReturnsProductInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       // Arrange
       String directorName = "Christopher Nolan";
       String mode = null;
       String page = "1";
       // Replace with your actual ProductInfo object creation
       ProductInfo expectedProductInfo = new ProductInfo();
       Method genericMethod = Search.class.getDeclaredMethod("Generic", String.class, String.class, String.class, String.class, String.class, String.class);
       genericMethod.setAccessible(true);
       when(genericMethod.invoke(searchUnderTest, "DirectorSearch", directorName, mode, "lite", page, "all")).thenReturn(expectedProductInfo);
       // Act
       ProductInfo actualProductInfo = searchUnderTest.DirectorSearch(directorName, mode, page);
       // Assert
       assertEquals(expectedProductInfo, actualProductInfo);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void DirectorSearch_EmptyMode_ReturnsProductInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
         // Arrange
         String directorName = "Christopher Nolan";
```

### Failed Test: `Search_ThirdParty_11_0_Test.java`

**Model:** gemini-1.5-flash

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash/a4j/net/kencochrane/a4j/DAO/failedtests/Search_ThirdParty_11_0_Test.java`

```java
--- 
+++ 
     Search search;
 
     @Test
// BEGIN DIFF
   void ThirdParty_fileExists_returnsSellerDetails() throws Exception {
       FileInputStream mockFileInputStream = mock(FileInputStream.class);
       when(fileUtil.fetchThirdPartySearchFile(anyString(), anyString(), anyString(), anyString())).thenReturn(mockFileInputStream);
       JOXBeanInputStream mockJoxIn = mock(JOXBeanInputStream.class);
       SellerSearch mockSellerDetails = new SellerSearch();
       when(mockJoxIn.readObject(SellerSearch.class)).thenReturn(mockSellerDetails);
       when(new JOXBeanInputStream(mockFileInputStream)).thenReturn(mockJoxIn);
       SellerSearch result = search.ThirdParty("123", "type", "1", "active");
       assertNotNull(result);
       assertEquals(mockSellerDetails, result);
       verify(fileUtil).fetchThirdPartySearchFile("123", "type", "1", "active");
       verify(mockJoxIn).readObject(SellerSearch.class);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void ThirdParty_fileDoesNotExist_returnsNull() throws Exception {
         when(fileUtil.fetchThirdPartySearchFile(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
// BEGIN DIFF
       SellerSearch result = search.ThirdParty("123", "type", "1", "active");
       assertNull(result);
       verify(fileUtil).fetchThirdPartySearchFile("123", "type", "1", "active");
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void ThirdParty_exceptionDuringRead_returnsNull() throws Exception {
       FileInputStream mockFileInputStream = mock(FileInputStream.class);
       when(fileUtil.fetchThirdPartySearchFile(anyString(), anyString(), anyString(), anyString())).thenReturn(mockFileInputStream);
       JOXBeanInputStream mockJoxIn = mock(JOXBeanInputStream.class);
       when(new JOXBeanInputStream(mockFileInputStream)).thenReturn(mockJoxIn);
       doThrow(new Exception()).when(mockJoxIn).readObject(SellerSearch.class);
// END DIFF
         SellerSearch result = search.ThirdParty("123", "type", "1", "active");
         assertNull(result);
         verify(fileUtil).fetchThirdPartySearchFile("123", "type", "1", "active");
```

### Failed Test: `Search_SimilaritesSearch_12_0_Test.java`

**Model:** gemini-1.5-flash

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_gemini-1.5-flash/a4j/net/kencochrane/a4j/DAO/failedtests/Search_SimilaritesSearch_12_0_Test.java`

```java
--- 
+++ 
 
     @InjectMocks
     Search search;
+
// BEGIN DIFF
   @Test
   void testSimilaritesSearchFileFound() throws Exception {
       String asin = "B012345678";
       String page = "1";
       // Use a real file for testing.  Create a dummy "test.txt"
       InputStream mockInputStream = new FileInputStream("test.txt");
       when(fileUtil.fetchSimilarItems(asin, page)).thenReturn(mockInputStream);
       ProductInfo actualProductInfo = search.SimilaritesSearch(asin, page);
       assertNotNull(actualProductInfo);
   }
// END DIFF
 
     @Test
     void testSimilaritesSearchFileNotFound() throws Exception {
```

### Failed Test: `Search_Blended_0_0_Test.java`

**Model:** open-codestral-mamba

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_open-codestral-mamba/a4j/net/kencochrane/a4j/DAO/failedtests/Search_Blended_0_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testBlended_FileFound() throws Exception {
       String searchTerm = "testTerm";
       String type = "testType";
       BlendedSearch expectedResult = new BlendedSearch();
       // Populate expectedResult with test data
       FileInputStream fileIn = new FileInputStream("testFilePath");
       when(fileUtil.fetchBlendedSearchFile(searchTerm, type)).thenReturn(fileIn);
       BlendedSearch actualResult = search.Blended(searchTerm, type);
       assertEquals(expectedResult, actualResult);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testBlended_FileNotFound() throws Exception {
         String searchTerm = "testTerm";
         String type = "testType";
```

### Failed Test: `Search_Keyword_1_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-1.5B-Instruct

**Failed Test File:** `../SF110/2_a4j/chatunitest-tests_Qwen2.5-Coder-1.5B-Instruct/a4j/net/kencochrane/a4j/DAO/failedtests/Search_Keyword_1_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   public void testKeywordWithValidParameters() throws Exception {
       // Arrange
       when(fileUtil.fetchKeywordSearchFile("test", "line", "type", "1")).thenReturn(new FileInputStream("path/to/file"));
       // Assuming ProductInfo has appropriate getters and setters
       ProductInfo expectedProductInfo = new ProductInfo();
       // Act
       Search search = new Search();
       ProductInfo result = search.Keyword("test", "line", "type", "1");
       // Assert
       assertEquals(expectedProductInfo, result);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testKeywordWithInvalidParameters() throws Exception {
         // Arrange
         when(fileUtil.fetchKeywordSearchFile("test", "line", "type", "1")).thenReturn(null);
```

## Source File: `../SF110/2_a4j/src/main/java/net/kencochrane/a4j/DAO/Search.java`

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
import net.kencochrane.a4j.beans.BlendedSearch;
import net.kencochrane.a4j.beans.ProductInfo;
import net.kencochrane.a4j.beans.SellerSearch;
import net.kencochrane.a4j.file.FileUtil;

import java.io.FileInputStream;

//import org.apache.log4j.Logger;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 23, 2003
 * Time: 2:49:48 PM
 *
 */
public class Search {
    //   Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param searchTerm
     * @param type
     * @return
     */
    public BlendedSearch Blended(String searchTerm, String type) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        BlendedSearch testBean = new BlendedSearch();
        try {
            FileInputStream fileIn = fileUtil.fetchBlendedSearchFile(searchTerm, type);

            if (fileIn != null) {
                joxIn = new JOXBeanInputStream(fileIn);
                testBean = (BlendedSearch) joxIn.readObject(BlendedSearch.class);
            } else {
                //           log.debug("Error no fileInput");
                testBean = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return testBean;
    }

    /**
     *
     * @param searchTerm
     * @param productLine
     * @param type
     * @param page
     * @return
     */
    public ProductInfo Keyword(String searchTerm, String productLine, String type, String page) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ProductInfo productInfo = new ProductInfo();
        try {
            FileInputStream fileIn = fileUtil.fetchKeywordSearchFile(searchTerm, productLine, type, page);

            if (fileIn != null) {
                joxIn = new JOXBeanInputStream(fileIn);
                productInfo = (ProductInfo) joxIn.readObject(ProductInfo.class);
            } else {
                //            log.debug("Error no fileInput");
                productInfo = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return productInfo;
    }

    /**
     *
     * @param searchType
     * @param searchTerm
     * @param mode
     * @param type
     * @param page
     * @param offer
     * @return
     */
    public ProductInfo Generic(String searchType, String searchTerm, String mode, String type, String page, String offer) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ProductInfo productInfo = new ProductInfo();

        try {
            FileInputStream fileIn = fileUtil.fetchGenericSearchFile(searchType, searchTerm, mode, type, page, offer);

            if (fileIn != null) {
                joxIn = new JOXBeanInputStream(fileIn);
                productInfo = (ProductInfo) joxIn.readObject(ProductInfo.class);
            } else {
                //              log.debug("Error no fileInput");
                productInfo = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }


        return productInfo;
    }

    /**
     * Search for an actor
     * @param actorName name to search for
     * @param mode (dvd, vhs, video)
     * @param page
     * @return
     */
    public ProductInfo ActorSearch(String actorName, String mode, String page) {
        String searchType = "ActorSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, actorName, mode, type, page, offer);
    }

    /**
     * Search for an Artist
     * @param artistName name to search for
     * @param mode (music, classical)
     * @param page
     * @return
     */
    public ProductInfo ArtistSearch(String artistName, String mode, String page) {
        String searchType = "ArtistSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, artistName, mode, type, page, offer);
    }

    /**
     * Search for the author of books
     * @param authorName name of the author to search for
     * @param page
     * @return
     */
    public ProductInfo AuthorSearch(String authorName, String page) {
        String searchType = "AuthorSearch";
        String type = "lite";
        String offer = "all";
        String mode = "books";
        return Generic(searchType, authorName, mode, type, page, offer);
    }

    /**
     * Search for the director of movies
     * @param directorName
     * @param mode (dvd, vhs, video)
     * @param page
     * @return
     */
    public ProductInfo DirectorSearch(String directorName, String mode, String page) {
        String searchType = "DirectorSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, directorName, mode, type, page, offer);
    }

    /**
     *  Search by Manufacture
     * @param manufactureName
     * @param mode (electronics, kitchen, videogames, software, photo, pc-hardware)
     * @param page
     * @return
     */
    public ProductInfo ManufactureSearch(String manufactureName, String mode, String page) {
        String searchType = "ManufacturerSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, manufactureName, mode, type, page, offer);
    }

    /**
     * Search for cd's by UPC
     * @param upc
     * @param mode (music, classical)
     * @param page
     * @return
     */
    public ProductInfo UpcSearch(String upc, String mode, String page) {
        String searchType = "UpcSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, upc, mode, type, page, offer);
    }

    //listmania
    //TODO: test to make sure it works correctly. it is a little different then the generic search
    public ProductInfo ListmaniaSearch(String listId) {
        String searchType = "ListManiaSearch";
        String type = "lite";
        String offer = "all";
        String page = "1";
        String mode = "mode";
        return Generic(searchType, listId, mode, type, page, offer);
    }

    //WishList
   //todo never used need to confirm if works it is different then generic search
    public ProductInfo WishListSearch(String wishListId) {
        String searchType = "WishlistSearch";
        String type = "lite";
        String offer = "all";
        String mode = "mode";
        String page = "1";
        return Generic(searchType, wishListId, mode, type, page, offer);
    }

    //thirdpartysearch
    /**
     *
     * @param sellerId The sellers ID
     * @param type heavy or lite
     * @param page page number
     * @param status open or closed
     * @return
     */
    public SellerSearch ThirdParty(String sellerId, String type, String page, String status) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        SellerSearch sellerDetails = new SellerSearch();

        try {
            FileInputStream fileIn = fileUtil.fetchThirdPartySearchFile(sellerId, type, page, status);

            if (fileIn != null) {
                //         log.debug("file is good");
                joxIn = new JOXBeanInputStream(fileIn);
                sellerDetails = (SellerSearch) joxIn.readObject(SellerSearch.class);
            } else {
                //           log.debug("Error no fileInput");
                sellerDetails = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }


        return sellerDetails;
    }


    //similarities
    public ProductInfo SimilaritesSearch(String asin, String page) {

        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ProductInfo productInfo = new ProductInfo();

        try {
            //       log.debug("in Similar Search");
            FileInputStream fileIn = fileUtil.fetchSimilarItems(asin, page);

            if (fileIn != null) {
                //        log.debug("Similar Search Filein not null");
                joxIn = new JOXBeanInputStream(fileIn);
                productInfo = (ProductInfo) joxIn.readObject(ProductInfo.class);
            } else {
                //           log.debug("Error no fileInput");
                productInfo = null;
            }
        } catch (Exception exc) {
            //       log.error(exc.toString());
            exc.printStackTrace();
        }

        //     log.debug("out similar search");
        return productInfo;
    }

    //todo sellerprofile

    //todo powersearch

    //todo MarketPlaceSearch

    //todo ExchangeSearch

    //todo add keywords to normal searches for more refined searching

    //todo add price ranges to searches to get within a low and high price level

    //todo add sorting to searches

    //todo create an advanced search that combines powersearch with keywords and price ranges and sorting.

}

```



=============================================================

