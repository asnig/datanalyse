package cn.datanalyse.web;

import cn.datanalyse.pojo.Data;
import cn.datanalyse.service.ExcelService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author XYQ
 */
@Controller
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @RequestMapping("/uploadExcel")
    @ResponseBody
    public Object doAnalyse(@RequestParam("upfile") MultipartFile multipartFile, HttpServletResponse response) {
        if (multipartFile != null) {
            List<Data> datas;
            //已经分析好的数据保存到result
            Map<Integer, List<String>> result = null;
            try {
                datas = excelService.readExcel(multipartFile.getInputStream());
                Map<Integer, List<Data>> map = excelService.groupByZuoBiaoX(datas);
                result = excelService.getA(map);
                excelService.exportExcel(result,response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        } else {
            System.out.println("接收参数为空！");
            return null;
        }
    }
}
