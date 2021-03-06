package com.vincent.framework.utils;

import android.util.Base64;
import android.widget.EditText;

import com.vincent.framework.LibApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The util for string
 * Created by liunan on 1/16/15.
 */
public class StringUtil {
    public static final int MAX_HOME_CHAR_EDITTEXT = 14;
    private static final String COLON = ":";
    private static final int STR_LENGTH = 8;

    public static final String NEW_PWD_RULE_MATCHER = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{8,30}";
    /**
     * judge if a string is null, empty, or only space
     *
     * @param str the specified string
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String notNullString(String str) {
        return isEmpty(str) ? "" : str;
    }

    /**
     * Trim the referred string on the left of source string.
     *
     * @param src
     * @param trimString
     * @return
     */
    public static String trimLeft(String src, String trimString) {
        if (isEmpty(src) || isEmpty(trimString)) {
            return src;
        }
        String result = src;
        while (result.startsWith(trimString)) {
            result = result.substring(trimString.length());
        }
        return result;
    }

    /**
     * Trim the referred string on the right of source string.
     *
     * @param src
     * @param trimString
     * @return
     */
    public static String trimRight(String src, String trimString) {
        if (isEmpty(src) || isEmpty(trimString)) {
            return src;
        }
        String result = src;
        while (result.endsWith(trimString)) {
            result = result.substring(0, result.length() - trimString.length());
        }
        return result;
    }

    /**
     * Replaces "searchFor" with "replaceWith" in "sourceStr".
     *
     * @param sourceStr   The source string
     * @param searchFor   Pattern to be replaced
     * @param replaceWith Pattern to replace with
     * @return resulting string.
     */
    public static String replace(String sourceStr, String searchFor, String replaceWith) {
        if (sourceStr == null || searchFor == null || searchFor.length() == 0 || replaceWith == null)
            return sourceStr;

        // Search for searchStr
        int pos = sourceStr.indexOf(searchFor);
        if (pos < 0) {
            return sourceStr;
        }

        StringBuilder sb = new StringBuilder();

        String result = sourceStr;
        while (pos >= 0) {
            sb.append(result.substring(0, pos)).append(replaceWith);

            result = result.substring(pos + searchFor.length());
            pos = result.indexOf(searchFor);
        }

        sb.append(result);

        return sb.toString();
    }

    /**
     * Splits the given string into an array of substrings. Examples:
     * split("a;b;c;d;e", ';') ->"a", "b", "c", "d", "e" split("a;;c;d;e", ';')
     * -> "a", "", "c", "d", "e" split("a;b;c;d;e",'=') -> "a;b;c;d;e"
     * split(";b;c;d;e", ';') -> "", "b", "c", "d", "e" split(";", ';') -> "",""
     * split("", ';') -> ""
     *
     * @param source String to split.
     * @param sep    Separator character.
     * @return Array of string tokens. In case of no tokens an empty array is
     * returned (never null.)
     */
    public static String[] split(String source, char sep) {
        if (isEmpty(source)) {
            return new String[]{""};
        }

        int len = source.length();
        ArrayList<String> list = new ArrayList<String>();
        int i = 0;
        int start = 0;

        while (i < len) {
            if (source.charAt(i) == sep) {
                list.add(source.substring(start, i));
                start = i + 1;
            }
            i++;
        }
        if (i > start) // some trailing text found, append it
        {
            list.add(source.substring(start));
        } else if (source.charAt(len - 1) == sep) // source ends with a
        // separator, add the final
        // empty token
        {
            list.add("");
        }

//        String[] arr = new String[list.size()];
//        list.copyInto(arr);
//

        return list.toArray(new String[list.size()]);
    }

    /**
     * Extracts one parameter from a string and converts to a long. Example:
     * getParseInt("name=Jazz;age=17;active=1", "age", 7) => 17
     *
     * @param line         String containing any number of name=value pairs.
     * @param keyword      Keyword marking the value to extract.
     * @param defaultValue Value used in case the parameter is wrong/not found.
     * @return Extracted value.
     */
    public static long getParseValue(String line, String keyword, long defaultValue) {
        long ret = defaultValue;
        String tempKeyword = keyword;
        if (!tempKeyword.endsWith("=")) {
            tempKeyword += "=";
        }
        String val = getValueStartingWith(line, tempKeyword, ";", "");
        if (!isEmpty(val)) {
            ret = toLong(val, defaultValue);
        }
        return ret;
    }

    /**
     * Extracts one parameter from a string. Example: getParseInt("name=Jazz;age=17;active=1",
     * "name", "Frank Bith") => "Jazz"
     *
     * @param line         String containing any number of name=value pairs.
     * @param keyword      Keyword marking the value to extract.
     * @param defaultValue Value used in case the parameter is wrong/not found.
     * @return Extracted value.
     */
    public static String getParseValue(String line, String keyword, String defaultValue) {
        String ret = defaultValue;
        String tempKeyword = keyword;
        if (!tempKeyword.endsWith("=")) {
            tempKeyword += "=";
        }
        String val = getValueStartingWith(line, tempKeyword, ";", "");
        if (!isEmpty(val)) {
            ret = val;
        }
        return ret;
    }

