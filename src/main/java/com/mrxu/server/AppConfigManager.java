package com.mrxu.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: ztowh
 * @Date: 2019-03-23 17:22
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfigManager {

	/*
	 * 60秒
	 */
	private int timeout = 60000;

	/*
	 * 20秒
	 */
	private int interval = 20000;
	/*
	 * 跨域
	 */
	private String origin;
}
