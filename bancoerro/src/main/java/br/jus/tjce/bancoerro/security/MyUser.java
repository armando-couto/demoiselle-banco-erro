package br.jus.tjce.bancoerro.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.gov.frameworkdemoiselle.security.User;

public class MyUser implements User, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private String id;
	
	private Map<Object, Object> attibutes = new HashMap<Object, Object>();
	
	@Override
	public Object getAttribute(Object key) {
		return attibutes.get(key);
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public void setAttribute(Object key, Object value) {
		attibutes.put(key, value);	
	}
}