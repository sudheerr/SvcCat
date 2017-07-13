package com.wiley.iss.service;

import java.util.List;

import com.wiley.iss.model.ServiceDetail;
import com.wiley.iss.model.ServiceRecord;
import com.wiley.iss.model.mapper.ServiceDetailMapper;
import com.wiley.iss.model.mapper.ServiceRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogService.class);

	//Showing only active services (SERVICE_ACTIVE ='Y')
	private static final String sqlQuery = "select  domain.domain_name, service.SVC_ID, service.SVC_NAME, service.DESCRIPTION, service.SVC_OPERATIONS,"
			+ " service.PROVIDER, svc_version.VERSION_ID, consumer.CONSUMER_NAME, svc_version.CANONICAL_DATA_MODEL,"
			+ " service.NETWORK_SCOPE, svc_version.EVENT_TYPE, svc_version.TRANSPORT_TYPE, svc_version.SVC_SECURITY, svc_version.PROVIDER_SLA,"
			+ " null as SLA_TIERS, svc_consumer.CLA, status.STATUS_NAME, service.INITIATIVE from svc_cat_service service"
			+ " JOIN svc_cat_service_version svc_version ON service.SVC_ID = svc_version.SVC_ID"
			+ " JOIN svc_cat_service_consumer svc_consumer ON svc_consumer.SVC_ID = svc_version.SVC_ID"
			+ " AND SERVICE_ACTIVE ='Y'"
			+ " AND svc_consumer.VERSION_ID = svc_version.VERSION_ID"
			+ " JOIN svc_cat_consumer consumer ON svc_consumer.CONSUMER_ID = consumer.CONSUMER_ID"
			+ " JOIN svc_cat_status status ON  svc_version.STATUS_ID = status.STATUS_ID"
			+ " JOIN svc_cat_domain domain ON domain.domain_id = service.domain_id"
			+ " ORDER BY domain.domain_id asc, service.SVC_ID asc";

	private static final String detailsQuery = " select service.svc_id , service.svc_name, service.description,"
			+ " svc_version.event_type, svc_version.frequency, service.SVC_OPERATIONS, service.provider, "
			+ " service.network_scope, svc_version.version_id,"
			+ " svc_version.canonical_data_model,status.STATUS_NAME, "
			+ " svc_version.TRANSPORT_TYPE, "
			+ " svc_version.DESIGN_DOC,"
			+ " svc_version.VERSION_IMG_URL,"
			+ " svc_version.ADDITIONAL_COMMENTS, consumer.consumer_name, domain.domain_name, "
			+ " svc_version.provider_consumer_map, svc_version.sample_request, svc_version.sample_response "
			+ " from svc_cat_service service"
			+ "	JOIN svc_cat_service_version svc_version ON service.SVC_ID = svc_version.SVC_ID"
			+ "	JOIN svc_cat_status status ON  svc_version.STATUS_ID = status.STATUS_ID"
			+ " JOIN svc_cat_service_consumer svc_con ON"
			+ " svc_con.svc_id = svc_version.SVC_ID and  svc_con.Version_id = svc_version.version_id"
			+ " JOIn svc_cat_consumer consumer ON"
			+ " consumer.consumer_id = svc_con.consumer_id"
			+ " JOIN svc_cat_domain domain on domain.domain_id =  service.domain_id"
			+ " where service.SVC_ID = ? and svc_version.VERSION_ID = ?";

	public List<ServiceRecord> fetchResults() {
		return jdbcTemplate.query(sqlQuery,new ServiceRecordMapper());
	}

	public ServiceDetail fetchDetailsResults(String svcId,
			String versionID) {
		return jdbcTemplate.queryForObject(detailsQuery,new Object[] { svcId,versionID },new ServiceDetailMapper());
	}
}
