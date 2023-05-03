import controller.LoginController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
         LoginController loginController = new LoginController();

/*        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Apple", 20);
        dataset.setValue("Orange", 25);
        dataset.setValue("Banana", 35);
        dataset.setValue("Mango", 10);
        dataset.setValue("Grapes", 10);

        // Create a chart
        JFreeChart chart = ChartFactory.createRingChart("Fruit Distribution", dataset, true, true, false);
        chart.setBackgroundPaint(new Color(255, 255, 255));
        JFrame frame = new JFrame();
        frame.setSize(1000, 800);
        frame.setLayout(null);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(400, 300);
        frame.add(chartPanel);
        frame.setVisible(true);*/
    }
}

