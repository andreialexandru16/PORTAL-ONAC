package ro.bithat.dms.microservices.portal.ecitizen.project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.FileUtils;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocumentList;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsDocumentService;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.ProjectService;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.*;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
public class ProjectController {

    @Autowired
    private URLUtil urlUtil;

    public static final String ERROR_TEXT = "Error Status Code ";

    @Autowired
    private ProjectService service;

    @Autowired
    private DmswsParticipatoryBudgetingService budgetingService;

    @Autowired
    private DmswsDocumentService documentService;

    @Value("${poi.project.type.cc.id}")
    private String projectTypeCCId;

    @Value("${poi.project.type.bp.id}")
    private String projectTypeBPId;

    @GetMapping("/dmsws/project/getPerioadaDepunere")
    public BaseModel getPerioadaDepunere() {

        return service.getPerioadaDepunere(SecurityUtils.getToken());
    }

    @GetMapping("/dmsws/wbs_uat/projectsTaskTicketById/{id}")
    public TaskTicketList getTaskuriTicket(@PathVariable String id) {
        TaskTicketList dt =  service.getTaskuriTicket(SecurityUtils.getToken(),id);
        return dt;
    }

    @GetMapping("/dmsws/document/electronic_services")
    public DocumentList getAllElectronicServices() {
        return documentService.getListaTipuriDocumente(SecurityUtils.getToken(),Optional.of(1));
    }

    @GetMapping("/dmsws/project/projects")
    public ProjectsList getProiecte() {

        ProjectsList projectsList = service.getProiecte(SecurityUtils.getToken());
        projectsList.setExtendedInfo(budgetingService.checkIfHasRole(SecurityUtils.getToken(),"POI_ADMIN").getInfo().toString());
        return  projectsList;
    }
    @GetMapping("/dmsws/project/extern_projects/{fromBugetare}")
    public ProjectsList getProiecteExterne(@PathVariable String fromBugetare) {
        String idTipProiect= null;
        if(fromBugetare.equals("true")){
            idTipProiect=projectTypeBPId;
        }
        else{
            idTipProiect=projectTypeCCId;
        }
        ProjectsList projectsList = service.getProiecteExterne(SecurityUtils.getToken(), Optional.ofNullable(idTipProiect));

        projectsList.setExtendedInfo(budgetingService.checkIfHasRole(SecurityUtils.getToken(),"POI_ADMIN").getInfo().toString());
        projectsList.setExtendedInfo2(budgetingService.checkIfHasRole(SecurityUtils.getToken(),"CONSULTARE_CETATENI").getInfo().toString());
        return  projectsList;

    }
    @GetMapping("/dmsws/project/extern_projects_short/{fromBugetare}")
    public ProjectsList getProiecteExterneShort(@PathVariable String fromBugetare) {
        String idTipProiect= null;
        if(fromBugetare.equals("true")){
            idTipProiect=projectTypeBPId;
        }
        else{
            idTipProiect=projectTypeCCId;
        }
        ProjectsList projectsList = service.getProiecteExterneShort(SecurityUtils.getToken(), Optional.ofNullable(idTipProiect));

        projectsList.setExtendedInfo(budgetingService.checkIfHasRole(SecurityUtils.getToken(),"POI_ADMIN").getInfo().toString());
        projectsList.setExtendedInfo2(budgetingService.checkIfHasRole(SecurityUtils.getToken(),"CONSULTARE_CETATENI").getInfo().toString());
        return  projectsList;

    }
    @GetMapping("/dmsws/project/extern_projects_short_pag_count/{fromBugetare}")
    public BaseModel getProiecteExterneShortPaginatCount(@PathVariable String fromBugetare) {
        String idTipProiect= null;
        if(fromBugetare.equals("true")){
            idTipProiect=projectTypeBPId;
        }
        else{
            idTipProiect=projectTypeCCId;
        }
        BaseModel projCount = service.getProiecteExterneShortPaginatCount(SecurityUtils.getToken(), Optional.ofNullable(idTipProiect));

        return  projCount;

    }
    @GetMapping("/dmsws/project/extern_projects_short_pag/{fromBugetare}")
    public ProjectsList getInregistrariList(
            @PathVariable String fromBugetare,
            @RequestParam(value = "indexStart", required = false) Integer indexStart,
            @RequestParam(value = "indexEnd", required = false) Integer indexEnd
    ) {
        String idTipProiect= null;
        if(fromBugetare.equals("true")){
            idTipProiect=projectTypeBPId;
        }
        else{
            idTipProiect=projectTypeCCId;
        }
        ProjectsList projectsList = service.getProiecteExterneShortPaginat(SecurityUtils.getToken(), Optional.ofNullable(idTipProiect), Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd));

        projectsList.setExtendedInfo(budgetingService.checkIfHasRole(SecurityUtils.getToken(),"POI_ADMIN").getInfo().toString());
        projectsList.setExtendedInfo2(budgetingService.checkIfHasRole(SecurityUtils.getToken(),"CONSULTARE_CETATENI").getInfo().toString());

