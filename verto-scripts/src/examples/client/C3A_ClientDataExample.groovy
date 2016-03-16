package examples.client

import pl.com.stream.lib.sidow.window.message.MessageWindow

class C3A_ClientDataExample extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        def result = clientData.get('Podaj dane osobowe', [
            firstName:[label:'Imię'],
            lastName:[label:'Nazwisko',value:'Nowak'],
            age:[label:'Wiek', type:'INTEGER', value:23],
            operatorLean: [label: 'Operator polecający', binding:'idOperator'],
            sex: [label:'Płeć', values:['Meżczyczna', 'Kobieta'], value: 'Kobieta']
        ]);


        MessageWindow.showMessage(result.firstName)
    }
}
