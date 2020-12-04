package com.imooc.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: huangqn
 * @Date: 2020/12/1 14:58
 * @Description:
 */
@Component
public class FileUpload {

	@Value(value = "${file.imageUserFaceLocation}")
	private String imageUserFaceLocation;

	@Value("${file.imageServerUrl}")
	private String imageServerUrl;

	public String getImageUserFaceLocation() {
		return imageUserFaceLocation;
	}

	public void setImageUserFaceLocation(String imageUserFaceLocation) {
		this.imageUserFaceLocation = imageUserFaceLocation;
	}

	public String getImageServerUrl() {
		return imageServerUrl;
	}

	public void setImageServerUrl(String imageServerUrl) {
		this.imageServerUrl = imageServerUrl;
	}
}
