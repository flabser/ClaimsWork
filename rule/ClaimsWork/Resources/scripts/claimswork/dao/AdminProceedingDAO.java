package claimswork.dao;

import claimswork.model.AdminProceeding;
import com.exponentus.dataengine.jpa.DAO;
import com.exponentus.scripting._Session;

import java.util.UUID;

public class AdminProceedingDAO extends DAO<AdminProceeding, UUID> {

	public AdminProceedingDAO(_Session session) {
		super(AdminProceeding.class, session);
	}

}
