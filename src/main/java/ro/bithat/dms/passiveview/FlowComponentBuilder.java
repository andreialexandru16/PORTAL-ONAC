package ro.bithat.dms.passiveview;


import com.vaadin.flow.component.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ServerWebInputException;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.passiveview.mvp.FlowPresenter;
import ro.bithat.dms.passiveview.mvp.FlowPresenterModelAndViewPostProcessor;
import ro.bithat.dms.passiveview.mvp.FlowView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
@UIScope
public class FlowComponentBuilder implements FlowViewFactory, FlowPresenterModelAndViewPostProcessor {

    private final String ROOT_ID = "ROOT";

    protected final Logger logger = LoggerFactory.getLogger(FlowComponentBuilder.class);

    private Map<String, FlowView> compositeView = new ConcurrentHashMap<>();

    private Map<String, List<FlowView>> routeViewCompositeTree = new ConcurrentHashMap<>();

    @Autowired
    private EventBus.UIEventBus uiEventBus;

    @Autowired
    private ConversionService conversionService;

    public void registerDomEvent(String viewId, String presenterMethod, Component component, Class<? extends ComponentEvent> eventType, Object... params) {
        FlowView view = compositeView.get(viewId);
        List<Class<?>> paramTypes = new ArrayList<>();
        paramTypes.add(eventType);
        paramTypes.addAll(Stream.of(params).map(p -> p.getClass()).collect(Collectors.toList()));
        Method method = ReflectionUtils.findMethod(view.getPresenterType(), presenterMethod, paramTypes.toArray(new Class[paramTypes.size()]));
        registerComponentEventListener(view.getPresenter(), method, component, eventType, params);
    }

    class FlowPresenterMethodInvocation implements MethodInvocation {

        protected final Logger logger = LoggerFactory.getLogger(FlowPresenterMethodInvocation.class);

        private final Object target;

        private final Method method;

        private final Object[] arguments;

        public FlowPresenterMethodInvocation(Object target, Method method, Object[] arguments) {
            this.target = target;
            this.method = method;
            this.arguments = arguments;
        }

        @Override
        public Method getMethod() {
            return this.method;
        }

        @Override
        public Object[] getArguments() {
            return this.arguments;
        }

        @Override
        public Object proceed() throws Throwable {
            return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
        }

        @Override
        public Object getThis() {
            return this.target;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return this.method;
        }

    }

    class GuiEvent extends EventObject {

        public GuiEvent(FlowPresenterMethodInvocation eventMethodInvocation) {
            super(eventMethodInvocation);
        }
    }

    class ModelEvent extends EventObject {

        public ModelEvent(FlowPresenterMethodInvocation eventMethodInvocation) {
            super(eventMethodInvocation);
        }
    }

    public void buildRoute(Object route) {
        compositeView.clear();
        routeViewCompositeTree.clear();
        constructFlowComponents(route);
    }

    private void constructFlowComponents(Object target) {
        ReflectionUtils.doWithFields(target.getClass(), field -> flowComponentField(target, field), this::isFlowComponentField);
    }

    private boolean isFlowComponentField(Field field) {
        return Optional.ofNullable(AnnotatedElementUtils.getMergedAnnotation(field, FlowComponent.class)).isPresent();
    }

    private void flowComponentField(Object target, Field field) {
        String targetId = ROOT_ID;
        if(Component.class.isAssignableFrom(target.getClass()) &&
                AnnotatedElementUtils.getMergedAnnotation(target.getClass(), Route.class) == null) {
            targetId = ((Component)target).getId().isPresent() ?
                    ((Component)target).getId().get() : UUID.randomUUID().toString();
        }
        flowComponentField(target, targetId, field);
    }

    private void flowComponentField(Object target, String targetId, Field field) {
        FlowComponent flowComponentAttributes = AnnotatedElementUtils.getMergedAnnotation(field, FlowComponent.class);
        String instanceId = flowComponentAttributes.instanceId().trim().length() == 0 ?
                UUID.randomUUID().toString() : flowComponentAttributes.instanceId();

        //        boolean isNew = !containsView(instanceId);
        FlowView view = getView(instanceId, (Class<? extends FlowView>) field.getType());
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, target, view);

        List<FlowView> leafs = routeViewCompositeTree.get(targetId);

        if(leafs == null) {
            leafs = new ArrayList<>();
            routeViewCompositeTree.put(targetId, leafs);
        }
        leafs.add(view);

