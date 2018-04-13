package io.github.santulator.session;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionState {
    public static final String FORMAT_NAME = "santulator";

    private String formatName = FORMAT_NAME;

    private int formatVersion = SessionFormatVersion.LATEST_VERSION;

    private String drawName;

    private String password;

    private String directory;

    private List<ParticipantState> participants = Collections.emptyList();

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(final String formatName) {
        this.formatName = formatName;
    }

    public int getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(final int formatVersion) {
        this.formatVersion = formatVersion;
    }

    public String getDrawName() {
        return drawName;
    }

    public void setDrawName(final String drawName) {
        this.drawName = drawName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(final String directory) {
        this.directory = directory;
    }

    public List<ParticipantState> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public void setParticipants(final List<ParticipantState> participants) {
        this.participants = new ArrayList<>(participants);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionState that = (SessionState) o;

        return new EqualsBuilder()
            .append(formatVersion, that.formatVersion)
            .append(formatName, that.formatName)
            .append(drawName, that.drawName)
            .append(password, that.password)
            .append(directory, that.directory)
            .append(participants, that.participants)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(formatName)
            .append(formatVersion)
            .append(drawName)
            .append(password)
            .append(directory)
            .append(participants)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("formatName", formatName)
            .append("formatVersion", formatVersion)
            .append("drawName", drawName)
            .append("password", password)
            .append("directory", directory)
            .append("participants", participants)
            .toString();
    }
}
