package com.wooshop.security.enums;

/**
 * token角色类型
 *
 * @author woo
 * @version v1.0
 * @since 2022/05/18 15:23
 */
public enum UserEnums {
    /**
     * 角色
     */
    MEMBER("会员"),
    STORE("商家"),
    MANAGER("管理员"),
    SYSTEM("系统");
    private final String role;

    UserEnums(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
