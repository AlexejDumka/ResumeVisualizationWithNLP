# ResumeVisualizationWithNLP

## Overview

ResumeVisualizationWithNLP is a project aimed at leveraging Natural Language Processing (NLP) techniques to analyze resumes and visualize their contents. This tool is designed to help recruiters and HR professionals streamline the resume screening process, identify key skills, and better understand candidate profiles.

---

## Features

- **Resume Parsing**: Extracts structured information from resumes in various formats (PDF, DOCX, etc.).
- **Keyword Extraction**: Identifies and highlights key skills, job roles, and other critical elements.
- **Data Visualization**: Generates insightful visualizations such as skill distributions, experience timelines, and keyword frequency graphs.
- **Natural Language Processing**: Uses NLP to analyze text for skills and experience.


---

## Technologies Used

- **Programming Languages**: Java, Python
- **Libraries and Frameworks**:
  - Apache OpenNLP
  - ModelMapper Mapstruct
  - Apache PDFBox
  - Apache Commons IO
  - Spring Boot
  - Spring Data JPA
  - gRPC
  - Hibernate ORM
- **Database**: PostgreSQL
- **Deployment**: Docker

---

## Installation

### Prerequisites

- Java 17+
- Maven 3.6+
- Docker (optional for containerized deployment)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/AlexejDumka/ResumeVisualizationWithNLP.git
   cd ResumeVisualizationWithNLP
   ```
2. Run the PostgreSQL database from container using Dockerfile:
   ```bash
      docker build -t resumeDB 
   ```
   ```bash
      docker run --name resumeDB-container -p 5432:5432 -d resumeDB
   ```
3. Run GRPC server from container using Docker compose:
   ```bash
      docker-compose up
   ```
4. Build the project using Maven:
   ```bash
      mvn clean install
   ```
5. Copy the resume file to the `Resumes` directory:
6. Run the application:
   ```bash
      mvn spring-boot:run
   ```
4. Access the of web  UI of application in your browser at `http://localhost:8080`.

> **_NOTE:_**  To compile the gRPC server directly, navigate to the `grpc_server` directory and run the `grpc_compile.sh` script:


> **_NOTE:_**  To compile the gRPC client directly, performs 2 steps:
> 1. Check the pom file regarding the following dependencies:
```
    <!-- gRPC Dependencies -->
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-netty-shaded</artifactId>
        <version>1.41.0</version>
    </dependency>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-protobuf</artifactId>
        <version>1.41.0</version>
    </dependency>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-stub</artifactId>
        <version>1.41.0</version>
    </dependency>
    
    <!-- Protocol Buffers -->
    <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
        <version>3.17.3</version>
    </dependency>
```

> 2. run the following command:
```bash 
protoc --java_out=src/main/java --grpc-java_out=src/main/java --proto_path=. text_processor.proto
   ```

## Project folders and files structure

```
ResumeVisualizationWithNLP/
|-- pom.xml                        # Maven build file
|-- src/main/java/                 # Java source code
|-- src/main/resources/            # Application resources (templates, properties)
|-- README.md                      # Project documentation
|-- docker-compose.yml             # Docker Compose configuration
|-- Dockerfile                     # Application Dockerfile
|-- grpc_server/                   # gRPC server implementation
|-- grpc_server/Dockerfile         # gRPC server Dockerfile
|-- grpc_server/grpc_compile.py    # Script to compile gRPC protos
|-- grpc_server/docker-compose.yml # Docker Compose configuration for gRPC server
|-- grpc_server/gRPC.proto         # gRPC service definition 
|-- grpc_server/grpc_pb2.py        # gRPC Python bindings
|-- grpc_server/grpc_pb2_grpc.py   # gRPC Python bindings 
|-- grpc_server/requirerments.txt  # Python dependencies
|-- grpc_server/server.py          # gRPC server implementation
|-- postgreSQL/                    # PostgreSQL database setup
|-- postgreSQL/init-schema.sql     # SQL script for database scheme initialization
|-- postgreSQL/Dockerfile          # PostgreSQL Dockerfile
|-- postgreSQL/init-db.sh          # Script to initialize the database
|-- Resumes/                       # Directory for storing resume files
|-- Resumes/Processed/             # Directory for storing processed resume
```

---