package claimswork.model.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import claimswork.model.constants.ProceedingStatusType;

@Converter(autoApply = true)
public class ProceedingStatusTypeConverter implements AttributeConverter<ProceedingStatusType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ProceedingStatusType issuePriority) {
		return issuePriority.getCode();
	}

	@Override
	public ProceedingStatusType convertToEntityAttribute(Integer priorityValue) {
		return ProceedingStatusType.getType(priorityValue);
	}
}
