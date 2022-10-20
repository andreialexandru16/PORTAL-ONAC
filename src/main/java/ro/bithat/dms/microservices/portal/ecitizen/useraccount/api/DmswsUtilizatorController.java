package ro.bithat.dms.microservices.portal.ecitizen.useraccount.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.ParamCuIstoric;
import ro.bithat.dms.microservices.dmsws.file.WsAndUserInfo;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.payment.OrderStatusResponse;
import ro.bithat.dms.microservices.dmsws.payment.PaymentService;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.UtilizatorList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.*;
import ro.bithat.dms.security.AnonymousAuthenticationHelper;
import ro.bithat.dms.security.OidUsernamePasswordAuthenticationToken;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.security.UserWithUserToken;

import java.io.IOException;
import java.util.Optional;

@RestController
public class DmswsUtilizatorController{

	@Autowired
	private DmswsUtilizatorService service;

	@Autowired
	private PS4Service ps4Service;

	@Value("${dmsws.url}")
	private String wsUrl;
	@Value("${zuulproxy.url:}")
	private String zuulproxyUrl;
	@Value("${portal.url}")
	private String portalUrl;

	@Value("${wordpress.url}")
	private String wordpressUrl;

	@Value("${dmsws.anonymous.token}")
	private String anonymousToken;

	@Value("${google.recaptcha.sitekey:}")
	private String captchaSiteKey;
	@Value("${dmsws.unitname:}")
	private String unitName;
	@Value("${dmsws.login.url:/login2}")
	private String loginUrl;
	@Value("${dmsws.logout.url:/logout}")
	private String logoutUrl;
	@Value("${custom.inregistrareurl:}")
	private String customInregistrareUrl;
	@Value("${dmsws.id_registru_decizii:}")
	private Integer idRegistruDecizii;

	@Value("${dmsws.id_registru_ordine:}")
	private Integer idRegistruOrdine;

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/dmsws/utilizator/getStatusOrderApi")
	public OrderStatusResponse getStatusOrderApi(@RequestBody String orderId) {
		return paymentService.getStatusOrderApi(orderId);

	}
	@GetMapping("dmsws/utilizator/userIsLogged")
	public String userIsLogged()  {
		SecurityContext context = SecurityContextHolder.getContext();
		 return String.valueOf(context.getAuthentication().getPrincipal() instanceof UserWithUserToken);
	}
	@GetMapping("/dmsws/api/getCaptchaKey")
	public String getCaptchaKey()  {
		return captchaSiteKey;
	}

	@GetMapping("dmsws/utilizator/getWsUrl")
	public String getWsUrl()  {

		if(zuulproxyUrl!=null && !zuulproxyUrl.isEmpty()){
			return  zuulproxyUrl;
		}else{
			return wsUrl;
		}

	}

	@GetMapping("dmsws/utilizator/getFirstName")
	public String getFirstName()  {
		return SecurityUtils.getFirstName();
	}

	@GetMapping("dmsws/utilizator/getFirstNameAndCompany")
	public String getFirstNameAndCompany()  {
		String result = SecurityUtils.getFirstName()==null?SecurityUtils.getLastName():SecurityUtils.getFirstName() ;
		if(SecurityUtils.getContCurentPortalE().getTertParinteUserCurent()!=null ){
			if(SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getNumeComplet()!=null){
				result+= "("+ SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getNumeComplet()+ ")";
			}
		}
		return  result;
	}

	@GetMapping("dmsws/utilizator/getUsername")
	public String getUsername()  {
		return SecurityUtils.getUsername();
	}

	@GetMapping("dmsws/utilizator/getAnonymousToken")
	public String getAnonymousToken()  {
		return anonymousToken;
	}

	@GetMapping("dmsws/utilizator/getPortalUrl")
	public String getPortalUrl()  {
		return portalUrl;
	}

	@GetMapping("dmsws/utilizator/getWorpresUrl")
	public String getWorpresUrl()  {
		return wordpressUrl;
	}
	@GetMapping("dmsws/utilizator/getUnitName")
	public String getUnitName()  {
		return unitName;
	}
	@GetMapping("dmsws/utilizator/getLoginUrl")
	public String getLoginUrl()  {
		return loginUrl;
	}

	@GetMapping("dmsws/utilizator/getLogoutUrl")
	public String getLogoutUrl()  {
//		SecurityContext context = SecurityContextHolder.getContext();
//		if (context != null && context.getAuthentication() != null && context.getAuthentication() instanceof OidUsernamePasswordAuthenticationToken) {
//			OidUsernamePasswordAuthenticationToken oidUsernamePasswordAuthenticationToken = (OidUsernamePasswordAuthenticationToken) context.getAuthentication();
//			OAuth2LoginAuthenticationToken oAuth2LoginAuthenticationToken = oidUsernamePasswordAuthenticationToken.getOAuth2LoginAuthenticationToken();
//
//			if (oAuth2LoginAuthenticationToken.getAccessToken() != null){
//				return logoutUrl +"&id_token_hint="+oAuth2LoginAuthenticationToken.getAccessToken();
//			}
//		}

		return logoutUrl;
	}

	@GetMapping("dmsws/utilizator/getCustomInregistrareUrl")
	public String getCustomInregistrareUrl()  {
		return customInregistrareUrl;
	}

