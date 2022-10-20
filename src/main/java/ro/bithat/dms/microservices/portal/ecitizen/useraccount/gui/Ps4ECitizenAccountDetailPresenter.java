package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.NativeButton;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsNomenclatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.*;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.ComponentValueChangeEventPresenterMethod;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;

public class Ps4ECitizenAccountDetailPresenter extends PrepareModelFlowPresenter<Ps4ECitizenAccountDetailView> {
    @Autowired
    DmswsNomenclatorService dmswsNomenclatorService;

    @Autowired
    DmswsUtilizatorService dmswsUtilizatorService;

    private Boolean estePfj = false;
    private Boolean areParinte = false;

    @Override
    public void prepareModel(String state) {
        getView().setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.detail.table.title"));

        PersoanaFizicaJuridica pfj = dmswsUtilizatorService.getPersoanaFizicaJuridica(SecurityUtils.getToken(), SecurityUtils.getUserId().intValue());
        if (pfj == null) {
            getLogger().error("Cannot read person.");
            throw new RuntimeException("Cannot read person.");
        }

        estePfj = pfj.getEstePersoanaFizica() != null && pfj.getEstePersoanaFizica().trim().toLowerCase().equals("1");
        areParinte = pfj.getAreParinte() != null && pfj.getAreParinte().trim().toLowerCase().equals("1");

        getView().setPersoanaFizicaJuridicaData(pfj);

        List<Tara> taraList = getTaraList(SecurityUtils.getToken());
        List<Judet> judetList = getJudetList(SecurityUtils.getToken(), pfj.getIdTara());
        List<Localitate> localitateList = getLocaliateJudetList(SecurityUtils.getToken(), pfj.getIdJudet());

        getView().setTaraList(taraList);
        getView().setJudetList(judetList);
        getView().setLocalitateList(localitateList);

        getView().setTaraParinteList(taraList);
        getView().setJudetParinteList(judetList);
        getView().setLocalitateParinteList(localitateList);

        setViewTara(taraList, pfj.getIdTara());
        setViewJudet(judetList, pfj.getIdJudet());
        setViewLocalitate(localitateList, pfj.getIdLocalitate());

        setViewTaraParinte(taraList, pfj.getIdTaraParinte());
        setViewJudetParinte(judetList, pfj.getIdJudetParinte());
        setViewLocalitateParinte(localitateList, pfj.getIdLocalitateParinte());

        System.out.println("test");
    }

    @ComponentValueChangeEventPresenterMethod(viewProperty = "tara")
    public void onTaraChangeListener(AbstractField.ComponentValueChangeEvent<ComboBox, Tara> textChangeEvent) {
        Tara tara = textChangeEvent.getValue();
        getView().clearJudetAndTariOnTaraChange();

        if (tara != null) {
            List<Judet> judetList = getJudetList(SecurityUtils.getToken(), tara.getId());
            List<Localitate> localitateList = getLocaliateList(SecurityUtils.getToken(), tara.getId());

            getView().setJudetList(judetList);
            getView().setLocalitateList(localitateList);
        }
    }

    @ComponentValueChangeEventPresenterMethod(viewProperty = "judet")
    public void onJudetChangeListener(AbstractField.ComponentValueChangeEvent<ComboBox, Judet> textChangeEvent) {
        Judet judet = textChangeEvent.getValue();
        if (judet != null) {
            List<Localitate> localitateList = getLocaliateJudetList(SecurityUtils.getToken(), judet.getId());

            getView().setLocalitateList(localitateList);
            setViewLocalitate(localitateList, judet.getId());

        }
    }

    @ComponentValueChangeEventPresenterMethod(viewProperty = "taraParinte")
    public void onTaraParinteChangeListener(AbstractField.ComponentValueChangeEvent<ComboBox, Tara> textChangeEvent) {
        Tara tara = textChangeEvent.getValue();
        getView().clearJudetAndTariOnTaraChange();

        if (tara != null) {
            List<Judet> judetList = getJudetList(SecurityUtils.getToken(), tara.getId());
            List<Localitate> localitateList = getLocaliateList(SecurityUtils.getToken(), tara.getId());

            getView().setJudetParinteList(judetList);
            getView().setLocalitateParinteList(localitateList);
        }
    }

