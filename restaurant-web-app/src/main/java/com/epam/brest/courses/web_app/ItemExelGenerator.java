package com.epam.brest.courses.web_app;


import com.epam.brest.courses.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class ItemExelGenerator {
    public static ByteArrayInputStream itemsToExcel(List<Item> items) throws IOException {
        String[] COLUMNs = {"Id", "Name", "Price"};
        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Items");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;

            ObjectMapper mapper = new ObjectMapper();
            List<Item> itemList = new ArrayList<>();
            for (int i = 0; i < items.size(); i++){
                Item item = mapper.convertValue(items.get(i), Item.class);
                itemList.add(item);
            }

            int id = 1;
            for (Item item : itemList) {

            Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(id);
                row.createCell(1).setCellValue(item.getItemName());
                row.createCell(2).setCellValue(String.valueOf(item.getItemPrice()));
                id++;
//
//                Cell ageCell = row.createCell(3);
//                ageCell.setCellValue(String.valueOf(item.getItemPrice()));
//                ageCell.setCellStyle(ageCellStyle);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
