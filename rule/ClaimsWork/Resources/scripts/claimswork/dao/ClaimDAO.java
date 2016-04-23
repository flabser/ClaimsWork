package claimswork.dao;

import java.util.UUID;

import com.exponentus.dataengine.jpa.DAO;
import com.exponentus.scripting._Session;

import claimswork.model.Claim;

public class ClaimDAO extends DAO<Claim, UUID> {

	public ClaimDAO(_Session session) {
		super(Claim.class, session);
	}

}
