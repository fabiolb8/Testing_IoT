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
	
	public TestCase() {

		esito = Esito.NON_ESEGUITO;
	}
	
	public List<Step> getListaStep() {
		return listaStep;
	}

	public void setListaStep(List<Step> listaStep) {
		this.listaStep = listaStep;
	}	
	
	public void calcolaEsito() {

		boolean bool_esito = true;
		int i = 0;
		
		while (i<listaStep.size() && bool_esito) {
			
			if ((listaStep.get(i).getOutputAllarmeAtteso() != listaStep.get(i).getOutputAllarmeRilevato()) 
					|| (listaStep.get(i).getOutputVentilazioneAtteso() != listaStep.get(i).getOutputventilazioneRilevato()))
				bool_esito = false;
			
			i++;
			
		}
		
		if (bool_esito)
			setEsito(Esito.POSITIVO);
		else 
			setEsito(Esito.NEGATIVO);
		
	}

	public void run() throws Exception {
		

		for (int i=0; i<listaStep.size(); i++) {
			
			
			try {
				listaStep.get(i).inviaInput();
				Thread.sleep(2000);
				listaStep.get(i).leggiOutput();

			} catch (InterruptedException e) {
				setEsito(Esito.NON_ESEGUITO);
			} 
			
			
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