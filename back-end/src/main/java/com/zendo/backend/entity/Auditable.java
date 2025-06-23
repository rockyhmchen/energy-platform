package com.zendo.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Auditable class is an abstract base class that provides auditing functionality for entities.
 * It includes fields for tracking the creation and last modification of an entity, as well as the user who performed these actions.
 * This class is annotated with JPA annotations to enable auditing and versioning.
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(of = {"createdBy", "createdAt", "updatedBy", "updatedAt", "version"})
@ToString(of = {"createdBy", "createdAt", "updatedBy", "updatedAt", "version"})
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    /**
     * The user who created the entity.
     */
    @CreatedBy
    protected String createdBy;

    /**
     * The date and time when the entity was created.
     */
    @CreatedDate
    protected LocalDateTime createdAt;

    /**
     * The user who last modified the entity.
     */
    @LastModifiedBy
    protected String updatedBy;

    /**
     * The date and time when the entity was last modified.
     */
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    /**
     * The version number of the entity, used for optimistic locking.
     */
    @Version
    protected Integer version;
}
