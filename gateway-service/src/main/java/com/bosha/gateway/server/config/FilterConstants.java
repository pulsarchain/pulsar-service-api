package com.bosha.gateway.server.config;

public interface FilterConstants {

    class Ordered {

        public static final int HEADER_FILTER = -3;

        public static final int ACCESS_FILTER = -2;

        public static final int POST_FILTER = -1;

        public static final int MANAGER_ACCESS_FILTER = 1;
    }

    class Key {

        public static final String IGNORE = FilterConstants.Key.class.getName() + ".IGNORE";

        public static final String FILTER = FilterConstants.Key.class.getName() + ".FILTER";

        public static final String TEXT_PLAIN_VALUE_UTF8 = "text/plain;charset=UTF-8";

        public static final String TEXT_PLAIN_VALUE = "text/plain";

        public static final String REQUEST_START_TIME = "REQUEST_START_TIME";

        public static final String REQ_ID = "reqId";
    }
}
