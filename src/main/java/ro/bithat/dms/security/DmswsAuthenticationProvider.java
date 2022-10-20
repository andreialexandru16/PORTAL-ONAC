package ro.bithat.dms.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ro.bithat.dms.config.LogbackFilter;
import ro.bithat.dms.microservices.dmsws.file.UserToken;
import ro.bithat.dms.microservices.dmsws.login.DmswsLoginService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ContCurentPortalE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DmswsAuthenticationProvider implements AuthenticationProvider {
 
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DmswsLoginService loginService;
	@Autowired
	private DmswsUtilizatorService dmswsUtilizatorService;
	@Value("${dmsws.anonymous.username}")
	private String anonymousUsername;
	
	@Value("${dmsws.anonymous.userid}")
	private Long anonymousUserId;
	
	@Value("${dmsws.anonymous.unitid}")
	private Long anonymousUnitId;
	
	@Value("${dmsws.anonymous.token}")
	private String anonymousToken;
	
	@Value("${security.use.anonymous.on.login.fail:true}")
	boolean useAnonymousOnLoginFail;

	@Value("${portal.endpoint.admins:}")
	private String portalEndpointsAdmin;

    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.info("user attempts to login" + userName);
        
        UserToken userToken = null;
        try {
        	userToken = loginService.login(userName, password);
        }catch (Exception e) {
        	logger.error("login failed for user " + userName + " with " + e.getMessage());
        	if (!useAnonymousOnLoginFail) {
        		throw new BadCredentialsException(e.getMessage(), e);
        	}
    		return new UsernamePasswordAuthenticationToken(buildAnonymousPrinicpal(), password, buildAnonymousPrinicpal().getAuthorities());
		}

    	if (!userToken.getResult_code().equals("OK_VALID_LOGIN")) {
    		logger.error("login failed for user " + userName + " with " + userToken.getResult_code());
    		if (!useAnonymousOnLoginFail) {
    			throw new BadCredentialsException(userToken.getResult_code());
    		}
    		return new UsernamePasswordAuthenticationToken(buildAnonymousPrinicpal(), password, buildAnonymousPrinicpal().getAuthorities());
    	}

        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        logger.info("user successfully logged in" + userName + " with userid= " + userToken.getUserId() + " for unitid=" +userToken.getUnitId());
        if (portalEndpointsAdmin != null && !portalEndpointsAdmin.isEmpty() && portalEndpointsAdmin.contains(userName)) {
        	grantedAuths.add( new SimpleGrantedAuthority("ROLE_ENDPOINT_ADMIN"));
        	logger.info("user successfully logged in with ROLE_ENDPOINT_ADMIN " + userName);
        }
        
        MDC.put(LogbackFilter.USERNAME, userToken.getUsername());
        MDC.put(LogbackFilter.USERID, userToken.getUserId());
		ContCurentPortalE contCurentPortalE = dmswsUtilizatorService.getContCurentPortalE(userToken.getToken(), Integer.valueOf(userToken.getUserId()), Optional.empty());
		SecurityUtils.forceGetAllDocumentTypes(userToken.getToken());

		final UserWithUserToken principal = new UserWithUserToken(userName, password, true, true,
        		true, true, grantedAuths, userToken,contCurentPortalE);
        final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);

		return auth;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    private UserWithUserToken buildAnonymousPrinicpal() {
    	UserToken dmsToken = new UserToken();
    	dmsToken.setToken(anonymousToken);
    	dmsToken.setUsername(anonymousUsername);
    	dmsToken.setUserId(anonymousUserId+"");
    	dmsToken.setUnitId(anonymousUnitId+"");
    	
    	final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        
    	return new UserWithUserToken(anonymousUsername, "", true, true, 
        		true, true, grantedAuths, dmsToken, new ContCurentPortalE());
	}
}