package com.test;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : Test
 * @Description : 描述
 * @Author : Mr.Sakura
 * @Date: 2020-03-26 16:22
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Map<String,Double> map = new HashMap<>();

        map.put("K1",0.1);
        map.put("K2",0.2);
        map.put("K3",0.3);
        map.put("K4",0.4);
        map.put("K5",0.5);


        /*draw("/home/baidunetdiskdownload/834015602.jpeg","/home/834015602.jpeg","你好测试");*/
        addImage("/home/test/template.jpg","/home/test/631e3e67-19c3-48a4-b94d-c12bc3742728.png","/home/test/a380_watermark_bottom_right.jpeg");
    }

    /**
     * 在图片上插入新的图片
     * @param originalImagePath 原图片位置
     * @param insertImagePath 要插入图片的位置
     * @param newImagePath 生成的新图片的位置
     * @throws IOException
     */
    private static void addImage(String originalImagePath,String insertImagePath,String newImagePath) throws IOException {
        Font font = new Font("黑体", Font.BOLD, 45);

        BufferedImage sourceImg = ImageIO.read(new FileInputStream(originalImagePath));
        String[][] textConent = {
                {"血压(非空腹) : 6.0mmol/L", "时间: 2017年9月16日 10:52", "正常"},
                {"血压(非空腹) : 13.0mmol/L", "时间: 2017年9月16日 15:47", "过高"},
                {"血压(非空腹) : 6.3mmol/L", "时间: 2017年9月16日 16:13", "正常"},
                {"血压(非空腹) : 6.6mmol/L", "时间: 2017年9月17日 10:24", "正常"},
                {"血压(非空腹) : 15.1mmol/L", "时间: 2017年9月17日 11:10", "过高"}};
        int x = 150;
        int y = 540;

           generateImage(originalImagePath, newImagePath,
                    textConent, ImageIO.read(new File(
                           insertImagePath)), font, x, y,sourceImg);
        /* Thumbnails.of(insertImagePath).size(sourceImg.getWidth(),sourceImg.getHeight())
                .watermark(Positions.CENTER, ImageIO.read(new File(originalImagePath)),1f)
                .outputQuality(0.8f).toFile(newImagePath);*/
    }


    public static void generateImage(String inPath, String outPath,
                                     String[][] content, Image img, Font font, int start_point_x,
                                     int start_point_y,BufferedImage sourceImg) {
        try {
            File file = new File(inPath);
            Image image = ImageIO.read(file);
            int height = image.getHeight(null);
            int width = image.getWidth(null);
            int text_x = 0;
            int text_y = 0;
            int img_x = 0;
            int img_y = 520;
            int line_y = 498;
            Color color = new Color(89, 89, 89);
            BufferedImage bufferedImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            //文字去锯齿
            graphics.drawImage(image, 0, 0, width, height, null);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_NORMALIZE);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            if (img != null) {
                graphics.setFont(font);
                graphics.drawImage(img, sourceImg.getWidth()/2 - ((BufferedImage) img).getWidth()/2,
                        sourceImg.getHeight()/2 - ((BufferedImage) img).getHeight(), null);
                graphics.setColor(new Color(000, 000, 000));
                graphics.drawString("测试", 150, 1000);

                /*graphics.setFont(font);
                text_x = start_point_x;
                text_y = start_point_y;
                for (int i = 0; i < content.length; i++) {
                    String[] tempData = content[i];
                    color = new Color(89, 89, 89);
                    text_x = start_point_x;
                    if (i == 1){
                        text_y += 110;
                        img_y += 140;
                    } else if (i != 0 && i > 1){
                        text_y += 100;
                        img_y += 130;
                    }
                    line_y += 132;
                    for (int j = 0; j < tempData.length; j++) {
                        if (j == 1) {
                           *//* text_y += 50;
                            color = new Color(171, 171, 171);
                            graphics.drawLine(12, line_y, 750 - 12, line_y);*//*
                        } else if (j == 2) {
                            text_x = width - 124;
                            text_y -= 50;
                            img_x = 50;
                            if (tempData[2].equals("过高")){
                            } else {
                                color = new Color(71, 207, 162);
                            }

                        }
                        graphics.setColor(new Color(000, 000, 000));
                        graphics.drawString(tempData[j], text_x, text_y);
                    }
                }*/

            } else {
                System.out.println("构建数据的图片对象为空...");
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            ImageIO.write(bufferedImage, "png", fileOutputStream);
            System.out.println("图片添加文字完成" + width + "---" + height);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {

        }
    }


    private static void create2() {
        try
        {
            int width = 128;
            int height = 64;
            // 创建BufferedImage对象
            Font font=new Font("宋体",Font.PLAIN,16);
            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            // 获取Graphics2D
            Graphics2D g2d = image.createGraphics();
            // 画图
            g2d.setBackground(new Color(255,255,255));
            g2d.setPaint(new Color(0,0,0));
            g2d.clearRect(0, 0, width, height);
            g2d.drawString("名称：娃哈哈纯净水",0,10);
            g2d.drawString("产地：浙江杭州",0,26);
            g2d.drawString("品牌：娃娃哈哈",0,42);
            g2d.drawString("单价：9876543210",0,58);
            g2d.setFont(font);
            //释放对象
            g2d.dispose();
            // 保存文件
            ImageIO.write(image, "png", new File("/home/test.png"));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * @param imagePath 模板图片路径
     * @param path 新文件路径
     * @param content 要添加的内容
     */
    public static void draw(String imagePath,String path,String content){
        //读取图片文件，得到BufferedImage对象
        BufferedImage bimg;
        try {
            bimg = ImageIO.read(new FileInputStream(imagePath));

            //得到Graphics2D 对象
            Graphics2D g2d=(Graphics2D)bimg.getGraphics();
            //设置颜色和画笔粗细
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(3));
            g2d.setFont(new Font("TimesRoman", Font.BOLD, 80));

            //绘制图案或文字
            g2d.drawString(content, 150, 468);
            //保存新图片
            ImageIO.write(bimg, "JPG",new FileOutputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void create1() {
        int imageWidth = 128;//图片的宽度
        int imageHeight = 64;//图片的高度
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        try
        {
            Font font=new Font("新宋体",Font.PLAIN,12);
            graphics.setFont(font);
            graphics.fillRect(0, 0, imageWidth, imageHeight);
            graphics.setColor(new Color(0,0,0));//设置黑色字体,同样可以graphics.setColor(Color.black);
            graphics.drawString("产品：深圳雅辉呼叫器", 0, 10);
            graphics.drawString("网址:www.szsyhaf.com", 0, 36);
            ImageIO.write(image, "PNG", new File("/home/abc.png"));//生成图片方法一
            //ImageIO,可以生成不同格式的图片，比如JPG,PNG,GIF.....
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //生成图片方法二开始,只知道生成jpg格式的图片,这个方法其他格式的还是不知道怎么弄。
		/*try {
			FileOutputStream fos = new FileOutputStream("D:\\abc.jpg");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
        //生成图片方法二结束
        graphics.dispose();//释放资源
    }
}
