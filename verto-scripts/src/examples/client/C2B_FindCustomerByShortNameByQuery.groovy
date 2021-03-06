package examples.client

import pl.com.stream.lib.sidow.window.message.MessageWindow
import pl.com.stream.verto.cmm.customer.server.pub.main.CustomerDto
import pl.com.stream.verto.cmm.customer.server.pub.main.CustomerService

/**
 * Przyklad pobrania kontrahent po nazwie skroconej za pomoca serwisu i wyswietlenie jego nazy pelnej.
 * Sytuacja kiedy w serwisie nie mamy dostepnej metody ktora umie wyszukac kontrahenta po nazwie skroconej
 *
 */
class C2B_FindCustomerByShortNameByQuery extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    /**
     * Wymaga zadeklarowania parametru wejsciowego shortName w programie
     * @return
     */
    def script() {
        def CustomerService service = context.getService(CustomerService.class);

        String shortName = inParams.shortName;

        //        Long idCustomer = service.findCustomerByShortcutName(shortName);
        def hql = "SELECT idCustomer FROM Customer WHERE shortcutName = :shortName";
        def List<Long> ids = database.executeQuery(hql, ["shortName":shortName]);
        def Long idCustomer = ids.get(0);

        def CustomerDto dto =service.find(idCustomer);

        MessageWindow.showMessage(dto.fullName);
    }
}
