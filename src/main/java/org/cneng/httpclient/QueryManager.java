package org.cneng.httpclient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.cneng.httpclient.Utils.*;
import static org.cneng.httpclient.ConfigUtil.get;

/**
 * 工商局查询管理器
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/2/3
 *
 *
 */
public class QueryManager {
    private static final Logger _log = LoggerFactory.getLogger(QueryManager.class);
    /**
     * 首页地址
     */
    private String homepageUrl = "http://gsxt.gdgs.gov.cn/aiccips/";
    /**
     * 验证码图片生成地址
     */
    private String verifyPicUrl = "http://gsxt.gdgs.gov.cn/aiccips/verify.html";
    /**
     * 验证码服务器验证地址
     */
    private String checkCodeUrl = "http://gsxt.gdgs.gov.cn/aiccips/CheckEntContext/checkCode.html";
    /**
     * 信息显示URL
     */
    private String showInfoUrl = "http://gsxt.gdgs.gov.cn/aiccips/CheckEntContext/showInfo.html";
    /**
     * 下载的验证码图片文件夹
     */
    private String outdir = "D:/work/";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LinkedBlockingQueue<Company> compQueue;
    private LinkedBlockingQueue<String> redoQueue;
    private LinkedBlockingQueue<String> redoBug;
    private Map<String, Integer> redoCount;

    private static final QueryManager instance = new QueryManager();

    public static QueryManager getInstance() {
        return instance;
    }

    private QueryManager() {
        ConfigUtil.getInstance();
        homepageUrl = get("homepageUrl");
        verifyPicUrl = get("verifyPicUrl");
        checkCodeUrl = get("checkCodeUrl");
        showInfoUrl = get("showInfoUrl");
        outdir = get("outdir");
        compQueue = new LinkedBlockingQueue<>();
        redoQueue = new LinkedBlockingQueue<>();
        redoBug = new LinkedBlockingQueue<>();
        redoCount = new ConcurrentHashMap<>();
    }

