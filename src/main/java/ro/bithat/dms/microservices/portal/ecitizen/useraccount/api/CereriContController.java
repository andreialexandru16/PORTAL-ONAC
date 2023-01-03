package ro.bithat.dms.microservices.portal.ecitizen.useraccount.api;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.UtilizatorList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.TertList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.AddUtilizatorSecurityService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.CereriContService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsNomenclatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.*;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.*;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
public class CereriContController {

    @Value("${dmsws.anonymous.token}")
    private String anonymousToken;

    @Autowired
    private CereriContService serviceCereri;

    @Autowired
    private URLUtil urlUtil;

    @Autowired
    private AddUtilizatorSecurityService checkerService;


    public static final String ERROR_TEXT = "Error Status Code ";
    public static final String[] ALLOWED_EXTENSIONS = {"docx","doc","pdf","jpg","jpeg","png","svg","tif","tiff",".gif"};


    public List<PortalFile> getFilesOnWorkflowByUser(){
        return serviceCereri.getFilesOnWorkflowByUser(SecurityUtils.getToken()).getPortalFileList();
    }
    public List<PortalFile> getInvoicesByUser(){
        return serviceCereri.getInvoicesByUser(SecurityUtils.getToken()).getPortalFileList();
    }

    public List<PortalFile> getLimitedFilesOnWorkflowByUser(String nrRows){
        return serviceCereri.getLimitedFilesOnWorkflowByUser(SecurityUtils.getToken(),nrRows).getPortalFileList();
    }

    public List<PortalFile> getLimitedInvoicesByUser(String nrRows){
        return serviceCereri.getLimitedInvoicesByUser(SecurityUtils.getToken(),nrRows).getPortalFileList();
    }


    @PostMapping(value = "/dmsws/cerericont/inactivareCont/{id}", produces = {"application/json"})
    public Utilizator inactivareCont(@PathVariable String id) {
        Utilizator u =  serviceCereri.inactivareCont(SecurityUtils.getToken(),id);
        return u;
    }

    @PostMapping(value = "/dmsws/cerericont/activareCont/{id}", produces = {"application/json"})
    public Utilizator activareCont(@PathVariable String id) {
        Utilizator u =  serviceCereri.activareCont(SecurityUtils.getToken(),id);
        return u;
    }

    @GetMapping("/dmsws/cerericont/getSubconturi")
    public UtilizatorList getSubconturi() {
        return serviceCereri.getSubconturi(SecurityUtils.getToken());
    }

    @PostMapping(value = "/dmsws/cerericont/validareEmail/{idFisier}", produces = {"application/json"})
    public StandardResponse validareEmail(@PathVariable Long idFisier) {
        return serviceCereri.validareEmail(SecurityUtils.getToken(), idFisier);
    }

    @GetMapping(value = "/dmsws/cerericont/checkFileOnFlow", produces = {"application/json"})
    public BaseModel checkFileOnFlow(@RequestParam String idFisier) {
        return serviceCereri.checkFileOnFlow(SecurityUtils.getToken(), idFisier);
    }

    @GetMapping("/dmsws/cerericont/getListOperatorTipCredite")
    public TipOrdonatorList getListOperatorTipCredite() {
        return serviceCereri.getListOperatorTipCredite(anonymousToken);
    }

    @GetMapping("/dmsws/cerericont/getListDenumireCif")
    public InstiutiiList getListDenumireCif() {
        return serviceCereri.getListDenumireCif(anonymousToken);
    }

    @GetMapping("/dmsws/cerericont/getDownloadSablon")
    public UtilizatorDocument getDownloadSablon() {
        return serviceCereri.getDownloadSablon(anonymousToken);
    }

   @GetMapping("/dmsws/cerericont/getListJudete")
    public JudetList getListJudete() {
        return serviceCereri.getListJudete(anonymousToken);
    }

    @GetMapping("/dmsws/cerericont/getListLocalitate/{idLocalitate}")
    public LocalitateList getListLocalitate(@PathVariable String idLocalitate) {
        return serviceCereri.getListLocalitate(anonymousToken, idLocalitate);
    }


    private boolean validateCode(String code) {
        boolean result = checkerService.checkCode(code);
        if (result == false) {
            throw new IllegalAccessError();
        }
        return result;
    }
    public static class FileUploadParamRequest {
        final Map<String, String> paramMap = new HashMap<>();
        final List<UploadFileDescription> uploadedFiles = new ArrayList<>();


        public void putParam(String name, String value) {
            paramMap.put(name, value);
        }
        public void addUploadedFile(UploadFileDescription file) {
            uploadedFiles.add(file);
        }

