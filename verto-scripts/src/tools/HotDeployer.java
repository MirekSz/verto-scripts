package tools;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.FileOperator;
import pl.com.stream.verto.adm.asen.tools.server.pub.script.repo.ScriptDataInfo;

/***
 * Watek do automatycznego zapisu
 * 
 * @author Mirek
 * 
 */
public class HotDeployer implements Runnable {
    Map<String, ScriptDataInfo> files = new HashMap<String, ScriptDataInfo>();
    Map<String, Long> lastModyfiDate = new HashMap<String, Long>();

    public HotDeployer(List<ScriptDataInfo> selectedObjects) {
        for (ScriptDataInfo info : selectedObjects) {
            String destFile = new FileOperator(
                    SynchronizationController.appLocation, info.getName(),
                    info.getType()).getDestFile();
            files.put(destFile, info);
            lastModyfiDate.put(destFile, new File(destFile).lastModified());
        }
    }

    @Override
    public void run() {
        while (true) {
            for (String fileLocation : files.keySet()) {
                File file = new File(fileLocation);
                long lastModified = file.lastModified();
                if (lastModyfiDate.get(fileLocation) != null
                        && lastModyfiDate.get(fileLocation) < lastModified) {
                    lastModyfiDate.put(fileLocation, lastModified);
                    SynchronizationController.updateScript(files
                            .get(fileLocation));
                    System.out.println("updating " + file);
                }
                try {
                    Thread.sleep(1000);
                    Thread.yield();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
