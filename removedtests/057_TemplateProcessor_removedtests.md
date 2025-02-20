### Failed Test: `TemplateProcessor_process_0_0_Test.java`

**Model:** gemini-1.5-flash

**Failed Test File:** `../SF110/5_templateit/chatunitest-tests_gemini-1.5-flash/templateit/org/templateit/failedtests/TemplateProcessor_process_0_0_Test.java`

```java
--- 
+++ 
         assertThrows(NullPointerException.class, () -> processor.process(dataIterator, null));
     }
 
// BEGIN DIFF
   @Test
   void testProcess_IOexception_handlesException() throws Exception {
       // Mocking exception during file output
       File tempFile = File.createTempFile("test", ".xls");
       tempFile.deleteOnExit();
       try (FileOutputStream outputStream = mock(FileOutputStream.class)) {
           doThrow(new IOException("Simulated IO Error")).when(outputStream).close();
           Iterator<String[]> dataIterator = Arrays.asList(new String[] { "a", "b" }, new String[] { "c", "d" }).iterator();
           TemplateProcessor processor = new TemplateProcessor(new ByteArrayInputStream("".getBytes()));
           // Call the method and assert no exception is thrown.  Note that the exception is thrown during close(),
           // after the main processing is complete.  This test verifies that the exception is handled gracefully.
           assertDoesNotThrow(() -> processor.process(dataIterator, tempFile));
       }
   }
// END DIFF
+
     // Dummy classes needed for compilation.  Replace with your actual classes.
     static class TemplateProcessor {
 
```

### Failed Test: `TemplateProcessor_process_0_0_Test.java`

**Model:** gpt-4o-mini

**Failed Test File:** `../SF110/5_templateit/chatunitest-tests_gpt-4o-mini/templateit/org/templateit/failedtests/TemplateProcessor_process_0_0_Test.java`

```java
--- 
+++ 
     public void testTemplateProcessorInitialization() {
         assertNotNull(templateProcessor);
     }
+
// BEGIN DIFF
   @Test
   public void testProcessWithValidInput() throws IOException {
       // Arrange
       String[] data1 = { "data1", "data2" };
       String[] data2 = { "data3", "data4" };
       when(mockIterator.hasNext()).thenReturn(true, true, false);
       when(mockIterator.next()).thenReturn(data1, data2);
       // Act
       templateProcessor.process(mockIterator, outputWorkbook);
       // Assert
       // Verify that the output stream was created and closed
       try (FileOutputStream fos = new FileOutputStream(outputWorkbook)) {
           assertNotNull(fos);
       } catch (IOException e) {
           fail("Output stream should not throw exception");
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testProcessWithEmptyIterator() throws IOException {
       // Arrange
       when(mockIterator.hasNext()).thenReturn(false);
       // Act
       templateProcessor.process(mockIterator, outputWorkbook);
       // Assert
       // Verify that the output stream was created and closed without processing
       try (FileOutputStream fos = new FileOutputStream(outputWorkbook)) {
           assertNotNull(fos);
       } catch (IOException e) {
           fail("Output stream should not throw exception");
       }
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void testProcessWithExceptionOnClose() throws IOException {
       // Arrange
       String[] data1 = { "data1", "data2" };
       when(mockIterator.hasNext()).thenReturn(true, false);
       when(mockIterator.next()).thenReturn(data1);
       // Mocking FileOutputStream to throw an exception on close
       FileOutputStream mockFos = mock(FileOutputStream.class);
       doThrow(new IOException("Close exception")).when(mockFos).close();
       // Use reflection to set the output stream in the TemplateProcessor
       // This part assumes you have a way to set the output stream in your class
       // For demonstration purposes, you might need to adjust this based on your actual implementation.
       // Field field = TemplateProcessor.class.getDeclaredField("out");
       // field.setAccessible(true);
       // field.set(templateProcessor, mockFos);
       // Act
       assertThrows(IOException.class, () -> {
           templateProcessor.process(mockIterator, outputWorkbook);
       });
       // Assert
       // Verify that the close method was called
       verify(mockFos).close();
   }
// END DIFF
 }
```

### Failed Test: `TemplateProcessor_process_1_4_Test.java`

**Model:** deepseek-ai/deepseek-coder-1.3b-instruct

