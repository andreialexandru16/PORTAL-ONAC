package ro.bithat.dms.microservices.dmsws.metadata;

import ro.bithat.dms.microservices.dmsws.file.RowAttrComplexList;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza attribute link.
 */
@XmlRootElement
public class AttributeLink implements Comparable<AttributeLink>{
    private Integer attributeId;
    private String name;
    private String label;
    private String dataType;
    private Integer length;
    private Integer precision;
    private Boolean mandatory;
    private Integer selectionType;
    private Integer lovId;
    private Integer position;
    private Boolean readOnly;
    private Boolean hidden;
    private Boolean multipleSelection;
    private Double rand;
    private Double coloana;
    private Double widthPx;
    private Double widthPercent;
    private String aliniere;
    private String lovDisplayMode;
    private Integer nrSelectiiLov;
    private String denumireTipSelectie;
    private String valoareImplicita;
    private List<Lov> valueForLov;
    private String value;
    private String attributeIcon;
    private String validator;
    private Integer idDocumentSelectie;
    private List<RowAttrComplexList> attrsOfComplex;
    private Boolean checkUniqueTert;
    private Boolean precompletareAutomata;
    private String formulaCalculPortal;
    private Integer id_parinte;
    private String formula_calcul_coloana;
    private String keyCode;
    private String validare_portal;
    private String validatorErrMessage;
    private String total;
    private String selectSql;
    private Integer attrComplexMaxRows;
    private Integer idFisier;
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

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public String getAttributeIcon() {
        return attributeIcon;
    }

    public void setAttributeIcon(String attributeIcon) {
        this.attributeIcon = attributeIcon;
    }
    public List<Lov> getValueForLov() {
        return valueForLov;
    }

    public void setValueForLov(List<Lov> valueForLov) {
        this.valueForLov = valueForLov;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValoareImplicita() {
        return valoareImplicita;
    }

    public void setValoareImplicita(String valoareImplicita) {
        this.valoareImplicita = valoareImplicita;
    }

    public String getDenumireTipSelectie() {
        return denumireTipSelectie;
    }

    public void setDenumireTipSelectie(String denumireTipSelectie) {
        this.denumireTipSelectie = denumireTipSelectie;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(Integer selectionType) {
        this.selectionType = selectionType;
    }

    public Integer getLovId() {
        return lovId;
    }

    public void setLovId(Integer lovId) {
        this.lovId = lovId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getMultipleSelection() {
        return multipleSelection;
    }

    public void setMultipleSelection(Boolean multipleSelection) {
        this.multipleSelection = multipleSelection;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public Integer getIdDocumentSelectie() {
        return idDocumentSelectie;
    }


    public void setIdDocumentSelectie(Integer idDocumentSelectie) {
        this.idDocumentSelectie = idDocumentSelectie;
    }

    @Override
    public int compareTo(AttributeLink other) {
        // Highest first
        return -Double.compare(getRand(), other.getRand());

        /* Or equivalently:
           return Integer.compare(other.getScore(), getScore());
        */
    }

    public Integer getId_parinte() {
        return id_parinte;
    }

    public void setId_parinte(Integer id_parinte) {
        this.id_parinte = id_parinte;
    }

    public String getFormula_calcul_coloana() {
        return formula_calcul_coloana;
    }

    public void setFormula_calcul_coloana(String formula_calcul_coloana) {
        this.formula_calcul_coloana = formula_calcul_coloana;
    }

    public String getValidare_portal() {
        return validare_portal;
    }

    public void setValidare_portal(String validare_portal) {
        this.validare_portal = validare_portal;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    public Integer getAttrComplexMaxRows() {
        return attrComplexMaxRows;
    }

    public void setAttrComplexMaxRows(Integer attrComplexMaxRows) {
        this.attrComplexMaxRows = attrComplexMaxRows;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
