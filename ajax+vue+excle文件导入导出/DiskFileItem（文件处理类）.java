
package org.apache.commons.fileupload.disk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileItemHeadersSupport;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ParameterParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.DeferredFileOutputStream;

/**
 * 接口org.apache.commons.fileupload.FileItem FileItem的默认实现
 * 从org.apache.commons.fileupload.DiskFileUpload实例检索此类的实例后，
 * 1. 可以使用#get()一次性请求文件的所有内容，
 * 2. 也可以使用#getInputStream()获取java.io.InputStream InputStream并处理该文件，而不尝试将其加载到内存中，这可能会使用大型文件。
 *
 * 当使用DiskFileItemFactory时，您应该考虑以下几点：
 * 	1.临时文件一旦不再需要就会自动删除。（更准确地说，当对应的{@link java.io.File}实例被垃圾回收时。）
 *  2.这是通过所谓的reaper线程完成的，这个线程在类org.apache.commons.io.FileCleaner被加载时自动启动
 */
public class DiskFileItem implements FileItem, FileItemHeadersSupport {
    private static final long serialVersionUID = 2237570099615271025L;

    // 默认的内容编码
    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    private static final String UID = new java.rmi.server.UID().toString().replace(':', '_').replace('-', '_');

    // 用于唯一标识符的生成
    private static int counter = 0;

    // 浏览器提供的表单字段名
    private String fieldName;

    // 浏览器传递的内容类型，如果未定义为null
    private String contentType;

    // 当前FileItem是否是一个简单的表单字段
    private boolean isFormField;

    // 上传文件的文件名，如果当前FileItem是表单字段为null
    private String fileName;

    // 当前FileItem的大小，单位为字节
    private long size = -1;

    // 阈值，上传文件大小小于此值保存在内存，大于此值缓存到本地
    private int sizeThreshold;

    // 上传的文件将被存储在磁盘上的目录
    private File repository;

    // 文件的缓存内容
    private byte[] cachedContent;

    // 当前FileItem的默认输出流
    private transient DeferredFileOutputStream dfos;

    // 使用的临时文件
    private transient File tempFile;

    // 用于序列化当前FileItem内容的文件
    private File dfosFile;

    // 当前FileItem的header
    private FileItemHeaders headers;

    /**
     * Constructs a new DiskFileItem instance.
     *
     * @param fieldName     表单字段名
     * @param contentType   浏览器传递的内容类型，如果未指定为null
     * @param isFormField   当前FileItem是否是一个表单字段，否则就是一个上传文件
     * @param fileName      上传文件的原始文件名，如果当前FielItem为表单字段，fileName为null
     * @param sizeThreshold 阈值，上传文件大于此值缓存到磁盘，小于就保存在内存
     * @param repository    当上传文件大于sizeThreshold，上传文件缓存到磁盘的文件路径
     */
    public DiskFileItem(String fieldName, String contentType, boolean isFormField, String fileName, int sizeThreshold, File repository) {
        this.fieldName = fieldName;
        this.contentType = contentType;
        this.isFormField = isFormField;
        this.fileName = fileName;
        this.sizeThreshold = sizeThreshold;
        this.repository = repository;
    }

    /*
     * 返回一个用于存储上传文件数据的输出流
     * FileUploadBase # parseRequest(RequestContext ctx) # Streams.copy(item.openStream(), fileItem.getOutputStream(), true);
     * 当FileItem刚实例化，dfos为null，调用getOutputStream()获取缓存文件Xxx.tmp，将上传文件缓存到Xxx.tmp中
     */
    public OutputStream getOutputStream() throws IOException {
        if (dfos == null) {
            // 返回一个用于存储上传文件的临时文件：Xxx.tmp
            File outputFile = getTempFile();
            dfos = new DeferredFileOutputStream(sizeThreshold, outputFile);
        }
        return dfos;
    }

    // 返回上传文件的输入流
    public InputStream getInputStream() throws IOException {
        if (!isInMemory()) {// 上传文件缓存在磁盘，打开文件流
            return new FileInputStream(dfos.getFile());
        }
        // 上传文件保存在内存
        if (cachedContent == null) {
        }
        return new ByteArrayInputStream(cachedContent);
    }

    // 从ContenType中解析出内容编码
    public String getCharSet() {
        ParameterParser parser = new ParameterParser();
        parser.setLowerCaseNames(true);
        Map params = parser.parse(getContentType(), ';');
        return (String) params.get("charset");
    }

    // 使用默认的编码将文件的内容作为字符串返回
    public String getString() {
        byte[] rawdata = get();
        String charset = getCharSet();
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        try {
            return new String(rawdata, charset);
        } catch (UnsupportedEncodingException e) {
            return new String(rawdata);
        }
    }

    // 使用指定的编码将文件的内容作为字符串返回
    public String getString(final String charset) throws UnsupportedEncodingException {
        return new String(get(), charset);
    }

