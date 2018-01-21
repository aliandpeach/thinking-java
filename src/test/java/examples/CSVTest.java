package examples;

import joptsimple.internal.Strings;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

import static examples.CSVTest.Headers1.*;

/**
 * CSVTest
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/11/22
 */
public class CSVTest {
    @Test
    public void testCsv1() throws Exception {
        Reader in = new FileReader("E:\\download\\云彩付文档汇总\\站点列表.csv");
//        Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(Headers1.class).parse(in);
        final CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
        int count = 0;
        for (CSVRecord record : parser) {
            String r1 = record.get("监测点编码");
            String r2 = record.get("监测点名称");
            String r3 = record.get("城市");
            String r4 = record.get("经度");
            String r5 = record.get("纬度");
            System.out.println(String.format("r1=%s, r2=%s, r3=%s, r4=%s, r5=%s", r1, r2, r3, r4, r5));
            count++;
            if (count == 5) break;
        }
    }

    public enum Headers1 {
        CODE, NAME, CITY, LONGITUDE, LATITUDE
    }

}


