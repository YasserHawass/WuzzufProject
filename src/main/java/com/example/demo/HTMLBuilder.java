package com.example.demo;

import org.apache.commons.codec.binary.Base64;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class HTMLBuilder {

    private final StringBuilder htmlBuilder = new StringBuilder();
    public static String STYLE = "<head><link rel=\"stylesheet\" href=\"src/main.css\">\n <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\"><link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin><link href=\"https://fonts.googleapis.com/css2?family=Roboto+Condensed&display=swap\" rel=\"stylesheet\">" +
        "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js\"></script>";
    public static String HTML_START = "<html>\n";
    public static String HTML_END = "\n</html>";
    public static String TABLE_START = "<div class=\"tbl-content\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tbody>\n";
    public static String TABLE_END = "</tbody></table></div>";
    public static String ROW_START = "<tr>";
    public static String ROW_END = "</tr>";
    public static String TABLE_HEADER_START = "<div class=\"tbl-header\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><thead>";
    public static String TABLE_HEADER_END = "</thead></table></div>";
    public static String HEADER_START = "<th>";
    public static String HEADER_END = "</th>";
    public static String COLUMN_START = "<td style=\"width:100px;\">";
    public static String COLUMN_END = "</td>";
    public static String HEAD_START = "<h1>";
    public static String HEAD_END = "</h1>";
    public static Dataset<Row> data;
    public static List<Row> dataList;


    public HTMLBuilder(String func, Dataset<Row> data) {
        this.data= data;
        htmlBuilder.append(HTML_START);
        htmlBuilder.append(STYLE);
        htmlBuilder.append(HEAD_START);
        htmlBuilder.append(func);
        htmlBuilder.append(HEAD_END);
        htmlBuilder.append(TABLE_START);
        htmlBuilder.append(TABLE_END);
        htmlBuilder.append(HTML_END);
    }

    public HTMLBuilder(String func, List<Row> data) {
        this.dataList= data;
        htmlBuilder.append(HTML_START);
        htmlBuilder.append(STYLE);
        htmlBuilder.append(HEAD_START);
        htmlBuilder.append(func);
        htmlBuilder.append(HEAD_END);
        htmlBuilder.append(TABLE_START);
        htmlBuilder.append(TABLE_END);
        htmlBuilder.append(HTML_END);
    }



    public HTMLBuilder(String func)
    {
        htmlBuilder.append(HTML_START);
        htmlBuilder.append(STYLE);
        htmlBuilder.append(HEAD_START);
        htmlBuilder.append(func);
        htmlBuilder.append(HEAD_END);
        htmlBuilder.append(TABLE_START);
        htmlBuilder.append(TABLE_END);
        htmlBuilder.append(HTML_END);
    }


    public void addTableHeader()
    {
        int lastIndex = htmlBuilder.lastIndexOf(TABLE_END);
        if (lastIndex > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(TABLE_HEADER_START);
            sb.append(ROW_START);
            for (String value : data.columns()) {
                sb.append(HEADER_START);
                sb.append(value);
                sb.append(HEADER_END);
            }
            sb.append(ROW_END);
            sb.append(TABLE_HEADER_END);
            htmlBuilder.insert(lastIndex, sb.toString());
        }
    }


    public String addTableList()
    {
        String columnName[] = new String[]{"Skills","Count"};
        int lastIndex = htmlBuilder.lastIndexOf(TABLE_END);
        if (lastIndex > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(ROW_START);
            for (String value : columnName) {
                sb.append(HEADER_START);
                sb.append(value);
                sb.append(HEADER_END);
            }
            sb.append(ROW_END);
            htmlBuilder.insert(lastIndex, sb.toString());
        }

        dataList.stream().forEach(r -> {
        String[] s = r.toString().replace("[","").replace("]","")
                .split(",");
        int lastIndex2 = htmlBuilder.lastIndexOf(ROW_END);
        if (lastIndex2 > 0) {
            int index = lastIndex2 + ROW_END.length();
            StringBuilder sb = new StringBuilder();
            sb.append(ROW_START);
            for (String value : s) {
                sb.append(COLUMN_START);
                sb.append(value);
                sb.append(COLUMN_END);
            }
            sb.append(ROW_END);
            htmlBuilder.insert(index, sb.toString());
        }
    });
        return build();
    }


    public void addTableValues(int iter)
    {
        data.collectAsList().stream().forEach(r -> {
            String[] s = r.toString().replace("{","").replace("}","")
                    .replace("[","").replace("]","")
                    .split(",",iter);
            int lastIndex2 = htmlBuilder.lastIndexOf(ROW_END);
            if (lastIndex2 > 0) {
                int index = lastIndex2 + ROW_END.length();
                StringBuilder sb = new StringBuilder();
                sb.append(ROW_START);
                for (String value : s) {
                    sb.append(COLUMN_START);
                    sb.append(value);
                    sb.append(COLUMN_END);
                }
                sb.append(ROW_END);
                htmlBuilder.insert(index, sb.toString());
            }
        });
    }

    public String displayTable(int iter) {
        addTableHeader();
        addTableValues(iter);
        return build();
    }

    public String build()
    {
        return htmlBuilder.toString();
    }



    public  String viewchart(String path,String chart_name){

        FileInputStream img ;
        try {
            File f= new File(path);
            img = new FileInputStream(f);
            byte[] bytes =  new byte[(int)f.length()];
            img.read(bytes);
            String encodedfile = new String(Base64.encodeBase64(bytes) , "UTF-8");

            return "<div style =\"margin:auto;\" >" +
                    "<img src=\"data:image/png;base64, "+encodedfile+"\" alt=\"Red dot;\" style=\"position: absolute;right: 25%;left:25%\"/>" +
                    "</div>";
        } catch (IOException e) {
            return "error";
        }
    }

}
