package ca.mcgill.ecse321.config;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean {

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {

            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);

        } else {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Invalid Authorization header");
            }

            final String token = authHeader.substring(7);

            try {
                final Claims claims = Jwts.parser().setSigningKey("elonmusk").parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid token, please log-in again");
            }

            chain.doFilter(req, res);
        }
    }
}
