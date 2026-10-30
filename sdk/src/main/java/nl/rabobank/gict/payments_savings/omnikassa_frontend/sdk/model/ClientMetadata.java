package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import java.util.Objects;

/**
 * Container for client metadata that is sent in the X-Api-User-Agent header.
 * Fields are immutable; use the Builder to create instances.
 */
public final class ClientMetadata {
    private final String userAgent;
    private final String partnerReference;
    private final String pluginName;
    private final String pluginVersion;

    ClientMetadata(Builder builder) {
        this.userAgent = builder.userAgent;
        this.partnerReference = builder.partnerReference;
        this.pluginName = builder.pluginName;
        this.pluginVersion = builder.pluginVersion;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getPartnerReference() {
        return partnerReference;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(ClientMetadata copy) {
        Builder builder = builder();
        if (copy == null) {
            return builder;
        }
        builder.userAgent = copy.userAgent;
        builder.partnerReference = copy.partnerReference;
        builder.pluginName = copy.pluginName;
        builder.pluginVersion = copy.pluginVersion;
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientMetadata that = (ClientMetadata) o;
        return Objects.equals(userAgent, that.userAgent)
                && Objects.equals(partnerReference, that.partnerReference)
                && Objects.equals(pluginName, that.pluginName)
                && Objects.equals(pluginVersion, that.pluginVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAgent, partnerReference, pluginName, pluginVersion);
    }

    @Override
    public String toString() {
        return "ClientMetadata{" +
                "userAgent='" + userAgent + '\'' +
                ", partnerReference='" + partnerReference + '\'' +
                ", pluginName='" + pluginName + '\'' +
                ", pluginVersion='" + pluginVersion + '\'' +
                '}';
    }

    public static final class Builder {
        private String userAgent;
        private String partnerReference;
        private String pluginName;
        private String pluginVersion;

        Builder() {
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder partnerReference(String partnerReference) {
            this.partnerReference = partnerReference;
            return this;
        }

        public Builder pluginName(String pluginName) {
            this.pluginName = pluginName;
            return this;
        }

        public Builder pluginVersion(String pluginVersion) {
            this.pluginVersion = pluginVersion;
            return this;
        }

        public ClientMetadata build() {
            return new ClientMetadata(this);
        }
    }
}
