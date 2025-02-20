### Failed Test: `Loader_getParams_14_0_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/7_sfmis/chatunitest-tests_codestral-latest/sfmis/com/hf/sfm/util/failedtests/Loader_getParams_14_0_Test.java`

```java
--- 
+++ 
         verifyNoInteractions(query);
         assertEquals(query, resultQuery);
     }
+
// BEGIN DIFF
   @Test
   void testGetParamsWithInvalidDateFormat() throws ParseException {
       String[][] params = { { "invalid-date" }, { "Date" } };
       Query resultQuery = loader.getParams(query, params);
       verify(query).setParameter(0, null);
       assertEquals(query, resultQuery);
   }
// END DIFF
 }
```

### Failed Test: `Loader_collectToMap_16_2_Test.java`

**Model:** codestral-latest

**Failed Test File:** `../SF110/7_sfmis/chatunitest-tests_codestral-latest/sfmis/com/hf/sfm/util/failedtests/Loader_collectToMap_16_2_Test.java`

```java
--- 
+++ 
         assertNotNull(data);
         assertEquals(0, data.size());
     }
+
// BEGIN DIFF
   @Test
   void testCollectToMapSingleColumn() throws Exception {
       // Prepare test data
       colNames = new String[] { "col1" };
       Field colNamesField = Loader.class.getDeclaredField("colNames");
       colNamesField.setAccessible(true);
       colNamesField.set(loader, colNames);
       rs.add(new Object[] { "value1" });
       rs.add(new Object[] { "value2" });
       // Invoke the method
       loader.collectToMap("combo");
       // Verify the results
       ListRange range = loader.getRange();
       assertNotNull(range);
       assertEquals(totalCount, range.getTotalSize());
       List<Object> data = range.getData();
       assertNotNull(data);
       assertEquals(rs.size(), data.size());
       for (int i = 0; i < data.size(); i++) {
           HashMap<String, Object> map = (HashMap<String, Object>) data.get(i);
           assertEquals("value" + (i + 1), map.get("col1"));
       }
   }
// END DIFF
 }
```

### Failed Test: `Loader_collectToMap_16_0_Test.java`

**Model:** gpt-4o-mini

**Failed Test File:** `../SF110/7_sfmis/chatunitest-tests_gpt-4o-mini/sfmis/com/hf/sfm/util/failedtests/Loader_collectToMap_16_0_Test.java`

```java
--- 
+++ 
     }
 
     @Test
// BEGIN DIFF
   void testCollectToMapWithComboFlag() {
       // Prepare mock data
       List<Object[]> mockRs = new ArrayList<>();
       mockRs.add(new Object[] { "1", "Item 1" });
       mockRs.add(new Object[] { "2", "Item 2" });
       loader.setRs(mockRs);
       // Call the method under test
       loader.collectToMap("combo");
       // Verify the results
       ListRange range = loader.getRange();
       assertNotNull(range);
       assertEquals(2, range.getTotalSize());
       List<Object> data = range.getData();
       assertEquals(2, data.size());
       HashMap<String, Object> firstRow = (HashMap<String, Object>) data.get(0);
       assertEquals("1", firstRow.get("value"));
       assertEquals("Item 1", firstRow.get("text"));
       HashMap<String, Object> secondRow = (HashMap<String, Object>) data.get(1);
       assertEquals("2", secondRow.get("value"));
       assertEquals("Item 2", secondRow.get("text"));
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void testCollectToMapWithoutComboFlag() {
         // Prepare mock data
         List<Object[]> mockRs = new ArrayList<>();
         assertEquals("Item 2", secondRow.get("name"));
         assertEquals("Extra Data 2", secondRow.get("extra"));
     }
+
// BEGIN DIFF
   @Test
   void testCollectToMapWithEmptyResultSet() {
       // Prepare empty result set
       loader.setRs(new ArrayList<>());
       // Call the method under test
       loader.collectToMap("combo");
       // Verify the results
       ListRange range = loader.getRange();
       assertNotNull(range);
       assertEquals(0, range.getTotalSize());
       assertTrue(range.getData().isEmpty());
   }
// END DIFF
 }
```

