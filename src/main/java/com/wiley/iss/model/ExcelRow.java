package com.wiley.iss.model;

import javax.activation.UnsupportedDataTypeException;

import com.wiley.iss.ApplicationConstants;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class ExcelRow {
	private String domainName;
	private String serviceName;
	private String serviceDescription;
	private Float serviceVersion;
	private String consumerName;
	private String consumerDescription;
	private String comments;
	private String decomissioningComments;
	
//	private int  serviceId;
//	private int consumerId;
	private int statusId;


	private String security;
	private String provider;
	private String serviceType;
	private String networkScope;
	private String operations;
	private String transportType;
	private String canonicalDataModel;
	private String eventType;
	
	public ExcelRow(Row row) throws UnsupportedDataTypeException{
		
		setDomainName(row.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setServiceName(row.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setServiceVersion(row.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setConsumerName(row.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setDecomissioningComments(row.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		
		setConsumerDescription(row.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setServiceDescription(row.getCell(7, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		
		setProvider(row.getCell(8, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setServiceType(row.getCell(9, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setNetworkScope(row.getCell(10, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setOperations(row.getCell(11, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		
		setTransportType(row.getCell(12, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setCanonicalDataModel(row.getCell(13, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setEventType(row.getCell(14, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setStatusId(row.getCell(15, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setSecurity(row.getCell(16, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
		setComments(row.getCell(17, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());

	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public void setStatusId(String statusDesc) {
		Integer status = ApplicationConstants.status.get(statusDesc.replace(" ", "").toUpperCase());
		if(status==null){
			status = 4;
		}
		this.statusId = status;
	}

	public String getSecurity() {
		return security;
	}

	private void setSecurity(String security) {
		this.security = security;
	}

	public String getProvider() {
		return provider;
	}

	private void setProvider(String provider) {
		this.provider = provider;
	}

	public String getServiceType() {
		return serviceType;
	}

	private void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getNetworkScope() {
		return networkScope;
	}

	private void setNetworkScope(String networkScope) {
		this.networkScope = networkScope;
	}

	public String getOperations() {
		return operations;
	}

	private void setOperations(String operations) {
		this.operations = operations;
	}

	public String getTransportType() {
		return transportType;
	}

	private void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getCanonicalDataModel() {
		return canonicalDataModel;
	}

	private void setCanonicalDataModel(String canonicalDataModel) {
		this.canonicalDataModel = canonicalDataModel;
	}

	public String getEventType() {
		return eventType;
	}

	private void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString() {
		return "ExcelRow [domainName=" + domainName + ", serviceName="
				+ serviceName + ", serviceDescription=" + serviceDescription
				+ ", serviceVersion=" + serviceVersion + ", consumerName="
				+ consumerName + ", consumerDescription=" + consumerDescription
				+ ", comments=" + comments + ", decomissioningComments="
				+ decomissioningComments +
//				", serviceId=" + serviceId
//				+ ", consumerId=" + consumerId +
				", security=" + security
				+ ", provider=" + provider + ", serviceType=" + serviceType
				+ ", networkScope=" + networkScope + ", operations=" + operations
				+ ", transportType=" + transportType + ", canonicalDataModel="
				+ canonicalDataModel + ", eventType=" + eventType + "]";
	}

	public String getDomainName() {
		return domainName;
	}
	private void setDomainName(String domainName) throws UnsupportedDataTypeException  {
		if(domainName.equals("")){
			throw new UnsupportedDataTypeException("Domain Name cannot be empty.");
		}
		this.domainName = domainName;
	}
	public String getServiceName() {
		return serviceName;
	}
	private void setServiceName(String serviceName) throws UnsupportedDataTypeException {
		if(serviceName.equals("")){
			throw new UnsupportedDataTypeException("Service Name cannot be empty.");
		}
		this.serviceName = serviceName;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	private void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription.equals("") ? serviceName: serviceDescription;
	}
	public Float getServiceVersion() {
		return serviceVersion;
	}
	private void setServiceVersion(String serviceVersion) throws UnsupportedDataTypeException {
		try{
			this.serviceVersion  = Float.parseFloat(serviceVersion);	
		}catch(NullPointerException | NumberFormatException ex){
			throw new UnsupportedDataTypeException("Service Version has to be a Number.");
		}
		
	}
	public String getConsumerName() {
		return consumerName;
	}
	private void setConsumerName(String consumerName)throws UnsupportedDataTypeException {
		if(consumerName.equals("")){
			throw new UnsupportedDataTypeException("Consumer Name cannot be empty.");
		}
		this.consumerName = consumerName;
	}

	public String getComments() {
		return comments;
	}

	private void setComments(String comments) {
		this.comments = comments;
	}

	public String getConsumerDescription() {
		return consumerDescription;
	}

	private void setConsumerDescription(String consumerDescription) {
		this.consumerDescription = consumerDescription.equals("") ? consumerName: consumerDescription;
	}

	public String getDecomissioningComments() {
		return decomissioningComments;
	}

	private void setDecomissioningComments(String decomissioningComments) {
		this.decomissioningComments = decomissioningComments;
	}
}
