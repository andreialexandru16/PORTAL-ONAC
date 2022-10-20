package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.passiveview.QueryParameterUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class PageableDocumentTypePresenter<V extends DocumentTypeView> extends DocumentTypePresenter<V> {

    @Value("${ps4.ecitizen.document.type.service.view.pageLimit:6}")
    private Integer pageLimit;

    private Map<Integer, List<Document>> pagesData = new ConcurrentHashMap<>();

    private Optional<Integer> currentPage;

    @Override
    public void prepareModel(String state) {
        super.prepareModel(state);
        currentPage = QueryParameterUtil.getQueryParameter("pagina", Integer.class);
    }

    public Integer getPageLimit() {
        return pageLimit;
    }

    public Map<Integer, List<Document>> getPagesData() {
        return pagesData;
    }

    public Optional<Integer> getCurrentPage() {
        return currentPage;
    }

    public int getPage() {
        return currentPage.isPresent() ? (currentPage.get() < pagesData.size() ? currentPage.get() : 0) : 0;
    }

    protected void setPagesData(List<Document> documents) {
        pagesData.clear();
        int chunkSize = pageLimit;
        AtomicInteger counter = new AtomicInteger();
        if(chunkSize > 0) {
            documents.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).forEach(this::setPageData);
        } else {
            setPageData(0, documents);
        }

    }


    protected void setPageData(Integer page, List<Document> documents) {
        pagesData.put(page, documents);
    }


}
