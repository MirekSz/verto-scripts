package examples.client

import pl.com.stream.lib.sidow.window.message.MessageWindow

class C1B_HelloWorldWithParam extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    /**
     * Wymaga zadeklarowania parametru wejsciowego firstName w programie
     * @return
     */
    def script() {


        MessageWindow.showMessage("Hello "+inParams.firstName);
    }
}