    /**
     * 工商局网站上面通过关键字搜索企业信息
     *
     * @param keyword 关键字：注册号或者是企业名称
     * @return 企业地址
     */
    public String searchLocation(String keyword) {
        _log.info(sdf.format(new Date()));
        String result = null;
        String detailHtml = detailHtml(keyword)[0];
        try {
            if (detailHtml != null) {
                // 第八步：解析详情HTML页面，获取最后的地址
                result = JSoupUtil.parseLocation(detailHtml);
            }
            // ---------------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        }
        _log.info(sdf.format(new Date()));
        return result;
    }

    /**
     * 工商局网站上面通过关键字搜索企业信息
     *
     * @param keyword 关键字：注册号或者是企业名称
     * @param type    关键字类型：1-企业名称 2-注册号
     * @return 企业详情
     */
    public Company searchCompany(String keyword) {
        _log.info(sdf.format(new Date()));
        Company company = new Company();
        company.setCompanyName(keyword);
        // 这一步是核心算法
        String[] dhtml = detailHtml(keyword);
        // 下面搜索详情后面再做
        String detailHtml = dhtml != null ? dhtml[0] : null;
        company.setLink(dhtml != null ? dhtml[1] : null);
        company.setResultType(1);  // 预设是正常的
        try {
            if (detailHtml != null) {
                // 第八步：解析详情HTML页面，填充公司数据
                // 这里我需要再去异步提交一个HTTP请求
                // http://121.8.226.101:7001/search/search!entityShow?entityVo.pripid=440106106022010041200851
                // http://121.8.226.101:7001/search/search!investorListShow?entityVo.pripid=440106106022010041200851
                String link2 = company.getLink().replace("entityShow", "investorListShow");
                String investorHtml = showDetail(link2, null)[0];
                JSoupUtil.parseCompany(detailHtml, investorHtml, company);
            } else {
                // 说明不存在
                company.setResultType(3);
            }
            // ---------------------------------------------------------------------------------
        } catch (Exception e) {
            _log.error("通过关键字搜索企业error: " + keyword, e);
            try {
                redoQ(keyword);
            } catch (InterruptedException e1) {
                _log.error("通过关键字搜索企业error->InterruptedException");
            }
        }
        _log.info(sdf.format(new Date()));
        return company;
    }

    private static final Lock lockCompany = new ReentrantLock();
    /**
     * 根据redo队列中的企业名称去爬取公司信息，向队列中放置公司信息
     */
    private void produceCompany() {
        int threadCount = 10;
        //障栅集合点(同步器)
        final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
        ExecutorService executorService = Executors.newCachedThreadPool(
                new NamedThreadPoolFactory("---爬取公司信息---"));
        _log.info("--------爬取公司信息开始---------");
        for (int i = 0; i < threadCount - 1; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            try {
                                String compName = null;
                                if (lockCompany.tryLock()) {
                                    try {
                                        compName = redoQueue.poll(30000L, TimeUnit.MILLISECONDS);
                                    } finally {
                                        lockCompany.unlock();
                                    }
                                }
                                if (compName == null) break;
                                _log.info("获取到的company name=" + compName);
                                Company company = searchCompany(compName);
                                //生产对象
                                compQueue.put(company);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                        barrier.await();
                    } catch (Exception e) {
                        _log.error("爬取公司信息", e);
                    }
                }
            });
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //execException(2000L);
                    barrier.await();
                } catch (Exception e) {
                    _log.error("=============error=========${e}", e);
                    e.printStackTrace();
                }
            }
        });
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        _log.info("--------爬取公司信息结束---------");
    }

    /**
     * 根据redo队列中的企业名称去爬取公司信息，向队列中放置公司信息
     */
    private void produceCompany1() {
        try {
            while (true) {
                try {
                    //if (redoQueue.isEmpty()) break;
                    String compName = redoQueue.poll(30000L, TimeUnit.MILLISECONDS);
                    if (compName == null) break;
                    _log.info("获取到的company name=" + compName);
                    Company company = searchCompany(compName);
                    //生产对象
                    compQueue.put(company);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (Exception e) {
            _log.error("爬取公司信息", e);
        }
        _log.info("--------爬取公司信息结束---------");
    }

    /**
     * 最后更新Redo表
     */
    private void lastUpdateRedo() {
        // 总线程数
        int threadCount = 2;
        //障栅集合点(同步器)
        final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
        ExecutorService executorService = Executors.newCachedThreadPool(
                new NamedThreadPoolFactory("----更新Redo----"));
        _log.info("--------更新Redo开始---------");
        for (int i = 0; i < threadCount - 1; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    JdbcUtils.endRedo(redoBug);
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        _log.error("更新Redo线程await出错出错...");
                    }
                }
            });
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    _log.error("=============error=========${e}", e);
                    e.printStackTrace();
                }
            }
        });
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        _log.info("--------更新Redo结束---------");
    }

    /**
     * 最后更新idmap
     */
    private void lastUpdateIdMap() {
        // 先更新下idMapQueue
        Utils.updateIdMapQueue();
        // 总线程数
        int threadCount = 6;
        //障栅集合点(同步器)
        final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
        ExecutorService executorService = Executors.newCachedThreadPool(
                new NamedThreadPoolFactory("---更新IdMap---"));
        _log.info("--------更新IdMap开始---------");
        for (int i = 0; i < threadCount - 1; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    JdbcUtils.endIdMap(Utils.idMapQueue);
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        _log.error("更新IdMap线程await出错出错...");
                    }
                }
            });
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    _log.error("=============error=========${e}", e);
                    e.printStackTrace();
                }
            }
        });
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        _log.info("--------更新IdMap结束---------");
    }

    /**
     * 最后更新公司信息到数据库中
     */
    private void lastUpdateCompany() {
        int threadCount = 6;
        //障栅集合点(同步器)
        final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
        ExecutorService executorService = Executors.newCachedThreadPool(
                new NamedThreadPoolFactory("---更新公司信息---"));
        _log.info("--------更新公司信息开始---------");
        for (int i = 0; i < threadCount - 1; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    JdbcUtils.endCompany(instance.compQueue);
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        _log.error("更新公司信息线程await出错...");
                    }
                }
            });
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    _log.error("=============error=========${e}", e);
                    e.printStackTrace();
                }
            }
        });
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        _log.info("--------更新公司信息结束---------");
    }

    private static class NamedThreadPoolFactory implements ThreadFactory {
        String name;
        final AtomicInteger threadNumber = new AtomicInteger(1);

        NamedThreadPoolFactory(String name) {
            this.name = name;
        }

        public Thread newThread(Runnable r) {
            return new Thread(r, name + threadNumber.getAndIncrement());
        }
    }

    /**
     * 初始化企业名到redo列表中
     *
     * @param fileName 企业名列表文件名
     */
    public void initRedo(String fileName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String compName;
            while ((compName = in.readLine()) != null) {
                if (StringUtil.isNotBlank(compName)) {
                    _log.info("---企业名：" + compName);
                    compName = compName.replaceAll("（", "(").replaceAll("）", ")");
                    redoQueue.put(compName);
                }
            }
        } catch (Exception e) {
            _log.error("读取企业名文件出错。", e);
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 工商局网站上面通过关键字搜索企业，显示详情页面
     *
     * @param keyword 关键字：注册号或者是企业名称
     * @return 详情页面
     */
    private String[] detailHtml(String keyword) {
        String[] result = new String[2];
        try {
            String link;
            if (Utils.idMap.containsKey(keyword)) {
                link = Utils.idMap.get(keyword);
                if (StringUtil.isBlank(link)) return result;
                // 第七步：点击详情链接，获取详情HTML页面
                 result = showDetail(link, null);
            } else {
                _log.info(keyword + " - 的link要去爬取----");
                // 第一步：访问首页获取sessionid
                String jsessionid = fetchSessionId();
                // 第二步：先下载验证码图片到本地
                String[] downloads = downloadVerifyPic(jsessionid);
                // 第三步：获取验证码
                String checkcode = CheckCodeClient.checkCode(downloads[0]);
                if (checkcode == null) {
                    _log.info(keyword + "验证码获取为空，进入重做队列");
                    redoQ(keyword);
                    return null;
                }
                // 这一步还得处理下验证码
                checkcode = Utils.realCode(checkcode);
                // 第四步：调用服务器的验证码认证接口
                CheckCodeResult checkCodeResult = authenticate(keyword, checkcode, jsessionid);
                // 第五步：调用查询接口查询结果列表
                if ("1".equals(checkCodeResult.getFlag())) {
                    String searchPage = showInfo(checkCodeResult.getTextfield(), checkcode, jsessionid);
                    // 第六步：解析出第一条链接地址
                    if(searchPage.contains("验证码不正确或已失效")) {
                        _log.info("验证码不正确或已失效, 重新查询：" + keyword);
                        redoQ(keyword);
                    } else {
                        link = JSoupUtil.parseLink(searchPage);
                        _log.info("我找到的列表页面的第一个链接：" + link);
                        // 把link存起来
                        _log.error("keyword=" + keyword + ", link=" + link);
                        if (StringUtil.isBlank(link)) {
                            return result;
                        } else {
                            Utils.updateIdMap(keyword, link);
                            // 第七步：点击详情链接，获取详情HTML页面
                            result = showDetail(link, jsessionid);
                        }
                    }
                } else {
                    _log.info("验证码未能正确识别, 重新查询：" + keyword);
                    redoQ(keyword);
                }
            }
        } catch (Exception e) {
            _log.error("通过关键字搜索企业error: " + keyword, e);
            try {
                redoQ(keyword);
            } catch (InterruptedException e1) {
                _log.error("通过关键字搜索企业error->InterruptedException");
            }
        }
        return result;
    }

    /**
     * 程序入重做队列
     */
    private synchronized void redoQ(String key) throws InterruptedException {
        if (redoCount.containsKey(key)) {
            int c = redoCount.get(key);
            if (c > 3) {
                _log.error("重试次数超过了3次：" + key);
                redoBug.put(key);
                return;
            } else {
                redoCount.put(key, c + 1);
            }
        } else {
            redoCount.put(key, 1);
        }
        redoQueue.put(key);
    }

    /**
     * 先访问首页获取SessionID
     *
     * @return http://gsxt.gdgs.gov.cn/
     * @throws Exception
     */
    private String fetchSessionId() throws Exception {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        CloseableHttpClient httpclient = HttpClients.custom().
                setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
        try {
            HttpGet httpget = new HttpGet(homepageUrl);
            //_log.info("Executing request " + httpget.getRequestLine());
            // 所有请求的通用header：
            httpget.addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
            httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpget.addHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            httpget.addHeader("Accept-Encoding", "gzip, deflate");
            httpget.addHeader("Connection", "keep-alive");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        return null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            httpclient.execute(httpget, responseHandler);
            List<Cookie> cookies = context.getCookieStore().getCookies();
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    //_log.info(cookie.getName() + ":" + cookie.getValue());
                    //_log.info("******************************");
                    return cookie.getValue();
                }
            }
        } finally {
            httpclient.close();
        }
        return null;
    }

    /**
     * 下载验证码图片
     *
     * @return [下载图片地址, Cookie中的JSESSIONID]
     * @throws Exception
     */
    private String[] downloadVerifyPic(String jsessionId) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(verifyPicUrl + "?random=" + Math.random());
            // _log.info("Executing request " + httpget.getRequestLine());
            // 所有请求的通用header：
            httpget.addHeader("Accept", "image/png,image/*;q=0.8,*/*;q=0.5");
            httpget.addHeader("Cookie", "JSESSIONID=" + jsessionId);

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? doDownload(entity, outdir) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String doawloadPic = httpclient.execute(httpget, responseHandler);
            return new String[]{doawloadPic, jsessionId};
        } finally {
            httpclient.close();
        }
    }

    private String doDownload(final HttpEntity entity, String outdir) throws IOException {
        String fullname = outdir + randomUUID() + ".png";
        OutputStream outputStream = new FileOutputStream(new File(fullname));
        Args.notNull(entity, "Entity");
        final InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
                    "HTTP entity too large to be buffered in memory");
            final byte[] buff = new byte[4096];
            int readed;
            while ((readed = instream.read(buff)) != -1) {
                byte[] temp = new byte[readed];
                System.arraycopy(buff, 0, temp, 0, readed);   // 这句是关键
                outputStream.write(temp);
            }
        } finally {
            instream.close();
            outputStream.close();
        }
        return fullname;
    }

    /**
     * 验证码服务器验证接口
     *
     * @param keywork    关键字
     * @param checkcode  验证码
     * @param jsessionid jsessionid
     * @return 结果
     * @throws Exception
     */
    private CheckCodeResult authenticate(String keywork, String checkcode, String jsessionid) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(checkCodeUrl);
            _log.info("Executing request " + httppost.getRequestLine());

            // 所有请求的通用header：
            httppost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36" +
                    " (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httppost.addHeader("Accept-Encoding", "gzip,deflate");
            httppost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httppost.addHeader("Cookie", "JSESSIONID=" + jsessionid);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("textfield", keywork));
            params.add(new BasicNameValuePair("code", checkcode));
            UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params, "UTF-8");
            httppost.setEntity(reqEntity);
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httppost, responseHandler);
            //_log.info("----------------------------------------");
            //_log.info(responseBody);
            return JSON.parseObject(responseBody, CheckCodeResult.class);
        } finally {
            httpclient.close();
        }
    }

    /**
     * 查询列表结果
     *
     * @param textfield  验证码验证后返回的关键字
     * @param checkcode  验证码
     * @param jsessionid sessionid
     * @return 查询页面
     * @throws Exception
     */
    private String showInfo(String textfield, String checkcode, String jsessionid) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(showInfoUrl);
            // _log.info("Executing request " + httppost.getRequestLine());

            // 所有请求的通用header：
            httppost.addHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36" +
                    " (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.addHeader("Accept-Encoding", "gzip,deflate");
            httppost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httppost.addHeader("Cookie", "JSESSIONID=" + jsessionid);
            httppost.addHeader("Host", "gsxt.gdgs.gov.cn");
            httppost.addHeader("Origin", "http://gsxt.gdgs.gov.cn");
            httppost.addHeader("Pragma", "no-cache");
            httppost.addHeader("Referer", "http://gsxt.gdgs.gov.cn/aiccips/CheckEntContext/showInfo.html");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("textfield", textfield));
            params.add(new BasicNameValuePair("code", checkcode));
            _log.info("textfield=" + textfield + "|||" + "code=" + checkcode);
            UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params);
            httppost.setEntity(reqEntity);

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httppost, responseHandler);
            // _log.info("----------------------------------------");
            return responseBody;
        } finally {
            httpclient.close();
        }
    }

    /**
     * 查询详情
     *
     * @param detailUrl  详情页面链接
     * @param jsessionid sessionid
     * @return 详情页面
     * @throws Exception
     */
    public String[] showDetail(String detailUrl, String jsessionid) throws Exception {
        if (StringUtil.isBlank(detailUrl)) {
            return new String[]{null, ""};
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(detailUrl);
            // _log.info("Executing request " + httpget.getRequestLine());

            // 所有请求的通用header：
            httpget.addHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36" +
                    " (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
            httpget.addHeader("Accept-Encoding", "gzip,deflate,sdch");
            httpget.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            if (jsessionid != null) httpget.addHeader("Cookie", "JSESSIONID=" + jsessionid);

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            // _log.info("----------------------------------------");
            return new String[]{responseBody, detailUrl};
        } finally {
            httpclient.close();
        }
    }

    private static class CheckCodeResult {
        private String flag;
        private String textfield;
        private String tip;

        /**
         * 获取 textfield.
         *
         * @return textfield.
         */
        public String getTextfield() {
            return textfield;
        }

        /**
         * 获取 flag.
         *
         * @return flag.
         */
        public String getFlag() {
            return flag;
        }

        /**
         * 设置 textfield.
         *
         * @param textfield textfield.
         */
        public void setTextfield(String textfield) {
            this.textfield = textfield;
        }

        /**
         * 设置 flag.
         *
         * @param flag flag.
         */
        public void setFlag(String flag) {
            this.flag = flag;
        }

        /**
         * 设置 tip.
         *
         * @param tip tip.
         */
        public void setTip(String tip) {
            this.tip = tip;
        }

        /**
         * 获取 tip.
         *
         * @return tip.
         */
        public String getTip() {
            return tip;
        }
    }

    public static void main(String[] args) throws Exception {
        // instance.searchLocation("广州云宏信息科技股份有限公司");
        _log.info("第一步：先把要爬取的企业名列表放到redo中去");
        instance.initRedo(
                "D:/work/projects/gitprojects/thinking-java/src/main/resources/names.txt");
        _log.info("第二步：初始化idmap");
        JdbcUtils.startIdMap(Utils.idMap);
//        _log.info("第三步：初始化公司表");
//        JdbcUtils.startCompany(instance.redoQueue);
        _log.info("第四步：线程池实现抓取动作");
        instance.produceCompany();
        _log.info("第五步：执行完后更新idmap");
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.lastUpdateIdMap();
            }
        }).start();
        _log.info("第六步：执行完后更新公司信息");
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.lastUpdateCompany();
            }
        }).start();
        _log.info("第七步：执行完后更新Redo表");
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.lastUpdateRedo();
            }
        }).start();
    }

    /**
     * Gets 首页地址.
     *
     * @return Value of 首页地址.
     */
    public String getHomepageUrl() {
        return homepageUrl;
    }
}
