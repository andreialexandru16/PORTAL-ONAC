package ro.bithat.dms.security;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.UserToken;

import javax.annotation.PostConstruct;

@SpringComponent
public class AnonymousAuthenticationHelper {

	//Preluare valori application.properties

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

	private UserToken userWithUserToken;// = buildAnonymousPrinicpal(); 
	
    public UserToken getUserToken() {
		return userWithUserToken;
	}
    
    @PostConstruct
	private UserToken buildAnonymousPrinicpal() {
    	UserToken dmsToken = new UserToken();
    	dmsToken.setToken(anonymousToken);
    	dmsToken.setUsername(anonymousUsername);
    	dmsToken.setUserId(anonymousUserId+"");
    	dmsToken.setUnitId(anonymousUnitId+"");
		dmsToken.setEmail(anonymousEmail+"");
    	userWithUserToken = dmsToken;
    	return dmsToken;
	}

}
