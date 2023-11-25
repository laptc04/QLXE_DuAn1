/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author ADMIN
 */
/**
 *
 * @author TranVanThien
 */
public class PieChartExample extends ApplicationFrame{
    
    public PieChartExample(String title) {
        super(title);
        DefaultPieDataset dataset = new DefaultPieDataset();
        //Set dữ liệu
        dataset.setValue("Category 1", 1.0);
        dataset.setValue("Category 2", 2.0);
        dataset.setValue("Category 3", 3.0);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Pie Chart Example",
                dataset,
                true, true, false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})")); // Hiển thị tên và phần trăm

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500)); // Đặt kích thước biểu đồ
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {
        PieChartExample chart = new PieChartExample("Pie Chart Example");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
