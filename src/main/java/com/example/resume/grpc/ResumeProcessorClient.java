package com.example.resume.grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResumeProcessorClient {
    private static  ResumeProcessorGrpc.ResumeProcessorBlockingStub blockingStub;

    public ResumeProcessorClient(ManagedChannel channel) {
        blockingStub = ResumeProcessorGrpc.newBlockingStub(channel);
    }

    public static void generateGraph(String text, GRPC.GraphType type, GRPC.GraphInfo info, String outputPath,
                                     int width, int height, String backgroundColor, String colormap,
                                     boolean grid, boolean logScaleX, boolean logScaleY, String legendPosition,
                                     int titleSize, int xlabelSize, int ylabelSize, String lineStyle,
                                     String markerStyle, float opacity, String gridColor, String gridLineStyle, float gridLineWidth,
                                     String numberFormat) throws IOException {
        GRPC.GraphRequest request = GRPC.GraphRequest.newBuilder()
                .setText(text)
                .setType(type)
                .setInfo(info)
                .setWidth(width)
                .setHeight(height)
                .setBackgroundColor(backgroundColor)
                .setColormap(colormap)
                .setGrid(grid)
                .setLogScaleX(logScaleX)
                .setLogScaleY(logScaleY)
                .setLegendPosition(legendPosition)
                .setTitleSize(titleSize)
                .setXlabelSize(xlabelSize)
                .setYlabelSize(ylabelSize)
                .setLineStyle(lineStyle)
                .setMarkerStyle(markerStyle)
                .setOpacity(opacity)
                .setGridColor(gridColor)
                .setGridLineStyle(gridLineStyle)
                .setGridLineWidth(gridLineWidth)
                .setNumberFormat(numberFormat)
                .build();

        GRPC.ImageResponse response = blockingStub.generateGraph(request);
        saveImage(response.getImageData().toByteArray(), outputPath);
    }

    public static void createWordCloud(String text,
                                       String outputPath,
                                       int width,
                                       int height,
                                       int maxFontSize,
                                       String backgroundColor,
                                       String colormap,
                                       int margin, String address
                                        ) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, 50051).usePlaintext().build();
        ResumeProcessorClient client = new ResumeProcessorClient(channel);


        GRPC.WordCloudRequest request = GRPC.WordCloudRequest.newBuilder()
                .setText(text)
                .setWidth(width)
                .setHeight(height)
                .setMaxFontSize(maxFontSize)
                .setBackgroundColor(backgroundColor)
                .setColormap(colormap)
                .setMargin(margin)
                .build();

        GRPC.ImageResponse response = blockingStub.generateWordCloud(request);
        saveImage(response.getImageData().toByteArray(), outputPath);
    }

    private static void saveImage(byte[] imageData, String outputPath) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        BufferedImage image = ImageIO.read(bis);
        ImageIO.write(image, "png", new File(outputPath));
    }

    public static void createGraph(List<String> labels, List<Integer> values, String title, String xLabel, String yLabel, int xTicksRotation,
                                   String colorA, String colorB, String colorC, boolean showValues, String g_text, GRPC.GraphType g_type, GRPC.GraphInfo g_info,
                                   String g_outputPath, int g_width, int g_height, String g_backgroundColor, String g_colormap,
                                   boolean g_grid, boolean g_logScaleX, boolean g_logScaleY, String g_legendPosition,
                                   int g_titleSize, int g_xlabelSize, int g_ylabelSize, String g_lineStyle, String g_markerStyle,
                                   float g_opacity, String g_gridColor, String g_gridLineStyle, float g_gridLineWidth,
                                   String g_numberFormat ) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        ResumeProcessorClient client = new ResumeProcessorClient(channel);

        // Пример создания столбчатой диаграммы (Bar Graph)
        GRPC.BarInfo barInfo = GRPC.BarInfo.newBuilder()
                .addAllLabels(labels)
                .addAllValues(values)
                .setTitle(title)
                .setXlabel(xLabel)
                .setYlabel(yLabel)
                .setXticksRotation(xTicksRotation)
                .addColors(colorA)
                .addColors(colorB)
                .addColors(colorC)
                .setShowValuesOnBars(showValues)
                .build();

        GRPC.GraphInfo graphInfoBar = GRPC.GraphInfo.newBuilder().setBarInfo(barInfo).build();
        generateGraph(g_text, GRPC.GraphType.BAR, graphInfoBar, g_outputPath,
                g_width, g_height, g_backgroundColor, g_colormap, g_grid, g_logScaleX, g_logScaleY,
                g_legendPosition, g_titleSize, g_xlabelSize, g_ylabelSize, g_lineStyle, g_markerStyle,g_opacity
                ,g_gridColor ,g_gridLineStyle, g_gridLineWidth, g_numberFormat);
    }
        public static void createPieChart(String labelsA, Integer valuesA, String labelsB, Integer valuesB, String labelsC,
                Integer valuesC,String title, String xLabel, String yLabel, Integer xTicksRotation, String colorA, String colorB, String colorC,Boolean showValues) throws IOException {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
            ResumeProcessorClient client = new ResumeProcessorClient(channel);

            GRPC.PieInfo pieInfo = GRPC.PieInfo.newBuilder()
                    .addLabels("A")
                    .addLabels("B")
                    .addLabels("C")
                    .addValues(10)
                    .addValues(20)
                    .addValues(30)
                    .setTitle("Pie Chart Title")
                    .addColors("red")
                    .addColors("green")
                    .addColors("blue")
                    .setShowPercentagesOnPie(true)
                    .build();
            GRPC.GraphInfo graphInfoPie = GRPC.GraphInfo.newBuilder().setPieInfo(pieInfo).build();
            client.generateGraph("Pie Chart", GRPC.GraphType.PIE, graphInfoPie, "pie_chart.png",
                    800, 600, "white", "plasma", true, false, false, "bottom", 16, 12, 12,
                    "-", "o", 1.0f, "gray", "--", 0.5f, "%.2f");
        }
    public static void createDonutChart(String labelsA, Integer valuesA, String labelsB, Integer valuesB,
                                        String labelsC,
                                   Integer valuesC,String title, String xLabel, String yLabel, Integer xTicksRotation,
                                        String colorA, String colorB, String colorC,Boolean showValues, String address) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, 50051).usePlaintext().build();
        ResumeProcessorClient client = new ResumeProcessorClient(channel);

        GRPC.DonutInfo donutInfo = GRPC.DonutInfo.newBuilder()
                .addLabels("A")
                .addLabels("B")
                .addLabels("C")
                .addValues(10)
                .addValues(20)
                .addValues(30)
                .setHoleSize(0.4f)
                .setTitle("Donut Chart")
                .addColors("red")
                .addColors("green")
                .addColors("blue")
                .build();
        GRPC.GraphInfo graphInfoDonut = GRPC.GraphInfo.newBuilder().setDonutInfo(donutInfo).build();
        client.generateGraph("Test Donut Chart", GRPC.GraphType.DONUT, graphInfoDonut, "donut_chart.png",
                800, 600, "white", "cividis", true, false, false, "right", 16, 12, 12,
                "-", "o", 1.0f, "gray", "--", 0.5f, "%.2f");
      //  channel.shutdown();
    }
    public static void createNetworkDiagram(List<String> labelsA,
                                            List<Integer> valuesA,
                                            List<String> labelsB,
                                            List<Integer> valuesB,
                                            List<String> labelsC,
                                            List<Integer> valuesC,
                                            String title,
                                            String xLabel,
                                            String yLabel,
                                            Integer xTicksRotation,
                                            String colorA,
                                            String colorB,
                                            String colorC,
                                            Boolean showValues,
                                            String fileName,
                                            String address) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, 50051).usePlaintext().build();
        ResumeProcessorClient client = new ResumeProcessorClient(channel);

        GRPC.NetworkDiagramInfo.Builder networkDiagramInfoBuilder = GRPC.NetworkDiagramInfo.newBuilder();

        if (labelsA != null && !labelsA.isEmpty()) {
            for (int i = 0; i < labelsA.size(); i++) {
                networkDiagramInfoBuilder.addNodes(labelsA.get(i));
            }
        }

        if (labelsB != null && !labelsB.isEmpty()) {
            for (int i = 0; i < labelsB.size(); i++) {
                networkDiagramInfoBuilder.addNodes(labelsB.get(i));
            }
        }

        if (labelsC != null && !labelsC.isEmpty()) {
            for (int i = 0; i < labelsC.size(); i++) {
                networkDiagramInfoBuilder.addNodes(labelsC.get(i));
            }
        }

        networkDiagramInfoBuilder.setTitle(title);

        GRPC.GraphInfo graphInfoNetwork = GRPC.GraphInfo.newBuilder().setNetworkDiagramInfo(networkDiagramInfoBuilder.build()).build();
        client.generateGraph("Network Diagram", GRPC.GraphType.NETWORK_DIAGRAM, graphInfoNetwork, fileName,
                800, 600, "white", "magma", true, false, false, "left", 16, 12, 12,
                "-", "o", 1.0f, "gray", "--", 0.5f, "%.2f");

    }
}
