package examples.client

import pl.com.stream.lib.sidow.window.message.MessageWindow
import pl.com.stream.next.plugin.database.pub.lib.simpledatasource.FieldType
import pl.com.stream.next.plugin.vedas.pub.lib.simpleeditwindow.SimpleEditWindow



/**
 * Przyklad jak stworzyc okno  z roznymi rodzajami pol + pokazanie jak mozna wplywac na wlasciwosci tych pol typu:
 * Filtry do slownikow, 
 * Slowniki definiowane 
 * 
 * @since 135
 */
class C6F_DataWindowAllKindsOfFieldsWithLayout extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def SimpleEditWindow window = dataWindow.create('idOkna');

        window.addField("idOperatorBusinessItemType", "Zakres dostępności", FieldType.Dictionary);
        window.addField("idsBusinessDictionaryItem", "Obiekty biznesowe", FieldType.Object).setDictionary(
                "operatorBusinessItem.idBusinessDictionaryItem");


        window.addField("idBusinessDictionaryItem", "Rodzaj slownika biznesowego", FieldType.Dictionary);
        window.addField("idBusinessDictionaryList", "Pozycja slownika biznesowego", FieldType.Dictionary);

        //   <Property name="idOperatorBusinessItemType" binding="field.main.idOperatorBusinessItemType" /> oznacza
        //   powiazanie property slownik z polem idOperatorBusinessItemType
        window.addPanel("Obiekty biznesowe operatora",
                """<Component id="idOperatorBusinessItemType" />
                  <Row expand="1">
                    <Component id="idsBusinessDictionaryItem" template="Ids" fill="both"> 
                        <Property name="idOperatorBusinessItemType" binding="field.main.idOperatorBusinessItemType" /> 
                   </Component> 
                </Row>""");

        window.addPanel("Sownik biznesowy",
                """<Component id="idBusinessDictionaryList" /> " 
                 <Component id="idBusinessDictionaryItem" > "
                      <Property name="idBusinessDictionaryList" binding="field.main.idBusinessDictionaryList" />
                 </Component>""");


        if (window.run()) {
            MessageWindow.showMessage("OK");
        } else {
            System.out.println("cancel");
        }
    }
}
