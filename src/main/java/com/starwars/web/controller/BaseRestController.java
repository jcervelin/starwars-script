package com.starwars.web.controller;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.dao.BaseDao;
import com.starwars.exception.DefaultErrorMessage;
import com.starwars.exception.ForbiddenMessage;
import com.starwars.exception.NotFoundMessage;
import com.starwars.model.Setting;
import com.starwars.model.Character;
import com.starwars.service.StarWarsScriptService;
import com.starwars.util.JSONBasicInfo;

@RestController
@Transactional
public class BaseRestController {
	@Autowired
	protected StarWarsScriptService service;
	@Autowired
	protected BaseDao<Setting> settingDao;
	@Autowired
	protected BaseDao<Character> characterDao;
	
	private final Logger logger = LoggerFactory.getLogger(BaseRestController.class);

	@RequestMapping(value = "/script", method = RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public ResponseEntity<JSONBasicInfo> upload(Model model,@RequestBody String request) throws Exception {
		try {
			logger.info(request);
			if(settingDao.IsEmpty()){
				service.saveScript(request);
				return new ResponseEntity<JSONBasicInfo>(new JSONBasicInfo("Movie script successfully received"),HttpStatus.OK);
			} else {
				throw new ForbiddenMessage();
			}
		} catch (ForbiddenMessage e) {
			logger.error(e.getMessage(),e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}
	
	@ExceptionHandler(ForbiddenMessage.class)
	@ResponseBody
	public ResponseEntity<JSONBasicInfo> handleForbiddenResourceException(
			ForbiddenMessage ex) {
		JSONBasicInfo info = new JSONBasicInfo();
		info.setMessage(ex.getMessage());
		return new ResponseEntity<JSONBasicInfo>(info, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(NotFoundMessage.class)
	@ResponseBody
	public ResponseEntity<JSONBasicInfo> handleNotFoundResourceException(
			NotFoundMessage ex) {
		JSONBasicInfo info = new JSONBasicInfo();
		info.setMessage(ex.getMessage());
		return new ResponseEntity<JSONBasicInfo>(info, HttpStatus.NOT_FOUND);
	}	

	@ExceptionHandler(DefaultErrorMessage.class)
	@ResponseBody
	public ResponseEntity<JSONBasicInfo> handleDefaultException(
			DefaultErrorMessage ex) {
		JSONBasicInfo info = new JSONBasicInfo();
		info.setMessage(ex.getMessage());
		return new ResponseEntity<JSONBasicInfo>(info, HttpStatus.BAD_REQUEST);
	}
}