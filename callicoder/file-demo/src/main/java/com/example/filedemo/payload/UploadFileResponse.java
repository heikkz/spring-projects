package com.example.filedemo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadFileResponse {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
