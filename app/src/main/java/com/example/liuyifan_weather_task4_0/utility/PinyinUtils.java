package com.example.liuyifan_weather_task4_0.utility;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {
    public static String toPinyin(String chinese){
        if(!TextUtils.isEmpty(chinese)) {
            StringBuilder sb = new StringBuilder();
            char[] chars = chinese.toCharArray();
            HanyuPinyinOutputFormat format = new
                    HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            format.setVCharType(HanyuPinyinVCharType.WITH_V);
            for (int i = 0; i < chars.length; i++) {
                char word = chars[i];
                if (word > 128) {
                    try {
                        sb.append(PinyinHelper.toHanyuPinyinStringArray(word, format)[0]
                                + " ");//多音字取第一个
                    } catch (BadHanyuPinyinOutputFormatCombination
                            badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                } else {
                    sb.append(word);// some bug here
                } }
            return sb.toString();
        }else{
            return ""; } }
    public static String toPinyinFirstLetter(String chinese){
        if(!TextUtils.isEmpty(chinese)) {
            StringBuilder sb = new StringBuilder();
            String s = toPinyin(chinese);
            String[] split = s.split("\\s");
            for (int i = 0; i < split.length; i++) {
                sb.append(split[i].charAt(0));
            }
            return sb.toString();
        }else {
            return ""; } } }