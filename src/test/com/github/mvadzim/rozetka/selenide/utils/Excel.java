package com.github.mvadzim.rozetka.selenide.utils;

import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Excel {
    private HSSFWorkbook workbook = new HSSFWorkbook();
    private HSSFSheet[] sheets = new HSSFSheet[10]; // В Java есть безрасмерные масивы?

    public void writeExcelFile(String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (Exception ex) {
        }
    }

    public void createSheets(String[] names) {
        for (int i = 0; i < names.length; i++) {
            sheets[i] = workbook.createSheet(names[i]);
        }
    }

    public void putRows(String[][] values, int sheetIndex) {
        for (int r = 0; r < values.length; r++) {
            HSSFRow row = sheets[sheetIndex].createRow((short) r);
            for (int c = 0; c < values[r].length; c++) {
                row.createCell(c).setCellValue(values[r][c]);
            }
        }
    }
}
