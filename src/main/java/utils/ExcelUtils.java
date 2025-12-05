package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static List<String[]> getStepsForTestCase(
            String filePath,
            String sheetName,
            String testCaseId) {

        List<String[]> steps = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheet(sheetName);
            int lastRow = sheet.getLastRowNum();

            // assuming row 0 is header
            for (int i = 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell tcIdCell = row.getCell(0);
                if (tcIdCell == null) continue;

                tcIdCell.setCellType(CellType.STRING);
                String tcId = tcIdCell.getStringCellValue().trim();

                if (tcId.equalsIgnoreCase(testCaseId)) {

                    String stepNo  = getCellString(row.getCell(1));
                    String keyword = getCellString(row.getCell(2));
                    String object  = getCellString(row.getCell(3));
                    String data    = getCellString(row.getCell(4));

                    steps.add(new String[]{stepNo, keyword, object, data});
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading Excel: " + e.getMessage());
        }

        return steps;
    }

    private static String getCellString(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }
}