	@GetMapping("dmsws/utilizator/getIdRegistruDecizii")
	public Integer getIdRegistruDecizii()  {
		return idRegistruDecizii;
	}
	@GetMapping("dmsws/utilizator/getIdRegistruOrdine")
	public Integer getIdRegistruOrdine()  {
		return idRegistruOrdine;
	}

	@PostMapping(value = "/dmsws/utilizator/resetPassword")
	public BaseModel resetPassword(@RequestParam String token, @RequestParam String newPass) {
		return service.resetPassword(token, newPass);

	}
	@PostMapping(value = "/dmsws/utilizator/resetPasswordBody")
	public BaseModel resetPasswordBody(@RequestParam String token, @RequestBody ChangePassReq changePassReq) {
		return service.resetPasswordBody(token, changePassReq.getNewPassword());

	}
	@PostMapping(value = "/dmsws/utilizator/resetPassByEmail/{email}")
	public BaseModel resetPassByEmail(@PathVariable String email) {
		return service.resetPasswordByEmail(email);
	}


	@PostMapping("/dmsws/utilizator/cookie_accepted")
	public BaseModel userHasAcceptCookie() throws ServerErrorException, ServerWebInputException, IOException {
		return service.userHasAcceptCookie(SecurityUtils.getToken());
	}
	@GetMapping("/dmsws/utilizator/getPersoanaFizicaJuridica")
	public PersoanaFizicaJuridica getPersoanaFizicaJuridica() {
		 return service.getPersoanaFizicaJuridica(SecurityUtils.getToken(), Math.toIntExact(SecurityUtils.getUserId()));
	}

	@PostMapping(value = "/dmsws/utilizator/updateLoginFailed/{username}", produces = {"application/json"})
	public Utilizator updateLoginFailed(@PathVariable String username) {
		return service.updateLoginFailed(SecurityUtils.getToken(), username);
	}

	@GetMapping(value = "/dmsws/utilizator/getUserInfoByUsername/{username}", produces = {"application/json"})
	public Utilizator getUserInfoByUsername(@PathVariable String username) {
		return service.getUserInfoByUsername(SecurityUtils.getToken(), username);
	}

	@PostMapping(value = "/dmsws/portalflow/sendFluxByIdFisier/{idFisier}", produces = {"application/json"})
	public StandardResponse sendFluxByIdFisier(@PathVariable String idFisier) {
		return service.sendFluxByIdFisier(SecurityUtils.getToken(), idFisier);
	}

	@PostMapping(value = "/dmsws/portalflow/sendFluxRequestByIdFisier/{idFisier}", produces = {"application/json"})
	public StandardResponse sendFluxRequestByIdFisier(@PathVariable Long idFisier) {
		return service.sendFluxRequestByIdFisier(SecurityUtils.getToken(), idFisier);
	}

	@GetMapping(value = "/dmsws/portalflow/checkFileOnFlow", produces = {"application/json"})
	public BaseModel checkFileOnFlow(@RequestParam String idFisier) {
		return service.checkFileOnFlow(SecurityUtils.getToken(), idFisier);
	}

	@GetMapping("/dmsws/utilizator/getInfoPsiholog/{codRup}/{cnp}")
	public PsihologList getInfoPsiholog(@PathVariable String codRup, @PathVariable String cnp) {
		return service.getInfoPsiholog(SecurityUtils.getToken(), codRup, cnp);
	}

	@GetMapping("/dmsws/users/getSubconturi")
	public UtilizatorList getSubconturi() {
		return service.getSubconturi(SecurityUtils.getToken());
	}

	@PostMapping(value = "/dmsws/users/insertSubcont/{username}", produces = {"application/json"})
	public Utilizator insertSubcont(@PathVariable String username) {
		Utilizator u =  service.insertSubcont(SecurityUtils.getToken(),username);
		return u;
	}

	@PostMapping(value = "/dmsws/users/deleteSubcont/{id}", produces = {"application/json"})
	public Utilizator deleteSubcont(@PathVariable String id) {
		Utilizator u =  service.deleteSubcont(SecurityUtils.getToken(),id);
		return u;
	}

	@GetMapping("/dmsws/utilizator/getWsAndUserInfo")
	public WsAndUserInfo WsAndUserInfo(){
		return ps4Service.getWsAndUserInfo();
	}

	@GetMapping( "/dmsws/utilizator/getListaParinti")
	public PersoanaFizicaJuridicaList getListaParinti() {
		return service.getListaParinti(SecurityUtils.getToken(), SecurityUtils.getUserId());
	}
	@GetMapping( "/dmsws/utilizator/resetDateContCurent/{idTertParinte}")
	public BaseModel resetDateContCurent(@PathVariable Integer idTertParinte) {
		BaseModel resp= new BaseModel();
		resp.setResult("OK");
		try{

			ContCurentPortalE contCurentPortalE =  service.getContCurentPortalE(SecurityUtils.getToken(), Integer.parseInt(SecurityUtils.getUserId().toString()), Optional.ofNullable(idTertParinte));

			SecurityUtils.setContCurentPortalE(contCurentPortalE);
		}catch (Exception e){
			resp.setResult("ERR");
		}
		return resp;
	}
	@GetMapping("/dmsws/utilizator/getSysParam/{cod}")
	public ParamCuIstoric getSysParam(@PathVariable String cod)  {
		return service.getSysParam(SecurityUtils.getToken(),cod);
	}
}
