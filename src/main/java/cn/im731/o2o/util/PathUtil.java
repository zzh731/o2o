package cn.im731.o2o.util;

public class PathUtil {
    private static String seperator = System.getProperty("file.seperator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "E:/00tempFiles/shopImg";
        } else {
            basePath = "/home/zzh/shopImg";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