        return  projectsList;
    }
    @GetMapping("/dmsws/project/check_role/{role_code}")
    public BaseModel checkRole(@PathVariable String role_code) {
        return  budgetingService.checkIfHasRole(SecurityUtils.getToken(),role_code);

    }
    @GetMapping("/dmsws/project/projectsLimited/{nrRows}")
    public ProjectsList getProiecteLimited(@PathVariable Integer nrRows) {
        return service.getProiecteLimited(SecurityUtils.getToken(),nrRows);
    }

    @GetMapping("/dmsws/project/team/{projectId}")
    public MembriiEchipaList getProjectTeam(@PathVariable String projectId) {
        return service.getProjectTeam(SecurityUtils.getToken(), projectId);
    }

    @GetMapping("/dmsws/project/getInfoProiect/{projectId}")
    public ProjectInfo getInfoProiect(@PathVariable String projectId) {
        return service.getInfoProiect(SecurityUtils.getToken(), projectId);
    }

    @GetMapping("/dmsws/project/getWbs/{projectId}")
    public WbsResponse2 getWbs(@PathVariable String projectId) {
        WbsResponse2 resp= service.getWbs2(SecurityUtils.getToken(), projectId);
        return resp;
    }
    @PostMapping("/dmsws/project/addFullWbs/{projectId}")
    public WbsUpdateResponse addWbs(@PathVariable String projectId, @RequestBody Wbs2 wbs) {

        return service.addWbs(SecurityUtils.getToken(), projectId, wbs);
    }

    @PostMapping("/dmsws/project/addWbs/{projectId}/{denumire}/{pozitie}")
    public WbsUpdateResponse addWbs(@PathVariable String projectId,@PathVariable String denumire,@PathVariable Integer pozitie) {
        Wbs2 wbs= new Wbs2();
        wbs.setDenumire(denumire);
        wbs.setPozitie(pozitie);
        return service.addWbs(SecurityUtils.getToken(), projectId, wbs);
    }

    @PostMapping("/dmsws/project/updateWbs/{wbsId}")
    public WbsUpdateResponse updateWbs(@PathVariable String wbsId, @RequestBody Wbs2 wbs) {
        return service.updateWbs2(SecurityUtils.getToken(), wbsId, wbs);
    }
    @PostMapping("/dmsws/wbs_uat/updateWbsET/{id}")
    public WbsUpdateResponse updateWbsET(@PathVariable String id, @RequestBody Wbs2 wbs) {
        return service.updateWbsET(SecurityUtils.getToken(), id, wbs);
    }

    @GetMapping("/dmsws/project/getTaskuriWbs/{wbsId}")
    public TaskInfoList getTaskuriWbs(@PathVariable String wbsId) {
        return service.getTaskuriWbs(SecurityUtils.getToken(), wbsId);
    }

    @GetMapping("/dmsws/wbs_uat/getInfoWbsById/{id}")
    public Wbs2 getInfoWbsById(@PathVariable String id) {
        return service.getInfoWbsById(SecurityUtils.getToken(), id);
    }

    @PostMapping("/dmsws/project/addTask/{wbsId}")
    public TaskInfo addTask(@PathVariable String wbsId, @RequestBody TaskInfo task) {

        return service.addTask(SecurityUtils.getToken(), wbsId, task);
    }
    @PostMapping(value = "/dmsws/project/uploadfile/{projectId}/{directorId}",consumes = "multipart/form-data",produces = "text/html")
    public ResponseEntity<String> uploadFile(
            @PathVariable Integer projectId,
            @PathVariable Integer directorId,
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
            service.uploadFisierToProject(SecurityUtils.getToken(),projectId, directorId,
                    ciFile.getFileName(), ciFile.getFileData());

        }
        return ResponseEntity.ok("OK");

    }

    @PostMapping("/dmsws/project/addDirector/{parentId}/{denumire}")
    public Director addDirector(@PathVariable String parentId, @PathVariable String denumire) {
        Director director= new Director();
        director.setDenumire(denumire);
        return service.addDirector(SecurityUtils.getToken(), parentId, director);
    }

    @GetMapping("/dmsws/project/getListaResponsabili")
    public UtilizatorList getListaResponsabili() {
        return service.getListaResponsabili(SecurityUtils.getToken());
    }

    @GetMapping("/dmsws/wbs_uat/getListaStatusEtapa")
    public StatusList getListaStatusEtapa() {
        return service.getListaStatusEtapa(SecurityUtils.getToken());
    }

    @GetMapping("/dmsws/wbs_uat/getListaUnitateMasura")
    public UnitateMasuraList getListaUnitateMasura() {
        return service.getListaUnitateMasura(SecurityUtils.getToken());
    }

    @PostMapping("dmsws/wbs_uat/deleteEtapa/{id}")
    public BaseModel deleteEtapa(@PathVariable String id ) {
        return service.deleteEtapa(SecurityUtils.getToken(),id);
    }


    @GetMapping("/dmsws/project/getListaPrioritatiTichet")
    public ImpactList getListaPrioritatiTichet() {
        return service.getListaPrioritatiTichet(SecurityUtils.getToken());
    }
}
