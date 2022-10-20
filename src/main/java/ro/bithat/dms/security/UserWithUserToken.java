package ro.bithat.dms.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.UserToken;
import ro.bithat.dms.microservices.dmsws.login.DmswsLoginService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ContCurentPortalE;

import java.util.Collection;

public class UserWithUserToken extends User {

	private volatile UserToken userToken;
	private volatile ContCurentPortalE contCurentPortalE;
	private String stupidWayToStorePass;
	private volatile long tokenTime;
	
	public UserWithUserToken(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, UserToken userToken,ContCurentPortalE contCurentPortalE ) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		stupidWayToStorePass = password;
		tokenTime = System.currentTimeMillis();
		this.userToken = userToken;
		this.contCurentPortalE = contCurentPortalE;
		// TODO Auto-generated constructor stub
	}

	public UserToken getUserToken() {
		refreshToken();
		return userToken;
	}

	public ContCurentPortalE getContCurentPortalE() {
		refreshToken();
		return contCurentPortalE;
	}

	public void setContCurentPortalE(ContCurentPortalE contCurentPortalE) {
		this.contCurentPortalE = contCurentPortalE;
	}

	private void refreshToken() {
		if ((System.currentTimeMillis() - tokenTime) > 55*60*1000) {
			tokenTime = System.currentTimeMillis();
			try {
				userToken = BeanUtil.getBean(DmswsLoginService.class).login(userToken.getUsername(), stupidWayToStorePass);
			}catch (Exception e) {
				throw new BadCredentialsException(e.getMessage());
			}
			if (!userToken.getResult_code().equals("OK_VALID_LOGIN")) {
	    		throw new BadCredentialsException(userToken.getResult_code());
	    	}
		}
	}
}
