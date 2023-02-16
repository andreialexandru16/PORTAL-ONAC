package ro.bithat.dms.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.config.LogbackFilter;
import ro.bithat.dms.microservices.dmsws.file.FormulareService;
import ro.bithat.dms.microservices.dmsws.file.User;
import ro.bithat.dms.microservices.dmsws.file.UserToken;
import ro.bithat.dms.microservices.dmsws.login.DmswsLoginService;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ContCurentPortalE;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.Formular;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/login2")
public class LoginController {
    @Autowired
    private DmswsLoginService loginService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DmswsUtilizatorService dmswsUtilizatorService;

    @Autowired
    private FormulareService formulareService;

    //PRELUARE VALORI DIN APPLICATION.PROPERTIES
    @Value("${dmsws.anonymous.username}")
    private String anonymousUsername;

    @Value("${dmsws.anonymous.userid}")
    private Long anonymousUserId;

    @Value("${dmsws.anonymous.unitid}")
    private Long anonymousUnitId;

    @Value("${dmsws.anonymous.token}")
    private String anonymousToken;

    @Value("${dmsws.anonymous.email}")
    private String anonymousEmail;

    @Value("${security.use.anonymous.on.login.fail:true}")
    boolean useAnonymousOnLoginFail;

    @Value("${portal.endpoint.admins:}")
    private String portalEndpointsAdmin;


    @RequestMapping(method = RequestMethod.GET)
    public void login() {
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String performLogin(
            @RequestBody User user,
            HttpServletRequest request, HttpServletResponse response) {

        String userName= user.getUsername();
        String password= user.getPassword();
        try {
            logger.info("user attempts to login" + userName);

            UserToken userToken = null;
            try {
                userToken = loginService.login(userName, password);
            } catch (Exception e) {
                logger.error("login failed for user " + userName + " with " + e.getMessage());
                if (!useAnonymousOnLoginFail) {
                    throw new BadCredentialsException(e.getMessage(), e);
                }
            //    return continueAuth(new UsernamePasswordAuthenticationToken(buildAnonymousPrinicpal(), password, buildAnonymousPrinicpal().getAuthorities()));
            }

            if (!userToken.getResult_code().equals("OK_VALID_LOGIN")) {
                logger.error("login failed for user " + userName + " with " + userToken.getResult_code());
                if (!useAnonymousOnLoginFail) {
                    throw new BadCredentialsException(userToken.getResult_code());
                }
            //    return continueAuth(new UsernamePasswordAuthenticationToken(buildAnonymousPrinicpal(), password, buildAnonymousPrinicpal().getAuthorities()));
            }

            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            logger.info("user successfully logged in" + userName + " with userid= " + userToken.getUserId() + " for unitid=" + userToken.getUnitId());
            if (portalEndpointsAdmin != null && !portalEndpointsAdmin.isEmpty() && portalEndpointsAdmin.contains(userName)) {
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_ENDPOINT_ADMIN"));
                logger.info("user successfully logged in with ROLE_ENDPOINT_ADMIN " + userName);
            }

            MDC.put(LogbackFilter.USERNAME, userToken.getUsername());
            MDC.put(LogbackFilter.USERID, userToken.getUserId());
            ContCurentPortalE contCurentPortalE = dmswsUtilizatorService.getContCurentPortalE(userToken.getToken(), Integer.valueOf(userToken.getUserId()), Optional.empty());
            contCurentPortalE.getUserCurent().setDrepturiTipDoc(formulareService.getDrepturi(userToken.getToken()).getDrepturiTipDoc());
            SecurityUtils.forceGetAllDocumentTypes(userToken.getToken());

            final UserWithUserToken principal = new UserWithUserToken(userName, password, true, true,
                    true, true, grantedAuths, userToken,contCurentPortalE);
            UsernamePasswordAuthenticationToken  auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
           // {idUser:32321, token:f"da"}
            String resp = "{\"status\": true, \"error\": \" \",\"id\":\""+userToken.getUserId()+"\",\"token\":\"" + userToken.getToken()+"\",\"prenume\":\""+userToken.getPrenume()+"\",\"expires\":\""+userToken.getExpires()+"\"}";

             return continueAuth(auth,resp);
        } catch (BadCredentialsException ex) {
            return "{\"status\": false, \"error\": \"Bad Credentials\"}";
        }
    }

    private String continueAuth(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, String response) {
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
     //   return "{\"status\": true}";
        return response;
    }

    private UserWithUserToken buildAnonymousPrinicpal() {
        UserToken dmsToken = new UserToken();
        dmsToken.setToken(anonymousToken);
        dmsToken.setUsername(anonymousUsername);
        dmsToken.setUserId(anonymousUserId + "");
        dmsToken.setUnitId(anonymousUnitId + "");
        dmsToken.setEmail(anonymousEmail + "");

        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));

        return new UserWithUserToken(anonymousUsername, "", true, true,
                true, true, grantedAuths, dmsToken, new ContCurentPortalE());
    }

    public String loginWithToken(String token){
        UserToken userToken = loginService.validateToken(token);
        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        logger.info("user successfully logged in" + userToken.getUsername() + " with userid= " + userToken.getUserId() + " for unitid=" + userToken.getUnitId());
        if (portalEndpointsAdmin != null && !portalEndpointsAdmin.isEmpty() && portalEndpointsAdmin.contains(userToken.getUsername())) {
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ENDPOINT_ADMIN"));
            logger.info("user successfully logged in with ROLE_ENDPOINT_ADMIN " + userToken.getUsername());
        }

        MDC.put(LogbackFilter.USERNAME, userToken.getUsername());
        MDC.put(LogbackFilter.USERID, userToken.getUserId());
        ContCurentPortalE contCurentPortalE = dmswsUtilizatorService.getContCurentPortalE(userToken.getToken(), Integer.valueOf(userToken.getUserId()), Optional.empty());
        contCurentPortalE.getUserCurent().setDrepturiTipDoc(formulareService.getDrepturi(userToken.getToken()).getDrepturiTipDoc());
        SecurityUtils.forceGetAllDocumentTypes(userToken.getToken());

        final UserWithUserToken principal = new UserWithUserToken(userToken.getUsername(), userToken.getToken(), true, true,
                true, true, grantedAuths, userToken,contCurentPortalE);
        UsernamePasswordAuthenticationToken  auth = new UsernamePasswordAuthenticationToken(principal, userToken.getToken(), grantedAuths);
        // {idUser:32321, token:f"da"}
        String resp = "{\"status\": true, \"error\": \" \",\"id\":\""+userToken.getUserId()+"\",\"token\":\"" + userToken.getToken()+"\",\"prenume\":\""+userToken.getPrenume()+"\",\"expires\":\""+userToken.getExpires()+"\"}";

        return continueAuth(auth,resp);
    }
}
