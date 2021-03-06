package com.wiley.iss.model.mapper;

import com.wiley.iss.model.ServiceDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ravuri on 4/19/17.
 */
public class ServiceDetailMapper implements RowMapper<ServiceDetail>
{
    public ServiceDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceDetail details = new ServiceDetail();
        details.setSvcId(rs.getInt(1));
        details.setSvcName(rs.getString(2));
        details.setSvcDescription(rs.getString(3));
        details.setServiceType(rs.getString(4));
        details.setServiceFrequency(rs.getString(5));
        details.setOperations(rs.getString(6));
        details.setProvider(rs.getString(7));

        details.setVersionId(rs.getFloat(9));
        details.setCanonicalDataModel(rs.getString(10));
        details.setStatus(rs.getString(11));

        details.setTransportType(rs.getString(12));
        details.setDesignDoc(rs.getString(13));
        details.setVersionImgUrl(rs.getString(14));

        details.setComments(rs.getString(15));

        details.setConsumerName(rs.getString(16));
        details.setDomainName(rs.getString(17));
        details.setProvConsMapping(rs.getString(18));
        details.setSampleRequest(rs.getString(19));
        details.setSampleResponse(rs.getString(20));


        while (rs.next()) {
            details.setConsumerName(details.getConsumerName().concat(", " + rs.getString(16)));
        }
        return  details;
    }
}
