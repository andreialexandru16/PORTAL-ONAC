package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza attribute link.
 */
@XmlRootElement
public class RegistraturaComplete extends BaseModel {

    private Integer id;
    private String nr;
    private String tert_extern;
    private String tert_extern_localitate;
    private String tert_extern_judet;
    private String tert_extern_telefon;

    private String file_name;
    private String data;
    private String nume_document;
    private Integer id_fisier;
    private Integer id_tip_document;
    private String tip;
    private String de_la;
    private String catre;
    private String creat_de;
    private String creat_la;
    private String modificat_de;
    private String modificat_la;
    private Integer id_registru;
    private Integer intrare;
    private Integer iesire;
    private String emitent_nr;
    private String emitent_data;
    private String mod_provenienta;
    private Integer id_unitate;
    private String info1;
    private String info2;
    private String info3;
    private String info4;
    private String info5;
    private String info6;
    private String info7;
    private String info8;
    private String info9;
    private String info10;
    private Integer id_departament;
    private Integer id_persoana;
    private Integer id_tert;
    private Integer id_contact;
    private Integer register_iesire;
    private String procesare;
    private Integer nr_pagini;
    private String nr_generat;
    private Integer id_termen_pastrare;
    private Integer id_flux;
    private String din_pagina;
    private Integer id_ref_number;
    private String id_departament_str;
    private String id_persoana_str;
    private Integer predat;
    private Integer predat_catre;
    private Integer iesit;
    private String termen_r;
    private Integer id_nivel_org;
    private String nr_intrare_sesizare;
    private String data_intr_sesizare;
    private Integer id_cat_sesizare;
    private Integer id_desc_sesizare;
    private Integer id_tip_sesizare;
    private Integer id_mod_rez;
    private String data_pnc_vedere;
    private String data_rsp_prel;
    private String data_rsp_fin;
    private String data_info_s;
    private String mod_sesizare;
    private Integer id_referinta;
    private String data_info_s_init;
    private String data_pnc_vedere_init;
    private String data_rsp_fin_init;
    private String data_rsp_prel_init;
    private String client_special;
    private String nr_rsp_prel;
    private String nr_rsp_fin;
    private String nr_info_s;
    private String nr_pnc_vedere;
    private Integer nr_anexe;
    private String termen_legal_zile;
    private Integer secret_serviciu;
    private String obs;
    private String rowid;
    private String info11;
    private String info12;
    private String info13;
    private String info14;
    private String info15;
    private String info16;
    private String info17;
    private String info18;
    private String info19;
    private String info20;
    private String info21;
    private String info22;
    private String info23;
    private String info24;
    private String info25;
    private String data_alerta;
    private String data_alerta_iesire;
    private String uuid_electronic;
    private String id_grup_str;
    private Integer id_departament_user;
    private Integer id_departament_secretariat;
    private Integer id_tert_extern;
    private String block_trigger;
    private Integer id_categ_registry;
    private String categ_registry_str;
    private String categ_registry_str_names;
    private Integer id_categorie_securitate;
    private String data_doc;
    private String data_intrare_in_vigoare;
    private String data_limita_rezolvare;
    private String data_valabilitate;
    private Integer id_tip_prioritate;
    private Integer id_subcateg_registry;
    private String info26;
    private String info27;
    private String info28;
    private String info29;
    private String info30;
    private String info31;
    private String info32;
    private String info33;
    private String info34;
    private String info35;
    private String info36;
    private String info37;
    private String info38;
    private String info39;
    private String info40;
    private String info41;
    private String info42;
    private String info43;
    private String info44;
    private String info45;
    private String info46;
    private String info47;
    private String info48;
    private String info49;
    private String info50;
    private String finalizat_text;
    private String nota_rezolutie_extern;
    private Integer zile_alerta;
    private Integer nr_zile_valabilitate;
    private Integer durata_nelimitata;
    private String id_persoana_read_str_names;
    private Integer nr_acte_aditionale;
    private Integer nr_contracte;
    private String nr_doc;
    private String perioada_valabilitate;
    private Integer respinse;
    private String subcateg_registry_str;
    private String subcateg_registry_str_names;
    private String tert_str;
    private String tert_str2;
    private String tert_str2_names;
    private String tert_str_names;
    private String tip_doc;
    private String mod_expediere;
    private Integer id_tip_document_iesire;
    private String finalizat_la;
    private String finalizat_de;
    private Integer cota;
    private Integer cota_tematica;
    private String id_referinte_multiple;
    private String id_referinte_multiple_names;
    private String lang;
    private Integer id_persoana_excl;
    private String id_persoana_excl_str;
    private Integer id_departament_master;
    private String id_departament_master_str;
    private Integer id_punct_lucru;
    private String id_unitate_logistica;
    private String data_interna;
    private String valoare_initiala;
    private String descriere_fisier;
    private String commands_tert;
    private String tip_document;
    private String departament_user;
    private String tert;
    private String referinta;
    private String unitati_logistice;
    private String contact;
    private String termen;
    private String id_flux_def;
    private String termen_raspuns_preliminar;
    private String numar_raspuns_preliminar;
    private String data_raspuns_preliminar;
    private String termen_informare;
    private String numar_informare;
    private String data_informare;
    private String termen_punct_vedere;
    private String numar_punct_vedere;
    private String data_punct_vedere;
    private String termen_raspuns_final;
    private String numar_raspuns_final;
    private String data_raspuns_final;
    private String departament_secretariat;
    private String note_rezolutie_extern;
    private String stadiu_registratura;
    private String commands_gen_cote;
    private String categ_registry;
    private String subcateg_registry;
    private String tert_codatm;
    private String tert_tip_client;
    private String tert_nlc;
    private String tert_adresa;
    private String tert_email;
    private String tert_cod_fiscal;
    private String tert_localitate;
    private String tert_cnp;
    private String tert_tara;
    private String tert_scara;
    private String tert_etaj;
    private String tert_judet;
    private String tert_numar;
    private String tert_strada;
    private String tert_apartament;
    private String tert_bloc;
    private String tert_mama;
    private String tert_tata;
    private String tert_prenume;
    private String tert_data_nastere;
    private String tert_cod_sap;
    private String tert_telefon;
    private String tip_prioritate;
    private String id_persoana_read_str;
    private String count_nr_anexe;
    private Double valoare;
    private Double procent_tva;
    private String count_nr_contracte;
    private Double valoare_tva;
    private Double valoare_cu_tva;
    private String moneda;
    private String departament;
    private String persoana;
    private String prefix;
    private String nota_rezolutie;
    private Integer predat_de;
    private String predat_la;
    private String mesaj;
    private String client_intern;
    private String persoana_excl;
    private String departament_master;
    private String descriere;
    private String nr_intrare;
    private String categorie;
    private String tip_petitie;
    private String tip_sesizare;
    private String nivel_organizational;
    private String mod_rezolvare;
    private String mod_rezolvare_reg;
    private String iesit_descriere;
    private String iesit_catre;
    private String iesit_data;
    private String nr_conexare;
    private String stare_reg;
    private String indicativ_dosar_arh;
    private String anulat;
    private String motiv_anulare;
    private String data_anulare;
    private String categorie_securitate;
    private String downloadLink;
    private String displayMode;
    private String fisierDownload;
    private String info9Str;

