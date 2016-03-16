package examples.client

import pl.com.stream.next.plugin.database.pub.lib.simpledatasource.FieldType
import pl.com.stream.next.plugin.vedas.pub.lib.simpleeditwindow.SimpleEditWindow



/**
 * Przyklad jak stworzyc okno z podstawowym rozkladem tzn jednokolumnowym
 *  @since 135
 *
 */
class C6B_DataWindowSimpleFields extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def SimpleEditWindow window = dataWindow.create('idOkna');

        //dodanie pol
        window.addField('login', "Login").setValue("mirek");
        window.addField('passowrd', "Hasło");
        window.addField('active', "Aktywny",FieldType.Boolean);

        if (window.run()) {
            System.out.println("ok");
        } else {
            System.out.println("cancel");
        }
    }
}
