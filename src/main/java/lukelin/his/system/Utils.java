package lukelin.his.system;

import io.ebean.ExpressionList;
import lukelin.common.springboot.exception.ApiValidationException;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

public class Utils {
    public Utils() {

    }

    public static String buildDisplayCode(Integer number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10 - number.toString().length(); i++)
            sb.append("0");
        return sb.toString() + number.toString();
    }

    public static Integer findDaysBetween(Date startDate, Date endDate) {
        LocalDate localStartDate = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
        int daysBetween = (int) ((localEndDate.toEpochDay() - localStartDate.toEpochDay()));
        return daysBetween;
    }

    public static String Md5Encrypt(String stringToEncrypt) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(stringToEncrypt.getBytes(StandardCharsets.UTF_8));
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new ApiValidationException(e.getMessage());
        }
    }


    public static <T> ExpressionList<T> addSearchExpression(ExpressionList<T> el, String searchCode, String nameField, String searchCodeField) {
        el = el.where().or()
                .like(searchCodeField, "%" + searchCode.toUpperCase() + "%")
                .like(nameField, "%" + searchCode.toUpperCase() + "%")
                .endOr();
        return el;
    }

    public static <T> ExpressionList<T> addSearchExpression(ExpressionList<T> el, String searchCode) {
        return Utils.addSearchExpression(el, searchCode, "searchCode", "name");
    }

    public static int getAge(Date birthDay) {
        if (birthDay == null) {
            throw new IllegalArgumentException(
                    "The birthDay is null.It's unbelievable!");
        }
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static List<String> getColourList() {
        List<String> allColourList = new ArrayList<>();
        allColourList.add("red");
        allColourList.add("blue");
        allColourList.add("purple");
        allColourList.add("green");
        allColourList.add("orange");
        allColourList.add("yellow");
        allColourList.add("cyan");
        allColourList.add("magenta");
        allColourList.add("volcano");
        allColourList.add("gold");
        allColourList.add("lime");
        allColourList.add("geekblue");
        allColourList.add("pink");
        return allColourList;
    }

    //public static final String DEF_REGEX="\\{(.+?)\\}";

    //public static final String ATTRIBUTE_REGEX = "\\{(.+?)\\}";
    public static final String DEF_REGEX = "/.*?< class=\"replace\" type=\"text\" value=\\\"(.*?)\\\" \\/>.*?/";


    public static String templateVariableReplace(String template, Map<String, String> data) {
        return templateVariableReplace(template, data, DEF_REGEX);
    }

    public static String templateVariableReplace(String template, Map<String, String> data, String regex) {
        if (StringUtils.isBlank(template)) {
            return "";
        }
        if (StringUtils.isBlank(regex)) {
            return template;
        }
        if (data == null || data.size() == 0) {
            return template;
        }
        try {
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(template);
            //Pattern attributePatten = Pattern.compile(ATTRIBUTE_REGEX);
            //Matcher attributeMatcher;
            while (matcher.find()) {
                String inputHtml = matcher.group(1);// 获取INPUTHTML
                // attributeMatcher = attributePatten.matcher(inputHtml);//找到INPUT里的值
                String appendValue = "";
//                while (attributeMatcher.find()) {
//                    if(data.containsKey(attributeMatcher.group(1)))
//                        appendValue = data.get(attributeMatcher.group(1));// 键值
//                }
                matcher.appendReplacement(sb, appendValue);
            }
            matcher.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;

    }

    public static String crateQRCode(String content, int width, int height) throws IOException {

        String resultImage = "";
        if (!StringUtils.isEmpty(content)) {
            ServletOutputStream stream = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            @SuppressWarnings("rawtypes")
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 指定字符编码为“utf-8”
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 指定二维码的纠错等级为中级
            hints.put(EncodeHintType.MARGIN, 2); // 设置图片的边距

            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage, "png", os);
                /**
                 * 原生转码前面没有 data:image/png;base64 这些字段，返回给前端是无法被解析，可以让前端加，也可以在下面加上
                 */
                resultImage = new String("data:image/png;base64," + Base64.encodeBase64String(os.toByteArray()));

                return resultImage;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
        return null;
    }

    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim().toUpperCase();
    }

    public static String numberToChinese(String moneyString) {
        return Utils.numberToChinese(moneyString, true);
    }

    public static String numberToChinese(String moneyString, boolean isMoney) {
        String[] pattern = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] cPattern = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};
        String[] cfPattern = {"", "角", "分"};
        String ZEOR = "零";

        int dotPoint = moneyString.indexOf("."); //判断是否为小数
        String moneyStr;
        if (dotPoint != -1) {
            moneyStr = moneyString.substring(0, moneyString.indexOf("."));
        } else {
            moneyStr = moneyString;
        }
        StringBuffer fraction = null;   //小数部分的处理,以及最后的yuan.
        StringBuffer ms = new StringBuffer();
        for (int i = 0; i < moneyStr.length(); i++) {
            ms.append(pattern[moneyStr.charAt(i) - 48]); //按数组的编号加入对应大写汉字
        }

        int cpCursor = 1;
        for (int j = moneyStr.length() - 1; j > 0; j--) {
            ms.insert(j, cPattern[cpCursor]);   //在j之后加字符,不影响j对原字符串的相对位置
            //只是moneyStr.length()不断增加
            //insert(j,"string")就在j位置处插入,j=0时为第一位
            cpCursor = cpCursor == 8 ? 1 : cpCursor + 1;    //亿位之后重新循环
        }


        while (ms.indexOf("零拾") != -1) {  //当十位为零时用一个"零"代替"零拾"
            //replace的起始于终止位置
            ms.replace(ms.indexOf("零拾"), ms.indexOf("零拾") + 2, ZEOR);
        }
        while (ms.indexOf("零佰") != -1) {  //当百位为零时,同理
            ms.replace(ms.indexOf("零佰"), ms.indexOf("零佰") + 2, ZEOR);
        }
        while (ms.indexOf("零仟") != -1) {  //同理
            ms.replace(ms.indexOf("零仟"), ms.indexOf("零仟") + 2, ZEOR);
        }
        while (ms.indexOf("零万") != -1) {  //万需保留，中文习惯
            ms.replace(ms.indexOf("零万"), ms.indexOf("零万") + 2, "万");
        }
        while (ms.indexOf("零亿") != -1) {  //同上
            ms.replace(ms.indexOf("零亿"), ms.indexOf("零亿") + 2, "亿");
        }
        while (ms.indexOf("零零") != -1) {//有连续数位出现零，即有以下情况，此时根据习惯保留一个零即可
            ms.replace(ms.indexOf("零零"), ms.indexOf("零零") + 2, ZEOR);
        }
        while (ms.indexOf("亿万") != -1) {  //特殊情况，如:100000000,根据习惯保留高位
            ms.replace(ms.indexOf("亿万"), ms.indexOf("亿万") + 2, "亿");
        }
        while (ms.lastIndexOf("零") == ms.length() - 1) {  //当结尾为零j，不必显示,经过处理也只可能出现一个零
            if (ms.indexOf("零") == -1) {
                ms.delete(ms.lastIndexOf("零"), ms.lastIndexOf("零") + 1);
            } else {
                break;
            }
        }


        int end;
        if ((dotPoint = moneyString.indexOf(".")) != -1) { //是小数的进入
            String fs = moneyString.substring(dotPoint + 1, moneyString.length());
            if (fs.indexOf("00") == -1 || fs.indexOf("00") >= 2) {//若前两位小数全为零，则跳过操作
                end = fs.length() > 2 ? 2 : fs.length();  //仅保留两位小数
                fraction = new StringBuffer(fs.substring(0, end));
                for (int j = 0; j < fraction.length(); j++) {
                    fraction.replace(j, j + 1, pattern[fraction.charAt(j) - 48]); //替换大写汉字
                }
                for (int i = fraction.length(); i > 0; i--) {  //插入中文标识
                    fraction.insert(i, cfPattern[i]);
                }
                if (isMoney)
                    fraction.insert(0, "元");      //为整数部分添加标识
            } else {
                if (isMoney)
                    fraction = new StringBuffer("元整");
            }

        } else {
            if (isMoney)
                fraction = new StringBuffer("元整");
        }

        if (fraction != null)
            ms.append(fraction);         //加入小数部分
        return ms.toString();
    }

    public static String numberToChineseSimple(String moneyString, boolean isMoney) {
        String[] pattern = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] cPattern = {"", "十", "百", "千", "万", "十", "百", "千", "亿"};
        String[] cfPattern = {"", "角", "分"};
        String ZEOR = "零";

        int dotPoint = moneyString.indexOf("."); //判断是否为小数
        String moneyStr;
        if (dotPoint != -1) {
            moneyStr = moneyString.substring(0, moneyString.indexOf("."));
        } else {
            moneyStr = moneyString;
        }
        StringBuffer fraction = null;   //小数部分的处理,以及最后的yuan.
        StringBuffer ms = new StringBuffer();
        for (int i = 0; i < moneyStr.length(); i++) {
            ms.append(pattern[moneyStr.charAt(i) - 48]); //按数组的编号加入对应大写汉字
        }

        int cpCursor = 1;
        for (int j = moneyStr.length() - 1; j > 0; j--) {
            ms.insert(j, cPattern[cpCursor]);   //在j之后加字符,不影响j对原字符串的相对位置
            //只是moneyStr.length()不断增加
            //insert(j,"string")就在j位置处插入,j=0时为第一位
            cpCursor = cpCursor == 8 ? 1 : cpCursor + 1;    //亿位之后重新循环
        }


        while (ms.indexOf("零十") != -1) {  //当十位为零时用一个"零"代替"零拾"
            //replace的起始于终止位置
            ms.replace(ms.indexOf("零十"), ms.indexOf("零十") + 2, ZEOR);
        }
        while (ms.indexOf("零百") != -1) {  //当百位为零时,同理
            ms.replace(ms.indexOf("零百"), ms.indexOf("零百") + 2, ZEOR);
        }
        while (ms.indexOf("零千") != -1) {  //同理
            ms.replace(ms.indexOf("零千"), ms.indexOf("零千") + 2, ZEOR);
        }
        while (ms.indexOf("零万") != -1) {  //万需保留，中文习惯
            ms.replace(ms.indexOf("零万"), ms.indexOf("零万") + 2, "万");
        }
        while (ms.indexOf("零亿") != -1) {  //同上
            ms.replace(ms.indexOf("零亿"), ms.indexOf("零亿") + 2, "亿");
        }
        while (ms.indexOf("零零") != -1) {//有连续数位出现零，即有以下情况，此时根据习惯保留一个零即可
            ms.replace(ms.indexOf("零零"), ms.indexOf("零零") + 2, ZEOR);
        }
        while (ms.indexOf("亿万") != -1) {  //特殊情况，如:100000000,根据习惯保留高位
            ms.replace(ms.indexOf("亿万"), ms.indexOf("亿万") + 2, "亿");
        }
        while (ms.lastIndexOf("零") == ms.length() - 1) {  //当结尾为零j，不必显示,经过处理也只可能出现一个零
            if (ms.indexOf("零") == -1) {
                ms.delete(ms.lastIndexOf("零"), ms.lastIndexOf("零") + 1);
            } else {
                break;
            }
        }


        int end;
        if ((dotPoint = moneyString.indexOf(".")) != -1) { //是小数的进入
            String fs = moneyString.substring(dotPoint + 1, moneyString.length());
            if (fs.indexOf("00") == -1 || fs.indexOf("00") >= 2) {//若前两位小数全为零，则跳过操作
                end = fs.length() > 2 ? 2 : fs.length();  //仅保留两位小数
                fraction = new StringBuffer(fs.substring(0, end));
                for (int j = 0; j < fraction.length(); j++) {
                    fraction.replace(j, j + 1, pattern[fraction.charAt(j) - 48]); //替换大写汉字
                }
                for (int i = fraction.length(); i > 0; i--) {  //插入中文标识
                    fraction.insert(i, cfPattern[i]);
                }
                if (isMoney)
                    fraction.insert(0, "元");      //为整数部分添加标识
            } else {
                if (isMoney)
                    fraction = new StringBuffer("元整");
            }

        } else {
            if (isMoney)
                fraction = new StringBuffer("元整");
        }

        if (fraction != null)
            ms.append(fraction);         //加入小数部分
        return ms.toString();
    }


    public static String getChineseDate(String riqi) {
        String[] newsdate = new String[8];

        for (int i = 0; i < riqi.length(); i++) {
            int k = Integer.parseInt(Character.toString(riqi.charAt(i)));
            switch (k) {
                case 0:
                    newsdate[i] = "〇";
                    break;
                case 1:
                    newsdate[i] = "一";
                    break;
                case 2:
                    newsdate[i] = "二";
                    break;
                case 3:
                    newsdate[i] = "三";
                    break;
                case 4:
                    newsdate[i] = "四";
                    break;
                case 5:
                    newsdate[i] = "五";
                    break;
                case 6:
                    newsdate[i] = "六";
                    break;
                case 7:
                    newsdate[i] = "七";
                    break;
                case 8:
                    newsdate[i] = "八";
                    break;
                case 9:
                    newsdate[i] = "九";
                    break;
            }
        }

        // 加入年月日
        List<String> s1 = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
            if (i < 4) {
                s1.add(newsdate[i]);
            } else if (i == 4) {
                s1.add("年");
                s1.add(newsdate[i]);
            } else if (i == 5) {
                s1.add(newsdate[i]);
            } else if (i == 6) {
                s1.add("月");
                s1.add(newsdate[i]);
            } else if (i == 7) {
                s1.add(newsdate[i]);
                s1.add("日");
            }

        }

        String newstr = "";
        for (String s : s1) {
            newstr += s;
        }

        /*
         * 截取月份、日期
         */
        int i = newstr.indexOf("年");
        int j = newstr.indexOf("月");
        String month = newstr.substring(i + 1, j);
        String day = newstr.substring(j + 1, newstr.length() - 1);
        /*
         * 处理月份
         */
        String str1 = month.substring(0, 1);
        String str2 = month.substring(1);
        String newmonth = "";
        if ("〇".equals(str1)) {
            newmonth = str2;
        } else if ("一".equals(str1) && "〇".equals(str2)) {
            newmonth = "十";
        } else if ("一".equals(str1) && !"〇".equals(str2)) {
            newmonth = "十" + str2;
        }

        /*
         * 处理日期
         */
        String st1 = day.substring(0, 1);
        String st2 = day.substring(1);
        String newday = "";
        if ("〇".equals(st1)) {
            newday = st2;
        } else if ("一".equals(st1) && "〇".equals(st2)) {
            newday = "十";
        } else if ("一".equals(st1) && !"〇".equals(st2)) {
            newday = "十" + st2;
        } else if ("二".equals(st1) && "〇".equals(st2)) {
            newday = st1 + "十";
        } else if ("二".equals(st1) && !"〇".equals(st2)) {
            newday = st1 + "十" + st2;
        } else if ("三".equals(st1) && "〇".equals(st2)) {
            newday = st1 + "十";
        } else if ("三".equals(st1) && !"〇".equals(st2)) {
            newday = st1 + "十" + st2;
        }
        String newstring = newstr.substring(0, i) + "年" + newmonth + "月" + newday + "日";
        return newstring;

    }


}