    public String getTert_telefon() {
        return tert_telefon;
    }

    public void setTert_telefon(String tert_telefon) {
        this.tert_telefon = tert_telefon;
    }

    public String getInfo9Str() {
        return info9Str;
    }

    public void setInfo9Str(String info9Str) {
        this.info9Str = info9Str;
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public Integer getRegister_iesire() {
        return register_iesire;
    }

    public void setRegister_iesire(Integer register_iesire) {
        this.register_iesire = register_iesire;
    }

    public Integer getZile_alerta() {
        return zile_alerta;
    }

    public void setZile_alerta(Integer zile_alerta) {
        this.zile_alerta = zile_alerta;
    }

    public Integer getNr_anexe() {
        return nr_anexe;
    }

    public void setNr_anexe(Integer nr_anexe) {
        this.nr_anexe = nr_anexe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getTert_extern() {
        return tert_extern;
    }

    public void setTert_extern(String tert_extern) {
        this.tert_extern = tert_extern;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNume_document() {
        return nume_document;
    }

    public void setNume_document(String nume_document) {
        this.nume_document = nume_document;
    }

    public Integer getId_fisier() {
        return id_fisier;
    }

    public void setId_fisier(Integer id_fisier) {
        this.id_fisier = id_fisier;
    }

    public Integer getId_tip_document() {
        return id_tip_document;
    }

    public void setId_tip_document(Integer id_tip_document) {
        this.id_tip_document = id_tip_document;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDe_la() {
        return de_la;
    }

    public void setDe_la(String de_la) {
        this.de_la = de_la;
    }

    public String getCatre() {
        return catre;
    }

    public void setCatre(String catre) {
        this.catre = catre;
    }

    public String getCreat_de() {
        return creat_de;
    }

    public void setCreat_de(String creat_de) {
        this.creat_de = creat_de;
    }

    public String getCreat_la() {
        return creat_la;
    }

    public void setCreat_la(String creat_la) {
        this.creat_la = creat_la;
    }

    public String getModificat_de() {
        return modificat_de;
    }

    public void setModificat_de(String modificat_de) {
        this.modificat_de = modificat_de;
    }

    public String getModificat_la() {
        return modificat_la;
    }

    public void setModificat_la(String modificat_la) {
        this.modificat_la = modificat_la;
    }

    public Integer getId_registru() {
        return id_registru;
    }

    public void setId_registru(Integer id_registru) {
        this.id_registru = id_registru;
    }

    public Integer getIntrare() {
        return intrare;
    }

    public void setIntrare(Integer intrare) {
        this.intrare = intrare;
    }

    public Integer getIesire() {
        return iesire;
    }

    public void setIesire(Integer iesire) {
        this.iesire = iesire;
    }

    public String getEmitent_nr() {
        return emitent_nr;
    }

    public void setEmitent_nr(String emitent_nr) {
        this.emitent_nr = emitent_nr;
    }

    public String getEmitent_data() {
        return emitent_data;
    }

    public void setEmitent_data(String emitent_data) {
        this.emitent_data = emitent_data;
    }

    public String getMod_provenienta() {
        return mod_provenienta;
    }

    public void setMod_provenienta(String mod_provenienta) {
        this.mod_provenienta = mod_provenienta;
    }

    public Integer getId_unitate() {
        return id_unitate;
    }

    public void setId_unitate(Integer id_unitate) {
        this.id_unitate = id_unitate;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public String getInfo6() {
        return info6;
    }

    public void setInfo6(String info6) {
        this.info6 = info6;
    }

    public String getInfo7() {
        return info7;
    }

    public void setInfo7(String info7) {
        this.info7 = info7;
    }

    public String getInfo8() {
        return info8;
    }

    public void setInfo8(String info8) {
        this.info8 = info8;
    }

    public String getInfo9() {
        return info9;
    }

    public void setInfo9(String info9) {
        this.info9 = info9;
    }

    public String getInfo10() {
        return info10;
    }

    public void setInfo10(String info10) {
        this.info10 = info10;
    }

    public Integer getId_departament() {
        return id_departament;
    }

    public void setId_departament(Integer id_departament) {
        this.id_departament = id_departament;
    }

    public Integer getId_persoana() {
        return id_persoana;
    }

    public void setId_persoana(Integer id_persoana) {
        this.id_persoana = id_persoana;
    }

    public Integer getId_tert() {
        return id_tert;
    }

    public void setId_tert(Integer id_tert) {
        this.id_tert = id_tert;
    }

    public Integer getId_contact() {
        return id_contact;
    }

    public void setId_contact(Integer id_contact) {
        this.id_contact = id_contact;
    }


    public String getProcesare() {
        return procesare;
    }

    public void setProcesare(String procesare) {
        this.procesare = procesare;
    }

    public Integer getNr_pagini() {
        return nr_pagini;
    }

    public void setNr_pagini(Integer nr_pagini) {
        this.nr_pagini = nr_pagini;
    }

    public String getNr_generat() {
        return nr_generat;
    }

    public void setNr_generat(String nr_generat) {
        this.nr_generat = nr_generat;
    }

    public Integer getId_termen_pastrare() {
        return id_termen_pastrare;
    }

    public void setId_termen_pastrare(Integer id_termen_pastrare) {
        this.id_termen_pastrare = id_termen_pastrare;
    }

    public Integer getId_flux() {
        return id_flux;
    }

    public void setId_flux(Integer id_flux) {
        this.id_flux = id_flux;
    }

    public String getDin_pagina() {
        return din_pagina;
    }

    public void setDin_pagina(String din_pagina) {
        this.din_pagina = din_pagina;
    }

    public Integer getId_ref_number() {
        return id_ref_number;
    }

    public void setId_ref_number(Integer id_ref_number) {
        this.id_ref_number = id_ref_number;
    }

    public String getId_departament_str() {
        return id_departament_str;
    }

    public void setId_departament_str(String id_departament_str) {
        this.id_departament_str = id_departament_str;
    }

    public String getId_persoana_str() {
        return id_persoana_str;
    }

    public void setId_persoana_str(String id_persoana_str) {
        this.id_persoana_str = id_persoana_str;
    }

    public Integer getPredat() {
        return predat;
    }

    public void setPredat(Integer predat) {
        this.predat = predat;
    }

    public Integer getPredat_catre() {
        return predat_catre;
    }

    public void setPredat_catre(Integer predat_catre) {
        this.predat_catre = predat_catre;
    }

    public Integer getIesit() {
        return iesit;
    }

    public void setIesit(Integer iesit) {
        this.iesit = iesit;
    }

    public String getTermen_r() {
        return termen_r;
    }

    public void setTermen_r(String termen_r) {
        this.termen_r = termen_r;
    }

    public Integer getId_nivel_org() {
        return id_nivel_org;
    }

    public void setId_nivel_org(Integer id_nivel_org) {
        this.id_nivel_org = id_nivel_org;
    }

    public String getNr_intrare_sesizare() {
        return nr_intrare_sesizare;
    }

    public void setNr_intrare_sesizare(String nr_intrare_sesizare) {
        this.nr_intrare_sesizare = nr_intrare_sesizare;
    }

    public String getData_intr_sesizare() {
        return data_intr_sesizare;
    }

    public void setData_intr_sesizare(String data_intr_sesizare) {
        this.data_intr_sesizare = data_intr_sesizare;
    }

    public Integer getId_cat_sesizare() {
        return id_cat_sesizare;
    }

    public void setId_cat_sesizare(Integer id_cat_sesizare) {
        this.id_cat_sesizare = id_cat_sesizare;
    }

    public Integer getId_desc_sesizare() {
        return id_desc_sesizare;
    }

    public void setId_desc_sesizare(Integer id_desc_sesizare) {
        this.id_desc_sesizare = id_desc_sesizare;
    }

    public Integer getId_tip_sesizare() {
        return id_tip_sesizare;
    }

    public void setId_tip_sesizare(Integer id_tip_sesizare) {
        this.id_tip_sesizare = id_tip_sesizare;
    }

    public Integer getId_mod_rez() {
        return id_mod_rez;
    }

    public void setId_mod_rez(Integer id_mod_rez) {
        this.id_mod_rez = id_mod_rez;
    }

    public String getData_pnc_vedere() {
        return data_pnc_vedere;
    }

    public void setData_pnc_vedere(String data_pnc_vedere) {
        this.data_pnc_vedere = data_pnc_vedere;
    }

    public String getData_rsp_prel() {
        return data_rsp_prel;
    }

    public void setData_rsp_prel(String data_rsp_prel) {
        this.data_rsp_prel = data_rsp_prel;
    }

    public String getData_rsp_fin() {
        return data_rsp_fin;
    }

    public void setData_rsp_fin(String data_rsp_fin) {
        this.data_rsp_fin = data_rsp_fin;
    }

    public String getData_info_s() {
        return data_info_s;
    }

    public void setData_info_s(String data_info_s) {
        this.data_info_s = data_info_s;
    }

    public String getMod_sesizare() {
        return mod_sesizare;
    }

    public void setMod_sesizare(String mod_sesizare) {
        this.mod_sesizare = mod_sesizare;
    }

    public Integer getId_referinta() {
        return id_referinta;
    }

    public void setId_referinta(Integer id_referinta) {
        this.id_referinta = id_referinta;
    }

    public String getData_info_s_init() {
        return data_info_s_init;
    }

    public void setData_info_s_init(String data_info_s_init) {
        this.data_info_s_init = data_info_s_init;
    }

    public String getData_pnc_vedere_init() {
        return data_pnc_vedere_init;
    }

    public void setData_pnc_vedere_init(String data_pnc_vedere_init) {
        this.data_pnc_vedere_init = data_pnc_vedere_init;
    }

    public String getData_rsp_fin_init() {
        return data_rsp_fin_init;
    }

    public void setData_rsp_fin_init(String data_rsp_fin_init) {
        this.data_rsp_fin_init = data_rsp_fin_init;
    }

    public String getData_rsp_prel_init() {
        return data_rsp_prel_init;
    }

    public void setData_rsp_prel_init(String data_rsp_prel_init) {
        this.data_rsp_prel_init = data_rsp_prel_init;
    }

    public String getClient_special() {
        return client_special;
    }

    public void setClient_special(String client_special) {
        this.client_special = client_special;
    }

    public String getNr_rsp_prel() {
        return nr_rsp_prel;
    }

    public void setNr_rsp_prel(String nr_rsp_prel) {
        this.nr_rsp_prel = nr_rsp_prel;
    }

    public String getNr_rsp_fin() {
        return nr_rsp_fin;
    }

    public void setNr_rsp_fin(String nr_rsp_fin) {
        this.nr_rsp_fin = nr_rsp_fin;
    }

    public String getNr_info_s() {
        return nr_info_s;
    }

    public void setNr_info_s(String nr_info_s) {
        this.nr_info_s = nr_info_s;
    }

    public String getNr_pnc_vedere() {
        return nr_pnc_vedere;
    }

    public void setNr_pnc_vedere(String nr_pnc_vedere) {
        this.nr_pnc_vedere = nr_pnc_vedere;
    }


    public String getTermen_legal_zile() {
        return termen_legal_zile;
    }

    public void setTermen_legal_zile(String termen_legal_zile) {
        this.termen_legal_zile = termen_legal_zile;
    }

    public Integer getSecret_serviciu() {
        return secret_serviciu;
    }

    public void setSecret_serviciu(Integer secret_serviciu) {
        this.secret_serviciu = secret_serviciu;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getInfo11() {
        return info11;
    }

    public void setInfo11(String info11) {
        this.info11 = info11;
    }

    public String getInfo12() {
        return info12;
    }

    public void setInfo12(String info12) {
        this.info12 = info12;
    }

    public String getInfo13() {
        return info13;
    }

    public void setInfo13(String info13) {
        this.info13 = info13;
    }

    public String getInfo14() {
        return info14;
    }

    public void setInfo14(String info14) {
        this.info14 = info14;
    }

    public String getInfo15() {
        return info15;
    }

    public void setInfo15(String info15) {
        this.info15 = info15;
    }

    public String getInfo16() {
        return info16;
    }

    public void setInfo16(String info16) {
        this.info16 = info16;
    }

    public String getInfo17() {
        return info17;
    }

    public void setInfo17(String info17) {
        this.info17 = info17;
    }

    public String getInfo18() {
        return info18;
    }

    public void setInfo18(String info18) {
        this.info18 = info18;
    }

    public String getInfo19() {
        return info19;
    }

    public void setInfo19(String info19) {
        this.info19 = info19;
    }

    public String getInfo20() {
        return info20;
    }

    public void setInfo20(String info20) {
        this.info20 = info20;
    }

    public String getInfo21() {
        return info21;
    }

    public void setInfo21(String info21) {
        this.info21 = info21;
    }

    public String getInfo22() {
        return info22;
    }

    public void setInfo22(String info22) {
        this.info22 = info22;
    }

    public String getInfo23() {
        return info23;
    }

    public void setInfo23(String info23) {
        this.info23 = info23;
    }

    public String getInfo24() {
        return info24;
    }

    public void setInfo24(String info24) {
        this.info24 = info24;
    }

    public String getInfo25() {
        return info25;
    }

    public void setInfo25(String info25) {
        this.info25 = info25;
    }

    public String getData_alerta() {
        return data_alerta;
    }

    public void setData_alerta(String data_alerta) {
        this.data_alerta = data_alerta;
    }

    public String getData_alerta_iesire() {
        return data_alerta_iesire;
    }

    public void setData_alerta_iesire(String data_alerta_iesire) {
        this.data_alerta_iesire = data_alerta_iesire;
    }

    public String getUuid_electronic() {
        return uuid_electronic;
    }

    public void setUuid_electronic(String uuid_electronic) {
        this.uuid_electronic = uuid_electronic;
    }

    public String getId_grup_str() {
        return id_grup_str;
    }

    public void setId_grup_str(String id_grup_str) {
        this.id_grup_str = id_grup_str;
    }

    public Integer getId_departament_user() {
        return id_departament_user;
    }

    public void setId_departament_user(Integer id_departament_user) {
        this.id_departament_user = id_departament_user;
    }

    public Integer getId_departament_secretariat() {
        return id_departament_secretariat;
    }

    public void setId_departament_secretariat(Integer id_departament_secretariat) {
        this.id_departament_secretariat = id_departament_secretariat;
    }

    public Integer getId_tert_extern() {
        return id_tert_extern;
    }

    public void setId_tert_extern(Integer id_tert_extern) {
        this.id_tert_extern = id_tert_extern;
    }

    public String getBlock_trigger() {
        return block_trigger;
    }

    public void setBlock_trigger(String block_trigger) {
        this.block_trigger = block_trigger;
    }

    public Integer getId_categ_registry() {
        return id_categ_registry;
    }

    public void setId_categ_registry(Integer id_categ_registry) {
        this.id_categ_registry = id_categ_registry;
    }

    public String getCateg_registry_str() {
        return categ_registry_str;
    }

    public void setCateg_registry_str(String categ_registry_str) {
        this.categ_registry_str = categ_registry_str;
    }

    public String getCateg_registry_str_names() {
        return categ_registry_str_names;
    }

    public void setCateg_registry_str_names(String categ_registry_str_names) {
        this.categ_registry_str_names = categ_registry_str_names;
    }

    public Integer getId_categorie_securitate() {
        return id_categorie_securitate;
    }

    public void setId_categorie_securitate(Integer id_categorie_securitate) {
        this.id_categorie_securitate = id_categorie_securitate;
    }

    public String getData_doc() {
        return data_doc;
    }

    public void setData_doc(String data_doc) {
        this.data_doc = data_doc;
    }

    public String getData_intrare_in_vigoare() {
        return data_intrare_in_vigoare;
    }

    public void setData_intrare_in_vigoare(String data_intrare_in_vigoare) {
        this.data_intrare_in_vigoare = data_intrare_in_vigoare;
    }

    public String getData_limita_rezolvare() {
        return data_limita_rezolvare;
    }

    public void setData_limita_rezolvare(String data_limita_rezolvare) {
        this.data_limita_rezolvare = data_limita_rezolvare;
    }

    public String getData_valabilitate() {
        return data_valabilitate;
    }

    public void setData_valabilitate(String data_valabilitate) {
        this.data_valabilitate = data_valabilitate;
    }

    public Integer getId_tip_prioritate() {
        return id_tip_prioritate;
    }

    public void setId_tip_prioritate(Integer id_tip_prioritate) {
        this.id_tip_prioritate = id_tip_prioritate;
    }

    public Integer getId_subcateg_registry() {
        return id_subcateg_registry;
    }

    public void setId_subcateg_registry(Integer id_subcateg_registry) {
        this.id_subcateg_registry = id_subcateg_registry;
    }

    public String getInfo26() {
        return info26;
    }

    public void setInfo26(String info26) {
        this.info26 = info26;
    }

    public String getInfo27() {
        return info27;
    }

    public void setInfo27(String info27) {
        this.info27 = info27;
    }

    public String getInfo28() {
        return info28;
    }

    public void setInfo28(String info28) {
        this.info28 = info28;
    }

    public String getInfo29() {
        return info29;
    }

    public void setInfo29(String info29) {
        this.info29 = info29;
    }

    public String getInfo30() {
        return info30;
    }

    public void setInfo30(String info30) {
        this.info30 = info30;
    }

    public String getInfo31() {
        return info31;
    }

    public void setInfo31(String info31) {
        this.info31 = info31;
    }

    public String getInfo32() {
        return info32;
    }

    public void setInfo32(String info32) {
        this.info32 = info32;
    }

    public String getInfo33() {
        return info33;
    }

    public void setInfo33(String info33) {
        this.info33 = info33;
    }

    public String getInfo34() {
        return info34;
    }

    public void setInfo34(String info34) {
        this.info34 = info34;
    }

    public String getInfo35() {
        return info35;
    }

    public void setInfo35(String info35) {
        this.info35 = info35;
    }

    public String getInfo36() {
        return info36;
    }

    public void setInfo36(String info36) {
        this.info36 = info36;
    }

    public String getInfo37() {
        return info37;
    }

    public void setInfo37(String info37) {
        this.info37 = info37;
    }

    public String getInfo38() {
        return info38;
    }

    public void setInfo38(String info38) {
        this.info38 = info38;
    }

    public String getInfo39() {
        return info39;
    }

    public void setInfo39(String info39) {
        this.info39 = info39;
    }

    public String getInfo40() {
        return info40;
    }

    public void setInfo40(String info40) {
        this.info40 = info40;
    }

    public String getInfo41() {
        return info41;
    }

    public void setInfo41(String info41) {
        this.info41 = info41;
    }

    public String getInfo42() {
        return info42;
    }

    public void setInfo42(String info42) {
        this.info42 = info42;
    }

    public String getInfo43() {
        return info43;
    }

    public void setInfo43(String info43) {
        this.info43 = info43;
    }

    public String getInfo44() {
        return info44;
    }

    public void setInfo44(String info44) {
        this.info44 = info44;
    }

    public String getInfo45() {
        return info45;
    }

    public void setInfo45(String info45) {
        this.info45 = info45;
    }

    public String getInfo46() {
        return info46;
    }

    public void setInfo46(String info46) {
        this.info46 = info46;
    }

    public String getInfo47() {
        return info47;
    }

    public void setInfo47(String info47) {
        this.info47 = info47;
    }

    public String getInfo48() {
        return info48;
    }

    public void setInfo48(String info48) {
        this.info48 = info48;
    }

    public String getInfo49() {
        return info49;
    }

    public void setInfo49(String info49) {
        this.info49 = info49;
    }

    public String getInfo50() {
        return info50;
    }

    public void setInfo50(String info50) {
        this.info50 = info50;
    }

    public String getFinalizat_text() {
        return finalizat_text;
    }

    public void setFinalizat_text(String finalizat_text) {
        this.finalizat_text = finalizat_text;
    }

    public String getNota_rezolutie_extern() {
        return nota_rezolutie_extern;
    }

    public void setNota_rezolutie_extern(String nota_rezolutie_extern) {
        this.nota_rezolutie_extern = nota_rezolutie_extern;
    }


    public Integer getNr_zile_valabilitate() {
        return nr_zile_valabilitate;
    }

    public void setNr_zile_valabilitate(Integer nr_zile_valabilitate) {
        this.nr_zile_valabilitate = nr_zile_valabilitate;
    }

    public Integer getDurata_nelimitata() {
        return durata_nelimitata;
    }

    public void setDurata_nelimitata(Integer durata_nelimitata) {
        this.durata_nelimitata = durata_nelimitata;
    }

    public String getId_persoana_read_str_names() {
        return id_persoana_read_str_names;
    }

    public void setId_persoana_read_str_names(String id_persoana_read_str_names) {
        this.id_persoana_read_str_names = id_persoana_read_str_names;
    }

    public Integer getNr_acte_aditionale() {
        return nr_acte_aditionale;
    }

    public void setNr_acte_aditionale(Integer nr_acte_aditionale) {
        this.nr_acte_aditionale = nr_acte_aditionale;
    }

    public Integer getNr_contracte() {
        return nr_contracte;
    }

    public void setNr_contracte(Integer nr_contracte) {
        this.nr_contracte = nr_contracte;
    }

    public String getNr_doc() {
        return nr_doc;
    }

    public void setNr_doc(String nr_doc) {
        this.nr_doc = nr_doc;
    }

    public String getPerioada_valabilitate() {
        return perioada_valabilitate;
    }

    public void setPerioada_valabilitate(String perioada_valabilitate) {
        this.perioada_valabilitate = perioada_valabilitate;
    }

    public Integer getRespinse() {
        return respinse;
    }

    public void setRespinse(Integer respinse) {
        this.respinse = respinse;
    }

    public String getSubcateg_registry_str() {
        return subcateg_registry_str;
    }

    public void setSubcateg_registry_str(String subcateg_registry_str) {
        this.subcateg_registry_str = subcateg_registry_str;
    }

    public String getSubcateg_registry_str_names() {
        return subcateg_registry_str_names;
    }

    public void setSubcateg_registry_str_names(String subcateg_registry_str_names) {
        this.subcateg_registry_str_names = subcateg_registry_str_names;
    }

    public String getTert_str() {
        return tert_str;
    }

    public void setTert_str(String tert_str) {
        this.tert_str = tert_str;
    }

    public String getTert_str2() {
        return tert_str2;
    }

    public void setTert_str2(String tert_str2) {
        this.tert_str2 = tert_str2;
    }

    public String getTert_str2_names() {
        return tert_str2_names;
    }

    public void setTert_str2_names(String tert_str2_names) {
        this.tert_str2_names = tert_str2_names;
    }

    public String getTert_str_names() {
        return tert_str_names;
    }

    public void setTert_str_names(String tert_str_names) {
        this.tert_str_names = tert_str_names;
    }

    public String getTip_doc() {
        return tip_doc;
    }

    public void setTip_doc(String tip_doc) {
        this.tip_doc = tip_doc;
    }

    public String getMod_expediere() {
        return mod_expediere;
    }

    public void setMod_expediere(String mod_expediere) {
        this.mod_expediere = mod_expediere;
    }

    public Integer getId_tip_document_iesire() {
        return id_tip_document_iesire;
    }

    public void setId_tip_document_iesire(Integer id_tip_document_iesire) {
        this.id_tip_document_iesire = id_tip_document_iesire;
    }

    public String getFinalizat_la() {
        return finalizat_la;
    }

    public void setFinalizat_la(String finalizat_la) {
        this.finalizat_la = finalizat_la;
    }

    public String getFinalizat_de() {
        return finalizat_de;
    }

    public void setFinalizat_de(String finalizat_de) {
        this.finalizat_de = finalizat_de;
    }

    public Integer getCota() {
        return cota;
    }

    public void setCota(Integer cota) {
        this.cota = cota;
    }

    public Integer getCota_tematica() {
        return cota_tematica;
    }

    public void setCota_tematica(Integer cota_tematica) {
        this.cota_tematica = cota_tematica;
    }

    public String getId_referinte_multiple() {
        return id_referinte_multiple;
    }

    public void setId_referinte_multiple(String id_referinte_multiple) {
        this.id_referinte_multiple = id_referinte_multiple;
    }

    public String getId_referinte_multiple_names() {
        return id_referinte_multiple_names;
    }

    public void setId_referinte_multiple_names(String id_referinte_multiple_names) {
        this.id_referinte_multiple_names = id_referinte_multiple_names;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getId_persoana_excl() {
        return id_persoana_excl;
    }

    public void setId_persoana_excl(Integer id_persoana_excl) {
        this.id_persoana_excl = id_persoana_excl;
    }

    public String getId_persoana_excl_str() {
        return id_persoana_excl_str;
    }

    public void setId_persoana_excl_str(String id_persoana_excl_str) {
        this.id_persoana_excl_str = id_persoana_excl_str;
    }

    public Integer getId_departament_master() {
        return id_departament_master;
    }

    public void setId_departament_master(Integer id_departament_master) {
        this.id_departament_master = id_departament_master;
    }

    public String getId_departament_master_str() {
        return id_departament_master_str;
    }

    public void setId_departament_master_str(String id_departament_master_str) {
        this.id_departament_master_str = id_departament_master_str;
    }

    public Integer getId_punct_lucru() {
        return id_punct_lucru;
    }

    public void setId_punct_lucru(Integer id_punct_lucru) {
        this.id_punct_lucru = id_punct_lucru;
    }

    public String getId_unitate_logistica() {
        return id_unitate_logistica;
    }

    public void setId_unitate_logistica(String id_unitate_logistica) {
        this.id_unitate_logistica = id_unitate_logistica;
    }

    public String getData_interna() {
        return data_interna;
    }

    public void setData_interna(String data_interna) {
        this.data_interna = data_interna;
    }

    public String getValoare_initiala() {
        return valoare_initiala;
    }

    public void setValoare_initiala(String valoare_initiala) {
        this.valoare_initiala = valoare_initiala;
    }

    public String getDescriere_fisier() {
        return descriere_fisier;
    }

    public void setDescriere_fisier(String descriere_fisier) {
        this.descriere_fisier = descriere_fisier;
    }

    public String getCommands_tert() {
        return commands_tert;
    }

    public void setCommands_tert(String commands_tert) {
        this.commands_tert = commands_tert;
    }

    public String getTip_document() {
        return tip_document;
    }

    public void setTip_document(String tip_document) {
        this.tip_document = tip_document;
    }

    public String getDepartament_user() {
        return departament_user;
    }

    public void setDepartament_user(String departament_user) {
        this.departament_user = departament_user;
    }

    public String getTert() {
        return tert;
    }

    public void setTert(String tert) {
        this.tert = tert;
    }

    public String getReferinta() {
        return referinta;
    }

    public void setReferinta(String referinta) {
        this.referinta = referinta;
    }

    public String getUnitati_logistice() {
        return unitati_logistice;
    }

    public void setUnitati_logistice(String unitati_logistice) {
        this.unitati_logistice = unitati_logistice;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTermen() {
        return termen;
    }

    public void setTermen(String termen) {
        this.termen = termen;
    }

    public String getId_flux_def() {
        return id_flux_def;
    }

    public void setId_flux_def(String id_flux_def) {
        this.id_flux_def = id_flux_def;
    }

    public String getTermen_raspuns_preliminar() {
        return termen_raspuns_preliminar;
    }

    public void setTermen_raspuns_preliminar(String termen_raspuns_preliminar) {
        this.termen_raspuns_preliminar = termen_raspuns_preliminar;
    }

    public String getNumar_raspuns_preliminar() {
        return numar_raspuns_preliminar;
    }

    public void setNumar_raspuns_preliminar(String numar_raspuns_preliminar) {
        this.numar_raspuns_preliminar = numar_raspuns_preliminar;
    }

    public String getData_raspuns_preliminar() {
        return data_raspuns_preliminar;
    }

    public void setData_raspuns_preliminar(String data_raspuns_preliminar) {
        this.data_raspuns_preliminar = data_raspuns_preliminar;
    }

    public String getTermen_informare() {
        return termen_informare;
    }

    public void setTermen_informare(String termen_informare) {
        this.termen_informare = termen_informare;
    }

    public String getNumar_informare() {
        return numar_informare;
    }

    public void setNumar_informare(String numar_informare) {
        this.numar_informare = numar_informare;
    }

    public String getData_informare() {
        return data_informare;
    }

    public void setData_informare(String data_informare) {
        this.data_informare = data_informare;
    }

    public String getTermen_punct_vedere() {
        return termen_punct_vedere;
    }

    public void setTermen_punct_vedere(String termen_punct_vedere) {
        this.termen_punct_vedere = termen_punct_vedere;
    }

    public String getNumar_punct_vedere() {
        return numar_punct_vedere;
    }

    public void setNumar_punct_vedere(String numar_punct_vedere) {
        this.numar_punct_vedere = numar_punct_vedere;
    }

    public String getData_punct_vedere() {
        return data_punct_vedere;
    }

    public void setData_punct_vedere(String data_punct_vedere) {
        this.data_punct_vedere = data_punct_vedere;
    }

    public String getTermen_raspuns_final() {
        return termen_raspuns_final;
    }

    public void setTermen_raspuns_final(String termen_raspuns_final) {
        this.termen_raspuns_final = termen_raspuns_final;
    }

    public String getNumar_raspuns_final() {
        return numar_raspuns_final;
    }

    public void setNumar_raspuns_final(String numar_raspuns_final) {
        this.numar_raspuns_final = numar_raspuns_final;
    }

    public String getData_raspuns_final() {
        return data_raspuns_final;
    }

    public void setData_raspuns_final(String data_raspuns_final) {
        this.data_raspuns_final = data_raspuns_final;
    }

    public String getDepartament_secretariat() {
        return departament_secretariat;
    }

    public void setDepartament_secretariat(String departament_secretariat) {
        this.departament_secretariat = departament_secretariat;
    }

    public String getNote_rezolutie_extern() {
        return note_rezolutie_extern;
    }

    public void setNote_rezolutie_extern(String note_rezolutie_extern) {
        this.note_rezolutie_extern = note_rezolutie_extern;
    }

    public String getStadiu_registratura() {
        return stadiu_registratura;
    }

    public void setStadiu_registratura(String stadiu_registratura) {
        this.stadiu_registratura = stadiu_registratura;
    }

    public String getCommands_gen_cote() {
        return commands_gen_cote;
    }

    public void setCommands_gen_cote(String commands_gen_cote) {
        this.commands_gen_cote = commands_gen_cote;
    }

    public String getCateg_registry() {
        return categ_registry;
    }

    public void setCateg_registry(String categ_registry) {
        this.categ_registry = categ_registry;
    }

    public String getSubcateg_registry() {
        return subcateg_registry;
    }

    public void setSubcateg_registry(String subcateg_registry) {
        this.subcateg_registry = subcateg_registry;
    }

    public String getTert_codatm() {
        return tert_codatm;
    }

    public void setTert_codatm(String tert_codatm) {
        this.tert_codatm = tert_codatm;
    }

    public String getTert_tip_client() {
        return tert_tip_client;
    }

    public void setTert_tip_client(String tert_tip_client) {
        this.tert_tip_client = tert_tip_client;
    }

    public String getTert_nlc() {
        return tert_nlc;
    }

    public void setTert_nlc(String tert_nlc) {
        this.tert_nlc = tert_nlc;
    }

    public String getTert_adresa() {
        return tert_adresa;
    }

    public void setTert_adresa(String tert_adresa) {
        this.tert_adresa = tert_adresa;
    }

    public String getTert_email() {
        return tert_email;
    }

    public void setTert_email(String tert_email) {
        this.tert_email = tert_email;
    }

    public String getTert_cod_fiscal() {
        return tert_cod_fiscal;
    }

    public void setTert_cod_fiscal(String tert_cod_fiscal) {
        this.tert_cod_fiscal = tert_cod_fiscal;
    }

    public String getTert_localitate() {
        return tert_localitate;
    }

    public void setTert_localitate(String tert_localitate) {
        this.tert_localitate = tert_localitate;
    }

    public String getTert_cnp() {
        return tert_cnp;
    }

    public void setTert_cnp(String tert_cnp) {
        this.tert_cnp = tert_cnp;
    }

    public String getTert_tara() {
        return tert_tara;
    }

    public void setTert_tara(String tert_tara) {
        this.tert_tara = tert_tara;
    }

    public String getTert_scara() {
        return tert_scara;
    }

    public void setTert_scara(String tert_scara) {
        this.tert_scara = tert_scara;
    }

    public String getTert_etaj() {
        return tert_etaj;
    }

    public void setTert_etaj(String tert_etaj) {
        this.tert_etaj = tert_etaj;
    }

    public String getTert_judet() {
        return tert_judet;
    }

    public void setTert_judet(String tert_judet) {
        this.tert_judet = tert_judet;
    }

    public String getTert_numar() {
        return tert_numar;
    }

    public void setTert_numar(String tert_numar) {
        this.tert_numar = tert_numar;
    }

    public String getTert_strada() {
        return tert_strada;
    }

    public void setTert_strada(String tert_strada) {
        this.tert_strada = tert_strada;
    }

    public String getTert_apartament() {
        return tert_apartament;
    }

    public void setTert_apartament(String tert_apartament) {
        this.tert_apartament = tert_apartament;
    }

    public String getTert_bloc() {
        return tert_bloc;
    }

    public void setTert_bloc(String tert_bloc) {
        this.tert_bloc = tert_bloc;
    }

    public String getTert_mama() {
        return tert_mama;
    }

    public void setTert_mama(String tert_mama) {
        this.tert_mama = tert_mama;
    }

    public String getTert_tata() {
        return tert_tata;
    }

    public void setTert_tata(String tert_tata) {
        this.tert_tata = tert_tata;
    }

    public String getTert_prenume() {
        return tert_prenume;
    }

    public void setTert_prenume(String tert_prenume) {
        this.tert_prenume = tert_prenume;
    }

    public String getTert_data_nastere() {
        return tert_data_nastere;
    }

    public void setTert_data_nastere(String tert_data_nastere) {
        this.tert_data_nastere = tert_data_nastere;
    }

    public String getTert_cod_sap() {
        return tert_cod_sap;
    }

    public void setTert_cod_sap(String tert_cod_sap) {
        this.tert_cod_sap = tert_cod_sap;
    }

    public String getTip_prioritate() {
        return tip_prioritate;
    }

    public void setTip_prioritate(String tip_prioritate) {
        this.tip_prioritate = tip_prioritate;
    }

    public String getId_persoana_read_str() {
        return id_persoana_read_str;
    }

    public void setId_persoana_read_str(String id_persoana_read_str) {
        this.id_persoana_read_str = id_persoana_read_str;
    }

    public String getCount_nr_anexe() {
        return count_nr_anexe;
    }

    public void setCount_nr_anexe(String count_nr_anexe) {
        this.count_nr_anexe = count_nr_anexe;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public Double getProcent_tva() {
        return procent_tva;
    }

    public void setProcent_tva(Double procent_tva) {
        this.procent_tva = procent_tva;
    }

    public String getCount_nr_contracte() {
        return count_nr_contracte;
    }

    public void setCount_nr_contracte(String count_nr_contracte) {
        this.count_nr_contracte = count_nr_contracte;
    }

    public Double getValoare_tva() {
        return valoare_tva;
    }

    public void setValoare_tva(Double valoare_tva) {
        this.valoare_tva = valoare_tva;
    }

    public Double getValoare_cu_tva() {
        return valoare_cu_tva;
    }

    public void setValoare_cu_tva(Double valoare_cu_tva) {
        this.valoare_cu_tva = valoare_cu_tva;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getPersoana() {
        return persoana;
    }

    public void setPersoana(String persoana) {
        this.persoana = persoana;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNota_rezolutie() {
        return nota_rezolutie;
    }

    public void setNota_rezolutie(String nota_rezolutie) {
        this.nota_rezolutie = nota_rezolutie;
    }

    public Integer getPredat_de() {
        return predat_de;
    }

    public void setPredat_de(Integer predat_de) {
        this.predat_de = predat_de;
    }

    public String getPredat_la() {
        return predat_la;
    }

    public void setPredat_la(String predat_la) {
        this.predat_la = predat_la;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getClient_intern() {
        return client_intern;
    }

    public void setClient_intern(String client_intern) {
        this.client_intern = client_intern;
    }

    public String getPersoana_excl() {
        return persoana_excl;
    }

    public void setPersoana_excl(String persoana_excl) {
        this.persoana_excl = persoana_excl;
    }

    public String getDepartament_master() {
        return departament_master;
    }

    public void setDepartament_master(String departament_master) {
        this.departament_master = departament_master;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getNr_intrare() {
        return nr_intrare;
    }

    public void setNr_intrare(String nr_intrare) {
        this.nr_intrare = nr_intrare;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTip_petitie() {
        return tip_petitie;
    }

    public void setTip_petitie(String tip_petitie) {
        this.tip_petitie = tip_petitie;
    }

    public String getTip_sesizare() {
        return tip_sesizare;
    }

    public void setTip_sesizare(String tip_sesizare) {
        this.tip_sesizare = tip_sesizare;
    }

    public String getNivel_organizational() {
        return nivel_organizational;
    }

    public void setNivel_organizational(String nivel_organizational) {
        this.nivel_organizational = nivel_organizational;
    }

    public String getMod_rezolvare() {
        return mod_rezolvare;
    }

    public void setMod_rezolvare(String mod_rezolvare) {
        this.mod_rezolvare = mod_rezolvare;
    }

    public String getMod_rezolvare_reg() {
        return mod_rezolvare_reg;
    }

    public void setMod_rezolvare_reg(String mod_rezolvare_reg) {
        this.mod_rezolvare_reg = mod_rezolvare_reg;
    }

    public String getIesit_descriere() {
        return iesit_descriere;
    }

    public void setIesit_descriere(String iesit_descriere) {
        this.iesit_descriere = iesit_descriere;
    }

    public String getIesit_catre() {
        return iesit_catre;
    }

    public void setIesit_catre(String iesit_catre) {
        this.iesit_catre = iesit_catre;
    }

    public String getIesit_data() {
        return iesit_data;
    }

    public void setIesit_data(String iesit_data) {
        this.iesit_data = iesit_data;
    }

    public String getNr_conexare() {
        return nr_conexare;
    }

    public void setNr_conexare(String nr_conexare) {
        this.nr_conexare = nr_conexare;
    }

    public String getStare_reg() {
        return stare_reg;
    }

    public void setStare_reg(String stare_reg) {
        this.stare_reg = stare_reg;
    }

    public String getIndicativ_dosar_arh() {
        return indicativ_dosar_arh;
    }

    public void setIndicativ_dosar_arh(String indicativ_dosar_arh) {
        this.indicativ_dosar_arh = indicativ_dosar_arh;
    }

    public String getAnulat() {
        return anulat;
    }

    public void setAnulat(String anulat) {
        this.anulat = anulat;
    }

    public String getMotiv_anulare() {
        return motiv_anulare;
    }

    public void setMotiv_anulare(String motiv_anulare) {
        this.motiv_anulare = motiv_anulare;
    }

    public String getData_anulare() {
        return data_anulare;
    }

    public void setData_anulare(String data_anulare) {
        this.data_anulare = data_anulare;
    }

    public String getCategorie_securitate() {
        return categorie_securitate;
    }

    public void setCategorie_securitate(String categorie_securitate) {
        this.categorie_securitate = categorie_securitate;
    }

    public String getFisierDownload() {
        return fisierDownload;
    }

    public void setFisierDownload(String fisierDownload) {
        this.fisierDownload = fisierDownload;
    }

    public String getTert_extern_localitate() {
        return tert_extern_localitate;
    }

    public void setTert_extern_localitate(String tert_extern_localitate) {
        this.tert_extern_localitate = tert_extern_localitate;
    }

    public String getTert_extern_judet() {
        return tert_extern_judet;
    }

    public void setTert_extern_judet(String tert_extern_judet) {
        this.tert_extern_judet = tert_extern_judet;
    }

    public String getTert_extern_telefon() {
        return tert_extern_telefon;
    }

    public void setTert_extern_telefon(String tert_extern_telefon) {
        this.tert_extern_telefon = tert_extern_telefon;
    }
}