    /**
     * Extracts one parameter from a string and converts to an int. Example:
     * getParseInt("name=Jazz;age=17;active=1", "age", 7) => 17
     *
     * @param line         String containing any number of name=value pairs.
     * @param keyword      Keyword marking the value to extract.
     * @param defaultValue Value used in case the parameter is wrong/not found.
     * @return Extracted value.
     */
    public static int getParseValue(String line, String keyword, int defaultValue) {
        int ret = defaultValue;
        String tempKeyword = keyword;
        if (!tempKeyword.endsWith("=")) {
            tempKeyword += "=";
        }
        // Add ";" to fix getValueStartingWith() bug
        String val = getValueStartingWith(";" + line, ";" + tempKeyword, ";", "");
        if (!isEmpty(val)) {
            ret = toInt(val, defaultValue);
        }
        return ret;
    }


    public static ArrayList<Integer> getParseValue(String line, String keyword) {

        ArrayList<Integer> ret = new ArrayList<>();
        String tempKeyword = keyword;
        if (!tempKeyword.endsWith("=")) {
            tempKeyword += "=";
        }
        // Add ";" to fix getValueStartingWith() bug
        String val = getValueStartingWith(";" + line, ";" + tempKeyword, ";", "");
        String[] strS = val.split(",");
        for (String str : strS) {
            if (!isEmpty(str)) {
                ret.add(toInt(val, 0));
            }
        }
        return ret;
    }

    /**
     * Converts given string to an integer value in a safe way.
     *
     * @param str string to convert
     * @param dft default value in case the string cannot be converted
     * @return integer value represented in the string, or the default value
     */
    public static int toInt(String str, int dft) {
        if (isEmpty(str)) {
            return dft;
        }

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
        }

