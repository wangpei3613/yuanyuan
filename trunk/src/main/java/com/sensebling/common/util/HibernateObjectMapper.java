package com.sensebling.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class HibernateObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 8343399576476244085L;
	public HibernateObjectMapper() {
		disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
}
