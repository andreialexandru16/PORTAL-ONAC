package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.colaboration.AmenziResponse;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.ParamCuIstoric;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.login.LiferayResponse;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.LinkUtilList;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.UtilizatorList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.*;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.FisierDraftExtendedList;

import java.util.Optional;

@Service
public class DmswsUtilizatorService extends DmswsRestService{

	@Value("${dms.id.flux.aprobare}")
	private String idFluxAprobare;
	private static Logger logger = LoggerFactory.getLogger(DmswsUtilizatorService.class);

	public PersoanaFizicaJuridica updatePersoanaFizicaJuridica(String token, Integer idUser, PersoanaFizicaJuridica persoanaFizicaJuridica) {
		return put(persoanaFizicaJuridica, PersoanaFizicaJuridica.class, checkBaseModel(), getDmswsUrl()+"/utilizator/{token}/updatePersoanaFizicaJuridica/{idUser}", token, idUser);
	}

	public PersoanaFizicaJuridica getPersoanaFizicaJuridica(String token, Integer idUser) {
		return get(PersoanaFizicaJuridica.class, checkBaseModel(), getDmswsUrl()+"/utilizator/{token}/getPersoanaFizicaJuridica/{idUser}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idUser);
	}
	public ContCurentPortalE getContCurentPortalE(String token, Integer idUser, Optional<Integer> idTertParinte) {
		String url= getDmswsUrl()+"/utilizator/{token}/getDateContCurent/{idUser}";
		if(idTertParinte.isPresent()){
			url+="?idTertParinte="+idTertParinte.get();
		}
		return get(ContCurentPortalE.class, checkBaseModel(), url ,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idUser);
	}

	public BaseModel resetPassword(String token, String newPass) {

			return post(null, BaseModel.class, checkBaseModel(), getDmswsUrl()+"/utilizator/{token}/resetPassword?newPassword={newPass}", token, newPass);

    }

	public BaseModel resetPasswordBody(String token, String newPass) {

		ChangePassReq changePassReq= new ChangePassReq();
		changePassReq.setNewPassword(newPass);
		changePassReq.setNewPassword2(newPass);
		changePassReq.setOldPassword("");
		return post(changePassReq, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(), getDmswsUrl()+"/utilizator/{token}/resetPasswordBody", token);


	}

	public BaseModel resetPasswordByEmail(String email) {
		try {
			return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(), getDmswsUrl()+"/login/resetPasswordByEmail/{email}/", email);
		}catch (Throwable e) {
			//silent
			logger.error("missing user tried to reset password:"+email, e);
			return new BaseModel();
		}
    }

	public Utilizator updateLoginFailed(String token, String username) {
		try {
			return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, Utilizator.class, checkBaseModel(),
					getDmswsUrl()+"/utilizator/{token}/updateLoginFailed/{username}/",token, username);
		}catch (Throwable e) {
			//silent
			logger.error("Update login failed for : "+username, e);
			return new Utilizator();
		}
	}

	public Utilizator getUserInfoByUsername(String token, String username) {
			return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, Utilizator.class, checkBaseModel(),
					getDmswsUrl()+"/utilizator/{token}/getUserInfoByUsername/{username}/",token, username);
	}

	public StandardResponse sendFluxByIdFisier(String token, String idFisier) {
		return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,StandardResponse.class, checkStandardResponse(),
				getDmswsUrl()+"/portalflow/{token}/sendFluxByIdFisier/{idFisier}/",token, idFisier);
	}

	public StandardResponse sendFluxRequestByIdFisier(String token, Long idFisier) {
		return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, StandardResponse.class, checkStandardResponse(),
				getDmswsUrl() + "/portalflow/{token}/sendFluxRequestByIdFisier/{idFisier}", token, idFisier);
	}

	public BaseModel checkFileOnFlow(String token, String idFisier) {
		String url=getDmswsUrl()+"/portalflow/{token}/checkFileOnFlow?&idFisier="+idFisier+"&idFlux="+idFluxAprobare;
		return get(BaseModel.class, checkBaseModel(),url ,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public BaseModel userHasAcceptCookie(String token){
		return post(null, BaseModel.class, checkBaseModel(),
				getDmswsUrl()+"/utilizator/{token}/setCookieAccepted", token);
	}
	public LinkUtilList getLinkuriUtile(String token) {
		return get(LinkUtilList.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getLinkuriUtileProiect",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public PsihologList getInfoPsiholog(String token, String codRup, String cnp) {
		return get(PsihologList.class, checkBaseModel(), getDmswsUrl()+"/utilizator/{token}/getInfoPsiholog/{codRup}/{cnp}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, codRup,cnp);
	}

    public UtilizatorList getSubconturi(String token) {
		return get(UtilizatorList.class, checkBaseModel(), getDmswsUrl()+"/utilizator/{token}/getSubconturi",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

	public Utilizator insertSubcont(String token, String utilizator) {
		return post(null,
				Utilizator.class,
				checkBaseModelWithExtendedInfo(),
				getDmswsUrl()+"/utilizator/{token}/insertSubcont/{username}/",token,utilizator);
	}

	public Utilizator deleteSubcont(String token, String id) {
		return post(null,
				Utilizator.class,
				checkBaseModelWithExtendedInfo(),
				getDmswsUrl()+"/utilizator/{token}/deleteSubcont/{id}/",token,id);

	}

	public AmenziResponse getAmenzi(String token) throws Exception{
		return get(AmenziResponse.class, checkBaseModel(), getDmswsUrl()+"/taxe_pn/{token}/getAmenzi",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public BaseModel insertUserWSO2 (String token, String username){
		return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/wso2/{token}/insertUserWSO2/"+username+"/",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public PersoanaFizicaJuridicaList getListaParinti(String token, Long idUser) {
		return get(PersoanaFizicaJuridicaList.class, checkStandardResponse(), getDmswsUrl()+"/utilizator/{token}/getListaParinti/{idUser}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idUser);
	}

	public ParamCuIstoric getSysParam(String token, String cod){
		return get(ParamCuIstoric.class, checkBaseModel(), getDmswsUrl()+"/portal_e/{token}/getSysParam/{cod}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,cod);
	}


}
