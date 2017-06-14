package com.wiley.iss.model;

public class ServiceDetail {
	private int svcId;
	private String svcName;
	private String svcDescription;
	private String useCase;
	private String operations;
	private String provider;
	private String providerSLA;
	private int consumerId;
	private String consumerName;
	private String status;
	private float versionId;
	private String integrationType;
	private String serviceType;
	private String serviceFrequency;
	private String transportType;

	private String dataType;
	private String designDoc;
	private String designDocUrl;
	private String provConsMapping;
	private String provConsMappingUrl;
	private String versionImgUrl;
	private String schemaDefs;
	private String testEnvs;
	private String domainName;
	private String sampleRequest;
	private String sampleResponse;
	private String otherServices;
	private String comments;


	@Override
	public String toString() {
		return "ServiceDetail [svcId=" + svcId + ", svcName=" + svcName
				+ ", svcDescription=" + svcDescription + ", useCase=" + useCase
				+ ", operations=" + operations + ", provider=" + provider
				+ ", consumerName=" + consumerName + ", status=" + status
				+ ", versionId=" + versionId + ", integrationType="
				+ integrationType + ", serviceType=" + serviceType
				+ ", serviceFrequency=" + serviceFrequency + ", transportType="
				+ transportType + ", dataType=" + dataType + ", designDoc="
				+ designDoc + ", designDocUrl=" + designDocUrl
				+ ", provConsMapping=" + provConsMapping
				+ ", provConsMappingUrl=" + provConsMappingUrl
				+ ", schemaDefs=" + schemaDefs + ", testEnvs=" + testEnvs
				+ ", sampleRequest=" + sampleRequest + ", sampleResponse="
				+ sampleResponse + ", otherServices=" + otherServices
				+ ", comments=" + comments + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + consumerId;
		result = prime * result + svcId;
		result = prime * result + Float.floatToIntBits(versionId);
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
		ServiceDetail other = (ServiceDetail) obj;
		if (consumerId != other.consumerId)
			return false;
		if (svcId != other.svcId)
			return false;
		if (Float.floatToIntBits(versionId) != Float
				.floatToIntBits(other.versionId))
			return false;
		return true;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public int getSvcId() {
		return svcId;
	}
	public void setSvcId(int svcId) {
		this.svcId = svcId;
	}
	public String getSvcName() {
		return svcName;
	}
	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}
	public String getSvcDescription() {
		return svcDescription;
	}
	public void setSvcDescription(String svcDescription) {
		this.svcDescription = svcDescription;
	}
	public String getUseCase() {
		return useCase;
	}
	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getVersionId() {
		return versionId;
	}
	public void setVersionId(float versionId) {
		this.versionId = versionId;
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
	public String getServiceFrequency() {
		return serviceFrequency;
	}
	public void setServiceFrequency(String serviceFrequency) {
		this.serviceFrequency = serviceFrequency;
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
	public String getDesignDocUrl() {
		return designDocUrl;
	}
	public void setDesignDocUrl(String designDocUrl) {
		this.designDocUrl = designDocUrl;
	}
	public String getProvConsMapping() {
		return provConsMapping;
	}
	public void setProvConsMapping(String provConsMapping) {
		this.provConsMapping = provConsMapping;
	}
	public String getProvConsMappingUrl() {
		return provConsMappingUrl;
	}
	public void setProvConsMappingUrl(String provConsMappingUrl) {
		this.provConsMappingUrl = provConsMappingUrl;
	}
	public String getSchemaDefs() {
		return schemaDefs;
	}
	public void setSchemaDefs(String schemaDefs) {
		this.schemaDefs = schemaDefs;
	}
	public String getTestEnvs() {
		return testEnvs;
	}
	public void setTestEnvs(String testEnvs) {
		this.testEnvs = testEnvs;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}

	public String getProviderSLA() {
		return providerSLA;
	}

	public void setProviderSLA(String providerSLA) {
		this.providerSLA = providerSLA;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}


	public String getVersionImgUrl() {
		return versionImgUrl;
	}

	public void setVersionImgUrl(String versionImgUrl) {
		this.versionImgUrl = versionImgUrl;
	}


}
