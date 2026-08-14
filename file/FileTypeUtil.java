package com.offlinew.android.file;

import android.webkit.MimeTypeMap;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FileTypeUtil {

    private String getMimeType(String filePath) {
        String mimeType = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        if (extension != null) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return mimeType;
    }

    public static String filenameToExt(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return ""; // unknown binary
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT);
    }
    public static String extensionToType(String ext){
        String type = fileTypes.get(ext.toLowerCase());
        return type != null ? type : "Unknown";
    }
    public static final Map<String, String> fileTypes = new HashMap<String, String>() {{

        //Image
        put("jpg", "Image");
        put("jpeg", "Image");
        put("png", "Image");
        put("gif", "Image");
        put("bmp", "Image");
        put("tiff", "Image");
        put("tif", "Image");
        put("svg", "Image");
        put("ico", "Image");
        put("webp", "Image");
        put("psd", "Image");
        put("heic", "Image");
        put("heif", "Image");
        put("raw", "Image");
        put("cr2", "Image");
        put("nef", "Image");
        put("orf", "Image");
        put("arw", "Image");
        put("dng", "Image");
        put("jfif", "Image");
        put("ai", "Image");
        put("eps", "Image");


        // Documents (all mapped to "Document")
        put("txt", "Document");
        put("pdf", "Document");
        put("doc", "Document");
        put("docx", "Document");
        put("odt", "Document");
        put("rtf", "Document");
        put("csv", "Document");
        put("html", "Document");
        put("htm", "Document");
        put("xml", "Document");
        put("json", "Document");
        put("epub", "Document");
        put("mobi", "Document");
        put("azw", "Document");
        put("xps", "Document");
        put("tex", "Document");
        put("wpd", "Document");
        put("key", "Document");
        put("pages", "Document");

        // Spreadsheets
        put("xls", "Spreadsheet");
        put("xlsx", "Spreadsheet");
        put("ods", "Spreadsheet");

        // Presentations
        put("ppt", "Presentation");
        put("pptx", "Presentation");
        put("odp", "Presentation");


        // Audio (all mapped to "Audio")
        put("mp3", "Audio");
        put("wav", "Audio");
        put("flac", "Audio");
        put("aac", "Audio");
        put("ogg", "Audio");
        put("m4a", "Audio");
        put("wma", "Audio");
        put("alac", "Audio");
        put("aiff", "Audio");
        put("pcm", "Audio");
        put("aif", "Audio");
        put("amr", "Audio");
        put("au", "Audio");
        put("mid", "Audio");
        put("midi", "Audio");
        put("opus", "Audio");
        put("dts", "Audio");
        put("ac3", "Audio");
        put("mka", "Audio");
        put("ra", "Audio");
        put("rm", "Audio");


        // Video (all mapped to "Video")
        put("mp4", "Video");
        put("avi", "Video");
        put("mkv", "Video");
        put("mov", "Video");
        put("wmv", "Video");
        put("flv", "Video");
        put("webm", "Video");
        put("vob", "Video");
        put("m4v", "Video");
        put("mpg", "Video");
        put("mpeg", "Video");
        put("3gp", "Video");
        put("3g2", "Video");
        put("m2ts", "Video");
        put("mts", "Video");
        put("ogv", "Video");
        put("qt", "Video");
        put("asf", "Video");
        put("rmvb", "Video");
        put("divx", "Video");
        put("f4v", "Video");
        put("mxf", "Video");
        put("ts", "Video");
        put("mpe", "Video");
        put("mpv", "Video");
        put("dv", "Video");


        // Archives (all mapped to "Archive")
        put("zip", "Archive");
        put("rar", "Archive");
        put("7z", "Archive");
        put("tar", "Archive");
        put("gz", "Archive");
        put("bz2", "Archive");
        put("xz", "Archive");
        put("lzma", "Archive");
        put("tgz", "Archive");
        put("tbz2", "Archive");
        put("txz", "Archive");
        put("tar.gz", "Archive");
        put("tar.bz2", "Archive");
        put("tar.xz", "Archive");
        put("tar.lzma", "Archive");
        put("z", "Archive");
        put("lz", "Archive");
        put("cab", "Archive");
        put("arj", "Archive");
        put("lha", "Archive");
        put("cpio", "Archive");
        put("rpm", "Archive");
        put("deb", "Archive");
        put("jar", "Archive");
        put("war", "Archive");
        put("ear", "Archive");


        // Source Code (all mapped to "Source Code")
        put("java", "Source Code");
        put("py", "Source Code");
        put("c", "Source Code");
        put("cpp", "Source Code");
        put("cs", "Source Code");
        put("js", "Source Code");
        put("css", "Source Code");
        put("php", "Source Code");
        put("rb", "Source Code");
        put("swift", "Source Code");
        put("kt", "Source Code");
        put("scala", "Source Code");
        put("go", "Source Code");
        put("rs", "Source Code");
        put("pl", "Source Code");
        put("r", "Source Code");
        put("m", "Source Code");
        put("mm", "Source Code");
        put("h", "Source Code");
        put("ino", "Source Code");
        put("asm", "Source Code");
        put("vb", "Source Code");
        put("lua", "Source Code");
        put("dart", "Source Code");
        put("groovy", "Source Code");
        put("jl", "Source Code");
        put("rkt", "Source Code");
        put("clj", "Source Code");
        put("hs", "Source Code");
        put("erl", "Source Code");
        put("ex", "Source Code");
        put("exs", "Source Code");
        put("tsx", "Source Code");
        put("jsx", "Source Code");
        put("vhd", "Source Code");
        put("vhdl", "Source Code");
        put("s", "Source Code");
        put("ps1", "Source Code");
        put("tcl", "Source Code");
        put("ml", "Source Code");
        put("scm", "Source Code");
        put("lisp", "Source Code");
        put("f", "Source Code");
        put("for", "Source Code");
        put("f90", "Source Code");
        put("ada", "Source Code");


        // Executables (all mapped to "Executable")
        put("exe", "Executable");
        put("bat", "Executable");
        put("cmd", "Executable");
        put("app", "Executable");
        put("msi", "Executable");
        put("bin", "Executable");
        put("sh", "Executable");
        put("out", "Executable");
        put("apk", "Executable");
        put("cgi", "Executable");
        put("com", "Executable");
        put("elf", "Executable");
        put("sfx", "Executable");
        put("pkg", "Executable");
        put("hqx", "Executable");
        put("xap", "Executable");
        put("msix", "Executable");
        put("appx", "Executable");
        put("cpl", "Executable");
        put("gadget", "Executable");
        put("xar", "Executable");
        put("run", "Executable");


        // Fonts (all mapped to "Font")
        put("ttf", "Font");
        put("otf", "Font");
        put("woff", "Font");
        put("woff2", "Font");
        put("eot", "Font");
        put("sfnt", "Font");
        put("fnt", "Font");
        put("fon", "Font");
        put("pfb", "Font");
        put("pfm", "Font");
        put("afm", "Font");
        put("ttc", "Font");
        put("otc", "Font");


        // Database files (all mapped to "Database")
        put("db", "Database");
        put("sql", "Database");
        put("mdb", "Database");
        put("accdb", "Database");
        put("sqlite", "Database");
        put("dbf", "Database");
        put("ndf", "Database");
        put("frm", "Database");
        put("ibd", "Database");
        put("myd", "Database");
        put("myi", "Database");
        put("sqlite3", "Database");
        put("pdb", "Database");
        put("db3", "Database");
        put("rds", "Database");
        put("btr", "Database");
        put("fdb", "Database");
        put("qlite", "Database");
        put("xsd", "Database"); // XML Schema Definition, sometimes used in database contexts
        put("xsql", "Database");

        // Miscellaneous
        put("iso", "Disc Image");
        put("dmg", "Disk Image");
        put("log", "Log");
        put("md", "Markdown");

    }};
}

