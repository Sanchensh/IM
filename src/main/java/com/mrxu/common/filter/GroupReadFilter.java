package com.mrxu.common.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * 过滤system,robot之类的账号
 *
 * @author feng.chuang
 * @date 2021-03-11 14:54
 */
public class GroupReadFilter {
    private static final Set<String> GROUP_READ_FILTER = new HashSet<>();

    static {
        GROUP_READ_FILTER.add("null");
        GROUP_READ_FILTER.add("robot");
        GROUP_READ_FILTER.add("ROBOT");
        GROUP_READ_FILTER.add("system");
        GROUP_READ_FILTER.add("SYSTEM");
    }

    public static Set<String> getGroupReadFilter() {
        return GROUP_READ_FILTER;
    }
}
