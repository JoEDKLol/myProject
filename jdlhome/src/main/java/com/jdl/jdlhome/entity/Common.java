package com.jdl.jdlhome.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@MappedSuperclass
public abstract class Common {
    @CreationTimestamp
    @Column(updatable = false)
    private String reg_date;
    private String reg_user;

    @CreationTimestamp
    private String up_date;
    private String up_id;

}
