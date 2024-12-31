package richlife.gateway.RichLife_Pharma.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import richlife.gateway.RichLife_Pharma.util.JwtUtil;

import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${admin.protected.post-paths}")
    private String adminProtectedPostPath;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Retrieve method and path
            String method = request.getMethod() != null ? request.getMethod().name() : "";
            String path = request.getURI().getPath();

            if ("POST".equalsIgnoreCase(method) && adminProtectedPostPath.equals(path)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization Header");
                }

                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization Header");
                }

                String token = authHeader.substring(7); // Remove "Bearer " prefix

                try {
                    // Extract claims from token
                    Map<String, Object> claims = jwtUtil.extractAllClaims(token);
                    String role = (String) claims.get("role");

                    // Check ADMIN role
                    if (role == null || !role.equalsIgnoreCase("ADMIN")) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: ADMIN role required for POST");
                    }

                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token: " + e.getMessage());
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
