package examples.client

import pl.com.stream.lib.sidow.window.message.MessageWindow

class C1A_HelloWorld extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    def script() {
        MessageWindow.showMessage("Hello world");
    }
}
