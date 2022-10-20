package ro.bithat.dms.microservices.dmsws.ps4.detaliiserviciu;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ElectronicService extends BaseModel {


    private String id;
    private String codeServiceName;
    private String serviceNameValue;
    private String codeServiceDescription;
    private String serviceDescriptionValue;
    private String codeServiceTimpSolutionare;
    private String serviceTimpSolutionareValue;
    private String codeServiceTermenCompletare;
    private String serviceTermenCompletareValue;
    private String codeServiceCost;
    private String serviceCostValue;
    private String codeServicePersType;
    private String servicePersTypeValue;
    private String codeServiceProgramPrestare;
    private String serviceProgramPrestareValue;
    private String adresaPrestator;
    private String programFunctionare;
    private String compartiment;
    private String wizDocuments;
    private String wizLines;
    private String wizPayments;
    private String wizWorkflow;
    private String idDocType;
    private String idDepartament;
    private String idUnitate;
    private String termenArhivare;
    private String serviceFormulaCost;

    public String getTermenArhivare() {
        return termenArhivare;
    }

    public void setTermenArhivare(String termenArhivare) {
        this.termenArhivare = termenArhivare;
    }

    public String getCodeServiceProgramPrestare() {
        return codeServiceProgramPrestare;
    }

    public void setCodeServiceProgramPrestare(String codeServiceProgramPrestare) {
        this.codeServiceProgramPrestare = codeServiceProgramPrestare;
    }

    public String getServiceProgramPrestareValue() {
        return serviceProgramPrestareValue;
    }

    public void setServiceProgramPrestareValue(String serviceProgramPrestareValue) {
        this.serviceProgramPrestareValue = serviceProgramPrestareValue;
    }

    public String getCodeServiceTimpSolutionare() {
        return codeServiceTimpSolutionare;
    }

    public void setCodeServiceTimpSolutionare(String codeServiceTimpSolutionare) {
        this.codeServiceTimpSolutionare = codeServiceTimpSolutionare;
    }

    public String getServiceTimpSolutionareValue() {
        return serviceTimpSolutionareValue;
    }

    public void setServiceTimpSolutionareValue(String serviceTimpSolutionareValue) {
        this.serviceTimpSolutionareValue = serviceTimpSolutionareValue;
    }

    public String getCodeServiceTermenCompletare() {
        return codeServiceTermenCompletare;
    }

    public void setCodeServiceTermenCompletare(String codeServiceTermenCompletare) {
        this.codeServiceTermenCompletare = codeServiceTermenCompletare;
    }

    public String getServiceTermenCompletareValue() {
        return serviceTermenCompletareValue;
    }

    public void setServiceTermenCompletareValue(String serviceTermenCompletareValue) {
        this.serviceTermenCompletareValue = serviceTermenCompletareValue;
    }

    public String getCodeServiceCost() {
        return codeServiceCost;
    }

    public void setCodeServiceCost(String codeServiceCost) {
        this.codeServiceCost = codeServiceCost;
    }

    public String getServiceCostValue() {
        return serviceCostValue;
    }

    public void setServiceCostValue(String serviceCostValue) {
        this.serviceCostValue = serviceCostValue;
    }

    public String getCodeServicePersType() {
        return codeServicePersType;
    }

    public void setCodeServicePersType(String codeServicePersType) {
        this.codeServicePersType = codeServicePersType;
    }

    public String getServicePersTypeValue() {
        return servicePersTypeValue;
    }

    public void setServicePersTypeValue(String servicePersTypeValue) {
        this.servicePersTypeValue = servicePersTypeValue;
    }

    public String getAdresaPrestator() {
        return adresaPrestator;
    }

    public void setAdresaPrestator(String adresaPrestator) {
        this.adresaPrestator = adresaPrestator;
    }

    public String getProgramFunctionare() {
        return programFunctionare;
    }

    public void setProgramFunctionare(String programFunctionare) {
        this.programFunctionare = programFunctionare;
    }

    public String getCompartiment() {
        return compartiment;
    }

    public void setCompartiment(String compartiment) {
        this.compartiment = compartiment;
    }

    public String getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(String idDepartament) {
        this.idDepartament = idDepartament;
    }

    public String getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(String idUnitate) {
        this.idUnitate = idUnitate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeServiceName() {
        return codeServiceName;
    }

    public void setCodeServiceName(String codeServiceName) {
        this.codeServiceName = codeServiceName;
    }

    public String getServiceNameValue() {
        return serviceNameValue;
    }

    public void setServiceNameValue(String serviceNameValue) {
        this.serviceNameValue = serviceNameValue;
    }

    public String getCodeServiceDescription() {
        return codeServiceDescription;
    }

    public void setCodeServiceDescription(String codeServiceDescription) {
        this.codeServiceDescription = codeServiceDescription;
    }

    public String getServiceDescriptionValue() {
        return serviceDescriptionValue;
    }

    public void setServiceDescriptionValue(String serviceDescriptionValue) {
        this.serviceDescriptionValue = serviceDescriptionValue;
    }

    public String getWizDocuments() {
        return wizDocuments;
    }

    public void setWizDocuments(String wizDocuments) {
        this.wizDocuments = wizDocuments;
    }

    public String getWizLines() {
        return wizLines;
    }

    public void setWizLines(String wizLines) {
        this.wizLines = wizLines;
    }

    public String getWizPayments() {
        return wizPayments;
    }

    public void setWizPayments(String wizPayments) {
        this.wizPayments = wizPayments;
    }

    public String getWizWorkflow() {
        return wizWorkflow;
    }

    public void setWizWorkflow(String wizWorkflow) {
        this.wizWorkflow = wizWorkflow;
    }

    public String getIdDocType() {
        return idDocType;
    }

    public void setIdDocType(String idDocType) {
        this.idDocType = idDocType;
    }

	public String getServiceFormulaCost() {
		return serviceFormulaCost;
	}

	public void setServiceFormulaCost(String serviceFormulaCost) {
		this.serviceFormulaCost = serviceFormulaCost;
	}
}


