package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public class SendBtnAction implements ActionListener {
    private static final Logger logger = Logger.getLogger(SendBtnAction.class);
    private JTextField urlField;
    private JTextArea requestArea;
    private JTextArea responseArea;
    
    public SendBtnAction(JTextField urlField,JTextArea requestArea,JTextArea responseArea){
        this.urlField = urlField;
        this.requestArea = requestArea;
        this.responseArea = responseArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("准备发送数据");
        String url = urlField.getText();
        String requestData = requestArea.getText();
        
        try {
            HttpURLConnection client = (HttpURLConnection) new URL(url).openConnection();
            client.setConnectTimeout(200000);
            client.setReadTimeout(200000);
            client.setDoInput(true);
            client.setDoOutput(true);
            OutputStream out =  client.getOutputStream();
            out.flush();
            out.write(requestData.getBytes());
            logger.info("发送数据"+requestData);
            
            int code = client.getResponseCode();
            if(code != 200){
                throw new Exception(client.getResponseMessage());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
            String temp = "";
            String responseData = "";
            while((temp = in.readLine()) != null){
                responseData += temp;
            }
            out.close();
            in.close();
            responseArea.setText(responseData);
            logger.info("接收到的数据:"+responseData);
        } catch (MalformedURLException e1) {
            logger.error("oh!又他妈出错了:",e1);
        } catch (IOException e1) {
            logger.error("oh!又他妈出错了:",e1);
        } catch (Exception e1) {
            logger.error("请求发生异常:",e1);
        }
    }
    
    public String getCharset(String str){
        Pattern p = Pattern.compile("charset=(\\w+)");
        Matcher m = p.matcher(str);
        String charset = "";
        
        if(m.find()){
            charset = m.group(1);
        }
        
        logger.info(charset);
        return charset;
    }

}
