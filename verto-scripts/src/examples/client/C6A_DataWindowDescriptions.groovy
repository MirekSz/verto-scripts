package examples.client

import pl.com.stream.lib.commons.date.Date
import pl.com.stream.next.plugin.vedas.pub.lib.simpleeditwindow.SimpleEditWindow



/**
 * Przyklad jak stworzyc okno okreslajac ikonki oraz etykiety.
 * @since 135
 *
 */
class C6A_DataWindowDescriptions extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def SimpleEditWindow window = dataWindow.create('idOkna');
        window.setWindowTitle("Dodaj");
        window.setBanerTitle("Załuż nowego operatora");
        window.setExecuteButtonLabel("Załóż");
        window.setCancelButtonLabel("Zamknij");

        window.setIcon('servicePlace','servicePlace');

        if (window.run()) {
            System.out.println("ok");
        } else {
            System.out.println("cancel");
        }
        def w=dataWindow.create('a');
        Date d= w.getFieldsValue().get('a');
    }
}
