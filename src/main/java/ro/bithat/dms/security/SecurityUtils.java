package ro.bithat.dms.security;

import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.UserToken;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ContCurentPortalE;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SecurityUtils takes care of all such static operations that have to do with
 * security and querying rights from different beans of the UI.
 *
 */
public final class SecurityUtils {


	private static List<TipDocument> tipDoc;
	private SecurityUtils() {
		// Util methods only
	}

	/**
	 * Tests if the request is an internal framework request. The test consists of
	 * checking if the request parameter is present and if its value is consistent
	 * with any of the request types know.
	 *
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return true if is an internal framework request. False otherwise.
	 */
	static boolean isFrameworkInternalRequest(HttpServletRequest request) {
		final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
		return request.getRequestURI().contains("sw.js") || parameterValue != null
				&& Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
	}

	/**
	 * Tests if some user is authenticated. As Spring Security always will create an {@link AnonymousAuthenticationToken}
	 * we have to ignore those tokens explicitly.
	 */
	static boolean isUserLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null
				&& !(authentication instanceof AnonymousAuthenticationToken)
				&& authentication.isAuthenticated();
	}

	/**
	 * Gets the user name of the currently signed in user.
	 *
	 * @return the user name of the current user or <code>null</code> if the
	 *         user has not signed in
	 */
	public static String getUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null || context.getAuthentication().getPrincipal() == null) {
			return null;
		}
		if (!(context.getAuthentication().getPrincipal() instanceof UserDetails)) {
			return "nouser";
		}
		UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}

	public static String getUserIdAsString() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null || context.getAuthentication().getPrincipal() == null) {
			return null;
		}
		if (!(context.getAuthentication().getPrincipal() instanceof UserDetails)) {
			return "nouser";
		}
		UserWithUserToken userDetails = (UserWithUserToken) context.getAuthentication().getPrincipal();
		return userDetails.getUserToken().getUserId();
	}
	
	public static UserToken getUserToken() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null || context.getAuthentication().getPrincipal() == null) {
			return BeanUtil.getBean(AnonymousAuthenticationHelper.class).getUserToken();
		}
		if (!(context.getAuthentication().getPrincipal() instanceof UserWithUserToken)) {
			return BeanUtil.getBean(AnonymousAuthenticationHelper.class).getUserToken();
		}
		UserWithUserToken userDetails = (UserWithUserToken) context.getAuthentication().getPrincipal();
		return userDetails.getUserToken();
	}
	
	public static String getToken() {
		return getUserToken().getToken();
	}

	public static List<TipDocument> getAllDocumentTypes() {
		if(tipDoc!=null && tipDoc.size()!=0){
			return tipDoc;
		}else{

			tipDoc=  BeanUtil.getBean(PS4Service.class).getAllDocumentTypes();
			return tipDoc;

		}
	}

	public static List<TipDocument> getAllDocumentTypes(Integer tipDocId) {
		if(tipDoc!=null && tipDoc.size()!=0){
			for (TipDocument clasaDoc:tipDoc){
				if (Objects.equals(clasaDoc.getId(), tipDocId) && clasaDoc.getDocumentList() != null && clasaDoc.getDocumentList().size()>0){
					return tipDoc;
				}
			}
		}

		tipDoc=  BeanUtil.getBean(PS4Service.class).getAllDocumentTypes();
		return tipDoc;
	}

	public static void forceGetAllDocumentTypes(String token) {

			tipDoc=  BeanUtil.getBean(PS4Service.class).getAllDocumentTypesByToken(token);

	}
	public static ContCurentPortalE getContCurentPortalE() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null || context.getAuthentication().getPrincipal() == null) {
			return new ContCurentPortalE();
		}
		if (!(context.getAuthentication().getPrincipal() instanceof UserWithUserToken)) {
			return new ContCurentPortalE();
		}
		UserWithUserToken userDetails = (UserWithUserToken) context.getAuthentication().getPrincipal();
		return userDetails.getContCurentPortalE();
	}
	public static void setContCurentPortalE(ContCurentPortalE contCurentPortalE) {
		try{
			SecurityContext context = SecurityContextHolder.getContext();

			UserWithUserToken userDetails = (UserWithUserToken) context.getAuthentication().getPrincipal();
			userDetails.setContCurentPortalE(contCurentPortalE);
		}catch (Exception e){

		}
	}
	public static void setUserId(Long userId) {
		getUserToken().setUserId(userId.toString());
	}
	public static void setToken(String token) {
		getUserToken().setToken(token);
	}
	public static Long getUserId() {
		return Long.valueOf(getUserToken().getUserId());
	}

	public static Long getUnitId() {
		return Long.valueOf(getUserToken().getUnitId());
	}

	public static String getEmail() {
		return getUserToken().getEmail();
	}

	public static String getFullName() { return getUserToken().getNpPerson(); }

	public static String getLastName() { return getUserToken().getNume(); }

	public static String getFirstName() { return getUserToken().getPrenume(); }

	public static Integer getPersonId() { return getUserToken().getIdPersoana(); }

	public static Integer getCookieAccepted() { return getUserToken().getCookieAccepted(); }

	/**
	 * Check if currently signed-in user is in the role with the given role
	 * name.
	 *
	 * @param role
	 *            the role to check for
	 * @return <code>true</code> if user is in the role, <code>false</code>
	 *         otherwise
	 */
	public static boolean isCurrentUserInRole(String role) {
		return getUserRoles().stream().filter(roleName -> roleName.equals(Objects.requireNonNull(role))).findAny()
				.isPresent();
	}

	/**
	 * Gets the roles the currently signed-in user belongs to.
	 *
	 * @return a set of all roles the currently signed-in user belongs to.
	 */
	public static Set<String> getUserRoles() {
		SecurityContext context = SecurityContextHolder.getContext();
		return context.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());
	}


}