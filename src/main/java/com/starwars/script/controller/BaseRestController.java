package com.starwars.script.controller;


import com.starwars.script.exception.DefaultErrorMessage;
import com.starwars.script.exception.ForbiddenMessage;
import com.starwars.script.service.ScriptProcessorService;
import com.starwars.script.util.JSONBasicInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.starwars.script.util.SWConstants.MOVIE_SCRIPT_SUCCESS;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(value = "Starwars Script")
public class BaseRestController {

	protected final ScriptProcessorService service;

	@RequestMapping(value = "/api/script", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	@ApiOperation(value = "Submit text via post")
	public ResponseEntity<JSONBasicInfo> upload(@RequestBody String request) throws DefaultErrorMessage, ForbiddenMessage {
		log.info("Uploading file with length: {}", request.length());
		if(service.isEmpty()){
			service.saveScript(request);
			return new ResponseEntity<>(new JSONBasicInfo(MOVIE_SCRIPT_SUCCESS),HttpStatus.OK);
		} else {
			throw new ForbiddenMessage();
		}
	}
}