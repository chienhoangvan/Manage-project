package com.project.jobworking.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Media extends BaseEntity {

    @Column(name = "name", unique = true)
    private String fileName;

    @Column(name = "url_download", unique = true)
    private String fileDownloadUri;

    @Column(name = "file_type")
    private String fileType;

    private long size;

    private Long projectId;

    private Long jobId;

    private Long reportId;

    private Long commentId;

    public Media(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public Media() {
    }
}
