package ro.bithat.dms.microservices.portal.ecitizen.website.controllers;

import com.vaadin.flow.server.StreamResource;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.FileUtils;
import ro.bithat.dms.microservices.dmsws.flow.ListaPublica;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Completari;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocumentList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.InboxInfo;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocumentList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.Tert;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.*;
import ro.bithat.dms.microservices.portal.ecitizen.website.services.ANREService;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RestController
public class ANREController {

    @Autowired
    private URLUtil urlUtil;

    public static final String ERROR_TEXT = "Error Status Code ";


    @Autowired
    private ANREService anreService;

    @Value("${ps4.ecitizen.workflow.status.changes.required}")
    private String idChangeRequire;

    @Value("${ps4.ecitizen.workflow.status.request.completed}")
    private String idChangeCompleted;

    @GetMapping("/dmsws/anre/getInregistrariConducere")
    public ConducereList getInregistrariConducere() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariConducere(SecurityUtils.getToken(),idTert);

        }else{
            return new ConducereList();
        }
    }

    @GetMapping("/dmsws/anre/getInregistrariSucursala")
    public SucursalaList getInregistrariSucursala() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariSucursala(SecurityUtils.getToken(),idTert);

        }else{
            return new SucursalaList();
        }
    }


    @GetMapping("/dmsws/anre/getInregistrariAutMediu")
    public AutMediuList getInregistrariAutMediu() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariAutMediu(SecurityUtils.getToken(),idTert);

        }else{
            return new AutMediuList();
        }
    }

    @PostMapping("/dmsws/anre/getExameneInstalatori")
    public LegitimatieList getExameneInstalatori(@RequestBody Legitimatie legitimatie, @RequestParam(value = "indexStart", required = false) Integer indexStart,
                                                     @RequestParam(value = "indexEnd", required = false) Integer indexEnd) {


        return anreService.getExameneInstalatori(SecurityUtils.getToken(),legitimatie, Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd));


    }

    @PostMapping("/dmsws/anre/getExameneElectricieni")
    public LegitimatieList getExameneElectricieni(@RequestBody Legitimatie legitimatie, @RequestParam(value = "indexStart", required = false) Integer indexStart,
                                                 @RequestParam(value = "indexEnd", required = false) Integer indexEnd) {


        return anreService.getExameneElectricieni(SecurityUtils.getToken(),legitimatie, Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd));


    }

    @PostMapping("/dmsws/anre/getAutorizatiiInstalatori")
    public LegitimatieList getAutorizatiiInstalatori(@RequestBody Legitimatie legitimatie, @RequestParam(value = "indexStart", required = false) Integer indexStart,
                                                     @RequestParam(value = "indexEnd", required = false) Integer indexEnd) {


        return anreService.getAutorizatiiInstalatori(SecurityUtils.getToken(),legitimatie, Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd));


    }
    @PostMapping("/dmsws/anre/getAutorizatiiInstalatoriCount/")
    public BaseModel getAutorizatiiInstalatoriCount(@RequestBody Legitimatie legitimatie) {


        return anreService.getAutorizatiiInstalatoriCount(SecurityUtils.getToken(),legitimatie);


    }

    @PostMapping("/dmsws/anre/getAutorizatiiElectricieni")
    public LegitimatieList getAutorizatiiElectricieni(@RequestBody Legitimatie legitimatie, @RequestParam(value = "indexStart", required = false) Integer indexStart,
                                                     @RequestParam(value = "indexEnd", required = false) Integer indexEnd) {


        return anreService.getAutorizatiiElectricieni(SecurityUtils.getToken(),legitimatie, Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd));


    }
    @PostMapping("/dmsws/anre/getAutorizatiiElectricieniCount/")
    public BaseModel getAutorizatiiElectricieniCount(@RequestBody Legitimatie legitimatie) {


        return anreService.getAutorizatiiElectricieniCount(SecurityUtils.getToken(),legitimatie);


    }

    @PostMapping("/dmsws/anre/getAtestate/{domeniu}")
    public LicenteFilteredResponse getAtestate(@PathVariable String domeniu,@RequestBody LicenteFilteredRequest licenta, @RequestParam(value = "indexStart", required = false) Integer indexStart,
                                                     @RequestParam(value = "indexEnd", required = false) Integer indexEnd) {


        return anreService.getAtestate(SecurityUtils.getToken(),domeniu,licenta, Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd));


    }
    @PostMapping("/dmsws/anre/getAtestateCount/{domeniu}")
    public BaseModel getAtestateCount(@PathVariable String domeniu,@RequestBody LicenteFilteredRequest licenta) {


        return anreService.getAtestateCount(SecurityUtils.getToken(),domeniu,licenta);


    }
    @PostMapping("/dmsws/anre/getExameneInstalatoriCount/")
    public BaseModel getExameneInstalatoriCount(@RequestBody Legitimatie legitimatie) {


        return anreService.getExameneInstalatoriCount(SecurityUtils.getToken(),legitimatie);


    }

    @PostMapping("/dmsws/anre/getExameneElectricieniCount/")
    public BaseModel getExameneElectricieniCount(@RequestBody Legitimatie legitimatie) {


        return anreService.getExameneElectricieniCount(SecurityUtils.getToken(),legitimatie);


    }

    @GetMapping("/dmsws/anre/getInregistrariAutGospApelor")
    public AutGospApelorList getInregistrariAutGospApelor() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariAutGospApelor(SecurityUtils.getToken(),idTert);

        }else{
            return new AutGospApelorList();
        }
    }

    @GetMapping("/dmsws/anre/getInregistrariStudii")
    public StudiiList getInregistrariStudii() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariStudii(SecurityUtils.getToken(),idTert);

        }else{
            return new StudiiList();
        }
    }
    @GetMapping("/dmsws/anre/getInregistrariAdeverinte")
    public AdeverinteList getInregistrariAdeverinte() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariAdeverinte(SecurityUtils.getToken(),idTert);

        }else{
            return new AdeverinteList();
        }
    }
    @GetMapping("/dmsws/anre/getInregistrariCursuri")
    public CursuriList getInregistrariCursuri() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariCursuri(SecurityUtils.getToken(),idTert);

        }else{
            return new CursuriList();
        }
    }

    @GetMapping("/dmsws/anre/getInregistrariDocumenteCerere/{idFisierCerere}")
    public DocumentList getInregistrariDocumenteCerere(@PathVariable Integer idFisierCerere) {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariDocumenteCerere(SecurityUtils.getToken(),idTert,idFisierCerere);

        }else{
            return new DocumentList();
        }
    }


    @GetMapping("/dmsws/anre/getExperientaManageriala/{idPers}")
    public ExperientaManagerialaList getExperientaManageriala(@PathVariable String idPers) {

        if(idPers!=null ){
            return anreService.getExperientaManageriala(SecurityUtils.getToken(),idPers);

        }else{
            return new ExperientaManagerialaList();
        }
    }

    @PostMapping("/dmsws/anre/adauga_conducere/")
    public BaseModel adaugaConducere(  @RequestBody Conducere conducere) {
        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.adaugaConducere(SecurityUtils.getToken(), conducere,idTert );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/adaugaSucursala/")
    public BaseModel adaugaSucursala(  @RequestBody Sucursala sucursala) {
        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.adaugaSucursala(SecurityUtils.getToken(), sucursala,idTert );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/adaugaAutMediu/")
    public BaseModel adaugaAutMediu(  @RequestBody AutMediu autMediu) {
        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.adaugaAutMediu(SecurityUtils.getToken(), autMediu,idTert );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/adaugaAutGospApelor/")
    public BaseModel adaugaAutGospApelor(  @RequestBody AutGospApelor autGospApelor) {
        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.adaugaAutGospApelor(SecurityUtils.getToken(), autGospApelor,idTert );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/adauagaStudii/")
    public BaseModel adauagaStudii(  @RequestBody Studii studii) {
        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.adaugaStudii(SecurityUtils.getToken(), studii,idTert );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping(value = "/dmsws/anre/uploadFileToStudii/",consumes = "multipart/form-data",produces = "text/html")
    public ResponseEntity<String> uploadFile(
            HttpServletRequest httpServletRequest) {
        FileUtils requestForm = FileUtils.getRequestForm(httpServletRequest);



            FileUtils.UploadFileDescription ciFile = (requestForm.getUploadedFiles().size() > 0 ? requestForm.getUploadedFiles().get(0) : null);

            String url="";
            try{
                url= urlUtil.getPath(httpServletRequest);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_TEXT + e.getMessage());

            }
            anreService.uploadFisierToStudii(SecurityUtils.getToken(),
                    ciFile != null ? ciFile.getFileName() : null, ciFile != null ? ciFile.getFileData() : new byte[0], requestForm.getParamMap());


        return ResponseEntity.ok("OK");

    }

    @PostMapping(value = "/dmsws/anre/uploadFileToAdeverinte/",consumes = "multipart/form-data",produces = "text/html")
    public ResponseEntity<String> uploadFileToAdeverinte(
            HttpServletRequest httpServletRequest) {
        FileUtils requestForm = FileUtils.getRequestForm(httpServletRequest);


        if(requestForm.uploadedFiles.size() == 1) {
            FileUtils.UploadFileDescription ciFile = requestForm.getUploadedFiles().get(0);

            String url="";
            try{
                url= urlUtil.getPath(httpServletRequest);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_TEXT + e.getMessage());

            }
            anreService.uploadFisierToAdeverinta(SecurityUtils.getToken(),
                    ciFile.getFileName(), ciFile.getFileData(), requestForm.getParamMap());

        }
        return ResponseEntity.ok("OK");

    }

    @PostMapping(value = "/dmsws/anre/uploadFileToDocAnexat/",consumes = "multipart/form-data",produces = "text/html")
    public ResponseEntity<String> uploadFileToDocAnexat(
            HttpServletRequest httpServletRequest) {
        FileUtils requestForm = FileUtils.getRequestForm(httpServletRequest);


        if(requestForm.uploadedFiles.size() == 1) {
            FileUtils.UploadFileDescription ciFile = requestForm.getUploadedFiles().get(0);

            String url="";
            try{
                url= urlUtil.getPath(httpServletRequest);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_TEXT + e.getMessage());

            }
            anreService.uploadFileToDocAnexat(SecurityUtils.getToken(),
                    ciFile.getFileName(), ciFile.getFileData(), requestForm.getParamMap());

        }
        return ResponseEntity.ok("OK");

    }

    @PostMapping(value = "/dmsws/anre/trimiteCompletari/")
    public BaseModel trimiteCompletari(@RequestBody Completari completari) {
        completari.setIdChangeComplete(idChangeCompleted);
        completari.setIdChangeRequire(idChangeRequire);

        return anreService.trimiteCompletari(SecurityUtils.getToken(),completari);

    }

    @PostMapping(value = "/dmsws/anre/uploadFileToCursuri/",consumes = "multipart/form-data",produces = "text/html")
    public ResponseEntity<String> uploadFileToCursuri(
            HttpServletRequest httpServletRequest) {
        FileUtils requestForm = FileUtils.getRequestForm(httpServletRequest);


        if(requestForm.uploadedFiles.size() == 1) {
            FileUtils.UploadFileDescription ciFile = requestForm.getUploadedFiles().get(0);

            String url="";
            try{
                url= urlUtil.getPath(httpServletRequest);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_TEXT + e.getMessage());

            }
            anreService.uploadFisierToCursuri(SecurityUtils.getToken(),
                    ciFile.getFileName(), ciFile.getFileData(), requestForm.getParamMap());

        }
        return ResponseEntity.ok("OK");

    }

    @PostMapping("/dmsws/anre/editeaza_conducere/{idPers}")
    public BaseModel updateConducere(@PathVariable String idPers, @RequestBody Conducere conducere) {
        Integer idTert=null;


        if(idPers!=null ){
            return anreService.updateConducere(SecurityUtils.getToken(), conducere,idPers );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/editeazaSucursala/{id}")
    public BaseModel editeazaSucursala(@PathVariable String id, @RequestBody Sucursala sucursala) {
        Integer idTert=null;


        if(id!=null ){
            return anreService.editeazaSucursala(SecurityUtils.getToken(), sucursala,id );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }


    @PostMapping("/dmsws/anre/editeazaAutMediu/{id}")
    public BaseModel editeazaSucursala(@PathVariable String id, @RequestBody AutMediu autMediu) {
        Integer idTert=null;


        if(id!=null ){
            return anreService.editeazaAutMediu(SecurityUtils.getToken(), autMediu,id );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }


    @PostMapping("/dmsws/anre/editeazaAutGospApelor/{id}")
    public BaseModel editeazaSucursala(@PathVariable String id, @RequestBody AutGospApelor autGospApelor) {
        Integer idTert=null;


        if(id!=null ){
            return anreService.editeazaAutGospApelor(SecurityUtils.getToken(), autGospApelor,id );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/editeaza_studii/{id}")
    public BaseModel updateConducere(@PathVariable String id, @RequestBody Studii studii) {
        Integer idTert=null;


        if(id!=null ){
            return anreService.updateStudii(SecurityUtils.getToken(), studii,id );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/editeaza_experienta/{id}")
    public BaseModel updateExperienta(@PathVariable String id, @RequestBody ExperientaManageriala experientaManageriala) {
        Integer idTert=null;


        if(id!=null ){
            return anreService.updateExperienta(SecurityUtils.getToken(), experientaManageriala,id );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/adaugaExperientaManageriala/{idPers}")
    public BaseModel adaugaConducere( @PathVariable String idPers, @RequestBody ExperientaManageriala experientaManageriala) {


        if(idPers!=null ){
            return anreService.adaugaExperientaManageriala(SecurityUtils.getToken(), experientaManageriala,idPers );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @GetMapping("/dmsws/anre/stergeConducere/{idPers}")
    public BaseModel stergeConducere(@PathVariable String idPers) {


        return anreService.stergeConducere(SecurityUtils.getToken(),idPers);


    }
    @GetMapping("/dmsws/anre/stergeSediu/{id}")
    public BaseModel stergeSediu(@PathVariable String id) {


        return anreService.stergeSediu(SecurityUtils.getToken(),id);


    }

    @GetMapping("/dmsws/anre/stergeSucursala/{id}")
    public BaseModel stergeSucursala(@PathVariable String id) {


        return anreService.stergeSucursala(SecurityUtils.getToken(),id);


    }

    @GetMapping("/dmsws/anre/stergeAutMediu/{id}")
    public BaseModel stergeAutMediu(@PathVariable String id) {
        return anreService.stergeAutMediu(SecurityUtils.getToken(),id);
    }

    @GetMapping("/dmsws/anre/stergeAutGospApelor/{id}")
    public BaseModel stergeAutGospApelor(@PathVariable String id) {
        return anreService.stergeAutGospApelor(SecurityUtils.getToken(),id);
    }

    @GetMapping("/dmsws/anre/stergeFisierAutMediu/{id}")
    public BaseModel stergeFisierAutMediu(@PathVariable String id) {
        return anreService.stergeFisierAutMediu(SecurityUtils.getToken(),id);
    }

    @GetMapping("/dmsws/anre/stergeFisierAutGospApelor/{id}")
    public BaseModel stergeFisierAutGospApelor(@PathVariable String id) {
        return anreService.stergeFisierAutGospApelor(SecurityUtils.getToken(),id);
    }

    @PostMapping("/dmsws/anre/updateFisierAutMediu/{id}")
    public BaseModel stergeFisierAutMediu(@PathVariable String id, @RequestBody UpdateFisierName u) {
        return anreService.updateFisierAutMediu(SecurityUtils.getToken(),id, u);
    }

    @PostMapping("/dmsws/anre/updateFisierAutGospApelor/{id}")
    public BaseModel stergeFisierAutGospApelor(@PathVariable String id, @RequestBody UpdateFisierName u) {
        return anreService.updateFisierAutGospApelor(SecurityUtils.getToken(),id, u);
    }

    @GetMapping("/dmsws/anre/stergeStudii/{id}")
    public BaseModel stergeStudii(@PathVariable String id) {


        return anreService.stergeStudii(SecurityUtils.getToken(),id);


    }

    @GetMapping("/dmsws/anre/stergeCursuri/{id}")
    public BaseModel stergeCursuri(@PathVariable String id) {


        return anreService.stergeCursuri(SecurityUtils.getToken(),id);


    }

    @GetMapping("/dmsws/anre/stergeAdeverinte/{id}")
    public BaseModel stergeAdeverinte(@PathVariable String id) {


        return anreService.stergeAdeverinte(SecurityUtils.getToken(),id);


    }

    @GetMapping("/dmsws/anre/stergeExperienta/{id}")
    public BaseModel stergeExperienta(@PathVariable String id) {


        return anreService.stergeExperienta(SecurityUtils.getToken(),id);


    }

    @GetMapping("/dmsws/anre/stergeContact/{id}")
    public BaseModel stergeContact(@PathVariable String id) {


        return anreService.stergeContact(SecurityUtils.getToken(),id);


    }

    @GetMapping("/dmsws/anre/stergeActionar/{id}")
    public BaseModel stergeActionar(@PathVariable String id) {


        return anreService.stergeActionar(SecurityUtils.getToken(),id);


    }

    @GetMapping("/dmsws/anre/getConducere/{idPers}")
    public ConducereList getConducere(@PathVariable String idPers) {

        if(idPers!=null ){
            return anreService.getConducere(SecurityUtils.getToken(),idPers);

        }else{
            return new ConducereList();
        }
    }

    @GetMapping("/dmsws/anre/getSucursala/{id}")
    public SucursalaList getSucursala(@PathVariable String id) {

        if(id!=null ){
            return anreService.getSucursala(SecurityUtils.getToken(),id);

        }else{
            return new SucursalaList();
        }
    }

    @GetMapping("/dmsws/anre/getAutMediu/{id}")
    public AutMediuList getAutMediu(@PathVariable String id) {

        if(id!=null ){
            return anreService.getAutMediu(SecurityUtils.getToken(),id);

        }else{
            return new AutMediuList();
        }
    }

    @GetMapping("/dmsws/anre/getAutGospApelor/{id}")
    public AutGospApelorList getAutGospApelor(@PathVariable String id) {

        if(id!=null ){
            return anreService.getAutGospApelor(SecurityUtils.getToken(),id);

        }else{
            return new AutGospApelorList();
        }
    }

    @GetMapping("/dmsws/anre/getExperienta/{id}")
    public ExperientaManagerialaList getExperienta(@PathVariable String id) {

        if(id!=null ){
            return anreService.getExperienta(SecurityUtils.getToken(),id);

        }else{
            return new ExperientaManagerialaList();
        }
    }

    @GetMapping("/dmsws/anre/getStudii/{id}")
    public StudiiList getStudii(@PathVariable String id) {

        if(id!=null ){
            return anreService.getStudii(SecurityUtils.getToken(),id);

        }else{
            return new StudiiList();
        }
    }

    @GetMapping("/dmsws/anre/getListaPersoaneContact")
    public PersoaneContactList getListaPersoaneContact() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getListaPersoaneContact(SecurityUtils.getToken(),idTert);

        }else{
            return new PersoaneContactList();
        }
    }

    @GetMapping("/dmsws/anre/getListaActionari")
    public ActionariList getListaActionari() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getListaActionari(SecurityUtils.getToken(),idTert);

        }else{
            return new ActionariList();
        }
    }
    @GetMapping("/dmsws/anre/getInregistrariSedii")
    public SediuList getInregistrariSedii() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getInregistrariSedii(SecurityUtils.getToken(),idTert);

        }else{
            return new SediuList();
        }
    }
 @GetMapping("/dmsws/anre/getPersoanaContactById/{idTert}")
    public PersoaneContactList getPersoanaContactById(@PathVariable String idTert) {

            return anreService.getPersoanaContactById(SecurityUtils.getToken(),idTert);
    }

    @GetMapping("/dmsws/anre/getActionarById/{idTert}")
    public ActionariList getActionarById(@PathVariable String idTert) {

            return anreService.getActionarById(SecurityUtils.getToken(),idTert);
    }
    @GetMapping("/dmsws/anre/getSediuInfo/{idTert}")
    public SediuList getSediuInfo(@PathVariable String idTert) {

            return anreService.getSediuInfo(SecurityUtils.getToken(),idTert);
    }

    @PostMapping("/dmsws/anre/adauga_persoana_contact/")
    public BaseModel adaugaPersoanaContact(  @RequestBody PersoaneContact persoanaContact) {
        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.adaugaPersoanaContact(SecurityUtils.getToken(), persoanaContact,idTert );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }

    }
@PostMapping("/dmsws/anre/adaugaActionar/")
    public BaseModel adaugaActionar(  @RequestBody Actionari actionari) {
        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.adaugaActionar(SecurityUtils.getToken(), actionari,idTert );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }

    }


    @GetMapping("/dmsws/anre/getTertInfoById")
    public Tert getTertInfoById() {

        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
             if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                    idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                }
             } else {
                if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                    idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                }
             }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.getTertInfoById(SecurityUtils.getToken(),idTert);

        }else{
            return new Tert();
        }
    }

    @PostMapping("/dmsws/anre/updateCertificatConstatator/")
    public BaseModel updateCertificatConstatator(  @RequestBody CertificatConstatator certificat) {
        Integer idTert=null;
        try{
            if ( SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null  ){
                if(!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if ( SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        }catch (Exception e){
            idTert=null;
        }

        if(idTert!=null ){
            return anreService.updateCertificatConstatator(SecurityUtils.getToken(), certificat,idTert );

        }else{
            BaseModel baseModel= new BaseModel();
            baseModel.setResult("ERR");
            baseModel.setInfo("Nu exista niciun id tert pentru utilizatorul curent.");

            return baseModel;
        }
    }

    @PostMapping("/dmsws/anre/updateInfoContact")
    public BaseModel updateInfoContact(  @RequestBody PersoaneContact PersoaneContact) {


            return anreService.updateInfoContact(SecurityUtils.getToken(), PersoaneContact);

    }

    @PostMapping("/dmsws/anre/updateInfoActionar")
    public BaseModel updateInfoActionar(  @RequestBody Actionari actionari) {


            return anreService.updateInfoActionar(SecurityUtils.getToken(), actionari);

    }

    @PostMapping("/dmsws/anre/editeazaSediu")
    public BaseModel editeazaSediu(@RequestBody Sediu sediu) {
        return anreService.editeazaSediu(SecurityUtils.getToken(), sediu);
    }

    @PostMapping("/dmsws/anre/adaugaSediu")
    public BaseModel adaugaSediu(@RequestBody Sediu sediu) {
        Integer idTert = null;
        try {
            if (SecurityUtils.getContCurentPortalE() != null && SecurityUtils.getContCurentPortalE().getUserCurent() != null) {
                if (!SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1")) {
                    if (SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert();

                    }
                } else {
                    if (SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert() != null) {
                        idTert = SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert();
                    }
                }
            }
        } catch (Exception e) {
            idTert = null;
        }
        if (idTert != null) {
            sediu.setId_tert(idTert);
        }
        return anreService.adaugaSediu(SecurityUtils.getToken(), sediu);
    }

    @GetMapping("/dmsws/anre/getListTipDoc")
    public TipDocumentList getTipDocumentList(){
        return anreService.getTipDocumentList(SecurityUtils.getToken());
    }

    @GetMapping("/dmsws/anre/getInboxInfo/{username}")
    public InboxInfo getInboxInfo(@PathVariable String username){
        return anreService.getInboxInfo(SecurityUtils.getToken(), username);
    }

    @GetMapping("/dmsws/anre/getTipAdeverintaByIdGrad/{idGrad}")
    public LovList getTipAdeverintaByIdGrad(@PathVariable Integer idGrad)
    {
        return anreService.getTipAdeverintaByIdGrad(SecurityUtils.getToken(), idGrad);
    }

    @PostMapping("/dmsws/anre_licente/getLicenteFilteredCount/{idDomeniu}")
    public BaseModel getLicenteFilteredCount(
            @PathVariable Integer idDomeniu,
            @RequestBody LicenteFilteredRequest licenteFilteredRequest

    ) {

        return anreService.getLicenteFilteredCount(SecurityUtils.getToken(),idDomeniu,licenteFilteredRequest);
    }


    @PostMapping("/dmsws/anre_licente/getLicenteFiltered/{idDomeniu}")
    public LicenteFilteredResponse getLicenteFiltered(
            @PathVariable Integer idDomeniu,
            @RequestBody LicenteFilteredRequest licenteFilteredRequest,
            @RequestParam(value = "indexStart", required = false) Integer indexStart,
            @RequestParam(value = "indexEnd", required = false) Integer indexEnd
    ) {

        return anreService.getLicenteFiltered(SecurityUtils.getToken(),idDomeniu,licenteFilteredRequest,Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd));
    }

    @GetMapping("/dmsws/download")
    public String downloadByToken(  @RequestParam(value = "downloadLink", required = false) String downloadLink){
        String encoded = Base64.getEncoder().encodeToString(StreamResourceUtil.downloadFile(downloadLink));

        return encoded;
    }

    @PostMapping("/dmsws/anre/exportXlsx")
    public String exportXlsx(  @RequestBody ExportXlsxReq req,
                               @RequestParam(value = "baseUrl", required = false) String baseUrl){
        String encoded = Base64.getEncoder().encodeToString(StreamResourceUtil.exportXlsx(baseUrl,req));

        return encoded;
    }
}
