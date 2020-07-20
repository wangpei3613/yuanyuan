package com.sensebling.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcelUtil {

	/**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,List<Map<String,Object>> title,List<List<String>> values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        List<HSSFCellStyle> styles = new ArrayList<HSSFCellStyle>();
        HSSFCell cell = null;
        //创建标题
        for(int i=0;i<title.size();i++){
        		Map<String,Object> m = title.get(i);
            cell = row.createCell(i);
            cell.setCellValue(String.valueOf(m.get("title")));
            sheet.setColumnWidth(i, Integer.parseInt(String.valueOf(m.get("boxWidth")))*256/7); 
            HSSFCellStyle style = wb.createCellStyle();
            if("center".equals(String.valueOf(m.get("align")))) {
            		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            }
            cell.setCellStyle(style);
            styles.add(style);
        }

        //创建内容
        for(int i=0;i<values.size();i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values.get(i).size();j++){
                //将内容按顺序赋给对应的列对象
            		cell = row.createCell(j);
            		cell.setCellValue(values.get(i).get(j));  
            		cell.setCellStyle(styles.get(j));  
            }
        }
        return wb;
    }
}
