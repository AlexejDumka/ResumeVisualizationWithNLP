import grpc
from concurrent import futures
import time
import grpc_pb2
import grpc_pb2_grpc
import matplotlib.pyplot as plt
import networkx as nx
from wordcloud import WordCloud
import io


class ResumeProcessorServicer(grpc_pb2_grpc.ResumeProcessorServicer):
    def GenerateGraph(self, request, context):
        plt.figure(figsize=(request.width / 100, request.height / 100))

        fig, ax = plt.subplots(figsize=(request.width / 100, request.height / 100))

        ax.set_facecolor(request.background_color)
        if request.grid:
            ax.grid(True, color=request.grid_color, linestyle=request.grid_line_style,
                    linewidth=request.grid_line_width)
        else:
            ax.grid(False)

        if request.type == grpc_pb2.GraphType.BAR:
            self._generate_bar_graph(request, ax)
        elif request.type == grpc_pb2.GraphType.PIE:
            self._generate_pie_chart(request, ax)
        elif request.type == grpc_pb2.GraphType.DONUT:
            self._generate_donut_chart(request, ax)
        elif request.type == grpc_pb2.GraphType.NETWORK_DIAGRAM:
            self._generate_network_diagram(request, ax)
        else:
            plt.plot([1, 2, 3], [1, 4, 9])

        plt.tight_layout(pad=3.0)
        buf = io.BytesIO()
        plt.savefig(buf, format='png', bbox_inches='tight', pad_inches=0.5)
        buf.seek(0)
        return grpc_pb2.ImageResponse(image_data=buf.getvalue())

    def GenerateWordCloud(self, request, context):
        wordcloud = WordCloud(
            width=request.width,
            height=request.height,
            max_font_size=request.max_font_size,
            background_color=request.background_color,
            colormap=request.colormap,
            margin=request.margin
        ).generate(request.text)

        plt.figure(figsize=(request.width / 100, request.height / 100))
        plt.imshow(wordcloud, interpolation='bilinear')
        plt.axis("off")
        buf = io.BytesIO()
        plt.savefig(buf, format='png', bbox_inches='tight')
        buf.seek(0)
        return grpc_pb2.ImageResponse(image_data=buf.getvalue())

    def _generate_bar_graph(self, request, ax):
        labels = request.info.bar_info.labels
        values = request.info.bar_info.values
        colors = request.info.bar_info.colors or ['skyblue'] * len(labels)

        ax.bar(labels, values, color=colors)
        ax.set_title(request.info.bar_info.title if request.info.bar_info.title else 'Bar Chart',
                     fontsize=request.title_size)
        ax.set_xlabel(request.info.bar_info.xlabel if request.info.bar_info.xlabel else 'Categories',
                      fontsize=request.xlabel_size)
        ax.set_ylabel(request.info.bar_info.ylabel if request.info.bar_info.ylabel else 'Values',
                      fontsize=request.ylabel_size)
        ax.tick_params(axis='x', rotation=request.info.bar_info.xticks_rotation, labelsize=request.tick_labels_size)

        if request.info.bar_info.show_values_on_bars:
            for i, v in enumerate(values):
                ax.text(i, v + max(values) * 0.01, str(v), ha='center', fontsize=request.tick_labels_size)

    def _generate_pie_chart(self, request, ax):
        colors = request.info.pie_info.colors or None
        wedges, texts, autotexts = ax.pie(request.info.pie_info.values, labels=request.info.pie_info.labels,
                                          colors=colors,
                                          autopct='%1.1f%%' if request.info.pie_info.show_percentages_on_pie else None)
        ax.set_title(request.info.pie_info.title if request.info.pie_info.title else 'Pie Chart',
                     fontsize=request.title_size)

    def _generate_donut_chart(self, request, ax):
        colors = request.info.donut_info.colors or None
        wedges, texts, autotexts = ax.pie(request.info.donut_info.values, labels=request.info.donut_info.labels,
                                          colors=colors,
                                          autopct='%1.1f%%', wedgeprops=dict(width=request.info.donut_info.hole_size))
        ax.set_title(request.info.donut_info.title if request.info.donut_info.title else 'Donut Chart',
                     fontsize=request.title_size)
        plt.setp(autotexts, size=10, weight="bold")

    def _generate_network_diagram(self, request, ax):
        G = nx.Graph()
        for edge in request.info.network_diagram_info.edges:
            G.add_edge(edge.source, edge.target)
        nx.draw(G, ax=ax, with_labels=True, node_size=700, node_color="skyblue", pos=nx.spring_layout(G))
        ax.set_title(
            request.info.network_diagram_info.title if request.info.network_diagram_info.title else 'Network Diagram',
            fontsize=request.title_size)


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    grpc_pb2_grpc.add_ResumeProcessorServicer_to_server(ResumeProcessorServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    try:
        while True:
            time.sleep(86400)
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':
    serve()