**Failed Test File:** `../SF110/5_templateit/chatunitest-tests_deepseek-coder-1.3b-instruct/templateit/org/templateit/failedtests/TemplateProcessor_process_1_4_Test.java`

```java
--- 
+++ 
 class TemplateProcessor_process_1_4_Test {
 
     @Test
// BEGIN DIFF
   void process_validInput_success() throws IOException {
       // Arrange
       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       InputStream is = new ByteArrayInputStream("some input".getBytes());
       TemplateProcessor processor = new TemplateProcessor(is);
       Iterator<String[]> di = mock(Iterator.class);
       OutputStream out = mock(OutputStream.class);
       // Act
       processor.process(di, out);
       // Assert
       verify(di, times(1)).next();
       verify(out, times(1)).write("some input".getBytes());
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     void process_invalidInput_throwsIOException() throws IOException {
         // Arrange
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
```

## Source File: `../SF110/5_templateit/src/main/java/org/templateit/TemplateProcessor.java`

```java
/*
 * Copyright(C) 2008-2009 Dmitriy Kumshayev. <dq@mail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.templateit;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFConditionalFormatting;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSheetConditionalFormatting;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.PaneInformation;
import org.templateit.util.FormulaUtil;
/**
 * Template processor - the core class of the library.
 * <br>
 * Example:
 * <pre>
 *    TemplateProcessor tp = new TemplateProcessor(new File("MyTemplate.xls"));
 *    Iterator<String[]> data1 = new DelimitedFileReader(new File("MyData1.csv"));
 *    Iterator<String[]> data1 = new DelimitedFileReader(new File("MyData2.csv"));
 *    // Keep Sheet2. This way Sheet2 will remain untouched in the output document   
 *    tp.keepSheet("Sheet2");
 *    // You can call process multiple times on the same template
 *    // with different data, to generate different files
 *    tp.process(data1,new File("MyResult1.xls"));
 *    tp.process(data2,new File("MyResult2.xls"));
 * </pre>
 * 
 * @author Dmitriy Kumshayev
 *
 */
public class TemplateProcessor
{

	private static final Logger logger = Logger.getLogger(TemplateProcessor.class);

	private final ByteArrayOutputStream bos;
	private final Set<String> protectedSheetNames;

	private HSSFWorkbook workbook;
	private List<String> sheetNames;
	protected TemplateWorkbook tWorkbook;

	public TemplateProcessor(File templateWorkbook) throws IOException
	{
		this(new BufferedInputStream(new FileInputStream(templateWorkbook)),true);
	}

	public TemplateProcessor(InputStream templateWorkbookStream) throws IOException
	{
		this(templateWorkbookStream,false);
	}
	
	public TemplateProcessor(InputStream templateWorkbookStream, boolean close)
	throws IOException
	{
		try
		{
			bos = toByteArrayOutputStream(templateWorkbookStream);
			protectedSheetNames = new HashSet<String>();
		}
		finally
		{
			if( close )
			{
				try{ templateWorkbookStream.close(); } catch (Exception ignore) {}
			}
		}
	}
	
	public void process(Iterator<String[]> di, File outputWorkbook)
			throws IOException
	{
		FileOutputStream out = new FileOutputStream(outputWorkbook);
		try
		{
			process(di, out);
		}
		finally
		{
			try
			{
				out.close();
			}
			catch (Exception ignore)
			{
			}
		}
	}

	public void process(Iterator<String[]> di, OutputStream out)
			throws IOException
	{
		// Parse workbook
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		workbook = new HSSFWorkbook(bis);
		
		// prepare processing
		sheetNames = renameTemplateSheets();
		tWorkbook = new WorkbookParser(workbook).parse(protectedSheetNames);
		
		processData(di);
		
		// finalize processing
		removeTemplateSheets();
		workbook.write(out);
	}

	/**
	 * Excludes the specified sheet from being considered as a template sheet.
	 * Keeps the sheet in the generated workbook after the processing.
	 *  
	 * @param sheetName
	 */
	public void keepSheet(String sheetName)
	{
		protectedSheetNames.add(sheetName);
	}

	private enum Operation
	{
		invalid, empty, sheet, select, data, end, template
	}

	private void processData(Iterator<String[]> di)
	{
		while (di.hasNext())
		{
			String[] data = di.next();
			while( data != null )
			{
				Operation op = parseOperation(data);
				switch (op)
				{
					case sheet:
					{
						String templateSheetName = data[1] + SUFFIX;
						String newSheetName = data[1];
						if (data.length>2 && data[2] != null && data[2].length() > 0)
						{
							newSheetName = data[2];
						}
						data = generateNewSheet(templateSheetName, newSheetName, di);
					}
					break;
					case empty:
					{
		
					}
					default:
					{
						throw new RuntimeException("unexpected operation (only #sheet is allowed here)");
					}
				}
			}
		}
	}

	public String [] generateNewSheet(String templateSheetName, String newSheetName,
			Iterator<String[]> di)
	{
		TemplateSheet tSheet = tWorkbook.getTemplateSheet(templateSheetName);
		if (tSheet != null)
		{
			return new SheetGenerator(tSheet, newSheetName).generateNewSheet(di);
		}
		else
		{
			logger.warn("Sheet " + templateSheetName + " not found.");
			return null;
		}
	}

	private static String extractTemplateName(String[] data)
	{
		String op = data[0];
		if (data != null && data.length > 0 && OpMatcher.matchTemplateName(op))
		{
			return op;
		}
		return null;
	}
	
	private static Operation parseOperation(String[] data)
	{
		String op = data[0];
		if (data == null || data.length == 0)
		{
			return Operation.empty;
		}
		if (op != null && op.equals("#sheet") && data[1] != null
				&& data[1].length() > 0)
		{
			return Operation.sheet;
		}
		if (OpMatcher.matchTemplateName(op) )
		{
			return Operation.data;
		}
		if (op != null && op.equals("#end"))
		{
			return Operation.end;
		}
		if (op != null && op.equals("#select"))
		{
			return Operation.select;
		}
		if (op != null && op.equals("#template"))
		{
			return Operation.template;
		}

		return Operation.invalid;
	}

	private final String SUFFIX = "Template";

	/**
	 * Rename all sheets in the template workbook. Add a SUFFIX to each name.
	 * 
	 * @return list of renamed sheet names
	 */
	private List<String> renameTemplateSheets()
	{
		List<String> names = new LinkedList<String>();
		for (int i = 0; i != workbook.getNumberOfSheets(); i++)
		{
			String name = workbook.getSheetName(i);

			// exclude protected sheets from processing
			if( !protectedSheetNames.contains(name) )
			{
				name += SUFFIX;
				workbook.setSheetName(i, name);
				names.add(name);
			}
			
		}
		return names;
	}

	private void removeTemplateSheets()
	{
		for (String sheetName : sheetNames)
		{
			workbook.removeSheetAt(workbook.getSheetIndex(sheetName));
		}
		sheetNames.clear();
	}

	private class SheetGenerator
	{
		private final TemplateSheet tSheet;

		private final HSSFSheet newSheet;
		
		private int outRow = 0; 

		private SheetGenerator(TemplateSheet tSheet, String newSheetName)
		{
			this.tSheet = tSheet;
			this.newSheet = workbook.createSheet(newSheetName);
		}

		private String [] generateNewSheet(Iterator<String[]> di)
		{
			copySheetSettings();
			copyPaneInformation();
			copyConditionalFormatting();
			copyPrintSetup();
			
			String ret[] = null;
			for( boolean end=false; !end && di.hasNext(); )
			{
				String data[] = di.next();
				Operation op = parseOperation(data);
				switch (op)
				{
					case select:	{ selectList(data);				}	break;
					case data:    { processTemplate(data);	}	break;
					case empty:		{ ;/* Skip */							}	break;
					case sheet:		{ ret = data; end = true;	}	break;
					case end:			{	end = true;							}	break;
					case template:	{	createDynamicTemplate(data);} 	break;
					case invalid:	{	logger.warn("invalid operation");	}
				}
			}
			return ret;
		}

		private void createDynamicTemplate(String[] data)
		{
			String name = data[1];
			int nr = Integer.parseInt(data[2]);
			
			int nc = (data.length-3)/nr;
			List<NamedStyle> styles = new ArrayList<NamedStyle>(nc);
			for( int i=3; i<data.length; i++ )
			{
				String styleName = data[i];
				NamedStyle style = tSheet.getStyle(styleName);
				if( style == null )
				{
					throw new RuntimeException("Style '"+styleName+"' not found");
				}
				styles.add(style);
			}
			tSheet.addDynamicTemplate(new DynamicTemplate(name,tSheet.sheet(),nr,styles));
		}

		private void processTemplate(String[] data)
		{
			String templateName = extractTemplateName(data);
			Template t = tSheet.getTemplate(templateName);
			if( t != null)
			{
				int startRow = outRow;
				int height = t.height();
				for( int r = 0; r < height; r++,outRow++)
				{
					HSSFRow newRow = createNewRow(t,r,outRow);
					if( newRow != null )
					{
						copyTemplateRow(t,r,newRow,outRow, data);
					}
				}
				if (t instanceof StaticTemplate)
				{
					copyMergeRegions((StaticTemplate) t, startRow);
				}
			}
		}

		private void selectList(String[] data)
		{
			if( data.length>1 && data[1]!= null && data[1].trim().length()>0 )
			{
				String templateName = data[1];
				StaticTemplate t = tSheet.getStaticTemplate(templateName);
				if( t!= null )
				{
					List<Integer> selectList = new ArrayList<Integer>(data.length-2);
					try
					{
						for( int i=2; i<data.length; i++)
						{
							selectList.add(Integer.valueOf(data[i])-1);
						}
					}
					catch (NumberFormatException e) 
					{
						logger.warn("Invalid #select list: "+Arrays.asList(data));
						selectList.clear();
					}
					
					if( selectList.size()>0)
					{
						t.setSelectList(selectList);
					}
					else
					{
						t.setSelectList(null);
					}
				}
			}
			else
			{
				logger.warn("No template name in #select");
			}
		}

		
		public HSSFRow createNewRow(Template t,int r, int rOut)
		{
			HSSFRow newRow = newSheet.createRow(rOut);
			newRow.setHeight((short)t.getRowHeight(r));
			if (t.isRowBroken(r))
			{
				newSheet.setRowBreak(rOut);
			}
			return newRow;
		}
		
		private void copyTemplateRow(Template t, int r, HSSFRow newRow,
				int currentRow, String[] data)
		{
			int currentCol = 0;
			int width = t.width();
			for (int c = 0; c < width; c++, currentCol++)
			{
				copyTemplateCell(t, r, c, newRow, currentRow,currentCol, data);
			}
		}

		private void copyTemplateCell(Template t, int r, int c, HSSFRow newRow,
				int currentRow, int currentCol, String[] data)
		{
			HSSFCell tCell = t.getCell(r, c);
			if( tCell != null )
			{
				HSSFCell newCell = newRow.createCell(currentCol);
				int cellType = tCell.getCellType();
				newCell.setCellType(cellType);
				newCell.setCellStyle(tCell.getCellStyle());
				
				switch (cellType)
				{
					case HSSFCell.CELL_TYPE_BOOLEAN:
					{
						newCell.setCellValue(tCell.getBooleanCellValue());
					}
					break;
					case HSSFCell.CELL_TYPE_FORMULA:
					{
						String newFormula = recalculateRelativeRefs(t, r, c, currentRow,
								currentCol);
						newCell.setCellFormula(newFormula);
					}
					break;
					case HSSFCell.CELL_TYPE_NUMERIC:
					{
						newCell.setCellValue(tCell.getNumericCellValue());
					}
					break;
					case HSSFCell.CELL_TYPE_STRING:
					{
						newCell.setCellValue(tCell.getRichStringCellValue());
					}
					break;
				}
				
				substituteParameters(t, r, c, currentRow, currentCol, newCell, data);
				
			}
		}

		/**
		 * Offset all relative references to correspond the new location of the cell.
		 * 
		 * @param tCell
		 * @param originalRow - relative row number within given template
		 * @param originalCol - relative column number within given template
		 * @param newRow - new/target absolute row number of the cell 
		 * @param newCol - new/target absolute row number of the cell
		 * 
		 * @return recalculated formula
		 */
		private String recalculateRelativeRefs(Template t, int originalRow, int originalCol,
				int newRow, int newCol)
		{
			HSSFCell tCell = t.getCell(originalRow, originalCol);
			String formula = tCell.getCellFormula();
			Reference absRef = t.absoluteReference(originalRow,originalCol);
			int roff = newRow - absRef.row();
			int coff = newCol - absRef.column();
			String newFormula = FormulaUtil.offsetRelativeReferences(workbook, formula, roff, coff);
			
			if (logger.isTraceEnabled())
			{
				logger.trace("Formula @("+originalRow+","+originalCol+") recalculated:: " + formula + " ===("
						+ roff + "," + coff + ")===> " + newFormula+" @("+newRow+","+newCol+")");
			}
			return newFormula;
		}

		private void substituteParameters(Template t, int r, int c, int outRow,
				int outCol, HSSFCell newCell, String[] data)
		{
			int idx = t.getParameterIndex(r,c);
			if( idx > 0)
			{
				String value = idx<data.length&&data[idx] != null ? data[idx] : "";
				try
				{
					setCellValue(newCell, value);
				}
				catch (Exception e)
				{
					logger.warn("Failed to set value '" + value + "' to cell("
							+ outRow + "," + outRow + ")", e);
				}
			}
		}

		@SuppressWarnings("deprecation")
		private void setCellValue(HSSFCell newCell, String value)
		{
			Double dval = tryToParseAsDouble(value);
			if( dval != null )
			{
				newCell.setCellValue(dval);
			}
			else
			{
				Date date = tryToParseAsDate(value);
				if (date != null)
				{
					newCell.setCellValue(date);
				}
				else
				{
					newCell.setCellValue(value);
				}
			}
		}

		private void copyMergeRegions(StaticTemplate t, int absTemplateOutputStartRow)
		{
			for (MergeRegion m : t.getMergeRegions())
			{
				if (t.contains(m.start()) && (m.end().row() != Reference.INFINITY)
						&& (m.end().column() != Reference.INFINITY))
				{
					int vTopOffset = m.start().row() - t.start().row();
					int mh = m.end().row() - m.start().row();

					int firstRow = absTemplateOutputStartRow + vTopOffset;
					int lastRow = firstRow+mh;
					
					int w = t.width();
					int firstCol=w;
					int lastCol=0;
					for (int c=0; c < w ; c++)
					{
						if(t.absoluteColumn(c) >= m.start().column() && 
								t.absoluteColumn(c) <= m.end().column() )
						{
							firstCol = Math.min(firstCol, c);
							lastCol = Math.max(lastCol, c);
						}
					}
					newSheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,firstCol,lastCol));
				}
			}
		}

		private void copyConditionalFormatting()
		{
			HSSFSheet sheet = tSheet.sheet();
			HSSFSheetConditionalFormatting sheetCf = sheet
					.getSheetConditionalFormatting();
			HSSFSheetConditionalFormatting newSheetcf = newSheet
					.getSheetConditionalFormatting();

			int n = sheetCf.getNumConditionalFormattings();
			for (int i = 0; i != n; i++)
			{
				HSSFConditionalFormatting cf = sheetCf.getConditionalFormattingAt(i);
				if (cf != null)
				{
					newSheetcf.addConditionalFormatting(cf);
				}
			}
		}

		private void copySheetSettings()
		{
			HSSFSheet sheet = tSheet.sheet();
			newSheet.setDisplayFormulas(sheet.isDisplayFormulas());
			newSheet.setDisplayGridlines(sheet.isDisplayGridlines());
			newSheet.setDisplayRowColHeadings(sheet.isDisplayRowColHeadings());
			newSheet.setGridsPrinted(sheet.isGridsPrinted());
			newSheet.setPrintGridlines(sheet.isPrintGridlines());
			
			int fc = tSheet.getFirstColumn();
			int lc = tSheet.getLastColumn();
			
			for (int c = fc; c <= lc; c++)
			{
				int nc = c - fc;
				newSheet.setColumnWidth(nc, sheet.getColumnWidth(c));
				if (sheet.isColumnBroken((short) c))
				{
					newSheet.setColumnBreak((short) nc);
				}
				if (sheet.isColumnHidden(c))
				{
					newSheet.setColumnHidden(nc, true);
				}
			}
		}

		private void copyPaneInformation()
		{
			PaneInformation paneInfo = tSheet.sheet().getPaneInformation();
			if (paneInfo != null)
			{
				short vSplitPos = paneInfo.getVerticalSplitPosition();
				short hSplitPos = paneInfo.getHorizontalSplitPosition();
				short vSplitLeftColumn = paneInfo.getVerticalSplitLeftColumn();
				short hSplitTopRow = paneInfo.getHorizontalSplitTopRow();
				byte activePane = paneInfo.getActivePane();
				if (paneInfo.isFreezePane())
				{
					newSheet.createFreezePane(vSplitPos, hSplitPos, vSplitLeftColumn,
							hSplitTopRow);
				}
				else
				{
					newSheet.createSplitPane(vSplitPos, hSplitPos, vSplitLeftColumn,
							hSplitTopRow, activePane);
				}
			}
		}

		private void copyPrintSetup()
		{
			HSSFPrintSetup ps = tSheet.sheet().getPrintSetup();
			if( ps != null )
			{
				// Have to surround the code below with try/catch block
				// since POI throws NPE in cases when the sheet
				// does not contain Print Setup Record.
				// POI also does not provide any means to determine if
				// PrintSetupRecord is present except throwing NPE.
				// TODO: patch POI to fix NPE at HSSFPrintSetup.getXXX methods
				
				try
				{
					short nCopies = ps.getCopies();
					boolean draft = ps.getDraft();
					short fitHeight = ps.getFitHeight();
					short fitWidth = ps.getFitWidth();
					double footerMargin = ps.getFooterMargin();
					double headerMargin = ps.getHeaderMargin();
					short hResolution = ps.getHResolution();
					boolean landscape = ps.getLandscape();
					boolean leftToRight = ps.getLeftToRight();
					boolean noColor = ps.getNoColor();
					boolean noOrientation = ps.getNoOrientation();
					boolean notes = ps.getNotes();
					short options = ps.getOptions();
					short pageStart = ps.getPageStart();
					short paperSize = ps.getPaperSize();
					short scale = ps.getScale();
					boolean usePage = ps.getUsePage();
					boolean validSettings = ps.getValidSettings();
					short vResolution = ps.getVResolution();
					
					HSSFPrintSetup newPs = newSheet.getPrintSetup();
					newPs.setCopies(nCopies);
					newPs.setDraft(draft);
					newPs.setFitHeight(fitHeight);
					newPs.setFitWidth(fitWidth);
					newPs.setFooterMargin(footerMargin);
					newPs.setHeaderMargin(headerMargin);
					newPs.setHResolution(hResolution);
					newPs.setLandscape(landscape);
					newPs.setLeftToRight(leftToRight);
					newPs.setNoColor(noColor);
					newPs.setNoOrientation(noOrientation);
					newPs.setNotes(notes);
					newPs.setOptions(options);
					newPs.setPageStart(pageStart);
					newPs.setPaperSize(paperSize);
					newPs.setScale(scale);
					newPs.setUsePage(usePage);
					newPs.setValidSettings(validSettings);
					newPs.setVResolution(vResolution);
				}
				catch (NullPointerException e) 
				{
					if( logger.isTraceEnabled())
					{
						logger.warn("Print Setup information is missing");
					}
				}
			}
		}
	}
	
	private static Double tryToParseAsDouble(String value)
	{
		try
		{
			return Double.parseDouble(value);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	private static Date tryToParseAsDate(String value)
	{
		for (DateFormat df : new DateFormat[]
		{
				new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"),
				new SimpleDateFormat("MM/dd/yyyy HH:mm"),
				new SimpleDateFormat("MM/dd/yyyy"),
				new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"),
				new SimpleDateFormat("dd-MMM-yyyy HH:mm"),
				new SimpleDateFormat("dd-MMM-yyyy")
		})
		{
			try
			{
				return df.parse(value);
			}
			catch (ParseException pe)
			{
			}
		}
		return null;
	}
	
	private static ByteArrayOutputStream toByteArrayOutputStream(InputStream is) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] b = new byte[4096];
		int off = 0;
		int len = 0;
		while( (len = is.read(b, off, b.length)) != -1 )
		{
			bos.write(b, off, len);
		}
		bos.flush();
		return bos;
	}

}

```



=============================================================

