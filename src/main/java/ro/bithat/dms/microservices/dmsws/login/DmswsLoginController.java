package ro.bithat.dms.microservices.dmsws.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.microservices.dmsws.file.UserToken;

@RestController("/dmsws/login")
public class DmswsLoginController {

	@Autowired
	private DmswsLoginService service;;
	
	@PostMapping(value = "/login")
	public UserToken loginUserPass(@RequestParam String user, @RequestParam String password){
		return service.login(user, password);
	}
	
	@GetMapping("/loginForToken")
	public String loginForToken(@RequestParam String user, @RequestParam String password){
		return service.loginForToken(user, password);
	}
	
	@GetMapping("/checkTokenValidity")
	public boolean checkTokenValidity(@RequestParam String token){
		return service.checkTokenValidity(token);
	}
	
}
