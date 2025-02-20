### Failed Test: `HibernateSessionFactory_closeSession_1_0_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/7_sfmis/chatunitest-tests_gemini-1.5-flash-8b/sfmis/com/hf/sfm/util/failedtests/HibernateSessionFactory_closeSession_1_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void closeSessionWithOpenSession() {
       try {
           Field threadSessionField = HibernateSessionFactory.class.getDeclaredField("threadSession");
           threadSessionField.setAccessible(true);
           ((ThreadLocal<Session>) threadSessionField.get(sessionFactory)).set(mockSession);
       } catch (NoSuchFieldException | IllegalAccessException e) {
           e.printStackTrace();
       }
       doNothing().when(mockSession).close();
       HibernateSessionFactory.closeSession();
       verify(mockSession).close();
       try {
           Field threadSessionField = HibernateSessionFactory.class.getDeclaredField("threadSession");
           threadSessionField.setAccessible(true);
           assertNull(((ThreadLocal<Session>) threadSessionField.get(sessionFactory)).get());
       } catch (NoSuchFieldException | IllegalAccessException e) {
           e.printStackTrace();
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void closeSessionWithNoOpenSession() {
         HibernateSessionFactory.closeSession();
         try {
```

### Failed Test: `HibernateSessionFactory_closeSession_1_0_Test.java`

**Model:** gpt-4o-mini

**Failed Test File:** `../SF110/7_sfmis/chatunitest-tests_gpt-4o-mini/sfmis/com/hf/sfm/util/failedtests/HibernateSessionFactory_closeSession_1_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   public void testCloseSession_Success() {
       // Arrange
       doNothing().when(mockSession).close();
       // Act
       HibernateSessionFactory.closeSession();
       // Assert
       assertNull(HibernateSessionFactory.threadSession.get());
       verify(mockSession, times(1)).close();
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void testCloseSession_NullSession() {
         // Arrange
         HibernateSessionFactory.threadSession.set(null);
```

### Failed Test: `HibernateSessionFactory_closeSession_1_0_Test.java`

**Model:** Qwen/Qwen2.5-Coder-32B-Instruct

**Failed Test File:** `../SF110/7_sfmis/chatunitest-tests_Qwen2.5-Coder-32B-Instruct/sfmis/com/hf/sfm/util/failedtests/HibernateSessionFactory_closeSession_1_0_Test.java`

```java
--- 
+++ 
         verify(logMock, never()).debug("关闭session成功！");
         verify(logMock, never()).debug("关闭session失败！");
     }
+
// BEGIN DIFF
   @Test
   void testCloseSession_SessionIsNotNullAndCloseSucceeds() {
       // Arrange
       HibernateSessionFactory.threadSession.set(sessionMock);
       doNothing().when(sessionMock).close();
       // Act
       HibernateSessionFactory.closeSession();
       // Assert
       verify(sessionMock).close();
       verify(logMock).debug("关闭session成功！");
       verify(logMock, never()).debug("关闭session失败！");
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   void testCloseSession_SessionIsNotNullAndCloseFails() {
       // Arrange
       HibernateSessionFactory.threadSession.set(sessionMock);
       doThrow(new HibernateException("Test Exception")).when(sessionMock).close();
       // Act
       HibernateSessionFactory.closeSession();
       // Assert
       verify(sessionMock).close();
       verify(logMock, never()).debug("关闭session成功！");
       verify(logMock).debug("关闭session失败！");
   }
// END DIFF
 }
```

## Source File: `../SF110/7_sfmis/src/main/java/com/hf/sfm/util/HibernateSessionFactory.java`

```java
package com.hf.sfm.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

	private static Log log = LogFactory.getLog(HibernateSessionFactory.class);
	
	private static SessionFactory sessionFactory;
	static{
		try{
		sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
		}catch(HibernateException e){
			log.error("加载hibernate.cfg.xml失败！");
		}
	}
	
	public static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
	
	public static Session currentSession(){
		Session s = threadSession.get();
		try {
			if(s==null){
				s=sessionFactory.openSession();
				threadSession.set(s);
				log.debug("创建session成功！");
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("创建session失败！");
		}
		return s;
	}
	
	public static void closeSession(){
		Session s = threadSession.get();
		threadSession.set(null);
		try {
			if(s!=null){
				s.close();
				log.debug("关闭session成功！");
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("关闭session失败！");
		}
	}
	
	public static void main(String[] args) {
		HibernateSessionFactory.currentSession();
		HibernateSessionFactory.closeSession();
	}
}

```



=============================================================

