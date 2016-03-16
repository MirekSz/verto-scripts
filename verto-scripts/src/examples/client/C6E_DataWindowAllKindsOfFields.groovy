package examples.client

import pl.com.stream.lib.sidow.window.message.MessageWindow
import pl.com.stream.next.plugin.database.pub.lib.simpledatasource.FieldType
import pl.com.stream.next.plugin.vedas.pub.lib.simpleeditwindow.SimpleEditWindow



/**
 * Przyklad jak stworzyc okno z roznymi rodzajami pol
 * 
 * @since 135
 */
class C6E_DataWindowAllKindsOfFields extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def SimpleEditWindow window = dataWindow.create('idOkna');


        window.addField("login", "Login");
        window.addField("password", "Hasło").setValue("1");
        window.addField("changePasswordReq", "Wymagana zmiana hasła", FieldType.Boolean);

        window.addField("idOperatorGroup", "Grupa domyślna", FieldType.Dictionary);

        window.addField("birthDate", "Wiek", FieldType.DateTime);

        window.addField("sex", "Płeć", FieldType.ValuesList).setValues("Mężczyzna", "Kobieta").setValue("Mężczyzna");

        window.addField("age", "Wiek", FieldType.Integer);

        window.addField("workDistanse", "Dystans do pracy", FieldType.Numeric).setPrecision(1);
        window.addField("startWorkTime", "Czas rozpoczęcia pracy", FieldType.Time);
        window.addField("endWorkTime", "Czas zakończenia pracy", FieldType.Time);


        window.addField("notes", "Uwagi", FieldType.Memo).setLength(20);//


        window.addField("idOperatorManager", "Kierownik", FieldType.Dictionary).setDictionary("idOperator");
        window.addField("idCustomerGuardian", "Opiekun", FieldType.Dictionary).setDictionary("idOperator");



        if (window.run()) {
            MessageWindow.showMessage("OK");
        } else {
            System.out.println("cancel");
        }
    }
}
