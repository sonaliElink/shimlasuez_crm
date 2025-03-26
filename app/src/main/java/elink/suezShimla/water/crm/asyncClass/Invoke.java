package elink.suezShimla.water.crm.asyncClass;

import android.content.Context;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import elink.suezShimla.water.crm.Utils.Constants;

public class Invoke {
    private Context mCon;
    public String getDataWOParams(String URL, String NAMESPACE, String methName)
            throws Exception {
        SoapObject request = new SoapObject(NAMESPACE, methName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(NAMESPACE + methName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            return response.toString();
        } catch (Exception e) {
            throw e;
        }
    }
    public String getOtherData(String URL, String NAMESPACE, String methName,String username,String password, String[] params, String[] paraName)
            throws Exception {

        SoapObject request = new SoapObject(NAMESPACE, methName);

        PropertyInfo paramName[] = new PropertyInfo[paraName.length];
        int iVal = 0;
        for (PropertyInfo para : paramName) {
            para = new PropertyInfo();
            para.setName(paraName[iVal]);
            para.setValue(params[iVal]);
            para.setType(String.class);
            request.addProperty(para);
            iVal++;
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

//-------------

        Element[] header = new Element[1];
        header[0] = new Element().createElement(Constants.NameSpace, "AuthenticationHeader");
        // header[0].addChild(Node.ELEMENT, header[0]);

        Element apiKey = new Element().createElement(Constants.NameSpace, "UserName");
        apiKey.addChild(Node.TEXT, username);
        header[0].addChild(Node.ELEMENT, apiKey);
        Element pass = new Element().createElement(Constants.NameSpace, "Password");
        pass.addChild(Node.TEXT, password);
        header[0].addChild(Node.ELEMENT, pass);

        envelope.dotNet = true;
        envelope.headerOut = header;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

           /* List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
            headerList.add(new HeaderProperty("DevToken","ELINK@QW"));*/
            androidHttpTransport.call(NAMESPACE + methName, envelope);



            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                return response.toString();
        } catch (Exception e) {
            throw e;
        }
    }
    public String getuploadphoto(String URL, String NAMESPACE, String methName,String[] params, String[] paraName)
            throws Exception {

        SoapObject request = new SoapObject(NAMESPACE, methName);
        try {
            PropertyInfo paramName[] = new PropertyInfo[paraName.length];
            int iVal = 0;
            for (PropertyInfo para : paramName) {
                para = new PropertyInfo();
                para.setName(paraName[iVal]);
                para.setValue(params[iVal]);
                para.setType(String.class);
                request.addProperty(para);
                iVal++;
            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            //List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();

            // headerPropertyList.add(new HeaderProperty("Authorization", "Basic : cG9ydHdzOjEyM3F3ZUFTRA=="));

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 1000 * 60);

            try {
                //Ssl_pinning.allowCERSSL(mCon);
                //TrustManagerManipulator.allowAllSSL();

                androidHttpTransport.call(NAMESPACE + methName, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    return response.toString();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
        return null;
    }

    public String getDataWITHParams(String URL, String NAMESPACE, String methName,String[] params, String[] paraName)
            throws Exception {

        SoapObject request = new SoapObject(NAMESPACE, methName);
        try {
            PropertyInfo paramName[] = new PropertyInfo[paraName.length];
            int iVal = 0;
            for (PropertyInfo para : paramName) {
                para = new PropertyInfo();
                para.setName(paraName[iVal]);
                para.setValue(params[iVal]);
                para.setType(String.class);
                request.addProperty(para);
                iVal++;
            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            //List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();

           // headerPropertyList.add(new HeaderProperty("8Authorization", "Basic : cG9ydHdzOjEyM3F3ZUFTRA=="));

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 1000 * 60);

            try {
                //Ssl_pinning.allowCERSSL(mCon);
                //TrustManagerManipulator.allowAllSSL();

                androidHttpTransport.call(NAMESPACE + methName, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    return response.toString();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
        return null;
    }


    public String getDataWOParamslogout(String URL, String NAMESPACE, String methName,String username,String password)
            throws Exception {

        SoapObject request = new SoapObject(NAMESPACE, methName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

//-------------

        Element[] header = new Element[1];
        header[0] = new Element().createElement(Constants.NameSpace, "AuthenticationHeader");
        // header[0].addChild(Node.ELEMENT, header[0]);

        Element apiKey = new Element().createElement(Constants.NameSpace, "UserName");
        apiKey.addChild(Node.TEXT, username);
        header[0].addChild(Node.ELEMENT, apiKey);
        Element pass = new Element().createElement(Constants.NameSpace, "Password");
        pass.addChild(Node.TEXT, password);
        header[0].addChild(Node.ELEMENT, pass);

        envelope.dotNet = true;
        envelope.headerOut = header;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

           /* List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
            headerList.add(new HeaderProperty("DevToken","ELINK@QW"));*/
            androidHttpTransport.call(NAMESPACE + methName, envelope);



            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            return response.toString();
        } catch (Exception e) {
            throw e;
        }
    }

}
