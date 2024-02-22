package com.sb.postgresTb.service;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

// Service class to handle exporting data to Excel
@Repository
public class ProductServiceImpl implements ProductService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getTableNames() {
        return jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'",
                String.class
        );
    }

    @Override
    public void ExcelGeneratorDB() {
        try
        {
            List<String> tableNames = getTableNames();

            for (String tableName : tableNames)
            {
                //declare file name
                String filename = "D:\\exportdata\\" + tableName + ".xlsx";
                //creating an instance of HSSFWorkbook class
                HSSFWorkbook workbook = new HSSFWorkbook();

                //invoking createSheet() method and passing the name of the sheet to be created
                HSSFSheet sheet = workbook.createSheet(tableName);
                //List<Map<String, Object>> tableData = jdbcTemplate.queryForList("SELECT * FROM " + tableName);
                List<Map<String, Object>> tableData = jdbcTemplate.queryForList("SELECT * FROM " + tableName + " LIMIT 50");
                int rowNum = 0;
                HSSFRow rowhead = sheet.createRow((short)rowNum++);
                int cellNumH = 0;
                for (String column : tableData.get(0).keySet()) {
                    rowhead.createCell(cellNumH++).setCellValue(column);
                }

                for (Map<String, Object> row : tableData) {
                    HSSFRow rowdata = sheet.createRow((short)rowNum++);
                    int cellNumD = 0;

                    for (Object value : row.values()) {
                        //dataRow.createCell(cellNum++).setCellValue(value.toString());
                        rowdata.createCell(cellNumD++).setCellValue((value != null) ? value.toString() : "");
                    }
                }

                FileOutputStream fileOut = new FileOutputStream(filename);
                workbook.write(fileOut);
                //closing the Stream
                fileOut.close();
                //closing the workbook
                workbook.close();
            }

            //prints the message on the console
            System.out.println("Excel file has been generated successfully.");
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }
    }

}
