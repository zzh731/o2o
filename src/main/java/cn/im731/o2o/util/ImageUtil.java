package cn.im731.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 加水印
 */
public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 生成缩略图
     * @param thumbnailInputStream
     * @param targetAddr
     * @return 缩略图路径
     */
    public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr) {
        String readFileName = getRamdomFileName();
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + readFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnailInputStream)
                    .size(200, 200)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")), 1f)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("E:/JavaWEB/o2o/src/main/resources"+"/watermark.jpg")), 1f)//TODO 改成相对路径
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("E:/JavaWEB/o2o/src/main/resources"+"/watermark.jpg")), 1f)//TODO 改成相对路径
                    .outputQuality(0.8f)
                    .toFile(dest);
        } catch (Exception e) {
            throw new RuntimeException("创建缩略图失败："+e.toString());
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录
     * 相当于mkdir -P
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath .exists()) {
            dirPath.mkdirs();
        }
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名
     */
    public static String getRamdomFileName() {
        //获取随机的5位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return rannum+nowTimeStr;
    }

    /**
     * 判断storePath是文件路径还是目录的路径
     * 如果是文件路径则删除文件
     * 如果是目录路径，则删除目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        //获取到全路径
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            //如果是目录,删除目录中所有文件
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            //然后删除该目录
            fileOrPath.delete();
        }
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("E:/1.jpg"))
                .size(200,200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")), 1f)
                .outputQuality(0.8f)
                .toFile("E:/2.jpg");
    }
}
