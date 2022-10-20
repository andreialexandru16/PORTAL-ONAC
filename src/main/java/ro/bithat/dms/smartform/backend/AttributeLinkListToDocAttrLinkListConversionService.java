package ro.bithat.dms.smartform.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;

import java.util.stream.Collectors;

@Component
public class AttributeLinkListToDocAttrLinkListConversionService implements Converter<AttributeLinkList,DocAttrLinkList> {

    @Autowired
    private AttributeLinkToDocAttrLinkConversionService converter;



    public DocAttrLinkList convert(AttributeLinkList source) {
        DocAttrLinkList result = new DocAttrLinkList();
        result.setInfo(source.getInfo());
        result.setExtendedInfo(source.getExtendedInfo());
        result.setInfoList(source.getInfoList());
        result.setResult(source.getResult());
        if(source.getAttributeLink()!=null){
            result.setDocAttrLink(source
                    .getAttributeLink().stream().map(al -> converter.convert(al))
                    .collect(Collectors.toList()));

        }


        return result;
    }

}
