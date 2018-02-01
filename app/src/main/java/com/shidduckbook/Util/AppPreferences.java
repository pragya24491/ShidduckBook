package com.shidduckbook.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {

    /*
    *  result":[{"first_name":"ashu","last_name":"kh","email_id":"ashu@gmail.com","gender":"male",
    *  "father_name":"Father","mother_name":"Mom","user_id":"1"}]
    * */

    public static final String MBPREFERENCES = "shidduck_book";

    public static final String USERID = "userid";
    public static final String VERIFIED_USERID = "verified_userid";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL_ID = "email_id";
    public static final String FATHER_NAME = "father_name";
    public static final String MOTHER_NAME = "mother_name";
    public static final String GENDER = "gender";
    public static final String USER_IMAGE = "user_image";
    public static final String PROFILE_PIC_PRIVACY = "profile_pic_privacy";
    public static final String IMAGE_EXPANSION = "image_expansion";

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DATE = "date";
    public static final String BAAL_TESHUVA = "baal_teshuva";
    public static final String YESHORIMA_TEST= "yeshorima_test";

    public static final String PHONE_NUMBER = "phone_number";
    public static final String COUNTRY = "country";
    public static final String STATE = "state";
    public static final String CITY = "city";
    public static final String DOB = "dob";
    public static final String RELIGION = "religion";
    public static final String ELEMENTARYSCHOOL = "elementary_school";
    public static final String HIGH_SCHOOL = "high_school";
    public static final String YESHIVA = "yeshiva";
    public static final String DESCRIPTION = "description";
    public static final String MOTHER_TONGUE = "mother_tongue";
    public static final String OTHER_LANGUAGE = "other_language";
    public static final String OTHER_LANGUAGE1 = "other_language1";
    public static final String OTHER_INFO_SELF = "other_info_self";
    public static final String OTHER_INFO_PARTNER = "other_info_partner";


    public static final String REFERENCE_NAME1 = "reference_name1";
    public static final String REFERENCE_NAME2 = "reference_name2";
    public static final String ADDITIONAL_REFERENCE_NAME1 = "additional_reference_name1";
    public static final String ADDITIONAL_REFERENCE_NAME2 = "additional_reference_name2";
    public static final String REFERENCE_FRIEND_NAME1 = "reference_friend_name1";
    public static final String REFERENCE_FRIEND_NAME2 = "reference_friend_name2";

    public static final String REFERENCE_PHONE1 = "reference_phone1";
    public static final String REFERENCE_PHONE2 = "reference_phone2";
    public static final String ADDITIONAL_REFERENCE_PHONE1 = "additional_reference_phone1";
    public static final String ADDITIONAL_REFERENCE_PHONE2 = "additional_reference_phone2";
    public static final String REFERENCE_FRIEND_PHONE1 = "reference_friend_phone1";
    public static final String REFERENCE_FRIEND_PHONE2 = "reference_friend_phone2";

    public static final String REFERENCE_EMAIL1 = "reference_email1";
    public static final String REFERENCE_EMAIL2 = "reference_email2";
    public static final String ADDITIONAL_REFERENCE_EMAIL1 = "additional_reference_email1";
    public static final String ADDITIONAL_REFERENCE_EMAIL2 = "additional_reference_email2";
    public static final String REFERENCE_FRIEND_EMAIL1 = "reference_friend_email1";
    public static final String REFERENCE_FRIEND_EMAIL2 = "reference_friend_email2";

    public static final String REFERENCE_RELATION1 = "reference_relation1";
    public static final String REFERENCE_RELATION2 = "reference_relation2";
    public static final String ADDITIONAL_REFERENCE_RELATION1 = "additional_reference_relation1";
    public static final String ADDITIONAL_REFERENCE_RELATION2 = "additional_reference_relation2";
    public static final String REFERENCE_FRIEND_RELATION1 = "reference_friend_relation1";
    public static final String REFERENCE_FRIEND_RELATION2 = "reference_friend_relation2";


    public static final String PARENT_MECHUTONIM = "parent_mechutonim";
    public static final String OTHER_REFERENCES = "other_references";
    public static final String ADDITIONAL_INFORMATION = "additional_information";


    public static final String PARTNER_PREFERENCE_STATUS = "partner_preference_status";
    public static final String PERSONAL_PREFERENCE_STATUS = "personal_preference_status";


    private final SharedPreferences sharedPreferences;
    private final Editor editor;

    public AppPreferences(Context context) {
        String prefsFile = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static String getUserId(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(USERID, "");
    }

    public static void setUserId(Context context, String id) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(USERID, id);
        editor.commit();
    }

    public static String getVerifiedUser(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(VERIFIED_USERID, "");
    }

    public static void setVerifiedUser(Context context, String id) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(VERIFIED_USERID, id);
        editor.commit();
    }

    public static String getFirstName(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(FIRST_NAME, "");
    }

    public static void setFirstName(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(FIRST_NAME, name);
        editor.commit();
    }

    public static String getLastName(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(LAST_NAME, "");
    }

    public static void setLastName(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(LAST_NAME, name);
        editor.commit();
    }

    public static String getEmailId(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(EMAIL_ID, "");
    }

    public static void setEmailId(Context context, String email) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(EMAIL_ID, email);
        editor.commit();
    }

    public static String getFatherName(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(FATHER_NAME, "");
    }

    public static void setFatherName(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(FATHER_NAME, name);
        editor.commit();
    }

    public static String getMotherName(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(MOTHER_NAME, "");
    }

    public static void setMotherName(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(MOTHER_NAME, name);
        editor.commit();
    }

    public static String getGender(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(GENDER, "");
    }

    public static void setGender(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(GENDER, name);
        editor.commit();
    }

    public static String getProfilePicturePrivacy(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(PROFILE_PIC_PRIVACY, "");
    }

    public static void setProfilePicturePrivacy(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(PROFILE_PIC_PRIVACY, name);
        editor.commit();
    }

    public static String getUserImage(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(USER_IMAGE, "");
    }

    public static void setUserImage(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(USER_IMAGE, name);
        editor.commit();
    }

    public static String getImageExpansionPrivacy(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(IMAGE_EXPANSION, "");
    }

    public static void setImageExpansionPrivacy(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(IMAGE_EXPANSION, name);
        editor.commit();
    }

    public static String getCity(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(CITY, "");
    }

    public static void setCity(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(CITY, name);
        editor.commit();
    }

    public static String getState(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(STATE, "");
    }

    public static void setState(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(STATE, name);
        editor.commit();
    }

    public static String getCountry(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(COUNTRY, "");
    }

    public static void setCountry(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(COUNTRY, name);
        editor.commit();
    }

    public static String getPhoneNumber(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(PHONE_NUMBER, "");
    }

    public static void setPhoneNumber(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(PHONE_NUMBER, name);
        editor.commit();
    }

    public static String getDob(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(DOB, "");
    }

    public static void setDob(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(DOB, name);
        editor.commit();
    }

    public static String getYear(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(YEAR, "");
    }

    public static void setYear(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(YEAR, name);
        editor.commit();
    }

    public static String getMonth(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(MONTH, "");
    }

    public static void setMonth(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(MONTH, name);
        editor.commit();
    }

    public static String getDate(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(DATE, "");
    }

    public static void setDate(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(DATE, name);
        editor.commit();
    }

    public static String getBaalTeshuva(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(BAAL_TESHUVA, "");
    }

    public static void setBaalTeshuva(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(BAAL_TESHUVA, name);
        editor.commit();
    }

    public static String getReligion(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(RELIGION, "");
    }

    public static void setReligion(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(RELIGION, name);
        editor.commit();
    }

    public static String getElementarySchool(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ELEMENTARYSCHOOL, "");
    }

    public static void setElementarySchool(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ELEMENTARYSCHOOL, name);
        editor.commit();
    }

    public static String getHighSchool(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(HIGH_SCHOOL, "");
    }

    public static void setHighSchool(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(HIGH_SCHOOL, name);
        editor.commit();
    }

    public static String getYeshiva(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(YESHIVA, "");
    }

    public static void setYeshiva(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(YESHIVA, name);
        editor.commit();
    }

    public static String getDescription(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(DESCRIPTION, "");
    }

    public static void setDescription(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(DESCRIPTION, name);
        editor.commit();
    }

    public static String getMotherTongue(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(MOTHER_TONGUE, "");
    }

    public static void setMotherTongue(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(MOTHER_TONGUE, name);
        editor.commit();
    }

    public static String getOtherLanguage(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(OTHER_LANGUAGE, "");
    }

    public static void setOtherLanguage(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(OTHER_LANGUAGE, name);
        editor.commit();
    }

    public static String getOtherLanguage1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(OTHER_LANGUAGE1, "");
    }

    public static void setOtherLanguage1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(OTHER_LANGUAGE1, name);
        editor.commit();
    }


    public static String getOtherInfoSelf(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(OTHER_INFO_SELF, "");
    }

    public static void setOtherInfoSelf(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(OTHER_INFO_SELF, name);
        editor.commit();
    }

    public static String getOtherInfoPartner(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(OTHER_INFO_PARTNER, "");
    }

    public static void setOtherInfoPartner(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(OTHER_INFO_PARTNER, name);
        editor.commit();
    }

    public static String getYeshorimaTest(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(YESHORIMA_TEST, "");
    }

    public static void setYeshorimaTest(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(YESHORIMA_TEST, name);
        editor.commit();
    }

    /*
    *
    *   REFERENCES TAB IN PROFILE SECTION
    *
    *
    * */

    public static String getReferenceName1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_NAME1, "");
    }

    public static void setReferenceName1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_NAME1, name);
        editor.commit();
    }

    public static String getReferenceName2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_NAME2, "");
    }

    public static void setReferenceName2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_NAME2, name);
        editor.commit();
    }

    public static String getAdditionalReferenceName1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_REFERENCE_NAME1, "");
    }

    public static void setAdditionalReferenceName1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_REFERENCE_NAME1, name);
        editor.commit();
    }

    public static String getAdditionalReferenceName2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_REFERENCE_NAME2, "");
    }

    public static void setAdditionalReferenceName2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_REFERENCE_NAME2, name);
        editor.commit();
    }

    public static String getReferenceFriendName1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_FRIEND_NAME1, "");
    }

    public static void setReferenceFriendName1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_FRIEND_NAME1, name);
        editor.commit();
    }

    public static String getReferenceFriendName2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_FRIEND_NAME2, "");
    }

    public static void setReferenceFriendName2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_FRIEND_NAME2, name);
        editor.commit();
    }


    public static String getParentMechutonim(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(PARENT_MECHUTONIM, "");
    }

    public static void setParentMechutonim(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(PARENT_MECHUTONIM, name);
        editor.commit();
    }

    public static String getOtherReferences(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(OTHER_REFERENCES, "");
    }

    public static void setOtherReferences(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(OTHER_REFERENCES, name);
        editor.commit();
    }


    public static String getAdditionalInformation(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_INFORMATION, "");
    }

    public static void setAdditionalInformation(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_INFORMATION, name);
        editor.commit();
    }

    public static String getReferencePhone1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_PHONE1, "");
    }

    public static void setReferencePhone1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_PHONE1, name);
        editor.commit();
    }

    public static String getReferencePhone2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_PHONE2, "");
    }

    public static void setReferencePhone2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_PHONE2, name);
        editor.commit();
    }

    public static String getAdditionalReferencePhone1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_REFERENCE_PHONE1, "");
    }

    public static void setAdditionalReferencePhone1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_REFERENCE_PHONE1, name);
        editor.commit();
    }

    public static String getAdditionalReferencePhone2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_REFERENCE_PHONE2, "");
    }

    public static void setAdditionalReferencePhone2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_REFERENCE_PHONE2, name);
        editor.commit();
    }

    public static String getReferenceFriendPhone1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_FRIEND_PHONE1, "");
    }

    public static void setReferenceFriendPhone1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_FRIEND_PHONE1, name);
        editor.commit();
    }

    public static String getReferenceFriendPhone2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_FRIEND_PHONE2, "");
    }

    public static void setReferenceFriendPhone2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_FRIEND_PHONE2, name);
        editor.commit();
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

    public static void setReferenceEmail1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_EMAIL1, name);
        editor.commit();
    }

    public static String getReferenceEmail1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_EMAIL1, "");
    }

    public static void setReferenceEmail2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_EMAIL2, name);
        editor.commit();
    }

    public static String getReferenceEmail2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_EMAIL2, "");
    }

    public static void setAdditionalReferenceEmail1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_REFERENCE_EMAIL1, name);
        editor.commit();
    }

    public static String getAdditionalReferenceEmail1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_REFERENCE_EMAIL1, "");
    }

    public static String getAdditionalReferenceEmail2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_REFERENCE_EMAIL2, "");
    }

    public static void setAdditionalReferenceEmail2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_REFERENCE_EMAIL2, name);
        editor.commit();
    }

    public static String getReferenceFriendEmail1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_FRIEND_EMAIL1, "");
    }

    public static void setReferenceFriendEmail1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_FRIEND_EMAIL1, name);
        editor.commit();
    }

    public static String getReferenceFriendEmail2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_FRIEND_EMAIL2, "");
    }

    public static void setReferenceFriendEmail2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_FRIEND_EMAIL2, name);
        editor.commit();
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

    public static void setReferenceRelation1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_RELATION1, name);
        editor.commit();
    }

    public static String getReferenceRelation1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_RELATION1, "");
    }

    public static void setReferenceRelation2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_RELATION2, name);
        editor.commit();
    }

    public static String getReferenceRelation2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_RELATION2, "");
    }

    public static void setAdditionalReferenceRelation1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_REFERENCE_RELATION1, name);
        editor.commit();
    }

    public static String getAdditionalReferenceRelation1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_REFERENCE_RELATION1, "");
    }

    public static String getAdditionalReferenceRelation2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(ADDITIONAL_REFERENCE_RELATION2, "");
    }

    public static void setAdditionalReferenceRelation2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(ADDITIONAL_REFERENCE_RELATION2, name);
        editor.commit();
    }

    public static String getReferenceFriendRelation1(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_FRIEND_RELATION1, "");
    }

    public static void setReferenceFriendRelation1(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_FRIEND_RELATION1, name);
        editor.commit();
    }

    public static String getReferenceFriendRelation2(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(REFERENCE_FRIEND_RELATION2, "");
    }

    public static void setReferenceFriendRelation2(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(REFERENCE_FRIEND_RELATION2, name);
        editor.commit();
    }


    /*
    *
    *   REFERENCES TAB IN PROFILE SECTION
    *
    *
    * */


    public static String getPartnerPreferenceStatus(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(PARTNER_PREFERENCE_STATUS, "");
    }

    public static void setPartnerPreferenceStatus(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(PARTNER_PREFERENCE_STATUS, name);
        editor.commit();
    }

    public static String getPersonalPreferenceStatus(Context context) {
        SharedPreferences pereference = context.getSharedPreferences(
                MBPREFERENCES, 0);
        return pereference.getString(PERSONAL_PREFERENCE_STATUS, "");
    }

    public static void setPersonalPreferenceStatus(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                MBPREFERENCES, 0);
        Editor editor = preferences.edit();
        editor.putString(PERSONAL_PREFERENCE_STATUS, name);
        editor.commit();
    }

}
