//package com.vincent.framework.http;
//
//import android.util.SparseArray;
//
//
//import com.vincent.framework.BuildConfig;
//import com.vincent.framework.LibApplication;
//import com.vincent.framework.utils.LogUtil;
//
//import java.io.InputStream;
//import java.security.KeyStore;
//import java.security.SecureRandom;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.Random;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.TrustManagerFactory;
//import javax.net.ssl.X509TrustManager;
//
//import okhttp3.OkHttpClient;
//
///**
// * Created by nan.liu on 1/15/15.
// */
//public class HTTPClient {
//
//    private static final String KEY_STORE_TYPE_BKS = "bks";//证书类型 固定值
//    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型 固定值
//
//    private static final String TAG = "AirTouchHTTPClient";
//    private Long timeDeviation = 0L;
//    private OkHttpClient mOkHttpClient;
//    private static final int SESSION_TIMEOUT = 15 * 60;
//    protected static final int MAX_RANDOM_REQUEST_ID = 1000000;
//
//    protected static Random sRandom = new SecureRandom();
//
//    protected SSLContext sslContext;
//
//    private static HTTPClient mHttpClient = new HTTPClient();
//
//    public static HTTPClient getInstance() {
//        return mHttpClient;
//    }
//
//    public int executeHTTPRequest(HTTPRequestParams httpRequestParams,
//                                  IReceiveResponse receiveResponse) {
//
//        final int requestId = sRandom.nextInt(MAX_RANDOM_REQUEST_ID);
//        httpRequestParams.setRandomRequestID(requestId);
//        HTTPRequestManager httpRequestManager = new HTTPRequestManager(httpRequestParams);
//        RequestTask requestTask = new RequestTask(httpRequestManager, receiveResponse);
//        AsyncTaskExecutorUtil.executeAsyncTask(requestTask);
//        mRequestTaskSparseArray.append(requestId, requestTask);
//        return requestId;
//    }
//
//    public HTTPRequestResponse executeMethodHTTPRequest(HTTPRequestParams httpRequestParams,
//                                                        IActivityReceive receiveResponse, int connectTimeout, int readTimeout) {
//        final int requestId = sRandom.nextInt(MAX_RANDOM_REQUEST_ID);
//        httpRequestParams.setRandomRequestID(requestId);
//        HTTPRequestManager httpRequestManager = new HTTPRequestManager(httpRequestParams);
//        httpRequestManager.setConnectTimeout(connectTimeout);
//        httpRequestManager.setReadTimeout(readTimeout);
//
//        OkHttpClient okHttpClient = getUnsafeOkHttpClient(httpRequestParams);
//
//        String body = "";
//        if (httpRequestParams.getOtherParams() != null) {
//            body = httpRequestParams.getOtherParamsJsonStr();
//        }
//
//        return httpRequestManager.sendRequest(okHttpClient, body);
//    }
//
//    /**
//     * 获取oKHttpClient对象
//     *
//     * @param httpRequestParams
//     * @return
//     */
//    public OkHttpClient getOkHttpClient(HTTPRequestParams httpRequestParams) {
//        final int requestId = sRandom.nextInt(MAX_RANDOM_REQUEST_ID);
//        httpRequestParams.setRandomRequestID(requestId);
//
//        return getUnsafeOkHttpClient(httpRequestParams);
//    }
//
//    /**
//     * 执行http 请求
//     *
//     * @param httpRequestParams
//     * @param okHttpClient
//     * @param connectTimeout
//     * @param readTimeout
//     * @return
//     */
//    public HTTPRequestResponse execute(HTTPRequestParams httpRequestParams, OkHttpClient okHttpClient,
//                                       int connectTimeout, int readTimeout) {
//        HTTPRequestManager httpRequestManager = new HTTPRequestManager(httpRequestParams);
//        httpRequestManager.setConnectTimeout(connectTimeout);
//        httpRequestManager.setReadTimeout(readTimeout);
//
//        String body = httpRequestParams.getRequestBody();
//        if (httpRequestParams.getOtherParams() != null) {
//            body = httpRequestParams.getOtherParamsJsonStr();
//        }
//
//        return httpRequestManager.sendRequest(okHttpClient, body);
//    }
//
//
//    public HTTPRequestResponse executeImgMethodHTTPRequest(HTTPRequestParams httpRequestParams,
//                                                           IActivityReceive receiveResponse, int connectTimeout, int readTimeout) {
//        final int requestId = sRandom.nextInt(MAX_RANDOM_REQUEST_ID);
//        httpRequestParams.setRandomRequestID(requestId);
//        HTTPRequestManager httpRequestManager = new HTTPRequestManager(httpRequestParams);
//        httpRequestManager.setConnectTimeout(connectTimeout);
//        httpRequestManager.setReadTimeout(readTimeout);
//
//        OkHttpClient okHttpClient = getUnsafeOkHttpClient(httpRequestParams);
//
//        String body = httpRequestParams.getRequestBody();
//        if (httpRequestParams.getOtherParams() != null) {
//            body = httpRequestParams.getOtherParamsJsonStr();
//        }
//
//        return httpRequestManager.sendImgRequest(okHttpClient, body);
//    }
//
//
//    protected OkHttpClient getUnsafeOkHttpClientNoCer() {
//        try {
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init((KeyStore) null);
//            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
//            final X509TrustManager origTrustmanager = (X509TrustManager) trustManagers[0];
//
//            TrustManager[] wrappedTrustManagers = new TrustManager[]{
//                    new X509TrustManager() {
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return origTrustmanager.getAcceptedIssuers();
//                        }
//
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
//                            try {
//                                origTrustmanager.checkClientTrusted(certs, authType);
//                            } catch (CertificateException e) {
//                                LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
//                            }
//                        }
//
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
//                            if (!BuildConfig.DEBUG) {
//                                if (certs == null || certs.length == 0) {
//                                    throw new IllegalArgumentException("certificate is null or empty");
//                                }
//                                if (authType == null || authType.length() == 0) {
//                                    throw new IllegalArgumentException("authtype is null or empty");
//                                }
//                                try {
//                                    origTrustmanager.checkServerTrusted(certs, authType);
//                                } catch (CertificateException e) {
//                                    throw new CertificateException("certificate is not trust");
//                                }
//                            }
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, wrappedTrustManagers, new SecureRandom());
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//            if (mOkHttpClient == null) {
//                mOkHttpClient = new OkHttpClient();
//                mOkHttpClient.setSslSocketFactory(sslSocketFactory);
//            }
//            return mOkHttpClient.clone();
//        } catch (Exception e) {
//            LogUtil.error(TAG, "getUnsafeOkHttpClientNoCer", e);
//        }
//        return null;
//    }
//
//
//    public void setProductCertificates(String cerFileName) {
//        InputStream ksIn = null;
//
//        try {
//            sslContext = SSLContext.getInstance("TLS");
//            ksIn = LibApplication.getContext().getResources().getAssets().open(cerFileName);
//            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509", "BC");
//            Certificate cer = cerFactory.generateCertificate(ksIn);
//
//
//            //创建一个证书库，并将证书导入证书库
//            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
//            keyStore.load(null, null);
//            keyStore.setCertificateEntry("trust", cer);
//
//
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//
//            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
//            final X509TrustManager origTrustmanager = (X509TrustManager) trustManagers[0];
//            TrustManager[] wrappedTrustManagers = new TrustManager[]{
//                    new X509TrustManager() {
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return origTrustmanager.getAcceptedIssuers();
//                        }
//
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
//                            try {
//                                origTrustmanager.checkClientTrusted(certs, authType);
//                            } catch (CertificateException e) {
//                                LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
//                            }
//                        }
//
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
//                            if (certs == null || certs.length == 0) {
//                                throw new IllegalArgumentException("certificate is null or empty");
//                            }
//                            if (authType == null || authType.length() == 0) {
//                                throw new IllegalArgumentException("authtype is null or empty");
//                            }
//                            try {
//                                origTrustmanager.checkServerTrusted(certs, authType);
//                            } catch (CertificateException e) {
//                                throw new CertificateException("certificate is not trust");
//                            }
//                        }
//                    }
//            };
//
//            sslContext.init(null, wrappedTrustManagers, new SecureRandom());
//
//            NoSSLv3SocketFactory sslSocketFactory = new NoSSLv3SocketFactory(sslContext);
//            mOkHttpClient.setSslSocketFactory(sslSocketFactory);
//
//
//        } catch (CertificateException e) {
//            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
//        } catch (Exception e) {
//            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
//        } finally {
//            try {
//                if (ksIn != null) {
//                    ksIn.close();
//                }
//            } catch (Exception e) {
//                LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
//            }
//
//        }
//
//    }
//
//
//    protected OkHttpClient getUnsafeOkHttpClientWithCer(HTTPRequestParams httpRequestParams) {
//        try {
//
////            if (mOkHttpClient == null) {
//            mOkHttpClient = new OkHttpClient();
//
//            String cerFileName = httpRequestParams.getUrl().contains("https://homecloud.honeywell.com.cn/v1/api/") ? "GeoTrust_Global_CA.PEM" : "qa.cer";
//            setProductCertificates(cerFileName);
//
//            mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
////            }
//            return mOkHttpClient.clone();
//        } catch (Exception e) {
//            LogUtil.error(TAG, "getUnsafeOkHttpClientWithCer", e);
//        }
//        return null;
//    }
//
//    protected OkHttpClient getUnsafeOkHttpClient(HTTPRequestParams httpRequestParams) {
//        if (httpRequestParams != null && httpRequestParams.getUrl() != null && httpRequestParams.getUrl().startsWith("https")) {
//            return getUnsafeOkHttpClientWithCer(httpRequestParams);
//
//        } else {
//            return getUnsafeOkHttpClientNoCer();
//        }
//    }
//
//    /**
//     * Return whether a request (specified by its id) is still in progress or not
//     *
//     * @param requestId The request id
//     * @return whether the request is still in progress or not.
//     */
//    public boolean isRequestInProgress(final int requestId) {
//        return (mRequestTaskSparseArray.indexOfKey(requestId) >= 0);
//    }
//
//    public void cancelRequest(final int requestId) {
//        mRequestTaskSparseArray.remove(requestId);
//    }
//
//}
