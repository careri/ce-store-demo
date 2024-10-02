package com.careri78.stores.domain;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
public class OutboxEntry {
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
    private long timestampMilli;

    public long getTimestampMilli() {
        return timestampMilli;
    }

    public Instant timestampAsInstant() {
        return Instant.ofEpochMilli(timestampMilli);
    }

    public void setTimestampMilli(long timestampMilli) {
        this.timestampMilli = timestampMilli;
    }

    public OutboxEntry() {
    }

    public OutboxEntry(final String name, final String source, final String content) {
        this();
        setName(name);
        setSource(source);
        setContent(content);
        timestampMilli = Instant.now().toEpochMilli();
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
}
