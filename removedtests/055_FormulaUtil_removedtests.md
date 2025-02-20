### Failed Test: `FormulaUtil_offsetRelativeReferences_0_2_Test.java`

**Model:** gemini-1.5-flash-8b

**Failed Test File:** `../SF110/5_templateit/chatunitest-tests_gemini-1.5-flash-8b/templateit/org/templateit/util/failedtests/FormulaUtil_offsetRelativeReferences_0_2_Test.java`

```java
--- 
+++ 
 public class FormulaUtil_offsetRelativeReferences_0_2_Test {
 
     @Test
// BEGIN DIFF
   public void offsetRelativeReferences_positiveOffset() {
       HSSFWorkbook wb = Mockito.mock(HSSFWorkbook.class);
       String formula = "A1+B2";
       int roff = 2;
       int coff = 1;
       String expectedFormula = "C3+D4";
       String actualFormula = FormulaUtil.offsetRelativeReferences(wb, formula, roff, coff);
       assertEquals(expectedFormula, actualFormula);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
   public void offsetRelativeReferences_negativeOffset() {
       HSSFWorkbook wb = Mockito.mock(HSSFWorkbook.class);
       String formula = "C3+D4";
       int roff = -2;
       int coff = -1;
       String expectedFormula = "A1+B2";
       String actualFormula = FormulaUtil.offsetRelativeReferences(wb, formula, roff, coff);
       assertEquals(expectedFormula, actualFormula);
   }
// END DIFF
+
// BEGIN DIFF
   @Test
// END DIFF
     public void offsetRelativeReferences_zeroOffset() {
         HSSFWorkbook wb = Mockito.mock(HSSFWorkbook.class);
         String formula = "A1+B2";
         String actualFormula = FormulaUtil.offsetRelativeReferences(wb, formula, roff, coff);
         assertEquals(expectedFormula, actualFormula);
     }
+
// BEGIN DIFF
   @Test
   public void offsetRelativeReferences_emptyFormula() {
       HSSFWorkbook wb = Mockito.mock(HSSFWorkbook.class);
       String formula = "";
       int roff = 2;
       int coff = 1;
       String expectedFormula = "";
       String actualFormula = FormulaUtil.offsetRelativeReferences(wb, formula, roff, coff);
       assertEquals(expectedFormula, actualFormula);
   }
// END DIFF
 }
```

## Source File: `../SF110/5_templateit/src/main/java/org/templateit/util/FormulaUtil.java`

```java
package org.templateit.util;

import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.formula.AreaPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.RefPtg;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FormulaUtil
{

	public static String offsetRelativeReferences(HSSFWorkbook wb,
			String formula, int roff, int coff)
	{
		Ptg[] ptgs = HSSFFormulaParser.parse(formula, wb);
		offsetRelativePtgs(ptgs, roff, coff);
		String newFormula = HSSFFormulaParser.toFormulaString(wb, ptgs);
		return newFormula;
	}

	private static void offsetRelativePtgs(Ptg[] ptgs, int roff, int coff)
	{
		for (Ptg ptg : ptgs)
		{
			offsetRelativePtg(ptg, roff, coff);
		}
	}

	private static void offsetRelativePtg(Ptg ptg, int roff, int coff)
	{
		if (ptg instanceof RefPtg)
		{
			RefPtg ref = (RefPtg) ptg;
			if (roff != 0 && ref.isRowRelative())
			{
				ref.setRow(ref.getRow() + roff);
			}
			if (coff != 0 && ref.isColRelative())
			{
				ref.setColumn(ref.getColumn() + coff);
			}
		}
		else if (ptg instanceof AreaPtg)
		{
			AreaPtg aptg = (AreaPtg) ptg;
			if (roff != 0)
			{
				if (aptg.isFirstRowRelative())
				{
					aptg.setFirstRow(aptg.getFirstRow() + roff);
				}
				if (aptg.isLastRowRelative())
				{
					aptg.setLastRow(aptg.getLastRow() + roff);
				}
			}
			if (coff != 0)
			{
				if (aptg.isFirstColRelative())
				{
					aptg.setFirstColumn(aptg.getFirstColumn() + coff);
				}
				if (aptg.isLastColRelative())
				{
					aptg.setLastColumn(aptg.getLastColumn() + coff);
				}
			}
		}
	}
}

```



=============================================================