        //        if(isNew) {
        //            constructFlowComponents(view);
        //        }
    }

    @PostConstruct
    private void initEventBus() {
        uiEventBus.subscribe(this);
    }

    @PreDestroy
    private void predestroyEventBus() {
        uiEventBus.unsubscribe(this);
    }

    @EventBusListenerMethod
    public synchronized void onGuiEvent(GuiEvent guiEvent) {
        //TODO enable this in only in production
        try {
            ((FlowPresenterMethodInvocation)guiEvent.getSource()).proceed();
            logger.info("Gui Event fired");
        } catch (Throwable throwable) {
            logger.error("Error on fire Gui Event", throwable);
            Notification.show("Eroare server DMSWS! Repetati operatia.", 10000,
					Notification.Position.BOTTOM_END);
        }
        //TODO enable this in only in production
        UI.getCurrent().getPage().executeJs("toggleDisplayState($0,$1);", "v-system-error", "none");
    }

    @EventBusListenerMethod
    public synchronized void onModelEvent(ModelEvent modelEvent) {
        //TODO enable this in only in production
        try {
            ((FlowPresenterMethodInvocation)modelEvent.getSource()).proceed();
            logger.info("Model Event fired");
        } catch (Throwable throwable) {
            logger.error("Error on fire Model Event", throwable);
            Notification.show("Eroare server DMSWS! Repetati operatia.", 10000,
					Notification.Position.BOTTOM_END);
        }
        //TODO enable this in only in production
        UI.getCurrent().getPage().executeJs("toggleDisplayState($0,$1);", "v-system-error", "none");
    }

    public void bindModelEventListener(Object sender, String methodBinding, Object... params) {
        bindModelEventListener(sender, Optional.empty(), methodBinding, params);
    }

    public void bindModelEventListener(Object sender, Optional<Class<? extends FlowPresenter>> destination, String methodBinding, Object... params) {
        compositeView.values().stream()
                .map(view -> view.getPresenter())
                .filter(presenter -> !destination.isPresent() || destination.get().isAssignableFrom(presenter.getClass()))
                .forEach(presenter -> {
                    ReflectionUtils.doWithMethods(presenter.getClass(), method -> {
                                uiEventBus.publish(sender,
                                        new ModelEvent(constructFlowEventMethodInvocation(presenter, method, params)));
                            },
                            method-> {
                                Optional<EventBusPresenterMethod> presenterEvent = Optional
                                        .ofNullable(AnnotatedElementUtils.getMergedAnnotation(method, EventBusPresenterMethod.class));
                                if(presenterEvent.isPresent()) {
                                    String eventBindingName = presenterEvent.get().value().trim().length() == 0 ?
                                            method.getName() : presenterEvent.get().value();
                                    return eventBindingName.equalsIgnoreCase(methodBinding);
                                }
                                return false;
                            });
                });

    }

    private FlowPresenterMethodInvocation constructFlowEventMethodInvocation(FlowPresenter presenter, Method methodBinding, Object... params) {
        return new FlowPresenterMethodInvocation(presenter, methodBinding, params);
    }

    @Override
    public FlowView getView(String instanceId, Class<? extends FlowView> viewType) {
        Optional<FlowView> view = Optional.ofNullable(getView(instanceId));
        if(view.isPresent()) {
            return view.get();
        }
        FlowView viewInstance = buildView(instanceId, viewType);
        compositeView.put(instanceId, viewInstance);
        return viewInstance;
    }

    private FlowView buildView(String instanceId, Class<?> viewType) {
        FlowView view = (FlowView) BeanUtils.instantiateClass(viewType);
        FlowPresenter presenter = (FlowPresenter) BeanUtils.instantiateClass(view.getPresenterType());
        ReflectionUtils.doWithFields(presenter.getClass(), field -> {
            ConfigurableBeanFactory beanFactory = (ConfigurableBeanFactory)BeanUtil.getBeanFactory();
            Value valueExpression = AnnotatedElementUtils.getMergedAnnotation(field, Value.class);
            Object value = new EmbeddedValueResolver(beanFactory).resolveStringValue(valueExpression.value());
            if(conversionService.canConvert(field.getType(), value.getClass())) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, presenter, conversionService.convert(value, field.getType()));
            }
        }, field -> AnnotatedElementUtils.isAnnotated(field, Value.class));
        ReflectionUtils.doWithFields(presenter.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, presenter, BeanUtil.getBean(field.getType()));
        }, field -> AnnotatedElementUtils.isAnnotated(field, Autowired.class));
        constructFlowComponents(view);
        view.init(instanceId, presenter);
        //        prepareModelAndView(presenter, VaadinClientUrlUtil.getQueryParameters().getQueryString());
        return view;
    }

    public List<FlowView> getCompositeView() {
        return compositeView.values().stream().collect(Collectors.toList());
    }

    public void prepareModelAndView() {
        String stateParam = VaadinClientUrlUtil.getQueryParameters().getQueryString();
        compositeView.values().stream().map(view -> view.getPresenter()).forEach(presenter -> prepareModelAndView(presenter, stateParam));
    }

