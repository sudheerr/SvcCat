package com.wiley.iss.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import com.wiley.iss.model.ExcelRow;
import oracle.jdbc.OracleTypes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiley.iss.model.Response;
import com.wiley.iss.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

/**
 * Service to upload the provided excel file(xlsx) as Inputstream to DB.
 * 
 */
@Service
public class FileUploadService {
	private static  final Logger LOGGER = LoggerFactory
			.getLogger(FileUploadService.class);

	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Response uploadFile(InputStream inputStream, UserDTO user){
		
		ExcelRow entry;
		Cell cell;
		Workbook wb;
		Sheet uploadTemplate;
		Response response = new Response();
		response.setMessage("Upload failed!");// Default Status for any unexpected exceptions
		
		try {
			wb = new XSSFWorkbook(inputStream);
		    uploadTemplate= wb.getSheet("UploadTemplate");
		    
			if (uploadTemplate == null) {
				response.setMessage("UploadTemplate Sheet not found in the excel file.");
				wb.close();
				return response;
			}
		} catch (IOException e1) {
			return response;
		}
		
		boolean partialSuccess =false, checkSum = true;
		StringBuilder details = new StringBuilder();


		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		simpleJdbcCall.withSchemaName("eissvccat").withCatalogName("svc_catalog_pkg").withProcedureName("svc_update_svc_consumer");

		simpleJdbcCall.declareParameters(new SqlParameter("servc_name", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("servc_version", OracleTypes.NUMBER));
		simpleJdbcCall.declareParameters(new SqlParameter("cons_name", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("cons_desc", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("decomm_comments", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("user_name", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("servc_desc", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("servc_provider", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("servc_type", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("servc_networkscope", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("servc_oper", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("ver_transport_type", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("ver_canonicalData", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("ver_event_type", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("ver_frequency", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("dom_name", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("servc_security", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("ver_comments", OracleTypes.VARCHAR));
		simpleJdbcCall.declareParameters(new SqlParameter("ver_status_id", OracleTypes.NUMBER));
		simpleJdbcCall.declareParameters( new SqlOutParameter("out_error_code",OracleTypes.VARCHAR));


		for (Row row : uploadTemplate) {
			cell = row.getCell(1, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			// Ignore Header(0) & Empty Records
			if (row.getRowNum() > 0 && cell != null && !cell.toString().isEmpty()) {
				try {
					entry = new ExcelRow(row);

					MapSqlParameterSource in = new MapSqlParameterSource();
					in.addValue("servc_name", entry.getServiceName());
					in.addValue("servc_version", entry.getServiceVersion());
					in.addValue("cons_name", entry.getConsumerName());
					in.addValue("cons_desc", entry.getConsumerDescription());
					in.addValue("decomm_comments", entry.getDecomissioningComments());
					in.addValue("user_name", user.getUid());
					in.addValue("servc_desc", entry.getServiceDescription());
					in.addValue("servc_provider", entry.getProvider());
					in.addValue("servc_type", entry.getServiceType());
					in.addValue("servc_networkscope", entry.getNetworkScope());
					in.addValue("servc_oper", entry.getOperations());
					in.addValue("ver_transport_type", entry.getTransportType());
					in.addValue("ver_canonicalData", entry.getCanonicalDataModel());
					in.addValue("ver_event_type", entry.getEventType());
					in.addValue("ver_frequency", entry.getFrequency());
					in.addValue("dom_name", entry.getDomainName());
					in.addValue("servc_security", entry.getSecurity());
					in.addValue("ver_comments", entry.getComments());
					in.addValue("ver_status_id", entry.getStatusId());

					Map out = simpleJdbcCall.execute(in);
					String errorCode = out.get("out_error_code").toString();

					if(errorCode.equals("09")){//Error code from stored proc
						LOGGER.error("Code from stored proc : " + errorCode);
						details.append("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;Row "+row.getRowNum()+" : Failed : Exception occurred while saving the record.<br/>");
						checkSum = false;
					}else{
						partialSuccess = true;
						details.append("<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;Row "+row.getRowNum()+" : Success!<br/>");
					}
				} catch (UnsupportedDataTypeException ex) {
					LOGGER.error("Exception loading record : "+ row.getRowNum() + " , " + ex.getMessage());
					details.append("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;Row "+row.getRowNum()+" : Failed : "+ex.getMessage()+"<br/>");
					checkSum  = false;
				}
			}
		}
		response.setDetails(details.toString());
		
		try {
			wb.close();
		} catch (IOException e) {
			LOGGER.error("Exception closing resources "+e.getMessage());
		}

		if(partialSuccess){
			response.setSuccess(false);
			response.setMessage("Upload Partially Successful!");
			if(checkSum){
				response.setSuccess(true);
				response.setMessage("Upload Successful!");
			}
		}
		return response;
	}
}
