package ro.bithat.dms.smartform.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

@Component
public class AttributeLinkToDocAttrLinkConversionService implements Converter<AttributeLink,DocAttrLink> {

    @Autowired
    private ConversionService conversionService;

    public DocAttrLink convert(AttributeLink source) {
        if(source!=null){
            DocAttrLink result = new DocAttrLink();
            result.setHidden(source.getHidden());
            result.setDataType(((AttributeLink) source).getDataType());
            result.setPrecision(conversionService.convert(((AttributeLink) source).getPrecision(), Long.class));
            result.setLength(conversionService.convert(source.getLength(), Long.class));
            result.setLovId(conversionService.convert(((AttributeLink) source).getLovId(), Long.class));
            result.setReadOnly(((AttributeLink) source).getReadOnly());
            result.setLabel(source.getLabel());
            result.setMandatory(((AttributeLink) source).getMandatory());
            result.setAttributeId(conversionService.convert(source.getAttributeId(), Long.class));
            result.setAttributeCode(source.getName());
            result.setSelectionType(conversionService.convert(((AttributeLink) source).getSelectionType(), Long.class));
            result.setPosition(conversionService.convert(((AttributeLink) source).getPosition(), Long.class));
            result.setValue(source.getValue());
            result.setMultipleSelection(((AttributeLink) source).getMultipleSelection());
            result.setValueForLov(source.getValueForLov());
            result.setRand(source.getRand());
            result.setColoana(source.getColoana());
            result.setWidthPx(source.getWidthPx());
            result.setWidthPercent(source.getWidthPercent());
            result.setAliniere(source.getAliniere());
            result.setLovDisplayMode(source.getLovDisplayMode());
            result.setNrSelectiiLov(source.getNrSelectiiLov());
            result.setDenumireTipSelectie(source.getDenumireTipSelectie());
            result.setValoareImplicita(source.getValoareImplicita());
            result.setValidator(source.getValidator());
            result.setAttributeIcon(source.getAttributeIcon());
            result.setAttrsOfComplex(source.getAttrsOfComplex());
            result.setCheckUniqueTert(source.getCheckUniqueTert());
            result.setPrecompletareAutomata(source.getPrecompletareAutomata());
            result.setFormulaCalculPortal(source.getFormulaCalculPortal());
            result.setKeyCode(source.getKeyCode());
            result.setValidatorErrMessage(source.getValidatorErrMessage());
            result.setTotal(source.getTotal());
            result.setFormula_calcul_coloana(source.getFormula_calcul_coloana());
            result.setIdDocumentSelectie(source.getIdDocumentSelectie());
            result.setFormatObligatoriu(source.getFormatObligatoriu());
            result.setShowImportXml(source.getShowImportXml());
            result.setNumeFisier(source.getNumeFisier());

            return result;
        }
        return new DocAttrLink();

    }

}
