package com.wiley.iss.mapper;

import com.wiley.iss.model.InterfaceRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ravuri on 4/19/17.
 */
public class InterfaceEntryMapper implements RowMapper<InterfaceRecord>
{
    public InterfaceRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        InterfaceRecord entry = new InterfaceRecord();
        entry.setServiceId(rs.getInt(1));
        entry.setServiceName(rs.getString(2));
        entry.setServiceStatusDesc(rs.getString(3));
        entry.setPurpose(rs.getString(4));
        entry.setUseCase(rs.getString(5));
        entry.setTaxonomy(rs.getString(6));
        entry.setSource(rs.getString(7));
        entry.setTarget(rs.getString(8));
        entry.setContextDiagram(rs.getString(9));
        entry.setIntegrationType(rs.getString(10));
        entry.setServiceType(rs.getString(11));
        entry.setFrequency(rs.getString(12));
        entry.setTransportType(rs.getString(13));
        entry.setDataType(rs.getString(14));
        entry.setDesignDoc(rs.getString(15));
        entry.setSourceTargetMapping(rs.getString(16));
        entry.setTestEnvironments(rs.getString(17));
        entry.setSchemaDefinitions(rs.getString(18));
        entry.setSampleRequest(rs.getString(19));
        entry.setSampleResponse(rs.getString(20));
        entry.setOtherServices(rs.getString(21));
        entry.setAdditionalComments(rs.getString(22));
        return entry;
    }
}
