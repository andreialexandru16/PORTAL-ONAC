package ro.bithat.dms.config;

import com.netflix.hystrix.strategy.concurrency.*;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Transfer MDC parameter to hystrix thread!
 * see Slide 18
 * http://de.slideshare.net/inovex/hystrix-in-action-ein-weg-zu-robuster-software
 */
public class LoggingConcurrencyStrategy extends HystrixConcurrencyStrategy {


    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        final Map<String,String> context = MDC.getCopyOfContextMap();
        final RequestAttributes attr = RequestContextHolder.getRequestAttributes();
        return super.wrapCallable(new Callable<T>() {

            @Override
            public T call() throws Exception {
                try {
                    if(context != null) {
                        for (Map.Entry<String, String> entry : context.entrySet()) {
                            MDC.put(entry.getKey(), entry.getValue());
                        }
                    }
                    RequestContextHolder.setRequestAttributes(attr);
                    return callable.call();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                    MDC.clear();
                }
            }
        });
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        return new HystrixRequestVariableDefault<T>() {
            @Override
            public T get() {
                if (!HystrixRequestContext.isCurrentThreadInitialized()) {
                    return null;
                }
                return super.get();
            }
        };
    }
}
