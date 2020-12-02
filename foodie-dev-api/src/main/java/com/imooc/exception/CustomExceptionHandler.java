package com.imooc.exception;

import com.imooc.utils.IMOOCJSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: huangqn
 * @Date: 2020/12/1 16:26
 * @Description: 全局异常统一处理
 */
@RestControllerAdvice
public class CustomExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler
	public IMOOCJSONResult handlerException(HttpServletRequest req, HttpServletResponse res, Exception e) {
//		log.info(e.getMessage(), e);
		if (res.getStatus() == HttpStatus.BAD_REQUEST.value()) {
			log.info("修改返回状态值为200");
			res.setStatus(HttpStatus.OK.value());
		}
		if (e instanceof NullPointerException) {
			log.error("空指针异常：" + e.getMessage(), e);
			return IMOOCJSONResult.errorMsg("空指针异常");
		} else if (e instanceof IllegalArgumentException) {
			log.error("参数类型不匹配异常：" + e.getMessage(), e);
			return IMOOCJSONResult.errorMsg("参数类型不匹配异常");
		}else if (e instanceof NumberFormatException) {
			log.error("数字类型转换异常：" + e.getMessage(), e);
			return IMOOCJSONResult.errorMsg("数字类型转换异常");
		}else if (e instanceof MaxUploadSizeExceededException) {
			log.error("文件上传大小不能超过200KB，请压缩图片或者降低图片质量：" + e.getMessage(), e);
			return IMOOCJSONResult.errorMsg("文件上传大小不能超过200KB，请压缩图片或者降低图片质量");
		} else {
			log.error("服务器异常，请稍后重试：" + e.getMessage(), e);
			return IMOOCJSONResult.errorMsg("服务器异常，请稍后重试");
		}
	}

}
