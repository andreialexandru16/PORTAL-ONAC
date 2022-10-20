package ro.bithat.dms.microservices.dmsws.flow;

public class PortalFlowInstanceList {
	private String ext;

	private String trimisDe;

	private String read;

	private String culoare;

	private String nume;

	private String trimisLa;

	private String nextSteps;

	private Long idFluxPas;

	private String descriere;

	private String idFisier;

	private Long id;

	private String denumireFlux;

	private String tipDocument;

	private String portalPage;

	private String status;
	
	private String metaDataAsString;

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getTrimisDe() {
		return trimisDe;
	}

	public void setTrimisDe(String trimisDe) {
		this.trimisDe = trimisDe;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getCuloare() {
		return culoare;
	}

	public void setCuloare(String culoare) {
		this.culoare = culoare;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getTrimisLa() {
		return trimisLa;
	}

	public void setTrimisLa(String trimisLa) {
		this.trimisLa = trimisLa;
	}

	public String getNextSteps() {
		return nextSteps;
	}

	public void setNextSteps(String nextSteps) {
		this.nextSteps = nextSteps;
	}

	public Long getIdFluxPas() {
		return idFluxPas;
	}

	public void setIdFluxPas(Long idFluxPas) {
		this.idFluxPas = idFluxPas;
	}

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}

	public String getIdFisier() {
		return idFisier;
	}

	public void setIdFisier(String idFisier) {
		this.idFisier = idFisier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenumireFlux() {
		return denumireFlux;
	}

	public void setDenumireFlux(String denumireFlux) {
		this.denumireFlux = denumireFlux;
	}

	public String getTipDocument() {
		return tipDocument;
	}

	public void setTipDocument(String tipDocument) {
		this.tipDocument = tipDocument;
	}

	public String getPortalPage() {
		return portalPage;
	}

	public void setPortalPage(String portalPage) {
		this.portalPage = portalPage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMetaDataAsString() {
		return metaDataAsString;
	}

	public void setMetaDataAsString(String metaDataAsString) {
		this.metaDataAsString = metaDataAsString;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PortalFlowInstanceList))
			return false;

		PortalFlowInstanceList that = (PortalFlowInstanceList) o;

		if (!idFluxPas.equals(that.idFluxPas))
			return false;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		int result = idFluxPas.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}
}