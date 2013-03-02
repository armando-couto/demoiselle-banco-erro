package br.jus.tjce.bancoerro.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.frameworkdemoiselle.util.Beans;
import br.jus.tjce.bancoerro.business.SistemaBC;
import br.jus.tjce.bancoerro.domain.Sistema;

@FacesConverter (forClass = Sistema.class)
public class SistemaConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {

		if (string != null && !string.trim().isEmpty()) {
			Long id = Long.parseLong(string);
			return sistemaBC().load(id);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {

		if (objeto != null && objeto instanceof Sistema) {
			Sistema sistema = (Sistema) objeto;
			if (sistema.getId() != null) {
				return sistema.getId().toString();
			}
		}
		return null;
	}

	private SistemaBC sistemaBC() {
		return Beans.getReference(SistemaBC.class);
	}
}