    /**
     * 将上传文件的内容作为字节数组返回
     * 如果上传文件没有缓存在内存中，它们将从磁盘加载并缓存
     */
    public byte[] get() {
        if (isInMemory()) {// 缓存在内存中
            if (cachedContent == null) {
                cachedContent = dfos.getData();
            }
            return cachedContent;
        }
        byte[] fileData = new byte[(int) getSize()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(dfos.getFile());
            // 从输入流中读取，存储在fileData中
            fis.read(fileData);
        } catch (IOException e) {
            fileData = null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return fileData;
    }

    // 返回上传文件的大小，单位为字节
    public long getSize() {
        if (size >= 0) {
            return size;
        } else if (cachedContent != null) {
            return cachedContent.length;
        } else if (dfos.isInMemory()) {
            return dfos.getData().length;
        } else {
            return dfos.getFile().length();
        }
    }

    /**
     * 将上传文件写入磁盘的方便方法
     */
    public void write(File file) throws Exception {
        if (isInMemory()) {// 上传文件缓存在内存中
            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(file);
                fout.write(get());
            } finally {
                if (fout != null) {
                    fout.close();
                }
            }
        } else {// 缓存到磁盘
            File outputFile = getStoreLocation();
            if (outputFile != null) {
                // Save the length of the file
                size = outputFile.length();
                // 上传的文件正在存储在磁盘上的临时位置，以便将其移动到所需的文件。
                if (!outputFile.renameTo(file)) {
                    BufferedInputStream in = null;
                    BufferedOutputStream out = null;
                    try {
                        in = new BufferedInputStream(new FileInputStream(outputFile));
                        out = new BufferedOutputStream(new FileOutputStream(file));
                        IOUtils.copy(in, out);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                // ignore
                            }
                        }
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                // ignore
                            }
                        }
                    }
                }
            } else {
                /*
                 * 无论什么原因，我们无法将文件写入磁盘。
                 */
                throw new FileUploadException("Cannot write uploaded file to disk!");
            }
        }
    }

    /**
     * 序列化, 写入此对象的状态
     *
     * @param out 要被写入流的状态
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Read the data
        if (dfos.isInMemory()) {
            cachedContent = get();
        } else {
            cachedContent = null;
            dfosFile = dfos.getFile();
        }
        // write out values
        out.defaultWriteObject();
    }

    // 反序列化,
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // read values
        in.defaultReadObject();

        OutputStream output = getOutputStream();
        if (cachedContent != null) {// 缓存数据
            output.write(cachedContent);
        } else {
            FileInputStream input = new FileInputStream(dfosFile);
            IOUtils.copy(input, output);
            dfosFile.delete();
            dfosFile = null;
        }
        output.close();
        cachedContent = null;
    }

    // 返回一个用于存储上传文件的临时文件
    protected File getTempFile() {
        if (tempFile == null) {
            // 临时文件路劲
            File tempDir = repository;
            if (tempDir == null) {
                tempDir = new File(System.getProperty("java.io.tmpdir"));
            }
            // 临时文件名
            String tempFileName = "upload_" + UID + "_" + getUniqueId() + ".tmp";
            // 临时文件
            tempFile = new File(tempDir, tempFileName);
        }
        return tempFile;
    }

    // 删除FileItem的底层存储，包括删除任何关联的临时磁盘文件
    public void delete() {
        cachedContent = null;
        File outputFile = getStoreLocation();
        if (outputFile != null && outputFile.exists()) {
            outputFile.delete();
        }
    }

    // 返回当前FileItem存储在磁盘上的位置，如果存储在内存中，返回null
    public File getStoreLocation() {
        return dfos == null ? null : dfos.getFile();
    }

    // 从临时存储中删除文件内容
    protected void finalize() {
        File outputFile = dfos.getFile();
        if (outputFile != null && outputFile.exists()) {
            outputFile.delete();
        }
    }

    // 返回一个唯一标识符
    private static String getUniqueId() {
        final int limit = 100000000;
        int current;
        synchronized (DiskFileItem.class) {
            current = counter++;
        }
        String id = Integer.toString(current);

        if (current < limit) {
            id = ("00000000" + id).substring(id.length());
        }
        return id;
    }

    public String toString() {
        return "name=" + this.getName() + ", StoreLocation=" + String.valueOf(this.getStoreLocation())
                + ", size=" + this.getSize() + "bytes, " + "isFormField=" + isFormField() + ", FieldName=" + this.getFieldName();
    }

    // 返回浏览器传递的内容类型，如果未指定返回null
    public String getContentType() {
        return contentType;
    }

    // 返回上传文件的原始文件名
    public String getName() {
        return fileName;
    }

    // 当前上传文件是否存储在内存中
    public boolean isInMemory() {
        if (cachedContent != null) {
            return true;
        }
        return dfos.isInMemory();
    }

    // 返回字段名
    public String getFieldName() {
        return fieldName;
    }

    // 设置字段名
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    // 返回当前FileItem是否是一个表单字段，false表示是一个上传文件
    public boolean isFormField() {
        return isFormField;
    }

    public void setFormField(boolean state) {
        isFormField = state;
    }

    // 返回当前FileItem的headers
    public FileItemHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(FileItemHeaders pHeaders) {
        headers = pHeaders;
    }

}