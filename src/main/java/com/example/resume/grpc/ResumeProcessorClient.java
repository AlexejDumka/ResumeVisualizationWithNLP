package com.example.resume.grpc;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class TextProcessorClient {
    private final TextProcessorGrpc.TextProcessorBlockingStub blockingStub;

    public TextProcessorClient(ManagedChannel channel) {
        blockingStub = TextProcessorGrpc.newBlockingStub(channel);
    }

    public void generateGraph(String text, GraphType type, GraphInfo info, String outputPath) throws IOException {
        TextRequest request = TextRequest.newBuilder().setText(text).setType(type).setInfo(info).build();
        ImageResponse response = blockingStub.generateGraph(request);
        saveImage(response.getImageData().toByteArray(), outputPath);
    }

    public void generateWordCloud(String text, String outputPath) throws IOException {
        TextRequest request = TextRequest.newBuilder().setText(text).build();
        ImageResponse response = blockingStub.generateWordCloud(request);
        saveImage(response.getImageData().toByteArray(), outputPath);
    }

    private void saveImage(byte[] imageData, String outputPath) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        BufferedImage image = ImageIO.read(bis);
        ImageIO.write(image, "png", new File(outputPath));
    }

    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        TextProcessorClient client = new TextProcessorClient(channel);

        BarInfo barInfo = BarInfo.newBuilder().addLabels("A").addLabels("B").addLabels("C").addValues(10).addValues(20).addValues(30).build();
        GraphInfo graphInfoBar = GraphInfo.newBuilder().setBarInfo(barInfo).build();
        client.generateGraph("Test Bar Graph", GraphType.BAR, graphInfoBar, "bar_graph.png");

        PieInfo pieInfo = PieInfo.newBuilder().addLabels("A").addLabels("B").addLabels("C").addValues(10).addValues(20).addValues(30).build();
        GraphInfo graphInfoPie = GraphInfo.newBuilder().setPieInfo(pieInfo).build();
        client.generateGraph("Test Pie Chart", GraphType.PIE, graphInfoPie, "pie_chart.png");

        DonutInfo donutInfo = DonutInfo.newBuilder().addLabels("A").addLabels("B").addLabels("C").addValues(10).addValues(20).addValues(30).setHoleSize(0.4f).build();
        GraphInfo graphInfoDonut = GraphInfo.newBuilder().setDonutInfo(donutInfo).build();
        client.generateGraph("Test Donut Chart", GraphType.DONUT, graphInfoDonut, "donut_chart.png");

        NetworkDiagramInfo networkDiagramInfo = NetworkDiagramInfo.newBuilder().addNodes("A").addNodes("B").addNodes("C")
                .addEdges(Edge.newBuilder().setSource("A").setTarget("B").build())
                .addEdges(Edge.newBuilder().setSource("B").setTarget("C").build()).build();
        GraphInfo graphInfoNetwork = GraphInfo.newBuilder().setNetworkDiagramInfo(networkDiagramInfo).build();
        client.generateGraph("Test Network Diagram", GraphType.NETWORK_DIAGRAM, graphInfoNetwork, "network_diagram.png");

        client.generateWordCloud("Test WordCloud", "wordcloud.png");

        channel.shutdown();
    }
}
