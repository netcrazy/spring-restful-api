package kr.pe.nonstop.libs.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 문자열 관련 유틸
 */
public class StringUtils {
	
	public static final String EMPTY = "";
	public static final char FORMAT = "#".charAt(0);
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	

	/**
	 * 문자열을 해당 포멧형태로 리턴
	 * 
	 * <pre>
	 * 	<b>사용예</b>
	 * 		String s = StringUtils.format("2011080401", "####-##-##");	
	 * </pre>
	 * @param value	문자열
	 * @param format	포멧 - 대체 문자열은 '#' 이다
	 * @since	1.0.0
	 */
	public static String format(String value, String format) {
		
		StringBuffer sb = new StringBuffer();
		
		char[] f = nvl(format).toCharArray();
		char[] c = nvl(value).toCharArray();
		int offset = 0;
		
		for(int i=0; i < f.length; i++) {
			
			if ( offset < c.length && f[i] == StringUtils.FORMAT ) {
				
				sb.append( c[offset] );
				offset++;
				
			} else {
				
				sb.append( f[i] );
				
			}
			
		}
		
		
		return sb.toString();
	}
	
	public static String formatCurrency(String value) {
	
		if ( isNumeric(value) ) {
			return String.format("%,d", Long.parseLong(value));
		} else {
			return "0";
		}
	}
	
	
	/**
	 * 문자열이 null 인 경우 해당 문자열로 대체
	 * 
	 * @since	1.0.0
	 */
	public static String nvl(String value, String defaultString) {
		
		return  isEmpty(value) ? defaultString: value;
		
		
	}
	
	public static String nvl(CharSequence value, String defaultString) {
		
		return  isEmpty(value) ? defaultString: value.toString();
	}
	
	/**
	 * 문자열이 null 인 경우 null string - '' -  으로 대체
	 * 
	 * @since	1.0.0
	 */
	public static String nvl(String value) {
		return StringUtils.nvl(value, StringUtils.EMPTY);
	}
	
	public static String nvl(CharSequence value) {
		
		return StringUtils.nvl(value, StringUtils.EMPTY);
	}

	/**
	 * null 이거나 null String 인 경우 true 리턴
	 * 
	 * @since	1.0.0
	 */
	public static boolean isEmpty(String value) {
		
		if ( value == null)
			return true;
		else
			return  EMPTY.equals(value.trim());
	}
	public static boolean isEmpty(CharSequence value) {
	
		if ( value == null ) return true;
		return isEmpty(value.toString());
	}
	
	
	/**
	 * 파라메터들이 모두 빈값이면 true, 하나라도 값 있다면 false
	 * 
	 * @since	1.0.3
	 */
	public static boolean isEmptyAll(String... values) {
		
		for(int i=0; i < values.length; i++) {
			if ( ! isEmpty(values[i]) ) return false;
		}
		
		return true;
	}
	
	/**
	 * 파라메터들이 모두 값이 있다면 true, 하나라도 빈 값이 있다면 false
	 * 
	 * @since	1.0.3
	 */
	public static boolean isNotEmptyAll(String... values) {
		
		for(int i=0; i < values.length; i++) {
			if ( isEmpty(values[i]) ) return false;
		}
		
		return true;
	}

	
	/**
	 * 숫자 문자열인지 확인
	 * 
	 * @since	1.0.0
	 */
	public static boolean isNumeric(String s) {
		
	    Pattern pattern = Pattern.compile("[+-]?\\d+");
	    return pattern.matcher(s).matches(); 
	}  
	
	
	/**
	 * substring
	 * 
	 * @param value	대상 문자열
	 * @param fr	시작점
	 * @param length	substring 길이
	 * @since	1.0.0
	 */
	public static String substring(String value, int fr, int length) { 
		
		if ( isEmpty(value) )
			return EMPTY;
		else {
			
			int end = value.length();
			if ( fr > end )
				return EMPTY;
			else if ( (fr + length) < end )
				end = fr + length;
			
			return value.substring(fr, end);
			
		}
			
		
	}
	
	public static final char SPLITE_SEPARATOR = 0x18;
	
	/**
	 * split - separator 는 SPLITE_SEPARATOR 사용
	 * 
	 * @param str	대상문자열
	 * @since	1.0.0
	 */
	public static List<String> split(String str) {
		return split(str, SPLITE_SEPARATOR);
	}
	
	/**
	 * split
	 * 
	 * @param str	대상문자열
	 * @param separatorChar	separator
	 * @since	1.0.0
	 */
    public static List<String> split(String str, char separatorChar) {

        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) new ArrayList<String>();

        List<String> list = new ArrayList<String>();
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match ) {
                    list.add(str.substring(start, i));
                    match = false;
                    lastMatch = true;
                }
                start = ++i;
                continue;
            }
            lastMatch = false;
            match = true;
            i++;
        }
        if (match || lastMatch) {
            list.add(str.substring(start, i));
        }
        return list;
    }

    /**
     * join (리스트를 하나의 문자열로 만든다)
     * 		- split 의 반대
     * 		- separator 는 SPLITE_SEPARATOR 사용
     * 
     * @since	1.0.0
     */
	public static String join(List<String> arr) {
		return join(arr, SPLITE_SEPARATOR);
	}

	/**
     * join (리스트를 하나의 문자열로 만든다)
     * 		- split 의 반대
	 * 
     * @since	1.0.0
	 */
	public static String join(List<String> arr, char separatorChar) {
		
		StringBuffer sbString = new StringBuffer();
		Iterator<String> it = arr.iterator();
		
		int offset = 0;
		while(it.hasNext()) {
			
			if ( offset > 0) sbString.append(separatorChar);
			sbString.append(it.next());
			offset++;
		}
		return sbString.toString();
	}
	
	/**
	 * 대상 문자열에 파라메터 문자열들이 포함되는지 여부
	 * 
	 * @param source	대상문자열
	 * @param args		파라메터 문자열들
	 * @since	1.0.0
	 */
	public static boolean contains(String source, String... args) {
		
		source = nvl(source);
		for(int i=0; i < args.length; i++) {
			if ( source.indexOf(args[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * URLEncoding 한다 - 공백일 경우 공백리턴 
	 * 		: 공백일 경우 URLENCODER.ENCODE 함수 사용시 에러 발생하는 것 회피
	 * 
	 * @param s	대상 문자열
	 * @param encoding	인코딩(예:utf-8 등)
	 * @return
	 */
	public static String encodeURL(String s, String encoding) throws UnsupportedEncodingException {
		return isEmpty(s) ? StringUtils.EMPTY: URLEncoder.encode(s, encoding);
	}
	
	
	// ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
	// ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ
	// ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ
	final static char[] ChoSung	= { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
	final static char[] JwungSung	= { 0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163 };
	final static char[] JongSung	= { 0, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
	
	/**
	 * 문자열서 초성 분리
	 * 
	 * @param s
	 * @return
	 */
	public static String hangulToJaso(String s) {
		int a, c; // 자소 버퍼: 초성/중성/종성 순
		//int b;

		String result = "";
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch >= 0xAC00 && ch <= 0xD7A3) { // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
				c = ch - 0xAC00;
				a = c / (21 * 28);
				c = c % (21 * 28);
				//b = c / 28;
				c = c % 28;
				result = result + ChoSung[a];
			} else {
				result = result + ch;
			}
		}
		return result;
	}
	
}
