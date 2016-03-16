package examples.server

import pl.com.stream.verto.crm.activity.server.pub.main.ActivityDto
import pl.com.stream.verto.crm.activity.server.pub.main.ActivityService
class S1A_HelloWorld extends pl.com.stream.next.asen.common.groovy.ServerScriptEnv {
    def script() {
        def   ActivityService activityService = context.getService(ActivityService.class);
        def ActivityDto dto;

        activityService.completet(dto.idActivityy)

        activityService.complete(dto.idActivity);
    }
}
