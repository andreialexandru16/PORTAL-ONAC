package ro.bithat.dms.microservices.dmsws.file;

import java.util.List;

public class RowAttrComplexList {

	private Integer rowNumber;
	private List<DocAttrLink> listaAtribute;


	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public List<DocAttrLink> getListaAtribute() {
		return listaAtribute;
	}

	public void setListaAtribute(List<DocAttrLink> listaAtribute) {
		this.listaAtribute = listaAtribute;
	}

	public RowAttrComplexList(Integer rowNumber, List<DocAttrLink> listaAtribute) {
		this.rowNumber = rowNumber;
		this.listaAtribute = listaAtribute;
	}

	public RowAttrComplexList() {
	}
}