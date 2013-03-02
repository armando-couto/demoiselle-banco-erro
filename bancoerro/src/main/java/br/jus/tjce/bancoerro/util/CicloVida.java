package br.jus.tjce.bancoerro.util;

import java.io.Serializable;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class CicloVida implements PhaseListener, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
		System.out.println("Antes "+arg0.getPhaseId().toString());
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		System.out.println("Depois "+arg0.getPhaseId().toString());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
