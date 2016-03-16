package examples.client

import pl.com.stream.verto.cmm.customer.server.pub.main.CustomerDto
import pl.com.stream.verto.cmm.customer.server.pub.main.CustomerService

/**
 * Przyklad pobrania kontrahent po nazwie skroconej za pomoca serwisu i wywoluje akcje Pokaz
 *
 */
class C2C_FindCustomerByShortNameAndShowIt extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    /**
     * Wymaga zadeklarowania parametru wejsciowego shortName w programie
     * @return
     */
    def script() {
        def CustomerService service = context.getService(CustomerService.class);

        String shortName = inParams.shortName;

        Long idCustomer = service.findCustomerByShortcutName(shortName);

        def CustomerDto dto =service.find(idCustomer);


        action.open2("pl.com.stream.verto.cmm.plugin.customer-client.CustomerShowAction", ['ID_BEAN':idCustomer])
    }
}
