package com.imooc.controller.center;

import com.imooc.bo.center.CenterUsersBO;
import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.resource.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: huangqn
 * @Date: 2020/12/1 09:35
 * @Description: 用户中心
 */
@Api(value = "用户信息", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

	@Autowired
	private CenterUserService centerUserService;
	@Autowired
	private FileUpload fileUpload;

	@GetMapping("/1")
	public String get1() throws Exception{
		try {
			int i = 1 / 0;
		} catch (Exception e) {
			throw new Exception("123");
		}
		return "1";
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
	public IMOOCJSONResult update(@ApiParam(name = "userId", value = "用户Id", required = true)
	                              @RequestParam String userId,
	                              @RequestBody @Valid CenterUsersBO centerUsersBO,
	                              BindingResult result,
	                              HttpServletRequest request, HttpServletResponse response
	) {
		if (result.hasErrors()) {
			Map<String, String> errors = getErrors(result);
//			return IMOOCJSONResult.errorMap(errors);

			return IMOOCJSONResult.errorMsg(getErrorsStr(result));
		}
		Users users = centerUserService.updateUserInfo(userId, centerUsersBO);
		this.setNullProperty(users);
		CookieUtils.setCookie(request, response, "user",
				JsonUtils.objectToJson(users), true);

		// TODO 后续要改，增加token，整合进redis，分布式会话

		return IMOOCJSONResult.ok();
	}


	@PostMapping("/uploadFace")
	@ApiOperation(value = "修改用户头像", notes = "修改用户头像", httpMethod = "POST")
	public IMOOCJSONResult uploadFace(@ApiParam(name = "userId", value = "用户Id", required = true)
	                                      @RequestParam String userId,
	                                      @ApiParam(name = "file", value = "用户头像", required = true)
	                                      @RequestParam MultipartFile file,
	                                      HttpServletRequest request, HttpServletResponse response
	) {
		// 头像保存地址
//		String fileSpace = IMAGE_USER_FACE_LOCATION;
		String fileSpace = fileUpload.getImageUserFaceLocation();
		// 在路径线下，为每个用户增加一个userId，用于区分用户上传
		String updateLoadPath = "faces" + File.separator + userId + File.separator;
		if (file == null) {
			return IMOOCJSONResult.errorMsg("上传头像不能为空");
		}
		String newFileName = "";
		String filename = file.getOriginalFilename();
		if (StringUtils.isNotEmpty(filename)) {
			// face-{userId}.png
			String[] fileNameArr = filename.split("\\.");
			// 获取文件后缀名
			String suffix = fileNameArr[fileNameArr.length - 1];
			if (!"png".equalsIgnoreCase(suffix) &&
					!"jpg".equalsIgnoreCase(suffix) &&
					!"jpeg".equalsIgnoreCase(suffix)){
				return IMOOCJSONResult.errorMsg("图片格式不正确！");
			}
			// 文件名称重组 // 覆盖式上传，如要增量式，则在文件名后面加时间戳
			newFileName = "face-" + userId + "-" + DateUtil.dateToString(new Date(), DateUtil.DATE_PATTERN) + "." + suffix;
			// 上传头像最终保存位置
			String fileFacePath = fileSpace + updateLoadPath + newFileName;
			File outFile = new File(fileFacePath);
			if (outFile.getParentFile() != null){
				outFile.getParentFile().mkdirs();
			}
			// 文件输出，保存
			FileOutputStream fileOutputStream = null;
			InputStream inputStream = null;
			try {
				fileOutputStream = new FileOutputStream(outFile);
				inputStream = file.getInputStream();
				IOUtils.copy(inputStream,fileOutputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if (fileOutputStream != null){
					try {
						fileOutputStream.flush();
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(inputStream != null){
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
		String imgUrl = fileUpload.getImageServerUrl() + userId + "/" + newFileName;
		imgUrl += "?t="+DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
		Users users = centerUserService.updateUserFace(userId,imgUrl);
		this.setNullProperty(users);
		CookieUtils.setCookie(request, response, "user",
				JsonUtils.objectToJson(users), true);
		return IMOOCJSONResult.ok(users);
	}


	private Map<String, String> getErrors(BindingResult result) {
		Map<String, String> resultMap = new HashMap<>();
		List<FieldError> fieldErrors = result.getFieldErrors();
		for (FieldError error : fieldErrors) {
			// 某个错误属性，属性对应的错误信息
			resultMap.put(error.getField(), error.getDefaultMessage());
		}
		return resultMap;
	}

	private String getErrorsStr(BindingResult result) {
		List<FieldError> fieldErrors = result.getFieldErrors();
		String resultStr = "";
		for (FieldError error : fieldErrors) {
			String defaultMessage = error.getDefaultMessage();
			resultStr += defaultMessage + "\n";
		}
		return resultStr;
	}


	/**
	 * 将敏感信息置空
	 *
	 * @param users
	 * @return
	 */
	private Users setNullProperty(Users users) {
		users.setPassword(null);
		users.setRealname(null);
		users.setEmail(null);
		users.setMobile(null);
		users.setCreatedTime(null);
		users.setUpdatedTime(null);
		users.setBirthday(null);
		return users;
	}


}
