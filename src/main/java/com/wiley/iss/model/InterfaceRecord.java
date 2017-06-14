package com.wiley.iss.model;


import com.wiley.iss.ApplicationConstants;

public class InterfaceRecord {
	private int serviceId;
	private String serviceName;
	private int serviceStatus;
	private String serviceStatusDesc;
	private String purpose;
	private String useCase;
	private String taxonomy;
	private String source;
	private String target;
	
	private String contextDiagram;
	
	private String integrationType;
	private String serviceType;
	private String frequency;
	private String transportType;
	private String dataType;
	private String designDoc;
	
	private String sourceTargetMapping;
	private String testEnvironments;
	private String schemaDefinitions;
	private String sampleRequest;
	private String sampleResponse;
	private String sampleRequestFile;
	private String sampleResponseFile;
	private String otherServices;
	private String additionalComments;
	
	private String fileName;
	private String testInSIT;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + serviceId;
		result = prime * result
				+ ((serviceName == null) ? 0 : serviceName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterfaceRecord other = (InterfaceRecord) obj;
		if (serviceId != other.serviceId)
			return false;
		if (serviceName == null) {
			if (other.serviceName != null)
				return false;
		} else if (!serviceName.equals(other.serviceName))
			return false;
		return true;
	}
	
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public void setServiceId(String serviceId) {
		try{
			this.serviceId = Integer.parseInt(serviceId);	
		}catch(NumberFormatException e){
			System.out.println("NumberFormatException: "+e.getMessage()+ " : "+this.getFileName());
		}
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		Integer status = ApplicationConstants.status.get(serviceStatus.replace(" ", "").toUpperCase());
		if(status==null){
			System.out.println("STATUS Not Found: "+serviceStatus+ " : "+this.getFileName());
		}
		this.serviceStatus = status;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getUseCase() {
		return useCase;
	}
	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}
	public String getTaxonomy() {
		return taxonomy;
	}
	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getContextDiagram() {
		return contextDiagram;
	}
	public void setContextDiagram(String contextDiagram) {
		this.contextDiagram = contextDiagram;
	}
	public String getIntegrationType() {
		return integrationType;
	}
	public void setIntegrationType(String integrationType) {
		this.integrationType = integrationType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDesignDoc() {
		return designDoc;
	}
	
	public void setDesignDoc(String designDoc) {
		this.designDoc = designDoc;
	}
	public String getSourceTargetMapping() {
		return sourceTargetMapping;
	}
	public void setSourceTargetMapping(String sourceTargetMapping) {
		this.sourceTargetMapping = sourceTargetMapping;
	}
	public String getTestEnvironments() {
		return testEnvironments;
	}
	public void setTestEnvironments(String testEnvironments) {
		this.testEnvironments = testEnvironments;
	}
	public String getSchemaDefinitions() {
		return schemaDefinitions;
	}
	public void setSchemaDefinitions(String schemaDefinitions) {
		this.schemaDefinitions = schemaDefinitions;
	}
	public String getSampleRequest() {
		return sampleRequest;
	}
	public void setSampleRequest(String sampleRequest) {
		this.sampleRequest = sampleRequest;
	}
	public String getSampleResponse() {
		return sampleResponse;
	}
	public void setSampleResponse(String sampleResponse) {
		this.sampleResponse = sampleResponse;
	}
	public String getOtherServices() {
		return otherServices;
	}
	public void setOtherServices(String otherServices) {
		this.otherServices = otherServices;
	}
	public String getAdditionalComments() {
		return additionalComments;
	}
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTestInSIT() {
		return testInSIT;
	}
	public void setTestInSIT(String testInSIT) {
		this.testInSIT = testInSIT;
	}
	public String getSampleResponseFile() {
		return sampleResponseFile;
	}
	public void setSampleResponseFile(String sampleResponseFile) {
		this.sampleResponseFile = sampleResponseFile;
	}
	public String getSampleRequestFile() {
		return sampleRequestFile;
	}
	public void setSampleRequestFile(String sampleRequestFile) {
		this.sampleRequestFile = sampleRequestFile;
	}
	public String getServiceStatusDesc() {
		return serviceStatusDesc;
	}
	public void setServiceStatusDesc(String serviceStatusDesc) {
		this.serviceStatusDesc = serviceStatusDesc;
	}
}
