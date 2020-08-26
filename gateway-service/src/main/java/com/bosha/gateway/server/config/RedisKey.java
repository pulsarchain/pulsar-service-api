package com.bosha.gateway.server.config;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: RedisKey
 * @Author liqingping
 * @Date 2019-12-25 17:07
 */

public interface RedisKey {

    class AdminUser {
        private static final String PREFIX = "UserService:Admin:";

        public static final String LOGIN_MAP = PREFIX + "login_map";

        public static final String STATUS_DISABLED_MAP = PREFIX + "status_disabled_map";

        public static final String RESET_PASSWORD_MAP = PREFIX + "reset_password_map";

        public static final String PERMISSION_MAP = PREFIX + "permission_map:";

    }
}
