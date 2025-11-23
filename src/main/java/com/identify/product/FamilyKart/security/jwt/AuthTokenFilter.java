package com.identify.product.FamilyKart.security.jwt;

import com.identify.product.FamilyKart.security.jwt.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;



    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.debug("doFilterInternal called for URI: {}", request.getRequestURI());
        try {
            String jwtToken = parseJWT(request);

            if (jwtToken != null && jwtUtils.validateToken(jwtToken)) {
                String usernameFromJWT = jwtUtils.getUsernameFromJWT(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromJWT);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails
                                .getAuthorities());
                //store the authentication token in the security context
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                //setting the authentication in the security context
                //so that the user is authenticated for the current request
                //and the user details can be accessed in the controller
                //by calling SecurityContextHolder.getContext().getAuthentication()
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                logger.debug("Roles from JWT: {}", userDetails.getAuthorities());
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }
        // sending the request to the next filter in the chain
        // if the request is authenticated it will be passed to the controller
        // if the request is not authenticated it will be rejected by the spring security filter
        // if there is no jwt token in the request it will be considered as an unauthenticated request
        // if there is a jwt token in the request it will be validated
        // if the token is valid the user details will be fetched from the database
        // and the authentication token will be created
        // the authentication token will be stored in the security context
        // the request will be passed to the next filter in the chain
        // if the token is not valid the request will be considered as an unauthenticated request
        // and pass it to controller if no other filter is present
        filterChain.doFilter(request, response);
    }

//  in case of getting token from cookie we need to enable this.
//    private String parseJWT(HttpServletRequest request) {
//
//
//        String jwtFromHeader = jwtUtils.getJwtCookie(request);
//        logger.debug("JWT from header: {}", jwtFromHeader);
//        return jwtFromHeader;
//
//    }

    private String parseJWT(HttpServletRequest request) {


        String jwtFromHeader = jwtUtils.getJWTFromHeader(request);
        if (jwtFromHeader != null) {
            logger.debug("JWT from header: {}", jwtFromHeader);
            return jwtFromHeader;
        } else {
            logger.debug("No JWT found in Authorization header");
        }
        String jwtCookie = jwtUtils.getJwtCookie(request);
        if(jwtCookie!=null)
        {
            logger.debug("JWT from cookie: {}", jwtCookie);
            return jwtCookie;
        }
        else
        {
            logger.debug("No JWT found in cookies");
        }

        return null;



    }
}
