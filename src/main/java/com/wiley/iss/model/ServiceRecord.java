package com.wiley.iss.model;

public class ServiceRecord {

	private int svcId;
	private String domainName;
	private String svcName;
	private String svcDescription;
	private String svcOperations;
	private String provider;
	private float versionId;
	private String consumerName;
	private String canonicalDataModel;
	private String networkScope;
	private String eventType;
	private String protocol;
	private String svcSecurity;
	private String providerSLA;
	private String tiersSLA;
	private String consumerCLA;
	private String status;
	private String initiative;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((consumerName == null) ? 0 : consumerName.hashCode());
		result = prime * result
				+ ((domainName == null) ? 0 : domainName.hashCode());
		result = prime * result + svcId;
		result = prime * result + ((svcName == null) ? 0 : svcName.hashCode());
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
		ServiceRecord other = (ServiceRecord) obj;
		if (consumerName == null) {
			if (other.consumerName != null)
				return false;
		} else if (!consumerName.equals(other.consumerName))
			return false;
		if (domainName == null) {
			if (other.domainName != null)
				return false;
		} else if (!domainName.equals(other.domainName))
			return false;
		if (svcId != other.svcId)
			return false;
		if (svcName == null) {
			if (other.svcName != null)
				return false;
		} else if (!svcName.equals(other.svcName))
			return false;
		if (Float.floatToIntBits(versionId) != Float
				.floatToIntBits(other.versionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceRecord [svcId=" + svcId + ", domainName=" + domainName
				+ ", svcName=" + svcName + ", svcDescription=" + svcDescription
				+ ", svcOperations=" + svcOperations + ", provider=" + provider
				+ ", versionId=" + versionId + ", consumerName=" + consumerName
				+ ", canonicalDataModel=" + canonicalDataModel
				+ ", networkScope=" + networkScope + ", eventType=" + eventType
				+ ", protocol=" + protocol + ", svcSecurity=" + svcSecurity
				+ ", providerSLA=" + providerSLA + ", tiersSLA=" + tiersSLA
				+ ", consumerCLA=" + consumerCLA + ", status=" + status
				+ ", initiative=" + initiative + "]";
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
	public String getSvcOperations() {
		return svcOperations;
	}
	public void setSvcOperations(String svcOperations) {
		this.svcOperations = svcOperations;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public float getVersionId() {
		return versionId;
	}
	public void setVersionId(float versionId) {
		this.versionId = versionId;
	}

	public String getNetworkScope() {
		return networkScope;
	}

	public void setNetworkScope(String networkScope) {
		this.networkScope = networkScope;
	}

	public String getCanonicalDataModel() {
		return canonicalDataModel;
	}

	public void setCanonicalDataModel(String canonicalDataModel) {
		this.canonicalDataModel = canonicalDataModel;
	}

	public String getSvcSecurity() {
		return svcSecurity;
	}

	public void setSvcSecurity(String svcSecurity) {
		this.svcSecurity = svcSecurity;
	}

	public String getProviderSLA() {
		return providerSLA;
	}

	public void setProviderSLA(String providerSLA) {
		this.providerSLA = providerSLA;
	}

	public String getConsumerCLA() {
		return consumerCLA;
	}

	public void setConsumerCLA(String consumerCLA) {
		this.consumerCLA = consumerCLA;
	}

	public String getInitiative() {
		return initiative;
	}

	public void setInitiative(String initiative) {
		this.initiative = initiative;
	}

	public String getTiersSLA() {
		return tiersSLA;
	}

	public void setTiersSLA(String tiersSLA) {
		this.tiersSLA = tiersSLA;
	}	
}