/*

//Image
        fileTypes.put("jpg", "Image");
        fileTypes.put("jpeg", "Image");
        fileTypes.put("png", "Image");
        fileTypes.put("gif", "Image");
        fileTypes.put("bmp", "Image");
        fileTypes.put("tiff", "Image");
        fileTypes.put("tif", "Image");
        fileTypes.put("svg", "Image");
        fileTypes.put("ico", "Image");
        fileTypes.put("webp", "Image");
        fileTypes.put("psd", "Image");
        fileTypes.put("heic", "Image");
        fileTypes.put("heif", "Image");
        fileTypes.put("raw", "Image");
        fileTypes.put("cr2", "Image");
        fileTypes.put("nef", "Image");
        fileTypes.put("orf", "Image");
        fileTypes.put("arw", "Image");
        fileTypes.put("dng", "Image");
        fileTypes.put("jfif", "Image");
        fileTypes.put("ai", "Image");
        fileTypes.put("eps", "Image");


        // Documents (all mapped to "Document")
        fileTypes.put("txt", "Document");
        fileTypes.put("pdf", "Document");
        fileTypes.put("doc", "Document");
        fileTypes.put("docx", "Document");
        fileTypes.put("odt", "Document");
        fileTypes.put("rtf", "Document");
        fileTypes.put("csv", "Document");
        fileTypes.put("html", "Document");
        fileTypes.put("htm", "Document");
        fileTypes.put("xml", "Document");
        fileTypes.put("json", "Document");
        fileTypes.put("epub", "Document");
        fileTypes.put("mobi", "Document");
        fileTypes.put("azw", "Document");
        fileTypes.put("xps", "Document");
        fileTypes.put("tex", "Document");
        fileTypes.put("wpd", "Document");
        fileTypes.put("key", "Document");
        fileTypes.put("pages", "Document");

        // Spreadsheets
        fileTypes.put("xls", "Spreadsheet");
        fileTypes.put("xlsx", "Spreadsheet");
        fileTypes.put("ods", "Spreadsheet");

        // Presentations
        fileTypes.put("ppt", "Presentation");
        fileTypes.put("pptx", "Presentation");
        fileTypes.put("odp", "Presentation");


        // Audio (all mapped to "Audio")
        fileTypes.put("mp3", "Audio");
        fileTypes.put("wav", "Audio");
        fileTypes.put("flac", "Audio");
        fileTypes.put("aac", "Audio");
        fileTypes.put("ogg", "Audio");
        fileTypes.put("m4a", "Audio");
        fileTypes.put("wma", "Audio");
        fileTypes.put("alac", "Audio");
        fileTypes.put("aiff", "Audio");
        fileTypes.put("pcm", "Audio");
        fileTypes.put("aif", "Audio");
        fileTypes.put("amr", "Audio");
        fileTypes.put("au", "Audio");
        fileTypes.put("mid", "Audio");
        fileTypes.put("midi", "Audio");
        fileTypes.put("opus", "Audio");
        fileTypes.put("dts", "Audio");
        fileTypes.put("ac3", "Audio");
        fileTypes.put("mka", "Audio");
        fileTypes.put("ra", "Audio");
        fileTypes.put("rm", "Audio");


        // Video (all mapped to "Video")
        fileTypes.put("mp4", "Video");
        fileTypes.put("avi", "Video");
        fileTypes.put("mkv", "Video");
        fileTypes.put("mov", "Video");
        fileTypes.put("wmv", "Video");
        fileTypes.put("flv", "Video");
        fileTypes.put("webm", "Video");
        fileTypes.put("vob", "Video");
        fileTypes.put("m4v", "Video");
        fileTypes.put("mpg", "Video");
        fileTypes.put("mpeg", "Video");
        fileTypes.put("3gp", "Video");
        fileTypes.put("3g2", "Video");
        fileTypes.put("m2ts", "Video");
        fileTypes.put("mts", "Video");
        fileTypes.put("ogv", "Video");
        fileTypes.put("qt", "Video");
        fileTypes.put("asf", "Video");
        fileTypes.put("rmvb", "Video");
        fileTypes.put("divx", "Video");
        fileTypes.put("f4v", "Video");
        fileTypes.put("mxf", "Video");
        fileTypes.put("ts", "Video");
        fileTypes.put("mpe", "Video");
        fileTypes.put("mpv", "Video");
        fileTypes.put("dv", "Video");


        // Archives (all mapped to "Archive")
        fileTypes.put("zip", "Archive");
        fileTypes.put("rar", "Archive");
        fileTypes.put("7z", "Archive");
        fileTypes.put("tar", "Archive");
        fileTypes.put("gz", "Archive");
        fileTypes.put("bz2", "Archive");
        fileTypes.put("xz", "Archive");
        fileTypes.put("lzma", "Archive");
        fileTypes.put("tgz", "Archive");
        fileTypes.put("tbz2", "Archive");
        fileTypes.put("txz", "Archive");
        fileTypes.put("tar.gz", "Archive");
        fileTypes.put("tar.bz2", "Archive");
        fileTypes.put("tar.xz", "Archive");
        fileTypes.put("tar.lzma", "Archive");
        fileTypes.put("z", "Archive");
        fileTypes.put("lz", "Archive");
        fileTypes.put("cab", "Archive");
        fileTypes.put("arj", "Archive");
        fileTypes.put("lha", "Archive");
        fileTypes.put("cpio", "Archive");
        fileTypes.put("rpm", "Archive");
        fileTypes.put("deb", "Archive");
        fileTypes.put("jar", "Archive");
        fileTypes.put("war", "Archive");
        fileTypes.put("ear", "Archive");


        // Source Code (all mapped to "Source Code")
        fileTypes.put("java", "Source Code");
        fileTypes.put("py", "Source Code");
        fileTypes.put("c", "Source Code");
        fileTypes.put("cpp", "Source Code");
        fileTypes.put("cs", "Source Code");
        fileTypes.put("js", "Source Code");
        fileTypes.put("css", "Source Code");
        fileTypes.put("php", "Source Code");
        fileTypes.put("rb", "Source Code");
        fileTypes.put("swift", "Source Code");
        fileTypes.put("kt", "Source Code");
        fileTypes.put("scala", "Source Code");
        fileTypes.put("go", "Source Code");
        fileTypes.put("rs", "Source Code");
        fileTypes.put("pl", "Source Code");
        fileTypes.put("r", "Source Code");
        fileTypes.put("m", "Source Code");
        fileTypes.put("mm", "Source Code");
        fileTypes.put("h", "Source Code");
        fileTypes.put("ino", "Source Code");
        fileTypes.put("asm", "Source Code");
        fileTypes.put("vb", "Source Code");
        fileTypes.put("lua", "Source Code");
        fileTypes.put("dart", "Source Code");
        fileTypes.put("groovy", "Source Code");
        fileTypes.put("jl", "Source Code");
        fileTypes.put("rkt", "Source Code");
        fileTypes.put("clj", "Source Code");
        fileTypes.put("hs", "Source Code");
        fileTypes.put("erl", "Source Code");
        fileTypes.put("ex", "Source Code");
        fileTypes.put("exs", "Source Code");
        fileTypes.put("tsx", "Source Code");
        fileTypes.put("jsx", "Source Code");
        fileTypes.put("vhd", "Source Code");
        fileTypes.put("vhdl", "Source Code");
        fileTypes.put("s", "Source Code");
        fileTypes.put("ps1", "Source Code");
        fileTypes.put("tcl", "Source Code");
        fileTypes.put("ml", "Source Code");
        fileTypes.put("scm", "Source Code");
        fileTypes.put("lisp", "Source Code");
        fileTypes.put("f", "Source Code");
        fileTypes.put("for", "Source Code");
        fileTypes.put("f90", "Source Code");
        fileTypes.put("ada", "Source Code");


        // Executables (all mapped to "Executable")
        fileTypes.put("exe", "Executable");
        fileTypes.put("bat", "Executable");
        fileTypes.put("cmd", "Executable");
        fileTypes.put("app", "Executable");
        fileTypes.put("msi", "Executable");
        fileTypes.put("bin", "Executable");
        fileTypes.put("sh", "Executable");
        fileTypes.put("out", "Executable");
        fileTypes.put("apk", "Executable");
        fileTypes.put("cgi", "Executable");
        fileTypes.put("com", "Executable");
        fileTypes.put("elf", "Executable");
        fileTypes.put("sfx", "Executable");
        fileTypes.put("pkg", "Executable");
        fileTypes.put("hqx", "Executable");
        fileTypes.put("xap", "Executable");
        fileTypes.put("msix", "Executable");
        fileTypes.put("appx", "Executable");
        fileTypes.put("cpl", "Executable");
        fileTypes.put("gadget", "Executable");
        fileTypes.put("xar", "Executable");
        fileTypes.put("run", "Executable");


        // Fonts (all mapped to "Font")
        fileTypes.put("ttf", "Font");
        fileTypes.put("otf", "Font");
        fileTypes.put("woff", "Font");
        fileTypes.put("woff2", "Font");
        fileTypes.put("eot", "Font");
        fileTypes.put("sfnt", "Font");
        fileTypes.put("fnt", "Font");
        fileTypes.put("fon", "Font");
        fileTypes.put("pfb", "Font");
        fileTypes.put("pfm", "Font");
        fileTypes.put("afm", "Font");
        fileTypes.put("ttc", "Font");
        fileTypes.put("otc", "Font");


        // Database files (all mapped to "Database")
        fileTypes.put("db", "Database");
        fileTypes.put("sql", "Database");
        fileTypes.put("mdb", "Database");
        fileTypes.put("accdb", "Database");
        fileTypes.put("sqlite", "Database");
        fileTypes.put("dbf", "Database");
        fileTypes.put("ndf", "Database");
        fileTypes.put("frm", "Database");
        fileTypes.put("ibd", "Database");
        fileTypes.put("myd", "Database");
        fileTypes.put("myi", "Database");
        fileTypes.put("sqlite3", "Database");
        fileTypes.put("pdb", "Database");
        fileTypes.put("db3", "Database");
        fileTypes.put("rds", "Database");
        fileTypes.put("btr", "Database");
        fileTypes.put("fdb", "Database");
        fileTypes.put("qlite", "Database");
        fileTypes.put("xsd", "Database"); // XML Schema Definition, sometimes used in database contexts
        fileTypes.put("xsql", "Database");

        // Miscellaneous
        fileTypes.put("iso", "Disc Image");
        fileTypes.put("dmg", "Disk Image");
        fileTypes.put("log", "Log");
        fileTypes.put("md", "Markdown");
 */

