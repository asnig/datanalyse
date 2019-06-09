package cn.datanalyse.service;

import cn.datanalyse.pojo.Data;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author XYQ
 */
@Service
public class ExcelService {

    public void exportExcel(Map<Integer, List<String>> map, HttpServletResponse response) {

    }

    public List<Data> readExcel(InputStream in) {
        List<Data> datas = new ArrayList<>();
        try {
            Workbook workBook = WorkbookFactory.create(in);
            parseWorkbook(datas, workBook);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public List<Data> readExcel(File file) {
        List<Data> datas = new ArrayList<>();
        try {
            Workbook workBook = WorkbookFactory.create(FileUtils.openInputStream(file));
            parseWorkbook(datas, workBook);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 按照坐标X开始分组
     *
     * @param datas 从Excel中读取的原始数据
     * @return 已经按照坐标X分好组的集合 key为坐标X，value为对应坐标的集合
     */
    public Map<Integer, List<Data>> groupByZuoBiaoX(List<Data> datas) {
        Map<Integer, List<Data>> map = new TreeMap<>();
        for (Data data : datas) {
            List<Data> tempList;
            tempList = map.get(data.getZuoBiaoX());
            if (tempList == null) {
                tempList = new ArrayList<>();
                tempList.add(data);
                map.put(data.getZuoBiaoX(), tempList);
            } else {
                tempList.add(data);
            }
        }
        return map;
    }

    /**
     * 计算每一组所有的Ai、Aj
     *
     * @param groupDatas 已经分组好的数据
     * @return 每一组的A值集合 Integer对应坐标X List存放当前分组的A1、A2的值
     */
    public Map<Integer, List<String>> getA(Map<Integer, List<Data>> groupDatas) {
        Map<Integer, List<String>> result = new TreeMap<>();
        // 遍历已经分好组的数据
        for (Map.Entry<Integer, List<Data>> entry : groupDatas.entrySet()) {
            List<Data> datas = entry.getValue();
            //flag用来表示A1找到没有 0未找到 1已经找到
            int flag = 0;
            List<String> aList = new ArrayList<>();
            for (Data data : datas) {
                //如果已经是最后一个坐标，无论误差大小，直接放入aList
                if (flag == 1 && "350".equals(data.getZuoBiaoA())) {
                    aList.add(data.getZuoBiaoA());
                    break;
                }

                if (data.getWuCha() > 0) {
                    if (flag == 0) {
                        aList.add(data.getZuoBiaoA());
                        flag = 1;
                    } else {
                        continue;
                    }
                }

                if (flag == 1 && data.getWuCha() == 0) {
                    aList.add(data.getZuoBiaoA());
                    flag = 0;
                }

            }
            result.put(entry.getKey(), aList);
        }
        return result;
    }

    private static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();

    }

    private void parseWorkbook(List<Data> datas, Workbook workBook) {
        Sheet sheet = workBook.getSheet("数据");
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);

            String biaoHao = row.getCell(0).getStringCellValue();
            String zuoBiaoX = row.getCell(1).getStringCellValue();
            String zuoBiaoA = row.getCell(2).getStringCellValue();
            double houDuL = row.getCell(3).getNumericCellValue();
            double wuCha = sub(houDuL, 19.1d);

            datas.add(new Data(biaoHao, Integer.valueOf(zuoBiaoX), zuoBiaoA, houDuL, wuCha));
        }
    }
}
