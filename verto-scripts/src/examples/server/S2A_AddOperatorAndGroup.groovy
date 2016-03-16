package examples.server

import pl.com.stream.verto.cmm.operator.server.pub.group.OperatorGroupDto
import pl.com.stream.verto.cmm.operator.server.pub.group.OperatorGroupService
import pl.com.stream.verto.cmm.operator.server.pub.main.OperatorDto
import pl.com.stream.verto.cmm.operator.server.pub.main.OperatorService
/**
 * Dodaje operatora do grupy. Grupa podaje jako string i teraz jezeli nie istnieje grupa o takiej nazwie to ja dodaje
 *
 */
class S2A_AddOperatorAndGroup extends pl.com.stream.next.asen.common.groovy.ServerScriptEnv {
    def script() {

        def groupName = inParams.groupName; //odczyt parametrow wejsciowych

        def OperatorGroupService operatorGroupService = context.getService(OperatorGroupService.class);

        def getidOperatorGroupForName = "SELECT idOperatorGroup FROM OperatorGroup WHERE name = :name";
        def List<Long> ids = database.executeQuery(getidOperatorGroupForName, ["name":groupName]);
        def idGroup;

        if(ids.isEmpty()){
            OperatorGroupDto operatorGroupDto = operatorGroupService.init(new OperatorGroupDto());
            operatorGroupDto.name = groupName;
            idGroup = operatorGroupService.insert(operatorGroupDto);
        }else{
            idGroup = ids.get(0);
        }


        def OperatorService operatorService = context.getService(OperatorService.class);
        def OperatorDto operatorDto= operatorService.init(new OperatorDto());

        operatorDto.firstName = 'Jan';
        operatorDto.lastName = 'Kowalski';
        operatorDto.login = 'login'+System.currentTimeMillis();
        operatorDto.password = 'admin';
        operatorDto.retypePassword = 'admin';

        def getidPasswordRule = "SELECT idPasswordRule FROM PasswordRule";
        def List<Long> passwordRulesIds = database.executeQuery(getidOperatorGroupForName);

        operatorDto.idPasswordRule = passwordRulesIds.get(0);
        operatorDto.idOperatorGroup = idGroup;

        def idOperator = operatorService.insert(operatorDto);

        outParams.idOperator = idOperator //napchanie parametrow wyjsciowych
    }
}