//    public void internationalize() {
//        compositeView.values().stream().forEach(this::internationalize);
//    }
//
//    private void internationalize(FlowView flowView) {
//        InternationalizeViewEngine.internationalize(flowView, UI.getCurrent().getLocale());
//    }

    @Override
    public void prepareModelAndView(FlowPresenter presenter, String state) {
        FlowPresenterModelAndViewPostProcessor.super.prepareModelAndView(presenter, state);
        addDomEventListeners(presenter);
    }

    private void addDomEventListeners(FlowPresenter presenter) {
        ReflectionUtils.doWithMethods(presenter.getClass(), method -> registerComponentEventListener(presenter, method),
                this::filterDomEventPresenterMethod);
    }

    private boolean filterDomEventPresenterMethod(Method method) {
        return Optional.ofNullable(getDomEventPresenterMethod(method)).isPresent();
    }

    private void registerComponentEventListener(FlowPresenter presenter, Method method) {
        ReflectionUtils.doWithFields(presenter.getView().getClass(), field -> registerComponentEventListener(presenter, field, method),
                field -> filterDomEventPresenterMethodViewComponent(method, field));
    }

    private boolean filterDomEventPresenterMethodViewComponent(Method method, Field field) {
        return getDomEventPresenterMethod(method).viewProperty().equalsIgnoreCase(field.getName());
    }

    private void registerComponentEventListener(FlowPresenter presenter, Field field, Method method) {
        DomEventPresenterMethod domEventPresenterMethodDescription = getDomEventPresenterMethod(method);
        ReflectionUtils.makeAccessible(field);
        Component component = (Component) ReflectionUtils.getField(field, presenter.getView());
        registerComponentEventListener(presenter, method, component, domEventPresenterMethodDescription.eventType());
    }

    private void registerComponentEventListener(FlowPresenter presenter, Method method, Component component, Class<? extends ComponentEvent> eventType, Object... params) {
        ComponentEventListener componentListener = event -> {
            UI.getCurrent().getPage().executeJs("toggleDisplayState($0,$1);", "v-system-error", "none");
            try {
//                todo if upload not work
//                if(SucceededEvent.class.isAssignableFrom(event.getClass())) {
//                    try {
//                        SecurityContextHolder.clearContext();
//                        SecurityContextHolder.getContext().setAuthentication(SecurityUtils.getAuthentication());
//                    }finally {
//                        SecurityContextHolder.clearContext();
//                    }
//                }
                List <Object> paramList = new ArrayList<>();
                paramList.add(event);
                if(Optional.ofNullable(params).isPresent() && params.length > 0) {
                    paramList.addAll(Arrays.asList(params));
                }
                try{
                    constructFlowEventMethodInvocation(presenter, method, paramList.toArray(new Object[paramList.size()])).proceed();

                }catch (Exception e ){

                }
                UI.getCurrent().getPage().executeJs("toggleDisplayState($0,$1);", "v-system-error", "none");
            } catch (Throwable throwable) {
                logger.error("Error proceed Dom Event", throwable.getStackTrace());
                Notification.show(((ServerWebInputException) throwable).getReason(), 10000,
    					Notification.Position.BOTTOM_END);
            }
        };
        ComponentUtil.addListener(component, eventType, componentListener);

    }

    private DomEventPresenterMethod getDomEventPresenterMethod(Method method) {
        return AnnotatedElementUtils.getMergedAnnotation(method, DomEventPresenterMethod.class);
    }

    @Override
    public FlowView getView(String instanceId) {
        return compositeView.get(instanceId);
    }

    @Override
    public int containsViewOfType(Class<?> viewType) {
        return (int) compositeView.values().stream()
                .filter(c -> viewType.isAssignableFrom(c.getClass()))
                .count();
    }

    @Override
    public boolean containsView(Object view) {
        if(Component.class.isAssignableFrom(view.getClass())) {
            return containsView(((Component)view).getId().get());
        }
        return false;
    }

    @Override
    public boolean containsView(String instanceId) {
        return Optional.ofNullable(compositeView.get(instanceId)).isPresent();
    }

}
