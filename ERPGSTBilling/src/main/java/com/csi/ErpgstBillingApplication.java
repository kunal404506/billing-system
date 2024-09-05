package com.csi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "CSI GST BILLING APPLICATION", version = "1.0", description = "GST billing application rest api documentation"),
        servers = @Server(description = "Local Tomcat 10", url = "http://localhost:2025"))
@SecurityScheme(name = "Bearer Auth", description = "Provide JWT Token", scheme = "Bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class ErpgstBillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpgstBillingApplication.class, args);
    }

}
