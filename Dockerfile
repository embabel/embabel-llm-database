FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Set working directory
WORKDIR /build

# Copy Maven configuration files
COPY ./ ./

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM maven:3.9.6-eclipse-temurin-21
# Create app user with home directory
RUN useradd -r -m -s /bin/false app

# Set working directory
WORKDIR /app

# Copy Maven configuration files first
COPY --from=builder /build/embabel-database-server/target/*.jar ./app.jar

# Copy the model collection
COPY --from=builder /build/data ./

# Change ownership to app user
RUN chown -R app:app .

# Switch to app user
USER app

# Expose port
EXPOSE 8000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8000/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]