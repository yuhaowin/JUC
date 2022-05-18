package com.yuhaowin.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class LocalBloomFilterUtils implements Closeable {

    private final static Logger LOG = LoggerFactory.getLogger(LocalBloomFilterUtils.class);
    private static final Double FALSE_POSITIVE_PROBABLITY = 0.03d;

    private static Long lastDay;
    private static BloomFilter BF;
    private static BloomFilter LAST_BF;

    /**
     * 布隆过滤器本地文件备份目录
     */
    public static String SNAPSHOT_BL_ROOT_PATH = "/data/br/conf/bfbk/";
    /**
     * 布隆过滤器大小1千万(占用内存8.7m左右)
     */
    public static int SNAPSHOT_BL_MAX_SIZE = 10000000;
    /**
     * 布隆过滤器缓存有效期
     */
    public static long SNAPSHOT_BL_CACHE_TIME = 24 * 60 * 60 * 1000l;

    private CountDownLatch cdl = new CountDownLatch(1);

    /**
     * 检查缓存有效期
     */
    private static void checkCache() {
        if (BF == null) {
            if (BF == null) {
                BF = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), SNAPSHOT_BL_MAX_SIZE, FALSE_POSITIVE_PROBABLITY);
                LOG.info("reinit bf");
            }
        }
        if (LAST_BF == null) {
            if (LAST_BF == null) {
                LAST_BF = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), SNAPSHOT_BL_MAX_SIZE, FALSE_POSITIVE_PROBABLITY);
                LOG.info("reinit last_bf");
            }
        }
        long currTime = System.currentTimeMillis();
        if (lastDay == null) {
            lastDay = getCurrDayMs(currTime);
        }
        if (currTime > lastDay + SNAPSHOT_BL_CACHE_TIME) {
            if (currTime > lastDay + SNAPSHOT_BL_CACHE_TIME) {
                LOG.info("re init bf");
                BF = LAST_BF;
                LAST_BF = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), SNAPSHOT_BL_MAX_SIZE, FALSE_POSITIVE_PROBABLITY);
                lastDay = getCurrDayMs(currTime);
            }
        }
    }

    private static long getCurrDayMs(long currTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(sdf.format(new Date(currTime))).getTime();
        } catch (ParseException e) {
            LOG.error(e.toString(), e);
        }
        return currTime;
    }

    public static boolean checkNotExist(String snapshotKey, EtlBizTypeEnum type) {
        checkCache();
        return !BF.mightContain(getBfKey(snapshotKey, type));
    }

    public static boolean addSnapshot(String snapshotKey, EtlBizTypeEnum type, Object snapshot) {
        checkCache();
        String bfKey = getBfKey(snapshotKey, type);
        Boolean res = BF.put(bfKey);
        LAST_BF.put(bfKey);
        return res;
    }

    private static String getBfKey(String snapshotKey, EtlBizTypeEnum type) {
        return type.getNumber() + snapshotKey;
    }


    @Override
    public void close() throws IOException {
        try {
            if (!File.separator.equals("/")) {//linux下进行关闭序列化布隆过滤器，防止启动后重复数据过多
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            File dir = new File(SNAPSHOT_BL_ROOT_PATH + sdf.format(new Date()) + File.separator);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (BF != null) {
                FileOutputStream fos = null;
                ObjectOutputStream oos = null;
                try {
                    fos = new FileOutputStream(new File(dir.getPath() + File.separator + "bf.obj"));
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(BF);
                    oos.flush();
                } catch (Exception e) {
                    LOG.error(e.toString(), e);
                } finally {
                    if (oos != null) {
                        oos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
            if (LAST_BF != null) {
                FileOutputStream fos = null;
                ObjectOutputStream oos = null;
                try {
                    fos = new FileOutputStream(new File(dir.getPath() + File.separator + "last_bf.obj"));
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(LAST_BF);
                    oos.flush();
                } catch (Exception e) {
                    LOG.error(e.toString(), e);
                } finally {
                    if (oos != null) {
                        oos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.toString(), e);
        }
        cdl.countDown();
    }


    public static void reloadLocalCache() {
        if (!File.separator.equals("/")) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date currDate = new Date();
        File dir = new File(SNAPSHOT_BL_ROOT_PATH + sdf.format(currDate) + "/");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj;
        try {
            LOG.info("reload bf cache");
            File bfFile = new File(dir.getPath() + File.separator + "bf.obj");
            if (bfFile.exists()) {
                LOG.info("bf exis to load");
                fis = new FileInputStream(bfFile);
                ois = new ObjectInputStream(fis);
                obj = ois.readObject();
                BF = (BloomFilter) obj;
                lastDay = sdf.parse(sdf.format(currDate)).getTime();
                bfFile.delete();
            }
        } catch (Exception e) {
            LOG.error(e.toString(), e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                    LOG.error(e.toString(), e);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    LOG.error(e.toString(), e);
                }

            }
        }
        try {
            LOG.info("reload last_bf cache");
            File bfFile = new File(dir.getPath() + File.separator + "last_bf.obj");
            if (bfFile.exists()) {
                LOG.info("last_bf exis to load");
                fis = new FileInputStream(bfFile);
                ois = new ObjectInputStream(fis);
                obj = ois.readObject();
                LAST_BF = (BloomFilter) obj;
                bfFile.delete();
            }
        } catch (Exception e) {
            LOG.error(e.toString(), e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                    LOG.error(e.toString(), e);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    LOG.error(e.toString(), e);
                }
            }
        }
    }
}