package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.Judet;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.Localitate;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.Tara;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.i18n.TableFormI18n;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Ps4ECitizenAccountDetailView extends ContentContainerView<Ps4ECitizenAccountDetailPresenter> {
    private Div ifTblDiv = new Div();
    private Div ifCellImageDiv = new Div();
    private Div ifCellContentDiv = new Div();
    private Div welcomeDiv = new Div();
    private Div dateDiv = new Div();
    private Div tableScroll = new Div();
    private Div pfTitleDiv = new Div();
    private Div parinteTitleDiv = new Div();
    private Image personImage = new Image();
    private TableFormI18n tableForm = new TableFormI18n();
    private TextField nume = new TextField();
    private TextField telefon = new TextField();
    private TextField serieBiCi = new TextField();
    private TextField rj = new TextField();
    private TextField reprezentantLegal = new TextField();
    private TextField codCui = new TextField();
    private TextField cnp = new TextField();
    private TextField numar = new TextField();
    private ComboBox<Tara> tara = new ComboBox<>();
    private ComboBox<Judet> judet = new ComboBox<>();
    private ComboBox<Localitate> localitate = new ComboBox<>();
    private TextField strada = new TextField();
    private TextField stradaNr = new TextField();
    private TextField bloc = new TextField();
    private TextField scara = new TextField();
    private TextField etaj = new TextField();
    private TextField apartament = new TextField();
    private TextField email = new TextField();
    private TextField idTertParinte = new TextField();
    private TextField numeParinte = new TextField();
    private TextField telefonParinte = new TextField();
    private TextField rjParinte = new TextField();
    private TextField reprezentantLegalParinte = new TextField();
    private TextField codCuiParinte = new TextField();
    private ComboBox<Tara> taraParinte = new ComboBox<>();
    private ComboBox<Judet> judetParinte = new ComboBox<>();
    private ComboBox<Localitate> localitateParinte = new ComboBox<>();
    private TextField stradaParinte = new TextField();
    private TextField stradaNrParinte = new TextField();
    private TextField blocParinte = new TextField();
    private TextField scaraParinte = new TextField();
    private TextField etajParinte = new TextField();
    private TextField apartamentParinte = new TextField();
    private TextField emailParinte = new TextField();
    private PasswordField parola = new PasswordField();
    private NativeButton saveButton = new NativeButton();
    private NativeButton manageAccess = new NativeButton();
//    private NativeButton contactePage = new NativeButton();
//    private NativeButton studiiPage = new NativeButton();
    private TextField codPostal = new TextField();
    private TextField codPostalParinte = new TextField();


    @Override
    public void beforeBinding() {
        addServiceListContent();
        setServicesListHeaderFontAwesomeIcon("fas", "fa-user");

        getServiceListContainer().addClassName("my-account-data");

        ifTblDiv.addClassName("if_tbl");
        ifCellImageDiv.addClassNames("if_cell", "image");

        personImage.setSrc("frontend/ps4/ecitizen/assets/images/administreaza_datele.png");

        ifCellImageDiv.add(personImage);

        ifCellContentDiv.addClassNames("if_cell", "content", "pt0");
        ifTblDiv.add(ifCellImageDiv);

        welcomeDiv.addClassNames("welcome", "title_border_bottom2", "mb30");
        welcomeDiv.add(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.detail.page.welcome"));

        dateDiv.addClassName("date");

        ifCellContentDiv.add(welcomeDiv);
        ifCellContentDiv.add(dateDiv);

        tableScroll.addClassName("table_scroll");
        tableForm.addClassNames("table", "table_date_contact");

        pfTitleDiv.addClassName("section_separator_vaadin");
        pfTitleDiv.add(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.detail.title.pf"));

        nume.addClassNames("vaadin-ps4-theme", "invisible_control");

        telefon.addClassNames("vaadin-ps4-theme", "invisible_control");
        codPostal.addClassNames("vaadin-ps4-theme", "invisible_control");

        rj.addClassNames("vaadin-ps4-theme", "invisible_control");

        codCui.addClassNames("vaadin-ps4-theme", "invisible_control");

        cnp.addClassNames("vaadin-ps4-theme", "invisible_control");

        reprezentantLegal.addClassNames("vaadin-ps4-theme", "invisible_control");

        serieBiCi.addClassNames("vaadin-ps4-theme", "invisible_control");

        numar.addClassNames("vaadin-ps4-theme", "invisible_control");

        tara.setItemLabelGenerator(Tara::getDenumire);
        tara.setClearButtonVisible(true);

        tara.addClassNames("vaadin-ps4-theme", "invisible_control");

        judet.setItemLabelGenerator(Judet::getDenumire);
        judet.setClearButtonVisible(true);

        judet.addClassNames("vaadin-ps4-theme", "invisible_control");

        localitate.setItemLabelGenerator(Localitate::getDenumire);
        localitate.setClearButtonVisible(true);

        localitate.addClassNames("vaadin-ps4-theme", "invisible_control");


        strada.addClassNames("vaadin-ps4-theme", "invisible_control");

        stradaNr.addClassNames("vaadin-ps4-theme", "invisible_control");

        bloc.addClassNames("vaadin-ps4-theme", "invisible_control");

        scara.addClassNames("vaadin-ps4-theme", "invisible_control");

        etaj.addClassNames("vaadin-ps4-theme", "invisible_control");

        apartament.addClassNames("vaadin-ps4-theme", "invisible_control");

        email.addClassNames("vaadin-ps4-theme", "invisible_control");

        //parola.addClassNames("vaadin-ps4-theme", "invisible_control");
        //tableForm.addFormRow(parola, "ps4.ecetatean.breadcrumb.myaccount.detail.table.parola");

        //Date tert parinte
        parinteTitleDiv.addClassName("section_separator_vaadin");
        parinteTitleDiv.add(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.detail.title.pj"));

//        ifCellContentDiv.add(parinteTitleDiv);

        idTertParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        numeParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        telefonParinte.addClassNames("vaadin-ps4-theme", "invisible_control");
        codPostalParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        rjParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        codCuiParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        reprezentantLegalParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        taraParinte.setItemLabelGenerator(Tara::getDenumire);
        taraParinte.setClearButtonVisible(true);

        taraParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        judetParinte.setItemLabelGenerator(Judet::getDenumire);
        judetParinte.setClearButtonVisible(true);

        judetParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        localitateParinte.setItemLabelGenerator(Localitate::getDenumire);
        localitateParinte.setClearButtonVisible(true);

        localitateParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        stradaParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        stradaNrParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        blocParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        scaraParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        etajParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        apartamentParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        emailParinte.addClassNames("vaadin-ps4-theme", "invisible_control");

        //BTN MANAGE ACCESS
        manageAccess.addClassNames( "btn", "btn_green", "btn_cogs");
        manageAccess.getElement().getStyle().set("text-align","left");
        manageAccess.getElement().getStyle().set("float","left");
        manageAccess.getElement().getStyle().set("margin-right","10px");
        manageAccess.getElement().getStyle().set("width","auto");
        manageAccess.getElement().getStyle().set("cursor","pointer");

        Label labelManage= new Label("ps4.ecetatean.breadcrumb.myaccount.detail.table.manageAccess");
        labelManage.getElement().getStyle().set("cursor","pointer");

        manageAccess.add(labelManage);
        HtmlContainer manageAccessIcon = new HtmlContainer("i");
        manageAccessIcon.addClassNames("fas", "fa-cogs");
        manageAccess.add(manageAccessIcon);
        manageAccess.addClickListener(new ComponentEventListener<ClickEvent<NativeButton>>() {
            @Override
            public void onComponentEvent(ClickEvent<NativeButton> clickEvent) {
                VaadinClientUrlUtil.setLocation("PORTAL/manage_access.html");
            }
        });
        //END BTN MANAGE ACCESS



        //BTN SAVE DATA
        saveButton.addClassNames("btn", "btn_green");
        Label labelEditData= new Label("ps4.ecetatean.breadcrumb.myaccount.detail.table.editData");
        saveButton.add(labelEditData);
        labelEditData.getElement().getStyle().set("cursor","pointer");
        /*HtmlContainer saveButtonIcon = new HtmlContainer("i");
        saveButtonIcon.addClassNames("fas", "fa-arrow-alt-circle-right");
        saveButton.add(saveButtonIcon);*/
        //END BTN SAVE DATA

        ifTblDiv.add(ifCellContentDiv);
        ifCellContentDiv.add(tableScroll);
        tableScroll.add(tableForm);
        ifCellContentDiv.add(saveButton);
        getServiceListContentContainer().add(ifTblDiv);
    }

    public void setTaraList(List<Tara> taraList) {
        if (taraList != null) {
            tara.setItems(taraList);
        }
    }

    public void setJudetList(List<Judet> judetList) {
        if (judetList != null) {
            judet.setItems(judetList);
        }
    }

    public void setLocalitateList(List<Localitate> localitateList) {
        if (localitateList != null) {
            localitate.setItems(localitateList);
        }
    }

    public void clearJudeteList() {
        judet.setItems(Collections.emptyList());
    }

    public void clearLocalitatiList() {
        localitate.setItems(Collections.emptyList());
    }

    public void setJudet(Judet judet) {
        if (judet != null) {
            this.judet.setValue(judet);
        }
    }

    public void setTara(Tara tara) {
        if (tara != null) {
            this.tara.setValue(tara);
        }
    }

    public void setLocalitate(Localitate localitate) {
        if (localitate != null) {
            this.localitate.setValue(localitate);
        }
    }

    public void clearJudet() {
        judet.setValue(null);
    }

    public void clearLocalitate() {
        localitate.setValue(null);
    }

    public void setTaraParinteList(List<Tara> taraList) {
        if (taraList != null) {
            taraParinte.setItems(taraList);
        }
    }

    public void setJudetParinteList(List<Judet> judetList) {
        if (judetList != null) {
            judetParinte.setItems(judetList);
        }
    }

    public void setLocalitateParinteList(List<Localitate> localitateList) {
        if (localitateList != null) {
            localitateParinte.setItems(localitateList);
        }
    }

    public void clearJudeteParinteList() {
        judetParinte.setItems(Collections.emptyList());
    }

    public void clearLocalitatiParinteList() {
        localitateParinte.setItems(Collections.emptyList());
    }

    public void setJudetParinte(Judet judet) {
        if (judet != null) {
            this.judetParinte.setValue(judet);
        }
    }

    public void setTaraParinte(Tara tara) {
        if (tara != null) {
            this.taraParinte.setValue(tara);
        }
    }

    public void setLocalitateParinte(Localitate localitate) {
        if (localitate != null) {
            this.localitateParinte.setValue(localitate);
        }
    }

    public void clearJudetParinte() {
        judetParinte.setValue(null);
    }

    public void clearLocalitateParinte() {
        localitateParinte.setValue(null);
    }

    public void setPersoanaFizicaJuridicaData(PersoanaFizicaJuridica pfj) {
        StringBuilder sb = new StringBuilder();
        String sep = "";

        if (pfj.getNumeComplet() != null) {
            nume.setValue(pfj.getNumeComplet());
        } else {
            if (pfj.getNume() != null && !pfj.getNume().trim().isEmpty()) {
                sb.append(sep).append(pfj.getNume().trim());
                sep = " ";
            }

            if (pfj.getPrenume() != null && !pfj.getPrenume().trim().isEmpty()) {
                sb.append(sep).append(pfj.getPrenume().trim());
                sep = " ";
            }

            nume.setValue(sb.toString().trim());
        }


        if (pfj.getNrAct() != null)
            numar.setValue(pfj.getNrAct());

        if (pfj.getTelefon() != null)
            telefon.setValue(pfj.getTelefon());

        if (pfj.getCodPostal() != null)
            codPostal.setValue(pfj.getCodPostal());

        if (pfj.getSerieAct() != null)
            serieBiCi.setValue(pfj.getSerieAct());

        if (pfj.getRj() != null)
            rj.setValue(pfj.getRj());

        if (pfj.getCodCui() != null)
            codCui.setValue(pfj.getCodCui());

        if (pfj.getCnp() != null)
            cnp.setValue(pfj.getCnp());

        if (pfj.getReprezentantLegal() != null)
            reprezentantLegal.setValue(pfj.getReprezentantLegal());

        if (pfj.getNrStrada() != null)
            stradaNr.setValue(pfj.getNrStrada());

        if (pfj.getStrada() != null)
            strada.setValue(pfj.getStrada());

        if (pfj.getBloc() != null)
            bloc.setValue(pfj.getBloc());

        if (pfj.getScara() != null)
            scara.setValue(pfj.getScara());

        if (pfj.getEtaj() != null)
            etaj.setValue(pfj.getEtaj());

        if (pfj.getApartament() != null)
            apartament.setValue(pfj.getApartament());

        if (pfj.getEmail() != null)
            email.setValue(pfj.getEmail());

        if (pfj.getIdTertParinte() != null) {
            idTertParinte.setValue(pfj.getIdTertParinte().toString());
        }

        if (pfj.getNumeCompletParinte() != null) {
            numeParinte.setValue(pfj.getNumeCompletParinte());
        }

        if (pfj.getTelefonParinte() != null)
            telefonParinte.setValue(pfj.getTelefonParinte());

        if (pfj.getCodPostalParinte() != null)
            codPostalParinte.setValue(pfj.getCodPostalParinte());

        if (pfj.getRjParinte() != null)
            rjParinte.setValue(pfj.getRjParinte());

        if (pfj.getCodCuiParinte() != null)
            codCuiParinte.setValue(pfj.getCodCuiParinte());

        if (pfj.getReprezentantLegalParinte() != null)
            reprezentantLegalParinte.setValue(pfj.getReprezentantLegalParinte());

        if (pfj.getNrStradaParinte() != null)
            stradaNrParinte.setValue(pfj.getNrStradaParinte());

        if (pfj.getStradaParinte() != null)
            stradaParinte.setValue(pfj.getStradaParinte());

        if (pfj.getBlocParinte() != null)
            blocParinte.setValue(pfj.getBlocParinte());

        if (pfj.getScaraParinte() != null)
            scaraParinte.setValue(pfj.getScaraParinte());

        if (pfj.getEtajParinte() != null)
            etajParinte.setValue(pfj.getEtajParinte());

        if (pfj.getApartamentParinte() != null)
            apartamentParinte.setValue(pfj.getApartamentParinte());

        if (pfj.getEmailParinte() != null)
            emailParinte.setValue(pfj.getEmailParinte());

        if (pfj.getEstePersoanaFizica() != null && pfj.getEstePersoanaFizica().equals("1")) {


            tableForm.addFormRow(nume, "ps4.ecetatean.breadcrumb.myaccount.detail.table.nume");
            tableForm.addFormRow(serieBiCi, "ps4.ecetatean.breadcrumb.myaccount.detail.table.seriebici");
            tableForm.addFormRow(numar, "ps4.ecetatean.breadcrumb.myaccount.detail.table.numar");
            tableForm.addFormRow(cnp, "ps4.ecetatean.breadcrumb.myaccount.detail.table.cnp");
            tableForm.addFormRow(telefon, "ps4.ecetatean.breadcrumb.myaccount.detail.table.telefon");
            tableForm.addFormRow(email, "ps4.ecetatean.breadcrumb.myaccount.detail.table.email");
            tableForm.addFormRow(tara, "ps4.ecetatean.breadcrumb.myaccount.detail.table.tara");
            tableForm.addFormRow(judet, "ps4.ecetatean.breadcrumb.myaccount.detail.table.judet");
            tableForm.addFormRow(localitate, "ps4.ecetatean.breadcrumb.myaccount.detail.table.localitate");
            tableForm.addFormRow(strada, "ps4.ecetatean.breadcrumb.myaccount.detail.table.strada");
            tableForm.addFormRow(stradaNr, "ps4.ecetatean.breadcrumb.myaccount.detail.table.strada.nr");
            tableForm.addFormRow(bloc, "ps4.ecetatean.breadcrumb.myaccount.detail.table.bloc");
            tableForm.addFormRow(scara, "ps4.ecetatean.breadcrumb.myaccount.detail.table.scara");
            tableForm.addFormRow(etaj, "ps4.ecetatean.breadcrumb.myaccount.detail.table.etaj");
            tableForm.addFormRow(apartament, "ps4.ecetatean.breadcrumb.myaccount.detail.table.apartament");
            tableForm.addFormRow(codPostal, "ps4.ecetatean.breadcrumb.myaccount.detail.table.cod_postal");

            if(pfj.getAreParinte() != null && pfj.getAreParinte().equals("1")){

                tableForm.addFormSeparator(parinteTitleDiv);
                tableForm.addFormRow(numeParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.nume");
                tableForm.addFormRow(rjParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.rj");
                tableForm.addFormRow(codCuiParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.codcui");
                tableForm.addFormRow(reprezentantLegalParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.reprezentantlegal");
                tableForm.addFormRow(telefonParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.telefon");
                tableForm.addFormRow(emailParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.email");
                tableForm.addFormRow(taraParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.tara");
                tableForm.addFormRow(judetParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.judet");
                tableForm.addFormRow(localitateParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.localitate");
                tableForm.addFormRow(stradaParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.strada");
                tableForm.addFormRow(stradaNrParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.strada.nr");
                tableForm.addFormRow(blocParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.bloc");
                tableForm.addFormRow(scaraParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.scara");
                tableForm.addFormRow(etajParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.etaj");
                tableForm.addFormRow(apartamentParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.apartament");
                tableForm.addFormRow(codPostalParinte, "ps4.ecetatean.breadcrumb.myaccount.detail.table.cod_postal");

            }

        } else if ( (pfj.getEstePersoanaFizica() == null || pfj.getEstePersoanaFizica().equals("0"))){

            tableForm.addFormRow(new Div(manageAccess), "ps4.ecetatean.breadcrumb.myaccount.detail.table.actions");

            tableForm.addFormRow(nume, "ps4.ecetatean.breadcrumb.myaccount.detail.table.nume");
            tableForm.addFormRow(rj, "ps4.ecetatean.breadcrumb.myaccount.detail.table.rj");
            tableForm.addFormRow(codCui, "ps4.ecetatean.breadcrumb.myaccount.detail.table.codcui");
            tableForm.addFormRow(reprezentantLegal, "ps4.ecetatean.breadcrumb.myaccount.detail.table.reprezentantlegal");
            tableForm.addFormRow(telefon, "ps4.ecetatean.breadcrumb.myaccount.detail.table.telefon");
            tableForm.addFormRow(email, "ps4.ecetatean.breadcrumb.myaccount.detail.table.email");
            tableForm.addFormRow(tara, "ps4.ecetatean.breadcrumb.myaccount.detail.table.tara");
            tableForm.addFormRow(judet, "ps4.ecetatean.breadcrumb.myaccount.detail.table.judet");
            tableForm.addFormRow(localitate, "ps4.ecetatean.breadcrumb.myaccount.detail.table.localitate");
            tableForm.addFormRow(strada, "ps4.ecetatean.breadcrumb.myaccount.detail.table.strada");
            tableForm.addFormRow(stradaNr, "ps4.ecetatean.breadcrumb.myaccount.detail.table.strada.nr");
            tableForm.addFormRow(bloc, "ps4.ecetatean.breadcrumb.myaccount.detail.table.bloc");
            tableForm.addFormRow(scara, "ps4.ecetatean.breadcrumb.myaccount.detail.table.scara");
            tableForm.addFormRow(etaj, "ps4.ecetatean.breadcrumb.myaccount.detail.table.etaj");
            tableForm.addFormRow(apartament, "ps4.ecetatean.breadcrumb.myaccount.detail.table.apartament");
            tableForm.addFormRow(codPostal, "ps4.ecetatean.breadcrumb.myaccount.detail.table.cod_postal");



        } else {
            tableForm.addFormRow(nume, "ps4.ecetatean.breadcrumb.myaccount.detail.table.nume");
            tableForm.addFormRow(email, "ps4.ecetatean.breadcrumb.myaccount.detail.table.email");
        }

    }

    public PersoanaFizicaJuridica getPersoanaFizicaJuridicaData(Integer idUser, Boolean estePfj, Boolean areParinte) {
        PersoanaFizicaJuridica pfj = new PersoanaFizicaJuridica();
        pfj.setEstePersoanaFizica(estePfj != null && estePfj ? "1" : "0");
        pfj.setIdUtilizator(idUser);
        pfj.setId(idUser);

        if (nume.getValue() != null) {
            StringTokenizer stok = new StringTokenizer(nume.getValue(), " ");

            // numele e prima valoarea din casuta de text
            if (stok.hasMoreTokens()) {
                String token = stok.nextToken();
                if (token != null && !token.trim().isEmpty()) {
                    pfj.setNume(token);
                }
            }

            // prenumele e restul din casuta de text indiferent cate
            StringBuilder sb = new StringBuilder();
            String sep = "";
            boolean found = false;

            while (stok.hasMoreTokens()) {
                String token = stok.nextToken();
                if (token != null && !token.trim().isEmpty()) {
                    sb.append(sep).append(token);
                    sep = " ";
                    found = true;
                }
            }

            if (found) {
                pfj.setPrenume(sb.toString().trim());
            }
        }

        pfj.setTelefon(telefon.getValue());
        pfj.setCodPostal(codPostal.getValue());
        pfj.setSerieAct(serieBiCi.getValue());
        pfj.setCnp(cnp.getValue());
        pfj.setNrAct(numar.getValue());
        pfj.setStrada(strada.getValue());
        pfj.setNrStrada(stradaNr.getValue());
        pfj.setBloc(bloc.getValue());
        pfj.setScara(scara.getValue());
        pfj.setEtaj(etaj.getValue());
        pfj.setApartament(apartament.getValue());
        pfj.setEmail(email.getValue());
        pfj.setCodCui(codCui.getValue());
        pfj.setReprezentantLegal(reprezentantLegal.getValue());
        pfj.setRj(rj.getValue());


//        private PasswordField parola = new PasswordField();

        if (tara.getValue() != null) {
            Tara taraO = tara.getValue();
            pfj.setIdTara(taraO.getId());
        }

        if (judet.getValue() != null) {
            Judet judetO = judet.getValue();
            pfj.setIdJudet(judetO.getId());
        }

        if (localitate.getValue() != null) {
            Localitate localitateO = localitate.getValue();
            pfj.setIdLocalitate(localitateO.getId());
        }


        //campuri tert parinte
        if(areParinte) {
            pfj.setAreParinte("1");
            try {
                pfj.setIdTertParinte(Integer.parseInt(idTertParinte.getValue()));
            } catch (Exception e) {

            }
            pfj.setNumeCompletParinte(numeParinte.getValue());
            pfj.setRjParinte(rjParinte.getValue());
            pfj.setCodCuiParinte(codCuiParinte.getValue());
            pfj.setReprezentantLegalParinte(reprezentantLegalParinte.getValue());
            pfj.setTelefonParinte(telefonParinte.getValue());
            pfj.setCodPostalParinte(codPostalParinte.getValue());
            pfj.setEmailParinte(emailParinte.getValue());
            pfj.setStradaParinte(stradaParinte.getValue());
            pfj.setNrStradaParinte(stradaNrParinte.getValue());
            pfj.setBlocParinte(blocParinte.getValue());
            pfj.setScaraParinte(scaraParinte.getValue());
            pfj.setEtajParinte(etajParinte.getValue());
            pfj.setApartamentParinte(apartamentParinte.getValue());


        if (taraParinte.getValue() != null) {
            Tara taraO = taraParinte.getValue();
            pfj.setIdTaraParinte(taraO.getId());
        }

        if (judetParinte.getValue() != null) {
            Judet judetO = judetParinte.getValue();
            pfj.setIdJudetParinte(judetO.getId());
        }

        if (localitateParinte.getValue() != null) {
            Localitate localitateO = localitateParinte.getValue();
            pfj.setIdLocalitateParinte(localitateO.getId());
        }

        }

        return pfj;
    }

    public void clearJudetAndTariOnTaraChange() {
        clearJudet();
        clearLocalitate();
        clearJudeteList();
        clearLocalitatiList();
    }

    public Image getPersonImage() {
        return personImage;
    }

    public TextField getNume() {
        return nume;
    }

    public TextField getTelefon() {
        return telefon;
    }

    public TextField getCodPostal() {
        return codPostal;
    }

    public TextField getCodPostalParinte() {
        return codPostalParinte;
    }

    public TextField getSerieBiCi() {
        return serieBiCi;
    }

    public TextField getNumar() {
        return numar;
    }

    public ComboBox<Tara> getTara() {
        return tara;
    }

    public ComboBox<Judet> getJudet() {
        return judet;
    }

    public ComboBox<Localitate> getLocalitate() {
        return localitate;
    }

    public TextField getStrada() {
        return strada;
    }

    public TextField getStradaNr() {
        return stradaNr;
    }

    public TextField getBloc() {
        return bloc;
    }

    public TextField getScara() {
        return scara;
    }

    public TextField getEtaj() {
        return etaj;
    }

    public TextField getApartament() {
        return apartament;
    }

    public TextField getEmail() {
        return email;
    }

    public PasswordField getParola() {
        return parola;
    }
}
