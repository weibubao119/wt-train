package com.dyys.hr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author wushicai@chinasie.com
 * @since 2022/10/21
 */
@Component
public class SsoAesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoAesUtil.class);
    /**
     * 私钥
     */
    private static String security;
    /**
     * 初始化向量,必须 16 位
     */
    private static String ivParameter;

    @Value("${aes.security}")
    @SuppressWarnings("squid:S2696")
    public void setSecurity(String security) {
        SsoAesUtil.security = security;
    }

    @Value("${aes.iv}")
    @SuppressWarnings("squid:S2696")
    public void setIvParameter(String ivParameter) {
        SsoAesUtil.ivParameter = ivParameter;
    }

    /**
     * 算法 AES/CBC/PKCS5Padding
     */
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * 指定为 AES 算法
     */
    private static final String AES = "AES";

    /**
     * 加密算法
     *
     * @param context 明文
     * @return 密文
     */
    @SuppressWarnings("squid:S5542")
    public static String encryptAes256(String context) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] raw = security.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(context.getBytes(StandardCharsets.UTF_8));
            // 此处使用BASE64做转码。
            return parseByte2HexStr(encrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param context 密文
     * @return 明文
     */
    @SuppressWarnings("squid:S5542")
    public static String decryptAes256(String context) {
        try {
            byte[] raw = security.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            // 先用base64解密
            byte[] encrypted = parseHexStr2Byte(context);
            if (encrypted.length == 0) {
                return null;
            }
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将二进制转换成十六进制
     *
     * @param buf 二进制
     * @return 16进制
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将十六进制转换为二进制
     *
     * @param hexStr 16进制
     * @return 二进制
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return new byte[0];
        } else {
            byte[] result = new byte[hexStr.length() / 2];
            for (int i = 0; i < hexStr.length() / 2; i++) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
            return result;
        }
    }
 
}
