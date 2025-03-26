package elink.suezShimla.water.crm;

public class Ssl_pinning {
 /*   static Context contextH;

    public static void allowCERSSL(Context context) throws CertificateException, IOException {
        try {
            contextH = context;
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.certificate);

            Certificate ca;
            ca = cf.generateCertificate(caInput);

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore;

            keyStore = KeyStore.getInstance(keyStoreType);

            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext sslCon = SSLContext.getInstance("TLSv1.3");
            sslCon.init(null, tmf.getTrustManagers(), null);

            //  wrapHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());
            *//********************working**************************//*
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            HttpsURLConnection.setDefaultSSLSocketFactory(sslCon.getSocketFactory());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //  e.printStackTrace();
        }
    }






//            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    if(hostname.contains("tab-banking.saraswatbank.co.in")||hostname.contains("10.20.5.214"))
//                    {
//                        return true;
//                    }
//                    else
//                    {
//                        return  false;
//                    }
//                }
//            });
//
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslCon.getSocketFactory());
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//
//        }
//    }

    *//*********************securitycode*********************//*
    public static HostnameVerifier wrapHostnameVerifier(final HostnameVerifier
                                                                defaultVerifier) {
        if (defaultVerifier == null)
            throw new IllegalArgumentException("The default verifier may not be null");
        return new SecuringHostnameVerifier(defaultVerifier);
    }

    *//*****************************************************//*

    static class SecuringHostnameVerifier implements HostnameVerifier {
        private HostnameVerifier defaultVerifier;

        public SecuringHostnameVerifier(HostnameVerifier wrapped) {
            defaultVerifier = wrapped;
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {

// if the default verifier accepts the hostname, we are done
            if (defaultVerifier.verify(hostname, session)) {

                return true;
            }
// otherwise, we check if the hostname is an alias for this cert in our keystore
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");

//               InputStream caInput = contextH.getResources().openRawResource(R.raw.tabankingssl);
//                InputStream caInput = contextH.getResources().openRawResource(R.raw.wildssl);change
                InputStream caInput = contextH.getResources().openRawResource(R.raw.certificate);
                //InputStream caInput = contextH.getResources().openRawResource(R.raw.ssl_cert);
                Certificate ca;
                ca = cf.generateCertificate(caInput);

                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore;

                keyStore = KeyStore.getInstance(keyStoreType);

                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);

                X509Certificate cert = (X509Certificate) session.getPeerCertificates()[0];
                if (cert.equals(keyStore.getCertificate(hostname.toLowerCase
                        (Locale.US)))) {

                    return true;
                } else {

                    return false;
                }
            } catch (Exception e) {
                //  e.printStackTrace();
                return false;
            }
        }
    }*/


}

