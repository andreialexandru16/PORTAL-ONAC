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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.AddUtilizatorSecurityService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.CereriContService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsNomenclatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.*;
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

    @GetMapping("/dmsws/cerericont/getListOperatorTipCredite")
    public TipOrdonatorList getListOperatorTipCredite() {
        return serviceCereri.getListOperatorTipCredite(anonymousToken);
    }

    @GetMapping("/dmsws/cerericont/getListDenumireCif")
    public InstiutiiList getListDenumireCif() {
        return serviceCereri.getListDenumireCif(anonymousToken);
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
        if(formRequest.getParamMap().get("id_institutie_solicitanta")!=null) {
            utilizatorAcOe.setId_institutie_solicitanta(new Integer(formRequest.getParamMap().get("id_institutie_solicitanta")));
        }else{
            utilizatorAcOe.setId_institutie_solicitanta(null);
        }
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

    //vaadin use apache commons-fileuploads and has no support for spring servlet  MultipartFile
    @PostMapping(value = "/dmsws/cerericont/addAc", consumes = "multipart/form-data", produces = "text/html")
    public ResponseEntity<String> addPersoanaFizica(HttpServletRequest httpServletRequest) {
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





//
//
//
////    @PostMapping(value = "/dmsws/utilizator/addChecker", produces = "text/html")
////    public String addChecker(HttpServletRequest request){
////    	return checkerService.getCode(request+"");
////    }
////

//
////    //vaadin use apache commons-fileuploads and has no support for spring servlet  MultipartFile
////    @PostMapping(value = "/dmsws/utilizator/addPf", consumes = "multipart/form-data", produces = "text/html")
////    public ResponseEntity<String> addPersoanaFizica(HttpServletRequest httpServletRequest) {
////        try {
////            FileUploadParamRequest requestForm = getRequestForm(httpServletRequest);
////            try{
////                validateCode(requestForm.getParamMap().get("checker"));
////            }catch (IllegalAccessError e) {
////
////            }
////            PersoanaFizicaJuridica persoanaFizicaJuridica = getPersoanaFizicaFields(requestForm);
////
////            if(requestForm.uploadedFiles.size() == 1 && requestForm.getUploadedFiles().get(0).getFileData().length!=0) {
////                UploadFileDescription ciFile = requestForm.getUploadedFiles().get(0);
////                Optional<String> extension = getExtensionByStringHandling(ciFile.getFileName());
////                boolean allowedExtension = false;
////                if (extension.isPresent() && !extension.get().isEmpty()){
////                    allowedExtension = Arrays.stream(ALLOWED_EXTENSIONS).anyMatch(extension.get()::equals);
////
////                    if (!allowedExtension)
////                        throw new IllegalArgumentException("Extensia ." + extension.get() + " nu este permisa.");
////                }
////
////                byte[] requestFormPdf = Base64.getDecoder().decode(requestForm.paramMap.get("request_form_pdf"));
////                String url="";
////                try{
////                    url= urlUtil.getPath(httpServletRequest);
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////                service.addPersoanaFizicaJuridica(SecurityUtils.getToken(), persoanaFizicaJuridica,
////                        ciFile.getFileName(), ciFile.getFileData(),
////                        null, null,
////                        null, null, requestFormPdf, url);
////            }
////            else{
////                byte[] requestFormPdf = Base64.getDecoder().decode(requestForm.paramMap.get("request_form_pdf"));
////                String url="";
////                try{
////                    url= urlUtil.getPath(httpServletRequest);
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////                service.addPersoanaFizicaJuridica(SecurityUtils.getToken(), persoanaFizicaJuridica,
////                        null, null,
////                        null, null,
////                        null, null, requestFormPdf, url);
////            }
////        } catch (IllegalAccessError e) {
////        	return ResponseEntity.badRequest().body("Cerere esuata. Repetati operatia!");
////        } catch (IllegalArgumentException e) {
////        	return ResponseEntity.badRequest().body(e.getMessage());
////        } catch (BadRequest e) {
////            try {
////                JSONParser parser = new JSONParser();
////                JSONObject json = (JSONObject) parser.parse(e.getResponseBodyAsString());
////                String msg = json.getAsString("info");
////                return ResponseEntity.badRequest().body(msg);
////            } catch (Exception e2){
////                return ResponseEntity.badRequest().body("Exista deja un utilizator inregistrat cu aceasta informatie!");
////            }
////        } catch (HttpClientErrorException.NotAcceptable e) {
////            return ResponseEntity.badRequest().body("Parola nu este destul de complexă.");
////        } catch (ServerWebInputException e) {
////            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
////        } catch (Exception e) {
////        	return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
////        }
////
////        return ResponseEntity.ok("OK");
////    }
////
////    public Optional<String> getExtensionByStringHandling(String filename) {
////        return Optional.ofNullable(filename)
////                .filter(f -> f.contains("."))
////                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
////    }
//
////    PersoanaFizicaJuridica getPersoanaFizicaFields(FileUploadParamRequest formRequest) {
////        PersoanaFizicaJuridica persoanaFizicaJuridica = new PersoanaFizicaJuridica();
////        persoanaFizicaJuridica.setCnp(formRequest.getParamMap().get("cnp"));
////        persoanaFizicaJuridica.setNume(formRequest.getParamMap().get("nume"));
////        persoanaFizicaJuridica.setPrenume(formRequest.getParamMap().get("prenume"));
////        if(formRequest.getParamMap().get("tip-act-pf")!=null) {
////            persoanaFizicaJuridica.setIdTipAct(new Integer(formRequest.getParamMap().get("tip-act-pf")));
////        }
////        persoanaFizicaJuridica.setSerieAct(formRequest.getParamMap().get("serie"));
////        persoanaFizicaJuridica.setNrAct(formRequest.getParamMap().get("numar"));
////        if(formRequest.getParamMap().get("country")!=null) {
////            persoanaFizicaJuridica.setIdTara(new Integer(formRequest.getParamMap().get("country")));
////        }
////        try {
////            persoanaFizicaJuridica.setIdJudet(new Integer(formRequest.getParamMap().get("region")));
////        } catch (Throwable e) {
////            persoanaFizicaJuridica.setIdJudet(1);
////        }
////        try {
////            persoanaFizicaJuridica.setIdLocalitate(new Integer(formRequest.getParamMap().get("city")));
////        } catch (Throwable e) {
////            persoanaFizicaJuridica.setIdLocalitate(1);
////        }
////        persoanaFizicaJuridica.setStrada(formRequest.getParamMap().get("street"));
////        persoanaFizicaJuridica.setNrStrada(formRequest.getParamMap().get("nr_street"));
////        persoanaFizicaJuridica.setBloc(formRequest.getParamMap().get("bloc"));
////        persoanaFizicaJuridica.setScara(formRequest.getParamMap().get("scara"));
////        persoanaFizicaJuridica.setEtaj(formRequest.getParamMap().get("etaj"));
////        persoanaFizicaJuridica.setApartament(formRequest.getParamMap().get("apartament"));
////
////        String prefixTel = formRequest.getParamMap().get("prefixtel");
////        if (prefixTel == null || prefixTel.trim().isEmpty() || prefixTel.trim().toLowerCase().equals("null")){
////            prefixTel = "";
////        }
////
////        persoanaFizicaJuridica.setTelefon(prefixTel+formRequest.getParamMap().get("tel"));
////        persoanaFizicaJuridica.setEmail(formRequest.getParamMap().get("email"));
////        persoanaFizicaJuridica.setParola(formRequest.getParamMap().get("pwd1"));
////        persoanaFizicaJuridica.setEstePersoanaFizica("1");
////        persoanaFizicaJuridica.setTipFormaOrganizare(1);
////
////        return persoanaFizicaJuridica;
////    }
//
//
//    public static class FileUploadParamRequest {
//        final Map<String, String> paramMap = new HashMap<>();
//        final List<UploadFileDescription> uploadedFiles = new ArrayList<>();
//
//
//        public void putParam(String name, String value) {
//            paramMap.put(name, value);
//        }
//        public void addUploadedFile(UploadFileDescription file) {
//            uploadedFiles.add(file);
//        }
//
//        public Map<String, String> getParamMap() {
//            return paramMap;
//        }
//
//        public List<UploadFileDescription> getUploadedFiles() {
//            return uploadedFiles;
//        }
//    }
//
//    public static class UploadFileDescription {
//        final String fileName;
//
//        final byte[] fileData;
//
//        public UploadFileDescription(String fileName, byte[] fileData) {
//            this.fileName = fileName;
//            this.fileData = fileData;
//        }
//
//        public String getFileName() {
//            return fileName;
//        }
//
//        public byte[] getFileData() {
//            return fileData;
//        }
//    }
//
//    private FileUploadParamRequest getRequestForm(HttpServletRequest httpServletRequest) {
//        boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);
//
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        factory.setRepository(
//                new File(System.getProperty("java.io.tmpdir")));
//        factory.setSizeThreshold(
//                DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
//        factory.setFileCleaningTracker(null);
//
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        FileUploadParamRequest requestForm = new FileUploadParamRequest();
//
//        if(isMultipart) {
//            try {
//                List items = upload.parseRequest(httpServletRequest);
//                Iterator iter = items.iterator();
//                while (iter.hasNext()) {
//                    FileItem item = (FileItem) iter.next();
//
//                    if (!item.isFormField()) {
//                                InputStream uploadedStream = item.getInputStream();
//                                requestForm.addUploadedFile(new UploadFileDescription(item.getName(), IOUtils.toByteArray(uploadedStream)));
//                    } else  {
//                        ((DiskFileItem) item).setDefaultCharset("UTF-8");
//                        requestForm.putParam(item.getFieldName(), item.getString());
//                    }
//                }
//            } catch (FileUploadException e) {
//            	e.printStackTrace();
//
//            } catch (IOException e) {
//            	e.printStackTrace();
//            }
//        }
//        return requestForm;
//    }
//
//    @PostMapping(value = "/dmsws/utilizator/addPj", consumes = "multipart/form-data", produces = "text/html")
//    public ResponseEntity<String> addPersoanaJuridica(HttpServletRequest httpServletRequest) {
//        try {
//
//            FileUploadParamRequest requestForm = getRequestForm(httpServletRequest);
//            validateCode(requestForm.getParamMap().get("checker-pfa"));
//            PersoanaFizicaJuridica persoanaFizicaJuridica = getPersoanaJuridica(requestForm);
//
//            if(requestForm.uploadedFiles.size() == 3 ) {
//                String url="";
//                try{
//                    url= urlUtil.getPath(httpServletRequest);
//                }catch (Exception e){
//                	e.printStackTrace();
//                }
//                UploadFileDescription biFile = requestForm.getUploadedFiles().get(0);
//                Optional<String> extension = getExtensionByStringHandling(biFile.getFileName());
//                boolean allowedExtension = false;
//                if (extension.isPresent() && !extension.get().isEmpty()){
//                    allowedExtension = Arrays.stream(ALLOWED_EXTENSIONS).anyMatch(extension.get()::equals);
//
//                    if (!allowedExtension)
//                        throw new IllegalArgumentException("Extensia ." + extension.get() + " nu este permisa.");
//                }
//
//                UploadFileDescription cuiFile = requestForm.getUploadedFiles().get(1);
//                Optional<String> extensionCui = getExtensionByStringHandling(cuiFile.getFileName());
//                boolean allowedExtensionCui = false;
//                if (extensionCui.isPresent() && !extensionCui.get().isEmpty()){
//                    allowedExtensionCui = Arrays.stream(ALLOWED_EXTENSIONS).anyMatch(extensionCui.get()::equals);
//
//                    if (!allowedExtensionCui)
//                        throw new IllegalArgumentException("Extensia ." + extensionCui.get() + " nu este permisa.");
//                }
//
//                UploadFileDescription imputernicireFile = requestForm.getUploadedFiles().get(2);
//
//                Optional<String> extensionImputernicire = getExtensionByStringHandling(imputernicireFile.getFileName());
//                boolean allowedExtensionImputernicire = false;
//                if (extensionImputernicire.isPresent() && !extensionImputernicire.get().isEmpty()){
//                    allowedExtensionImputernicire = Arrays.stream(ALLOWED_EXTENSIONS).anyMatch(extensionImputernicire.get()::equals);
//
//                    if (!allowedExtensionImputernicire)
//                        throw new IllegalArgumentException("Extensia ." + extensionImputernicire.get() + " nu este permisa.");
//                }
//                byte[] requestFormPdf = Base64.getDecoder().decode(requestForm.paramMap.get("request_form_pdf"));
//
//                //TODO coment this is a test
//                OutputStream out = new FileOutputStream("formular_insriere_cont_pj.pdf");
//
//                out.write(requestFormPdf);
//                out.close();
//                // todo <-
//
//
//                service.addPersoanaFizicaJuridica(SecurityUtils.getToken(), persoanaFizicaJuridica,
//                        biFile.getFileName(), biFile.getFileData(),
//                        cuiFile.getFileName(), cuiFile.getFileData(),
//                        imputernicireFile.getFileName(), imputernicireFile.getFileData(), requestFormPdf, url);
//            }
//        } catch (IllegalAccessError e) {
//        	return ResponseEntity.badRequest().body("Cerere esuata. Repetati operatia!");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (BadRequest e) {
//            return ResponseEntity.badRequest().body("Exista deja un utilizator inregistrat cu aceasta informatie!");
//        } catch (HttpClientErrorException.NotAcceptable e) {
//            return ResponseEntity.badRequest().body("Parola nu este destul de complexă.");
//        } catch (ServerWebInputException e) {
//            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
//        } catch (Exception e) {
//        	return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia !");
//        }
//
//        return ResponseEntity.ok("OK");
//    }
//
//    PersoanaFizicaJuridica getPersoanaJuridica(FileUploadParamRequest formRequest) {
//        PersoanaFizicaJuridica persoanaFizicaJuridica = new PersoanaFizicaJuridica();
//        persoanaFizicaJuridica.setNumeComplet(formRequest.getParamMap().get("company"));
//        persoanaFizicaJuridica.setCodCui(formRequest.getParamMap().get("cui"));
//        persoanaFizicaJuridica.setRj(formRequest.getParamMap().get("rj"));
//        persoanaFizicaJuridica.setCnp(formRequest.getParamMap().get("cnp-pfa"));
//        persoanaFizicaJuridica.setNume(formRequest.getParamMap().get("nume-pfa"));
//        persoanaFizicaJuridica.setPrenume(formRequest.getParamMap().get("prenume-pfa"));
//        if(formRequest.getParamMap().get("tip-act-pfa")!=null) {
//            persoanaFizicaJuridica.setIdTipAct(new Integer(formRequest.getParamMap().get("tip-act-pfa")));
//        }
//        persoanaFizicaJuridica.setSerieAct(formRequest.getParamMap().get("serie-pfa"));
//        persoanaFizicaJuridica.setNrAct(formRequest.getParamMap().get("numar-pfa"));
//        persoanaFizicaJuridica.setIdTara(new Integer(formRequest.getParamMap().get("country-pfa")));
//        try {
//            persoanaFizicaJuridica.setIdJudet(new Integer(formRequest.getParamMap().get("region-pfa")));
//        } catch (Throwable e) {
//            persoanaFizicaJuridica.setIdJudet(1);
//        }
//        try {
//            persoanaFizicaJuridica.setIdLocalitate(new Integer(formRequest.getParamMap().get("city-pfa")));
//        } catch (Throwable e) {
//            persoanaFizicaJuridica.setIdLocalitate(1);
//        }
//        String prefixTel = formRequest.getParamMap().get("prefixtel1");
//        if (prefixTel == null || prefixTel.trim().isEmpty() || prefixTel.trim().toLowerCase().equals("null")){
//            prefixTel = "";
//        }
//        persoanaFizicaJuridica.setStrada(formRequest.getParamMap().get("street-pfa"));
//        persoanaFizicaJuridica.setNrStrada(formRequest.getParamMap().get("nr_street-pfa"));
//        persoanaFizicaJuridica.setBloc(formRequest.getParamMap().get("bloc-pfa"));
//        persoanaFizicaJuridica.setScara(formRequest.getParamMap().get("scara-pfa"));
//        persoanaFizicaJuridica.setEtaj(formRequest.getParamMap().get("etaj-pfa"));
//        persoanaFizicaJuridica.setApartament(formRequest.getParamMap().get("apartament-pfa"));
//        persoanaFizicaJuridica.setTelefon(prefixTel+formRequest.getParamMap().get("tel-pfa"));
//        persoanaFizicaJuridica.setEmail(formRequest.getParamMap().get("email-pfa"));
//        persoanaFizicaJuridica.setParola(formRequest.getParamMap().get("pwd1-pfa"));
//        persoanaFizicaJuridica.setEstePersoanaFizica("0");
//        persoanaFizicaJuridica.setTipFormaOrganizare(2);
//        persoanaFizicaJuridica.setDomeniuEnergieElectrica(formRequest.getParamMap().get("domeniu_ee") != null && formRequest.getParamMap().get("domeniu_ee").equals("true"));
//        persoanaFizicaJuridica.setDomeniuGazeNaturale(formRequest.getParamMap().get("domeniu_gn") != null && formRequest.getParamMap().get("domeniu_gn").equals("true"));
//        persoanaFizicaJuridica.setDomeniuEnergieTermica(formRequest.getParamMap().get("domeniu_et") != null && formRequest.getParamMap().get("domeniu_et").equals("true"));
//
//        return persoanaFizicaJuridica;
//    }
//
//
//    @PostMapping(value = "/dmsws/utilizator/addIp", consumes = "multipart/form-data", produces = "text/html")
//    public ResponseEntity<String> addInstitutiePublica(HttpServletRequest httpServletRequest) {
//        try {
//
//            FileUploadParamRequest requestForm = getRequestForm(httpServletRequest);
//            validateCode(requestForm.getParamMap().get("checker-pfa1"));
//            PersoanaFizicaJuridica persoanaFizicaJuridica = getInstitutiePublica(requestForm);
//
//            if(requestForm.uploadedFiles.size() == 2 ) {
//                String url="";
//                try{
//                    url= urlUtil.getPath(httpServletRequest);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                UploadFileDescription biFile = requestForm.getUploadedFiles().get(0);
//                UploadFileDescription cuiFile = requestForm.getUploadedFiles().get(1);
//                byte[] requestFormPdf = Base64.getDecoder().decode(requestForm.paramMap.get("request_form_pdf"));
//
//                //TODO coment this is a test
//                OutputStream out = new FileOutputStream("formular_insriere_cont_pj.pdf");
//
//                out.write(requestFormPdf);
//                out.close();
//                // todo <-
//
//
//                service.addPersoanaFizicaJuridica(SecurityUtils.getToken(), persoanaFizicaJuridica,
//                        biFile.getFileName(), biFile.getFileData(),
//                        cuiFile.getFileName(), cuiFile.getFileData(),
//                        null, null, requestFormPdf, url);
//            }
//        } catch (IllegalAccessError e) {
//            return ResponseEntity.badRequest().body("Cerere esuata. Repetati operatia!");
//        } catch (BadRequest e) {
//            return ResponseEntity.badRequest().body("Exista deja un utilizator inregistrat cu aceasta informatie!");
//        } catch (HttpClientErrorException.NotAcceptable e) {
//            return ResponseEntity.badRequest().body("Parola nu este destul de complexă.");
//        } catch (ServerWebInputException e) {
//            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
//        }
//        return ResponseEntity.ok("OK");
//    }
//
//    @PostMapping(value = "/dmsws/utilizator/addSub", consumes = "multipart/form-data", produces = "text/html")
//    public ResponseEntity<String> addSubordonata(HttpServletRequest httpServletRequest) {
//        try {
//
//            FileUploadParamRequest requestForm = getRequestForm(httpServletRequest);
//            validateCode(requestForm.getParamMap().get("checker-pfa2"));
//            PersoanaFizicaJuridica persoanaFizicaJuridica = getSubordonata(requestForm);
//
//            if(requestForm.uploadedFiles.size() == 2 ) {
//                String url="";
//                try{
//                    url= urlUtil.getPath(httpServletRequest);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                UploadFileDescription biFile = requestForm.getUploadedFiles().get(0);
//                UploadFileDescription cuiFile = requestForm.getUploadedFiles().get(1);
//                byte[] requestFormPdf = Base64.getDecoder().decode(requestForm.paramMap.get("request_form_pdf"));
//
//                //TODO coment this is a test
//                OutputStream out = new FileOutputStream("formular_insriere_cont_pj.pdf");
//
//                out.write(requestFormPdf);
//                out.close();
//                // todo <-
//
//
//                service.addPersoanaFizicaJuridica(SecurityUtils.getToken(), persoanaFizicaJuridica,
//                        biFile.getFileName(), biFile.getFileData(),
//                        cuiFile.getFileName(), cuiFile.getFileData(),
//                        null, null, requestFormPdf, url);
//            }
//        } catch (IllegalAccessError e) {
//            return ResponseEntity.badRequest().body("Cerere esuata. Repetati operatia!");
//        } catch (BadRequest e) {
//            return ResponseEntity.badRequest().body("Exista deja un utilizator inregistrat cu aceasta informatie!");
//        } catch (HttpClientErrorException.NotAcceptable e) {
//            return ResponseEntity.badRequest().body("Parola nu este destul de complexă.");
//        } catch (ServerWebInputException e) {
//            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Eroare server DMSWS. Repetati operatia!");
//        }
//
//        return ResponseEntity.ok("OK");
//    }
//    PersoanaFizicaJuridica getInstitutiePublica(FileUploadParamRequest formRequest) {
//        PersoanaFizicaJuridica persoanaFizicaJuridica = new PersoanaFizicaJuridica();
//        persoanaFizicaJuridica.setNumeComplet(formRequest.getParamMap().get("company1"));
//        persoanaFizicaJuridica.setCodCui(formRequest.getParamMap().get("cui1"));
//        persoanaFizicaJuridica.setCnp(formRequest.getParamMap().get("cnp-pfa1"));
//        persoanaFizicaJuridica.setNume(formRequest.getParamMap().get("nume-pfa1"));
//        persoanaFizicaJuridica.setPrenume(formRequest.getParamMap().get("prenume-pfa1"));
//        persoanaFizicaJuridica.setIdTipAct(new Integer(formRequest.getParamMap().get("tip-act-pfa1")));
//        persoanaFizicaJuridica.setSerieAct(formRequest.getParamMap().get("serie-pfa1"));
//        persoanaFizicaJuridica.setNrAct(formRequest.getParamMap().get("numar-pfa1"));
//        persoanaFizicaJuridica.setIdTara(new Integer(formRequest.getParamMap().get("country-pfa1")));
//        try {
//            persoanaFizicaJuridica.setIdJudet(new Integer(formRequest.getParamMap().get("region-pfa1")));
//        } catch (Throwable e) {
//            persoanaFizicaJuridica.setIdJudet(1);
//        }
//        try {
//            persoanaFizicaJuridica.setIdLocalitate(new Integer(formRequest.getParamMap().get("city-pfa1")));
//        } catch (Throwable e) {
//            persoanaFizicaJuridica.setIdLocalitate(1);
//        }
//        persoanaFizicaJuridica.setStrada(formRequest.getParamMap().get("street-pfa1"));
//        persoanaFizicaJuridica.setNrStrada(formRequest.getParamMap().get("nr_street-pfa1"));
//        persoanaFizicaJuridica.setBloc(formRequest.getParamMap().get("bloc-pfa1"));
//        persoanaFizicaJuridica.setScara(formRequest.getParamMap().get("scara-pfa1"));
//        persoanaFizicaJuridica.setEtaj(formRequest.getParamMap().get("etaj-pfa1"));
//        persoanaFizicaJuridica.setApartament(formRequest.getParamMap().get("apartament-pfa1"));
//        persoanaFizicaJuridica.setTelefon(formRequest.getParamMap().get("prefixtel2")+formRequest.getParamMap().get("tel-pfa1"));
//        persoanaFizicaJuridica.setEmail(formRequest.getParamMap().get("email-pfa1"));
//        persoanaFizicaJuridica.setParola(formRequest.getParamMap().get("pwd1-pfa1"));
//        persoanaFizicaJuridica.setEstePersoanaFizica("0");
//        persoanaFizicaJuridica.setTipFormaOrganizare(3);
//        return persoanaFizicaJuridica;
//    }
//
//    PersoanaFizicaJuridica getSubordonata(FileUploadParamRequest formRequest) {
//        PersoanaFizicaJuridica persoanaFizicaJuridica = new PersoanaFizicaJuridica();
//        persoanaFizicaJuridica.setNumeComplet(formRequest.getParamMap().get("company2"));
//        persoanaFizicaJuridica.setCodCui(formRequest.getParamMap().get("cui2"));
//        persoanaFizicaJuridica.setCnp(formRequest.getParamMap().get("cnp-pfa2"));
//        persoanaFizicaJuridica.setNume(formRequest.getParamMap().get("nume-pfa2"));
//        persoanaFizicaJuridica.setPrenume(formRequest.getParamMap().get("prenume-pfa2"));
//        persoanaFizicaJuridica.setIdTipAct(new Integer(formRequest.getParamMap().get("tip-act-pfa2")));
//        persoanaFizicaJuridica.setSerieAct(formRequest.getParamMap().get("serie-pfa2"));
//        persoanaFizicaJuridica.setNrAct(formRequest.getParamMap().get("numar-pfa2"));
//        persoanaFizicaJuridica.setIdTara(new Integer(formRequest.getParamMap().get("country-pfa2")));
//        try {
//            persoanaFizicaJuridica.setIdJudet(new Integer(formRequest.getParamMap().get("region-pfa2")));
//        } catch (Throwable e) {
//            persoanaFizicaJuridica.setIdJudet(1);
//        }
//        try {
//            persoanaFizicaJuridica.setIdLocalitate(new Integer(formRequest.getParamMap().get("city-pfa2")));
//        } catch (Throwable e) {
//            persoanaFizicaJuridica.setIdLocalitate(1);
//        }
//        persoanaFizicaJuridica.setStrada(formRequest.getParamMap().get("street-pfa2"));
//        persoanaFizicaJuridica.setNrStrada(formRequest.getParamMap().get("nr_street-pfa2"));
//        persoanaFizicaJuridica.setBloc(formRequest.getParamMap().get("bloc-pfa2"));
//        persoanaFizicaJuridica.setScara(formRequest.getParamMap().get("scara-pfa2"));
//        persoanaFizicaJuridica.setEtaj(formRequest.getParamMap().get("etaj-pfa2"));
//        persoanaFizicaJuridica.setApartament(formRequest.getParamMap().get("apartament-pfa2"));
//        persoanaFizicaJuridica.setTelefon(formRequest.getParamMap().get("tel-pfa2"));
//        persoanaFizicaJuridica.setEmail(formRequest.getParamMap().get("email-pfa2"));
//        persoanaFizicaJuridica.setParola(formRequest.getParamMap().get("pwd1-pfa2"));
//        persoanaFizicaJuridica.setEstePersoanaFizica("0");
//        persoanaFizicaJuridica.setTipFormaOrganizare(4);
//        return persoanaFizicaJuridica;
//    }
//
//
//    @PostMapping(value = "/dmsws/utilizator/addPsihologExtension")
//    public BaseModel addPsihologExtension( @RequestBody Psiholog psiholog) {
//        return service.addPsihologExtension(SecurityUtils.getToken(),psiholog);
//    }

}
