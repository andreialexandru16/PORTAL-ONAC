package ro.bithat.dms.microservices.portal.ecitizen.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.MenuRights;
import ro.bithat.dms.microservices.portal.ecitizen.website.services.PortalService;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import java.util.Map;


@RestController
public class PortalController {

    @Autowired
    private URLUtil urlUtil;

    public static final String ERROR_TEXT = "Error Status Code ";
    @Autowired
    private PortalService service;


    //return all the services with meniu_electronic=1  (must be use on the top side)
    @GetMapping("/dmsws/meniu/all_menu_web_electronic")
    public MenuRights getAllMeniuWebElectronic()
    {
        return service.getAllMeniuWebElectronic(SecurityUtils.getToken());
    }


    //return all values by LOV code
    @GetMapping("/dmsws/lov/values_by_code/{lovCode}")
    public LovList getValuesLovByCode(@PathVariable String lovCode)
    {
        return service.getValuesLovByCode(SecurityUtils.getToken(), lovCode);
    }



    //return all values by LOV code
    @PostMapping("/dmsws/lov/values_by_code_with_dep/{lovCode}")
    public LovList getValuesLovByCode(@PathVariable String lovCode, @RequestBody Map<String,String> data)
    {
        return service.getValuesLovByCodeWithDep(SecurityUtils.getToken(), lovCode, data);
    }


}
