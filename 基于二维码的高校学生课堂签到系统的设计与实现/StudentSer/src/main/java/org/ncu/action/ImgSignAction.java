package org.ncu.action;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.ncu.service.ImgSignImService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Controller
public class ImgSignAction {
    @Autowired
    ImgSignImService imgSignImService;

    @RequestMapping(value = "imgSign.json")
    public void testDecode(HttpServletRequest request, HttpServletResponse response) {

        String studentSignAccount = request.getParameter("studentAccount");

        String filePath = "D://zxing.png";
        boolean yy = false;

        String key1 = "软件工程141计算机组成原理999911";

        try (ServletInputStream sis = request.getInputStream()) {
            OutputStream os = new FileOutputStream("D://zxing.png");
            BufferedOutputStream bos = new BufferedOutputStream(os);

            byte[] buf = new byte[1024];
            int length = 0;
            length = sis.readLine(buf, 0, buf.length);//使用sis的读取数据的方法
            while (length != -1) {
                bos.write(buf, 0, length);
                length = sis.read(buf);
            }
            BufferedImage image;
            if (yy) {
                image = ImageIO.read(new File(filePath));
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                Binarizer binarizer = new HybridBinarizer(source);
                BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
                Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
                Result result = null;// 对图像进行解码
                try {
                    result = new MultiFormatReader().decode(binaryBitmap, hints);
                } catch (NotFoundException e) {

                }
                JSONObject content = JSONObject.parseObject(result.getText());
                String week = content.getString("week");
                String curse = content.getString("curse");
                String major = content.getString("major");
                String teacher = content.getString("teacher");
                String className = content.getString("className");
                String indexCurse = content.getString("indexCurse");
                String key2 = major + className + curse + teacher + week + indexCurse;

                int count1 = 0;
                int n = imgSignImService.findCountImgIndexByKey(key1);
            }
            try {
                int n = 1;
                int count1 = 0;
                PrintWriter out = null;
                net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
                response.setCharacterEncoding("utf-8");
                out = response.getWriter();

                if (n == 0) {
                    jsonObject.put("message", "未找到签到信息");
                    out.write(jsonObject.toString());
                    out.flush();
                    out.close();
                } else {
                        System.out.println("123123123");
                        imgSignImService.ImgSignIn(studentSignAccount, key1);
                        jsonObject.put("message", "签到成功");
                        out.write(jsonObject.toString());
                        out.flush();
                        out.close();
                }
            } catch (IOException e) {

            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }
}
