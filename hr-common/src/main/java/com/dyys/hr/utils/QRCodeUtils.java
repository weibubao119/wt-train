package com.dyys.hr.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * QR二维码
 *
 * @author 李巨川
 * @date 2019/1/23 9:08
 */
public class QRCodeUtils {

    /**
     * 生成二维码
     *
     * @throws WriterException
     * @throws IOException
     */
    public static void createEncode(HttpServletResponse response, String code) throws IOException {
        ServletOutputStream stream = null;

        if (StrUtil.isBlank(code)) {
            return;
        }

        code = new String(code.getBytes("UTF-8"), "ISO-8859-1");

        try {
            int width = 400;
            int height = 400;
            stream = response.getOutputStream();
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix m = writer.encode(code, BarcodeFormat.QR_CODE, height, width);
            MatrixToImageWriter.writeToStream(m, "jpg", stream);
        } catch (WriterException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 生成二維碼
     *
     * @param response
     * @param code
     * @param width
     * @param height
     * @throws IOException
     */
    public static void createEncode(HttpServletResponse response, String code, Integer width, Integer height) throws IOException {
        ServletOutputStream stream = null;

        if (StrUtil.isBlank(code)) {
            return;
        }

        try {
            stream = response.getOutputStream();
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix m = writer.encode(code, BarcodeFormat.QR_CODE, height, width);
            MatrixToImageWriter.writeToStream(m, "png", stream);
        } catch (WriterException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 解析二维码
     */
    public static String analyzeDecode(String qrcodeUrl) throws Exception {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(qrcodeUrl));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            // 对图像进行解码
            com.google.zxing.Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            return result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
