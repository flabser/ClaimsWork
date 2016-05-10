package claimswork.dao;

import claimswork.model.CriminalProceeding;
import com.exponentus.dataengine.jpa.DAO;
import com.exponentus.scripting._Session;

import java.util.UUID;

public class CriminalProceedingDAO extends DAO<CriminalProceeding, UUID> {

	public CriminalProceedingDAO(_Session session) {
		super(CriminalProceeding.class, session);
	}

}
