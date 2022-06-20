package com.zsj.util;

import com.zsj.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static AccountProfile getProfile() {

        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
