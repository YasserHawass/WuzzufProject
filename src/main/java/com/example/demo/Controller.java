package com.example.demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
    SparkDAO sparkObject = new SparkDAO();

    @GetMapping("/display_data")
    public String display_data(){
        HTMLBuilder h = new HTMLBuilder("Display Data", sparkObject.show_first10_records()) ;
        return h.displayTable(8);
    }


    @GetMapping("/display_structure")
    public String Data_Structure(){
        return sparkObject.show_data_structure();
    }


    @GetMapping("/display_summary")
    public String Data_Summary(){
        HTMLBuilder h = new HTMLBuilder("Data summary", sparkObject.show_data_summary()) ;
        return h.displayTable(9);
    }

    @GetMapping("/clean_data")
    public String Clean_Data(){
        HTMLBuilder h = new HTMLBuilder("Data Cleaned", sparkObject.clean_duplicates_data()) ;
        return h.displayTable(8);
    }

    @GetMapping("/display_top_companies")
    public String Top_Companies() throws Exception {
        HTMLBuilder h = new HTMLBuilder("Top Companies", sparkObject.show_top_companies()) ;
        return h.displayTable(2);

    }

    @GetMapping("/display_top_titles")
    public String Top_Titles() throws Exception {
        HTMLBuilder h = new HTMLBuilder("Top Titles", sparkObject.show_top_titles()) ;
        return h.displayTable(2);
    }


    @GetMapping("/display_top_locations")
    public String Top_Locations() throws Exception {
        HTMLBuilder h = new HTMLBuilder("Top Areas", sparkObject.show_top_locations()) ;
        return h.displayTable(2);
    }


    @GetMapping("/display_top_skills")
    public String show_top_skills()throws Exception {
        HTMLBuilder h = new HTMLBuilder("Top Skills", sparkObject.show_top_skills()) ;
        return h.addTableList();
    }


    @GetMapping("/display_pie_chart")
    public String Companies_pie_chart() throws Exception {
        
        String path = sparkObject.pieChart();
        HTMLBuilder h = new HTMLBuilder("Companies Pie Chart");
        return h.viewchart(path,"Top Companies Pie Chart");
    }

    @GetMapping("/title_bar_chart")
    public String Title_Bar_Chart() throws Exception {
        String path = sparkObject.barChart1();
        HTMLBuilder h = new HTMLBuilder("titles bar Chart");
        return h.viewchart(path,"Top Titles Bar Chart");
    }

    @GetMapping("/location_bar_chart")
    public String Area_Bar_Chart() throws Exception {
        String path = sparkObject.barChart2();
        HTMLBuilder h = new HTMLBuilder("Area bar Chart");
        return h.viewchart(path,"Top Locations Bar Chart");
    }


    @GetMapping("/display_YearsExp")
    public String YearsExp() throws Exception {
        // SparkDemoEDA sp = new SparkDemoEDA();
        HTMLBuilder h = new HTMLBuilder("Factorized Years Experience", sparkObject.Factorized()) ;
        return h.displayTable(2);
    }
    
    @GetMapping("/kmeans")
    public String KMeans() throws Exception {
       HTMLBuilder h = new HTMLBuilder("KMeans", sparkObject.KMeansx()) ;
       return h.displayTable(5);
    }

}

