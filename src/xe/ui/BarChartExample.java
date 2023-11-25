/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.ui;

import com.orsoncharts.label.CategoryItemLabelGenerator;
import com.orsoncharts.label.StandardCategoryItemLabelGenerator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
/**
 *
 * @author ADMIN
 */
public class BarChartExample extends ApplicationFrame{
    
    public BarChartExample(String title) {
        super(title);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //Set dữ liệu
        dataset.addValue(1.0, "Category 1", "Value 1");
        dataset.addValue(2.0, "Category 1", "Value 2");
        dataset.addValue(10.0, "Category 1", "Value 3");

        JFreeChart chart = ChartFactory.createBarChart(
                "Bar Chart Example",
                "Category",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        //Hiển thị giá trị bên trên cột
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        renderer.setBaseItemLabelGenerator((org.jfree.chart.labels.CategoryItemLabelGenerator) generator);
        renderer.setBaseItemLabelsVisible(true);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600)); // Đặt kích thước biểu đồ
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {
        BarChartExample chart = new BarChartExample("Bar Chart Example");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
