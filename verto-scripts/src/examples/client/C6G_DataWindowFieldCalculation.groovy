package examples.client

import pl.com.stream.lib.commons.date.*
import pl.com.stream.lib.sidow.window.message.MessageWindow
import pl.com.stream.next.plugin.database.pub.lib.simpledatasource.*
import pl.com.stream.next.plugin.vedas.pub.lib.simpleeditwindow.SimpleEditWindow


/**
 * Przyklad jak stworzyc okno z polami ktorych wartosci wplywaja na inne pola
 * 
 * @since 135
 */
class C6G_DataWindowFieldCalculation extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def SimpleEditWindow window = dataWindow.create('idOkna');
        window.addField("login", "Login");
        window.addField("password", "Hasło");

        def emailReqiredValueListener = {
            Fields fields, Field changeField ->

            if(changeField.getValue()){
                fields.get('email').setState(State.Required);
            }else{
                fields.get('email').setState(State.Invisible);
            }
            //ONE LINE  fields.get('email').setState(changeField.getValue()?State.Required:State.Invisible);
        } as FieldValueChangeListener

        window.addField("emailReqired", "Wymaga podania maila", FieldType.Boolean).addFieldValueChangeListener(emailReqiredValueListener)
        window.addField("email", "E-mail").setState(State.Invisible);


        def timeValueCHangeListener = {
            Fields fields, Field changeField ->
            def Date startTime = fields.get('startWorkTime').getValue();
            def  Date endTime = fields.get('endWorkTime').getValue();
            fields.get('wortTime').setValue(endTime.hour - startTime.hour);
        } as FieldValueChangeListener


        window.addField("startWorkTime", "Czas rozpoczęcia pracy", FieldType.Time)
                .setValue(Date.getCurrentDateAndTime()).addFieldValueChangeListener(timeValueCHangeListener);
        window.addField("endWorkTime", "Czas zakończenia pracy", FieldType.Time)
                .setValue(Date.getCurrentDateAndTime().addHour(5)).addFieldValueChangeListener(timeValueCHangeListener);

        window.addField("wortTime", "Twój czas pracy").setState(State.NotEditable);


        if (window.run()) {
            MessageWindow.showMessage("OK");
        } else {
            System.out.println("cancel");
        }
    }
}
