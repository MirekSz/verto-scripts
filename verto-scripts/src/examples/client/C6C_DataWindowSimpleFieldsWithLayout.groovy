package examples.client

import pl.com.stream.next.plugin.database.pub.lib.simpledatasource.FieldType
import pl.com.stream.next.plugin.vedas.pub.lib.simpleeditwindow.SimpleEditWindow



/**
 * Przyklad jak stworzyc okno okreslajac uklad
 * 
 * @since 135
 */
class C6C_DataWindowSimpleFieldsWithLayout extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def SimpleEditWindow window = dataWindow.create('idOkna');

        //dodanie pol
        window.addField('login', "Login").setValue("mirek");
        window.addField('passowrd', "Hasło");
        window.addField('active', "Aktywny",FieldType.Boolean);


        //definicja układu
        window.addPanel("Dane podstawowe",
                """<Row>
                   <Component id="login" /> 
                    <Component id="passowrd" /> 
                 </Row>
                <Component id="panelSeparator1" class="separator" />
                 <Row>
                   <Component id="active" /> 
                 </Row>""")



        if (window.run()) {
            System.out.println("ok");
        } else {
            System.out.println("cancel");
        }
    }
}
