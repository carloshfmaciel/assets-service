package com.integrador.assets.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.integrador.assets.service.AssetUpdateService;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
public class AssetCron {

	@Autowired
	private AssetUpdateService assetUpdateService;

	@Scheduled(cron = "0 0/5 * * * ?")
	@SchedulerLock(name = "AssetCron_execute()", lockAtMostFor = "5m", lockAtLeastFor = "30s")
	public void execute() {
		assetUpdateService.update();
	}

}
