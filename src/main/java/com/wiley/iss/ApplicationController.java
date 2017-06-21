package com.wiley.iss;

import com.wiley.iss.model.*;
import com.wiley.iss.service.CatalogService;
import com.wiley.iss.service.FileUploadService;
import com.wiley.iss.service.InterfaceService;
import com.wiley.iss.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/webapi")
public class ApplicationController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ApplicationController.class);

    private CatalogService catalogService;
    private InterfaceService interfaceService;
    private FileUploadService fileUploadService;

    @Autowired
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Autowired
    public void setInterfaceService(InterfaceService interfaceService) {
        this.interfaceService = interfaceService;
    }

    @Autowired
    public void setFileUploadService(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    @ResponseBody
    public List<ServiceRecord> getResultSet() {
        return catalogService.fetchResults();
    }


    @RequestMapping(value = "/interfaces", method = RequestMethod.GET)
    @ResponseBody
    public List<InterfaceRecord> getInterfaceResultSet() {
        return interfaceService.fetchResults();
    }

    @RequestMapping(value = "/service/{svcId}/version/{versionId:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ServiceDetail getServiceDetail(@PathVariable("svcId") String svcId,
                                          @PathVariable("versionId") String versionId) {
        LOGGER.debug("svcId " + svcId + ", versionId " + versionId);
        if (svcId != null && !svcId.isEmpty() && versionId != null
                && !versionId.isEmpty()) {
            return catalogService.fetchDetailsResults(svcId, versionId);
        } else {
            return null;
        }
    }


    @RequestMapping(value = "/interface/{interfaceId}", method = RequestMethod.GET)
    @ResponseBody
    public InterfaceRecord getInterfaceDetail(@PathVariable("interfaceId") String serviceId) {
        LOGGER.debug("serviceId " + serviceId);
        if (serviceId != null && !serviceId.isEmpty()) {
            try {
                int svcId = Integer.parseInt(serviceId);
                return interfaceService.getInterfaceDetails(svcId);
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @RequestMapping("/flare")
    @ResponseBody
    public Node<String> getResultsinFlare() {
        List<ServiceRecord> results = catalogService.fetchResults();
        Node<String> parent = new Node<>("Business Domains");
        Node<String> temp;
        for (ServiceRecord svc : results) {
            temp = parent.addChildIfNotExists(svc.getDomainName());
            temp = temp.addChildIfNotExists(svc.getSvcName());
            temp = temp.addChildIfNotExists("" + svc.getVersionId());
            temp = temp.addChildIfNotExists(svc.getConsumerName());
        }

        return parent;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Response uploadTemplate(
            @RequestParam("file") MultipartFile fileDetail, HttpServletRequest request) throws IOException {

        String fileName = fileDetail.getOriginalFilename();
        String[] strAr = fileName.split("\\.");
        Response response = new Response();
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ApplicationConstants.USER_DTO);

        // Validate User
		if (user == null || user.getUid()==null) {
            response.setMessage("Unable to fetch User details from session.");
            return  response;
		}
        LOGGER.info("Logged in : " + user.toString());

        if (strAr.length < 2
                || !strAr[strAr.length - 1].equalsIgnoreCase("xlsx")) {
            LOGGER.info("File Type Mismatch : " + fileDetail.getOriginalFilename());

            response.setMessage("File Type has to be xlsx.");
            return response;
        } else {
            return fileUploadService.uploadFile(fileDetail.getInputStream(), user);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO getUserDetails(HttpServletRequest request){
        HttpSession session = request.getSession();
        return  (UserDTO) session.getAttribute(ApplicationConstants.USER_DTO);
    }
}