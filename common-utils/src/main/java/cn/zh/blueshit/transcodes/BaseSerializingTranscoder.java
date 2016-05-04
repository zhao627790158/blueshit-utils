package cn.zh.blueshit.transcodes;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by zhaoheng on 2016/5/4.
 */
public abstract class BaseSerializingTranscoder {


    private final int maxSize;

    public BaseSerializingTranscoder(int maxSize) {
        this.maxSize = maxSize;
    }


    protected byte[] serialize(Object o) {
        if (o == null) {
            throw new NullPointerException("Can\'t serialize null");
        }
        Object rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        byte[] rv1;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(o);
            os.close();
            bos.close();
            rv1 = bos.toByteArray();
        } catch (IOException var9) {
            throw new IllegalArgumentException("Non-serializable object", var9);
        } finally {
            CloseUtil.close(os);
            CloseUtil.close(bos);
        }
        return rv1;
    }


    protected Object deserialize(byte[] in) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(is);
            CloseUtil.close(bis);
        }
        return rv;

    }


    protected byte[] compress(byte[] in) {
        if (in == null) {
            throw new NullPointerException("Can\'t compress null");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gz = null;
        try {
            gz = new GZIPOutputStream(bos);
            gz.write(in);
        } catch (IOException var8) {
            throw new RuntimeException("IO exception compressing data", var8);
        } finally {
            CloseUtil.close(gz);
            CloseUtil.close(bos);
        }
        byte[] rv = bos.toByteArray();
        return rv;
    }

    protected byte[] decompress(byte[] in) {
        ByteArrayOutputStream bos = null;
        if (in != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(in);
            bos = new ByteArrayOutputStream();
            GZIPInputStream gis = null;
            try {
                gis = new GZIPInputStream(bis);
                byte[] e = new byte[8192];
                boolean r = true;
                int r1;
                while ((r1 = gis.read(e)) > 0) {
                    bos.write(e, 0, r1);
                }
            } catch (IOException e) {
                e.printStackTrace();
                bos = null;
            } finally {
                CloseUtil.close(gis);
                CloseUtil.close(bis);
                CloseUtil.close(bos);
            }
        }
        return bos == null ? null : bos.toByteArray();
    }


}
