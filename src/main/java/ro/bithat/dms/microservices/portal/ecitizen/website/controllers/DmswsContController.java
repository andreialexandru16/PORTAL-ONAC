package ro.bithat.dms.microservices.portal.ecitizen.website.controllers;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.flow.DmswsFlowService;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.api.DmswsAddUtilizatorController;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.AddUtilizatorSecurityService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsAddUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@RestController
public class DmswsContController {

    @Autowired
    private DmswsFlowService service;

    @Autowired
    private AddUtilizatorSecurityService checkerService;

    @Autowired
	private URLUtil urlUtil;

    public static final String ERROR_TEXT = "Error Status Code ";
    @PostMapping("/dmsws/portalflow/sendFluxByIdFisier/{idFisier}")
    public StandardResponse confirmareEmail(@PathVariable String idFisier) {
        return service.sendFluxByIdFisier(SecurityUtils.getToken(),Long.parseLong(idFisier));
    }



}
