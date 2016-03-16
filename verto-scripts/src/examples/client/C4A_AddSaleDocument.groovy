package examples.client

import pl.com.stream.verto.cmm.good.server.pub.main.service.GoodService
import pl.com.stream.verto.sal.document.server.pub.item.SaleDocumentItemDto
import pl.com.stream.verto.sal.document.server.pub.item.SaleDocumentItemService
import pl.com.stream.verto.sal.document.server.pub.main.SaleDocumentDto
import pl.com.stream.verto.sal.document.server.pub.main.SaleDocumentService


class C4A_AddSaleDocument extends pl.com.stream.verto.adm.asen.tools.client.pub.script.api.ClinetScriptEnv {
    /**
     * Wymaga zadeklarowania parametrow wejsciowych w programie
     * @return
     */
    def script() {
        def SaleDocumentService saleDocumentService=context.getService(SaleDocumentService.class);

        SaleDocumentDto document = new SaleDocumentDto();
        document.idSalePlace = inParams.idSalePlace;
        document = saleDocumentService.init(document);
        document.idCustomer = inParams.idCustomer;
        document = saleDocumentService.calculateDto(document, 'idCustomer');

        document.idDocumentDefinition= 100035L; //FVAT
        Long idSaleDocument = saleDocumentService.insert(document);

        saleDocumentService.close(idSaleDocument)

        def SaleDocumentItemService saleDocumentItemService =  context.getService(SaleDocumentItemService.class)
        def GoodService goodService = context.getService(GoodService.class)

        def result =  clientData.get('Dane pozycji', [
            quantity:[label:'Ilość',type:'NUMERIC'],
            price:[label:'Cena',type:'NUMERIC'],
        ]);



        def SaleDocumentItemDto documentItem =  new SaleDocumentItemDto()
        documentItem.idSaleDocument =  idSaleDocument
        documentItem.idGood = goodService.findGoodWithIndex('7001'); //Twarog tlusty
        documentItem.idSaleArticle = goodService.find(documentItem.idGood).idSaleArticle;
        documentItem = saleDocumentItemService.init(documentItem);

        documentItem.idPartStock = 150571L //stan parti

        documentItem.quantity=result.quantity;
        documentItem.netPriceBeforeAllowance = result.price;
        documentItem.grossPriceBeforeAllowance = result.price;
        saleDocumentItemService.insert(documentItem);

        action.open2('pl.com.stream.verto.sal.plugin.sale-document-client.SaleDocumentShowAction', ['ID_BEAN':idSaleDocument])
    }
}