    @ComponentValueChangeEventPresenterMethod(viewProperty = "judetParinte")
    public void onJudetParinteChangeListener(AbstractField.ComponentValueChangeEvent<ComboBox, Judet> textChangeEvent) {
        Judet judet = textChangeEvent.getValue();
        if (judet != null) {
            List<Localitate> localitateList = getLocaliateJudetList(SecurityUtils.getToken(), judet.getId());

            getView().setLocalitateParinteList(localitateList);
            setViewLocalitateParinte(localitateList, judet.getId());

        }
    }

    @ClickEventPresenterMethod(viewProperty = "saveButton")
    public void onSaveButtonClicked(ClickEvent<NativeButton> clickEvent) {
        PersoanaFizicaJuridica persoanaFizicaJuridica = getView().getPersoanaFizicaJuridicaData(SecurityUtils.getUserId().intValue(), estePfj, areParinte);
        dmswsUtilizatorService.updatePersoanaFizicaJuridica(SecurityUtils.getToken(), SecurityUtils.getUserId().intValue(), persoanaFizicaJuridica);
        UI.getCurrent().getPage().executeJs("swalInfo($0)", "Datele au fost salvate cu succes!");
    }

    private void setViewTara(List<Tara> taraList, Integer idTara) {
        if (taraList != null && idTara != null) {
            for (Tara tara : taraList) {
                if (tara.getId() != null && tara.getId().equals(idTara)) {
                    getView().setTara(tara);
                }
            }
        }
    }

    private void setViewJudet(List<Judet> judetList, Integer idJudet) {
        if (judetList != null && idJudet != null) {
            for (Judet judet : judetList) {
                if (judet.getId() != null && judet.getId().equals(idJudet)) {
                    getView().setJudet(judet);
                }
            }
        }
    }

    private void setViewLocalitate(List<Localitate> localitateList, Integer idLocalitate) {
        if (localitateList != null && idLocalitate != null) {
            for (Localitate localitate : localitateList) {
                if (localitate.getId() != null && localitate.getId().equals(idLocalitate)) {
                    getView().setLocalitate(localitate);
                }
            }
        }
    }

    private void setViewTaraParinte(List<Tara> taraList, Integer idTara) {
        if (taraList != null && idTara != null) {
            for (Tara tara : taraList) {
                if (tara.getId() != null && tara.getId().equals(idTara)) {
                    getView().setTaraParinte(tara);
                }
            }
        }
    }

    private void setViewJudetParinte(List<Judet> judetList, Integer idJudet) {
        if (judetList != null && idJudet != null) {
            for (Judet judet : judetList) {
                if (judet.getId() != null && judet.getId().equals(idJudet)) {
                    getView().setJudetParinte(judet);
                }
            }
        }
    }

    private void setViewLocalitateParinte(List<Localitate> localitateList, Integer idLocalitate) {
        if (localitateList != null && idLocalitate != null) {
            for (Localitate localitate : localitateList) {
                if (localitate.getId() != null && localitate.getId().equals(idLocalitate)) {
                    getView().setLocalitateParinte(localitate);
                }
            }
        }
    }

    public List<Tara> getTaraList(String token) {
        List<Tara> ret = null;

        TaraList taraList = dmswsNomenclatorService.getInfoTara(token);
        if (taraList != null && taraList.getTaraList() != null) {
            ret = taraList.getTaraList();
        }

        return ret;
    }

    public List<Judet> getJudetList(String token, Integer idTara) {
        List<Judet> ret = null;

        if (idTara != null) {
            JudetList judetList = dmswsNomenclatorService.getInfoJudet(token, String.valueOf(idTara));
            if (judetList != null && judetList.getJudetList() != null) {
                ret = judetList.getJudetList();
            }
        }

        return ret;
    }

    public List<Localitate> getLocaliateList(String token, Integer idTara) {
        List<Localitate> ret = null;

        if (idTara != null) {
            LocalitateList localitateList = dmswsNomenclatorService.getInfoLocalitate(token, String.valueOf(idTara));
            if (localitateList != null && localitateList.getLocalitateList() != null) {
                ret = localitateList.getLocalitateList();
            }
        }

        return ret;
    }

    public List<Localitate> getLocaliateJudetList(String token, Integer idJudet) {
        List<Localitate> ret = null;

        if (idJudet != null) {
            LocalitateList localitateList = dmswsNomenclatorService.getLocalitatiJudet(token, String.valueOf(idJudet));
            if (localitateList != null && localitateList.getLocalitateList() != null) {
                ret = localitateList.getLocalitateList();
            }
        }

        return ret;
    }
}
