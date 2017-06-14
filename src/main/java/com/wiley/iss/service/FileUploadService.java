package com.wiley.iss.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.activation.UnsupportedDataTypeException;

import com.wiley.iss.model.ExcelRow;
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


	private static final String insertServiceConsumer = "{call svc_catalog_pkg.svc_update_svc_consumer(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public Response uploadFile(InputStream inputStream, UserDTO user) throws SQLException {
		
		ExcelRow entry;
		Cell cell;
		Workbook wb;
		Sheet uploadTemplate;
		Response response = new Response();
		response.setMessage("Upload failed!");// Default Status
		
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
		
		boolean partialSuccess =false;
		boolean checkSum = true;
		
		StringBuilder details = new StringBuilder();
		
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		if(connection ==null){
			response.setMessage("DataBase Connection Exception!");
			LOGGER.error("DataBase Connection Exception!");
			try {
				wb.close();
			} catch (IOException e1) {
				LOGGER.error("Exception closing wb "+e1.getMessage());
			}
			return response;
		}
		
		for (Row row : uploadTemplate) {
			cell = row.getCell(1, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			// Ignore Header(0) & Empty Records
			if (row.getRowNum() > 0 && cell != null && !cell.toString().isEmpty()) {
				try {
					entry = new ExcelRow(row);
					CallableStatement callStmt = connection.prepareCall(insertServiceConsumer);
					callStmt.setString(1, entry.getServiceName());
					callStmt.setFloat(2, entry.getServiceVersion());
					callStmt.setString(3, entry.getConsumerName());
					//callStmt.setString(4, entry.getComments());
					callStmt.setString(4, entry.getConsumerDescription());
					callStmt.setString(5, entry.getDecomissioningComments());
					callStmt.setString(6, user.getUid());
					
					callStmt.setString(7, entry.getServiceDescription());
					//callStmt.setString(9, entry.getSecurity());
					callStmt.setString(8, entry.getProvider());
					callStmt.setString(9, entry.getServiceType());
					callStmt.setString(10, entry.getNetworkScope());
					callStmt.setString(11, entry.getOperations());

					callStmt.setString(12, entry.getTransportType());
					callStmt.setString(13, entry.getCanonicalDataModel());
					callStmt.setString(14, entry.getEventType());
					callStmt.setString(15, entry.getDomainName());
					callStmt.setString(16, entry.getSecurity());
					callStmt.setString(17, entry.getComments());
					callStmt.setInt(18, entry.getStatusId());

					callStmt.registerOutParameter(19, java.sql.Types.VARCHAR);

					callStmt.execute();
					String errorCode = callStmt.getString(19);
					
					callStmt.close();
					
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
				}catch(SQLException e){
					response.setMessage("Upload failed! "+e.getMessage());
				}
			}
		}
		response.setDetails(details.toString());
		
		try {
			connection.close();
			wb.close();
		} catch (IOException |SQLException e) {
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
