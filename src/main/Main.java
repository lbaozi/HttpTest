package main;

import java.awt.Font;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.apache.log4j.Logger;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import controle.RequestAreaDropAction;
import controle.SendBtnAction;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    
    public static void main(String[] args) {
        Main mainO = new Main();
        mainO.initUI();
        mainO.initGlobalFontSetting(new Font("SimHei", Font.PLAIN, 12));
        JFrame frame = new JFrame("测试工具");
        JPanel panel = new JPanel();
        
        //设置窗口大小
        frame.setSize(400, 600);
        //设置退出按钮动作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置初始位置
        frame.setLocationRelativeTo(null);
        //地址Label
        JLabel urlLabel = new JLabel("服务器地址:");
        JTextField urlField = new JTextField(50);
        urlLabel.setLabelFor(urlField);
        
        //输入请求数据Label
        JLabel requestLabel = new JLabel("发送数据:");
        JTextArea requestArea = new JTextArea(8,50);
        requestArea.setDragEnabled(true);
        DropTarget requestDT = new DropTarget(requestArea, DnDConstants.ACTION_COPY_OR_MOVE, new RequestAreaDropAction(requestArea));      
        requestArea.setDropTarget(requestDT);
        //设置输入框自动换行
        requestArea.setLineWrap(true);
        //设置换行 不断字
        requestArea.setWrapStyleWord(true);
        JScrollPane requestSP = new JScrollPane(requestArea);
        requestLabel.setLabelFor(requestSP);
        
        JLabel responseLabel = new JLabel("接收到的数据:");
        JTextArea responseArea = new JTextArea(8,50);
        responseArea.setLineWrap(true);
        responseArea.setWrapStyleWord(true);
        JScrollPane responseSP = new JScrollPane(responseArea);
        
        JButton sendBtn = new JButton("发送");
        sendBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        SendBtnAction sendBtnAction = new SendBtnAction(urlField, requestArea, responseArea);
        sendBtn.addActionListener(sendBtnAction);
        
        
        panel.add(urlLabel);
        panel.add(urlField);
        panel.add(requestLabel);
        panel.add(requestSP);
        panel.add(responseLabel);
        panel.add(responseSP);
        panel.add(sendBtn);
        
        frame.add(panel);
        frame.setVisible(true);
    }
    
    public void initUI(){
        try{
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible", false);
            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }catch(Exception e){
            logger.info("我擦,我抛出了一个异常:"+e.getMessage());
        }
    }
    
    public void initGlobalFontSetting(Font fnt){
        FontUIResource fontRes = new FontUIResource(fnt);
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }
}
