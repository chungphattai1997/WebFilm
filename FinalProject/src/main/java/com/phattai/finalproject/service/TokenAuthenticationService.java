package com.phattai.finalproject.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 86_400_000; // 1 day
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, String username, List<GrantedAuthority> roles) {
        String infoUser = username + "&&" + roles.toString();
        String JWT = Jwts.builder().setSubject(infoUser)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {

            if (!Jwts.parser().setSigningKey(SECRET).isSigned(token.replace(TOKEN_PREFIX, ""))) {
                return null;
            }

            Jws<Claims> jwt = null;
            try {
                jwt = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
            } catch (SignatureException ex) {
                return null;
            }

            if (jwt == null) {
                return null;
            }

            // parse the token.
//			String infoUser = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//					.getBody().getSubject();

            String infoUser = jwt.getBody().getSubject();
            String[] temp = infoUser.split("&&");
            String username = temp[0];
            String role = "ROLE_" + temp[1].substring(1, temp[1].length() - 1).toUpperCase();

            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                private static final long serialVersionUID = 3468963110258137402L;
                @Override
                public String getAuthority() {
                    return role;
                }
            };

            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(grantedAuthority);

            return username != null ? new UsernamePasswordAuthenticationToken(username, null, roles) : null;
        }
        return null;
    }
}
