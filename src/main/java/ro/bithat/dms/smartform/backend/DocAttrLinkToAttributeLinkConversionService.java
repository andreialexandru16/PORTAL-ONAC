package ro.bithat.dms.smartform.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

@Component
public class DocAttrLinkToAttributeLinkConversionService implements Converter<DocAttrLink,AttributeLink> {

    @Autowired
    private ConversionService conversionService;

    public AttributeLink convert( DocAttrLink source) {
        AttributeLink result = new AttributeLink();
        result.setAttributeId(conversionService.convert(source.getAttributeId(), Integer.class));
        //        result.setAttributeCode(((DocAttrLink) source).get); //TODO not in AttributeLink
        result.setDataType(((DocAttrLink) source).getDataType());
        //        result.setFileId(((DocAttrLink) source).); //TOOD noi in AttributeLink
        result.setHidden(source.getHidden());
        result.setLabel(source.getLabel());
        result.setLength(conversionService.convert(source.getLength(), Integer.class));
        result.setMandatory(((DocAttrLink) source).getMandatory());
        result.setMultipleSelection(((DocAttrLink) source).getMultipleSelection());
        result.setPosition(conversionService.convert(((DocAttrLink) source).getPosition(), Integer.class));
        result.setPrecision(conversionService.convert(((DocAttrLink) source).getPrecision(), Integer.class));
        result.setReadOnly(((DocAttrLink) source).getReadOnly());
        result.setSelectionType(conversionService.convert(((DocAttrLink) source).getSelectionType(), Integer.class));
        result.setLovId(conversionService.convert(((DocAttrLink) source).getLovId(), Integer.class));
        result.setValue(source.getValue());
        result.setValidator(source.getValidator());
        result.setAttributeIcon(source.getAttributeIcon());
        result.setValoareImplicita(source.getValoareImplicita());
        result.setCheckUniqueTert(source.getCheckUniqueTert());
        result.setNrSelectiiLov(source.getNrSelectiiLov());
        result.setPrecompletareAutomata(source.getPrecompletareAutomata());
        result.setFormula_calcul_coloana(source.getFormula_calcul_coloana());
        result.setFormulaCalculPortal(source.getFormulaCalculPortal());
        result.setKeyCode(source.getKeyCode());
        result.setValidatorErrMessage(source.getValidatorErrMessage());
        result.setTotal(source.getTotal());
        result.setKeyCode(source.getKeyCode());
        result.setIdDocumentSelectie(source.getIdDocumentSelectie());
        result.setFormatObligatoriu(source.getFormatObligatoriu());
        result.setShowImportXml(source.getShowImportXml());
        result.setNumeFisier(source.getNumeFisier());

        return result;
    }

}
