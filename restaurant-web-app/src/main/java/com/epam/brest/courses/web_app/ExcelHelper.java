package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Item;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHelper.class);

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Id", "Name", "Price"};
    static String SHEET = "Items";

    public static boolean hasExcelFormat(MultipartFile file) {


        if (!TYPE.equals(file.getContentType())) {
            LOGGER.info("dont excel!");
            return false;
        }

        return true;
    }

    public static List<Item> excelToItems(InputStream is) throws IOException {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Item> items = new ArrayList<Item>();

            int rowNumber = 0;
            while (rows.hasNext() && !(rowNumber==-1)) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Item item = new Item();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            item.setItemId(Integer.valueOf((int) currentCell.getNumericCellValue()));
                            break;

                        case 1:
                            item.setItemName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            item.setItemPrice(BigDecimal.valueOf(Long.parseLong(currentCell.getStringCellValue())));
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                if(!(item.getItemId()==null)){
                    items.add(item);
                } else rowNumber = -1;


            }

            workbook.close();

            return items;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
