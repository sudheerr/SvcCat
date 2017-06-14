package com.wiley.iss.service;

import com.wiley.iss.model.InterfaceRecord;
import com.wiley.iss.mapper.InterfaceEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterfaceService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(InterfaceService.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final String sqlQuery = "SELECT service_id, svc_name, svc_cat_status.description, purpose, use_case, domain.domain_name,"
			+ " SOURCE, target, context_diagram, integration_type, service_type, frequency,"
			+ " transport_type, data_type, design_doc, source_target_mapping,"
			+ " test_environments, schema_definitions, sample_request_file,"
			+ " sample_response_file, sample_request, sample_response, other_services,"
			+ " ADDITIONAL_COMMENTS FROM SVC_INTERFACE JOIN svc_cat_status ON "
			+ " svc_interface.service_status = svc_cat_status.status_id "
			+ " JOIN SVC_CAT_DOMAIN domain on SVC_INTERFACE.domain_id = domain.domain_id"
			+ " where SVC_INTERFACE.service_used ='Y'";
	
	private static final String fetchDetails = "SELECT service_id, svc_name, svc_cat_status.description, purpose, use_case, domain.domain_name,"
			+ " SOURCE, target, context_diagram, integration_type, service_type, frequency,"
			+ " transport_type, data_type, design_doc, source_target_mapping,"
			+ " test_environments, schema_definitions, sample_request_file,"
			+ " sample_response_file, sample_request, sample_response, other_services,"
			+ " ADDITIONAL_COMMENTS FROM SVC_INTERFACE JOIN svc_cat_status ON "
			+ " svc_interface.service_status = svc_cat_status.status_id "
			+ " JOIN SVC_CAT_DOMAIN domain on SVC_INTERFACE.domain_id = domain.domain_id"
			+ " where SVC_INTERFACE.service_id =?";

	public List<InterfaceRecord> fetchResults() {
		return jdbcTemplate.query(sqlQuery,new InterfaceEntryMapper());
	}
	
	public InterfaceRecord getInterfaceDetails(Integer serviceId) {
		return jdbcTemplate.queryForObject(fetchDetails,new Object[]{serviceId},new InterfaceEntryMapper());
	}
}
