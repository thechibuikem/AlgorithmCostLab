package com.algorithmcostlab.benchmark;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
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
 * Reads results/benchmark.csv (JMH's own CSV format) and writes
 * results/performance.png: time vs. dataset size, one line per algorithm.
 */
public class ChartGenerator {

    public static void main(String[] args) throws IOException {
        Path csvPath = Path.of("results/benchmark.csv");
        if (!Files.exists(csvPath)) {
            throw new IllegalStateException(
                "results/benchmark.csv not found. Run BenchmarkRunner first.");
        }

        List<String> lines = Files.readAllLines(csvPath);
        String[] header = splitCsvLine(lines.get(0));
        int benchmarkCol = indexOf(header, "Benchmark");
        int scoreCol = indexOf(header, "Score");
        int sizeCol = indexOfContains(header, "size");

        Map<String, XYSeries> seriesByAlgorithm = new LinkedHashMap<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] row = splitCsvLine(lines.get(i));
            String fullName = row[benchmarkCol];
            String algorithm = fullName.substring(fullName.lastIndexOf('.') + 1);
            double score = Double.parseDouble(row[scoreCol]);
            double size = Double.parseDouble(row[sizeCol]);

            seriesByAlgorithm
                .computeIfAbsent(algorithm, XYSeries::new)
                .add(size, score);
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
        ((NumberAxis) chart.getXYPlot().getRangeAxis()).setAutoRangeIncludesZero(false);

        File outFile = new File("results/performance.png");
        ChartUtils.saveChartAsPNG(outFile, chart, 900, 600);
        System.out.println("Wrote " + outFile.getPath());
    }

    private static String[] splitCsvLine(String line) {
        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].replaceAll("^\"|\"$", "");
        }
        return parts;
    }

    private static int indexOf(String[] header, String name) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].equalsIgnoreCase(name)) return i;
        }
        throw new IllegalStateException("Column not found: " + name);
    }

    private static int indexOfContains(String[] header, String fragment) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].toLowerCase().contains(fragment.toLowerCase())) return i;
        }
        throw new IllegalStateException("No column containing: " + fragment);
    }
}