package com.hcg.simpletaskmanager.base.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This class include all db tables common properties of this system.
 * If you need another db table mapping domain object you could extend this class
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
    /**
     * Unique auto incremental primary key for db table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;


    /**
     * Created date row data
     */
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdDate;

    /**
     * last updated date row data
     */
    @Column(name = "UPDATED_DATE", nullable = false)
    @UpdateTimestamp
    private Date updatedDate;

    /**
     * object version for row data
     * This feature is used to manage more than one person making data changes at the same time.
     */
    @org.springframework.data.annotation.Version
    @Column(name = "OBJ_VERSION")
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

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass() && (obj instanceof BaseEntity && (id != null) ? id.equals(((BaseEntity) obj).id) : (obj == this));
    }

    @Override
    public int hashCode() {
        return id != null ? this.getClass().hashCode() + id.hashCode() : super.hashCode();
    }

}
