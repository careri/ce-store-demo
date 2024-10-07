package com.careri78.stores.domain;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "outbox_entry")
/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
public class OutboxEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "source")
    private String source;

    @Column(name = "content")
    private String content;

    @Column(name = "name")
    private String name;

    @Column(name = "timestamp")
    private String timestampUtc;

    public String getTimestampUtc() {
        return timestampUtc;
    }

    public Instant toTimestampInstant() {
        return Instant.parse(timestampUtc);
    }

    public void setTimestampUtc(String timestampUtc) {
        this.timestampUtc = timestampUtc;
    }

    public OutboxEntry() {
        timestampUtc = Instant.now().toString();
    }

    public OutboxEntry(final String name, final String source) {
        this(name, source, "");
    }

    public OutboxEntry(final String name, final String source, final String content) {
        this();
        setName(name);
        setSource(source);
        setContent(content);
    }    

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        if (!StringUtils.isNoneBlank(name)) {
            throw new IllegalArgumentException("Name can't be empty");
        }

        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) throws IllegalArgumentException {
        if (!StringUtils.isNoneBlank(source)) {
            throw new IllegalArgumentException("Source can't be empty");
        }

        this.source = source;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Object getEvent(ObjectMapper mapper) throws IOException, ClassNotFoundException {
        Class<?> contentClass = Class.forName(this.getName());
        return mapper.reader().readValue(this.getContent(), contentClass);
    }
}
