package com.workflowManager.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // 비밀번호 해싱
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // 비밀번호 검증
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
