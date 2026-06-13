package br.edu.ufrgs;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class SalesOpsApplication extends Application {
    // Empty — WildFly scans and registers resources automatically
}