#!/bin/bash
protoc --java_out=. --grpc-java_out=. --proto_path=. gRPC.proto