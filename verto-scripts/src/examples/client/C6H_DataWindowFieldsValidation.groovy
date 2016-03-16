package examples.client

import pl.com.stream.lib.sidow.window.message.MessageWindow
import pl.com.stream.next.plugin.database.pub.lib.simpledatasource.*
import pl.com.stream.next.plugin.vedas.pub.lib.simpleeditwindow.SimpleEditWindow



/**
 * Przyklad jak stworzyc okno z walidacja
 * 
 * @since 135
 */
class C6H_DataWindowFieldsValidation extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def SimpleEditWindow window = dataWindow.create('idOkna');

        window.addField("login", "Login");
        window.addField("password", "Hasło");


        def validator = { Fields fields, ValidationResultBuilder builder ->

            if(fields.get('login').getValue().length() <5)
                builder.addFieldValidationError('login', 'Login musi zawierać więcej niż 5 znaków');
            String password = fields.get('password').getValue();

            if(!password?.contains('A') || !password?.contains('1'))
                builder.addFieldValidationError('password', 'Hasło musi zawierać dużą literę A oraz liczbe 1');
        } as Validator

        window.addValidator(validator)
        if (window.run()) {
            MessageWindow.showMessage("OK");
        } else {
            System.out.println("cancel");
        }
    }
}