        public Map<String, String> getParamMap() {
            return paramMap;
        }

        public List<UploadFileDescription> getUploadedFiles() {
            return uploadedFiles;
        }
    }
    public static class UploadFileDescription {
        final String fileName;

        final byte[] fileData;

        public UploadFileDescription(String fileName, byte[] fileData) {
            this.fileName = fileName;
            this.fileData = fileData;
        }

        public String getFileName() {
            return fileName;
        }

        public byte[] getFileData() {
            return fileData;
        }
    }
    private FileUploadParamRequest getRequestForm(HttpServletRequest httpServletRequest) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(
                new File(System.getProperty("java.io.tmpdir")));
        factory.setSizeThreshold(
                DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
        factory.setFileCleaningTracker(null);

        ServletFileUpload upload = new ServletFileUpload(factory);
        FileUploadParamRequest requestForm = new FileUploadParamRequest();

        if(isMultipart) {
            try {
                List items = upload.parseRequest(httpServletRequest);
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (!item.isFormField()) {
                        InputStream uploadedStream = item.getInputStream();
                        requestForm.addUploadedFile(new CereriContController.UploadFileDescription(item.getName(), IOUtils.toByteArray(uploadedStream)));
                    } else  {
                        ((DiskFileItem) item).setDefaultCharset("UTF-8");
                        requestForm.putParam(item.getFieldName(), item.getString());
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return requestForm;
    }
    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    UtilizatorAcOe getUtilizatorAc(FileUploadParamRequest formRequest) {
        UtilizatorAcOe utilizatorAcOe = new UtilizatorAcOe();
        if(formRequest.getParamMap().get("id_tip_utilizator")!=null) {
            utilizatorAcOe.setId_tip_utilizator(new Integer(formRequest.getParamMap().get("id_tip_utilizator")));
        }else{
            utilizatorAcOe.setId_tip_utilizator(null);
        }
        utilizatorAcOe.setCod_cui(formRequest.getParamMap().get("cui_ac"));
        utilizatorAcOe.setNume_tert_master(formRequest.getParamMap().get("nume_tert_master_ac"));
        utilizatorAcOe.setNr_inmatriculare_tert_master(formRequest.getParamMap().get("nr_inmatriculare_tert_master_ac"));
        utilizatorAcOe.setEmail_tert_master(formRequest.getParamMap().get("email_tert_master_ac"));

        if(formRequest.getParamMap().get("id_tip_ordonator_credite")!=null) {
            utilizatorAcOe.setId_tip_ordonator_credite(new Integer(formRequest.getParamMap().get("id_tip_ordonator_credite")));
        }else{
            utilizatorAcOe.setId_tip_ordonator_credite(null);
        }
        utilizatorAcOe.setWebsite(formRequest.getParamMap().get("website"));
        utilizatorAcOe.setTl_sediu(formRequest.getParamMap().get("tl_sediu"));
        utilizatorAcOe.setFax(formRequest.getParamMap().get("fax"));
        utilizatorAcOe.setNume_rp(formRequest.getParamMap().get("nume_rp"));
        utilizatorAcOe.setPrenume_rp(formRequest.getParamMap().get("prenume_rp"));
        utilizatorAcOe.setFunctie_rp(formRequest.getParamMap().get("functie_rp"));
        utilizatorAcOe.setEmail_rp(formRequest.getParamMap().get("email_rp"));
        utilizatorAcOe.setNume_c1(formRequest.getParamMap().get("nume_c1"));
        utilizatorAcOe.setPrenume_c1(formRequest.getParamMap().get("prenume_c1"));
        utilizatorAcOe.setEmail_c1(formRequest.getParamMap().get("email_c1"));
        utilizatorAcOe.setTelefon_c1(formRequest.getParamMap().get("telefon_c1"));
        utilizatorAcOe.setNume_c2(formRequest.getParamMap().get("nume_c2"));
        utilizatorAcOe.setPrenume_c2(formRequest.getParamMap().get("prenume_c2"));
        utilizatorAcOe.setEmail_c2(formRequest.getParamMap().get("email_c2"));
        utilizatorAcOe.setTelefon_c2(formRequest.getParamMap().get("telefon_c2"));
        if(formRequest.getParamMap().get("id_judet")!=null) {
            utilizatorAcOe.setId_judet(new Integer(formRequest.getParamMap().get("id_judet")));
        }else{
            utilizatorAcOe.setId_judet(null);
        }
        if(formRequest.getParamMap().get("id_localitate")!=null) {
            utilizatorAcOe.setId_localitate(new Integer(formRequest.getParamMap().get("id_localitate")));
        }else{
            utilizatorAcOe.setId_localitate(null);
        }
        utilizatorAcOe.setStrada(formRequest.getParamMap().get("strada"));
        utilizatorAcOe.setNr_strada(formRequest.getParamMap().get("nr_strada"));
        utilizatorAcOe.setBloc(formRequest.getParamMap().get("bloc"));
        utilizatorAcOe.setScara(formRequest.getParamMap().get("scara"));
        utilizatorAcOe.setApartament(formRequest.getParamMap().get("apartament"));
        utilizatorAcOe.setEtaj(formRequest.getParamMap().get("etaj"));
        utilizatorAcOe.setCod(formRequest.getParamMap().get("cod"));
        utilizatorAcOe.setParola(formRequest.getParamMap().get("pwd1"));
        utilizatorAcOe.setTos_pers_jur(formRequest.getParamMap().get("id_entitate_pers_jur"));
        utilizatorAcOe.setUtilizator_ac_oe("AC");






        return utilizatorAcOe;
    }

    UtilizatorAcOe getUtilizatorOe(FileUploadParamRequest formRequest) {
        UtilizatorAcOe utilizatorAcOe = new UtilizatorAcOe();

        utilizatorAcOe.setCod_cui(formRequest.getParamMap().get("cui"));
        utilizatorAcOe.setNume_tert_master(formRequest.getParamMap().get("nume_tert_master"));
        utilizatorAcOe.setNr_inmatriculare_tert_master(formRequest.getParamMap().get("nr_inmatriculare_tert_master"));
        utilizatorAcOe.setTl_sediu(formRequest.getParamMap().get("telefon_tert_master"));
        utilizatorAcOe.setEmail_tert_master(formRequest.getParamMap().get("email_tert_master"));
        utilizatorAcOe.setNume_rp(formRequest.getParamMap().get("nume_rp_oe"));
        utilizatorAcOe.setPrenume_rp(formRequest.getParamMap().get("prenume_rp_oe"));
        utilizatorAcOe.setFunctie_rp(formRequest.getParamMap().get("functie_rp_oe"));
        utilizatorAcOe.setEmail_rp(formRequest.getParamMap().get("email_rp_oe"));
        utilizatorAcOe.setNume_c1(formRequest.getParamMap().get("nume_c1_oe"));
        utilizatorAcOe.setPrenume_c1(formRequest.getParamMap().get("prenume_c1_oe"));
        utilizatorAcOe.setEmail_c1(formRequest.getParamMap().get("email_c1_oe"));
        utilizatorAcOe.setTelefon_c1(formRequest.getParamMap().get("telefon_c1_oe"));
        utilizatorAcOe.setNume_c2(formRequest.getParamMap().get("nume_c2_oe"));
        utilizatorAcOe.setPrenume_c2(formRequest.getParamMap().get("prenume_c2_oe"));
        utilizatorAcOe.setEmail_c2(formRequest.getParamMap().get("email_c2_oe"));
        utilizatorAcOe.setTelefon_c2(formRequest.getParamMap().get("telefon_c2_oe"));
        if(formRequest.getParamMap().get("id_judet_oe")!=null) {
            utilizatorAcOe.setId_judet(new Integer(formRequest.getParamMap().get("id_judet_oe")));
        }else{
            utilizatorAcOe.setId_judet(null);
        }

        if(formRequest.getParamMap().get("id_localitate_oe")!=null) {
            utilizatorAcOe.setId_localitate(new Integer(formRequest.getParamMap().get("id_localitate_oe")));
        }else{
            utilizatorAcOe.setId_localitate(null);
        }
        utilizatorAcOe.setStrada(formRequest.getParamMap().get("strada_oe"));
        utilizatorAcOe.setNr_strada(formRequest.getParamMap().get("nr_strada_oe"));
        utilizatorAcOe.setBloc(formRequest.getParamMap().get("bloc_oe"));
        utilizatorAcOe.setScara(formRequest.getParamMap().get("scara_oe"));
        utilizatorAcOe.setApartament(formRequest.getParamMap().get("apartament_oe"));
        utilizatorAcOe.setEtaj(formRequest.getParamMap().get("etaj_oe"));
        utilizatorAcOe.setCod(formRequest.getParamMap().get("cod_oe"));
        utilizatorAcOe.setParola(formRequest.getParamMap().get("pwd1_oe"));
        utilizatorAcOe.setUtilizator_ac_oe("OE");

        utilizatorAcOe.setNume(formRequest.getParamMap().get("nume_c"));
        utilizatorAcOe.setPrenume(formRequest.getParamMap().get("prenume_c"));
        utilizatorAcOe.setEmail(formRequest.getParamMap().get("email_c"));
        utilizatorAcOe.setTelefon(formRequest.getParamMap().get("telefon_c"));

        return utilizatorAcOe;
    }

    //vaadin use apache commons-fileuploads and has no support for spring servlet  MultipartFile
    @PostMapping(value = "/dmsws/cerericont/addOe", consumes = "multipart/form-data", produces = "text/html")
    public ResponseEntity<String> addOperatorEconomic(HttpServletRequest httpServletRequest) {
        Integer idCerere=null;
        try {
            FileUploadParamRequest requestForm = getRequestForm(httpServletRequest);
            try{
                validateCode(requestForm.getParamMap().get("checker"));
            }catch (IllegalAccessError e) {

            }
            UtilizatorAcOe utilizatorAcOe = getUtilizatorOe(requestForm);

            if(requestForm.uploadedFiles.size() == 1 && requestForm.getUploadedFiles().get(0).getFileData().length!=0) {
                UploadFileDescription mandatFile = requestForm.getUploadedFiles().get(0);
                Optional<String> extension = getExtensionByStringHandling(mandatFile.getFileName());
                boolean allowedExtension = false;
                if (extension.isPresent() && !extension.get().isEmpty()){
                    allowedExtension = Arrays.stream(ALLOWED_EXTENSIONS).anyMatch(extension.get()::equals);

                    if (!allowedExtension)
                        throw new IllegalArgumentException("Extensia ." + extension.get() + " nu este permisa.");
                }

                byte[] requestFormPdf = Base64.getDecoder().decode(requestForm.paramMap.get("request_form_pdf"));
                String url="";
                try{
                    url= urlUtil.getPath(httpServletRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
                idCerere = serviceCereri.addUtilizatorAcOe(SecurityUtils.getToken(), utilizatorAcOe, mandatFile.getFileName(), mandatFile.getFileData(), requestFormPdf, url);
            }
            else{
                throw new ServerWebInputException("Incarcati mandat!");
            }
        } catch (IllegalAccessError e) {
            return ResponseEntity.badRequest().body("Cerere esuata. Repetati operatia!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HttpClientErrorException.BadRequest e) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(e.getResponseBodyAsString());
                String msg = json.getAsString("info");
                return ResponseEntity.badRequest().body(msg);
            } catch (Exception e2){
                return ResponseEntity.badRequest().body("Exista deja un utilizator inregistrat cu aceasta informatie!");
            }
        } catch (HttpClientErrorException.NotAcceptable e) {
            return ResponseEntity.badRequest().body("Parola nu este destul de complexă.");
        } catch (ServerWebInputException e) {
            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
        }

        return ResponseEntity.ok(String.valueOf(idCerere));
    }


    //vaadin use apache commons-fileuploads and has no support for spring servlet  MultipartFile
    @PostMapping(value = "/dmsws/cerericont/addAc", consumes = "multipart/form-data", produces = "text/html")
    public ResponseEntity<String> addAutoritateContractanta(HttpServletRequest httpServletRequest) {
        Integer idCerere=null;
        try {
            FileUploadParamRequest requestForm = getRequestForm(httpServletRequest);
            try{
                validateCode(requestForm.getParamMap().get("checker"));
            }catch (IllegalAccessError e) {

            }
            UtilizatorAcOe utilizatorAcOe = getUtilizatorAc(requestForm);

            if(requestForm.uploadedFiles.size() == 1 && requestForm.getUploadedFiles().get(0).getFileData().length!=0) {
               UploadFileDescription mandatFile = requestForm.getUploadedFiles().get(0);
                Optional<String> extension = getExtensionByStringHandling(mandatFile.getFileName());
                boolean allowedExtension = false;
                if (extension.isPresent() && !extension.get().isEmpty()){
                    allowedExtension = Arrays.stream(ALLOWED_EXTENSIONS).anyMatch(extension.get()::equals);

                    if (!allowedExtension)
                        throw new IllegalArgumentException("Extensia ." + extension.get() + " nu este permisa.");
                }

                byte[] requestFormPdf = Base64.getDecoder().decode(requestForm.paramMap.get("request_form_pdf"));
                String url="";
                try{
                    url= urlUtil.getPath(httpServletRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
               idCerere = serviceCereri.addUtilizatorAcOe(SecurityUtils.getToken(), utilizatorAcOe, mandatFile.getFileName(), mandatFile.getFileData(), requestFormPdf, url);
            }
            else{
                throw new ServerWebInputException("Incarcati mandat!");
            }
        } catch (IllegalAccessError e) {
            return ResponseEntity.badRequest().body("Cerere esuata. Repetati operatia!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HttpClientErrorException.BadRequest e) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(e.getResponseBodyAsString());
                String msg = json.getAsString("info");
                return ResponseEntity.badRequest().body(msg);
            } catch (Exception e2){
                return ResponseEntity.badRequest().body("Exista deja un utilizator inregistrat cu aceasta informatie!");
            }
        } catch (HttpClientErrorException.NotAcceptable e) {
            return ResponseEntity.badRequest().body("Parola nu este destul de complexă.");
        } catch (ServerWebInputException e) {
            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
        }

        return ResponseEntity.ok(String.valueOf(idCerere));
    }

    //vaadin use apache commons-fileuploads and has no support for spring servlet  MultipartFile
    @PostMapping(value = "/dmsws/cerericont/addCt", consumes = "multipart/form-data", produces = "text/html")
    public ResponseEntity<String> addContact(HttpServletRequest httpServletRequest) {
        Integer raspuns=null;
        try {
            FileUploadParamRequest requestForm = getRequestForm(httpServletRequest);
            UtilizatorAcOe utilizatorContact = getUtilizatorOe(requestForm);
            if(requestForm.uploadedFiles.size() == 1 && requestForm.getUploadedFiles().get(0).getFileData().length!=0) {
                UploadFileDescription mandatFile = requestForm.getUploadedFiles().get(0);
                Optional<String> extension = getExtensionByStringHandling(mandatFile.getFileName());
                boolean allowedExtension = false;
                if (extension.isPresent() && !extension.get().isEmpty()){
                    allowedExtension = Arrays.stream(ALLOWED_EXTENSIONS).anyMatch(extension.get()::equals);

                    if (!allowedExtension)
                        throw new IllegalArgumentException("Extensia ." + extension.get() + " nu este permisa.");
                }

              raspuns =  serviceCereri.addContact(SecurityUtils.getToken(), utilizatorContact, mandatFile.getFileName(), mandatFile.getFileData());
            }
            else{
                throw new ServerWebInputException("Incarcati mandat!");
            }
        } catch (IllegalAccessError e) {
            return ResponseEntity.badRequest().body("Cerere esuata. Repetati operatia!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HttpClientErrorException.BadRequest e) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(e.getResponseBodyAsString());
                String msg = json.getAsString("info");
                return ResponseEntity.badRequest().body(msg);
            } catch (Exception e2){
                return ResponseEntity.badRequest().body("Exista deja un utilizator inregistrat cu aceasta informatie!");
            }
        } catch (HttpClientErrorException.NotAcceptable e) {
            return ResponseEntity.badRequest().body("Parola nu este destul de complexă.");
        } catch (ServerWebInputException e) {
            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
        }

        if(raspuns == -1){
            return ResponseEntity.ok("NRER");
        }else{
            return ResponseEntity.ok("OK");
        }
    }






    @GetMapping("/dmsws/cerericont/getListaRelatii/{idCerere}")
    public RelatiiTertList getListaRelatii(@PathVariable String idCerere) {

            return serviceCereri.getListaRelatii(SecurityUtils.getToken(),idCerere);
    }

    @PostMapping("/dmsws/cerericont/adaugaRelatie/")
    public BaseModel adaugaRelatie(  @RequestBody RelatiiTert relatie) {

            return serviceCereri.adaugaRelatie(SecurityUtils.getToken(), relatie);


    }
    @GetMapping("/dmsws/cerericont/stergeRelatie/{id}")
    public BaseModel stergeRelatie(@PathVariable String id) {


        return serviceCereri.stergeRelatie(SecurityUtils.getToken(),id);


    }
    @GetMapping("/dmsws/cerericont/getRelatieInfo/{idRelatie}")
    public RelatiiTertList getRelatieInfo(@PathVariable String idRelatie) {

        return serviceCereri.getRelatieInfo(SecurityUtils.getToken(),idRelatie);
    }
    @PostMapping("/dmsws/cerericont/updateInfoRelatie")
    public BaseModel updateInfoRelatie(  @RequestBody RelatiiTert relatie) {


        return serviceCereri.updateInfoRelatie(SecurityUtils.getToken(), relatie);

    }
    @PostMapping("/dmsws/cerericont/trimSolicitare/")
    public BaseModel trimSolicitare(  @RequestBody RelatiiTert relatiiTert) {
            return serviceCereri.trimSolicitare(SecurityUtils.getToken(), relatiiTert);

    }
}
