package ro.bithat.dms.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.bithat.dms.config.LogbackFilter;
import ro.bithat.dms.microservices.dmsws.file.User;
import ro.bithat.dms.microservices.dmsws.file.UserToken;
import ro.bithat.dms.microservices.dmsws.login.DmswsLoginService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ContCurentPortalE;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CustomLogoutSuccessHandler extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${logout.wordpress.url:/logout}")
    private String logoutUrl;
    @Value("${logout.wordpress.url:/}")
    private String logoutWordpressUrl;
    @Value("${wordpress.url}")
    private String wordpressUrl;


    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        String refererUrl = request.getHeader("Referer");
        logger.info("Logout from: " + refererUrl);

       // super.onLogoutSuccess(request, response, authentication);
        if (authentication instanceof OidUsernamePasswordAuthenticationToken) {
            OidUsernamePasswordAuthenticationToken oidUsernamePasswordAuthenticationToken = (OidUsernamePasswordAuthenticationToken) authentication;
            OAuth2LoginAuthenticationToken oAuth2LoginAuthenticationToken = oidUsernamePasswordAuthenticationToken.getOAuth2LoginAuthenticationToken();

            if (oAuth2LoginAuthenticationToken.getAccessToken() != null){
                response.sendRedirect(getFinalLogoutWordpressUrl() +"&id_token_hint="+oAuth2LoginAuthenticationToken.getAccessToken().getTokenValue());
                super.onLogoutSuccess(request, response, authentication);

                return;
            }
        }

        response.sendRedirect(wordpressUrl);
        super.onLogoutSuccess(request, response, authentication);

    }
    private String getFinalLogoutWordpressUrl() {
        return getLogoutWordpressUrl().equals("/")?getWordpressUrl():getLogoutWordpressUrl();
    }

    private String getLogoutWordpressUrl() {
        return logoutWordpressUrl;
    }

    private String getWordpressUrl() {
        return wordpressUrl;
    }
}
