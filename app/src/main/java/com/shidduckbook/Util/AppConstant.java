package com.shidduckbook.Util;

/**
 * Created by Peter on 29-May-17.
 */

public class AppConstant {

    public static String KEY_USER_ID = "user_id";
    public static String KEY_METHOD = "method";
    public static String KEY_FNAME = "first_name";
    public static String KEY_LNAME = "last_name";
    public static String KEY_EMAILID = "email_id";
    public static String KEY_FATHERNAME = "father_name";
    public static String KEY_GENDER = "gender";
    public static String KEY_MOTHERSNAME = "mother_name";
    public static String KEY_PASSWORD = "password";
    public static String KEY_PHONE_NUMBER = "phone_number";
    public static String KEY_COUNTRY = "country";
    public static String KEY_STATE = "state";
    public static String KEY_CITY = "city";
    public static String KEY_DOB = "dob";
    public static String KEY_BAAL_TESHUVA = "baal_teshuva";
    public static String KEY_RELIGION = "religion";
    public static String KEY_USER_IMAGE = "user_image";

    public static String KEY_ELEMENTARY_SCHOOL = "elementary_school";
    public static String KEY_HIGH_SCHOOL = "high_school";
    public static String KEY_YESHIVA = "yeshiva";
    public static String KEY_DESCRIPTION = "description";
    public static String KEY_MOTHER_TONGUE = "mother_tongue";
    public static String KEY_OTHER_LANGUAGE = "other_language";
    public static String KEY_OTHER_LANGUAGE1 = "other_language1";


    public static String KEY_PERSONAL_TRAITS = "personal_traits";
    public static String KEY_PARTNER_TRAITS = "partner_traits";
    public static String KEY_HOBBIES = "hobbies";
    public static String KEY_YESHORIM_TEST = "yeshorim_test";
    public static String KEY_DESCRIBE_YOUR_SELF = "describe_your_Self";
    public static String KEY_IN_A_PARTNER = "in_a_partner";

    public static String KEY_EMAIL = "email";
    public static String KEY_AGE = "age";

    public static String KEY_MATCH_USER_ID = "match_user_id";
    public static String KEY_FAVOURITE_USER_ID = "favourite_user_id";
    public static String KEY_ARCHIVE_USER_ID = "archive_user_id";

    public static String KEY_NOTES_ID = "notes_id";


    public interface BASEURL {
//        String URL = "http://truckslogistics.com/Projects-Work/shidduch_book/USERAPI/";
        String URL = "http://www.shidduchbook.com/USERAPI/";
    }

    public interface ENDPOINT {

        String REGISTRATION = "register_log_API.php";
        String GENERALAPI = "general_API.php";
        String MY_MATCHED_API = "my_matched_API.php";
        String USER_NOTES_API = "user_notes_Api.php";
    }

    public interface METHOD {

        String M_HOMEPAGE = "homePage";
        String M_REGISTRATION = "user_signup";
        String M_LOGIN = "user_login";
        String M_LOGINHOMEPAGE = "loginhomePage";

        String M_PERSONAL_INFO = "personal_info";
        String M_PERSONAL_TRAITS = "personal_traits";
        String M_PARTNER_TRAITS = "partner_traits";
        String M_PEROSNAL_EDUCATION = "perosnal_education";
        String M_OTHER_INFO = "other_info";

        String M_YOUR_REFERENCE = "your_reference";
        String M_ADD_OTHERUSER = "addOtherUser";
        String M_EDIT_PROFILE = "edit_profile";

        String M_ADD_FAVOURITE = "add_favourite";
        String M_MY_FAVORITE = "my_favourite";

        String M_ADD_INTEREST = "send_interested";
        String M_MY_INTEREST = "my_interest";
        String REMOVE_INTERESTED = "remove_interested";

        String ADD_ARCHIVE = "add_archive";
        String M_ARCHIVE = "my_archive";
        String REMOVE_ARCHIVE = "remove_archive";

        String M_ADD_NOTES = "add_notes";
        String M_REMOVE_NOTES = "removeNotes";
        String M_MY_NOTES = "my_notes";

        String M_REQUEST_PROFILE = "request_profile";

        String M_MY_MATCHES = "my_matches";

        String ADD_PERSONAL_PREFERENCE_TRAITS = "add_personal_preference_traits";
        String ADD_PARTNER_PREFERENCE_TRAITS = "add_partner_preference_traits";

        String ALL_PREFERENCE_TRAITS = "preference_traits";
        String MY_PERSONAL_TRAITS = "my_personal_traits";
        String MY_PARTNER_TRAITS = "my_partner_traits";

    }
}
