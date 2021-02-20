package com.hcg.simpletaskmanager.base.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * This class include network transfer data model base properties
 * If you need another transfer data model you could extend this class
 */
public class BaseModel implements Serializable {
    private Integer id;

    private Date createdDate;

    private Date updatedDate;

    private Long version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
