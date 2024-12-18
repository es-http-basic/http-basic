package com.cleafy.elasticsearch6.plugins.http.utils;

/**
 * format validation
 * This class encodes/decodes hexadecimal data
 */
public final class  HexBin {
    static private final int  BASELENGTH   = 255;
    static private final int  LOOKUPLENGTH = 16;
    static final private byte [] hexNumberTable    = new byte[BASELENGTH];
    static final private char [] lookUpHexAlphabet = new char[LOOKUPLENGTH];


    static {
        for (int i = 0; i<BASELENGTH; i++ ) {
            hexNumberTable[i] = -1;
        }
        for ( int i = '9'; i >= '0'; i--) {
            hexNumberTable[i] = (byte) (i-'0');
        }
        for ( int i = 'F'; i>= 'A'; i--) {
            hexNumberTable[i] = (byte) ( i-'A' + 10 );
        }
        for ( int i = 'f'; i>= 'a'; i--) {
           hexNumberTable[i] = (byte) ( i-'a' + 10 );
        }

        for(int i = 0; i<10; i++ )
            lookUpHexAlphabet[i] = (char)('0'+i);
        for(int i = 10; i<=15; i++ )
            lookUpHexAlphabet[i] = (char)('A'+i -10);
    }

    /**
     * Encode a byte array to hex string
     *
     * @param binaryData  array of byte to encode
     * @return return     encoded string
     */
    static public String encode(byte[] binaryData) {
        if (binaryData == null)
            return null;
        int lengthData   = binaryData.length;
        int lengthEncode = lengthData * 2;
        char[] encodedData = new char[lengthEncode];
        int temp;
        for (int i = 0; i < lengthData; i++) {
            temp = binaryData[i];
            if (temp < 0)
                temp += 256;
            encodedData[i*2] = lookUpHexAlphabet[temp >> 4];
            encodedData[i*2+1] = lookUpHexAlphabet[temp & 0xf];
        }
        return new String(encodedData);
    }

    /**
     * Decode hex string to a byte array
     *
     * @param encoded  encoded string
     * @return return     array of byte to encode
     */
    static public byte[] decode(String encoded) {
        if (encoded == null)
            return null;
        int lengthData = encoded.length();
        if (lengthData % 2 != 0)
            return null;

        char[] binaryData = encoded.toCharArray();
        int lengthDecode = lengthData / 2;
        byte[] decodedData = new byte[lengthDecode];
        byte temp1, temp2;
        for( int i = 0; i<lengthDecode; i++ ){
            temp1 = hexNumberTable[binaryData[i*2]];
            if (temp1 == -1)
                return null;
            temp2 = hexNumberTable[binaryData[i*2+1]];
            if (temp2 == -1)
                return null;
            decodedData[i] = (byte)((temp1 << 4) | temp2);
        }
        return decodedData;
    }

    /**
     * Encode a int to hex string
     * <p/>
     *
     * @param intData  int to encode
     * @return return     encoded string
     */
	public static String encode(int intData) {
		byte[] intb = new byte[4];
		intb[3] = (byte) (intData & 0xff);
		intb[2] = (byte) ((intData >> 8) & 0xff);
		intb[1] = (byte) ((intData >> 16) & 0xff);
		intb[0] = (byte) ((intData >> 24) & 0xff);
		return encode(intb);
	}


    /**
     * Decode hex string to a int
     * <p/>
     *
     * @param encoded  encoded string
     * @return return     int to encode
     * @throws Exception 
     */
	public static int decode2int(String encoded) throws Exception {
		byte[] intb = decode(encoded);
		if(intb==null){
			throw new Exception("encoded is null");
		}
		return (intb[3]& 0xff) + (intb[2] << 8 & 0xff00) + (intb[1] << 16 & 0xff0000) + (intb[0] << 24);
	}
	

	/**
	 * arrays.hashCode()替代方法
	 * @param a array
	 * @return hashcode
	 */
	public static int arraysHashCode(Object a[]) {
		if (a == null)
			return 0;
 
		int result = 1;
 
		for (int i = 0; i < a.length; i ++)
			result = 31 * result + (a[i] == null ? 0 : a[i].hashCode());
 
		return result;
	}

	/**
	 * mix
	 * <p/>
	 * 
	 * 简单混杂数据
	 * @param longData 输入
	 * @return 返回 
	 */
	public static byte[] mix(long longData) {
		byte[] longb = new byte[8];
		longb[7] = (byte) (longData & 0xff);
		longb[6] = (byte) ((longData >> 8) & 0xff);
		longb[5] = (byte) ((longData >> 16) & 0xff);
		longb[4] = (byte) ((longData >> 24) & 0xff);
		longb[3] = (byte) ((longData >> 32) & 0xff);
		longb[2] = (byte) ((longData >> 40) & 0xff);
		longb[1] = (byte) ((longData >> 48) & 0xff);
		longb[0] = (byte) ((longData >> 56) & 0xff);
		for (int i = 0; i < longb.length; i ++) {
			longb[i] ^= (i * 7);
		}
		for (int i = longb.length - 1; i >0; i --) {
			longb[i - 1] ^= longb[i];
		}
		return longb;
	}
}
