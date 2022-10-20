package ro.bithat.dms.passiveview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouteConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import ro.bithat.dms.boot.BeanUtil;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class QueryParameterUtil {

    private final static Logger logger = LoggerFactory.getLogger(QueryParameterUtil.class);


    public static final <T> Optional<T> getQueryParameter(String name, Class<T> parameterType) {
        Optional<List<String>> parameterComposite = Optional
                .ofNullable(VaadinClientUrlUtil.getQueryParameters().getParameters().get(name));
        if(!parameterComposite.isPresent()) {
            logger.warn("parametrul " + name + "  nu este in definit");
            return Optional.empty();
        }
        String parameterStr = "";
        if(parameterComposite.get().size() != 1) {
            logger.warn("parametrul " + name + "  este setat gresit");
            return Optional.empty();
        }
        parameterStr = parameterComposite.get().get(0);
        ConversionService conversionService = BeanUtil.getBean(ConversionService.class);
        if(!conversionService.canConvert(String.class, parameterType)) {
            logger.warn("valoarea\t"+ parameterStr +" a parametrului " + name + "  nu poate fi convertit la tipul cerut\t"+parameterType);
            return Optional.empty();
        }
        return Optional.of(conversionService.convert(parameterStr, parameterType));
    }

    public static final Optional<String> getQueryParameter(String name) {
        Optional<String> encodingValue = getQueryParameter(name, String.class);
        try {
            if(encodingValue.isPresent()) {
                return Optional.of(URLDecoder.decode(encodingValue.get(), StandardCharsets.UTF_8.toString()));
            }
        } catch (Throwable e) {

            logger.warn("decode error", e.getStackTrace());
        }
        return encodingValue;
    }

    public static final String getRelativePathWithQueryParameter(String name, Object parameter) {
        ConversionService conversionService = BeanUtil.getBean(ConversionService.class);
        if(conversionService.canConvert(parameter.getClass(), String.class)) {
            if(VaadinClientUrlUtil.getRouteRelativePath().contains(name + "=")){
                return VaadinClientUrlUtil.getRouteRelativePath().
                        replaceFirst("\\b"+name+"=.*?(&|$)", "") + getParameterConcatString()
                        + name + "=" + conversionService.convert(parameter, String.class);
            }
            //03.06.2021 - Neata Georgiana - ANRE - am adaugat acest if pentru ca daca exista deja un parametru un URL
            else if(VaadinClientUrlUtil.getRouteRelativePath().contains(  "?")){
                return VaadinClientUrlUtil.getRouteRelativePath() + getParameterConcatString()
                        +"&"+ name + "=" + conversionService.convert(parameter, String.class);
            }
            else {
                return VaadinClientUrlUtil.getRouteRelativePath() + getParameterConcatString()
                        + name + "=" + conversionService.convert(parameter, String.class);
            }
        } else {
            logger.warn("parametrul de tipul\t"+ parameter.getClass() + " nu poate este serializabil!");
        }
        return VaadinClientUrlUtil.getRouteRelativePath();
    }

    private static String getParameterConcatString() {
        return VaadinClientUrlUtil.getRouteRelativePath().contains("&") ? "&" :
                (VaadinClientUrlUtil.getRouteRelativePath().contains("?") ? "" : "?" );
    }

    public static final String getRelativePathWithQueryParameters(Map<String, Object> parameters, Class<? extends Component> route) {
        ConversionService conversionService = BeanUtil.getBean(ConversionService.class);
        if(parameters == null || parameters.size() == 0) {
            return RouteConfiguration.forApplicationScope().getUrl(route);

        }
        StringBuilder parameterQuery = new StringBuilder("?");
        parameters.keySet().forEach(parameter -> {
            if(conversionService.canConvert(parameters.get(parameter).getClass(), String.class)) {
                parameterQuery.append(parameter + "=" + conversionService.convert(parameters.get(parameter), String.class) + "&");
            } else {
                logger.warn("parametrul de tipul\t"+ parameter.getClass() + " nu poate este serializabil!");
            }
        });
        return RouteConfiguration.forApplicationScope().getUrl(route) + parameterQuery.substring(0, parameterQuery.length() - 1).toString();
    }

    public static final QueryParameters getQueryParameters(String url) {
        Map<String, String[]> parameters = new HashMap<>();
        if(url.contains("?")) {
            String[] parameterValues = url.substring(url.indexOf("?") + 1).split("&");
            for(String parameterValue: parameterValues) {
                String[] pv = parameterValue.split("=");
                List<String> newValues = new ArrayList<>();
                String[] values = parameters.get(pv[0]);
                newValues.add(pv[1]);
                if(values != null) {
                    newValues.addAll(Arrays.asList(values));
                }
                parameters.put(pv[0], newValues.toArray(new String[newValues.size()]));
            }
        }
        return QueryParameters.full(parameters);
    }

}
