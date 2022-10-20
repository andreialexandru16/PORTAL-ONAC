package ro.bithat.dms.microservices.dmsws.file;

import ro.bithat.dms.microservices.dmsws.metadata.Lov;

import java.util.List;

public class DocAttrLink {
	
	private Long fileId;
	
	private Boolean hidden;

	private String dataType;

	private Long precision;

	private Long length;

	private Long lovId;

	private Boolean readOnly;

	private String label;

	private Boolean mandatory;

	private Long attributeId;

	private String attributeCode;

	private Long selectionType;

	private Long position;

	private String value;

	private Boolean multipleSelection;
	private List<Lov> valueForLov;
	private Double rand;
	private Double coloana;
	private Double widthPx;
	private Double widthPercent;
	private String aliniere;
	private String lovDisplayMode;
	private Integer nrSelectiiLov;
	private String denumireTipSelectie;
	
    private String valoareImplicita;
    private String validator;

	private String attributeIcon;
	private List<RowAttrComplexList> attrsOfComplex;
	private Boolean checkUniqueTert;
	private Boolean precompletareAutomata;
	private String formulaCalculPortal;
	private String keyCode;
	private String validatorErrMessage;
	private String total;
	private String formula_calcul_coloana;
	private Integer idDocumentSelectie;
	private Integer idTipDocDataFisier;
	private String formatObligatoriu;
	private Integer idTemplateFileXml;
	private String templateFileXml;
	private String templateFileXmlNume;
	private Integer showImportXml;
	private String image;
	private String numeFisier;

	public String getNumeFisier() {
		return numeFisier;
	}

	public void setNumeFisier(String numeFisier) {
		this.numeFisier = numeFisier;
	}
	public Integer getShowImportXml() {
		return showImportXml;
	}

	public void setShowImportXml(Integer showImportXml) {
		this.showImportXml = showImportXml;
	}
	public String getTemplateFileXmlNume() {
		return templateFileXmlNume;
	}

	public void setTemplateFileXmlNume(String templateFileXmlNume) {
		this.templateFileXmlNume = templateFileXmlNume;
	}
	public String getTemplateFileXml() {
		return templateFileXml;
	}

	public void setTemplateFileXml(String templateFileXml) {
		this.templateFileXml = templateFileXml;
	}
	public Integer getIdTemplateFileXml() {
		return idTemplateFileXml;
	}

	public void setIdTemplateFileXml(Integer idTemplateFileXml) {
		this.idTemplateFileXml = idTemplateFileXml;
	}
	public String getFormatObligatoriu() {
		return formatObligatoriu;
	}

	public void setFormatObligatoriu(String formatObligatoriu) {
		this.formatObligatoriu = formatObligatoriu;
	}

	public Integer getIdTipDocDataFisier() {
		return idTipDocDataFisier;
	}

	public void setIdTipDocDataFisier(Integer idTipDocDataFisier) {
		this.idTipDocDataFisier = idTipDocDataFisier;
	}
	public Integer getIdDocumentSelectie() {
		return idDocumentSelectie;
	}

	public void setIdDocumentSelectie(Integer idDocumentSelectie) {
		this.idDocumentSelectie = idDocumentSelectie;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getFormula_calcul_coloana() {
		return formula_calcul_coloana;
	}

	public void setFormula_calcul_coloana(String formula_calcul_coloana) {
		this.formula_calcul_coloana = formula_calcul_coloana;
	}

	public String getValidatorErrMessage() {
		return validatorErrMessage;
	}

	public void setValidatorErrMessage(String validatorErrMessage) {
		this.validatorErrMessage = validatorErrMessage;
	}
	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getFormulaCalculPortal() {
		return formulaCalculPortal;
	}

	public void setFormulaCalculPortal(String formulaCalculPortal) {
		this.formulaCalculPortal = formulaCalculPortal;
	}
	public Boolean getPrecompletareAutomata() {
		return precompletareAutomata;
	}

	public void setPrecompletareAutomata(Boolean precompletareAutomata) {
		this.precompletareAutomata = precompletareAutomata;
	}
	public Boolean getCheckUniqueTert() {
		return checkUniqueTert;
	}

	public void setCheckUniqueTert(Boolean checkUniqueTert) {
		this.checkUniqueTert = checkUniqueTert;
	}

	public List<RowAttrComplexList> getAttrsOfComplex() {
		return attrsOfComplex;
	}

	public void setAttrsOfComplex(List<RowAttrComplexList> attrsOfComplex) {
		this.attrsOfComplex = attrsOfComplex;
	}

	public DocAttrLink(Long attributeId, String attributeCode, String value) {
		super();
		this.attributeId = attributeId;
		this.attributeCode = attributeCode;
		this.value = value;
	}
	public DocAttrLink( String attributeCode, String value) {
		super();
		this.attributeCode = attributeCode;
		this.value = value;
	}
	public DocAttrLink() {
		super();
	}

	public String getAttributeIcon() {
		return attributeIcon;
	}

	public void setAttributeIcon(String attributeIcon) {
		this.attributeIcon = attributeIcon;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getPrecision() {
		return precision;
	}

	public void setPrecision(Long precision) {
		this.precision = precision;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Long getLovId() {
		return lovId;
	}

	public void setLovId(Long lovId) {
		this.lovId = lovId;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	public String getAttributeCode() {
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}

	public Long getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(Long selectionType) {
		this.selectionType = selectionType;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getMultipleSelection() {
		return multipleSelection;
	}

	public void setMultipleSelection(Boolean multipleSelection) {
		this.multipleSelection = multipleSelection;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public List<Lov> getValueForLov() {
		return valueForLov;
	}

	public void setValueForLov(List<Lov> valueForLov) {
		this.valueForLov = valueForLov;
	}

	public Double getRand() {
		return rand;
	}

	public void setRand(Double rand) {
		this.rand = rand;
	}

	public Double getColoana() {
		return coloana;
	}

	public void setColoana(Double coloana) {
		this.coloana = coloana;
	}

	public Double getWidthPx() {
		return widthPx;
	}

	public void setWidthPx(Double widthPx) {
		this.widthPx = widthPx;
	}

	public Double getWidthPercent() {
		return widthPercent;
	}

	public void setWidthPercent(Double widthPercent) {
		this.widthPercent = widthPercent;
	}

	public String getAliniere() {
		return aliniere;
	}

	public void setAliniere(String aliniere) {
		this.aliniere = aliniere;
	}

	public String getLovDisplayMode() {
		return lovDisplayMode;
	}

	public void setLovDisplayMode(String lovDisplayMode) {
		this.lovDisplayMode = lovDisplayMode;
	}

	public Integer getNrSelectiiLov() {
		return nrSelectiiLov;
	}

	public void setNrSelectiiLov(Integer nrSelectiiLov) {
		this.nrSelectiiLov = nrSelectiiLov;
	}

	public String getDenumireTipSelectie() {
		return denumireTipSelectie;
	}

	public void setDenumireTipSelectie(String denumireTipSelectie) {
		this.denumireTipSelectie = denumireTipSelectie;
	}

	public String getValoareImplicita() {
		return valoareImplicita;
	}

	public void setValoareImplicita(String valoareImplicita) {
		this.valoareImplicita = valoareImplicita;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}