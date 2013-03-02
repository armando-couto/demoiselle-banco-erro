package br.jus.tjce.bancoerro.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.frameworkdemoiselle.util.Beans;
import br.jus.tjce.bancoerro.business.PerfilBC;
import br.jus.tjce.bancoerro.domain.Perfil;

@FacesConverter (forClass = Perfil.class)
public class PerfilConverter  implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {

		if (string != null && !string.trim().isEmpty()) {
			Long id = Long.parseLong(string);
			return perfilBC().load(id);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {

		if (objeto != null && objeto instanceof Perfil) {
			Perfil perfil = (Perfil) objeto;
			if (perfil.getId() != null) {
				return perfil.getId().toString();
			}
		}
		return null;
	}
	
	private PerfilBC perfilBC() {
		return Beans.getReference(PerfilBC.class);
	}
}