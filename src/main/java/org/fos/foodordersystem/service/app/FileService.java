package org.fos.foodordersystem.service.app;

import org.fos.foodordersystem.model.enums.TipeUpload;
import org.fos.foodordersystem.model.response.BaseResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    BaseResponse<?> upload(MultipartFile files, TipeUpload tipeUpload);

    Resource loadFileAsResource(String pathFile);

}