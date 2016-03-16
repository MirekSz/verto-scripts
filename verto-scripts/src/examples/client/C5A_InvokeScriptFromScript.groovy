package examples.client

import pl.com.stream.next.asen.common.groovy.api.common.ScriptReference


/**
 * Wywolanie skryptu ze skryptu
 *
 */
class C5A_InvokeScriptFromScript extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def result = clientData.get('Podaj dane osobowe', [
            firstName:[label:'Imię'],
            lastName:[label:'Nazwisko'],
            grupa:[label:'Grupa']
        ]);

        def ScriptReference script = script.find("AddOperatorAndGroup"); //pobieram skrupt po nazwie lub id patrz skrypt P2A_AddOperatorAndGroup
        def scritExecutionResult = script.execute(["firstName":firstName, "lastName":lastName, "grupName":grupa]); //wywoluje skrypt przekazuja mu parametry wejsciowe
        def idOperator = scritExecutionResult.idOperator //odbieram wynik i czyta

        action.open2("pl.com.stream.verto.cmm.plugin.operator-client.OperatorShowAction", ['ID_BEAN':idOperator])
    }
}
