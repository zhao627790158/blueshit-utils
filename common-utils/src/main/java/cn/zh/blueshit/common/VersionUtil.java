package cn.zh.blueshit.common;

import org.apache.maven.artifact.versioning.ComparableVersion;

/**
 * Created by zhaoheng on 16/8/18.
 */
public class VersionUtil {

    private VersionUtil() {

    }

    private static int compare(String current, String target) {
        ComparableVersion comparableVersionA = new ComparableVersion(current);
        ComparableVersion comparableVersionB = new ComparableVersion(target);
        return comparableVersionA.compareTo(comparableVersionB);
    }

    public static void main(String[] args) {
        System.out.println(VersionUtil.compare("1.1", "1.0"));
        System.out.println(VersionUtil.compare("1-1", "1-0"));
    }


}
