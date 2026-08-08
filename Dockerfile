FROM eclipse-temurin:21-jdk-jammy

COPY target/mcp-server-0.0.1.jar /home/

CMD ["java", "-jar", "/home/mcp-server-0.0.1.jar"]
#CMD ["sleep", "1000"]
EXPOSE 8081