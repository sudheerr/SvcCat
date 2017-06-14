package com.wiley.iss.mapper;

import com.wiley.iss.model.ServiceRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ravuri on 4/19/17.
 */
public class ServiceResultMapper implements RowMapper<ServiceRecord>
{
    public ServiceRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceRecord serviceRecord = new ServiceRecord();
        serviceRecord.setDomainName(rs.getString(1));
        serviceRecord.setSvcId(rs.getInt(2));
        serviceRecord.setSvcName(rs.getString(3));
        serviceRecord.setSvcDescription(rs.getString(4));
        serviceRecord.setSvcOperations(rs.getString(5));
        serviceRecord.setProvider(rs.getString(6));
        serviceRecord.setVersionId(rs.getFloat(7));
        serviceRecord.setConsumerName(rs.getString(8));
        serviceRecord.setCanonicalDataModel(rs.getString(9));
        serviceRecord.setNetworkScope(rs.getString(10));
        serviceRecord.setEventType(rs.getString(11));
        serviceRecord.setProtocol(rs.getString(12));
        serviceRecord.setSvcSecurity(rs.getString(13));
        serviceRecord.setProviderSLA(rs.getString(14));
        serviceRecord.setTiersSLA(rs.getString(15));
        serviceRecord.setConsumerCLA(rs.getString(16));
        serviceRecord.setStatus(rs.getString(17));
        serviceRecord.setInitiative(rs.getString(18));
        return serviceRecord;
    }
}