        return dft;
    }

    /**
     * Extracts the value part of the token starting with the specified pattern. Tokens are
     * delimited with sSep. Case is ignored.
     * <p/>
     * Example: GetValueStartingWith("a=15;b=-23;c=3", "b=", ";", "0") ==> "-23".
     * <p/>
     * Warning! GetValueStartingWith("stt=5;t=abc", "t=", ";", "0") ==> "5" !!!
     *
     * @param sStr string String to search in.
     * @param sPtt string Pattern the value starts with.
     * @param sSep string Tokens separator.
     * @param sDft string Default value in case pattern is not found.
     * @return Value part of the token.
     */
    public static String getValueStartingWith(final String sStr, final String sPtt, final String sSep, final String sDft) {
        if (isEmpty(sStr)) {
            return sDft;
        }

        String ret = sDft;
        try {
            int pos = sStr.indexOf(sPtt);
            if (pos < 0) {
                pos = sStr.toUpperCase(Locale.getDefault()).indexOf(sPtt.toUpperCase(Locale.getDefault()));
            }
            if (pos >= 0) {
                pos += sPtt.length();

                int end = sStr.indexOf(sSep, pos);
                if (end >= pos) {
                    ret = sStr.substring(pos, end);
                } else {
                    ret = sStr.substring(pos);
                }
            }
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, "StringUtil", e.toString());
        }

        return ret;
    }

    /**
     * Converts given string to a long value in a safe way.
     *
     * @param str string to convert
     * @param dft default value in case the string cannot be converted
     * @return long value represented in the string, or the default value
     */
    public static long toLong(String str, long dft) {
        if (isEmpty(str)) {
            return dft;
        }

        long ret = dft;
        try {
            ret = Long.parseLong(str);
        } catch (NumberFormatException ex) {
            ret = dft;
        }

        return ret;
    }


    public static void specialCharacterAndChineseFilter(EditText et) {
        String editable = et.getText().toString();
        String str = stringFilterChinese(editable);
        if (!editable.equals(str)) {
            et.setText(str);
            et.setSelection(str.length());
        }
    }


    public static void specialCharactFilter(EditText et) {
        String editable = et.getText().toString();
        String str = stringFilter(editable);
        if (!editable.equals(str)) {
            et.setText(str);
            et.setSelection(str.length());
        }
    }


    public static void stringOnlyAcsiiFilter(EditText et) {
        String editable = et.getText().toString();
        String str = stringNoFilter(editable);
        if (!editable.equals(str)) {
            et.setText(str);
            et.setSelection(str.length());
        }
    }


    public static String stringFilterAddHome(EditText et) throws PatternSyntaxException {
        String regEx = "[<=>%#^&|\\\\/]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(et.getText().toString());
        return m.replaceAll("");
    }

    public static void isAlaboNumeric(EditText et) {
        String editable = et.getText().toString();
        String str = alaboFilter(et);
        if (!editable.equals(str)) {
            et.setText(str);
        }
        et.setSelection(et.length());
    }


    public static void filterCharacterEmpty(EditText et) {
        String editable = et.getText().toString();
        String str = "";
        if (!editable.equals(str)) {
            et.setText(str);
        }
        et.setSelection(et.getText().length());
    }

    public static String alaboFilter(EditText et) {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(et.getText().toString());
        return m.replaceAll("");
    }


    public static String stringFilterAddHome(String str) throws PatternSyntaxException {
        String regEx = "[<=>%#^&|\\\\/]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        // Only digits and characters allowed
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 所有的字符，除了中文
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringNoFilter(String str) throws PatternSyntaxException {
        // Only digits and characters allowed
        String regEx = "[\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    public static String stringFilterChinese(String str) throws PatternSyntaxException {
        // Only digits and characters allowed
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    public static void addOrEditHomeFilter(EditText et) {
        String editable = et.getText().toString();
        String str = stringFilterAddHome(et); //filter special character

        if (!editable.equals(str)) {
            et.setText(str);
        }
        et.setSelection(et.length());
    }


    public static void maxCharacterFilter(EditText et) {
        try {
            String strTransfer = new String(et.getText().toString().getBytes("GBK"), "ISO8859_1");
            if (strTransfer.length() > MAX_HOME_CHAR_EDITTEXT) {

                for (int i = 0; i <= et.getText().toString().length(); i++) {
                    String s = et.getText().toString().substring(0, i);
                    String st = new String(s.getBytes("GBK"), "ISO8859_1");
                    if (st.length() == MAX_HOME_CHAR_EDITTEXT) {
                        String str = et.getText().toString().substring(0, i);
                        et.setText(str);
                        et.setSelection(str.length());
                        return;
                    }
                    if (st.length() > MAX_HOME_CHAR_EDITTEXT) {
                        String str = et.getText().toString().substring(0, i - 1);
                        et.setText(str);
                        et.setSelection(str.length());
                        return;
                    }
                }

            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, "StringUtil", e.toString());
        }
    }


    /**
     * every number split with colon
     *
     * @param splitedStr
     * @param number
     * @return
     */
    public static String getStringWithColon(String splitedStr, int number) {
        if (isEmpty(splitedStr)) {
            return "";
        }
        if (splitedStr.length() <= number) {
            return splitedStr;
        }
        StringBuilder builder = new StringBuilder();
        String temp = splitedStr;

        while (temp.length() > number) {
            builder.append(temp.substring(0, number));
            temp = temp.substring(number);
            builder.append(COLON);
        }
        builder.append(temp);
        return builder.toString();
    }


    public static String decodeURL(String base64url)
            throws UnsupportedEncodingException {
        String decodeURLresult = java.net.URLDecoder.decode(base64url, "utf-8");
        String unBase64Result = base64decode(decodeURLresult);
        return unBase64Result;
    }

    /**
     * @param base64URL
     * @return
     */
    public static String base64decode(String base64URL) {
        return new String(Base64.decode(base64URL, Base64.DEFAULT));
    }

    public static String parseJDURL(String url, int startIndex, int endIndex) {
        try {
//            String decodeURL = decodeURL(url);
            String macAddress = url.substring(startIndex, endIndex);
            return macAddress;
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, "StringUtil", e.toString());
            return "";

        }
    }


    public static int[] StringtoInt(String str) {

        String strs[] = str.split(",");
        int ret[] = new int[strs.length];

        for (int i = 0; i < strs.length; i++) {
            if (!isEmpty(strs[i])) {
                ret[i] = Integer.valueOf(strs[i]);
            }
        }
        return ret;

    }


    public static String encodeStringWithTripleDES(String str) {
        try {
            TripleDES tripleDES = new TripleDES("ECB");
            return Base64.encodeToString(tripleDES.encrypt(str), Base64.DEFAULT);

        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, "StringUtil", e.toString());
        }
        return "";
    }


    public static String decryptStringWithTripleDES(String str) {
        try {
            TripleDES tripleDES = new TripleDES("ECB");
            return new String(tripleDES.decrypt(Base64.decode(str, Base64.DEFAULT)));

        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, "StringUtil", e.toString());
        }
        return "";
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isInvalidQrCode(String str) {
        String regEx = "^[A-Za-z0-9_]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static boolean isNeedChangeHintColor(String str) {
        Matcher passwordMatcher = Pattern.compile(NEW_PWD_RULE_MATCHER).matcher(str);
        if (str.length() >= STR_LENGTH && passwordMatcher.matches() == false) {
            return true;
        }
        return false;
    }

    /**
     *  利用java原生的摘要实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, "StringUtil", e.toString());
        } catch (UnsupportedEncodingException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, "StringUtil", e.toString());
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuilder stringBuffer = new StringBuilder();
        for (int i=0;i<bytes.length;i++){
          String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
