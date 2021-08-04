package sumo.util;

public final class Paths {

    public static final String SUPPORT_EQUIP = "support-equip";
    public static final String USER_EQUIP = "user-equip";
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String WFC = "wfc";
    public static final String BZEM_DEPARTMENTS = "departments";
    public static final String ACCEESS_DENIED = "access-denied";
    public static final String MAIL_TEST = "mail-test";

    public static String[] getAnonymousPaths() {
        return new String[]{WFC, BZEM_DEPARTMENTS};
    }

    public static String[] getAuthenticatedPaths() {
        return new String[]{SUPPORT_EQUIP, USER_EQUIP, USER};
    }

}
