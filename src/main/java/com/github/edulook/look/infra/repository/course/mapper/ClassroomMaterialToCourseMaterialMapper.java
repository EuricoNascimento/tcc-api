package com.github.edulook.look.infra.repository.course.mapper;

import com.github.edulook.look.core.data.FileType;

import java.util.List;
import java.util.Locale;

import static com.github.edulook.look.core.model.Course.WorkMaterial.Material;

public class ClassroomMaterialToCourseMaterialMapper {
    private  static List<String> videoExtension = List.of(
        "mp4", "avi", "mkv", "mov", "wmv", "flv", "webm", "mpg", "mpeg", "3gp", "m4v", "ts", "vob",
        "m2ts", "mts", "divx", "rmvb", "ogv", "mpv", "mxf", "asf", "f4v", "rm", "mp2", "mp3", "m1v", "m2v", "mpe",
        "mpeg1", "mpeg2", "mpeg4", "mpg2", "mpg4", "ogm", "qt", "swf", "vcd", "xvid", "3g2", "3gp2", "3gpp", "amv",
        "dpg", "dvd", "h264", "mjpeg", "mod"
    );
    
    private static List<String> imageExtensions = List.of(
        "jpg", "jpeg", "png", "gif", "bmp", "svg", "webp", "tiff", "ico", "raw", "heif", "eps", "ai", "psd",
        "indd", "cdr", "wmf", "emf", "pcx", "pict", "jp2", "jxr", "hdp", "wdp", "dds", "dng", "cr2", "nef", "orf",
        "arw", "rw2", "raf", "sr2", "pef", "x3f", "mrw", "srf", "erf", "mef", "mos", "crw", "kdc", "dcr", "ptx",
        "pxn", "fff", "3fr", "mfw", "rwl", "srw", "spx", "rwz", "r3d", "cap", "bay", "iiq", "fff", "jpe", "jif",
        "jfif", "jfi", "jng", "jbig", "jbg", "jxr", "hdp", "wdp", "cur", "ani", "icns", "ico", "icn", "pic", "pct",
        "pnt", "pntg", "mac", "qtif", "qif", "lbm", "iff", "ilbm", "pbm", "pgm", "ppm", "pnm", "ras", "sun", "tga",
        "tpic", "vda", "icb", "vst", "wbmp", "xbm", "xpm", "xwd", "yuv", "sgi", "rgb", "rgba", "bw", "int", "inta",
        "vicar", "fits", "ftc", "fpx", "mic", "mpo", "mng", "j2k", "jpf", "jpx", "jpm", "mj2", "wdp", "jxr", "hdp",
        "dpx", "cin", "exr", "hdr", "rgbe", "xyze", "dcm", "dicom", "dic", "dcm30", "dcm40", "dcm50", "dcm60", "dcm70",
        "dcm80", "dcm90", "dcm100", "dcm200", "dcm300", "dcm400", "dcm500", "dcm600", "dcm700", "dcm800", "dcm900",
        "dcm1000", "dcm2000", "dcm3000", "dcm4000", "dcm5000", "dcm6000", "dcm7000", "dcm8000", "dcm9000", "dcm10000"
    );

    public static Material fromYoutubeResource(com.google.api.services.classroom.model.Material source) {
        return Material
            .builder()
            .originLink(source.getYoutubeVideo().getAlternateLink())
            .type(FileType.VIDEO)
            .description(source.getYoutubeVideo().getTitle())
            .build();
    }

    public static Material fromDriveResource(com.google.api.services.classroom.model.Material source) {
        var filename = source.getDriveFile().getDriveFile().getTitle();

        return Material
            .builder()
            .originLink(source.getDriveFile().getDriveFile().getAlternateLink())
            .type(getFiletype(filename))
            .description(source.getDriveFile().getDriveFile().getTitle())
            .build();
    }

    private static String getFiletype(String filename) {
        var slices = filename.split("[.]");
        if(slices.length < 2)
            return FileType.FILE;

        var extension  = slices[slices.length - 1 ].toLowerCase(Locale.ROOT);

        if (videoExtension.contains(extension))
            return FileType.VIDEO;
        else if (imageExtensions.contains(extension))
            return FileType.IMAGE;

        return FileType.FILE;
    }

    public static Material fromFormResource(com.google.api.services.classroom.model.Material source) {
        return Material
            .builder()
            .originLink(source.getForm().getFormUrl())
            .type(FileType.FORM)
            .description(source.getForm().getTitle())
            .build();
    }
}
