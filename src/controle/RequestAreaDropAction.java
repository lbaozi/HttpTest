package controle;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

public class RequestAreaDropAction extends DropTargetAdapter {
    private JTextArea requestArea;
    private final static Logger log = Logger.getLogger(RequestAreaDropAction.class);
    
    public RequestAreaDropAction(JTextArea requestArea){
        this.requestArea = requestArea;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void drop(DropTargetDropEvent dtde) {
        
        try {
            dtde.acceptDrop(DnDConstants.ACTION_COPY);
            List<File> fileList = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            for(File file:fileList){
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
                StringBuffer jb = new StringBuffer();
                String line = null;
                while((line = in.readLine()) != null){
                    jb.append(line);
                }
                in.close();
                requestArea.setText(jb.toString());
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
