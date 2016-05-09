package com.wenjie.blog.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommonUtil {

    public static void assertContainsIngoreCase(String set, String subset) {
        assertThat(set.toLowerCase(), containsString(subset.toLowerCase()));
    }
}
