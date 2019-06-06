<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    application.setAttribute("APP_PATH", request.getServletContext().getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Excel数据分析</title>
    <link rel="stylesheet" href="static/css/bootstrap.min.css">
    <script src="static/js/jquery-2.2.3.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script></head>
<body>
<form method="post" enctype="multipart/form-data" action="${APP_PATH}/uploadExcel" onsubmit="return checkData($('#upfile')[0])">
          
    <table>
        <tr>
            <td>上传文件:</td>
            <td><input id="upfile" type="file" name="upfile" class="btn btn-blue"></td>
        </tr>
        <tr>
            <td>
                <input id="submitBtn" type="submit" class="btn btn-primary" value="提交">
            </td>
        </tr>
    </table>
     
</form>
</body>
<script>
    //JS校验form表单信息
    function checkData(input) {
        var fileDir = input.value;
        var suffix = fileDir.substr(fileDir.lastIndexOf("."));
        if ("" === fileDir) {
            alert("选择需要导入的Excel文件！");
            return false;
        }
        if (".xls" !== suffix && ".xlsx" !== suffix) {
            alert("选择Excel格式的文件导入！");
            input.outerHTML=input.outerHTML.replace(/(value=").+"/i,"$1\"");
            return false;
        }
        return true;
    }

    option = {
        title: {
            text: '分析结果'
        },
        xAxis: {
            name:'坐标X',
            boundaryGap: false,
            splitLine: {
                show: true,
                lineStyle: {
                    color: '#000',
                    type: 'solid'
                }
            },
            minInterval: 1,
            type:'value'
        },
        yAxis: {
            name:'坐标A',
            max:'350',
            type:'value',
            axisLine:{
                show:false
            }
        },
        series: [{
            symbolSize: 10,
            data: [
                [1, 0],
                [1, 50],
                [1, 60],
                [1, 90],
                [1, 120],
                [1, 160],
                [1, 200],
                [1, 220],
                [1, 230],
                [1, 300],
                [1, 350],
                [2,0],
                [2,3]
            ],
            type: 'scatter'
        }]
    };

</script>


</html>