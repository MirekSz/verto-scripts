package examples.client

import pl.com.stream.lib.sidow.window.message.MessageWindow
import pl.com.stream.next.plugin.database.pub.lib.simpledatasource.FieldType
import pl.com.stream.next.plugin.vedas.pub.lib.simpleeditwindow.SimpleEditWindow



/**
 * Przyklad jak stworzyc okno okreslajac uklad i zakladki
 * 
 * @since 135
 */
class C6D_DataWindowMultiPanel extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def SimpleEditWindow window = dataWindow.create('idOkna');

        //dodanie pol
        window.addField('login', "Login").setValue("mirek");
        window.addField('passowrd', "Hasło");
        window.addField('active', "Aktywny",FieldType.Boolean);
        window.addField('notes', "Uwagi", FieldType.Memo);

        //definicja układu
        window.addPanel("Dane podstawowe",
                """<Row>
                   <Component id="login" /> 
                    <Component id="passowrd" template="password"/> 
                 </Row>
                 <Row>
                   <Component id="active" /> 
                 </Row>""")

        window.addPanel("Uwagi",
                """<Row expand="1"> 
                   <Component id="notes" class="field" fill="both"/> 
                 </Row>""")


        if (window.run()) {
            MessageWindow.showMessage("OK");
        } else {
            System.out.println("cancel");
        }
    }
}
