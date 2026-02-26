/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.util.Map;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.custom.CustomConfiguration;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateKeeperConstants;
import com.genohm.slimsgate.camel.gatekeeper.SlimsProxy;
import com.genohm.slimsgateclient.workflow.SlimsFlowInitParam;

@Component
public class FileUpload {

	@Autowired
	private CustomConfiguration customConfiguration;

	@Transactional
	@Handler
	public void validateFile(@Header(SlimsGateKeeperConstants.SLIMS_PROXY) SlimsProxy slimsProxy,
							 @Header(SlimsGateKeeperConstants.SLIMS_WORKFLOW_INIT_PARAMETER)  SlimsFlowInitParam initParam)
	{
		Logger log = slimsProxy.getLogger(getClass());
		log.info("Processing file upload, this is my configuration: \n" +
				"parameterOne: " + customConfiguration.getParameterOne() + "\n" +
				"parameterTwo: " + customConfiguration.getParameterTwo());

		log.info(initParam.getInputParameterValues().toString());

		// access the file
		Map<String, Object> inputParameters = initParam.getInputParameterValues();
		String commentEntered = StringUtil.getAsString(inputParameters.get("strCommentInputHandle"));
		String textCommentEntered = StringUtil.getAsString(inputParameters.get("txtCommentInputHandle"));
		Object dateSelected = inputParameters.get("dateSelectorHandle");
		log.info("Comment entered (String): " + commentEntered);
		log.info("Comment entered (Text): " + textCommentEntered);
		log.info("Date selected: " + dateSelected);
	}
	
}
