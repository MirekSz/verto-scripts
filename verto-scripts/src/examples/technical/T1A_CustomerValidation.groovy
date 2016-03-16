package examples.technical

import pl.com.stream.next.asen.server.service.ValidationResultBuilder
import pl.com.stream.verto.cmm.customer.server.pub.main.CustomerDto

class T1A_CustomerValidation extends pl.com.stream.next.asen.common.groovy.TechnicalEventScriptEnv {
	def script() {
		def CustomerDto dto= event.methodParameters[0];
		ValidationResultBuilder builder= event.methodResult.result;
		if(dto.idProvince == null){
			builder.addErrorValidationInfo('Proszę podać wojewódzctwo',CustomerDto.ID_PROVINCE)
		}
	}
}
