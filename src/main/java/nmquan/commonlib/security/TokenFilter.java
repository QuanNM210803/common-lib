package nmquan.commonlib.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nmquan.commonlib.model.JwtUser;
import nmquan.commonlib.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenFilter extends OncePerRequestFilter {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = JwtUtils.getToken(request);
            if(jwt != null){
                JwtUser jwtUser = JwtUtils.validate(jwt, SECRET_KEY);
                var authentication = new UsernamePasswordAuthenticationToken(jwtUser.getUser(), jwtUser.getUsername(), jwtUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e){
            log.error("Authenticated: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