/*


    // Images
    fileTypes.put("jpg", "Image");
    fileTypes.put("jpeg", "Image");
    fileTypes.put("png", "Image");
    fileTypes.put("gif", "Image");
    fileTypes.put("bmp", "Image");
    fileTypes.put("tiff", "Image");
    fileTypes.put("svg", "Vector Image");
    fileTypes.put("ico", "Icon Image");

    // Documents
    fileTypes.put("txt", "Text Document");
    fileTypes.put("pdf", "PDF Document");
    fileTypes.put("doc", "Microsoft Word Document");
    fileTypes.put("docx", "Microsoft Word Document");
    fileTypes.put("odt", "OpenDocument Text Document");
    fileTypes.put("rtf", "Rich Text Format Document");

    // Spreadsheets
    fileTypes.put("xls", "Microsoft Excel Spreadsheet");
    fileTypes.put("xlsx", "Microsoft Excel Spreadsheet");
    fileTypes.put("ods", "OpenDocument Spreadsheet");

    // Presentations
    fileTypes.put("ppt", "Microsoft PowerPoint Presentation");
    fileTypes.put("pptx", "Microsoft PowerPoint Presentation");
    fileTypes.put("odp", "OpenDocument Presentation");

    // Audio
    fileTypes.put("mp3", "Audio File");
    fileTypes.put("wav", "Waveform Audio File");
    fileTypes.put("flac", "Free Lossless Audio Codec");
    fileTypes.put("aac", "Advanced Audio Codec");
    fileTypes.put("ogg", "Ogg Vorbis Audio File");
    fileTypes.put("m4a", "MPEG-4 Audio File");
    fileTypes.put("wma", "Windows Media Audio File");

    // Video
    fileTypes.put("mp4", "MPEG-4 Video File");
    fileTypes.put("avi", "Audio Video Interleave File");
    fileTypes.put("mkv", "Matroska Video File");
    fileTypes.put("mov", "Apple QuickTime Movie");
    fileTypes.put("wmv", "Windows Media Video File");
    fileTypes.put("flv", "Flash Video File");
    fileTypes.put("webm", "WebM Video File");

    // Archives
    fileTypes.put("zip", "ZIP Archive");
    fileTypes.put("rar", "RAR Archive");
    fileTypes.put("tar", "TAR Archive");
    fileTypes.put("gz", "GZIP Compressed Archive");
    fileTypes.put("7z", "7-Zip Archive");
    fileTypes.put("bz2", "BZIP2 Compressed Archive");

    // Code/Programming Files
    fileTypes.put("java", "Java Source File");
    fileTypes.put("py", "Python Script");
    fileTypes.put("js", "JavaScript File");
    fileTypes.put("html", "HTML File");
    fileTypes.put("css", "Cascading Style Sheets File");
    fileTypes.put("php", "PHP Script");
    fileTypes.put("c", "C Source File");
    fileTypes.put("cpp", "C++ Source File");
    fileTypes.put("cs", "C# Source File");
    fileTypes.put("sh", "Shell Script");
    fileTypes.put("rb", "Ruby Script");

    // Executables
    fileTypes.put("exe", "Windows Executable File");
    fileTypes.put("bat", "Batch File");
    fileTypes.put("sh", "Shell Script");
    fileTypes.put("apk", "Android Package");

    // Fonts
    fileTypes.put("ttf", "TrueType Font");
    fileTypes.put("otf", "OpenType Font");
    fileTypes.put("woff", "Web Open Font Format");
    fileTypes.put("woff2", "Web Open Font Format 2");

    // Miscellaneous
    fileTypes.put("iso", "Disc Image File");
    fileTypes.put("dmg", "Apple Disk Image");
    fileTypes.put("csv", "Comma-Separated Values File");
    fileTypes.put("xml", "XML File");
    fileTypes.put("json", "JSON File");
    fileTypes.put("log", "Log File");
    fileTypes.put("md", "Markdown File");



 */
