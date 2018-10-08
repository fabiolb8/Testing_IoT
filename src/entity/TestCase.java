package entity;

import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TestCase")
@XmlAccessorType (XmlAccessType.FIELD)

public class TestCase {

	@XmlElement(name = "Step")
	private List<Step> listaStep;
	@XmlElement(name = "id")
	private int id;
	@XmlElement(name = "descrizione")
	private String descrizione;
	private Esito esito;
	
	public List<Step> getListaStep() {
		return listaStep;
	}

	public void setListaStep(List<Step> listaStep) {
		this.listaStep = listaStep;
	}	
	
	public void calcolaEsito() {
		// TODO - implement TestCase.calcolaEsito

		boolean ok = true;
		
		for (int i=0; i<listaStep.size(); i++) {
			
			if ((listaStep.get(i).getOutputAllarmeAtteso()==listaStep.get(i).getOutputAllarmeRilevato()) && (listaStep.get(i).getOutputAllarmeAtteso()==listaStep.get(i).getOutputAllarmeRilevato()))
				ok = true;
			else {
				ok = false;
				break;
			}	
			
		}
		
		if (ok == true)
			esito = Esito.POSITIVO;
		else esito = Esito.NEGATIVO;
	}

	public void run() {
		

		for (int i=0; i<listaStep.size(); i++) {
			
			listaStep.get(i).inviaInput();
			
			try {
				//METTI 3000
				Thread.sleep(300);
				//System.out.println("Attesa " + i + "terminata");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			listaStep.get(i).leggiOutput();
			
		}
		
		calcolaEsito();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}


}