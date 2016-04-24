package claimswork.dao;

import java.util.UUID;

import com.exponentus.dataengine.jpa.DAO;
import com.exponentus.scripting._Session;

import claimswork.model.CivilProceeding;

public class CivilProceedingDAO extends DAO<CivilProceeding, UUID> {

	public CivilProceedingDAO(_Session session) {
		super(CivilProceeding.class, session);
	}

}
