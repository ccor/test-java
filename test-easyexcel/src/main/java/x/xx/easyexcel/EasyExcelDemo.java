package x.xx.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @date 2024/4/17 15:49
 */
public class EasyExcelDemo {
    public static void main(String[] args) {
        String fileName = "D:\\test.xlsx";

        simpleWrite(fileName);
        simpleRead(fileName);
    }

    @PostMapping("/importData")
    public void importData(@RequestParam("file") MultipartFile file) throws IOException {
        DemoListener demoListener = new DemoListener();
        EasyExcel.read(file.getInputStream(), DemoData.class, demoListener).sheet().doRead();
        System.out.println(demoListener.getList());
    }

    @PostMapping("/exportData")
    public void impData(HttpServletResponse response) throws IOException {
        // 1.3 hcz
        response.setContentType("application/vnd.ms-excel");
        // 1.2 zwy
        // response.setContentType("text/csv");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("文件名", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".csv");
        response.setContentType("application/octet-stream;charset=utf-8");
        ExcelWriterSheetBuilder sheet = EasyExcel.write(response.getOutputStream(), DemoData.class).sheet("sheet1");
        // 数据
        List<DemoData> list = new ArrayList<>();
        sheet.doWrite(list);
    }

    @SneakyThrows
    private static void simpleRead(String fileName) {
        DemoListener demoListener = new DemoListener();
        EasyExcel.read(new FileInputStream(fileName), DemoData.class, demoListener).sheet().doRead();
        System.out.println(demoListener.getList());
    }

    private static void simpleWrite(String fileName) {
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        try(ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
            for (int i = 0; i < 5; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoData> data = data();
                excelWriter.write(data, writeSheet);
            }
        }
    }

    private static List<DemoData> data() {
        java.util.List<DemoData> list = new java.util.ArrayList<DemoData>();
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setTitle("字符串" + i);
            data.setCreateTime(new Date());
            data.setDoubleData(rnd.nextDouble());
            list.add(data);
        }
        return list;
    }

    @Data
    public static class DemoData {
        @ExcelProperty("标题")
        private String title;

        @ExcelProperty("标题")
        @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
        private Date createTime;

        @NumberFormat("#.##%")
        @ExcelProperty(value = "占比")
        private Double doubleData;
    }

    public static class DemoListener extends AnalysisEventListener<DemoData> {

        @Getter
        private List<DemoData> list = new ArrayList<>();

        @Override
        public void invoke(DemoData demoData, AnalysisContext analysisContext) {
            list.add(demoData);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            // 读取完毕
        }
    }
}
