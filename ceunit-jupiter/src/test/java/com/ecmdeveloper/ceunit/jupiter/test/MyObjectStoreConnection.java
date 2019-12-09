/**
 * Copyright (C) 2019 ECM Partners - All Rights Reserved
 */
package com.ecmdeveloper.ceunit.jupiter.test;

import com.ecmdeveloper.ceunit.jupiter.ContentEngineConfiguration;

/**
 * @author Ricardo Belfor
 *
 */
public class MyObjectStoreConnection implements ContentEngineConfiguration {

	@Override
	public String username() {
		return "P8Admin";
	}

	@Override
	public String password() {
		return "This is not the password!";
	}

	@Override
	public String url() {
		return "http://192.168.174.82:9080/wsi/FNCEWS40MTOM/";
	}

	@Override
	public String objectStoreName() {
		return "OS";
	}
}
