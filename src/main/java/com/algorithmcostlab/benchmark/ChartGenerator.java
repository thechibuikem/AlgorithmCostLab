
package com.algorithmcostlab.benchmark;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads results/benchmark.json (JMH's own CSV format) and writes
 * results/performance.png: time vs. dataset size, one line per algorithm.
 */
public class ChartGenerator {

    public static void main(String[] args) throws IOException {
        Path jsonPath = Path.of("results/benchmark.json");
        if (!Files.exists(jsonPath)) {
            throw new IllegalStateException(
                "results/benchmark.json not found. Run BenchmarkRunner first.");
        }

        Map<String, XYSeries> seriesByAlgorithm = new LinkedHashMap<>();

        List<BenchmarkResultReader.Row> rows = BenchmarkResultReader.readAll(jsonPath);
        for (BenchmarkResultReader.Row row : rows) {
            seriesByAlgorithm
            .computeIfAbsent(row.algorithm(), XYSeries::new)
            .add(row.size(), row.scoreNanos());
}

        XYSeriesCollection dataset = new XYSeriesCollection();
        seriesByAlgorithm.values().forEach(dataset::addSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Lookup Time vs. Dataset Size",
                "Dataset size",
                "Average time (ns)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        LogAxis xAxis = new LogAxis("Dataset size (log scale)");
        xAxis.setBase(10);
        chart.getXYPlot().setDomainAxis(xAxis);

        LogAxis yAxis = new LogAxis("Average time (ns, log scale)");
        yAxis.setBase(10);
        chart.getXYPlot().setRangeAxis(yAxis);
        
        File outFile = new File("results/performance.png");
        ChartUtils.saveChartAsPNG(outFile, chart, 900, 600);
        System.out.println("Wrote " + outFile.getPath());
    }

}