## Source File: `../SF110/7_sfmis/src/main/java/com/hf/sfm/util/Loader.java`

```java
package com.hf.sfm.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class Loader {

	private String sql;
	private String filepath;
	private int start;
	private int limit;
	private int totalCount;
	private String[] colNames;
	private String[][] pas;
	private String sort;
	private String dir;
    private final String XMLPATH="sqlfolder/";
    private Session session = null;
    private Query query;
    private List rs;
    private boolean paging;
    private ListRange range;
    private String querySql;
    private String queryValue;
	
	
	public ListRange getRange() {
		return range;
	}

	public void setRange(ListRange range) {
		this.range = range;
	}

	public List getRs() {
		return rs;
	}

	public void setRs(List rs) {
		this.rs = rs;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String[] getColNames() {
		return colNames;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	public void run(BasePara basePara){
		filepath = basePara.getSqlpath();
		if(paging=basePara.isPaging()){
			start = basePara.getStart();
			limit = basePara.getLimit();
		}
		sort = basePara.getSort();
		dir = basePara.getDir();
		pas = basePara.single2plannar();
		this.parseXML();
		querySql = querySql==null?basePara.getQuerySql():querySql;
		queryValue = basePara.getQueryValue();
		this.getCount();
		this.getColsName();
		this.loadDataWithSql();
		
	}
	
	/**
	 *从xml解析sql 
	 *
	 */
	public void parseXML(){
		String pa = this.getClass().getResource("Loader.class").toString();
		String rootpath = pa.substring(pa.indexOf(":")+2, pa.lastIndexOf("classes")+8).replaceAll("%20", " ");
		String[] path = this.filepath.split("//");
		String filepath = rootpath+XMLPATH+path[0]+".xml";
		File file = new File(filepath);
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			for(Iterator it = root.elementIterator();it.hasNext();){
				Element el = (Element) it.next();
				if(el.getName().equals(path[1])){
					sql = el.elementText("main_sql");
					querySql = el.elementText("query_sql");
					break;
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *获取总记录数 
	 */
	public void getCount(){
		String subsql="";
		this.getQuerySql();
		if(this.sql.lastIndexOf("group by")!=-1){
			subsql = this.sql.substring(this.sql.lastIndexOf("from"), this.sql.lastIndexOf("group by"));
		}else{
			subsql = this.sql.substring(this.sql.lastIndexOf("from"));
		}
		session = HibernateSessionFactory.currentSession();
		query = session.createSQLQuery("select count(1) "+subsql);
		if(pas!=null){
			query = this.getParams(query, pas);
		}
		totalCount = Integer.parseInt(query.uniqueResult()+"");
		HibernateSessionFactory.closeSession();
	}
	
	/**
	 *获取sql中的字段名集合 
	 */
	public void getColsName(){
		String colssql = this.sql.substring(this.sql.lastIndexOf("select")+6,this.sql.lastIndexOf("from"));
		String[] cols = colssql.split(",");
		colNames = new String[cols.length];
		for(int i=0;i<cols.length;i++){
			String[] subcols=new String[cols.length];
			if(cols[i].indexOf(" as ")>0){
				subcols = cols[i].split("as");
				colNames[i]=subcols[1].trim();
			}else{
				if(cols[i].indexOf(".")>0){
					colNames[i]=cols[i].substring(cols[i].indexOf(".")+1).trim();
				}else{
					colNames[i]=cols[i].trim();
				}
			}
		}
	}
	
	/**
	 *查询数据 
	 */
	public List loadDataWithSql(){
		session = HibernateSessionFactory.currentSession();
		this.getQuerySql();
		if(sort!=null&&sort!=""){
			if(this.sql.indexOf("order by")>0){
				this.sql=this.sql.substring(0, this.sql.indexOf("order by"))+" order by "+sort+" "+dir;
			}else{
				this.sql=this.sql+" order by "+sort+" "+dir;
			}
		}
		query = session.createSQLQuery(this.sql);
		if(pas!=null){
			query = this.getParams(query, pas);
		}
		if(paging){
		query.setFirstResult(start);
		query.setMaxResults(limit);
		}
		List rows = query.list();
		HibernateSessionFactory.closeSession();
		this.setRs(rows);
		return rows;
	}
	
	public void getQuerySql(){
		int wherenum,ordernum,groupnum;
		System.out.println("querySql:"+querySql+",sql:"+sql);
		if(querySql!=null){
			wherenum=this.sql.indexOf("where");
			ordernum = this.sql.indexOf("order by");
			groupnum = this.sql.indexOf("group by");
			querySql = querySql.replace("?", queryValue);
			System.out.println("&&&&&&&&querySql:"+querySql);
			if(wherenum>0){
				if(groupnum>0){
					this.sql = this.sql.substring(0, groupnum)+" and ("+querySql+")"+this.sql.substring(groupnum);
				}else if(ordernum>0){
						this.sql = this.sql.substring(0, ordernum)+" and ("+querySql+")"+this.sql.substring(ordernum);
				}
			}else{
				if(groupnum>0){
					this.sql = this.sql.substring(0, groupnum)+" where ("+querySql+")"+this.sql.substring(groupnum);
				}else if(ordernum>0){
					this.sql = this.sql.substring(0, ordernum)+" where ("+querySql+")"+this.sql.substring(ordernum);
				}else{
					this.sql=this.sql+ " where ("+querySql+")";
				}
			}
		}
	}
	
	/**
	 *接收参数 
	 */
	public Query getParams(Query query, String[][] params) {
		if (params != null) {
			String[] pValue = params[0];// 参数值
			String[] pType = params[1]; // 参数值类型
			for (int i = 0; i < pValue.length; i++) {
				if (pType[i].equals("String")) {
					query.setParameter(i, pValue[i]);
				} else if (pType[i].equals("Long")) {
					query.setParameter(i, Long.parseLong(pValue[i]));
				}else if(pType[i].equals("Integer")){
					query.setParameter(i, Integer.parseInt(pValue[i]));
				} else if (pType[i].equals("Double")) {
					query.setParameter(i, Double.parseDouble(pValue[i]));
				} else if (pType[i].equals("Date")) {
					try {
						query.setParameter(i,new SimpleDateFormat("yyyy-MM-dd").parse(pValue[i]));
					} catch (HibernateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return query;
	}
	
	/**
	 *首页Tree数据 
	 */
	public String getArrayResults(){
		List rows = this.getRs();
		if(rows.size()==0){return "";}
		String jsonstr="";
		for(Iterator<?> it = rows.iterator();it.hasNext();){
			Object[] obj=(Object[]) it.next();
			String constr = "{";
			for(int i=0;i<obj.length;i++){
				if(i==obj.length-1){constr+='"'+colNames[i]+'"'+":"+'"'+obj[i]+'"';}
				else{constr+='"'+colNames[i]+'"'+":"+'"'+obj[i]+'"'+",";}
			}
			constr+="}";
			jsonstr+=constr+",";
		}
		jsonstr = "["+jsonstr.substring(0, jsonstr.lastIndexOf(","))+"]";
		return jsonstr;
	}
	
	public void collectToMap(String flag) {
		ArrayList<Object> al = null;
		HashMap<String,Object> map = null;
		String[] colNameArray = this.getColNames();
		if(flag.equals("combo")){
			colNameArray[0]="value";
			colNameArray[1]="text";
		}
		al = new ArrayList<Object>();
		List rs =this.getRs();
		if (rs.size()>0) {
			al = new ArrayList<Object>();
			for (Iterator<?> it = rs.iterator(); it.hasNext();) {
				Object[] obj = null;
				if(colNameArray.length==1){
					obj = new Object[]{it.next()};
				}else{
					obj = (Object[]) it.next();
				}
				map = new HashMap<String,Object>();
				for (int i = 0; i < colNameArray.length; i++) {
					String tag = colNameArray[i];
					map.put(tag, obj[i]);
				}
				al.add(map);
			}
		}
		range = new ListRange();
		range.setData(al);
		range.setTotalSize(this.getTotalCount());
	}
	
	
	public void collectToMap() {
		this.collectToMap("");
	}
	
}

```



=============================================================

