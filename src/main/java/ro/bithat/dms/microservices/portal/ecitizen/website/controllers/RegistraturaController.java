package ro.bithat.dms.microservices.portal.ecitizen.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.FileUtils;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.ProjectService;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.*;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.CriteriiCautare;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.CriteriiCautareList;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RegistraturaCompleteList;
import ro.bithat.dms.microservices.portal.ecitizen.website.services.RegistraturaService;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
public class RegistraturaController {

    @Autowired
    private URLUtil urlUtil;

    public static final String ERROR_TEXT = "Error Status Code ";


    @Autowired
    private RegistraturaService registraturaService;

    @GetMapping("/dmsws/regsistratura/getInregistrariList/{idRegistru}")
    public RegistraturaCompleteList getInregistrariList(
            @PathVariable String idRegistru,
            @RequestParam(value = "indexStart", required = false) Integer indexStart,
            @RequestParam(value = "indexEnd", required = false) Integer indexEnd
                                                                                                  ) {

        return registraturaService.getInregistrariList(SecurityUtils.getToken(),idRegistru, Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd));
    }
    @GetMapping("/dmsws/regsistratura/getInregistrariListCount/{idRegistru}")
    public BaseModel getInregistrariListCount(
            @PathVariable String idRegistru

    ) {

        return registraturaService.getInregistrariListCount(SecurityUtils.getToken(),idRegistru);
    }



    @PostMapping("/dmsws/regsistratura/getInregistrariListCautareP/{idRegistru}")
    public RegistraturaCompleteList getInregistrariListCautareP(
            @PathVariable String idRegistru,
            @RequestBody List<String> criteriiCautareList,
            @RequestParam(value = "indexStart", required = false) Integer indexStart,
            @RequestParam(value = "indexEnd", required = false) Integer indexEnd,
            @RequestParam(value = "searchStr", required = false) String searchStr,
            @RequestParam(value = "an", required = false) String an,
            @RequestParam(value = "luna", required = false) String luna
    ) {

        return registraturaService.getInregistrariListCautareP(SecurityUtils.getToken(),idRegistru, Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd),Optional.ofNullable(an),Optional.ofNullable(luna),Optional.ofNullable(searchStr),criteriiCautareList);
    }
    @PostMapping("/dmsws/regsistratura/getInregistrariListFiltrare/{idRegistru}")
    public RegistraturaCompleteList getInregistrariListFiltrare(
            @PathVariable String idRegistru,
            @RequestBody HashMap<String,String> criteriiCautareList,
            @RequestParam(value = "indexStart", required = false) Integer indexStart,
            @RequestParam(value = "indexEnd", required = false) Integer indexEnd,
            @RequestParam(value = "searchStr", required = false) String searchStr,
            @RequestParam(value = "an", required = false) String an,
            @RequestParam(value = "luna", required = false) String luna
    ) {

        return registraturaService.getInregistrariListFiltrare(SecurityUtils.getToken(),idRegistru, Optional.ofNullable(indexStart),Optional.ofNullable(indexEnd),Optional.ofNullable(an),Optional.ofNullable(luna),Optional.ofNullable(searchStr),criteriiCautareList);
    }
    @PostMapping("/dmsws/regsistratura/getInregistrariListCautareCountP/{idRegistru}")
    public BaseModel getInregistrariListCautareCountP(
            @PathVariable String idRegistru,
            @RequestBody List<String> criteriiCautareList,
            @RequestParam(value = "searchStr", required = false) String searchStr,
            @RequestParam(value = "an", required = false) String an,
            @RequestParam(value = "luna", required = false) String luna

    ) {

        return registraturaService.getInregistrariListCautareCountP(SecurityUtils.getToken(),idRegistru,Optional.ofNullable(an),Optional.ofNullable(luna),Optional.ofNullable(searchStr),criteriiCautareList);
    }

    @GetMapping("/dmsws/regsistratura/getCriteriiCautare/{idRegistru}")
    public CriteriiCautareList getCriteriiCautare(
            @PathVariable Integer idRegistru
    ) {

        return registraturaService.getCriteriiCautare(SecurityUtils.getToken(),idRegistru);
    }
}
